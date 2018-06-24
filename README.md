# Java Spring E-commerce

E-commerce REST API based on Java Spring, Spring Boot, Hibernate ORM with MySQL, Spring HATEOAS, Spring Fox (Swagger API docs), JWT and Redis.

## REST API Endpoints

All inputs and outputs use JSON format.

**To open Swagger (interactive) API documentation, navigate your browser to [YOUR-URL]/swagger-ui.html**


```
/login
  POST / - Login using username: b and password:b

/product
  GET / - List of products
  POST / - Add product - required : String name , String groupId, String userId
  GET /{id} - View product
  POST /{id} - Update product
  GET /{id}/images - View product images
  GET /image/{id}- View image
  POST /{id}/uploadimage - Upload product image

/group
  GET / - List of groups
  POST / - Add group
  GET /{id} - View group
  POST /{id} - Update group

/order
  GET / - List of orders
  POST / - Add order
  GET /{id} - View order
  POST /{id} - Update order

/cart
  POST / - Create cart
  GET /{id} - Get items for card with ID = {id}
  POST /{id} - Add CartItem to cart with ID {id}
  DELETE /{id}/{product_id} - Remove product with ID {product_id} from cart with ID {id}
  POST /{id}/quantity - Updates cart item, i.e. set product quantity
  POTS /{id}/order - Create order from cart

```
