create schema mybnb;

use mybnb;

create table acc_occupancy (
	acc_id varchar(10),
    vacancy int,
    primary key (acc_id));
    
create table reservations(
	resv_id varchar(8),
    name varchar(128),
    email varchar(128),
    acc_id varchar(10),
    
    primary key (resv_id),
    constraint fk_acc_id foreign key (acc_id) references mybnb(acc_occupancy));