create table client
(
    id         bigint identity
        constraint client_pk
        primary key,
    name       varchar(200)       not null,
    active     bit      default 1 not null,
    created_at datetime default getdate(),
    updated_at datetime,
    last_name  varchar(500)       not null,
    document   varchar(8)         not null,
    phone      varchar(30)        not null,
    email      varchar(100)       not null
)
    go

create unique index client_id_uindex
    on client (id)
    go

create table invoice
(
    id         bigint identity
        constraint invoice_pk
        primary key,
    client_id  bigint not null
        constraint invoice_client_id_fk
            references client,
    created_at datetime,
    updated_at datetime
)
    go

create unique index invoice_id_uindex
    on invoice (id)
    go

create table invoice_header
(
    invoice_id bigint         not null
        constraint invoice_header_invoice_id_fk
            references invoice,
    created_at datetime       not null,
    updated_at datetime,
    total      decimal(10, 2) not null,
    id         bigint identity
        constraint invoice_header_pk
        primary key
)
    go

create unique index invoice_header_id_uindex
    on invoice_header (id)
    go

create table product
(
    id         bigint identity
        constraint product_pk
        primary key,
    name       varchar(200)   not null,
    active     bit default 1  not null,
    created_at datetime,
    updated_at datetime,
    price      decimal(10, 2) not null
)
    go

create table invoice_detail
(
    id                bigint identity
        constraint invoice_detail_pk
        primary key,
    invoice_header_id bigint         not null
        constraint invoice_detail_invoice_header_id_fk
            references invoice_header,
    product_id        bigint         not null
        constraint invoice_detail_product_id_fk
            references product,
    quantity          int            not null,
    ammount           decimal(10, 2) not null
)
    go

create unique index invoice_detail_id_uindex
    on invoice_detail (id)
    go

create unique index product_id_uindex
    on product (id)
    go

create procedure db_maintenance.createinvoce @clientId bigint, @total decimal(10,2)
as
    SET NOCOUNT ON
declare @invoiceId bigint
    declare @invoiceHeaderId bigint
    insert into db_maintenance.invoice(client_id, created_at) values (@clientId,getdate());
    set @invoiceId = @@IDENTITY
    insert into db_maintenance.invoice_header(invoice_id, created_at, total) values (@invoiceId,getdate(),@total);
    set @invoiceHeaderId = @@IDENTITY
select 'invoiceHeaderId' = @invoiceHeaderId
    go

