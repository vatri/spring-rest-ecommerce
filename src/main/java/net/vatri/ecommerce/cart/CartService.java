package net.vatri.ecommerce.cart;

import net.vatri.ecommerce.cart.CartItem;
import net.vatri.ecommerce.models.Order;

import java.util.Set;

public interface CartService {
    public String createNewCart();
    public void addProduct(String cartId, CartItem cartItem);
    public void removeProduct(String cartId, String productId);

    public void setProductQuantity(String cartId, String productId, int quantity);
    public Set<CartItem> getItems(String cartId);
    public Order createOrder(String cartId, Order order);
}
