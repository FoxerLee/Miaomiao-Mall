create table product_t(product_id string, name string, current_price string, inventory int, has_saled int, classification string, update_time string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;
load data local inpath '/home/hadoop/ly_data/product.csv' into table product_t;

create table pm_statistics_t(product_id string, month int, day int, has_saled int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;

load data local inpath '/home/hadoop/ly_data/pm_statistics/day_order_3.csv' into table pm_statistics_t;


create table r_order_t(order_id string, product_id string, sales_price double, buyer string, address string, phone string, sales_time string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;

load data local inpath '/home/hadoop/ly_data/r_order/order_1.csv' overwrite into table r_order_t;


create table o_statistics_t(address string, month int, day int, has_saled int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;
load data local inpath '/home/hadoop/ly_data/o_statistics.csv' into table o_statistics_t;

create table classification_t(classification_id string, name string, has_saled int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;
load data local inpath '/home/hadoop/ly_data/classification.csv' into table classification_t;


create table cm_statistics_t(classification string, month int, day int, has_saled int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;
load data local inpath '/home/hadoop/ly_data/cm_statistics.csv' into table cm_statistics_t;

insert into product(product_id, name, current_price, inventory, has_saled, classification, update_time)
values('1', '小阳阳', 28.0, 123, 123, 'TAC', 2018-01-01 12:12:12);

insert into product(product_id, name, current_price, inventory, has_saled, classification, update_time)
values('2', '小金金', 28.0, 123, 123, 'TAC', 2018-01-01 12:12:12);

insert into product(product_id, name, current_price, inventory, has_saled, classification, update_time)
values('3', '小媛媛', 28.0, 123, 123, 'TAC', 2018-01-01 12:12:12);

insert into product(product_id, name, current_price, inventory, has_saled, classification, update_time)
values('4', '小毛毛', 28.0, 123, 123, 'TAC', 2018-01-01 12:12:12);


create table product(product_id string, name string, current_price string, inventory int, has_saled int, classification string, update_time string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS ORC;

insert into product
select * from product_t;

create table pm_statistics(product_id string, month int, day int, has_saled int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS ORC;

insert into pm_statistics
select * from pm_statistics_t;

create table r_order(order_id string, product_id string, sales_price double, buyer string, address string, phone string, sales_time string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS ORC;

insert into r_order
select * from r_order_t;

create table o_statistics(address string, month int, day int, has_saled int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS ORC;

insert into o_statistics
select * from o_statistics_t;

create table classification(classification_id string, name string, has_saled int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS ORC;

insert into classification
select * from classification_t;

create table cm_statistics(classification string, month int, day int, has_saled int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS ORC;

insert into cm_statistics
select * from cm_statistics_t;
