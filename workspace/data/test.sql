insert into user (id, created, email, enabled, first_name, is_admin, last_name, password)
values ( 1, '2017/11/08', 'test@sporticus.com', true, 'test', true, 'last', '$2a$10$nqVxG8EUTLx4.RZp4d32XudHeXWn5esUe.cjxwB2Rcl1cNUKBEf4C');

insert into organisation (id, created, name, owner_id, enabled, domain, address)
values ( 1, '2017/11/08', 'Northcroft Leisure', 1, 1, "northcroft.co.uk", "Newbury");


-- clear groups and memberships (if you need to!)

delete from group_member;
delete from groupings;

-- Groupings (groups)

insert into grouping (id, created, description, enabled, name, owner_organisation_id, type)
values ( 1, '2017/11/08', 'Test Group', 1, "Test1", 1, "LADDER");

insert into grouping (id, created, description, enabled, name, owner_organisation_id, type)
values ( 2, '2017/11/08', 'Test Group2', 1, "Test2", 1, "LADDER");


-- Group members

insert into group_member (id, accepted_or_rejected_date, created, enabled, group_id, permissions, status, user_id)
values ( 1, '2017/11/08', '2017/11/08', 1, 1, 2, 1, 1);



