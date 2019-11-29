package com.tyl.service;

import com.github.pagehelper.PageInfo;
import com.tyl.common.ServerResponse;
import com.tyl.pojo.Product;
import com.tyl.vo.ProductDetailVo;

/**
 * @author Administrator
 * @date 2019-10-26 15:14
 */
public interface IProductService {
     ServerResponse saveOrUpdateProduct(Product product) ;
     ServerResponse<String> setSaleStatus(Integer productId,Integer status);
     ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
      ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);
    ServerResponse<PageInfo> searchProduct( String productName,Integer productId,int pageNum,int pageSize);
}
