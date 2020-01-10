INSERT INTO person(`id`, `name`, `year_of_birthday`, `month_of_birthday`,`day_of_birthday`,`job`) VALUES (1,'martin', 1991,8,15, 'programmer');
INSERT INTO person(`id`, `name`, `year_of_birthday`, `month_of_birthday`,`day_of_birthday`) VALUES (2,'david', 1992,7,21);
INSERT INTO person(`id`, `name`, `year_of_birthday`, `month_of_birthday`,`day_of_birthday`) VALUES (3,'dennis',1993,10,15);
INSERT INTO person(`id`, `name`, `year_of_birthday`, `month_of_birthday`,`day_of_birthday`) VALUES (4,'sophia', 1994,8,31);
INSERT INTO person(`id`, `name`, `year_of_birthday`, `month_of_birthday`,`day_of_birthday`) VALUES (5,'benny',1995,12,23);

INSERT INTO block(`id`, `name`) VALUES (1,'dennis');
INSERT INTO block(`id`,`name`) VALUES (2,'sophia');

UPDATE person SET block_id = 1 WHERE id = 3;
UPDATE person SET block_id = 2 WHERE id = 4;