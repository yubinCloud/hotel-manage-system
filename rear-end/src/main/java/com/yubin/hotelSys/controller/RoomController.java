package com.yubin.hotelSys.controller;

import com.yubin.hotelSys.dao.AdminMapper;
import com.yubin.hotelSys.dao.RoomMapper;
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
@RequestMapping("/room")
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
}
