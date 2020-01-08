INSERT INTO person(`id`, `name`, `age`, `year_of_birthday`, `month_of_birthday`,`day_of_birthday`) VALUES (1,'martin', 10, 1991,8,15);
INSERT INTO person(`id`, `name`, `age`, `year_of_birthday`, `month_of_birthday`,`day_of_birthday`) VALUES (2,'david', 9 , 1992,7,21);
INSERT INTO person(`id`, `name`, `age`, `year_of_birthday`, `month_of_birthday`,`day_of_birthday`) VALUES (3,'dennis', 8 ,1993,10,15);
INSERT INTO person(`id`, `name`, `age`, `year_of_birthday`, `month_of_birthday`,`day_of_birthday`) VALUES (4,'sophia', 7 , 1994,8,31);
INSERT INTO person(`id`, `name`, `age`, `year_of_birthday`, `month_of_birthday`,`day_of_birthday`) VALUES (5,'benny', 6 ,1995,12,23);

INSERT INTO block(`id`, `name`) VALUES (1,'dennis');
INSERT INTO block(`id`,`name`) VALUES (2,'sophia');

UPDATE person SET block_id = 1 WHERE id = 3;
UPDATE person SET block_id = 2 WHERE id = 4;