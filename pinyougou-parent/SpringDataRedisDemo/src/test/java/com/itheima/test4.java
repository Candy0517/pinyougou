package com.itheima;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-redis.xml")
public class test4 {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void setValue(){
            redisTemplate.boundHashOps("hash").put("1","唐僧");
            redisTemplate.boundHashOps("hash").put("2","悟空");
            redisTemplate.boundHashOps("hash").put("3","悟能");
            redisTemplate.boundHashOps("hash").put("4","悟净");
    }

    @Test
    public void getKeys(){
        Object hash = redisTemplate.boundHashOps("hash").keys();
        System.out.println(hash);
    }

    @Test
    public void getValue(){
        Object hash = redisTemplate.boundHashOps("hash").values();
        System.out.println(hash);
    }
    @Test
    public void getValueByKey(){
        Object hash = redisTemplate.boundHashOps("hash").get("1");
        System.out.println(hash);
    }
    @Test
    public void removeValueByKey(){
        Long hash = redisTemplate.boundHashOps("hash").delete("1");
        System.out.println(hash);
    }
    @Test
    public void deleteHash(){
redisTemplate.delete("hash");
    }
}
