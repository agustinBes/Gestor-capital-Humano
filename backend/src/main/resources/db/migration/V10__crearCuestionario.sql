CREATE TYPE tipo_estado AS ENUM ('ACTIVO',   'COMPLETADO','INCOMPLETO','EN_PROCESO','SIN_CONTESTAR');


CREATE TABLE cuestionario (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_candidato BIGINT NOT NULL,
    id_puesto BIGINT NOT NULL,
    estado tipo_estado NOT NULL DEFAULT 'ACTIVO',
    fecha_inicio TIMESTAMP(0),
    fecha_intento TIMESTAMP(0),
    fecha_creacion TIMESTAMP(0) NOT NULL ,
    cantidad_bloques SMALLINT NOT NULL,
    puntaje SMALLINT ,

    clave VARCHAR(8) NOT NULL,
    nro_acceso SMALLINT NOT NULL DEFAULT 4,

    FOREIGN KEY (id_candidato) REFERENCES candidato( nro_candidato ),
    FOREIGN KEY (id_puesto) REFERENCES puesto(id)
);


CREATE TABLE item_cuestionario (
    id_cuestionario BIGINT NOT NULL,
    id_pregunta BIGINT NOT NULL,

    nro_bloque SMALLINT NOT NULL,
    puntaje SMALLINT NOT NULL DEFAULT 0,
    opcion SMALLINT,

    PRIMARY KEY (id_cuestionario, id_pregunta),

    FOREIGN KEY (id_cuestionario)
        REFERENCES cuestionario(id),

    FOREIGN KEY (id_pregunta)
        REFERENCES pregunta(id)
);