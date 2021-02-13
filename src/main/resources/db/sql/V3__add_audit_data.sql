alter table banks
    add created_date timestamp;
alter table banks
    add modified_date timestamp;
alter table banks
    add created_by varchar;
alter table banks
    add modified_by varchar;

alter table currencies
    add created_date timestamp;
alter table currencies
    add modified_date timestamp;
alter table currencies
    add created_by varchar;
alter table currencies
    add modified_by varchar;