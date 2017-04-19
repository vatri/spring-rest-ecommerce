package net.vatri.ecommerce.services;

import net.vatri.ecommerce.models.Product;
import net.vatri.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcommerceService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(long id){
        return productRepository.findOne(id);
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }
}
