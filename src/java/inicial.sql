/*DELETE FROM ruta;
DELETE FROM usuario;*/

/* esto hace que el admin siempre sea el id 1 (solo para pruebas)*/
ALTER TABLE usuario ALTER COLUMN id RESTART WITH 1;

INSERT INTO usuario (nombre, correo, biografia, contrasena, rol) VALUES ('Admin', 'admin@gmail.com', 'jaime', '1c7d68d5f1a87ea968fef5b935aedcd7', 'admin');
INSERT INTO usuario (nombre, correo, biografia, contrasena, rol) VALUES ('usuario1', 'usuario1@gmail.com', 'usuario1', '1c7d68d5f1a87ea968fef5b935aedcd7', 'usuario');
INSERT INTO usuario (nombre, correo, biografia, contrasena, rol) VALUES ('usuario2', 'usuario2@gmail.com', 'usuario2', '1c7d68d5f1a87ea968fef5b935aedcd7', 'usuario');
INSERT INTO usuario (nombre, correo, biografia, contrasena, rol) VALUES ('usuario3', 'usuario3@gmail.com', 'usuario3', '1c7d68d5f1a87ea968fef5b935aedcd7', 'usuario');

INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, rutafoto, publica, usuarioid) VALUES ('Sierra Norte Extreme', 'Ruta desafiante por las montañas del norte con curvas técnicas y vistas espectaculares', 45.5, 2.5, 'Difícil', 'Montaña', 'imagenes/rutas/montaña1.jpg', 1, null);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, rutafoto, publica, usuarioid) VALUES ('Camino del Río Verde', 'Paseo tranquilo junto al río, ideal para principiantes y disfrutar del paisaje', 25.3, 1.2, 'Fácil', 'Carretera', 'imagenes/rutas/carretera1.jpg', 1, null);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, rutafoto, publica, usuarioid) VALUES ('Subida al Pico del Águila', 'Ascenso técnico con pendientes pronunciadas, solo para expertos', 38.7, 3.8, 'Difícil', 'Montaña', 'imagenes/rutas/montaña2.jpg', 1, null);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, rutafoto, publica, usuarioid) VALUES ('Ruta Costera del Atlántico', 'Recorrido junto al mar con curvas suaves y brisa marina', 52.1, 2.8, 'Media', 'Costa', 'imagenes/rutas/costa1.jpg', 1, null);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, rutafoto, publica, usuarioid) VALUES ('Travesía del Bosque Encantado', 'Ruta mística a través de bosques densos con caminos sinuosos', 41.8, 2.3, 'Media', 'Offroad', 'imagenes/rutas/offroad1.jpg', 1, null);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, rutafoto, publica, usuarioid) VALUES ('Carretera de los Lagos', 'Recorrido panorámico pasando por varios lagos de montaña', 67.3, 3.5, 'Media', 'Carretera', 'imagenes/rutas/carretera2.jpg', 1, null);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, rutafoto, publica, usuarioid) VALUES ('Desafío del Desfiladero', 'Ruta técnica con curvas cerradas y cambios de elevación bruscos', 33.6, 2.1, 'Difícil', 'Montaña', 'imagenes/rutas/montaña3.jpg', 1, null);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, rutafoto, publica, usuarioid) VALUES ('Paseo del Valle Florido', 'Ruta relajada a través de valles con mucha vegetación', 28.9, 1.4, 'Fácil', 'Carretera', 'imagenes/rutas/carretera3.jpg', 1, null);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, rutafoto, publica, usuarioid) VALUES ('Ruta Nocturna Urbana', 'Recorrido por la ciudad con iluminación y tráfico moderado', 35.2, 1.8, 'Fácil', 'Carretera', 'imagenes/rutas/carretera4.jpg', 1, null);
INSERT INTO ruta (nombre, descripcion, distancia, tiempo, dificultad, tiporuta, rutafoto, publica, usuarioid) VALUES ('Trail de la Montaña Roja', 'Ruta offroad con terreno irregular y desafíos técnicos', 48.5, 3.2, 'Difícil', 'Offroad', 'imagenes/rutas/offroad2.jpg', 1, null);