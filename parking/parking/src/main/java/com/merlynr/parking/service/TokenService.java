package com.merlynr.parking.service;

import com.merlynr.parking.model.Users;
import org.springframework.stereotype.Service;


@Service
public interface TokenService {

    String getToken(Users user);
}
