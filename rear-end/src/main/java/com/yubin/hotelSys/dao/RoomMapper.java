package com.yubin.hotelSys.dao;

import com.yubin.hotelSys.model.Room;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface RoomMapper {

    public List<Room> showAllRoom();
}
