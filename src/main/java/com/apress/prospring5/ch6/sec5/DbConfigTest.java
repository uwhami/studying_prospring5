package com.apress.prospring5.ch6.sec5;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;


public class DbConfigTest {

    private static Logger logger = LoggerFactory.getLogger(DbConfigTest.class);

    /**
     * XML 구성 테스트에 사용됨.
     */
    @Test
    public void testOne() throws SQLException{
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
//        ctx.load("classpath:spring/ch6/drivemanager-cfg-01.xml");
//        ctx.load("classpath:spring/ch6/drivemanager-cfg-02.xml");
        ctx.load("classpath:spring/ch6/datasource-dbcp.xml");
        ctx.refresh();

        DataSource dataSource = ctx.getBean("dataSource", DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);

        ctx.close();
    }

    /**
     * DbConfig 구성 클래스 테스트에 사용됨.
     */
    @Test
    public void testTwo() throws  SQLException{
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(DbConfigTest.class);
        DataSource dataSource = ctx.getBean("dataSource", DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);

        ctx.close();
    }

    private void testDataSource(DataSource dataSource) throws SQLException{
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT 1");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int mockVal = resultSet.getInt("1");
                assertTrue(mockVal == 1);
            }
            statement.close();
        }catch(Exception e){
            logger.debug("==========Problem unexpected.", e);
        }finally {
            if(connection != null){
                connection.close();
            }
        }
    }

}
