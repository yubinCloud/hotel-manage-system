package com.yubin.hotelSys.dao;

import com.yubin.hotelSys.model.Guest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GuestMapper {

    // 身份证号精确查询顾客
    public Guest queryGuestById(@Param("gid") String gid);

    // 根据姓名粗略查找用户
    public List<Guest> queryGuestByName(@Param("nameRegexp") String nameRegexp);

    // 根据手机号的最后几位去粗略查找用户
    public List<Guest> queryGuestByPhone(@Param("phoneRegexp") String phoneRegexp);
}
