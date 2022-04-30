------Question 1 -----------


-- yrb_offer and yrb_purchase add "on delete cascade" to the foreign key contraint

create table yrb_offer (
    club   varchar(15)  not null,
    title  varchar(25)  not null,
    year   smallint     not null,
    price  decimal(5,2) not null,
    constraint yrb_off_pk
        primary key (club, title, year),
    constraint yrb_off_fk_club
        foreign key (club) references yrb_club,
    constraint yrb_off_fk_book
        foreign key (title, year) references yrb_book
        on delete cascade,
    constraint yrb_off_price
        check (price > 0)
);

create table yrb_purchase (
    cid    smallint     not null,
    club   varchar(15)  not null,
    title  varchar(25)  not null,
    year   smallint     not null,
    when   timestamp    not null,
    qnty   smallint     not null,
    constraint yrb_pur_pk
        primary key (cid, club, title, year, when),
    constraint yrb_pur_fk_offer
        foreign key (club, title, year) references yrb_offer
        on delete cascade,
    constraint yrb_pur_fk_mem
        foreign key (club, cid) references yrb_member,
    constraint yrb_pur_qnty
        check (qnty > 0)
);


-------Question 2------------

create trigger bkpurchases
AFTER INSERT on yrb_purchase
referencing new row as r
for each row
when (r.cid not in (select cid from yrb_member where club = r.club))
delete from yrb_purchase
where cid = r.cid and club = r.club and title = r.title and  when = r.when and year = r.year; 

------Question 3-----------

create trigger basic
AFTER INSERT on yrb_customer
referencing new row as b
for each row
INSERT into new_member(club, cid)
values('Basic', b.cid);
