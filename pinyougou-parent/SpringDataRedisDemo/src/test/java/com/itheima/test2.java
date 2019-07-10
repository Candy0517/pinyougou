package com.itheima;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-redis.xml")
public class test2 {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void setValue(){
        redisTemplate.boundSetOps("set").add("刘备");
        redisTemplate.boundSetOps("set").add("关羽");
        redisTemplate.boundSetOps("set").add("张飞");
    }

    @Test
    public void getValue(){
        Object set = redisTemplate.boundSetOps("set").members();
        System.out.println(set);
    }

    @Test
    public void deleteValue(){
       redisTemplate.boundSetOps("set").remove("张飞");
       redisTemplate.boundSetOps("set").remove("刘备");
       redisTemplate.boundSetOps("set").remove("关羽");
    }
    @Test
    public void deleteAllValue(){
        redisTemplate.delete("set");//删集合

    }
}
