--es para introducir un admin en la bd
-- meto directamente la contraseña encriptada

DELETE FROM usuarios WHERE correo = 'admin@gmail.com';
INSERT INTO usuarios (nombre, correo, biografia, contrasena, rol) VALUES ('Admin', 'admin@gmail.com', 'jaime', '1c7d68d5f1a87ea968fef5b935aedcd7', 'admin');
INSERT INTO usuarios (nombre, correo, biografia, contrasena, rol) VALUES ('usuario1', 'usuario1@gmail.com', 'usuario1', '1c7d68d5f1a87ea968fef5b935aedcd7', 'usuario');
INSERT INTO usuarios (nombre, correo, biografia, contrasena, rol) VALUES ('usuario2', 'usuario2@gmail.com', 'usuario2', '1c7d68d5f1a87ea968fef5b935aedcd7', 'usuario');
INSERT INTO usuarios (nombre, correo, biografia, contrasena, rol) VALUES ('usuario3', 'usuario3@gmail.com', 'usuario3', '1c7d68d5f1a87ea968fef5b935aedcd7', 'usuario');



/*INSERT INTO "APP"."rutas" (nombre, descripcion, distancia, tiempo, inclinacionmaxima, dificultad, tipocarretera, usuario_id) VALUES
('Sierra Norte Extreme', 'Ruta desafiante por las montañas del norte con curvas técnicas y vistas espectaculares', 45.5, 2.5, 12.8, 'Difícil', 'montaña', 1),
('Camino del Río Verde', 'Paseo tranquilo junto al río, ideal para principiantes y disfrutar del paisaje', 25.3, 1.2, 3.2, 'Fácil', 'carretera', 1),
('Subida al Pico del Águila', 'Ascenso técnico con pendientes pronunciadas, solo para expertos', 38.7, 3.8, 18.5, 'Difícil', 'montaña', 1),
('Ruta Costera del Atlántico', 'Recorrido junto al mar con curvas suaves y brisa marina', 52.1, 2.8, 5.4, 'Media', 'costa', 1),
('Travesía del Bosque Encantado', 'Ruta mística a través de bosques densos con caminos sinuosos', 41.8, 2.3, 8.7, 'Media', 'offroad', 1),
('Carretera de los Lagos', 'Recorrido panorámico pasando por varios lagos de montaña', 67.3, 3.5, 7.2, 'Media', 'carretera', 1),
('Desafío del Desfiladero', 'Ruta técnica con curvas cerradas y cambios de elevación bruscos', 33.6, 2.1, 15.3, 'Difícil', 'montaña', 1),
('Paseo del Valle Florido', 'Ruta relajada a través de valles con mucha vegetación', 28.9, 1.4, 4.1, 'Fácil', 'carretera', 1),
('Ruta Nocturna Urbana', 'Recorrido por la ciudad con iluminación y tráfico moderado', 35.2, 1.8, 2.8, 'Fácil', 'urbana', 1),
('Trail de la Montaña Roja', 'Ruta offroad con terreno irregular y desafíos técnicos', 48.5, 3.2, 14.6, 'Difícil', 'offroad', 1);*/