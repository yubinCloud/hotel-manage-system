package com.yubin.hotelSys.controller;

import com.yubin.hotelSys.dao.RoomMapper;
import com.yubin.hotelSys.result.ExceptionMsg;
import com.yubin.hotelSys.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomMapper roomMapper;

    @RequestMapping(value = "/show_all")
    public Object showAllRoom() {
        return new ResponseData(ExceptionMsg.SUCCESS, roomMapper.showAllRoom());
    }
}
