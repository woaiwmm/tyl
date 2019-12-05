package com.tyl.common;

import com.google.common.collect.Sets;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Set;

/**
 * 常量类
 * @author Administrator
 * @date 2019-10-09 22:37
 */
public class Const {
    public  static final String CURRENT_USER="currentUser";
    public  static final String EMAIL="email";
    public  static final String USERNAME="username";

    public interface ProductListOrderBy{
        //desc降序   asc升序
        Set<String> PRICE_ASC_DESC= Sets.newHashSet("price_desc","price_asc");
    }


    public interface Role{
        int Role_CUSTOMER=0;//普通用户
        int Role_ADMIN=1;//管理员
    }
    public interface Cart{
        int CHECKED=1;//购物车选中状态
        int UN_CHECKED=0;//购物车未选中状态
        String LIMIT_NUM_FAIL="LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS="LIMIT_NUM_SUCCESS";
    }

    public enum ProductStatusEnum{
        ON_SALE(1,"在线");
        private String value;
        private int code;
        ProductStatusEnum(int code,String value){
            this.code=code;
            this.value=value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

}
