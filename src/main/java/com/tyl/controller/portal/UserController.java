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
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
//        service-->Mybatis-->dao
//        todo 没有搞懂这里的逻辑
        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {//登录成功
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 用户退出
     *
     * @param session
     * @return
     */
    @RequestMapping("loginout.do")
    @ResponseBody
    public ServerResponse<String> loginout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping("register.do")
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return userService.register(user);
    }

    /**
     * 校验用户名和邮箱是否存在
     *
     * @param str
     * @param type
     * @return
     */
    @RequestMapping("check_valid.do")
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return userService.checkValid(str, type);
    }

    /**
     * 获取用户登录信息
     *
     * @param session
     * @return
     */
    @RequestMapping("get_user_info.do")
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    /**
     * 通过账号获取设置的问题
     * @param username
     * @return
     */
    @RequestMapping("forget_get_question.do")
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) {
        return userService.forgetGetQuestion(username);
    }

    /**
     * 比较问题与答案是否匹配
     *
     * @param username
     * @param answer
     * @return
     */
    @RequestMapping("forget_check_answer.do")
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    /**
     * 非登录状态下的重置密码
     * @param username
     * @param passwordNew
     * @return
     */
    @RequestMapping("forget_reset_password.do")
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew,String forgetToken) {
        return userService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    /**
     * 登录状态下的重置密码
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping("reset_password.do")
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return userService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 更新个人信息
     * @param session
     * @param user
     * @return
     */
    @RequestMapping("update_information.do")
    @ResponseBody
    public ServerResponse<User> update_information(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateInformation(currentUser);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
}
