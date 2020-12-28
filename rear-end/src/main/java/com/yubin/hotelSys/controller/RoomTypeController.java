package com.yubin.hotelSys.controller;

import com.yubin.hotelSys.dao.RoomTypeMapper;
import com.yubin.hotelSys.dto.RoomTypeSearchFormDTO;
import com.yubin.hotelSys.result.ExceptionMsg;
import com.yubin.hotelSys.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/roomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeMapper roomTypeMapper;

    @RequestMapping(value = "/allType", method = RequestMethod.GET)
    public Object queryAllRoomType() {
        return new ResponseData(ExceptionMsg.SUCCESS, roomTypeMapper.queryAllRoomType());
    }

    @RequestMapping(value = "/selectRoomType")
    public Object selectRoomType(RoomTypeSearchFormDTO roomTypeSearchFormDTO) {
        roomTypeSearchFormDTO.setRoomTypeName(
                '%' + roomTypeSearchFormDTO.getRoomTypeName() + '%'
        );
        var selectResult = roomTypeMapper.selectRoomType(roomTypeSearchFormDTO);
        return new ResponseData(ExceptionMsg.SUCCESS, selectResult);
    }

    @RequestMapping(value = "/setPrice", method = RequestMethod.POST)
    public Object modifyStdPrice(@RequestBody Map<String, Object> json) {
        int typeId = (Integer) json.get("id");
        double price = (Double) json.get("price");
        roomTypeMapper.modifyStdPrice(typeId, price);
        return new ResponseData(ExceptionMsg.SUCCESS, "success");
    }
}
