package design;

import java.util.*;

/**
 * 355. 设计推特
 * <p>
 * 设计一个简化版的推特(Twitter)，可以让用户实现发送推文，关注/取消关注其他用户，能够看见关注人（包括自己）的最近十条推文。
 * 你的设计需要支持以下的几个功能：
 * postTweet(userId, tweetId): 创建一条新的推文
 * getNewsFeed(userId): 检索最近的十条推文。每个推文都必须是由此用户关注的人或者是用户自己发出的。
 *                      推文必须按照时间顺序由最近的开始排序。
 * follow(followerId, followeeId): 关注一个用户
 * unfollow(followerId, followeeId): 取消关注一个用户
 * <p>
 * 思路：
 * 1. 设计子类
 * 类1 Tweet：链表，变量为时间戳，id，（时间戳更小的）下一条推文 next；方法为 id 的 getter；
 * 类2 User：用户，变量为 id，当前的最新推文 head（Tweet 类型），关注列表（包括自己的 id 的散列集）；
 *          方法为 关注、取消关注、发推文（链表以时间戳最大的为头部）；
 * 2. 设计主类
 * 变量：时间戳，最大返回推文数，用户映射（id : user）；
 * 方法：除获取推文方法 getNewsFeed 外，其他均使用 User 类的定义方法；
 * getNewsFeed()：本质是合并 k 个有序链表，使用优先队列（大/小顶堆）实现；
 * 顺序为 userId -> followeeList -> queue -> resultant Tweet List；
 */
public class Twitter {

    int timestamp;
    Map<Integer, User> users;
    int TWEETS_LIMIT = 10;

    class Tweet {
        int tweetId;
        int timestamp;
        Tweet next;

        Tweet(int userId, int timestamp) {
            this.tweetId = userId;
            this.timestamp = timestamp;
        }

        public int getTweetId() {
            return tweetId;
        }
    }

    class User {
        int userId;
        Tweet head;
        Set<Integer> followList;

        User(int userId) {
            this.userId = userId;
            followList = new HashSet<>();
            followList.add(userId);
        }

        void follow(int followeeId) {
            followList.add(followeeId);
        }

        void unfollow(int followeeId) {
            if (followeeId != this.userId)
                followList.remove(followeeId);
        }

        void postTweet(int tweetId) {
            Tweet newHead = new Tweet(tweetId, ++timestamp);
            newHead.next = head;
            head = newHead;
        }

        public Set<Integer> getFollowList() {
            return followList;
        }

        public Tweet getHead() {
            return head;
        }
    }

    /**
     * Initialize your data structure here.
     */
    public Twitter() {
        users = new HashMap<>();
    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        if (!users.containsKey(userId)) {
            User user = new User(userId);
            user.postTweet(tweetId);
            users.put(userId, user);
        } else {
            users.get(userId).postTweet(tweetId);
        }
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed.
     * Each item in the news feed must be posted by users who the user followed or by the user herself.
     * Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>(TWEETS_LIMIT);
        if (users.containsKey(userId)) {
            List<Integer> followeeList = List.copyOf(users.get(userId).getFollowList());
            PriorityQueue<Tweet> queue = new PriorityQueue<>(Comparator.comparingInt(tweet -> -tweet.timestamp));
            for (int id : followeeList) {
                if (users.containsKey(id)) {
                    Tweet head = users.get(id).getHead();
                    if (head != null) queue.offer(head);
                }
            }

            while (!queue.isEmpty() && res.size() < TWEETS_LIMIT) {
                Tweet curr = queue.poll();
                res.add(curr.getTweetId());
                curr = curr.next;
                if (curr != null) queue.offer(curr);
            }
        }

        return res;
    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        if (!users.containsKey(followerId)) {
            User user = new User(followerId);
            user.follow(followeeId);
            users.put(followerId, user);
        } else users.get(followerId).follow(followeeId);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        if (!users.containsKey(followerId)) {
            User user = new User(followerId);
            user.unfollow(followeeId);
            users.put(followerId, user);
        } else users.get(followerId).unfollow(followeeId);
    }
}
