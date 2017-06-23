package net.vatri.ecommerce.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.*;

public class RedisCache implements Cache{

    private ObjectMapper objectMapper;
    private Jedis jedis;

    public RedisCache(ObjectMapper objectMapper, Jedis jedis){
        this.objectMapper = objectMapper;
        this.jedis = jedis;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public Object getItem(String key, Class type) {
        String jsonObject = jedis.get(key);
        try {
            return objectMapper.readValue(jsonObject, type);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Object setItem(String key, Object item) {
        try {
            String jsonItem = objectMapper.writeValueAsString(item);
            String out = jedis.set(key, jsonItem);
            return objectMapper.readValue(out, Object.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void removeItem(String key) {
        jedis.del(key);
    }

    @Override
    public Collection<Object> getList(String key, Class type) {
        return getListFromRedis(key, type);
    }

    @Override
    public Collection<Object> addItemToList(String key, Object item) {
        try {
            String jsonItem = objectMapper.writeValueAsString(item);
            jedis.sadd(key, jsonItem);

            return this.getListFromRedis(key, item.getClass());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Collection<Object> removeItemFromList(String key, Object item) {

        getListFromRedis(key, item.getClass()).forEach( row -> {
            if(row.equals(item)){
                try {
                    String jsonItem = objectMapper.writeValueAsString(row);
                    jedis.srem(key, jsonItem);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Can't find object in Redis list.");
            }
        });

        return getListFromRedis(key, item.getClass());
    }


    private Collection<Object> getListFromRedis(String key, Class type){
        Collection<Object> list = new ArrayList<>();
        jedis.smembers(key).forEach(jsonItem -> {
            try {
                list.add(objectMapper.readValue(jsonItem, type));
            } catch (Exception e){
                e.getMessage();
            }
        });
        return list;
    }
}
