--es para introducir un admin en la bd
-- meto directamente la contraseña encriptada

DELETE FROM usuarios WHERE correo = 'jaimeabadq@gmail.com';
INSERT INTO usuarios (nombre, correo, contrasena) VALUES ('Admin', 'admin@gmail.com','1c7d68d5f1a87ea968fef5b935aedcd7')