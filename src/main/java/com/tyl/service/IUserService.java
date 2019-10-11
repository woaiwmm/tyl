package com.tyl.service;


import com.tyl.common.ServerResponse;
import com.tyl.pojo.User;

/**
 * @author Administrator
 * @date 2019-09-13 1:04
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);
}
