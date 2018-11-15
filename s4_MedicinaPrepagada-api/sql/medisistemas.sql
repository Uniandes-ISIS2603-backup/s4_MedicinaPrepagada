delete from CitaMedicaEntity;

delete from HorarioAtencionEntity;
delete from ConsultorioEntity;
delete from SedeEntity;


delete from MedicoEntity;
delete from EspecialidadEntity;

delete from TARJETACREDITOENTITY;


delete from CitaLaboratorioEntity;
delete from FacturaEntity;

delete from AdministradorEntity; 
delete from HistoriaClinicaEntity; 
delete from OrdenMedicaEntity; 

delete from MedicamentoEntity;
delete from FarmaciaEntity;
delete from ExamenMedicoEntity;
delete from LaboratorioEntity;
delete from PACIENTEENTITY;



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
values(1,1234564,'Alfredo Torres Figueroa','Cra 18#124-80','21/11/1997','Compensar','atorres123@gmail.com',3102454978,'ale2C','a.torres','Paciente');

INSERT INTO PACIENTEENTITY (ID,DOCUMENTOIDENTIDAD,NOMBRE,DIRECCION,FECHANACIMIENTO,EPS,MAIL,NUMEROCONTACTO,CONTRASENA,LOGIN,TIPOUSUARIO)
values(2,1234789,'Nicolas Novoa Arciniegas','Cra 21#70-90','31/10/1997','Coomeva','nsnovos@gmial.com',3014394958,'perrito','n.nov','Paciente');

INSERT INTO PACIENTEENTITY (ID,DOCUMENTOIDENTIDAD,NOMBRE,DIRECCION,FECHANACIMIENTO,EPS,MAIL,NUMEROCONTACTO,CONTRASENA,LOGIN,TIPOUSUARIO)
values(3,1272039,'pepito Perez Moreno','Cll 64#13-90','19/02/1998','Coomeva','pperezs@gmial.com',2154530,'perrito2','peerex','Paciente');

INSERT INTO PACIENTEENTITY (ID,DOCUMENTOIDENTIDAD,NOMBRE,DIRECCION,FECHANACIMIENTO,EPS,MAIL,NUMEROCONTACTO,CONTRASENA,LOGIN,TIPOUSUARIO)
values(4,127789686,'Mario Laserna Alv','Cra 57#70-90','08/10/1965','Coomeva','ml@gmial.com',2768916,'givvu7897','mhhkgg','Paciente');

INSERT INTO PACIENTEENTITY (ID,DOCUMENTOIDENTIDAD,NOMBRE,DIRECCION,FECHANACIMIENTO,EPS,MAIL,NUMEROCONTACTO,CONTRASENA,LOGIN,TIPOUSUARIO)
values(5,1739221,'Carlos Montoya Ruge','Cra 57#70-69','20/10/1965','Coomeva','cmp@gmial.com',6050505,'4792342ied','cmpg','Paciente');





insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia, paciente_id) values (5100171235475198, 'Hartley Lamberts', 725, '22/73', 'mastercard',1);
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia, paciente_id) values (3565828670929210, 'Marlo McMorland', 490, '55/46', 'jcb',1);
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia,paciente_id) values (3567173574390047, 'Teodoor Klinck', 922, '33/29', 'jcb',2);
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia,paciente_id) values (6376268016415909, 'Cirstoforo Joust', 498, '93/46', 'instapayment',2);
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia,paciente_id) values (3536535204091005, 'Lorenzo Plimmer', 300, '39/79', 'jcb',3);
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia,paciente_id) values (3550791653030546, 'Hobie Enticknap', 995, '53/69', 'jcb',3);
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia,paciente_id) values (3558450459779002, 'Bartholomeo Fader', 097, '84/09', 'jcb',4);
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia,paciente_id) values (3585861946780451, 'Aurea Marsh', 412, '00/21', 'jcb',4);
insert into TARJETACREDITOENTITY (numero, nombreEnTarjeta, codigoseguridad, fechaExpiracion, franquicia,paciente_id) values (5602235051148099, 'Lucas Jenik', 849, '23/76', 'bankcard',5);




insert into MEDICOENTITY (id, contrasena, correo, descripcion, documentomedico, firma, login, nombre, telefono, tipousuario, ESPECIALIDAD_NOMBRE  )
values (100, 'JuliTo98', 'eljulito@gmail.com', 'mal medico', 7878493, 'mifirmauno', 'megaJulito984', 'Julio Alberto' , 98739279, 'basico', 'Endocrinologia' );

insert into MEDICOENTITY (id, contrasena, correo, descripcion, documentomedico, firma, login, nombre, telefono, tipousuario, ESPECIALIDAD_NOMBRE  )
values (200, 'megaMan847', 'andrux@hotmail.com', 'super medico', 89998493, 'mifirmados', 'superArmunx', 'Andres Lleras' , 99908673, 'basico', 'Cardiologia' );

insert into MEDICOENTITY (id, contrasena, correo, descripcion, documentomedico, firma, login, nombre, telefono, tipousuario, ESPECIALIDAD_NOMBRE  )
values (300, 'megaMan847', 'andrux@hotmail.com', 'super medico', 89955593, 'mifirmados', 'superArmunx', 'Andres Lleras' , 99988673, 'basico', 'Cardiologia' );


insert into HORARIOATENCIONENTITY (id, fechafin,fechainicio, CONSULTORIO_ID, MEDICO_ID )
values (100, '2018-09-20 08:00:00' , '2018-09-20 10:00:00',700 ,100 );

insert into HORARIOATENCIONENTITY (id, fechafin,fechainicio, CONSULTORIO_ID, MEDICO_ID )
values (200, '2018-09-20 08:00:00' , '2018-09-20 10:00:00',300 ,200 );

insert into HORARIOATENCIONENTITY (id, fechafin,fechainicio, CONSULTORIO_ID, MEDICO_ID )
values (300, '2018-09-22 08:00:00' , '2018-09-22 10:00:00',700 ,100 );

insert into HORARIOATENCIONENTITY (id, fechafin,fechainicio, CONSULTORIO_ID, MEDICO_ID )
values (400, '2018-09-22 08:00:00' , '2018-09-22 11:00:00',300 ,200 );

insert into HORARIOATENCIONENTITY (id, fechafin,fechainicio, CONSULTORIO_ID, MEDICO_ID )
values (500, '2018-09-23 08:00:00' , '2018-09-23 10:00:00',700 ,100 );

insert into HORARIOATENCIONENTITY (id, fechafin,fechainicio, CONSULTORIO_ID, MEDICO_ID )
values (600, '2018-09-23 08:00:00' , '2018-09-23 10:00:00',300 ,200 );


insert into HORARIOATENCIONENTITY (id, fechafin,fechainicio, CONSULTORIO_ID, MEDICO_ID )
values (700, '2018-10-22 10:00:00' , '2018-10-22 08:00:00',300 ,200 );

insert into CitaMedicaEntity (id, fecha, comentarios, PACIENTEAATENDER_ID, HORARIOATENCIONASIGNADO_ID )
values (2000, '2018-10-22 08:00:00','Excelente cita médica', 1, 700 );

insert into CitaMedicaEntity (id, fecha, comentarios, PACIENTEAATENDER_ID, HORARIOATENCIONASIGNADO_ID )
values (2001, '2018-10-22 08:20:00','Excelente cita médica', 2, 700 );

insert into CitaMedicaEntity (id, fecha, comentarios, PACIENTEAATENDER_ID, HORARIOATENCIONASIGNADO_ID )
values (2002, '2018-10-22 08:40:00','Excelente cita médica', 3, 700 );

insert into CitaMedicaEntity (id, fecha, comentarios, PACIENTEAATENDER_ID, HORARIOATENCIONASIGNADO_ID )
values (2003, '2018-10-22 09:00:00','Excelente cita médica', 4, 700 );



insert into AdministradorEntity (id,login, contrasena, tipoUsuario)
values(100,'Carlos12', 'carlitos12', 'Administrador'); 
insert into AdministradorEntity (id, login, contrasena, tipoUsuario)
values(200, 'Esteban', 'esteban12', 'Administrador'); 
insert into AdministradorEntity (id, login, contrasena, tipoUsuario)
values(300, 'Perdo24', 'pedrito12', 'Paciente'); 
insert into AdministradorEntity (id, login, contrasena, tipoUsuario)
values(400 , 'Laura1', 'laurita56', 'Paciente'); 





insert into HistoriaClinicaEntity ( fecha, descripcionDiagnostico, alergias, peso, estatura, fuma, bebe, operaciones, paciente_id)
values( to_date('08/10/2018', 'DD/MM/YYYY') , 'Gripa', 'Ninguna', 55, 178, 0, 0, 'Fractura pie izquierdo',1);
insert into HistoriaClinicaEntity ( fecha, descripcionDiagnostico, alergias, peso, estatura, fuma, bebe, operaciones, paciente_id)
values(to_date('07/20/2018', 'DD/MM/YYYY'), 'Dolor de cabeza', 'Mani', 70, 170, 1, 1, 'Ninguna',2);
insert into HistoriaClinicaEntity ( fecha, descripcionDiagnostico, alergias, peso, estatura, fuma, bebe, operaciones, paciente_id)
values(to_date('26/10/2018',  'DD/MM/YYYY'), 'Migraña', 'Ninguna', 68, 173, 0, 1, 'Apendicitis',3);




insert into OrdenMedicaEntity (id, firmaMedico, fechaExpedicion, comentarios, validaHasta)
values (400, 'Pedro Franco', '2018-10-19', 'Examen de sangre', '2018-12-19'); 
insert into OrdenMedicaEntity (id, firmaMedico, fechaExpedicion, comentarios, validaHasta)
values (300, 'Pedro Franco', '2018-10-24', 'Paracetamol 400', '2018-12-24'); 
insert into OrdenMedicaEntity (id, firmaMedico, fechaExpedicion, comentarios, validaHasta)
values (200, 'Carlos Estupiñan', '2018-10-01', 'Dolex gripa', '2018-12-01'); 
insert into OrdenMedicaEntity (id, firmaMedico, fechaExpedicion, comentarios, validaHasta)
values (100, 'Claudia Forero', '2018-09-07', 'Mucha agua', '2018-11-07'); 




insert into LaboratorioEntity (id,nombre,direccion,telefono,horarioAtencion,longitud,latitud)
values (1,'Laboratorio Dios Me Ampare','Cra 17#34-25',4561798,'Lunes a Viernes de 8:00 a 10:00am',-74.0817500,4.6097100);

insert into LaboratorioEntity (id,nombre,direccion,telefono,horarioAtencion,longitud,latitud)
values (2,'Laboratorio Heisenberg','Calle 167 # 43-32',4561795,'Lunes a Viernes de 8:00 a 10:00am',-73.2,10.00);

insert into LaboratorioEntity (id,nombre,direccion,telefono,horarioAtencion,longitud,latitud)
values (3,'Laboratorio El Campin','Cra 17#04-15',4535298,'Lunes a Viernes de 8:00 a 10:00am',-74.01,2.354);

insert into LaboratorioEntity (id,nombre,direccion,telefono,horarioAtencion,longitud,latitud)
values (4,'Laboratorio GOAT','Cra 7#34-25',4595198,'Lunes a Viernes de 8:00 a 10:00am',-73.5,4.5);

insert into CitaLaboratorioEntity (id, fecha, especialidad, comentarios, recomendaciones,paciente_id,laboratorio_id)
values (156,'2018-10-22 08:00:00','Examen de sangre', 'Paciente alergico a acetaminofen','Ayunas de minimo 3 horas',1,1);

insert into CitaLaboratorioEntity (id, fecha, especialidad, comentarios, recomendaciones,paciente_id,laboratorio_id)
values (157,'2018-10-25 08:30:00','Examen de orina', 'Ninguna','Ayunas de minimo 3 horas',2,4);

insert into CitaLaboratorioEntity (id, fecha, especialidad, comentarios, recomendaciones,paciente_id,laboratorio_id)
values (158,'2018-10-17 08:00:00','Coprologia', 'Ninguna','Ayunas de minimo 3 horas',3,2);

insert into CitaLaboratorioEntity (id, fecha, especialidad, comentarios, recomendaciones,paciente_id,laboratorio_id)
values (15,'2018-10-22 09:00:00','Examen de sangre', 'Paciente con anemia','Ayunas de minimo 3 horas',3,3);

insert into FacturaEntity (id,idCliente,fecha,valor,concepto,pagada,paciente_id)
values (1,1,'2018-10-22 09:00:00 ',123500,'Cita Laboratorio Examen de Sangre', 1,1);

insert into FacturaEntity (id,idCliente,fecha,valor,concepto,pagada,paciente_id)
values (2,5,'2018-10-22 09:00:00',80500,'Cita Laboratorio Examen de orina', 1,5);

insert into FacturaEntity (id,idCliente,fecha,valor,concepto,pagada,paciente_id)
values (3,1,'2018-10-24 09:00:00',115000,'Cita Laboratorio Examen coprologico', 1,1);

insert into FacturaEntity (id,idCliente,fecha,valor,concepto,pagada,paciente_id)
values (4,3,'2018-10-22 09:00:00',100000,'Cita Laboratorio Examen de Sangre', 1,3);



insert into MedicamentoEntity(id, nombre, cantidad, descripcion, elaboradoPor, costo) values (100001, 'Advil', '50 mg', 'No apto para menores de edad', 'Pfizer', 15000);
insert into MedicamentoEntity(id, nombre, cantidad, descripcion, elaboradoPor, costo) values (100002, 'Aspirina', '70 mg', 'No apto para menores de edad', 'Bayer', 12000);
insert into MedicamentoEntity(id, nombre, cantidad, descripcion, elaboradoPor, costo) values (100003, 'Amoxicilina', '100 mg', 'Tomar abundante agua', 'Tecnoquimicas', 170000);
insert into MedicamentoEntity(id, nombre, cantidad, descripcion, elaboradoPor, costo) values (100004, 'Ibuprofeno', '800 mg', 'No usar durante el embarazo', 'MK', 17000);


insert into FarmaciaEntity(id, nombre, ubicacion, telefono, longitud, latitud, correo) values (100001, 'Farmatodo', 'Calle 30 # 45-67', 2345678, -72.03, 4.05, 'servicio@farmatodo.com');
insert into FarmaciaEntity(id, nombre, ubicacion, telefono, longitud, latitud, correo) values (100002, 'Cruz Verde', 'Calle 20 # 35-77', 1245678, -72.05, 4.08, 'servicio@cruzverde.com');
insert into FarmaciaEntity(id, nombre, ubicacion, telefono, longitud, latitud, correo) values (100003, 'Drogas la Rebaja', 'Calle 36 # 65-17', 2347878, -72.13, 4.15, 'servicio@rebaja.com');
insert into FarmaciaEntity(id, nombre, ubicacion, telefono, longitud, latitud, correo) values (100004, 'Drogueria Alemana', 'Calle 56 # 15-17', 2437878, -72.11, 4.16, 'servicio@alemana.com');


insert into ExamenMedicoEntity(id, nombre, costo, recomendaciones) values (100001, 'Biopsia', 120000, 'Venir con acompanante');
insert into ExamenMedicoEntity(id, nombre, costo, recomendaciones) values (100002, 'Rayos X', 12000, 'Es preferible no estar en estado de embarazo');
insert into ExamenMedicoEntity(id, nombre, costo, recomendaciones) values (100003, 'Ecografia', 140000, 'Venir con ropa ligera');
insert into ExamenMedicoEntity(id, nombre, costo, recomendaciones) values (100004, 'Examen de orina', 15000, 'Venir en ayunas');

















