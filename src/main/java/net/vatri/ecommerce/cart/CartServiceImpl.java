package net.vatri.ecommerce.cart;

import net.vatri.ecommerce.cache.Cache;
import net.vatri.ecommerce.models.Product;
import net.vatri.ecommerce.services.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return new HashSet<CartItem>( (List) cache.getList(cartId, CartItem.class));
    }

    @Override
    public void createOrder(String cartId) {

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
