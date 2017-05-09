package net.vatri.ecommerce.cart;

import java.util.List;

public class Cart {
    private List<CartItem> items;

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
