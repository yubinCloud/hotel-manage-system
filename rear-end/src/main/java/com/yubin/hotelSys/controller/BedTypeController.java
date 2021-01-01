package com.yubin.hotelSys.controller;


import com.yubin.hotelSys.dao.BedTypeMapper;
import com.yubin.hotelSys.result.ExceptionMsg;
import com.yubin.hotelSys.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bedType")
public class BedTypeController {

    @Autowired
    private BedTypeMapper bedTypeMapper;

    @RequestMapping(value = "allType", method = RequestMethod.GET)
    public Object queryAllBedType() {
        return new ResponseData(ExceptionMsg.SUCCESS, bedTypeMapper.queryAllBedType());
    }
}
