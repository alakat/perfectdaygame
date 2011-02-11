create table usuario(login varchar(255), pass varchar(255), id int primary key);
create table myvTitulo(id int primary key,titulo varchar(255));
create table unidadPeso(id int primary key,titulo varchar(255));
create table categoria(id int primary key,titulo varchar(255));
create table myvItem(id int primary key,cantidad double,idMyVItem int, idUnidad int, idProducto int);
create table productos(id int primary key,titulo varchar(255),  hidratos_carbono double,kilocalorias double, proteinas double, grasas double, idCategoria int, idPais int, idUsuario int);
create table pais(id int primary key, nombre varchar(255));
insert into categoria values (0,'CEREALES Y DERIVADOS');
insert into categoria values (1,'CARNES');
insert into categoria values (2,'PESCADOS');
insert into categoria values (3,'HUEVOS');
insert into categoria values (4,'LEGUMBRES');
insert into categoria values (5,'HORTALIZAS Y VERDURAS');
insert into categoria values (6,'LACTEOS Y DERIVADOS');
insert into categoria values (7,'GRASAS');
insert into categoria values (8,'SUPLEMENTOS');
insert into categoria values (9,'CHOCOLATES Y DULCES');
insert into unidadPeso values (0,'GRAMO (g)');
insert into unidadPeso values (1,'MILIGRAMO (mg)');
insert into unidadPeso values (2,'MICROGRAMO (ug)');
insert into unidadPeso values (3,'TRAZA (t)');
insert into unidadPeso values (4,'KILOCALORIAS (kcal)');
insert into myvTitulo values (0,'VITAMINA A');
insert into myvTitulo values (1,'VITAMINA C');
insert into myvTitulo values (2,'VITAMINA D');
insert into myvTitulo values (3,'VITAMINA E');
insert into myvTitulo values (4,'VITAMINA K');
insert into myvTitulo values (5,'VITAMINA B1');
insert into myvTitulo values (6,'VITAMINA B2');
insert into myvTitulo values (7,'NIACINA');
insert into myvTitulo values (8,'VITAMINA B6');
insert into myvTitulo values (9,'VITAMINA B12');
insert into myvTitulo values (10,'BIOTINA');
insert into myvTitulo values (11,'AC PANTOTENICO');
insert into myvTitulo values (12,'CALCIO Ca');
insert into myvTitulo values (13,'MAGNESIO Mg');
insert into myvTitulo values (14,'HIERRO Fe');
insert into myvTitulo values (15,'COBRE Co');
insert into myvTitulo values (16,'YODO I');
insert into myvTitulo values (17,'ZINC Zn');
insert into myvTitulo values (18,'MANGANESO Mn');
insert into myvTitulo values (19,'POTASIO K');
insert into myvTitulo values (20,'SELENIO Se');
insert into myvTitulo values (21,'CROMO Cr');
insert into myvTitulo values (22,'MOLIBDENO Mo');
insert into myvTitulo values (23,'CLORO Cl');
insert into myvTitulo values (24,'FIBRA');
insert into usuario values ('betty','betty',0);
insert into usuario values ('celia','celia',1);
insert into pais values(0,'España');
insert into pais values(1,'Alemania');
insert into pais values(2,'EEUU');