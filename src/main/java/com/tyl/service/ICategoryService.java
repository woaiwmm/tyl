package com.tyl.service;

import com.tyl.common.ServerResponse;
import com.tyl.pojo.Category;

import java.util.List;

/**
 * @author Administrator
 * @date 2019-10-17 20:01
 */
public interface ICategoryService {
     ServerResponse addCategory(String categoryName, Integer parenId);
    ServerResponse updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
