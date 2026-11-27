WITH datos_cuestionario AS (
    SELECT *
    FROM (VALUES
        (1, 0, 0, 0),
        (2, 1, 1, 2),
        (3, 2, 4, 1),
        (4, 3, 2, 1),
        (5, 4, 1, 2),
        (6, 6, 3, 1),
        (7, 8, 2, 2),
        (8, 10, 4, 1)
    ) AS datos(id_candidato, dias_inicio, dias_hasta_intento, nro_acceso)
)
UPDATE cuestionario c
SET fecha_creacion = CURRENT_DATE - datos.dias_inicio + TIME '08:00:00',
    fecha_inicio = CURRENT_DATE - datos.dias_inicio + TIME '09:00:00',
    fecha_intento = CURRENT_DATE - datos.dias_inicio
        + TIME '09:00:00'
        + make_interval(days => datos.dias_hasta_intento, hours => 2),
    nro_acceso = datos.nro_acceso
FROM datos_cuestionario datos
WHERE c.id_candidato = datos.id_candidato
  AND c.id_puesto = 7;

UPDATE cuestionario
SET estado = CASE id_candidato
    WHEN 1 THEN 'COMPLETADO'::tipo_estado
    WHEN 2 THEN 'COMPLETADO'::tipo_estado
    WHEN 3 THEN 'COMPLETADO'::tipo_estado
    WHEN 4 THEN 'COMPLETADO'::tipo_estado
    WHEN 5 THEN 'INCOMPLETO'::tipo_estado
    WHEN 6 THEN 'INCOMPLETO'::tipo_estado
    WHEN 7 THEN 'SIN_CONTESTAR'::tipo_estado
    WHEN 8 THEN 'SIN_CONTESTAR'::tipo_estado
END
WHERE id_puesto = 7
  AND id_candidato BETWEEN 1 AND 8;

WITH respuesta AS (
    SELECT DISTINCT ON (po.id_pregunta)
           po.id_pregunta,
           po.fila,
           po.ponderacion
    FROM ponderacion_opcion po
    JOIN pregunta p
      ON p.id = po.id_pregunta
     AND p.id_opcion_respuesta = po.id_opcion_respuesta
    ORDER BY po.id_pregunta, po.fila DESC
),
completados AS (
    SELECT id
    FROM cuestionario
    WHERE id_puesto = 7
      AND id_candidato BETWEEN 1 AND 4
)
UPDATE item_cuestionario ic
SET opcion = respuesta.fila,
    puntaje = respuesta.ponderacion
FROM completados c
JOIN respuesta ON TRUE
WHERE ic.id_cuestionario = c.id
  AND ic.id_pregunta = respuesta.id_pregunta;

WITH respuesta AS (
    SELECT DISTINCT ON (po.id_pregunta)
           po.id_pregunta,
           po.fila,
           po.ponderacion
    FROM ponderacion_opcion po
    JOIN pregunta p
      ON p.id = po.id_pregunta
     AND p.id_opcion_respuesta = po.id_opcion_respuesta
    ORDER BY po.id_pregunta, po.fila DESC
),
items_incompletos AS (
    SELECT ic.id_cuestionario,
           ic.id_pregunta,
           ROW_NUMBER() OVER (
               PARTITION BY ic.id_cuestionario
               ORDER BY ic.nro_bloque, ic.id_pregunta
           ) AS numero_item
    FROM item_cuestionario ic
    JOIN cuestionario c ON c.id = ic.id_cuestionario
    WHERE c.id_puesto = 7
      AND c.id_candidato IN (5, 6)
)
UPDATE item_cuestionario ic
SET opcion = respuesta.fila,
    puntaje = respuesta.ponderacion
FROM items_incompletos incompleto
JOIN respuesta ON respuesta.id_pregunta = incompleto.id_pregunta
WHERE ic.id_cuestionario = incompleto.id_cuestionario
  AND ic.id_pregunta = incompleto.id_pregunta
  AND incompleto.numero_item <= 6;
