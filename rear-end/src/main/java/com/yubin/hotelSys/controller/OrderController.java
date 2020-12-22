package com.yubin.hotelSys.controller;


import com.yubin.hotelSys.dao.OrderMapper;
import com.yubin.hotelSys.model.Order;
import com.yubin.hotelSys.result.ExceptionMsg;
import com.yubin.hotelSys.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询某一段时间预定的房间
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 响应数据
     */
    @RequestMapping(value = "/day_reserve", method = RequestMethod.GET)
    public Object orderedRoomsOfDay(@RequestParam("year") int year,
                                    @RequestParam("month") int month,
                                    @RequestParam("day") int day) {
        LocalDateTime startTime = LocalDateTime.of(year, month, day, 12, 0);
        LocalDateTime endTime = startTime.plusDays(1);
        return new ResponseData(ExceptionMsg.SUCCESS, orderMapper.orderedRooms(startTime, endTime));
    }

    /**
     * 根据订单id来获取一个订单的详细信息
     * @param orderId 订单的id
     * @return 响应数据
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public Object selectOrder(@RequestParam("oid") long orderId) {
        var result = orderMapper.selectOrder(orderId);
        return new ResponseData(ExceptionMsg.SUCCESS, result);
    }

    /**
     * 获取某一天的利润
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 响应数据
     */
    @RequestMapping(value = "/profit/day", method = RequestMethod.GET)
    public Object computeProfitsOfDay(@RequestParam("year") int year,
                                      @RequestParam("month") int month,
                                      @RequestParam("day") int day) {
        LocalDateTime startTime = LocalDateTime.of(year, month, day, 12, 0);
        LocalDateTime endTime = startTime.plusDays(1);
        return new ResponseData(ExceptionMsg.SUCCESS, orderMapper.computeProfits(startTime, endTime));
    }

    /**
     * 获取某一个月的利润
     * @param year 年
     * @param month 月
     * @return 响应数据
     */
    @RequestMapping(value = "profit/month", method = RequestMethod.GET)
    public Object computeProfitsOfMonth(@RequestParam("year") int year,
                                        @RequestParam("month") int month) {
        LocalDateTime startTime = LocalDateTime.of(year, month, 1, 12, 0);
        LocalDateTime endTime = startTime.plusMonths(1);
        return new ResponseData(ExceptionMsg.SUCCESS, orderMapper.computeProfits(startTime, endTime));
    }

    @RequestMapping(value = "profit/year", method = RequestMethod.GET)
    public Object computeProfitsOfYear(@RequestParam("year") int year) {
        LocalDateTime startTime = LocalDateTime.of(year, 1, 1, 12, 0);
        LocalDateTime endTime = startTime.plusYears(1);
        return new ResponseData(ExceptionMsg.SUCCESS, orderMapper.computeProfits(startTime, endTime));
    }

}
