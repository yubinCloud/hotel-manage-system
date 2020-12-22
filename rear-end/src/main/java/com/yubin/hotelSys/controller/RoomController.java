package com.yubin.hotelSys.controller;

import com.yubin.hotelSys.dao.RoomMapper;
import com.yubin.hotelSys.result.ExceptionMsg;
import com.yubin.hotelSys.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomMapper roomMapper;

    @RequestMapping(value = "/show_all")
    public Object showAllRoom() {
        return new ResponseData(ExceptionMsg.SUCCESS, roomMapper.showAllRoom());
    }

    @RequestMapping(value = "/day_reserve", method = RequestMethod.GET)
    public Object orderedRoomsOfDay(@RequestParam("year") int year,
                                    @RequestParam("month") int month,
                                    @RequestParam("day") int day) {
        LocalDateTime startTime = LocalDateTime.of(year, month, day, 12, 0);
        LocalDateTime endTime = startTime.plusDays(1);
        return new ResponseData(ExceptionMsg.SUCCESS, roomMapper.orderedRooms(startTime, endTime));
    }
}
