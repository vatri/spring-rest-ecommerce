package net.vatri.ecommerce.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.vatri.ecommerce.models.GroupVariant;
import net.vatri.ecommerce.models.ProductGroup;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class GroupResource extends ResourceSupport{
    @JsonProperty
    public long id;
    public String groupName;
    public String price;
    public List<GroupVariant> variants;

    public GroupResource(ProductGroup group){
        this.id = group.getId();
        this.groupName = group.getGroupName();
        this.price = group.getPrice();
        this.variants = group.getGroupVariants();
    }
}
