package com.itheima.test;

import com.itheima.dao.IUserDao;
import com.itheima.domain.QueryVo;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;


public class MybatisTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before//用于在测试方法执行之前执行
    public void init()throws Exception{
        //1.读取配置文件，生成字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession();
        //4.获取dao的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After//用于在测试方法执行之后执行
    public void destroy()throws Exception{
        //提交事务
        sqlSession.commit();
        //6.释放资源
        sqlSession.close();
        in.close();
    }

    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll(){
        //5.执行查询所有方法
        List<User> users = userDao.findAll();
        for(User user : users){
            System.out.println(user);
        }

    }
    /**
     * 测试保存
     */
    @Test
    public void testSave(){
        User user=new User();
        user.setUsername("ben");
        user.setAddress("new york");
        user.setSex("男");
        user.setBirthday(new Date());
        System.out.println("保存操作之前："+user);
        userDao.saveUser(user);
        System.out.println("保存操作之后："+user);
    }
    /**
     * 测试更新
     */
    @Test
    public void testUpdate(){
        User user=new User();
        user.setId(43);
        user.setUsername("jack");
        user.setAddress("new york");
        user.setSex("女");
        user.setBirthday(new Date());
        userDao.updateUser(user);

    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete(){
        userDao.deleteUser(49);
    }

    /**
     * 测试id查询
     */
    @Test
    public void testFindOne(){
        User user=userDao.findById(48);
        System.out.println(user);
    }
    /**
     * 测试模糊查询
     */
    @Test
    public void testFindByName(){
        List<User> users=userDao.findByName("%王%");
        for(User user:users){
            System.out.println(user);
        }
    }

    /**
     * 测试查询总的记录条数
     */
    @Test
    public void testFindTotal(){
        int count=userDao.findTotal();
        System.out.println(count );
    }
    /**
     * 使用QueryVo作为查询条件
     */
    @Test
    public void testFindByVo(){
        QueryVo vo=new QueryVo();
        User user=new User();
        user.setUsername("%王%");
        vo.setUser(user);
        List<User> users=userDao.findUserByVo(vo);
        for(User u:users){
            System.out.println(u);
        }
    }

}
