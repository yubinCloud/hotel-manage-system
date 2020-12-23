package com.yubin.hotelSys.controller;

import com.yubin.hotelSys.dao.RoomTypeMapper;
import com.yubin.hotelSys.result.ExceptionMsg;
import com.yubin.hotelSys.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/room_type")
public class RoomTypeController {

    @Autowired
    private RoomTypeMapper roomTypeMapper;

    @RequestMapping(value = "/set_price", method = RequestMethod.POST)
    public Object modifyStdPrice(@RequestBody Map<String, Object> json) {
        int typeId = (Integer) json.get("id");
        double price = (Double) json.get("price");
        roomTypeMapper.modifyStdPrice(typeId, price);
        return new ResponseData(ExceptionMsg.SUCCESS, "success");
    }
}