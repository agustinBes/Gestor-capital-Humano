WITH datos_cuestionario AS (
    SELECT *
    FROM (VALUES
        (1, 0, 2, 0),
        (2, 1, 3, 1),
        (3, 2, 1, 4),
        (4, 3, 4, 2),
        (5, 4, 2, 1),
        (6, 6, 3, 3),
        (7, 8, 2, 2),
        (8, 9, 6, 4)
    ) AS datos(id_candidato, dias_creacion, horas_creacion, dias_hasta_intento)
)
UPDATE cuestionario c
SET fecha_creacion = LOCALTIMESTAMP
        - make_interval(days => datos.dias_creacion, hours => datos.horas_creacion),
    fecha_inicio = LOCALTIMESTAMP
        - make_interval(days => datos.dias_creacion, hours => datos.horas_creacion)
        + INTERVAL '1 hour',
    fecha_intento = LOCALTIMESTAMP
        - make_interval(days => datos.dias_creacion, hours => datos.horas_creacion)
        + INTERVAL '1 hour'
        + make_interval(days => datos.dias_hasta_intento, hours => 2)
FROM datos_cuestionario datos
WHERE c.id_candidato = datos.id_candidato
  AND c.id_puesto = 7;

WITH puntaje_factor AS (
    SELECT ic.id_cuestionario,
           f.id_competencia,
           f.id AS id_factor,
           SUM(ic.puntaje)::INTEGER / 2 AS puntaje
    FROM item_cuestionario ic
    JOIN cuestionario c ON c.id = ic.id_cuestionario
    JOIN pregunta p ON p.id = ic.id_pregunta
    JOIN factor f ON f.id = p.id_factor
    WHERE c.id_puesto = 7
      AND c.id_candidato BETWEEN 1 AND 4
      AND f.id_competencia IS NOT NULL
    GROUP BY ic.id_cuestionario, f.id_competencia, f.id
),
puntajes_calculados AS (
    SELECT id_cuestionario,
           id_competencia,
           ROUND(AVG(puntaje)::NUMERIC, 2) AS puntaje_obtenido
    FROM puntaje_factor
    GROUP BY id_cuestionario, id_competencia
)
INSERT INTO puntaje_competencia (id_cuestionario, id_competencia, puntaje_obtenido)
SELECT id_cuestionario, id_competencia, puntaje_obtenido
FROM puntajes_calculados
ON CONFLICT (id_cuestionario, id_competencia)
DO UPDATE SET puntaje_obtenido = EXCLUDED.puntaje_obtenido;
