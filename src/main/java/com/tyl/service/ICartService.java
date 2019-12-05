package com.tyl.service;

import com.tyl.common.ServerResponse;
import com.tyl.vo.CartVo;

/**
 * @author Administrator
 * @date 2019-12-05 8:25
 */
public interface ICartService {
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
}
