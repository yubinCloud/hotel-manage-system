package com.yubin.hotelSys.dao;

import com.yubin.hotelSys.model.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Mapper
@Repository
public interface RoomMapper {

    // 展示所有房间
    public List<Room> showAllRoom();

    // 查看某一段时间的已预订房间
    public List<String> orderedRooms(@Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);
}
