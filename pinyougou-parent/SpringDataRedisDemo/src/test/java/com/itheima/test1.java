package com.itheima;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-redis.xml")
public class test1 {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void setValue(){
        redisTemplate.boundValueOps("string").set("str");
    }
    @Test
    public void getValue(){
        Object string = redisTemplate.boundValueOps("string").get();
        System.out.println(string);
    }
    @Test
    public void deleteValue(){
        redisTemplate.delete("string");
    }
}
