/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.21 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `user` (
	`username` varchar (150),
	`id` int (50),
	`password` varchar (150),
	`aae001` varchar (3),
	`aae036` date ,
	`isrelative` varchar (3),
	`relativeuser` varchar (150),
	`relativeweb` varchar (30)
); 
insert into `user` (`username`, `id`, `password`, `aae001`, `aae036`, `isrelative`, `relativeuser`, `relativeweb`) values('admin','1','VsyrVxgJ2Dug56qt1Jn33g==','1','2017-09-06',NULL,NULL,NULL);
insert into `user` (`username`, `id`, `password`, `aae001`, `aae036`, `isrelative`, `relativeuser`, `relativeweb`) values('luotao','2',NULL,'1','2017-09-15','1','luotao','1');
