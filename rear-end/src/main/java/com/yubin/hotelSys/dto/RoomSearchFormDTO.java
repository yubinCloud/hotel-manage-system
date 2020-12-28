package com.yubin.hotelSys.dto;

import lombok.Data;

@Data
public class RoomSearchFormDTO {
    private String roomId;
    private Integer peopleNum;
    private Integer roomTypeId;
    private Integer floor;
    private Integer bedTypeId;
    private String status;
    private Double startPrice;
    private Double endPrice;
}
