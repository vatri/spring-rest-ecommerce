package net.vatri.ecommerce.controllers;

import net.vatri.ecommerce.models.Product;
import net.vatri.ecommerce.services.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    EcommerceService ecommerceService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> index() {
        return ecommerceService.getProducts();
    }

    @RequestMapping("/{id}")
    public Product view(@PathVariable("id") long id){
        return ecommerceService.getProduct(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Product edit(@PathVariable("id") long id, @RequestBody Product product){

        Product updatedProduct = ecommerceService.getProduct(id);

        if(updatedProduct == null){
            return null;
        }

        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());

        return ecommerceService.saveProduct(updatedProduct);
    }

    // Todo: add delete method

}