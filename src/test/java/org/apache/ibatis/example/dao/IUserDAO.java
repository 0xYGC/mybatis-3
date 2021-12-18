package org.apache.ibatis.example.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.example.pojo.User;

/**
 * Description:
 *
 * @Author: Yanggc
 * DateTime: 12-18 20:46
 */

public interface IUserDAO {


    @Select("SELECT * FROM saas_user WHERE id = #{1}")
    public User selectById(Long id);

    @Update("update saas_user set name = #{arg1} WHERE id = #{arg0}")
    public User updateNameById(Long id,String name);

}
