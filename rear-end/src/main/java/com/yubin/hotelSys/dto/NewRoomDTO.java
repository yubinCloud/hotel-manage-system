package com.yubin.hotelSys.dto;

import lombok.Data;

/**
 * 用来接收新增房间的 DTO
 */
@Data
public class NewRoomDTO {
    private String roomId;
    private String roomName;
    private String status;
    private int floor;
    private String desc;
    private int bedTypeId;
    private int roomTypeId;
}
