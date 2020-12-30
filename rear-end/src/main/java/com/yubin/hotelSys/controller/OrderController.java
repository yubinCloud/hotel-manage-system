package com.yubin.hotelSys.controller;


import com.yubin.hotelSys.dao.OrderMapper;
import com.yubin.hotelSys.dto.NewCheckinDTO;
import com.yubin.hotelSys.dto.OrderSearchFormDTO;
import com.yubin.hotelSys.model.MonthTurnover;
import com.yubin.hotelSys.result.ExceptionMsg;
import com.yubin.hotelSys.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询某一个时间段所预定的房间
     */
    @RequestMapping(value = "/selectOrder")
    public Object selectOrders(@NotNull OrderSearchFormDTO orderSearchFormDTO) {
        var startTime = timeStrToDateTime(orderSearchFormDTO.getStartTime());
        var endTime = timeStrToDateTime(orderSearchFormDTO.getEndTime());
        String roomId = orderSearchFormDTO.getRoomId();
        if (roomId != null) {
            roomId = '%' + roomId + '%';
        }
        var selectResult = orderMapper.selectOrder(roomId, startTime, endTime);
        return new ResponseData(ExceptionMsg.SUCCESS, selectResult);
    }

    @RequestMapping(value = "/turnover")
    public Object aggregateTurnover(@RequestParam(value = "startTime") String startTimeStr,
                                    @RequestParam(value = "endTime") String endTimeStr) {
        LocalDateTime startTime, endTime;
        try {
            startTime = timeStrToDateTime(startTimeStr);
            endTime = timeStrToDateTime(endTimeStr);
        } catch (NumberFormatException e) {
            return new ResponseData(ExceptionMsg.FAILED, "datetime str format is error.");
        }
        List<MonthTurnover> monthTurnoverList = orderMapper.aggregateMonthTurnover(startTime, endTime);
        monthTurnoverList.sort(Comparator.comparing(MonthTurnover::getMonth));
        double money = monthTurnoverList.stream().mapToDouble(MonthTurnover::getMoney).sum();
        int orderCount = monthTurnoverList.stream().mapToInt(MonthTurnover::getOrderCount).sum();
        Map<String, Object> result = new HashMap<>();
        result.put("money", money);
        result.put("orderCount", orderCount);
        result.put("statList", monthTurnoverList);

        return new ResponseData(ExceptionMsg.SUCCESS, result);
    }

    /**
     * 增加新的 checkin 订单
     * @param newCheckinDTO 新订单的接收信息
     */
    @RequestMapping(value = "/addCheckin", method = RequestMethod.POST)
    public Object addCheckinOrder(@RequestBody NewCheckinDTO newCheckinDTO) {
        if (newCheckinDTO.getDiscountRatio() == null) {
            newCheckinDTO.setDiscountRatio(1.0);
        }
        orderMapper.addCheckin(newCheckinDTO);
        return new ResponseData(ExceptionMsg.SUCCESS, "success");
    }

    /**
     * 查询某一天内预定的房间
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
        var result = orderMapper.selectOrderById(orderId);
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

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public Object checkout(@RequestBody Map<String, Object> json) {
        long orderId = Long.parseLong((String) json.get("orderId"));
        orderMapper.checkout(orderId);
        return new ResponseData(ExceptionMsg.SUCCESS, "success");
    }

    /**
     * 将前端传来的时间字符串转换成 LocalDateTime 类型
     * @param timeStr 前端传来的时间字符串
     * @return 转换后的 LocalDateTime 对象
     */
    private LocalDateTime timeStrToDateTime(String timeStr) {
        if (timeStr == null)
            return null;
        String[] sec = timeStr.split("-");
        int year = Integer.parseInt(sec[0]);
        int month = Integer.parseInt(sec[1]);
        int day = Integer.parseInt(sec[2]);
        return LocalDateTime.of(year, month, day, 12, 0);
    }

}
