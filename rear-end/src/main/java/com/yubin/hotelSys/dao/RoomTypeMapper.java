package com.yubin.hotelSys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoomTypeMapper {

    public void modifyStdPrice(@Param("typeId") int typeId, @Param("price") double price);
}
