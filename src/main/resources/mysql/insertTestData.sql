-- --------------------------- --
--          User Data          --
-- --------------------------- --
SET FOREIGN_KEY_CHECKS=0;
truncate table role;
SET FOREIGN_KEY_CHECKS=1;

insert into role (`role`) values ("ADMIN");
insert into role (`role`) values ("READONLY");
insert into role (`role`) values ("SUPERUSER");

drop procedure if exists load_user_test_data;

delimiter #
create procedure load_user_test_data()
	begin
		declare v_max int unsigned default 100;
		declare v_counter int unsigned default 0;
		declare v_userId varchar(8);
		declare v_userPrefix varchar(8) default (substring(replace(uuid(),'-',''),28,32));

		SET FOREIGN_KEY_CHECKS=0;
		truncate table user_role;
		truncate table user;
		SET FOREIGN_KEY_CHECKS=1;

		while v_counter < v_max do
			set v_userId = concat(v_userPrefix, cast(v_counter as char(3)));
			insert into user (`lanId`, `enabled`) values (v_userId, true);
			insert into user_role values ((select id from role where role.role = 'READONLY'), (select id from User where lanId = v_userId));
			if mod(v_counter, 10) = 0 then
				insert into user_role values ((select id from role where role.role = 'SUPERUSER'), (select id from User where lanId = v_userId));
			end if;
			if mod(v_counter, 25) = 0 then
				insert into user_role values ((select id from role where role.role = 'ADMIN'), (select id from User where lanId = v_userId));
			end if;
			set v_counter=v_counter+1;
		end while;
		insert into user (`lanId`, `enabled`) values ('M091355', true);
		insert into user_role values ((select id from role where role.role = 'ADMIN'), (select id from User where lanId = 'M091355'));
		commit;
	end #

delimiter ;

call load_user_test_data();

-- --------------------------- --
--         Keyword Data        --
-- --------------------------- --
SET FOREIGN_KEY_CHECKS=0;
truncate table keyword;
SET FOREIGN_KEY_CHECKS=1;

insert into keyword (`keyword`, `creator`) values ("Disease", 101);
insert into keyword (`keyword`, `creator`) values ("Medication", 101);
insert into keyword (`keyword`, `creator`) values ("Billing", 101);

-- --------------------------- --
--        Property Data        --
-- --------------------------- --
SET FOREIGN_KEY_CHECKS=0;
truncate table prop;
truncate table prop_val;
SET FOREIGN_KEY_CHECKS=1;

insert into prop (`property`, `governed`) values ("System", true);
insert into prop_val (`propertyId`,`value`, `governed`) values ((select p.id from prop p where p.property = 'System'), "DMS", true);
insert into prop_val (`propertyId`,`value`, `governed`) values ((select p.id from prop p where p.property = 'System'), "Sending Survey", true);
insert into prop_val (`propertyId`,`value`, `governed`) values ((select p.id from prop p where p.property = 'System'), "SIRS", true);

insert into prop (`property`, `governed`) values ("Status", true);
insert into prop_val (`propertyId`,`value`, `governed`) values ((select p.id from prop p where p.property = 'Status'), "Final", true);
insert into prop_val (`propertyId`,`value`, `governed`) values ((select p.id from prop p where p.property = 'Status'), "Testing", true);
insert into prop_val (`propertyId`,`value`, `governed`) values ((select p.id from prop p where p.property = 'Status'), "Under Development", true);

insert into prop (`property`, `governed`) values ("Title", false);

-- --------------------------- --
--       Definition Data       --
-- --------------------------- --
SET FOREIGN_KEY_CHECKS=0;
truncate table def;
truncate table def_kw;
truncate table def_vs;
truncate table def_cmnt;
truncate table def_prop;
SET FOREIGN_KEY_CHECKS=1;

SET @v_did = uuid();
insert into def values (@v_did, (select id from User where lanId = 'M091355'), now(), false);
insert into def_kw values ((select kw.id from keyword kw where kw.keyword = "Billing"), @v_did);
insert into def_vs values (@v_did, 'gender.value.set', '1');
insert into def_cmnt (`definitionId`,`author`, `comment`, `date`) values (@v_did, (select id from User where lanId = 'M091355'), 'Comment for number 1', now());
insert into def_prop values (@v_did, (select pv.id from prop_val pv where pv.value = "Under Development"));
insert into def_prop values (@v_did, (select pv.id from prop_val pv where pv.value = "DMS"));
insert into prop_val (`propertyId`,`value`) values ((select p.id from prop p where p.property = "Title"), "Gender Billing Definition");
insert into def_prop values(@v_did, (select pv.id from prop_val pv where pv.value = "Gender Billing Definition"));

SET @v_did = uuid();
insert into def values (@v_did, (select id from User where lanId = 'M091355'), now(), false);
insert into def_kw values ((select kw.id from keyword kw where kw.keyword = "Disease"), @v_did);
insert into def_vs values (@v_did, 'diabetes.value.set', '1');
insert into def_cmnt (`definitionId`,`author`, `comment`,`date`) values (@v_did, (select id from User where lanId = 'M091355'), 'Comment for number 2', now());
insert into def_prop values (@v_did, (select pv.id from prop_val pv where pv.value = "Testing"));
insert into def_prop values (@v_did, (select pv.id from prop_val pv where pv.value = "DMS"));
insert into prop_val (`propertyId`,`value`) values ((select p.id from prop p where p.property = "Title"), "Diabetes Disease Definition");
insert into def_prop values(@v_did, (select pv.id from prop_val pv where pv.value = "Diabetes Disease Definition"));

SET @v_did = uuid();
insert into def values (@v_did, (select id from User where lanId = 'M091355'), now(), false);
insert into def_kw values ((select kw.id from keyword kw where kw.keyword = "Disease"), @v_did);
insert into def_vs values (@v_did, 'hiv.value.set', '1');
insert into def_cmnt (`definitionId`,`author`,`comment`,`date`) values (@v_did, (select id from User where lanId = 'M091355'), 'Comment for number 3', now());
insert into def_prop values (@v_did, (select pv.id from prop_val pv where pv.value = "Testing"));
insert into def_prop values (@v_did, (select pv.id from prop_val pv where pv.value = "DMS"));
insert into prop_val (`propertyId`,`value`) values ((select p.id from prop p where p.property = "Title"), "HIV Disease Definition");
insert into def_prop values(@v_did, (select pv.id from prop_val pv where pv.value = "HIV Disease Definition"));