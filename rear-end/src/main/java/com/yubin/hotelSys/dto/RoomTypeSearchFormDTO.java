package com.yubin.hotelSys.dto;

import lombok.Data;

@Data
public class RoomTypeSearchFormDTO {
    private String roomTypeName;
    private Integer peopleNum;
    private Double minPrice;
    private Double maxPrice;
    private Double minArea;
    private Double maxArea;
}
