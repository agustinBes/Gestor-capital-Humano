CREATE TABLE opcion_respuesta (
                                  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                  nombre      VARCHAR(50) NOT NULL ,
                                  descripcion VARCHAR(300),
                                    activo boolean NOT NULL  DEFAULT true);
CREATE TABLE opcion (
                        id_opcion_respuesta BIGINT NOT NULL,
                        fila SMALLINT NOT NULL,
                        orden SMALLINT NOT NULL ,
                        descripcion VARCHAR(100),

                PRIMARY KEY (id_opcion_respuesta, fila),
                 FOREIGN KEY (id_opcion_respuesta)REFERENCES opcion_respuesta(id)
                            ON DELETE CASCADE);
-- ........................................................................................................................
-- .................. para la asociacion con una pregunta 
-- ........................................................................................................................
