-- =============================================
-- 7 COMPETENCIAS ADICIONALES
-- =============================================

INSERT INTO competencia
(codigo, nombre, descripcion, tipo, eliminacion)
VALUES
    ('CTP23081L', 'Liderazgo estratégico',
     'Capacidad para orientar equipos, anticipar escenarios y promover decisiones alineadas con los objetivos institucionales.',
     'GENERICA', FALSE),

    ('CTP24492N', 'Innovación y creatividad',
     'Capacidad para generar ideas originales, explorar alternativas y transformar necesidades en soluciones novedosas.',
     'GENERICA', FALSE),

    ('CTP25703P', 'Negociación',
     'Capacidad para acordar soluciones favorables considerando intereses propios y ajenos mediante un intercambio constructivo.',
     'ACTITUDINAL', FALSE),

    ('CTP26814R', 'Atención al detalle',
     'Capacidad para revisar información y tareas con precisión, identificando errores, omisiones o inconsistencias.',
     'GENERICA', FALSE),

    ('CTP27925T', 'Servicio al cliente',
     'Capacidad para comprender necesidades de usuarios o clientes y brindar respuestas oportunas y satisfactorias.',
     'ACTITUDINAL', FALSE),

    ('CTP28336V', 'Gestión de la calidad',
     'Conocimiento y aplicación de métodos para asegurar que productos, servicios y procesos cumplan requisitos establecidos.',
     'TECNICA', FALSE),

    ('CTP29147X', 'Seguridad y cumplimiento',
     'Capacidad para aplicar normas, procedimientos y controles destinados a prevenir riesgos y asegurar el cumplimiento.',
     'TECNICA', FALSE);


-- =============================================
-- ASOCIACIONES PUESTO - COMPETENCIA NUEVAS
-- =============================================

INSERT INTO puesto_competencia
(id_puesto, id_competencia, ponderacion)
VALUES
    ((SELECT id FROM puesto WHERE codigo = 'PST-005'),
     (SELECT id FROM competencia WHERE codigo = 'CTP23081L'), 5),

    ((SELECT id FROM puesto WHERE codigo = 'PST-010'),
     (SELECT id FROM competencia WHERE codigo = 'CTP23081L'), 4),

    ((SELECT id FROM puesto WHERE codigo = 'PST-002'),
     (SELECT id FROM competencia WHERE codigo = 'CTP24492N'), 4),

    ((SELECT id FROM puesto WHERE codigo = 'PST-007'),
     (SELECT id FROM competencia WHERE codigo = 'CTP24492N'), 5),

    ((SELECT id FROM puesto WHERE codigo = 'PST-003'),
     (SELECT id FROM competencia WHERE codigo = 'CTP25703P'), 4),

    ((SELECT id FROM puesto WHERE codigo = 'PST-010'),
     (SELECT id FROM competencia WHERE codigo = 'CTP25703P'), 5),

    ((SELECT id FROM puesto WHERE codigo = 'PST-006'),
     (SELECT id FROM competencia WHERE codigo = 'CTP26814R'), 5),

    ((SELECT id FROM puesto WHERE codigo = 'PST-009'),
     (SELECT id FROM competencia WHERE codigo = 'CTP26814R'), 4),

    ((SELECT id FROM puesto WHERE codigo = 'PST-007'),
     (SELECT id FROM competencia WHERE codigo = 'CTP27925T'), 5),

    ((SELECT id FROM puesto WHERE codigo = 'PST-010'),
     (SELECT id FROM competencia WHERE codigo = 'CTP27925T'), 4),

    ((SELECT id FROM puesto WHERE codigo = 'PST-004'),
     (SELECT id FROM competencia WHERE codigo = 'CTP28336V'), 4),

    ((SELECT id FROM puesto WHERE codigo = 'PST-006'),
     (SELECT id FROM competencia WHERE codigo = 'CTP28336V'), 3),

    ((SELECT id FROM puesto WHERE codigo = 'PST-008'),
     (SELECT id FROM competencia WHERE codigo = 'CTP29147X'), 5),

    ((SELECT id FROM puesto WHERE codigo = 'PST-004'),
     (SELECT id FROM competencia WHERE codigo = 'CTP29147X'), 4);


-- =============================================
-- 15 FACTORES ADICIONALES
-- =============================================

INSERT INTO factor
(codigo, nombre, descripcion, eliminacion, id_competencia, nro_orden)
VALUES
    ('FCT30571A', 'Visualización de datos',
     'Capacidad para representar información mediante gráficos y recursos visuales comprensibles.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP03561F'), 3),

    ('FCT31682C', 'Validación de datos',
     'Capacidad para comprobar la exactitud, coherencia y procedencia de la información utilizada.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP03561F'), 4),

    ('FCT32793E', 'Gestión de riesgos',
     'Capacidad para anticipar riesgos de proyectos y definir acciones preventivas o correctivas.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP04872K'), 3),

    ('FCT33804G', 'Automatización de tareas',
     'Capacidad para identificar procesos repetitivos y automatizarlos mediante herramientas digitales.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP05293M'), 3),

    ('FCT34915I', 'Visión estratégica',
     'Capacidad para relacionar decisiones de corto plazo con objetivos y escenarios organizacionales.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP23081L'), 1),

    ('FCT35026K', 'Toma de iniciativa',
     'Predisposición para proponer acciones y asumir responsabilidades sin requerir indicaciones constantes.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP23081L'), 2),

    ('FCT36137M', 'Influencia organizacional',
     'Capacidad para persuadir a otras personas y movilizar apoyo en torno a objetivos compartidos.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP23081L'), 3),

    ('FCT37248O', 'Generación de ideas',
     'Capacidad para producir propuestas nuevas y alternativas ante necesidades o dificultades.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP24492N'), 1),

    ('FCT38359Q', 'Experimentación',
     'Predisposición para probar nuevas soluciones, evaluar resultados y ajustar enfoques.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP24492N'), 2),

    ('FCT39460S', 'Preparación de acuerdos',
     'Capacidad para preparar posiciones, alternativas y argumentos antes de una negociación.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP25703P'), 1),

    ('FCT40571U', 'Criterio de concesiones',
     'Capacidad para identificar qué aspectos pueden negociarse sin afectar objetivos esenciales.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP25703P'), 2),

    ('FCT41682W', 'Revisión documental',
     'Capacidad para inspeccionar documentos y registros verificando datos y requisitos.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP26814R'), 1),

    ('FCT42793Y', 'Detección de inconsistencias',
     'Capacidad para reconocer diferencias, errores o contradicciones en información y procesos.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP26814R'), 2),

    ('FCT43804A', 'Empatía con usuarios',
     'Capacidad para comprender perspectivas y sentimientos de usuarios o clientes durante la atención.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP27925T'), 1),

    ('FCT44915C', 'Control de procesos',
     'Capacidad para verificar etapas, controles y registros asociados a procesos de calidad.',
     FALSE,
     (SELECT id FROM competencia WHERE codigo = 'CTP28336V'), 1);


-- =============================================
-- 20 PREGUNTAS ADICIONALES
-- =============================================

INSERT INTO pregunta
(id_factor, id_opcion_respuesta, nombre, pregunta, descripcion, eliminacion)
VALUES
    ((SELECT id FROM factor WHERE codigo = 'FCT02451A'), 1,
     'NUEVA - Validación de conclusiones',
     '¿Con qué precisión verifica las conclusiones obtenidas a partir de la información analizada?',
     'Evalúa la revisión crítica de conclusiones derivadas de datos.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT02451A'), 1,
     'NUEVA - Identificación de relaciones',
     '¿Qué nivel de eficacia demuestra al identificar relaciones relevantes entre diferentes datos?',
     'Evalúa la capacidad para relacionar variables e información.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT02451A'), 1,
     'NUEVA - Exposición de hallazgos',
     '¿Qué claridad utiliza al comunicar los hallazgos obtenidos del análisis?',
     'Evalúa la presentación comprensible de resultados analíticos.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT02451A'), 1,
     'NUEVA - Uso de evidencia',
     '¿Qué nivel de respaldo aporta a sus conclusiones mediante datos verificables?',
     'Evalúa el uso de evidencia objetiva en las decisiones.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT34915I'), 10,
     'NUEVA - Anticipación estratégica',
     '¿Qué capacidad demuestra para anticipar escenarios relacionados con sus decisiones?',
     'Evalúa la perspectiva estratégica y la anticipación.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT34915I'), 10,
     'NUEVA - Coherencia organizacional',
     '¿Qué nivel de alineación mantiene entre sus propuestas y los objetivos institucionales?',
     'Evalúa la coherencia de las decisiones con la estrategia.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT34915I'), 10,
     'NUEVA - Priorización estratégica',
     '¿Qué eficacia demuestra al priorizar iniciativas según su impacto organizacional?',
     'Evalúa la selección de prioridades con criterio estratégico.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT37248O'), 10,
     'NUEVA - Propuestas innovadoras',
     '¿Qué frecuencia propone soluciones originales ante necesidades conocidas?',
     'Evalúa la generación regular de ideas innovadoras.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT37248O'), 10,
     'NUEVA - Diversidad de alternativas',
     '¿Qué capacidad demuestra para plantear alternativas diferentes ante un mismo problema?',
     'Evalúa la amplitud y diversidad del pensamiento creativo.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT37248O'), 10,
     'NUEVA - Viabilidad creativa',
     '¿Qué nivel de criterio aplica al convertir ideas creativas en propuestas realizables?',
     'Evalúa la transformación de ideas originales en soluciones aplicables.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT43804A'), 9,
     'NUEVA - Comprensión de necesidades',
     '¿Qué eficacia demuestra al comprender las necesidades expresadas por usuarios o clientes?',
     'Evalúa la escucha y comprensión de requerimientos.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT43804A'), 9,
     'NUEVA - Respuesta empática',
     '¿Qué nivel de sensibilidad mantiene al responder situaciones problemáticas de usuarios?',
     'Evalúa la consideración de perspectivas y sentimientos ajenos.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT43804A'), 9,
     'NUEVA - Seguimiento al usuario',
     '¿Con qué frecuencia verifica que la respuesta brindada haya satisfecho al usuario?',
     'Evalúa el cierre efectivo de necesidades de atención.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT03862C'), 1,
     'NUEVA - Lectura de métricas',
     '¿Qué precisión demuestra al interpretar indicadores de desempeño?',
     'Evalúa la comprensión de métricas organizacionales.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT04173E'), 8,
     'NUEVA - Secuencia de actividades',
     '¿Qué capacidad demuestra para ordenar las actividades de un proyecto de manera lógica?',
     'Evalúa la definición coherente de secuencias de trabajo.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT35026K'), 10,
     'NUEVA - Iniciativa autónoma',
     '¿Qué frecuencia toma decisiones operativas dentro de su ámbito sin esperar instrucciones detalladas?',
     'Evalúa la autonomía asociada a la iniciativa.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT36137M'), 10,
     'NUEVA - Construcción de apoyo',
     '¿Qué eficacia demuestra al lograr apoyo para una propuesta necesaria?',
     'Evalúa la influencia positiva sobre otras personas.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT39460S'), 6,
     'NUEVA - Preparación de posiciones',
     '¿Qué nivel de preparación demuestra antes de participar en una negociación?',
     'Evalúa la definición previa de objetivos y argumentos.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT40571U'), 6,
     'NUEVA - Concesiones equilibradas',
     '¿Qué criterio aplica al realizar concesiones durante una negociación?',
     'Evalúa el equilibrio entre concesiones y objetivos esenciales.',
     FALSE),

    ((SELECT id FROM factor WHERE codigo = 'FCT44915C'), 4,
     'NUEVA - Verificación de controles',
     '¿Qué nivel de dominio demuestra al verificar controles asociados a un proceso?',
     'Evalúa la aplicación de controles técnicos y registros de calidad.',
     FALSE);


-- Ponderaciones para las 20 preguntas nuevas, según su escala asociada.
INSERT INTO ponderacion_opcion
(id_pregunta, id_opcion_respuesta, fila, ponderacion)
SELECT p.id,
       p.id_opcion_respuesta,
       o.fila,
       o.orden
FROM pregunta p
JOIN opcion o ON o.id_opcion_respuesta = p.id_opcion_respuesta
WHERE p.nombre LIKE 'NUEVA - %';
