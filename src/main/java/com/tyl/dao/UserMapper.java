package com.tyl.dao;

import com.tyl.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.Interceptor;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 检查账号是否存在
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 检查账户密码是否正确
     * @param username
     * @param password
     * @return
     */
    User selectLogin(@Param("username") String username, @Param("password")String password);

    /**
     * 检查邮箱是否存在
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 通过账号找到对应的问题
     * @param username
     * @return
     */
    String selectQuestionByUsername(String username);

    /**
     * 检查问题与答案是否匹配
     * @param question
     * @return
     */
    int checkAnswer(@Param("username") String username,@Param("question") String question,@Param("answer") String answer);

    /**
     * 修改密码
     * @param username
     * @param passwordNew
     * @return
     */
   int updatePasswordByUsername(@Param("username")String username,@Param("passwordNew")String passwordNew);

    /**
     * 校验密码是否为该登录用户的
     * @param password
     * @param userId
     * @return
     */
   int checkPassword(@Param("password")String password,@Param("userId")Integer userId);

    /**
     * 校验需要更新的email是否存在
     * @param email
     * @param userId
     * @return
     */
   int checkEmailByUserId(@Param("email")String email, @Param("userId")Integer userId);
}