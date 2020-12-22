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

}
