CREATE SEQUENCE user_master_seq
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 9223372036854775807
CACHE 1;

create table user_master(
	id_key bigint primary key not null,
	first_name character varying(255) Not NULL,
	last_name character varying(255) Not NULL,
	user_id character varying(255) Not NULL,
	mobile_no character varying(255) Not NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL
);


CREATE SEQUENCE account_master_seq
INCREMENT 1
START 1
MINVALUE 1
MAXVALUE 9223372036854775807
CACHE 1;

create table account_master(
	id_key bigint primary key not null,
	user_master_id_key bigint not null,
	balance numeric CHECK (balance >= 0.0),
	currency character varying(255) Not NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	CONSTRAINT fk_user_master_id_key FOREIGN KEY (user_master_id_key)
    REFERENCES user_master (id_key) MATCH SIMPLE
);

select * from user_master;
select * from account_master;