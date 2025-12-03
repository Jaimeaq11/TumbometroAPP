DELETE FROM ruta;
DELETE FROM usuario;

/* esto hace que el admin siempre sea el id 1 (solo para pruebas)*/
ALTER TABLE usuario ALTER COLUMN id RESTART WITH 1;

INSERT INTO usuario (nombre, correo, biografia, contrasena, rol, nombreFoto) VALUES ('Admin', 'admin@gmail.com', 'el admin', '1c7d68d5f1a87ea968fef5b935aedcd7', 'admin', 'sin_foto.jpg');
INSERT INTO usuario (nombre, correo, biografia, contrasena, rol, nombreFoto) VALUES ('Jaime', 'jaimeabadq@gmail.com', 'soyjaime', '1c7d68d5f1a87ea968fef5b935aedcd7', 'usuario', 'user3.png');
INSERT INTO usuario (nombre, correo, biografia, contrasena, rol, nombreFoto) VALUES ('usuario1', 'usuario1@gmail.com', 'usuario1', '1c7d68d5f1a87ea968fef5b935aedcd7', 'usuario', 'sin_foto.jpg');
INSERT INTO usuario (nombre, correo, biografia, contrasena, rol, nombreFoto) VALUES ('usuario2', 'usuario2@gmail.com', 'usuario2', '1c7d68d5f1a87ea968fef5b935aedcd7', 'usuario', 'sin_foto.jpg');
INSERT INTO usuario (nombre, correo, biografia, contrasena, rol, nombreFoto) VALUES ('usuario3', 'usuario3@gmail.com', 'usuario3', '1c7d68d5f1a87ea968fef5b935aedcd7', 'usuario', 'sin_foto.jpg');

/*el usuario 2 soy yo mismo (jaimeabadq@gmail.com)*/
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, nombreFoto, publica, likes, listadeids, usuarioid) VALUES ('Subida a Berrocal', 'Ruta desafiante por la HU-4103 con curvas técnicas y vistas espectaculares', 30.0, 0.25, 'Difícil', 'Montaña', 'berrocal.jpg', 1, 0, ',', 2);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, nombreFoto, publica, likes, listadeids, usuarioid) VALUES ('Carretera de Sotiel', 'Paseo tranquilo junto al río, ideal para principiantes y disfrutar del paisaje', 47.2, 0.40, 'Fácil', 'Carretera', 'sotiel.jpg', 1, 0, ',', 2);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, nombreFoto, publica, likes, listadeids, usuarioid) VALUES ('Mirador de alajar', 'Ascenso técnico con pendientes pronunciadas, solo para expertos', 67.1, 1.5, 'Difícil', 'Montaña', 'alajar.jpg', 1, 0, ',', 2);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, nombreFoto, publica, likes, listadeids, usuarioid) VALUES ('Puente de Zalamea', 'Visita el puente abandonado de la provincia de Zalamea la Real', 52.1, 1.1, 'Media', 'Carretera', 'zalamea.jpg', 1, 0, ',', 2);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, nombreFoto, publica, likes, listadeids, usuarioid) VALUES ('Minas de RioTinto', 'Ruta mística a través de bosques densos con caminos sinuosos', 41.8, 1.2, 'Media', 'Carretera', 'riotinto.jpg', 1, 0, ',', 2);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, nombreFoto, publica, likes, listadeids, usuarioid) VALUES ('Venta del Cruce Santa Ana', 'Recorrido panorámico por la N-435 hasta la sierra de Huelva', 94.3, 2.4, 'Media', 'Urbana', 'santa_ana.jpg', 1, 0, ',', 2);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, nombreFoto, publica, likes, listadeids, usuarioid) VALUES ('Ruta del Madroño', 'Ruta técnica con curvas cerradas y cambios de elevación bruscos', 78.8, 2.0, 'Difícil', 'Montaña', 'madroño.jpg', 1, 0, ',', 2);