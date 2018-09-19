delete from ConsultorioEntity;
delete from SedeEntity;

delete from HorarioAtencionEntity;
delete from MedicoEntity;
delete from EspecialidadEntity;


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




















































