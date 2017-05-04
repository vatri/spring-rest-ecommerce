ALTER TABLE `order_items`
CHANGE `order_id` `order_id` int(11) NOT NULL AFTER `product_variant_id`,
CHANGE `product_id` `product_id` int(11) NOT NULL AFTER `order_id`;