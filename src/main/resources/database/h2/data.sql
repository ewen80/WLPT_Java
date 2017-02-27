insert into role(id,name) values('admin','admin')
insert into role(id,name) values('guest','guest')
insert into user(id,name,password,role_id) values('admin','admin','admin','admin')
insert into user(id,name,password,role_id) values('guest','guest','guest','guest')
insert into resource(id,type,number) values(100,'resource1',50)
insert into resource(id,type,number) values(101,'resource2',100)

------------------以下用于ACL测试-----------------------------------------------------------------------
-- class
insert into acl_class (id, class) values (10, 'pw.ewen.WLPT.entity.Resource');

-- SIDs
insert into acl_sid (id, principal, sid) values (20, true, 'admin');
insert into acl_sid (id, principal, sid) values (24, true, 'guest');
insert into acl_sid (id, principal, sid) values (21, false, 'ROLE_guest');
insert into acl_sid (id, principal, sid) values (22, false, 'ROLE_admin');


-- object identity
insert into acl_object_identity (id,object_id_identity,object_id_class,parent_object,owner_sid,entries_inheriting) values (30,100, 10, null, 20, false);

insert into acl_object_identity (id,object_id_identity,object_id_class,parent_object,owner_sid,entries_inheriting) values (31,101, 10, null, 20, false);

--insert into acl_object_identity (id,object_id_identity,object_id_class,parent_object,owner_sid,entries_inheriting) --values (32,102, 10, null, 20, false);


-- ACE list
-- mask == R
insert into acl_entry(acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)     values(30, 1, 20, 1, true, true, true);

-- custom permission
--insert into acl_sid (id, principal, sid) values (23, true, 'admin1@example.com');
--insert into acl_entry(acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)   values(31, 1, 23, 32, true, true, true);