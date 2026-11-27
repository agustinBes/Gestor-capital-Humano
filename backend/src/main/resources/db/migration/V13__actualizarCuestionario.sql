CREATE TABLE puntaje_competencia(
    id_cuestionario BIGINT NOT NULL,
    id_competencia BIGINT NOT NULL,
    puntaje_obtenido NUMERIC(4,2) NOT NULL,
PRIMARY KEY (id_cuestionario,id_competencia),

    CONSTRAINT fk_puntaje_cuestionario
        FOREIGN KEY (id_cuestionario)
        REFERENCES cuestionario(id),

    CONSTRAINT fk_puntaje_competencia
        FOREIGN KEY (id_competencia)
            REFERENCES competencia(id)
);
ALTER TABLE cuestionario DROP COLUMN puntaje;
