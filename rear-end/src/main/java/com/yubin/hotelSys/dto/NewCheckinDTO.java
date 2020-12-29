package com.yubin.hotelSys.dto;

import lombok.Data;

@Data
public class NewCheckinDTO {
    private String guestId;
    private String guestName;
    private String guestSex;
    private String guestTel;
    private String roomId;
    private String group;
    private int useDays;
    private Double discountRatio;
    private int age;
}
