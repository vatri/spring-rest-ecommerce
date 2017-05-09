package net.vatri.ecommerce.cart;

import net.vatri.ecommerce.cart.CartItem;

public interface CartService {
    public String createNewCart();
    public void addProduct(String cartId, CartItem cartItem);
    public void createOrder(String cartId);
}
