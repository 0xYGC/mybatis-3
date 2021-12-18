package org.apache.ibatis.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * 执行器测试
 * @Author: Yanggc
 * DateTime: 12-18 22:09
 */
public class ExecutorTest {

    final static String DB_URL =  "jdbc:mysql://yanggc.com:3306/s2b2c?useUnicode=true&useSSL=false";
    final static String DB_USERNAME =  "root";
    final static String DB_PASSWORD =  "useradmin";

    private Configuration configuration;

    private Connection connection;

    private JdbcTransaction jdbcTransaction;

    @Before
    public void init() throws IOException, SQLException {
        // 获取配置文件输入流
        InputStream inputStream = Resources.getResourceAsStream("org/apache/ibatis/example/mybatis-config.xml");
        // 通过SqlSessionFactoryBuilder的build()方法创建SqlSessionFactory实例
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        configuration = sqlSessionFactory.getConfiguration();
        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        jdbcTransaction = new JdbcTransaction(connection);
    }

    /**
     *简单执行器测试
     * 每次都会预编译
     */
    @Test
    public void simpleTest() throws SQLException {
        SimpleExecutor simpleExecutor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement mappedStatement = configuration.getMappedStatement("org.apache.ibatis.example.dao.IUserDAO.selectById");
        List<Object> resList = simpleExecutor.doQuery(mappedStatement, 1L, RowBounds.DEFAULT
                , SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(10));
        System.out.println(resList.get(0).toString());
        List<Object> res2List = simpleExecutor.doQuery(mappedStatement, 1L, RowBounds.DEFAULT
                , SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(10));


    }

    /**
     * 可重用执行器
     * @throws SQLException
     */
    @Test
    public void reuseTest() throws SQLException {
        ReuseExecutor reuseExecutor = new ReuseExecutor(configuration, jdbcTransaction);
        MappedStatement mappedStatement = configuration.getMappedStatement("org.apache.ibatis.example.dao.IUserDAO.selectById");
        List<Object> resList = reuseExecutor.doQuery(mappedStatement, 1L, RowBounds.DEFAULT
                , SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(10));
        System.out.println(resList.get(0).toString());
        List<Object> res2List = reuseExecutor.doQuery(mappedStatement, 1L, RowBounds.DEFAULT
                , SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(10));
    }

    /**
     * 批处理执行器:
     * 只针对增删改操作：只预编译一次
     * @throws SQLException
     */
    @Test
    public void batchTest() throws SQLException {
        BatchExecutor batchExecutor = new BatchExecutor(configuration, jdbcTransaction);
//        MappedStatement mappedStatement = configuration.getMappedStatement("org.apache.ibatis.example.dao.IUserDAO.selectById");
//        List<Object> resList = batchExecutor.doQuery(mappedStatement, 1L, RowBounds.DEFAULT
//                , SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(10));
//        System.out.println(resList.get(0).toString());
//
//        List<Object> res2List = batchExecutor.doQuery(mappedStatement, 1L, RowBounds.DEFAULT
//                , SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(10));
        MappedStatement updateMappedStatement = configuration.getMappedStatement("org.apache.ibatis.example.dao.IUserDAO.updateNameById");
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("arg0",1);
        paramsMap.put("arg1","我是姓名");

        batchExecutor.doUpdate(updateMappedStatement, paramsMap);
        paramsMap.put("arg0",1);
        paramsMap.put("arg1","我是姓名我是姓名我是姓名我是姓名");
        batchExecutor.doUpdate(updateMappedStatement, paramsMap);
        //更改时候要进行刷新
        batchExecutor.doFlushStatements(false);
    }
}
