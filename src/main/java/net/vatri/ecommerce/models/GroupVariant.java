package net.vatri.ecommerce.models;

import javax.persistence.*;

@Entity
@Table(name = "group_variants")
public class GroupVariant {

    private Integer id;
    private String variantName;
    private ProductGroup group;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "variant_name")
    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public ProductGroup getGroup() {
        return group;
    }

    public void setGroup(ProductGroup group) {
        this.group = group;
    }

    public String toString() {
        return getVariantName();
    }
}
