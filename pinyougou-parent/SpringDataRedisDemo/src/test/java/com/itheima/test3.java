package com.itheima;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-redis.xml")
public class test3 {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void setValue() {
        redisTemplate.boundListOps("list").rightPush("刘备");
        redisTemplate.boundListOps("list").rightPush("🐙");
        redisTemplate.boundListOps("list").rightPush("关羽");

    }

    @Test
    public void getValue() {
        List list = redisTemplate.boundListOps("list").range(0, 10);
        System.out.println(list);
    }

    @Test
    public void searchValueByIndex(){
        Object list = redisTemplate.boundListOps("list").index(1);
        System.out.println(list);
    }

    @Test
    public void removeValue(){
        redisTemplate.boundListOps("list").remove(1,"🐙");
    }

    @Test
    public void deleteValue() {
        redisTemplate.delete("list");

    }
}
