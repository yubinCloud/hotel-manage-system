package com.yubin.hotelSys.dao;

import com.yubin.hotelSys.dto.NewCheckinDTO;
import com.yubin.hotelSys.model.MonthTurnover;
import com.yubin.hotelSys.model.Order;
import com.yubin.hotelSys.model.SimplifyOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface OrderMapper {

    // 根据条件来选择查询订单
    public List<Order> selectOrder(@Param("roomId") String roomId,
                             @Param("startTime") LocalDateTime startTime,
                             @Param("endTime") LocalDateTime endTime);

    // 统计交易金额
    public List<MonthTurnover> aggregateMonthTurnover(@Param("startTime") LocalDateTime startTime,
                                                      @Param("endTime") LocalDateTime endTime);

    // 根据订单的id来获得订单的详细信息
    public Order selectOrderById(@Param("orderId") long orderId);

    // 查看某一段时间的已预订房间
    public List<SimplifyOrder> orderedRooms(@Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime);

    // 计算某一段时间的总营收
    public double computeProfits(@Param("startTime") LocalDateTime startTime,
                               @Param("endTime") LocalDateTime endTime);

    // 登记离开
    public void checkout(@Param("orderId") long orderId);

    // 登记入住
    public void addCheckin(NewCheckinDTO newCheckinDTO);
}
