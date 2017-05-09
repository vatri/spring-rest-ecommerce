package net.vatri.ecommerce.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vatri.ecommerce.models.Product;
import net.vatri.ecommerce.services.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private Jedis redis;

    @Autowired
    EcommerceService ecommerceService;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String createNewCart() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void addProduct(String cartId, CartItem cartItem) {

        String item = null;
        try {
            item = objectMapper.writeValueAsString(cartItem);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        redis.sadd (generateCartRedisId(cartId), item );
    }

    @Override
    public void createOrder(String cartId) {
        Set<String> products =  redis.smembers(generateCartRedisId(cartId));
        products.forEach( cartItemJson -> {

            CartItem cartItem = null;
            try{
                cartItem = objectMapper.readValue(cartItemJson, CartItem.class);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

            if (cartItem != null) {
                Product prod = ecommerceService.getProduct(cartItem.getProductId());
                if(prod != null){

                    System.out.println(prod.getName());

                }
            }

        } );
    }

    private String generateCartRedisId(String cartId){
        return "cart#"+cartId+"#items";
    }
}
