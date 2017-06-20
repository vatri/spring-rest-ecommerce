package net.vatri.ecommerce.controllers;

import net.vatri.ecommerce.cart.CartItem;
import net.vatri.ecommerce.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/")
    public String create(){
        return cartService.createNewCart();
    }

    @PostMapping("/{id}")
    public String addProduct(@PathVariable("id") String cartId, @RequestBody CartItem cartItem){
        cartService.addProduct(cartId, cartItem);
        return "OK";
    }

    @GetMapping("/{id}")
    public Set<CartItem> getCartItems(@PathVariable("id") String cartId){
        return cartService.getItems(cartId);
    }

    @DeleteMapping("{id}/{product_id}")
    public String removeItem(@PathVariable("id") String cartId, @PathVariable("product_id") String productId){
        cartService.removeProduct(cartId,productId);
        return "OK";
    }

    @PostMapping("{id}/quantity")
    public String setProductQuantity(@PathVariable("id") String cartId,@RequestBody CartItem cartItem){
        String productId = Long.toString(cartItem.getProductId());
        cartService.setProductQuantity(cartId, productId, cartItem.getQuantity());
        return "OK";
    }

    @PostMapping("{id}/order")
    public String createOrder(@PathVariable("id") String cartId){
        cartService.createOrder(cartId);
        return "OK";
    }

}
