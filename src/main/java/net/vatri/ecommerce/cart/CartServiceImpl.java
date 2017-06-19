package net.vatri.ecommerce.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vatri.ecommerce.cache.Cache;
import net.vatri.ecommerce.models.OrderItem;
import net.vatri.ecommerce.models.Product;
import net.vatri.ecommerce.services.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private EcommerceService ecommerceService;

    @Autowired
    private Cache cache;

    @Override
    public String createNewCart() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void addProduct(String cartId, CartItem cartItem) {
        cache.addItemToList(cartId, cartItem);
    }

    @Override
    public void removeProduct(String cartId, String productId) {
        Product product = new Product();
        product.setId(Long.parseLong(productId));
        cache.removeItemFromList(cartId, product);
    }

    @Override
    public void setProductQuantity(String cartId, String productId, int quantity){

        List<CartItem> list = (List<CartItem>) cache.getItem(cartId, CartItem.class);

        list.forEach( cartItem -> {
            try{
                if(cartItem.getProductId() == Long.parseLong(productId) ){
                    cartItem.setQuantity( quantity );
                    cache.removeItemFromList(cartId, cartItem);
                    cache.addItemToList(cartId, cartItem);
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } );
    }

    @Override
    public Set<CartItem> getItems(String cartId){

        return (Set) cache.getList(cartId, CartItem.class);

//        redis.smembers(generateCartRedisId(cartId)).forEach( cartItemJson -> {
////            System.out.println("cartItemJson " + cartItemJson );
//            try{
//                CartItem item = objectMapper.readValue(cartItemJson, CartItem.class);
//                output.add(item);
//            } catch (Exception e){
////                System.out.println( "GetItems() ERROR: " + e.getMessage() );
//                e.printStackTrace();
//            }
//        } );
//        return output;
    }

    @Override
    public void createOrder(String cartId) {
//        redis.smembers(generateCartRedisId(cartId)).forEach( cartItemJson -> {
//
//            CartItem cartItem = null;
//            try{
//                cartItem = objectMapper.readValue(cartItemJson, CartItem.class);
//            } catch (Exception e){
//                System.out.println(e.getMessage());
//            }

        Set<CartItem> list = (Set) cache.getList(cartId, CartItem.class);

        list.forEach(cartItem -> {

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
