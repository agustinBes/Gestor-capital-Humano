CREATE TYPE tipo_auditoria AS ENUM ('PREGUNTA', 'FACTOR', 'COMPETENCIA','PUESTO','OPCION_RESPUESTA');
CREATE TYPE tipo_documento AS ENUM('DNI','LC','LE','PP');
CREATE TYPE genero AS ENUM('F','M','X');

CREATE TABLE consultor(
    id BIGINT GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
    usuario VARCHAR(15) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL ,
    apellido VARCHAR(50) NOT NULL,
    clave VARCHAR(300) NOT NULL);
INSERT INTO consultor (usuario, nombre, apellido, clave) VALUES
('JDOE', 'John', 'Doe', '<HASH_JDOE>'),
('PSISTEMAS', 'profesores', 'sistemas', '<HASH_PSISTEMAS>');
CREATE TABLE candidato (
    nro_candidato   BIGINT  PRIMARY KEY NOT NULL ,
    fecha_nacimiento DATE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    mail VARCHAR(150) NOT NULL UNIQUE,
    tipo_documento tipo_documento  NOT NULL,
    documento VARCHAR(30) NOT NULL UNIQUE,
    nacionalidad VARCHAR(60),
  genero genero NOT NULL ,
    escolaridad VARCHAR(100),
    eliminacion boolean NOT NULL  DEFAULT  FALSE
);
CREATE TABLE auditoria(
    id BIGINT GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
    id_consultor BIGINT NOT NULL,
    id_entidad BIGINT NOT NULL,
    nombre_registro VARCHAR(100) NOT NULL,
    tipo_auditoria tipo_auditoria NOT NULL,
 registro_actividad timestamp(0) DEFAULT NOW(),
    FOREIGN KEY (id_consultor) REFERENCES consultor(id)
);
