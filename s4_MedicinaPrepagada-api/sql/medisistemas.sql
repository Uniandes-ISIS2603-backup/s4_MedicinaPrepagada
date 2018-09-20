delete from HorarioAtencionEntity;
delete from ConsultorioEntity;
delete from SedeEntity;


delete from MedicoEntity;
delete from EspecialidadEntity;

delete from TARJETACREDITOENTITY;
delete from PACIENTEENTITY;

delete from AdministradorEntity; 
delete from HistoriaClinicaEntity; 
delete from OrdenMedicaEntity; 


insert into EspecialidadEntity (nombre) values ('Endocrinologia');

insert into EspecialidadEntity (nombre) values ('Cardiologia');

insert into SedeEntity (id, nombre, direccion, tipoSede, descripcion, latitud, longitud, telefono, correo) 
values (100, 'super sede 1', 'Calle 153 # 15 - 13', 2, 'Super buena sede', 2, -69.2, 98999423, 'pepito12@hotmail.com' );

insert into SedeEntity (id, nombre, direccion, tipoSede, descripcion, latitud, longitud, telefono, correo)
values (200, 'super sede 2', 'Calle 133 # 90 - 01', 1, 'Super buena sede mega', 1.3, -70.19, 98761233, 'pepitoJav@hotmail.com' );

insert into SedeEntity (id, nombre, direccion, tipoSede, descripcion, latitud, longitud, telefono, correo) 
values (300, 'super sede 3', 'Calle 53 # 14 - 12', 2, 'Super buena sede superx2', 2.1, -69.4, 98065423, 'pepitoSuperman@gmail.com' );

insert into SedeEntity (id, nombre, direccion, tipoSede, descripcion, latitud, longitud, telefono, correo) 
values (400, 'super sede 4', 'Calle 111 # 44 - 21', 1, 'Super buena sede superx2 megax', 2.8, -70.8, 98975423, 'pepitoSupermanSantafe@gmail.com' );

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


insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia) values (5100171235475198, 'Hartley Lamberts', 725, '22/73', 'mastercard');
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia) values (3565828670929210, 'Marlo McMorland', 490, '55/46', 'jcb');
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia) values (3567173574390047, 'Teodoor Klinck', 922, '33/29', 'jcb');
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia) values (6376268016415909, 'Cirstoforo Joust', 498, '93/46', 'instapayment');
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia) values (56102814006070778, 'Desi Insull', 499, '11/43', 'china-unionpay');
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia) values (3536535204091005, 'Lorenzo Plimmer', 300, '39/79', 'jcb');
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia) values (3550791653030546, 'Hobie Enticknap', 995, '53/69', 'jcb');
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia) values (3558450459779002, 'Bartholomeo Fader', 097, '84/09', 'jcb');
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia) values (3585861946780451, 'Aurea Marsh', 412, '00/21', 'jcb');
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia) values (5602235051148099, 'Lucas Jenik', 849, '23/76', 'bankcard');

insert into MEDICOENTITY (id, contrasena, correo, descripcion, documentomedico, firma, login, nombre, telefono, tipousuario, ESPECIALIDAD_NOMBRE  )
values (100, 'JuliTo98', 'eljulito@gmail.com', 'mal medico', 7878493, 'mifirmauno', 'megaJulito984', 'Julio Alberto' , 98739279, 'basico', 'Endocrinologia' );

insert into MEDICOENTITY (id, contrasena, correo, descripcion, documentomedico, firma, login, nombre, telefono, tipousuario, ESPECIALIDAD_NOMBRE  )
values (200, 'megaMan847', 'andrux@hotmail.com', 'super medico', 89998493, 'mifirmados', 'superArmunx', 'Andres Lleras' , 99908673, 'basico', 'Cardiologia' );

insert into AdministradorEntity (login, contrasena, tipoUsuario)
values('Carlos12', 'carlitos12', 'Administrador'); 
insert into AdministradorEntity (login, contrasena, tipoUsuario)
values('Esteban', 'esteban12', 'Administrador'); 
insert into AdministradorEntity (login, contrasena, tipoUsuario)
values('Perdo24', 'pedrito12', 'Paciente'); 
insert into AdministradorEntity (login, contrasena, tipoUsuario)
values('Laura1', 'laurita56', 'Paciente'); 

insert into HistoriaClinicaEntity ( fecha, descripcionDiagnostico, alergias, peso, estatura, fuma, bebe, operaciones  )
values('2018/10/08', 'Gripa', 'Ninguna', 55, 178, 0, 0, 'Fractura pie izquierdo');
insert into HistoriaClinicaEntity ( fecha, descripcionDiagnostico, alergias, peso, estatura, fuma, bebe, operaciones  )
values('2018/07/20', 'Dolor de cabeza', 'Mani', 70, 170, 1, 1, 'Ninguna');
insert into HistoriaClinicaEntity ( fecha, descripcionDiagnostico, alergias, peso, estatura, fuma, bebe, operaciones  )
values('2018/10/26', 'Migraña', 'Ninguna', 68, 173, 0, 1, 'Apendicitis');

insert into OrdenMedicaEntity (id, firmaMedico, fechaExpedicion, comentarios, validaHasta)
values (400, 'Pedro Franco', '2018/10/19', 'Examen de sangre', '2018/12/19'); 
insert into OrdenMedicaEntity (id, firmaMedico, fechaExpedicion, comentarios, validaHasta)
values (300, 'Pedro Franco', '2018/10/24', 'Paracetamol 400', '2018/12/24'); 
insert into OrdenMedicaEntity (id, firmaMedico, fechaExpedicion, comentarios, validaHasta)
values (200, 'Carlos Estupiñan', '2018/10/01', 'Dolex gripa', '2018/12/01'); 
insert into OrdenMedicaEntity (id, firmaMedico, fechaExpedicion, comentarios, validaHasta)
values (100, 'Claudia Forero', '2018/09/07', 'Mucha agua', '2018/11/07'); 


























