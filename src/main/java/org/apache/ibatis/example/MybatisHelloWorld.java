package org.apache.ibatis.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Description:
 *
 * @Author: Yanggc
 * DateTime: 12-18 20:45
 */
public class MybatisHelloWorld {
    public static void main(String[] args) {
        String resource = "org/apache/ibatis/example/mybatis-config.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

            SqlSession sqlSession = sqlMapper.openSession();
            try {

                Object o = sqlSession.selectOne("SELECT * FROM saas_user WHERE id = #{1}", 1);
                System.out.println(o.toString());
//                User user = (User) session.selectOne("org/apache/ibatis/example/mapper/UserMapper.xml", 1);
//                System.out.println(user.toString());
            } finally {
                sqlSession.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
