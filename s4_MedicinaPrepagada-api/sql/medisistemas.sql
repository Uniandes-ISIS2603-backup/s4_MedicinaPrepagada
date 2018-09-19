delete from ConsultorioEntity;
delete from SedeEntity;

delete from HorarioAtencionEntity;
delete from MedicoEntity;
delete from EspecialidadEntity;

delete from PACIENTEENTITY;
delete from TARJETACREDITOENTITY;


insert into EspecialidadEntity (nombre) values ('Endocrinologia');

insert into EspecialidadEntity (nombre) values ('Cardiologia');

insert into SedeEntity (id, nombre, direccion, tipoSede, descripcion, latitud, longitud, telefono, correo) 
values (100, 'super sede 1', 'Calle 153 # 15 - 13', 2, 'Super buena sede', 2, -69.2, 98999423, 'pepito12@hotmail.com' );

insert into SedeEntity (id, nombre, direccion, tipoSede, descripcion, latitud, longitud, telefono, correo)
values (200, 'super sede 2', 'Calle 133 # 90 - 01', 1, 'Super buena sede mega', 1.3, -70.19, 98761233, 'pepitoJav@hotmail.com' );

insert into SedeEntity (id, nombre, direccion, tipoSede, descripcion, latitud, longitud, telefono, correo) 
values (300, 'super sede 3', 'Calle 53 # 14 - 12', 2, 'Super buena sede superx2', 2.1, -69.4, 98065423, 'pepitoSuperman@gmail.com' );

insert into ConsultorioEntity (id, edificio, noficina, sede_id, especialidad_nombre)
values (100, 'lleras' , 2, 100, 'Cardiologia');

insert into ConsultorioEntity (id, edificio, noficina, sede_id , especialidad_nombre)
values (200, 'Camargo' , 3, 100, 'Cardiologia');

insert into ConsultorioEntity (id, edificio, noficina, sede_id , especialidad_nombre)
values (300, 'Alberto' , 1, 200, 'Cardiologia');

insert into ConsultorioEntity (id, edificio, noficina, sede_id , especialidad_nombre)
values (400, 'Julio' , 5, 200, 'Endocrinologia');

insert into ConsultorioEntity (id, edificio, noficina, sede_id , especialidad_nombre)
values (500, 'Principal' , 1, 300, 'Endocrinologia');

insert into ConsultorioEntity (id, edificio, noficina, sede_id , especialidad_nombre)
values (600, 'Samuel' , 7, 300, 'Endocrinologia');

insert into ConsultorioEntity (id, edificio, noficina, sede_id , especialidad_nombre)
values (700, 'Adios' , 20, 300, 'Endocrinologia');

INSERT INTO PACIENTEENTITY (ID,DOCUMENTOIDENTIDAD,NOMBRE,DIRECCION,FECHANACIMIENTO,EPS,MAIL,NUMEROCONTACTO,CONTRASENA,LOGIN,TIPOUSUARIO)
values(1,1234564,'Alfredo Torres Figueroa','Cra 18 #124-80','21/11/1997','Compensar','a.torres123@gmail.com',3219896969,'ale2C','a.torres','Paciente');

INSERT INTO PACIENTEENTITY (ID,DOCUMENTOIDENTIDAD,NOMBRE,DIRECCION,FECHANACIMIENTO,EPS,MAIL,NUMEROCONTACTO,CONTRASENA,LOGIN,TIPOUSUARIO)
values(2,1234789,'Nicolas Novoa Arciniegas','Cra 21 #70-90','31/10/1997','Coomeva','n.novos@gmial.com',890896969,'perrito','n.nov','Paciente');

INSERT INTO PACIENTEENTITY (ID,DOCUMENTOIDENTIDAD,NOMBRE,DIRECCION,FECHANACIMIENTO,EPS,MAIL,NUMEROCONTACTO,CONTRASENA,LOGIN,TIPOUSUARIO)
values(3,1272039,'pepito Perez Moreno','Cll 64#13-90','19/02/1998','Coomeva','pperezs@gmial.com',56885886,'perrito2','peerex','Paciente');

INSERT INTO PACIENTEENTITY (ID,DOCUMENTOIDENTIDAD,NOMBRE,DIRECCION,FECHANACIMIENTO,EPS,MAIL,NUMEROCONTACTO,CONTRASENA,LOGIN,TIPOUSUARIO)
values(4,127789686,'Mario Laserna Alv','Cra 57#70-90','08/10/1965','Coomeva','ml@gmial.com',889768996,'givvu7897','mhhkgg','Paciente');

INSERT INTO PACIENTEENTITY (ID,DOCUMENTOIDENTIDAD,NOMBRE,DIRECCION,FECHANACIMIENTO,EPS,MAIL,NUMEROCONTACTO,CONTRASENA,LOGIN,TIPOUSUARIO)
values(5,1739221,'Carlos Montoya Ruge','Cra 57#70-69','20/10/1965','Coomeva','cmp@gmial.com',889797796,'4792342ied','cmpg','Paciente');




































