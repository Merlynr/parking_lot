package com.merlynr.parking.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.merlynr.parking.dao.UsersDao;
import com.merlynr.parking.model.Users;
import com.merlynr.parking.service.UserService;
import com.merlynr.parking.common.PageRequest;
import com.merlynr.parking.common.PageResult;
import com.merlynr.parking.common.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersDao usersDao;

    /**
     * 根据用户ID查找用户
     *
     * @param userId
     * @return
     */
    @Override
    public Users findByUserId(int userId) {
        return usersDao.selectByPrimaryKey(userId);
    }

    /**
     * 查找所有用户
     *
     * @return
     */
    @Override
    public List<Users> findAll() {
        return usersDao.selectAll();
    }

    /**
     * 分页查询接口
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     *
     * @param pageRequest 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest,getPageInfo(pageRequest));
    }

    /**
     * 获取页面信息
     * 调用分页插件完成分页
     *
     * @param pageRequest 页面请求
     * @return {@link PageInfo<Users>}
     */
    private PageInfo<Users> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Users> sysMenus = usersDao.selectAll();
        return new PageInfo<Users>(sysMenus);
    }

    @Override
    public Users findByUserName(String userName) {
        return usersDao.findByUsername(userName);
    }


    /**
     * 添加用户
     *
     * @return {@link Users}
     */
    @Override
    public String addUser(Users user) {
        if(usersDao.findByUsername(user.getUserName()) ==null){
            usersDao.insert(user);
            return "新增成功";
        }else {
            return "已存在";
        }
    }

    /**
     * 删除用户
     *
     * @param id id
     * @return {@link Integer}
     */
    @Override
    public Integer deleteUser(int id) {
        return usersDao.deleteByPrimaryKey(id);
    }

    @Override
    public Integer updateUser(Users user) {
        return usersDao.updateByPrimaryKeySelective(user);
    }



}
