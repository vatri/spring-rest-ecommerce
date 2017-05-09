package net.vatri.ecommerce.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/")
    public String create(){
        return cartService.createNewCart();
    }

    @PostMapping("{id}/add")
    public String addProduct(@PathVariable("id") String cartId, @RequestBody CartItem cartItem){
        cartService.addProduct(cartId, cartItem);
        return "OK";
    }

    @PostMapping("{id}/create_order")
    public void createOrder(@PathVariable("id") String cartId){
        cartService.createOrder(cartId);
    }
}
