package com.tyl.controller.portal;

import com.github.pagehelper.PageInfo;
import com.tyl.common.ServerResponse;
import com.tyl.service.IProductService;
import com.tyl.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**前台的商品功能
 * @author Administrator
 * @date 2019-12-01 9:58
 */



@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private IProductService productService;

    /**
     * 获得商品详情
     * @param productId
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
       return productService.getProductDetail(productId);
    }


    /**
     * 获得商品列表
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keyword,@RequestParam(value = "categoryId",required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue ="" )String orderBy){
      return productService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }
}
