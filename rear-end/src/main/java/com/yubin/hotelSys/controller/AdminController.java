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
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
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

}
