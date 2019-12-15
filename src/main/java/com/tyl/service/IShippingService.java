package com.tyl.service;

import com.github.pagehelper.PageInfo;
import com.tyl.common.ServerResponse;
import com.tyl.pojo.Shipping;

/**
 * @author Administrator
 * @date 2019-12-14 15:07
 */
public interface IShippingService {
    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse del(Integer userId, Integer shippingId);
    ServerResponse update(Integer userId, Shipping shipping);
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
