package com.test.springdemo.dp;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 递归和动态规划
 */
public class Solution {
    private static class Interval {
        int start;
        int end;
        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private static Integer removeDuplicateIntervas(Interval[] intervals) {
        // 将区间按起始点由小到大进行排序
        Arrays.sort(intervals, Comparator.comparingInt(a -> a.start));
        // 首次遍历从 -1,0 开始
        return removeSubDuplicate(-1, 0, intervals);
    }

    private static Integer removeSubDuplicate(int pre, int cur, Interval[] intervals) {
        if (cur == intervals.length) {
            return 0;
        }

        int notRemove = Integer.MAX_VALUE;
        if (pre == -1 || intervals[pre].end <= intervals[cur].start) {

            /**
             * 如果是首次遍历，或者 pre 区间的终点小于 cur 区间的起点（即区间不重叠）,
             * 则 pre = cur; cur = cur+1
             */
            notRemove = removeSubDuplicate(cur, cur+1, intervals);
        }

        /**
         * 如果 pre 区间的终点大于 cur 区间的起点，代表两区间重叠，cur 指向后一个区间，即 cur = cur + 1
         * 代表 cur 被移除，所以需要加1（代表这个区间被移除了）
         */
        int remove = removeSubDuplicate(pre, cur+1, intervals) + 1;

        // 取两者的较小值
        return Math.min(notRemove, remove);
    }

    private static boolean isOverlapping(Interval i, Interval j) {
        return j.end > i.start;
    }
    /**
     * 动态规划解
     * @param intervals
     */
    private static Integer removeSubDuplicateWithDP(Interval[] intervals){
        Arrays.sort(intervals,Comparator.comparingInt(a->a.start));
        int[] dp=new int[intervals.length];
        Arrays.fill(dp,0);
        dp[0]=1;
        int result=1;
        for (int i = 1; i < intervals.length; i ++) {
            int max=0;
            for(int j=0;j<i;j++){
                if (!isOverlapping(intervals[i], intervals[j])) {
                    max=Math.max(dp[j],max);
                }
            }
            dp[i]=max+1;
        }
        return intervals.length - dp[intervals.length - 1];
    }

    public static  void main(String[] args) {
        // 初始化区间
        Interval[] intervals = {
                new Interval(1, 2),
                new Interval(3, 5),
                new Interval(4, 7),
                new Interval(8, 10),
                new Interval(9, 11)
        };
        int result = removeDuplicateIntervas(intervals);
        System.out.println("result = " + result);
        int dpresult=removeSubDuplicateWithDP(intervals);
        System.out.println("dpresult = " + dpresult);
    }
}
