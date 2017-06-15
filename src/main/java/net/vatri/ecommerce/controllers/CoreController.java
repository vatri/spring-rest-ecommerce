package net.vatri.ecommerce.controllers;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CoreController {
    protected Link createHateoasLink(long id){
        Link link = linkTo(getClass()).slash(id).withSelfRel();
        return link;
    }
}
