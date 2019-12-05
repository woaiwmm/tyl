package com.tyl.dao;

import com.tyl.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
    Cart selectByUserIdProductId(@Param("userId") Integer userId,@Param("userId") Integer productId);
    List<Cart> selectCartByUserId(Integer userId);
    int selectCartProductCheckedStatusByUserId(Integer userId);
}