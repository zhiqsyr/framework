/* added by dongbz at 2016-05-17 start */
create database cloud default charset utf-8 collate utf8_general_ci;
 
create table cloud.dept
	(
	id   int not null auto_increment,
	name varchar (20),
	primary key (id)
	);
/* added by dongbz at 2016-05-17 end */