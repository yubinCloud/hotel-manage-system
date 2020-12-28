package com.yubin.hotelSys.controller;

import com.yubin.hotelSys.dao.AdminMapper;
import com.yubin.hotelSys.dao.RoomMapper;
import com.yubin.hotelSys.dto.RoomSearchForm;
import com.yubin.hotelSys.model.Admin;
import com.yubin.hotelSys.model.Room;
import com.yubin.hotelSys.model.RoomType;
import com.yubin.hotelSys.result.ExceptionMsg;
import com.yubin.hotelSys.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 展示所有房间的信息
     * @return 响应数据
     */
    @RequestMapping(value = "/show_all")
    public Object showAllRoom() {
        var queryRet = roomMapper.showAllRoom();
        return new ResponseData(ExceptionMsg.SUCCESS, queryRet);
    }

    @RequestMapping(value = "/selectRoom")
    public Object selectRoom(RoomSearchForm roomSearchForm) {
        System.out.println(roomSearchForm);
        List<Room> selectResult = roomMapper.selectRoom(roomSearchForm);
        return new ResponseData(ExceptionMsg.SUCCESS, selectResult);
    }

    @RequestMapping(value = "/set_type", method = RequestMethod.POST)
    public Object modifyRoomType(HttpServletRequest request, @RequestBody Map<String, Object> json) {
        var session = request.getSession();
        String revAccount = (String) session.getAttribute("account");
        Admin admin = adminMapper.findAdminByAccount(revAccount);
        if (admin == null) {
            return new ResponseData(ExceptionMsg.FAILED, "被拒绝");
        }
        int typeId = (Integer) json.get("typeId");
        String roomId = (String) json.get("roomId");
        roomMapper.modifyTypeOfOneRoom(roomId, typeId);
        return new ResponseData(ExceptionMsg.SUCCESS, "success");
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addRoom(@RequestBody Map<String, Object> json) {
        String roomId = (String) json.get("roomId");
        String roomName = (String) json.getOrDefault("name", "");
        int typeId = (Integer) json.getOrDefault("typeId", 1);
        int floor = (Integer) json.get("floor");
        String desc = (String) json.getOrDefault("desc", "");
        roomMapper.addRoom(roomId, roomName, typeId, floor, desc);

        return new ResponseData(ExceptionMsg.SUCCESS, "success");
    }
}
