package net.vatri.ecommerce.controllers;

import net.vatri.ecommerce.models.Order;
import net.vatri.ecommerce.services.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private EcommerceService ecommerceService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Order> index() {
        return ecommerceService.getOrders();
    }

    @PostMapping
    public Order create(@RequestBody Order order){

        // Required by Hibernate ORM to save properly
        if(order.getItems() !=null){
            order.getItems().forEach(item -> item.setOrder(order));
        }
        return ecommerceService.saveOrder(order);
    }

    @RequestMapping("/{id}")
    public Order view(@PathVariable("id") long id){
        return ecommerceService.getOrder(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Order edit(@PathVariable("id") long id, @RequestBody Order order){

        Order updatedOrder = ecommerceService.getOrder(id);

        if(updatedOrder== null){
            return null;
        }


        return ecommerceService.saveOrder(updatedOrder);
    }
}
