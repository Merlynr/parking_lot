package com.merlynr.parking.dao;

import com.merlynr.parking.model.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    List<Users> selectAll();

    Users findByUsername(String userName);

    List<Users> selectByTelAndPlates(String tel,String licensePlates);

    Users searchByLicense(String licensePlates);
}