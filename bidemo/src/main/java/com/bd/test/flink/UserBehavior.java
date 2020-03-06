package com.bd.test.flink;

public class UserBehavior {
    public long userId; // 用户ID
    public long itemId; // 商品ID
    public int categoryId; // 商品类目ID
    public String behavior; // 用户行为, 包括("pv", "buy", "cart", "fav")
    public long timestamp;
}
