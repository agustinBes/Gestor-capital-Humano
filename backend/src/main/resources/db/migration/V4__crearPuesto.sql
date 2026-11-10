CREATE TABLE puesto(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(30) NOT NULL ,
    descripcion VARCHAR(300),
    empresa VARCHAR(50)
);
CREATE TABLE puesto_competencia(
    id_puesto BIGINT NOT NULL,
    id_competencia BIGINT NOT NULL,
    PRIMARY KEY (id_puesto,id_competencia),
    FOREIGN KEY (id_puesto) REFERENCES puesto(id),
    FOREIGN KEY (id_competencia) REFERENCES  competencia(id),
    CONSTRAINT uq_pregunta_competencia
        UNIQUE(id_puesto,id_competencia)
    ponderacion SMALLINT NOT NULL
);