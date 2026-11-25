CREATE TABLE  archivo_importado(
    id BIGINT GENERATED ALWAYS  AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL UNIQUE,
    id_consultor BIGINT NOT NULL,
    FOREIGN KEY (id_consultor) REFERENCES Consultor(id)
);