package com.yubin.hotelSys.dao;

import com.yubin.hotelSys.model.Room;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface RoomMapper {

    // 展示所有房间
    public List<Room> showAllRoom();

}
