package net.vatri.ecommerce.cache;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.*;

public class RedisCache implements Cache{

    private ObjectMapper objectMapper;
    private Jedis jedis;

//    @Autowired
    private Jedis redis;

    public RedisCache(ObjectMapper objectMapper, Jedis jedis){
        this.objectMapper = objectMapper;
        this.jedis = jedis;
    }


    @Override
    public Object getItem(String key, Class type) {
        return null;
    }

    @Override
    public void addItem(String key, Object item) {

    }

    @Override
    public void removeItem(String key, Object obj) {

    }

    @Override
    public Collection<Object> getList(String key, Class type) {
        Collection<Object> list = new ArrayList<Object>();
        jedis.smembers(key).forEach(jsonItem -> {
            list.add(objectMapper.readValue(jsonItem, Object.class));
        });
        return list;
    }

    @Override
    public void addItemToList(String key, Object item) {

    }

    @Override
    public void removeItemFromList(String key, Object item) {

    }
}
