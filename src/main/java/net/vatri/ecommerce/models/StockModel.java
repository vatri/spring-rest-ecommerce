package net.vatri.ecommerce.models;

public class StockModel {

    public String product_name = "";
    public String variant_name = "";
    public String stock = "0";

    public StockModel(String product_name, String variant_name, String stock) {
        this.setProductName(product_name);
        this.setVariantName(variant_name);
        this.setStock(stock);
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public String getVariantName() {
        return variant_name;
    }

    public void setVariantName(String variant_name) {
        this.variant_name = variant_name;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
