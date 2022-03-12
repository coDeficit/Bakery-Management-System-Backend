--Hash user passwords 
CREATE EXTENSION pgcrypto;

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Table structure for table 'org_meta'
--
drop table if exists org_meta CASCADE;
create table org_meta (
   org_name             varchar(254)         not null,
   phone                varchar(254)         not null,
   email                varchar(254)         default '',
   address1             varchar(254)         not null,
   address2             varchar(254)         default '',
   city                 varchar(254)         not null,
   state                varchar(254)         default '',
   zip                  varchar(254)         default '0000',
   country              varchar(254)         not null,
   timezone             varchar(254)         default '',
   website              varchar(254)         default '',
   image                bytea                null,
   description          text                 default '',
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            default CURRENT_TIMESTAMP,
   constraint pk_org_name primary key (org_name)
);

create unique index org_meta_pk on org_meta (
   org_name
);

--
-- Dumping data for table app_meta
--
insert into org_name (org_name, phone, email, address1, city, country, timezone) values
   ('Daily Bread BMS', '652119430', 'dailybreadbkry@gmail.com', 'Nkolanga, Awae', 'Yaounde', 'Cameroon', 'Africa/Douala');


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for Employee Management ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

---- Table structure for table jobs
--
drop table if exists jobs;
create table jobs (
   jobid                serial                    not null,
   job_title            varchar(254)              not null default '',
   job_desc             text                      default '',
   constraint pk_job primary key (jobid)
);

create unique index unq_job_title on jobs (
   job_title
);

--
-- Dumping data for table jobs
--
insert into jobs (job_title, job_desc) values
   ('Manager', 'Manages the day-in day-out activities of the Daily Bread Bakery'),
   ('IT Technician', 'Responsible for the technical aspects of the business and software');


-------------------------------------------------------------------------------------------------------------------
-- Table structure for table employees
--

drop table if exists employees CASCADE;
create table employees (
   employeeid           serial               not null,
   job                  int4                 not null,
   emp_fullname         varchar(254)         not null default '',
   emp_gender           char(1)              default '',
   emp_phone            varchar(254)         not null default '',
   emp_email            varchar(254)         default '',
   employ_type          varchar(254)         default '',
   emp_address1         varchar(254)         not null default '',
   emp_address2         varchar(254)         default '',
   emp_city             varchar(254)         not null default '',
   emp_state            varchar(254)         default '',
   emp_country          varchar(254)         not null default '',
   emp_salary           numeric(10, 0)       default 0,
   emp_image            varchar(254)         default '',
   emp_status           varchar(254)         default 'Current',
   emp_notes            text                 default '',
   emp_createdat        timestamp            default CURRENT_TIMESTAMP,
   emp_updatedat        timestamp            default CURRENT_TIMESTAMP,
   constraint pk_employee primary key (employeeid)
);

create unique index unq_emp_phone on employees (
   emp_phone
);

--
-- Dumping data for table employees
--
insert into employees (job, emp_fullname, emp_gender, emp_phone, emp_email, emp_address1, emp_city, emp_state, emp_country, emp_salary) values
   (2,'Fondem Princess', 'F', '652119430', 'fondempnkeng@gmail.com', 'Awae Escalier', 'Yaounde', 'Centre', 'Cameroon', 500000);




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
   constraint pk_shift_break primary key (shiftid, breakid)
);

--
-- Dumping data for table shift_breaks
--
insert into shift_breaks(shiftid, breakid, starttime, endtime) values 
   (1, 2, '2022-02-15 12:00:00.001', '2022-02-15 12:00:00.001');



----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for User Management ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

---- Table structure for table roles
--
drop table if exists roles CASCADE;
create table roles (
   roleid               serial               not null,
   role_name            varchar(254)         not null default '',
   permissions          text                 default '',
   role_desc            text                 default '',
   constraint pk_role primary key (roleid)
);

create unique index unq_role_name on roles (
   role_name
);

--
-- Dumping data for table roles
--
insert into roles (role_name, role_desc) values 
   ('Administrator', 'Has all rights'),
   ('Manager', 'Manages all Transactions, prepares reports,...');
   --('Clerk', 'Performs daily cash handling, POS usage, stocking, handles inquiries and orders pertaining to the bakery', 1 ),
   --('Cashier', 'Receives on behalf of the bakery, issues receipt to customers, prepares financial report at the end of every working week and handles all financial transaction on behalf of the bakery', 1 ),
--(4, 'Guest', '' );


-------------------------------------------------------------------------------------------------------------------
-- Table structure for table users
--

drop table if exists users CASCADE;
create table users (
   userid               serial               not null,
   employee             int4                 not null,
   role                 int4                 not null,
   username             varchar(254)         not null default '',
   password             text                 not null default '',
   user_state           boolean              default FALSE,
   user_createdat       timestamp            default CURRENT_TIMESTAMP,
   user_updatedat       timestamp            default CURRENT_TIMESTAMP,
   constraint pk_user primary key (userid)
);

create unique index user_name on users (
   username
);

--
-- Dumping data for table users
--
insert into users (employee, role, username, password) values
  (1, 1, 'fondem', 'adminPass');



----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for Customer Management ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

---- Table structure for table customers
--
drop table if exists customers CASCADE;
create table customers (
   customerid           serial               not null,
   fullname             varchar(254)         not null,
   gender               varchar(1)           default 'u',
   phone                varchar(254)         default '',
   email                varchar(254)         default '',
   address1             varchar(254)         default '',
   address2             varchar(254)         default '',
   city                 varchar(254)         default '',
   state                varchar(254)         default '',
   country              varchar(254)         default '',
   image                varchar(254)         default '',
   comments             text                 default '',
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            default CURRENT_TIMESTAMP,
   constraint pk_customer primary key (customerid)
);


--
-- Dumping data for table customers
--
insert into customers (fullname, gender, email) values
   ('Jane Doe', 'F', '1234fondem@gmail.com');



----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for Stock Management ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--
-- Table structure for table categories
--
drop table if exists categories CASCADE;
create table categories (
   categoryid           serial               not null,
   cat_name             varchar(254)         not null,
   cat_desc             text                 default '',
   constraint pk_category primary key (categoryid)
);

--
-- Dumping data for table categories
--
insert into categories(cat_name) values 
   ('Pastries'),
   ('Breads');



-------------------------------------------------------------------------------------------------------------------
---- Table structure for table items
--
drop table if exists items CASCADE;
create table items (
   itemid               serial               not null,
   categoryid           int4                 not null,
   item_name            varchar(254)         not null,
   item_color           varchar(16)          default '',
   item_image           varchar(254)         default '',
   cost_price           numeric(10, 0)       default 0,
   unit_price           numeric(10, 0)       not null,
   base_qty             numeric(10, 0)       default 0,
   current_qty          numeric(10, 0)       default 0,
   stock_level          numeric(10, 0)       default 0,
   sku                  varchar(14)          default '',
   item_desc            text                 default '',
   item_visible         boolean              default TRUE,
   item_avail           boolean              default FALSE,
   item_createdat        timestamp            default CURRENT_TIMESTAMP,
   item_updatedat        timestamp            default CURRENT_TIMESTAMP,
   constraint pk_item primary key (itemid)
);

--
-- Dumping data for table items
--
insert into items (categoryid, item_name, unit_price) values
   (2, 'Sugar balls', 100);


-------------------------------------------------------------------------------------------------------------------
---- Table structure for table stock_control
--
drop table if exists stock_control CASCADE;
create table stock_control (
   stck_ctrl_id         serial               not null,
   itemid               int4                 not null,
   saleid               int4                 null,
   old_qty              numeric(10, 0)       default 0,
   new_qty              numeric(10, 0)       default 0,
   reasons              text                 null,
   createdby            int4                 not null,
   updatedby            int4                 null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            null,
   constraint pk_stck_ctrl primary key (stck_ctrl_id)
);



----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for Sales Management ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--
-- Table structure for table pay_methods
--
drop table if exists pay_methods CASCADE;
create table pay_methods (
   pay_methodid        serial                not null,
   pay_name             varchar(254)         not null,
   pay_provider         varchar(14)          default '',
   pay_image            varchar(254)         default '',
   pay_desc             text                 default '',
   constraint pk_pay_method primary key (pay_methodid)
);

--
-- Dumping data for table pay_methods
--
insert into pay_methods(pay_name, pay_provider) values 
   ('Cash', ''),
   ('MTN MOMO', 'MTN'),
   ('Orange Money', 'Orange'),
   ('Debit Card', ''),
   ('Credit Card', ''),
   ('Other', '');


-------------------------------------------------------------------------------------------------------------------
-- Table structure for table order_status
--
drop table if exists order_status CASCADE;
create table order_status (
   order_statusid       serial               not null,
   status_name          varchar(254)         not null,
   status_color         varchar(16)          default '',
   status_desc          text                 default '',
   constraint pk_order_status primary key (order_statusid)
);

create unique index order_status_name on order_status (
   status_name
);

--
-- Dumping data for table order_status
--
insert into order_status(status_name, status_desc) values 
   ('Pending', 'Initial state of order. No action has been performed yet.'),
   ('Confirmed', 'Order has been confirmed by client but not paid for yet.'),
   ('Paid', 'Money exchanged and sale performed.'),
   ('Cancelled', 'order cancelled');


-------------------------------------------------------------------------------------------------------------------
-- Table structure for table orders
--
drop table if exists orders CASCADE;
create table orders (
   orderid              serial               not null,
   order_cust           int4                 not null,
   order_stat           varchar(254)         not null default 1,
   order_notes          text                 null,
   order_createdby      int4                 not null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            default CURRENT_TIMESTAMP,
   constraint pk_order primary key (orderid)
);


-------------------------------------------------------------------------------------------------------------------
-- Table structure for table order_items
--
drop table if exists order_items CASCADE;
create table order_items (
   orderid              int4                 not null,
   itemid               int4                 not null,
   quantity             numeric(10, 0)       default 0,
   discount             numeric(10, 2)       default 0.00,
   sub_total            numeric(10, 0)       null,
   total_price          numeric(10, 0)       null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            null,
   constraint pk_order_item primary key (orderid, itemid)
);



-------------------------------------------------------------------------------------------------------------------
-- Table structure for table sales
--
drop table if exists sales CASCADE;
create table sales (
   saleid               serial               not null,
   orderid              int4                 not null,
   pay_method_id        int4                 not null,
   payment              numeric(10, 0)       not null,
   change               numeric(10, 0)       null,
   notes                text                 null,
   createdby            int4                 not null,
   updatedby            int4                 null,
   createdat            timestamp            default CURRENT_TIMESTAMP,
   updatedat            timestamp            null,
   constraint pk_sale primary key (saleid)
);



----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------  Added for Foreign Key Constraints ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

---- Foreign Key structure for table org_meta
--
alter table org_meta
   add constraint fk_org_meta_assoc_employee foreign key (employeeid)
      references employees (employeeid)
      on delete CASCADE on update RESTRICT;


---- Foreign Key structure for table employees
--
alter table employees
   add constraint fk_employee_assoc_job foreign key (job)
      references jobs (jobid)
      on delete CASCADE on update RESTRICT;


---- Foreign Key structure for table shifts
--
alter table shifts
   add constraint fk_shift_assoc_employee foreign key (employeeid)
      references employees (employeeid)
      on delete CASCADE on update RESTRICT;
      

---- Foreign Key structure for table shift_breaks
--
alter table shift_breaks
   add constraint fk_shift_break_assoc_shift foreign key (shiftid)
      references shifts (shiftid)
      on delete CASCADE on update RESTRICT;

alter table shift_breaks
   add constraint fk_shift_break_assoc_break foreign key (breakid)
      references breaks (breakid)
      on delete CASCADE on update RESTRICT;


---- Foreign Key structure for table users
--
alter table users
   add constraint fk_user_assoc_role foreign key (role)
      references roles (roleid)
      on delete CASCADE on update RESTRICT;

alter table users
   add constraint fk_user_assoc_employee foreign key (employee)
      references employees (employeeid)
      on delete CASCADE on update RESTRICT;


---- Foreign Key structure for table items
--
alter table items
   add constraint fk_item_assoc_cat foreign key (categoryid)
      references categories (categoryid)
      on delete CASCADE on update RESTRICT;


---- Foreign Key structure for table stock_control
--
alter table stock_control
   add constraint fk_stck_ctrl_assoc_item foreign key (itemid)
      references items (itemid)
      on delete CASCADE on update RESTRICT;

alter table stock_control
   add constraint fk_stck_ctrl_assoc_sale foreign key (saleid)
      references sales (saleid)
      on delete CASCADE on update RESTRICT;

alter table stock_control
   add constraint fk_stck_ctrl_assoc_user foreign key (createdby)
      references users (userid)
      on delete CASCADE on update RESTRICT;

alter table stock_control
   add constraint fk_stck_ctrl_assoc_user foreign key (updatedby)
      references users (userid)
      on delete CASCADE on update RESTRICT;


---- Foreign Key structure for table orders
--
alter table orders
   add constraint fk_order_assoc_customer foreign key (customerid)
      references customers (customerid)
      on delete CASCADE on update RESTRICT;

alter table orders
   add constraint fk_order_assoc_state foreign key (state_code)
      references order_states (state_code)
      on delete CASCADE on update RESTRICT;

alter table orders
   add constraint fk_order_assoc_user foreign key (createdby)
      references users (userid)
      on delete CASCADE on update RESTRICT;

alter table orders
   add constraint fk_order_assoc_user foreign key (updatedby)
      references users (userid)
      on delete CASCADE on update RESTRICT;


---- Foreign Key structure for table sales
--
alter table sales
   add constraint fk_sale_assoc_order foreign key (orderid)
      references orders (orderid)
      on delete CASCADE on update RESTRICT;

alter table sales
   add constraint fk_sale_assoc_pay foreign key (pay_method_id)
      references pay_methods (pay_method_id)
      on delete CASCADE on update RESTRICT;

alter table sales
   add constraint fk_sale_assoc_user foreign key (createdby)
      references users (userid)
      on delete CASCADE on update RESTRICT;

alter table sales
   add constraint fk_sale_assoc_user foreign key (updatedby)
      references users (userid)
      on delete CASCADE on update RESTRICT;



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

---- Tigger structure for table org_meta
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON org_meta
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

---- Tigger structure for table employees
--

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.emp_updatedat = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

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


---- Tigger structure for table users
--
CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.user_updatedat = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

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


---- Tigger structure for table categories
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON categories
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


---- Tigger structure for table orders
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON orders
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table order_items
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON order_items
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();


---- Tigger structure for table sales
--
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON sales
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();
