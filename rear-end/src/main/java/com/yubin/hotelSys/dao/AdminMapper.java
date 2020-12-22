package com.yubin.hotelSys.dao;

import com.yubin.hotelSys.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMapper {

    // 根据账号和密码查找一个管理员
    public Admin findAdmin(@Param("account") String account, @Param("pwd") String pwd);

    // 插入一个管理员
    public void insertAdmin(@Param("id") String id, @Param("name") String name,
                            @Param("pwd") String pwd, @Param("roleId") int roleId);
}
