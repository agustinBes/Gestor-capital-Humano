-- =============================================
-- CONFIGURACIONES DE OPCIONES DE RESPUESTA
-- =============================================

INSERT INTO opcion_respuesta (nombre, descripcion)
VALUES
    ('Escala de desempeño',
     'Evalúa el nivel general de desempeño del candidato en una actividad laboral.'),

    ('Frecuencia de comportamiento',
     'Determina con qué frecuencia el candidato manifiesta un comportamiento determinado.'),

    ('Nivel de autonomía',
     'Evalúa la capacidad del candidato para desarrollar tareas sin supervisión constante.'),

    ('Dominio técnico',
     'Mide el grado de conocimiento y dominio de herramientas y procedimientos técnicos.'),

    ('Adaptabilidad',
     'Evalúa la capacidad de adaptación ante cambios en el entorno laboral.'),

    ('Resolución de conflictos',
     'Determina la capacidad para gestionar desacuerdos y resolver situaciones conflictivas.'),

    ('Trabajo en equipo',
     'Evalúa la predisposición y capacidad para colaborar con otros integrantes del equipo.'),

    ('Gestión del tiempo',
     'Mide la capacidad para organizar actividades y cumplir plazos establecidos.'),

    ('Comunicación laboral',
     'Evalúa la claridad y efectividad de la comunicación en situaciones profesionales.'),

    ('Liderazgo',
     'Determina el nivel de iniciativa y capacidad para orientar a otras personas.');


-- =============================================
-- OPCIONES DE RESPUESTA
-- =============================================

-- 1. Escala de desempeño (5 opciones)
INSERT INTO opcion (id_opcion_respuesta, fila, orden, descripcion)
VALUES
    (1, 1, 1, 'Muy deficiente'),
    (1, 2, 2, 'Deficiente'),
    (1, 3, 3, 'Regular'),
    (1, 4, 4, 'Bueno'),
    (1, 5, 5, 'Excelente');


-- 2. Frecuencia de comportamiento (5 opciones)
INSERT INTO opcion (id_opcion_respuesta, fila, orden, descripcion)
VALUES
    (2, 1, 1, 'Nunca'),
    (2, 2, 2, 'Rara vez'),
    (2, 3, 3, 'Ocasionalmente'),
    (2, 4, 4, 'Frecuentemente'),
    (2, 5, 5, 'Siempre');


-- 3. Nivel de autonomía (4 opciones)
INSERT INTO opcion (id_opcion_respuesta, fila, orden, descripcion)
VALUES
    (3, 1, 1, 'Requiere supervisión constante'),
    (3, 2, 2, 'Requiere supervisión frecuente'),
    (3, 3, 3, 'Trabaja con autonomía parcial'),
    (3, 4, 4, 'Trabaja con autonomía completa');


-- 4. Dominio técnico (5 opciones)
INSERT INTO opcion (id_opcion_respuesta, fila, orden, descripcion)
VALUES
    (4, 1, 1, 'Sin conocimientos previos'),
    (4, 2, 2, 'Conocimientos básicos'),
    (4, 3, 3, 'Conocimientos intermedios'),
    (4, 4, 4, 'Conocimientos avanzados'),
    (4, 5, 5, 'Dominio experto');


-- 5. Adaptabilidad (4 opciones)
INSERT INTO opcion (id_opcion_respuesta, fila, orden, descripcion)
VALUES
    (5, 1, 1, 'Presenta mucha dificultad'),
    (5, 2, 2, 'Presenta cierta dificultad'),
    (5, 3, 3, 'Se adapta adecuadamente'),
    (5, 4, 4, 'Se adapta con facilidad');


-- 6. Resolución de conflictos (3 opciones)
INSERT INTO opcion (id_opcion_respuesta, fila, orden, descripcion)
VALUES
    (6, 1, 1, 'Evita abordar los conflictos'),
    (6, 2, 2, 'Intenta resolverlos con ayuda'),
    (6, 3, 3, 'Resuelve conflictos autónomamente');


-- 7. Trabajo en equipo (4 opciones)
INSERT INTO opcion (id_opcion_respuesta, fila, orden, descripcion)
VALUES
    (7, 1, 1, 'Presenta dificultades para colaborar'),
    (7, 2, 2, 'Colabora cuando se lo solicitan'),
    (7, 3, 3, 'Colabora activamente'),
    (7, 4, 4, 'Promueve la colaboración grupal');


-- 8. Gestión del tiempo (3 opciones)
INSERT INTO opcion (id_opcion_respuesta, fila, orden, descripcion)
VALUES
    (8, 1, 1, 'No logra cumplir los plazos'),
    (8, 2, 2, 'Cumple los plazos con dificultad'),
    (8, 3, 3, 'Organiza eficazmente sus tiempos');


-- 9. Comunicación laboral (3 opciones)
INSERT INTO opcion (id_opcion_respuesta, fila, orden, descripcion)
VALUES
    (9, 1, 1, 'Comunicación poco clara'),
    (9, 2, 2, 'Comunicación parcialmente efectiva'),
    (9, 3, 3, 'Comunicación clara y efectiva');


-- 10. Liderazgo (4 opciones)
INSERT INTO opcion (id_opcion_respuesta, fila, orden, descripcion)
VALUES
    (10, 1, 1, 'No demuestra iniciativa'),
    (10, 2, 2, 'Demuestra iniciativa ocasionalmente'),
    (10, 3, 3, 'Orienta y acompaña al equipo'),
    (10, 4, 4, 'Lidera y motiva eficazmente');