package net.vatri.ecommerce.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class MySwaggerConfig {

    @Bean
    public ApiListingScannerPlugin pi1(){
        return new ApiListingScannerPlugin(){

            @Override
            public List<ApiDescription> apply(DocumentationContext documentationContext) {
                return new ArrayList<ApiDescription>(
                    Arrays.asList(
                        new ApiDescription(
                            "/login",
                            "Login and get JWT token",
                            Arrays.asList(
                                new OperationBuilder(new CachingOperationNameGenerator())
                                    .authorizations(new ArrayList())
                                    .codegenMethodNameStem("login")
                                    .method(HttpMethod.POST)
                                    .parameters(
                                        Arrays.asList(
                                            new ParameterBuilder()
                                                .description("Username / email")
                                                .type(new TypeResolver().resolve(String.class))
                                                .name("username")
                                                .parameterType("body")
                                                .parameterAccess("access")
                                                .required(true)
                                                .modelRef(new ModelRef("string"))
                                                .build()
                                            ,new ParameterBuilder()
                                                .description("search by description")
                                                .type(new TypeResolver().resolve(String.class))
                                                .name("email")
                                                .parameterType("body")
                                                .parameterAccess("access")
                                                .required(true)
                                                .modelRef(new ModelRef("string"))
                                                .build()
                                        )
                                    )
                                    .build()
                            ),
                            false
                        )
                    )
                );
            }

            @Override
            public boolean supports(DocumentationType documentationType) {
                return DocumentationType.SWAGGER_2.equals(documentationType);
            }
        };
    }

    public Docket api() {

//        apis.add(RequestHandlerSelectors.any().apply());

//        Predicate<RequestHandler> apis = RequestHandlerSelectors.basePackage("net.vatri.ecommerce.controllers");// RequestHandlerSelectors.any();
        Predicate apis = Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"));
        ///////////// 2017-06-28

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(apis)
//                .paths(PathSelectors.any())
//                .paths(paths())
                .build();

        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Spring REST e-commerce starter app")
                .description("E-commerce REST API based on Java Spring, Spring Boot, Hibernate ORM with MySQL, Spring HATEOAS, Spring Fox (Swagger API docs), JWT and Redis.")
                .version("1.0")
                .build();
    }

    private Predicate<String> paths() {
        return or(
                regex("/login.*"),
                regex("/product.*"),
                regex("/group.*"),
                regex("/order.*"),
//                regex("/cart.*"),
//                regex("/springsRestController.*"),
                regex("/cart.*"));
    }
}
