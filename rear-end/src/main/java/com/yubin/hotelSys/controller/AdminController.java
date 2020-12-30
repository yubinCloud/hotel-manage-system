package com.yubin.hotelSys.controller;

import com.yubin.hotelSys.dao.AdminMapper;
import com.yubin.hotelSys.dto.AdminSearchFormDTO;
import com.yubin.hotelSys.model.Admin;
import com.yubin.hotelSys.result.ExceptionMsg;
import com.yubin.hotelSys.result.ResponseData;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    /**
     * 使用 md5 算法对密码进行哈希
     * @param pwd 原始密码
     * @return 哈希后的密码
     */
    private String md5Hash(String pwd) {
        try {
            pwd = Arrays.toString(MessageDigest.getInstance("md5").digest(pwd.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return pwd;
    }

    @Autowired
    private AdminMapper adminMapper;

    static private final String[] dbTableNames = {
            "Admin", "AdminType", "BedType", "Guest", "Order", "Room", "RoomType", "User"
    };


    @RequestMapping(value = "selectAdmin")
    public Object selectAdmin(AdminSearchFormDTO adminForm) {
        List<Admin> selectRet = adminMapper.selectAdmin(adminForm);
        return new ResponseData(ExceptionMsg.SUCCESS, selectRet);
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public Object signup(@RequestBody Admin admin) {
        admin.setPwd( md5Hash( admin.getPwd() ) );
        adminMapper.insertAdmin(admin);
        return new ResponseData(ExceptionMsg.SUCCESS, "success");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request, @RequestBody Map<String, Object> json) {
        String account = (String) json.get("account");
        String pwd = (String) json.get("pwd");
        pwd = md5Hash(pwd);
        var findRet = adminMapper.findAdmin(account, pwd);
        if (findRet == null)
            return new ResponseData(ExceptionMsg.SUCCESS, "login fail");
        var session = request.getSession();
        session.setAttribute("account", account);
        return new ResponseData(ExceptionMsg.SUCCESS, "success");
    }

    @RequestMapping(value = "/adminInfo", method = RequestMethod.GET)
    public Object getAdmin(HttpServletRequest request) {
        var session = request.getSession();

        String account = null;
        try {
            account = (String) session.getAttribute("account");
        } catch (IllegalStateException e) {
            return new ResponseData(ExceptionMsg.FAILED, "Session状态错误");
        }

        if (account == null) {
            return new ResponseData(ExceptionMsg.FAILED, "Session中找不到account信息");
        }

        var admin = adminMapper.findAdminByAccount(account);
        return new ResponseData(ExceptionMsg.SUCCESS, admin);
    }

    @NotNull
    private Object buildCmdAndRun(String backupCmdFormat) {
        for (String dbTableName : dbTableNames) {
            String backupCmd = String.format(backupCmdFormat, dbTableName, dbTableName);
            try {
                var process = Runtime.getRuntime().exec(backupCmd);
            } catch (IOException e) {
                return new ResponseData(ExceptionMsg.FAILED, "failed");
            }
        }
        return new ResponseData(ExceptionMsg.SUCCESS, "success");
    }

    /**
     * 对数据库进行备份
     */
    @RequestMapping(value = "/backup", method = RequestMethod.POST)
    public Object dbBackup() {
        String backupCmdFormat = "mysqldump -u root -h 127.0.0.1 -p yubin3869 -P 3306 hotel %s > d:\\backup\\%s.sql";
        return buildCmdAndRun(backupCmdFormat);
    }

    @RequestMapping(value = "/recover", method = RequestMethod.POST)
    public Object dbRecover() {
        String recoverCmdFormat = "mysql -u root -p 123456 -h 127.0.0.1 -P 3306 hotel < d:\\backup\\%s.sql";
        return buildCmdAndRun(recoverCmdFormat);
    }

}
