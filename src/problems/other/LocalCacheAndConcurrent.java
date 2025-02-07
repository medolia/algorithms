package problems.other;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 并发编程试题
 */
public class LocalCacheAndConcurrent {

    // =============== 测试演示 ===============
    public static void main(String[] args) {
        LocalCacheAndConcurrent outer = new LocalCacheAndConcurrent();
        AService aService = outer.new AService();

        // 构造一个最多100个ID的测试场景，这里仅演示
        List<Integer> testIds = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            testIds.add(i);
        }

        Map<Integer, ShopInfo> result = aService.getShopInfo(testIds);
        for (Map.Entry<Integer, ShopInfo> entry : result.entrySet()) {
            Integer id = entry.getKey();
            ShopInfo info = entry.getValue();
            System.out.println("ID=" + id + " => Name=" + info.getName() + ", Addr=" + info.getAddress());
        }
    }

    public class ShopInfo {
        private Integer id;
        private String name;
        private String address;

        public ShopInfo(Integer id, String name, String address) {
            this.id = id;
            this.name = name;
            this.address = address;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }
    }

    public class ShopService {

        // 已经提供的方法1:批量根据商户id查询商户名称
        // 限制:入参shopIds不大于10个
        public Map<Integer, String> getShopName(List<Integer> shopIds) {
            // 这里仅示例，可自行替换为实际调用逻辑
            Map<Integer, String> result = new HashMap<>();
            for (Integer id : shopIds) {
                result.put(id, "Name_" + id);
            }
            return result;
        }

        // 已经提供的方法2:批量根据商户id查询商户地址
        // 限制:入参shopIds不大于10个
        public Map<Integer, String> getShopAddress(List<Integer> shopIds) {
            // 这里仅示例，可自行替换为实际调用逻辑
            Map<Integer, String> result = new HashMap<>();
            for (Integer id : shopIds) {
                result.put(id, "Addr_" + id);
            }
            return result;
        }

    }

    public class AService {

        private static final Object lock = new Object();
        // 本地缓存：key 为shopId，value 为 ShopInfo
        private final ConcurrentMap<Integer, ShopInfo> localCache = new ConcurrentHashMap<>();

        private final ShopService shopService = new ShopService();

        /**
         * 目标：根据商户id批量查询商户信息，入参shopIds不大于100个。
         * <p>
         * 要求：
         * 1. 并行调用 ShopService 提供的 getShopName 和 getShopAddress 方法获取商户名称和地址，构造 ShopInfo。
         * 2. 从 ShopService 获取数据时，需要满足“入参shopIds不大于10个”的限制条件，即需要分批调用。
         * 3. 实现本地缓存，优先从本地缓存获取数据，如果本地缓存没有数据，再从 ShopService 获取数据。
         */
        public Map<Integer, ShopInfo> getShopInfo(List<Integer> shopIds) {
            // 1. 去重、过滤非法ID
            List<Integer> distinctIds = shopIds.stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());

            // 2. 优先从缓存获取
            Map<Integer, ShopInfo> resultMap = new HashMap<>();
            List<Integer> missingIds = new ArrayList<>();

            for (Integer id : distinctIds) {
                ShopInfo cached = localCache.get(id);
                if (cached != null) {
                    resultMap.put(id, cached);
                } else {
                    missingIds.add(id);
                }
            }

            // 如果所有数据都在缓存中，直接返回
            if (missingIds.isEmpty()) {
                return resultMap;
            }

            // 3. 分批调用ShopService
            //   并行地获取 nameMap 和 addressMap
            //   这里使用 CompletableFuture 做并行演示：
            CompletableFuture<Map<Integer, String>> nameFuture =
                    CompletableFuture
                            .supplyAsync(() -> batchGet(missingIds, shopService::getShopName));
            CompletableFuture<Map<Integer, String>> addressFuture =
                    CompletableFuture.supplyAsync(() -> batchGet(missingIds, shopService::getShopAddress));

            // 4. 等待两项并行任务完成，并合并结果
            Map<Integer, String> nameMap;
            Map<Integer, String> addressMap;
            try {
                nameMap = nameFuture.get();      // 阻塞等待结果
                addressMap = addressFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                // 真实场景中需要根据业务处理异常，这里仅简单抛出
                throw new RuntimeException("并行查询失败", e);
            }

            // 5. 组装 ShopInfo, 更新缓存
            for (Integer id : missingIds) {
                String name = nameMap.get(id);
                String addr = addressMap.get(id);
                if (name == null && addr == null) {
                    // 说明远程没有查到该id，可根据业务需要进行特殊处理
                    continue;
                }
                ShopInfo newInfo = new ShopInfo(id, name, addr);
                // 使用锁或直接 putIfAbsent 避免并发覆盖
                localCache.put(id, newInfo);
                resultMap.put(id, newInfo);
            }

            return resultMap;
        }

        /**
         * 将较大列表分批，每批不超过10个，然后循环调用指定的函数进行查询，合并结果。
         */
        private Map<Integer, String> batchGet(List<Integer> shopIds,
                                              Function<List<Integer>, Map<Integer, String>> fetchFunction) {
            Map<Integer, String> combinedResult = new HashMap<>();
            int batchSize = 10;
            for (int start = 0; start < shopIds.size(); start += batchSize) {
                int end = Math.min(start + batchSize, shopIds.size());
                List<Integer> subList = shopIds.subList(start, end);
                // 调用 ShopService 相关方法获取数据
                Map<Integer, String> partialResult = fetchFunction.apply(subList);
                if (partialResult != null) {
                    combinedResult.putAll(partialResult);
                }
            }
            return combinedResult;
        }
    }
}
