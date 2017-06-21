# Java Spring E-commerce

E-commerce REST API based on Java Spring, Spring Boot, Spring HATEOAS, Hibernate ORM with MySQL, JWT and Redis.

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
    @Return: (List) All items in the cart
  /{id} POST - Add CartItem to cart with ID {id}
    @Param: CartItem(productId*, int quantity*)
    @Return: (String) "OK"
  /{id}/{product_id} DELETE - Remove product with ID {product_id} from cart with ID {id}
    @Return: (String) "OK"
  /{id}/quantity POST - Updates cart item, i.e. set product quantity
    @Param: (JSON) CartItem(productId*, quantity*)
    @Return: (String) "OK"
  /{id}/order - POST - Create order from cart
    @Param: (JSON) Order(name*, OrderItem items*, address,city,zip,status,comment,totalPrice,type)
    @Return: (String) "OK"
```
