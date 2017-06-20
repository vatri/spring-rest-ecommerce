# Java Spring E-commerce

E-commerce REST API based on Java Spring, Spring Boot, Spring HATEOAS, Hibernate ORM, JWT and Redis.

## REST API Endpoints

All inputs and outputs use JSON format.

```
/product
  / GET - List of products
  / POST - Add product - required : String name , String groupId, String userId
  /{id} GET - View product
  /{id} POST - Update product
  /{id}/images GET - View product images
  /image/{id} GET - View image
  /{id}/uploadimage POST - Upload product image

/group
  / GET - List of groups
  / POST - Add group
  /{id} GET - View group
  /{id} POST - Update group

/order
  / GET - List of orders
  / POST - Add order
  /{id} GET - View order
  /{id} POST - Update order

/cart
  / POST - Create cart
  /{id} GET - Get items for card with ID = {id}
  /{id} POST - {"productId" : [ID], "quantity": [QTY]}  - Add CartItem to cart with ID {id}
  /{id}/{product_id} DELETE - Remove product with ID {product_id} from cart with ID {id}
  /{id}/quantity POST - { "productId" :[ID] , "quantity" : [QTY]} - Update cart item, i.e. set product quantity
  /{id}/order - POST - Create order from cart
```
