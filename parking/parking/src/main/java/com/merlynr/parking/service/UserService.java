package com.merlynr.parking.service;

import com.merlynr.parking.common.PageRequest;
import com.merlynr.parking.common.PageResult;
import com.merlynr.parking.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: userService
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/2/6
 **/
@Service
public interface UserService {
    /**
     * 根据用户ID查找用户
     * @param userId
     * @return
     */
    Users findByUserId(int userId);

    /**
     * 查找所有用户
     * @return
     */
    List<Users> findAll();

    /**
     * 分页查询接口
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     * @param pageRequest 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    PageResult findPage(PageRequest pageRequest);

    /**
     * 根据用户名查找用户
     *
     * @param userName 用户名
     * @return {@link Users}
     */
    Users findByUserName(String userName);

    /**
     * 添加用户
     *
     * @return {@link Users}
     */
    String addUser(Users user);

    /**
     * 删除用户
     *
     * @param id id
     * @return {@link Integer}
     */
    Integer deleteUser(int id);

    Integer updateUser(Users user);

    List<Users> searchByTelAndPlates(String tel,String plates);

    Users searchByLicense(String license);
}
