package com.yubin.hotelSys.dao;

import com.yubin.hotelSys.model.BedType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BedTypeMapper {

    // 查询全部床的类型
    public List<BedType> queryAllBedType();
}
