package net.vatri.ecommerce.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_groups")
public class ProductGroup {

    private Integer id;
    private String groupName;
    private String price;
    @Temporal(TemporalType.TIMESTAMP)
    private String created;//Todo - Date type...

    private List<GroupVariant> groupVariants = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<GroupVariant> getGroupVariants() {
        return groupVariants;
    }

    public void setGroupVariants(List<GroupVariant> groupVariants) {
        this.groupVariants = groupVariants;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String toString() {
        return getGroupName();
    }
}
