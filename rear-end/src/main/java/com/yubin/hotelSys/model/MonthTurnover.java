package com.yubin.hotelSys.model;

import lombok.Data;

@Data
public class MonthTurnover {
    private String month;  // 月份
    private double money;  // 当月的营业额
    private int orderCount;  // 当月的交易量
}
