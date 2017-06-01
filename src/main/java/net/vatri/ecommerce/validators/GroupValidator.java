package net.vatri.ecommerce.validators;

import net.vatri.ecommerce.models.ProductGroup;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GroupValidator implements Validator{

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductGroup.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "groupName", "Group name empty");

        ProductGroup group = (ProductGroup) o;

        if( group.getGroupName().length() < 2 ){
            errors.rejectValue("groupName", "Name must be 2 or more characters");
        }
    }
}
