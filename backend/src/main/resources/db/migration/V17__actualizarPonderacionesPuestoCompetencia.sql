UPDATE puesto_competencia
SET ponderacion = LEAST(10, GREATEST(6, ponderacion + 4));

INSERT INTO puesto_competencia (id_puesto, id_competencia, ponderacion)
VALUES (
    (SELECT id FROM puesto WHERE codigo = 'PST-001'),
    (SELECT id FROM competencia WHERE codigo = 'CTP03561F'),
    8
);
