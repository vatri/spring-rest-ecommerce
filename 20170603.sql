ALTER TABLE `group_variants`
CHANGE `variant_name` `variant_name` varchar(255) COLLATE 'utf8_general_ci' NOT NULL AFTER `id`,
CHANGE `group_id` `group_id` int(11) NOT NULL AFTER `variant_name`;
ALTER TABLE `group_variants`
DROP FOREIGN KEY `FKjggn49ydnd0a0lusxeuhqutlp`,
ADD FOREIGN KEY (`group_id`) REFERENCES `product_groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE

---
ALTER TABLE `order_items`
DROP FOREIGN KEY `FKbioxgbv59vetrxe0ejfubep1w`,
ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `order_items`
DROP FOREIGN KEY `FKrftu5hh0vupk70occwy3rx8on`,
ADD FOREIGN KEY (`product_variant_id`) REFERENCES `group_variants` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `order_items`
DROP FOREIGN KEY `FKocimc7dtr037rh4ls4l95nlfi`,
ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

------
update `orders` set created = "2017-06-03 00:00:00";
ALTER TABLE `orders`
CHANGE `created` `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `comment`,
CHANGE `name` `name` varchar(255) COLLATE 'utf8_general_ci' NOT NULL AFTER `created`;


-----
ALTER TABLE `products`
DROP FOREIGN KEY `FK9an6554j5j7f3x8rifrgh4phr`,
ADD FOREIGN KEY (`group_id`) REFERENCES `product_groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

-----

update product_groups set created = '2017-06-03 00:00:00';
ALTER TABLE `product_groups`
CHANGE `created` `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `id`,
CHANGE `group_name` `group_name` varchar(255) COLLATE 'utf8_general_ci' NOT NULL AFTER `created`;

---
ALTER TABLE `product_images`
DROP FOREIGN KEY `product_images_ibfk_1`,
ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

-----
ALTER TABLE `users`
CHANGE `email` `email` varchar(255) COLLATE 'utf8_general_ci' NOT NULL AFTER `created`,
CHANGE `name` `name` varchar(255) COLLATE 'utf8_general_ci' NOT NULL AFTER `email`;