WITH opciones_por_pregunta AS (
    SELECT p.id AS id_pregunta,
           p.id_opcion_respuesta,
           COUNT(o.fila)::INTEGER AS cantidad_opciones
    FROM pregunta p
    JOIN opcion o ON o.id_opcion_respuesta = p.id_opcion_respuesta
    GROUP BY p.id, p.id_opcion_respuesta
)
UPDATE ponderacion_opcion po
SET ponderacion = CASE
    WHEN opciones.cantidad_opciones = 2 THEN
        CASE (po.id_pregunta - 1) % 5
            WHEN 0 THEN CASE po.fila WHEN 1 THEN 6 WHEN 2 THEN 4 END
            WHEN 1 THEN CASE po.fila WHEN 1 THEN 7 WHEN 2 THEN 3 END
            WHEN 2 THEN CASE po.fila WHEN 1 THEN 5 WHEN 2 THEN 5 END
            WHEN 3 THEN CASE po.fila WHEN 1 THEN 8 WHEN 2 THEN 2 END
            WHEN 4 THEN CASE po.fila WHEN 1 THEN 9 WHEN 2 THEN 1 END
        END
    WHEN opciones.cantidad_opciones = 3 THEN
        CASE po.fila WHEN 1 THEN 0 WHEN 2 THEN 0 WHEN 3 THEN 10 END
    WHEN opciones.cantidad_opciones = 4 THEN
        CASE (po.id_pregunta - 1) % 3
            WHEN 0 THEN CASE po.fila WHEN 1 THEN 0 WHEN 2 THEN 1 WHEN 3 THEN 2 WHEN 4 THEN 7 END
            WHEN 1 THEN CASE po.fila WHEN 1 THEN 0 WHEN 2 THEN 0 WHEN 3 THEN 2 WHEN 4 THEN 8 END
            WHEN 2 THEN CASE po.fila WHEN 1 THEN 0 WHEN 2 THEN 0 WHEN 3 THEN 1 WHEN 4 THEN 9 END
        END
    WHEN opciones.cantidad_opciones = 5 THEN
        CASE (po.id_pregunta - 1) % 3
            WHEN 0 THEN CASE po.fila WHEN 1 THEN 0 WHEN 2 THEN 0 WHEN 3 THEN 1 WHEN 4 THEN 2 WHEN 5 THEN 7 END
            WHEN 1 THEN CASE po.fila WHEN 1 THEN 0 WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 2 WHEN 5 THEN 8 END
            WHEN 2 THEN CASE po.fila WHEN 1 THEN 0 WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 1 WHEN 5 THEN 9 END
        END
END
FROM opciones_por_pregunta opciones
WHERE po.id_pregunta = opciones.id_pregunta
  AND po.id_opcion_respuesta = opciones.id_opcion_respuesta;
