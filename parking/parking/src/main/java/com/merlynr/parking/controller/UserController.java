package com.merlynr.parking.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.merlynr.parking.common.*;
import com.merlynr.parking.dao.UsersDao;
import com.merlynr.parking.model.Users;
import com.merlynr.parking.service.TokenService;
import com.merlynr.parking.service.UserService;
import com.merlynr.parking.common.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 *
 * @author merlynr
 * @date 2021/03/13
 */
@BaseResponse
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsersDao userDao;

    /**
     * resigter
     *
     * @param user 用户
     * @return {@link Users}
     */
    @PostMapping("register")
    public Object register(@RequestBody Users user){
        JSONObject jsonObject=new JSONObject();
        if (user == null){
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else{
            jsonObject.put("msg",userService.addUser(user));
            return jsonObject;
        }
    }

    /**
     * 登录
     *
     * @param params 参数个数
     * @return {@link Object}
     */
    @PostMapping("login")
    public Object login(@RequestBody Map<String,String> params){
        String userName = params.get("userName");
        String pwd = params.get("pwd");
        JSONObject jsonObject=new JSONObject();
        if (userName == null || pwd==null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
            Users userForBase = userService.findByUserName(userName);
            if (userForBase == null) {
                throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
            } else {
                if (!userForBase.getPwd().equals(pwd)) {
                    throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
                } else {
                    String token = tokenService.getToken(userForBase);
                    jsonObject.put("token", token);
                    userForBase.setPwd("*******");
                    jsonObject.put("user", userForBase);
                    return jsonObject;
                }
            }
        }
    }

    /**
     * 删除用户
     *
     * @param params 参数个数
     * @return {@link Integer}
     */
    @UserLoginToken
    @PostMapping("deleteUser")
    public Integer deleteUser(@RequestBody Map<String,Integer> params){
        int id = params.get("id");
        if (id == 0){
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else{
            return userService.deleteUser(id);
        }
    }

    @UserLoginToken
    @PostMapping("updateUserInfo")
    public Integer update(@RequestBody Users user){
        if (user == null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
             return userService.updateUser(user);
        }
    }

    @UserLoginToken
    @GetMapping("list")
    public List<Users> list(){
        return userService.findAll();
    }

    /**
     * 分页查询用户
     *
     * @param pageRequest 页面请求
     * @return {@link Object}
     */
    @UserLoginToken
    @PostMapping("findUserByPage")
    public Object findPage(@RequestBody PageRequest pageRequest){
        if (pageRequest == null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
            return userService.findPage(pageRequest);
        }
    }
    @UserLoginToken
    @GetMapping("findUserById")
    public Users findById(@RequestParam("id") Integer id){
        if (id == null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
            return userService.findByUserId(id);
        }
    }
    @UserLoginToken
    @GetMapping("findByTelAndPlates")
    public List<Users> findUserByTelAndPlates(@RequestParam("tel") String tel,@RequestParam("license_plates") String license_plates){
        if (tel == null || license_plates==null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
            if ((tel.isEmpty()||tel.trim().length()== 0)&& (license_plates.isEmpty() || license_plates.trim().length()== 0)){
                return userService.findAll();
            }else {
                return userService.searchByTelAndPlates(tel,license_plates);
            }
        }
    }
    @UserLoginToken
    @GetMapping("findByLicense")
    public Users findByLicense(@RequestParam("license_plates") String license_plates){
        if (license_plates==null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
           return userService.searchByLicense(license_plates);
        }
    }

}
