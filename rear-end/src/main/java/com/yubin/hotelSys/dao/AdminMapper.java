package com.yubin.hotelSys.dao;

import com.yubin.hotelSys.dto.AdminSearchFormDTO;
import com.yubin.hotelSys.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    // 根据条件查询管理员
    public List<Admin> selectAdmin(AdminSearchFormDTO adminForm);

    // 根据账号和密码查找一个管理员
    public Admin findAdmin(@Param("account") String account, @Param("pwd") String pwd);

    // 插入一个管理员
    public void insertAdmin(Admin admin);

    // 根据账号来查找一个管理员
    public Admin findAdminByAccount(@Param("account") String account);
}
