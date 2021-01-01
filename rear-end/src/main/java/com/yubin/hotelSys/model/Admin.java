package com.yubin.hotelSys.model;

import lombok.Data;

/**
 * 管理员类
 */
@Data
public class Admin {
    private String id;
    private String name;
    private String pwd;
    private int roleId;
    private String telPhone;

    private AdminRole role;
}
