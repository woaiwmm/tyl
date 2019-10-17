package com.tyl.controller.backend;

import com.tyl.common.Const;
import com.tyl.common.ResponseCode;
import com.tyl.common.ServerResponse;
import com.tyl.pojo.User;
import com.tyl.service.ICategoryService;
import com.tyl.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Response;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 * @date 2019-10-17 19:30
 */
@Component
@RequestMapping("/manage/category/")
public class CategoryManageController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ICategoryService categoryService;

    /**
     * 增加分类
     *
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorcodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {//是管理员
            //增加分类的逻辑
            return categoryService.addCategory(categoryName, parentId);
        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

    /**
     * 修改商品类别的名称
     *
     * @param session
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorcodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {//是管理员
            //修改类别名称的逻辑
            return categoryService.updateCategoryName(categoryId, categoryName);
        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

    /**
     * 获得指定类别下一个节点的全部类别，不向下递归
     *
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorcodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {//是管理员
            //查询子节点的category信息，并且不递归，保持平级
            return categoryService.getChildrenParallelCategory(categoryId);
        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

    /**
     * 递归查询本节点的id及孩子节点的id
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorcodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {//是管理员
            //查询当前节点的id和递归子节点的id
            return categoryService.selectCategoryAndChildrenById(categoryId);

        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

}
