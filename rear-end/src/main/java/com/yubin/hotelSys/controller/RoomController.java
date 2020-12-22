package com.yubin.hotelSys.controller;

import com.yubin.hotelSys.dao.RoomMapper;
import com.yubin.hotelSys.model.Room;
import com.yubin.hotelSys.model.RoomType;
import com.yubin.hotelSys.result.ExceptionMsg;
import com.yubin.hotelSys.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomMapper roomMapper;

    @RequestMapping(value = "/show_all")
    public Object showAllRoom() {
        var queryRet = roomMapper.showAllRoom();
        List<Map<String, Object>> result = new LinkedList<>();
        for (Room room: queryRet) {
            Map<String, Object> roomDict = new HashMap<>();
            roomDict.put("id", room.getId());
            roomDict.put("name", room.getName());
            roomDict.put("floor", room.getFloor());
            roomDict.put("desc", room.getDesc());
            RoomType roomType = room.getRoomType();
            roomDict.put("typeName", roomType.getName());
            roomDict.put("stdPrice", roomType.getStdPrice());
            result.add(roomDict);
        }
        return new ResponseData(ExceptionMsg.SUCCESS, result);
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
