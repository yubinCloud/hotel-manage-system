package com.yubin.hotelSys.dao;

import com.yubin.hotelSys.model.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Priority;
import java.util.List;


@Mapper
@Repository
public interface RoomMapper {

    // 展示所有房间
    public List<Room> showAllRoom();

    // 修改某个房间的房型
    public void modifyTypeOfOneRoom(@Param("roomId") String roomId, @Param("typeId") int typeId);

    // 增加一个房间
    public void addRoom(@Param("roomId") String roomId, @Param("name") String name,
                        @Param("typeId") int typeId, @Param("floor") int floor,
                        @Param("description") String description);
}
