--Hash user passwords 
CREATE EXTENSION pgcrypto;

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Table structure for table 'app_meta'
--
drop table if exists app_meta CASCADE;
create table app_meta (
   meta_key                 varchar(254)         not null,
   value                    varchar(254)         not null,
   constraint pk_app_meta primary key (meta_key)
);

create unique index app_meta_pk on app_meta (
meta_key
);

--
-- Dumping data for table app_meta
--
insert into app_meta (meta_key, value) values
   ('address', 'Nkolanga, Awae'),
   ('city', 'Yaounde'),
   ('country', 'Cameroon'),
   ('business', 'Daily Bread BMS'),
   ('email', 'fondempnkeng@gmail.com'),
   ('fax', ''),
   ('phoneCode', '237'),
   ('phone', '652119430'),
   ('timezone', 'Africa/Douala'),
   ('website', ''),
   ('logo', '');


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for Employee Management ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

---- Table structure for table jobs
--
drop table if exists jobs CASCADE;
create table jobs (
   jobid                serial                    not null,
   title                varchar(254)              not null,
   description          text                      null,
   createdat            timestamp                 default CURRENT_TIMESTAMP,
   updatedat            timestamp                 null,
   constraint pk_job primary key (jobid)
);

create unique index job_title on jobs (
   title
);

--
-- Dumping data for table jobs
--
insert into jobs (title, description) values
   ('Manager', 'Manages the day-in day-out activities of the Daily Bread Bakery'),
   ('IT Technician', 'Responsible for the technical aspects of the business and software');


-------------------------------------------------------------------------------------------------------------------
-- Table structure for table emptypes
--
drop table if exists emptypes CASCADE;
create table emptypes (
   emptypeid            serial                    not null,
   name                 varchar(254)              not null,
   notes                text                      null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat           timestamp            null,
   constraint pk_emptype primary key (emptypeid)
);

create unique index emptype_title on emptypes (
   name
);

--
-- Dumping data for table emptypes
--
insert into emptypes (name, notes) values
   ('Full-time', ''),
   ('Part-time', ''),
   ('Casual', ''),
   ('Contract', ''),
   ('Trainee', ''),
   ('Commission', '');


-------------------------------------------------------------------------------------------------------------------
-- Table structure for table employees
--

drop table if exists employees CASCADE;
create table employees (
   employeeid           serial               not null,
   jobid                int4                 not null,
   emptypeid            int4                 not null,
   firstname            varchar(254)         not null,
   lastname             varchar(254)         not null,
   gender               varchar(1)           default null,
   phonenumber          varchar(254)         not null,
   email                varchar(254)         null,
   address1             varchar(254)         not null,
   address2             varchar(254)         null,
   city                 varchar(254)         not null,
   state                varchar(254)         null,
   zip                  varchar(254)         default '0000',
   country              varchar(254)         not null,
   salary               numeric              null,
   image                bytea                null,
   status               varchar(254)         default 'Current',
   notes                text                 null,
   createdby            int4                 not null,
   updatedby            int4                 null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            null,
   constraint pk_employee primary key (employeeid)
);

create unique index emp_phonenumber on employees (
   phonenumber
);

create  index association1_fk on employees (
   jobid
);

create  index association2_fk on employees (
   emptypeid
);

--
-- Dumping data for table employees
--
insert into employees (jobid, emptypeid, firstname, lastname, gender, phonenumber, email, address1, address2, city, state, country, salary, createdby) values
   (2, 4, 'Jane', 'Doe', 'F', '655555555', 'fondempnkeng@gmail.com', 'Awae Escalier', '', 'Yaounde', 'Centre', 'Cameroon', 500000, 1);


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for Employee Presence Management ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

---- Table structure for table shifts
--
drop table if exists shifts CASCADE;
create table shifts (
   shiftid              serial               not null,
   employeeid           int4                 not null,
   period               varchar(254)         default 'Day',
   clockin              timestamp            default CURRENT_TIMESTAMP,
   clockout             timestamp            null,
   status               varchar(254)         default 'In progress',
   notes                text                 null,
   createdby            int4                 not null,
   updatedby            int4                 null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat           timestamp            null,
   constraint pk_shift primary key (shiftid)
);

create unique index shift_pk on shifts (
   shiftid
);

create  index association1_fk on shifts (
   employeeid
);

--
-- Dumping data for table shifts
--
insert into shifts (employeeid, period, clockin, clockout, status, createdby) values 
   (1, 'day','2022-02-15 07:30:00.001', '2022-02-15 16:30:00.001', 'Completed', 1);


-------------------------------------------------------------------------------------------------------------------
-- Table structure for table breaks
--
drop table if exists breaks CASCADE;
create table breaks (
   breakid              serial               not null,
   name                 varchar(254)         not null,
   visibile             boolean              not null,
   notes                text                 null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat           timestamp             null,
   constraint pk_break primary key (breakid)
);

create unique index break_name on breaks (
   name
);

--
-- Dumping data for table breaks
--
insert into breaks (name, visibile) values 
   ('Breakfast Break', TRUE),
   ('Lunch Break', TRUE),
   ('Mid Break', TRUE),
   ('Dinner Break', TRUE);

-------------------------------------------------------------------------------------------------------------------
-- Table structure for table shift_breaks
--
drop table if exists shift_breaks CASCADE;
create table shift_breaks (
   shiftid              int4                 not null,
   breakid              int4                 not null,
   starttime            timestamp            null,
   endtime              timestamp            null,
   createdby            int4                 not null,
   updatedby            int4                 null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            null,
   constraint pk_shift_break primary key (shiftid, breakid)
);

--
-- Dumping data for table shift_breaks
--
insert into shift_breaks(shiftid, breakid, starttime, endtime, createdby) values 
   (1, 2, '2022-02-15 12:00:00.001', '2022-02-15 12:00:00.001', 1);



----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for User Management ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

---- Table structure for table roles
--

drop table if exists roles CASCADE;
create table roles (
   roleid               serial               not null,
   name                 varchar(254)         not null,
   permissions          text                 null,
   description          text                 default null,
   createdby            int4                 not null,
   updatedby            int4                 null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            null,
   constraint pk_role primary key (roleid)
);

create unique index role_name on roles (
name
);

--
-- Dumping data for table roles
--
insert into roles (name, permissions, description, createdby) values 
   ('Administrator', '', 'Has all rights', 1),
   ('Manager', '', 'Manages all activities, prepares reports, e.t.c', 1 );
   --('Clerk', 'Performs daily cash handling, POS usage, stocking, handles inquiries and orders pertaining to the bakery', 1 ),
   --('Cashier', 'Receives on behalf of the bakery, issues receipt to customers, prepares financial report at the end of every working week and handles all financial transaction on behalf of the bakery', 1 ),
--(4, 'Guest', '' );


-------------------------------------------------------------------------------------------------------------------
-- Table structure for table users
--

drop table if exists users CASCADE;
create table users (
   userid               serial               not null,
   employeeid           int4                 not null,
   roleid               int4                 not null,
   username             varchar(254)         not null,
   password             text                 not null,
   status               varchar(254)         default 'Activated',
   visible              boolean              default TRUE,
   createdby            int4                 not null,
   updatedby            int4                 null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            null,
   constraint pk_user primary key (userid)
);

create unique index user_name on users (
   username
);

create  index association1_fk on users (
   employeeid
);

create  index association2_fk on users (
   roleid
);

--
-- Dumping data for table users
--
insert into users (employeeid, roleid, username, password, createdby) values
  (1, 1, 'fondem', crypt('adminPass', gen_salt('bf', 8) ), 1 );

-- insert into users (employeeid, roleid, username, password, createdby) values
--   (1, 2, 'Nguh', crypt('adminPass', gen_salt('bf', 8) ), 1 );

-- select u.* from users u left join users c on u.userid = c.userid where c.userid = 1;



----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for Customer Management ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

---- Table structure for table customers
--
drop table if exists customers CASCADE;
create table customers (
   customerid           serial               not null,
   fullname             varchar(254)         not null,
   gender               varchar(1)           default null,
   phonenumber          varchar(254)         null,
   email                varchar(254)         null,
   address1             varchar(254)         null,
   address2             varchar(254)         null,
   city                 varchar(254)         null,
   state                varchar(254)         null,
   zip                  varchar(254)         default '0000',
   country              varchar(254)         null,
   image                bytea                null,
   comments             text                 null,
   createdby            int4                 not null,
   updatedby            int4                 null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            null,
   constraint pk_customer primary key (customerid)
);

create unique index cust_fullname on customers (
   fullname
);

create  index association1_fk on customers (
   createdby
);

create  index association2_fk on customers (
   updatedby
);

--
-- Dumping data for table customers
--
insert into customers (fullname, gender, email, createdby) values
   ('Jane Doe', 'F', '1234fondem@gmail.com', 1);

--
-- View structure for table customers
--

drop view if exists cust_items CASCADE;
create or replace view cust_items as select * from customers;



----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for Stock Management ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


--
-- Table structure for table categories
--
drop table if exists categories CASCADE;
create table categories (
   categoryid           int4                 not null,
   name                 varchar(254)         not null,
   description          text                 null,
   constraint pk_category primary key (categoryid)
);

create unique index cat_name on categories (
   name
);

--
-- Dumping data for table categories
--
insert into categories(name) values 
   ('Pastries'),
   ('Breads');


-------------------------------------------------------------------------------------------------------------------
---- Table structure for table units
--
drop table if exists units CASCADE;
create table units (
   unitid               int4                 not null,
   code                 varchar(254)         default 'whole',
   value                varchar(254)         default '',
   constraint pk_unit primary key (unitid)
);

create unique index unit_code on units (
   code
);

--
-- Dumping data for table unit
--
insert into units (code, value) values 
   ('whole', ''),
   ('kilograms', 'kg'),
   ('pounds', 'ib'),
   ('inches', 'in');


-------------------------------------------------------------------------------------------------------------------
---- Table structure for table items
--
drop table if exists items CASCADE;
create table items (
   itemid               serial               not null,
   -- supplierid           int4                 null,
   categoryid           int4                 null,
   unitid               int4                 default 1,
   name                 varchar(254)         not null,
   color_ref            varchar(16)          null,
   image                bytea                null,
   cost_price           numeric(10, 0)       null,
   unit_price           numeric(10, 0)       not null,
   stock_volume         numeric(10,2)        default 0.00,
   current_vol          numeric(10,2)        default 0.00,
   stock_level          numeric(10,2)        default 0.00,
   sku                  varchar(14)          null,
   description          text                 null,
   visible              boolean              default TRUE,
   available            boolean              default FALSE,
   createdby            int4                 not null,
   updatedby            int4                 null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            null,
   constraint pk_item primary key (itemid)
);

create unique index item_sku on items (
   sku
);


--
-- Dumping data for table items
--
insert into items (name, unit_price, createdby) values
   ('Sugar balls', 100, 1);

--
-- View structure for table items
--


-------------------------------------------------------------------------------------------------------------------
---- Table structure for table stock_control
--
drop table if exists stock_control CASCADE;
create table stock_control (
   stck_ctrl_id         serial               not null,
   itemid               int4                 not null,
   transid              int4                 null,
   old_volume           numeric(10,2)        null,
   new_volume           numeric(10,2)        null,
   reasons              text                 null,
   createdby            int4                 not null,
   updatedby            int4                 null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            null,
   constraint pk_stck_ctrl primary key (stck_ctrl_id)
);

--
-- Dumping data for table stock_control
--

--
-- View structure for table stock_control
--


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for Foreign Key Constraints ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

---- Foreign Key structure for table employees
--
alter table employees
   add constraint fk_employee_assoc_job foreign key (jobid)
      references jobs (jobid)
      on delete CASCADE on update CASCADE;

alter table employees
   add constraint fk_employee_assoc_emptype foreign key (emptypeid)
      references emptypes (emptypeid)
      on delete CASCADE on update CASCADE;
      
alter table employees
   add constraint fk_employee_assoc_user foreign key (createdby)
      references users (userid)
      on delete CASCADE on update CASCADE;


---- Foreign Key structure for table shifts
--
alter table shifts
   add constraint fk_shift_assoc_employee foreign key (employeeid)
      references employees (employeeid)
      on delete CASCADE on update CASCADE;

alter table shifts
   add constraint fk_employee_assoc_user foreign key (createdby)
      references users (userid)
      on delete CASCADE on update CASCADE;
      

---- Foreign Key structure for table shift_breaks
--
alter table shift_breaks
   add constraint fk_shift_break_assoc_shift foreign key (shiftid)
      references shifts (shiftid)
      on delete CASCADE on update CASCADE;

alter table shift_breaks
   add constraint fk_shift_break_assoc_break foreign key (breakid)
      references breaks (breakid)
      on delete CASCADE on update CASCADE;
      
alter table shift_breaks
   add constraint fk_shift_break_assoc_user foreign key (createdby)
      references users (userid)
      on delete CASCADE on update CASCADE; 


---- Foreign Key structure for table users
--
alter table users
   add constraint fk_user_assoc_employee foreign key (employeeid)
      references employees (employeeid)
      on delete CASCADE on update CASCADE;

alter table users
   add constraint fk_user_assoc_role foreign key (roleid)
      references roles (roleid)
      on delete CASCADE on update CASCADE;
      
alter table users
   add constraint fk_user_assoc_user foreign key (createdby)
      references users (userid)
      on delete CASCADE on update CASCADE;


---- Foreign Key structure for table customers
--
alter table customers
   add constraint fk_cust_assoc_user foreign key (createdby)
      references users (userid)
      on delete CASCADE on update CASCADE;

alter table customers
   add constraint fk_cust_assoc_user foreign key (updatedby)
      references users (userid)
      on delete CASCADE on update CASCADE;


---- Foreign Key structure for table items
--
alter table items
   add constraint fk_item_assoc_cat foreign key (categoryid)
      references categories (categoryid)
      on delete CASCADE on update CASCADE;

alter table items
   add constraint fk_item_assoc_unit foreign key (unitid)
      references units (unitid)
      on delete CASCADE on update CASCADE;

alter table items
   add constraint fk_item_assoc_user foreign key (createdby)
      references users (userid)
      on delete CASCADE on update CASCADE;

alter table items
   add constraint fk_item_assoc_user foreign key (updatedby)
      references users (userid)
      on delete CASCADE on update CASCADE;


---- Foreign Key structure for table stock_control
--

alter table stock_control
   add constraint fk_item_assoc_item foreign key (itemid)
      references items (itemid)
      on delete CASCADE on update CASCADE;

alter table stock_control
   add constraint fk_item_assoc_user foreign key (createdby)
      references users (userid)
      on delete CASCADE on update CASCADE;

alter table stock_control
   add constraint fk_item_assoc_user foreign key (updatedby)
      references users (userid)
      on delete CASCADE on update CASCADE;



----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for Automatic Data Date Management ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updatedat = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;


---- Tigger structure for table jobs
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON jobs
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table emptypes
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON emptypes
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table employees
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON employees
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table shifts
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON shifts
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table breaks
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON breaks
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table shift_breaks
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON shift_breaks
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table roles
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON roles
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table users
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table customers
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON customers
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table items
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON items
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table stock_control
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON stock_control
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();
