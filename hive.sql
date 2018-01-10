create table product(product_id string, name string, current_price string, inventory int, has_saled int, classification string, update_time date)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

create table pm_statistics(product_id string, month int, day int, has_saled int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

create table r_order(order_id string, buyer string, address string, phone string, sales_price double, sales_time date, product_id string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

create table o_statistics(address string, month int, day int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

create table classification(classification_id string, name string, has_saled int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';

create table cm_statistics(classification_id string, month int, day int, has_saled int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n';