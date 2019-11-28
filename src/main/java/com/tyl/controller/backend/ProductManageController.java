package com.tyl.controller.backend;

import com.tyl.common.Const;
import com.tyl.common.ResponseCode;
import com.tyl.common.ServerResponse;
import com.tyl.pojo.Product;
import com.tyl.pojo.User;
import com.tyl.service.IProductService;
import com.tyl.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 * @date 2019-10-26 14:57
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IProductService productService;

    /**
     * 增加商品
     * @param session
     * @param product
     * @return
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorcodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
           return productService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


    /**
     *商品上下架
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session,Integer productId,Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorcodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            return productService.setSaleStatus(productId,status);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


    /**
     * 获取商品详情
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session,Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorcodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            return productService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


}
