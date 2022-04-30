create table photography_contest (
	name varchar(15) not null,
	year integer not null,
	location varchar(15) not null,
	from_date date(8) not null,
	to_date date not null,
	from_date date not null,
	constraint photography_contest_PK
		primary key(name,year),
);

create table support (
	level integer not null,
	name varchar(15) not null,
	year integer not null,
	sponsor_name not null,
	constraint support_PK
		primary key (name,year,sponsor_name),
	constraint photography_contest_FK
		foreign key(name,year),
	constraint sponsors_FK
		foreign key(sponsor_name),
);

create table sponsors (
	sponsor_type varchar(15) not null.
	sponsor_name varchar(15) not null,
	constraint sponsors_PK
		primary key(sponsor_name),
);

create table category(
	cat_name varchar(15) not null,
	description varchar(140) not null,
	constraint category_PK
		primary key(cat_name),
);

create table photographer(
	email varchar(15) not null,
	photographer_name varchar(15) not null,
	country char not null,
	reg_num integer AUTO_INCREMENT not null,
	DOB date not null,
	constraint photographer_PK
		primary key(reg_num),
);

create table submissions (
	caption varchar(50) not null,
	name varchar(15) not null,
	year integer not null,
	reg_num integer not null,
	cat_name varchar(15) not null,
	format varchar(15) not null,
	size integer not null,
	description varchar(140)not null,
	date date not null,
	constraint submissions_PK
		primary key(caption, name, year, reg_num),
	constraint photography_contest_FK
		foreign key(name,year),
	constraint photographer_FK
		foreign key(reg_num),
	constraint category_FK
		foreign key(cat_num),
);



create table in (
	cat_name varchar(15) not null,
	caption varchar(50) not null,
	name varchar(15) not null,
	year varchar(15) not null,
	reg_num integer not null,
	constraint in_PK
		primary key(cat_name,name,year,caption,reg_num)
	constraint category_FK
		foreign key(cat_num),
	constraint submission_FK
		foreign key(caption, name, year, reg_num),
);

create table featured_exhibition (
	caption varchar(50) not null,
	name varchar(15) not null,
	year integer not null,
	reg_num integer not null,
	cat_name varchar(15) not null,
	format varchar(15) not null,
	size integer not null,
	description varchar(140)not null,
	constraint featured_exhibition_PK
		primary key(caption, name, year, reg_num),
	constraint photography_contest_FK
		foreign key(name,year),
	constraint photographer_FK
		foreign key(reg_num),
	constraint category_FK
		foreign key(cat_num),
);

create table photo_magazine(
	publish_year date not null,
	editor_in_chief varchar(15) not null,
	title varchar(15) not null,
	url varchar(15) not null,
	constraint photo_magazine_PK 
		primary key(publish_year),
);

create table publish(	
	caption varchar(50) not null,
	name varchar(15) not null,
	year integer not null,
	reg_num integer not null,
	publish_year date not null,
	constraint publish_PK
		primary key(name,year,reg_num,publish_year,caption)
	contraint photo_magazine_FK
		foreign key(publish_year)
	constrain featured_exhibition_FK
		foreign key(caption,name,year,reg_num)
);

create table open_call_exhibition(
	caption varchar(50) not null,
	name varchar(15) not null,
	year integer not null,
	reg_num integer not null,
	cat_name varchar(15) not null,
	format varchar(15) not null,
	size integer not null,
	description varchar(140)not null,
	constraint open_call_exhibition_PK
		primary key(caption, name, year, reg_num),
	constraint photography_contest_FK
		foreign key(name,year),
	constraint photographer_FK
		foreign key(reg_num),
	constraint category_FK
		foreign key(cat_num),
);

create table nominee(
	caption varchar(50) not null,
	name varchar(15) not null,
	year integer not null,
	reg_num integer not null,
	cat_name varchar(15) not null,
	format varchar(15) not null,
	size integer not null,
	description varchar(140)not null,
	constraint nominee_PK
		primary key(caption, name, year, reg_num),
	constraint photography_contest_FK
		foreign key(name,year),
	constraint photographer_FK
		foreign key(reg_num),
	constraint category_FK
		foreign key(cat_num),
);	

create table members(
	member_name varchar(15) not null,
	expertise char not null,
	member_email varchar(15) not null,
	member_country char not null,
	member_id integer not null,
	constraint members_PK
		primary key(member_id),
);

create table nominator(
	name varchar(15) not null,
	year integer not null,
	caption varchar(50) not null,
	reg_num integer not null,
	member_id integer not null,
	constrain nominator_PK
		primary key(name, year, member_id),
	constraint photography_contest_FK
		foreign key(name,year),
	constraint nominee_FK
		foreign key(caption,reg_num),
	constraint members_FK
		foreign key(member_id),
);

create table nominate(
	name varchar(15) not null,
	year varchar(15) not null,
	member_id integer not null,
	score integer not null,
	comment varchar(15)  not null,
	caption varchar(50) not null,
	reg_num integer not null,
	constraint nominate_PK
		primary key(name,year,member_id,caption,reg_num),
	constraint members_FK
		foreign key(member_id),
	constraint nominee_FK
		foreign key(name, year, caption,reg_num),
	constraint photography_contest_FK
		foreign key(name, year),
);


create table award(
	award_name varchar(15) not null,
	prize integer not null,
	name varchar(15) not null,
	year integer not null,
	caption varchar(50) not null,
	reg_num integer not null,
	constraint award_PK
		primary key(award_name,name,year),
	constraint photography_contest_FK
		foreign key(name, year),
	constraint nominee_FK
		foreign key(name,year,caption,reg_num),
);

create table win(
	award_name varchar(15) not null,
	name varchar(15) not null,
	year integer not null,
	caption varchar(50) not null,
	reg_num integer not null,
	constraint win_PK
		primary key(award_name,name,year,reg_num,caption),
	constraint award_FK
		foreign key(name,year,award_name),
	constraint nominee_FK
		foreign key(name, year, caption,reg_num),
);





	


