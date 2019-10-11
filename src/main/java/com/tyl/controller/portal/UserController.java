package com.tyl.controller.portal;

import com.tyl.common.Const;
import com.tyl.common.ServerResponse;
import com.tyl.pojo.User;
import com.tyl.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author Jun
 * @date 2019-09-12 14:48
 */
@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService userService;
    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
//        service-->Mybatis-->dao
//        todo 没有搞懂这里的逻辑
        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()){//登录成功
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
}
