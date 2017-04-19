package net.vatri.ecommerce.controllers;

import net.vatri.ecommerce.models.Product;
import net.vatri.ecommerce.services.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}