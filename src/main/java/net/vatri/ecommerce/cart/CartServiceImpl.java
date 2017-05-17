package net.vatri.ecommerce.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vatri.ecommerce.models.Product;
import net.vatri.ecommerce.services.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private Jedis redis;

    @Autowired
    private EcommerceService ecommerceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String createNewCart() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void addProduct(String cartId, CartItem cartItem) {

        String itemJson = null;
        try {
            itemJson = objectMapper.writeValueAsString(cartItem);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Adding item:" + itemJson);
        redis.sadd(generateCartRedisId(cartId), itemJson);
    }

    @Override
    public void removeProduct(String cartId, String productId) {
        Set<String> items = redis.smembers(generateCartRedisId(cartId));

        items.forEach( jsonItem -> {
            CartItem cartItem = null;
            try{
                cartItem = objectMapper.readValue(jsonItem, CartItem.class);

                if(cartItem.getProductId() == Long.parseLong(productId) ){
                    redis.srem(generateCartRedisId(cartId), jsonItem );
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } );
    }

    @Override
    public void setProductQuantity(String cartId, String productId, int quantity){
        redis.smembers(generateCartRedisId(cartId)).forEach( jsonItem -> {
            CartItem cartItem = null;
            try{
                cartItem = objectMapper.readValue(jsonItem, CartItem.class);

                if(cartItem.getProductId() == Long.parseLong(productId) ){
                    cartItem.setQuantity( quantity );
                    String newJsonItem = objectMapper.writeValueAsString(cartItem);
                    redis.srem(generateCartRedisId(cartId), jsonItem );
                    redis.sadd(generateCartRedisId(cartId), newJsonItem);
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } );
    }

    @Override
    public Set<CartItem> getItems(String cartId){

        Set<CartItem> output = new HashSet<CartItem>();

        redis.smembers(generateCartRedisId(cartId)).forEach( cartItemJson -> {
//            System.out.println("cartItemJson " + cartItemJson );
            try{
                CartItem item = objectMapper.readValue(cartItemJson, CartItem.class);
                output.add(item);
            } catch (Exception e){
//                System.out.println( "GetItems() ERROR: " + e.getMessage() );
                e.printStackTrace();
            }
        } );
        return output;
    }

    @Override
    public void createOrder(String cartId) {
        redis.smembers(generateCartRedisId(cartId)).forEach( cartItemJson -> {

            CartItem cartItem = null;
            try{
                cartItem = objectMapper.readValue(cartItemJson, CartItem.class);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

            if (cartItem != null) {
                Product prod = ecommerceService.getProduct(cartItem.getProductId());
                if(prod != null){

                    // Todo: create new Hibernate model (OrderItem) and add to list
                    System.out.println(prod.getName());

                }
            }

        } );
    }

    private String generateCartRedisId(String cartId){
        return "cart#"+cartId+"#items";
    }
}
