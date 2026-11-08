CREATE TYPE tipo_auditoria AS ENUM ('PREGUNTA', 'FACTOR', 'COMPETENCIA','PUESTO','OPCION_RESPUESTA');
CREATE TYPE tipo_documento AS ENUM('DNI','LC','LE','PP');


CREATE TABLE consultor(
    id BIGINT GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
nombre_usuario VARCHAR(30) NOT NULL UNIQUE,
clave VARCHAR(300) NOT NULL,
 nivel_acceso SMALLINT NOT NULL DEFAULT 2);


CREATE TABLE candidato (
    nro_candidato   BIGINT GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
   clave VARCHAR(255) NOT NULL,
    fecha_nacimiento TIMESTAMP(0),
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    mail VARCHAR(150) NOT NULL UNIQUE,
    tipo_documento tipo_documento  NOT NULL,
    documento VARCHAR(30) NOT NULL UNIQUE,
    nacionalidad VARCHAR(60),
    genero CHAR(1),
    escolaridad VARCHAR(100)
);
CREATE TABLE auditoria(
    id BIGINT GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
    id_consultor BIGINT NOT NULL,
    nombre_registro VARCHAR(100) NOT NULL,
    tipo_auditoria tipo_auditoria NOT NULL,
 registro_actividad timestamp(0) DEFAULT NOW(),
    FOREIGN KEY (id_consultor) REFERENCES consultor(id)
);
