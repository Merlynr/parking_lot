package com.merlynr.parking.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.merlynr.parking.model.Users;
import com.merlynr.parking.service.TokenService;
import org.springframework.stereotype.Component;

@Component
public class TokenServiceImpl implements TokenService {

    /**
     * 获得令牌
     *
     * @param user 用户
     * @return {@link String}
     */
    @Override
    public String getToken(Users user){
        String token = "";
        // 将 user id 保存到 token 里面
        // 以 password 作为 token 的密钥
        token = JWT.create().withAudience(user.getId().toString()).sign(Algorithm.HMAC256(user.getPwd()));
        return token;
    }
}
