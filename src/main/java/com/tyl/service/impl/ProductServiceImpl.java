package com.tyl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tyl.common.Const;
import com.tyl.common.ResponseCode;
import com.tyl.common.ServerResponse;
import com.tyl.dao.CategoryMapper;
import com.tyl.dao.ProductMapper;
import com.tyl.pojo.Category;
import com.tyl.pojo.Product;
import com.tyl.service.ICategoryService;
import com.tyl.service.IProductService;
import com.tyl.util.DateTimeUtil;
import com.tyl.util.PropertiesUtil;
import com.tyl.vo.ProductDetailVo;
import com.tyl.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @date 2019-10-26 15:16
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    //平级调用
    @Autowired
    private ICategoryService categoryService;

    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null) {
            if (StringUtils.isNoneBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }

            }
            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {

                    return ServerResponse.createBySuccess("更新产品成功");
                } else {
                    return ServerResponse.createBySuccess("更新产品失败");
                }
            } else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccess("新增产品成功");
                } else {
                    return ServerResponse.createBySuccess("新增产品失败");
                }
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新商品参数不正确");
    }

    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorcodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("修改产品销售状态成功");
        }
        return ServerResponse.createBySuccessMessage("修改产品销售状态失败");
    }

    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        //VO对象
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);

        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubTitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImage(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            productDetailVo.setParenCategoryId(0);//默认根节点
        } else {
            productDetailVo.setParenCategoryId(category.getParentId());
        }
        //配置文件与代码分离
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix)", "http://img.happymmall.com/"));

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    //    todo 这个是PageHelper的使用，没搞懂
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        //startPage--start
        PageHelper.startPage(pageNum, pageSize);
        //填充自己的sql查询逻辑
        List<Product> productList = productMapper.selectList();
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = assembleProductLisVo(product);
            productListVoList.add(productListVo);
        }
        //pageHelper收尾
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }


    private ProductListVo assembleProductLisVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return productListVo;
    }


    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = assembleProductLisVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        //VO对象
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);

        return ServerResponse.createBySuccess(productDetailVo);
    }

    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.createByErrorcodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                //没有该分类，并且没有该关键字，这时候返回一个空结果集，不报错
                PageHelper.startPage(pageNum, pageSize);
                List<ProductDetailVo> productDetailVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productDetailVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }

            categoryIdList = categoryService.selectCategoryAndChildrenById(category.getId()).getData();
        }
        if (StringUtils.isNotBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        PageHelper.startPage(pageNum, pageSize);
        //排序处理
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }

        }
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword, categoryIdList.size() == 0 ? null : categoryIdList);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = assembleProductLisVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productListVoList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

}
