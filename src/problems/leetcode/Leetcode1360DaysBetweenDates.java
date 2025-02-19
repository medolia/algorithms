package problems.leetcode;

/**
 * <a href="https://leetcode.cn/problems/number-of-days-between-two-dates/?envType=company&envId=huawei&favoriteSlug=huawei-six-months">1360. 日期之间隔几天</a><br>
 * 题目描述:
 * 请你编写一个程序来计算两个日期之间隔了多少天。
 * <p>
 * 日期以字符串形式给出，格式为 YYYY-MM-DD，如示例所示。
 * <p>
 * 示例 1：
 * <p>
 * 输入：date1 = "2019-06-29", date2 = "2019-06-30"
 * 输出：1
 * 示例 2：
 * <p>
 * 输入：date1 = "2020-01-15", date2 = "2019-12-31"
 * 输出：15
 * <p>
 * <p>
 * 提示：
 * <p>
 * 给定的日期是 1971 年到 2100 年之间的有效日期。
 * <p>思路: 所有日期转化为距 1971-01-01 的天数再相减取绝对值即可
 *
 * <p>时间复杂度:O(1)
 * <p>空间复杂度:O(1)
 */
class Leetcode1360DaysBetweenDates {

    private static final int[] MONTH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static void main(String[] args) {
        // 测试示例1
        String date1 = "2019-06-29";
        String date2 = "2019-06-30";
        System.out.println(new Leetcode1360DaysBetweenDates().daysBetweenDates(date1, date2)); // 期待 1

        // 测试示例2
        date1 = "2020-01-15";
        date2 = "2019-12-31";
        System.out.println(new Leetcode1360DaysBetweenDates().daysBetweenDates(date1, date2)); // 期待 15
    }

    /**
     * 计算 date1 和 date2 相差多少天（绝对值）
     */
    public int daysBetweenDates(String date1, String date2) {
        // 解析字符串
        int[] ymd1 = parseDate(date1); // [year, month, day]
        int[] ymd2 = parseDate(date2);

        // 计算各自距离 1970-01-01 的天数
        long days1 = daysFrom1970(ymd1[0], ymd1[1], ymd1[2]);
        long days2 = daysFrom1970(ymd2[0], ymd2[1], ymd2[2]);

        // 返回绝对值
        return (int) Math.abs(days2 - days1);
    }

    /**
     * 将 "YYYY-MM-DD" 解析为 [year, month, day]
     */
    private int[] parseDate(String dateStr) {
        String[] parts = dateStr.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        return new int[]{year, month, day};
    }

    /**
     * 计算从 1970-01-01 到 (year, month, day) 之间的天数（可能为正或负）
     * 注意：month 的值为 1~12
     */
    private long daysFrom1970(int year, int month, int day) {
        long totalDays = 0;
        // 1. 累加完整年份（1970 ~ year-1）
        //    判断每年是闰年就加 366 天，否则 365
        for (int y = 1970; y < year; y++) {
            totalDays += (isLeapYear(y) ? 366 : 365);
        }

        // 2. 在当前 year 年内，累加该年 1~(month-1) 所有月份的天数
        for (int m = 1; m < month; m++) {
            totalDays += MONTH_DAYS[m - 1];
            // 如果是闰年 且 m=2 走完二月 就再加 1 天
            if (m == 2 && isLeapYear(year)) {
                totalDays += 1;
            }
        }

        // 3. 最后加上当月的 day
        totalDays += day;

        return totalDays;
    }

    /**
     * 判断是否闰年
     * 闰年规则：
     *   1) 能被 400 整除
     *   2) 或能被 4 整除但不能被 100 整除
     */
    private boolean isLeapYear(int y) {
        return (y % 400 == 0) || ((y % 4 == 0) && (y % 100 != 0));
    }

}
