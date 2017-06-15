package net.vatri.ecommerce.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class GroupResource extends ResourceSupport{
    @JsonProperty public long id;
    public String groupName;
    public String price;
}
