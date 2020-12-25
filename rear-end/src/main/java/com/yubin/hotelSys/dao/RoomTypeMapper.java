package com.yubin.hotelSys.dao;

import com.yubin.hotelSys.model.RoomType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomTypeMapper {

    // 修改一个房间的价格
    public void modifyStdPrice(@Param("typeId") int typeId, @Param("price") double price);

    // 查看一个房间的全部类型
    public List<RoomType> queryAllRoomType();
}
