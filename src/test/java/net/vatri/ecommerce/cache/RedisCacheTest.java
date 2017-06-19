package net.vatri.ecommerce.cache;

import io.jsonwebtoken.lang.Collections;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mock;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class RedisCacheTest extends TestCase {

    private RedisCache cache;

    private class MockObject{
        public int id = 0;
        public MockObject(int id){
            this.id = id;
        }
    }

    public void setUp(){
        ObjectMapper om = mock(JacksonObjectMappper.class);
        when(om.readValue(anyString(),anyObject())).thenReturn(new MockObject(1));
        when(om.writeValueAsString(new MockObject(1) )).thenReturn("ok");

        Set<String> list = new HashSet<>();
        list.add("{id:1}");
        list.add("{id:2}");

        Jedis jedis = mock(Jedis.class);
        when(jedis.smembers("item") ).thenReturn(list);

        cache = new RedisCache(om,jedis);
    }
    @Test
    public void testGetList(){
        Collection<MockObject> list = (Collection) this.cache.getList("item", MockObject.class);

        list.forEach(o -> {
            if(o==null || o.id < 1){
                fail("ID not set");
            }
        });
        assertTrue(true);
    }


}