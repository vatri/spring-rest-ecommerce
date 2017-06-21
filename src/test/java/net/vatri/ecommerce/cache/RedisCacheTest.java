package net.vatri.ecommerce.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Test;
import redis.clients.jedis.Jedis;

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


    public void setUp() throws Exception{
        ObjectMapper om = mock(ObjectMapper.class);
        when(om.readValue(anyString(), (Class<Object>) anyObject())).thenReturn(new MockObject(1));
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

    @Test
    public void testGetItem(){
        MockObject o = (MockObject) this.cache.getItem("item", MockObject.class);

        assertTrue(o.id > 0);
    }

    @Test
    public void testAddObjectToList(){
        Collection<Object> list = this.cache.addItemToList("item", new MockObject(1));
        assertEquals(2, list.size());// See the list in setUp()
    }

    @Test
    public void testRemoveObjectFromList(){

        Set<String> list = new HashSet<>();
        list.add("{id:1}");

        Jedis newJedis = mock(Jedis.class);
        when(newJedis.smembers(anyString())).thenReturn(list);

        cache.setJedis(newJedis);

        Collection<Object> result = cache.removeItemFromList("item", new MockObject(1));
        assertEquals(1, result.size()); // See above list
    }

    @Test
    public void testAddingObjectToCache(){
        MockObject res = (MockObject) cache.setItem("new_item", new MockObject(1));
        assertEquals(1, res.id);
    }


}