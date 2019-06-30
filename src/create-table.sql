create table banco
(
	id bigserial not null
		constraint banco_pkey
			primary key,
	nome varchar(255)
);

alter table banco owner to mitobank;

create table agencia
(
	id bigserial not null
		constraint agencia_pkey
			primary key,
	nome varchar(255),
	banco_id bigint
		constraint fkitd0jcpl1a6fqcn5acemi1e6l
			references banco
);

alter table agencia owner to mitobank;

create table conta
(
	id bigserial not null
		constraint conta_pkey
			primary key,
	digito integer,
	saldo numeric(19,2),
	senha varchar(4)
);

alter table conta owner to mitobank;

create table cliente
(
	id bigserial not null
		constraint cliente_pkey
			primary key,
	cpf varchar(255),
	email varchar(255),
	first_name varchar(255),
	last_name varchar(255),
	login varchar(255)
		constraint uk_p7phhm1xt3yxj5bl1rinpow1h
			unique,
	middle_name varchar(255),
	senha varchar(255),
	conta_id bigint
		constraint fkecelonnki00i82at8krt5ptjs
			references conta,
	agencia_id bigint
		constraint fke9pxr6osax8d82qby7j95lwkd
			references agencia
);

alter table cliente owner to mitobank;

create table historico
(
	id bigserial not null
		constraint historico_pkey
			primary key,
	dataehora timestamp not null,
	deferido boolean not null,
	obs varchar(255),
	saldo_antes numeric(19,2) not null,
	saldo_depois numeric(19,2) not null,
	tipo varchar(255),
	conta_id bigint
		constraint fk1egtyfd2bsgtc2oloetmelwux
			references conta
);

alter table historico owner to mitobank;


