package com.yubin.hotelSys.dto;

import lombok.Data;

@Data
public class OrderSearchFormDTO {
    private String roomId;
    private String startTime;
    private String endTime;
}
