package org.apache.ibatis.example.pojo;


import lombok.Data;

import java.util.Date;

/**
 * Staff 和 user 类
 * 目前映射同一个表
 * @author xq2
 */
@Data
public class User {

    public static final int FUNCTION_ADVISOR = 1 << 0;
    public static final int FUNCTION_TRAINER = 1 << 1;
    public static final int FUNCTION_GROUP = 1 << 2;
    public static final int FUNCTION_OTHER = 1 << 3;

    private Long id;

    /**
     * 性别：0：男，1：女
     */

    private Integer sex;

    private String mobile;

    private String name;

    private String nickName;

    private Date birthday;

    private String avatar;

    private Integer creditType;

    private String creditNo;

    private String wechatNo;

    /**
     * 1：会籍人员,2：私教人员，4：团操教练，8：其他工作人员，16：店长，多个身份通过位或运算确定值
     */
    private Integer functionType;

    /**
     * 权限ID,1 saas使用权限，2 场馆端使用权限 多个身份通过位或运算确定值
     */
    private Integer authority;

    private Long clubId;

    private String brandName;

    private String workNo;

    private String creditFront;

    private String creditNegative;

    private Boolean status;


    private Boolean isAdmin;

    private Integer bindStatus;

}