insert into role(id,name) values('admin','admin');
--insert into role(id,name) values('guest','guest');
--insert into role(id,name) values('allresource','allresource');
--insert into role(id,name) values('noresource','noresource');
--
insert into user(id,name,password,role_id) values('admin','admin','admin','admin');
--insert into user(id,name,password,role_id) values('guest','guest','guest','guest');
--insert into user(id,name,password,role_id) values('poweruser','poweruser','1','allresource');
--insert into user(id,name,password,role_id) values('nopoweruser','nopoweruser','1','noresource');
--
--insert into my_resource(id,number) values(200,50);
--insert into my_resource(id,number) values(201,80);
--insert into my_resource(id,number) values(202,100);
--
--
--insert into my_resource_range(id,filter,role_id) values(100,'number > 60 and number <90', 'admin');
--insert into my_resource_range(id,filter,role_id) values(101,'number < 60', 'admin');
--insert into my_resource_range(id,filter,role_id) values(102,'number < 60', 'guest');
--insert into my_resource_range(id,filter,role_id) values(103,'1 == 1','allresource');

------------------以下用于ACL测试-----------------------------------------------------------------------
-- class
--insert into acl_class (id, class) values (10, 'pw.ewen.WLPT.domain.entity.MyResourceRange');

-- SIDs
--insert into acl_sid (id, principal, sid) values (20, true, 'admin');
--insert into acl_sid (id, principal, sid) values (24, true, 'guest');
--insert into acl_sid (id, principal, sid) values (21, false, 'ROLE_guest');
--insert into acl_sid (id, principal, sid) values (22, false, 'ROLE_admin');
--insert into acl_sid (id, principal, sid) values (25, false, 'ROLE_allresource');
--insert into acl_sid (id, principal, sid) values (26, false, 'ROLE_noresource');


-- object identity
----number>60,admin
--insert into acl_object_identity (id,object_id_identity,object_id_class,parent_object,owner_sid,entries_inheriting) values (30,100, 10, null, 20, false);
----number<60,admin
--insert into acl_object_identity (id,object_id_identity,object_id_class,parent_object,owner_sid,entries_inheriting) values (31,101, 10, null, 20, false);
----number<60,guest
--insert into acl_object_identity (id,object_id_identity,object_id_class,parent_object,owner_sid,entries_inheriting) values (32,102, 10, null, 21, false);
----all
--insert into acl_object_identity (id,object_id_identity,object_id_class,parent_object,owner_sid,entries_inheriting) values (33,103, 10, null, 20, false);


-- ACE list
----admin -> ResourceRange:number>60,admin
--insert into acl_entry(acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)     values(30, 1, 20, 1, true, true, true);
----admin -> ResourceRange:number<60,admin
--insert into acl_entry(acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)     values(31, 1, 20, 1, true, true, true);
----guest -> ResourceRange:number<60,guest
--insert into acl_entry(acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)     values(32, 1, 21, 1, true, true, true);
----allresource -> ResourceRange:all
--insert into acl_entry(acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)     values(33, 1, 25, 1, true, true, true);
