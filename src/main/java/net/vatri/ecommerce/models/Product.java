package net.vatri.ecommerce.models;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    private long id;
    private String name;
    private String created;
    private String price;
    private ProductGroup group;
//    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    public ProductGroup getGroup() {
        return group;
    }

    public void setGroup(ProductGroup group) {
        this.group = group;
    }

    public String toString() {
        return getName();
    }

}