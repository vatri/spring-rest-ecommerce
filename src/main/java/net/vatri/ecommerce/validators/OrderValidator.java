package net.vatri.ecommerce.validators;

import net.vatri.ecommerce.models.Order;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class OrderValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Order.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");

        Order order = (Order) o;

        if( order.getName().length() < 2 ){
            errors.rejectValue("name", "name.required");
        }

        if(order.getItems()==null || order.getItems().size() < 1){
            errors.rejectValue("items", "items.required");
        }
    }
}

