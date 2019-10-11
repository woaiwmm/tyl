package com.tyl.service;


import com.tyl.common.ServerResponse;
import com.tyl.pojo.User;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 * @date 2019-09-13 1:04
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str,String type);
     ServerResponse<String> forgetGetQuestion(String username);
     ServerResponse<String> checkAnswer(String username,String question,String answer);
}

