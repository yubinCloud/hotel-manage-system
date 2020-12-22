package com.yubin.hotelSys.controller;

import com.yubin.hotelSys.dao.RoomMapper;
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
        return roomMapper.showAllRoom();
    }
}
