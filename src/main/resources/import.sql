INSERT INTO departamentos (nombre, ubicacion) VALUES ('Contabilidad', 'Planta 1');
INSERT INTO departamentos (nombre, ubicacion) VALUES ('Marketing', 'Planta 2');
INSERT INTO departamentos (nombre, ubicacion) VALUES ('Recursos Humanos', 'Planta 3');
INSERT INTO departamentos (nombre, ubicacion) VALUES ('Direccion', 'Planta 4');

INSERT INTO empleados (dni, nombre, salario, telefono, dpto_id) VALUES ('12345678A', 'Pepe', '1500', '332211', 2);
INSERT INTO empleados (dni, nombre, salario, telefono, dpto_id) VALUES ('87654321B', 'Maria', '1121', '665544', 3);
INSERT INTO empleados (dni, nombre, salario, telefono, dpto_id) VALUES ('12345678A', 'Jose', '1150', '998877', 1);

INSERT INTO jefes (dni, nombre, salario, telefono, dpto_id) VALUES ('999888222S', 'Marcos', '2300', '339933', 1);
INSERT INTO jefes (dni, nombre, salario, telefono, dpto_id) VALUES ('111333222Q', 'Lucia', '1875', '232323', 2);
INSERT INTO jefes (dni, nombre, salario, telefono, dpto_id) VALUES ('222444333P', 'Narciso', '2125', '565656', 4);

INSERT INTO usuarios (username, pass) VALUES ('juan', '123');
