package net.vatri.ecommerce.hateoas;

import org.springframework.hateoas.ResourceSupport;

public class ProductResource extends ResourceSupport {
    public long id;
    public String name;
    public String price;
    public String description;
    public Object group;
}
