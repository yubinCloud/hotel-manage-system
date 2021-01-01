package com.yubin.hotelSys.controller;

import com.yubin.hotelSys.dao.GuestMapper;
import com.yubin.hotelSys.result.ExceptionMsg;
import com.yubin.hotelSys.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/guest")
public class GuestController {
    @Autowired
    private GuestMapper guestMapper;

    @RequestMapping(value = "/query/id", method = RequestMethod.POST)
    public Object queryById(@RequestBody Map<String, Object> json) {
        String guestId = (String) json.get("gid");
        return new ResponseData(ExceptionMsg.SUCCESS, guestMapper.queryGuestById(guestId));
    }

    @RequestMapping(value = "/query/name", method = RequestMethod.POST)
    public Object queryByName(@RequestBody Map<String, Object> json) {
        String name = (String) json.get("name");
        String nameRegexp = ".*" + name + ".*";
        return new ResponseData(ExceptionMsg.SUCCESS, guestMapper.queryGuestByName(nameRegexp));
    }

    @RequestMapping(value = "/query/phone", method = RequestMethod.POST)
    public Object queryByPhone(@RequestBody Map<String, Object> json) {
        String phone = (String) json.get("phone");
        String phoneRegexp = ".*" + phone;
        return new ResponseData(ExceptionMsg.SUCCESS, guestMapper.queryGuestByPhone(phoneRegexp));
    }
}
