package net.vatri.ecommerce.cart;

public class CartItem {

    private long productId;
    private long variantId;
    private int quantity;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getVariantId() {
        return variantId;
    }

    public void setVariantId(long variantId) {
        this.variantId = variantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o){
        CartItem item = (CartItem) o;
        return item!= null && item.getProductId()==this.getProductId();
    }
}
