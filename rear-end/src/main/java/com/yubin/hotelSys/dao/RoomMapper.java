package com.yubin.hotelSys.dao;

import com.yubin.hotelSys.dto.NewRoomDTO;
import com.yubin.hotelSys.dto.RoomSearchFormDTO;
import com.yubin.hotelSys.model.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface RoomMapper {

    // 展示所有房间
    public List<Room> showAllRoom();

    // 根据条件选择房间并展示
    public List<Room> selectRoom(RoomSearchFormDTO roomSearchFormDTO);

    // 修改某个房间的房型
    public void modifyTypeOfOneRoom(@Param("roomId") String roomId, @Param("typeId") int typeId);

    // 增加一个房间
    public void addRoom(NewRoomDTO newRoomDTO);
}
