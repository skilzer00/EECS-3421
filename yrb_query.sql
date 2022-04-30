--===================================================================
--EECS3421B
--Project 3
--Name:Sadman Sakib
--ID: 215916232
--EECS Account:skilzer
--===================================================================

-- Question 1

select * 
from yrb_customer 
where yrb_customer.name LIKE '_a%' AND yrb_customer.name LIKE '%e'
;

-- Question 2

select yrb_book.cat, count(yrb_purchase.title) as QNTY_SOLD
from yrb_book 
INNER JOIN yrb_purchase using (title)
group by yrb_book.cat 
order by count(yrb_purchase.title) DESC limit 1;

-- Question 3

select yrb_member.cid,name,club
from yrb_customer,yrb_member
where yrb_member.cid IN (
	select cid
	from yrb_member
	group by cid
	having count <= 2)
and yrb_member.cid = yrb_customer.cid
order by yrb_member.cid;


-- Question 4

select distinct cat
from yrb_book
where cat not in (
select distinct cat
from yrb_purchase,yrb_book
where yrb_purchase.title = yrb_book.title
and yrb_purchase.year = yrb_book.year
and cid = 9 )
order by cat;


-- Question 5

select * 
from yrb_offer 
where yrb_offer.price in (
select max(yrb_offer.price) 
from yrb_offer) 
union 
(
select * 
from yrb_offer 
where yrb_offer.price in (
select min(yrb_offer.price) 
from yrb_offer)
);

-- Question 6
select cid,when,totalweight,cost,(cost*totalweight) as Total_Cost
from yrb_shipping,(
	select yrb_purchase.cid,yrb_purchase.when,sum(yrb_book.weight * yrb_purchase.qnty) as totalweight
	from yrb_purchase,yrb_book,yrb_shipping
	where yrb_book.weight < yrb_shipping.weight
	and (yrb_book.weight + 500) > yrb_shipping.weight
	and (yrb_book.year = yrb_purchase.year)
	and (yrb_book.title = yrb_purchase.title)
	group by yrb_purchase.cid,yrb_purchase.when
	order by yrb_purchase.cid)
where totalweight < yrb_shipping.weight
and yrb_shipping.weight < (totalweight + 500 );	

-- Question 7

select yrb_book.title, yrb_book.year, reader.year1  as year2 
from (
select title, year as year1 from yrb_book
) as reader 
INNER JOIN yrb_book ON yrb_book.title = reader.title 
and (yrb_book.year < reader.year1)
;


-- Question 8

select * from
(select cid,name,count as number_of_purchase,0 sort_order
from yrb_customer
inner join yrb_purchase using(cid)
group by (cid,name)
)
UNION
select cid,name, 0 np,1 sort_order 
from yrb_customer 
where cid not in (
select cid 
from yrb_purchase) 
order by sort_order;

-- Question 9

select club,count(club) as Number_of_Offers,sum(price) as Total_Price
from yrb_offer
group by club
having avg(price) > (select avg(price) from yrb_offer)
order by yrb_offer.club
;

-- Question 10

select yrb_purchase.cid,name,sum(price*qnty) as TotalPrice
from yrb_purchase,yrb_offer,yrb_customer
where yrb_purchase.club = yrb_offer.club
and yrb_customer.cid = yrb_purchase.cid
and yrb_offer.title = yrb_purchase.title
and yrb_offer.year = yrb_purchase.year
group by yrb_purchase.cid,name
having sum(price*qnty) > 300
order by sum(price*qnty) desc;

