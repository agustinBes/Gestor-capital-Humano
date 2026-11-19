INSERT INTO pregunta
(id_factor, id_opcion_respuesta, nombre, pregunta, descripcion, eliminacion)
VALUES
-- 1. Interpretación de información (Factor 1)
(1, 1, 'Interpretación de datos',
 '¿Qué capacidad demuestra al interpretar información para obtener conclusiones relevantes?',
 'Evalúa la capacidad para analizar información y extraer conclusiones útiles para la toma de decisiones.',
 FALSE),

(1, 1, 'Análisis de información',
 '¿Con qué precisión identifica tendencias y patrones en la información disponible?',
 'Evalúa la precisión en la interpretación de datos cuantitativos y cualitativos.',
 FALSE),

-- 2. Manejo de indicadores (Factor 2)
(2, 1, 'Uso de indicadores',
 '¿Qué nivel de dominio demuestra al utilizar indicadores para evaluar resultados?',
 'Evalúa el manejo de métricas e indicadores de desempeño.',
 FALSE),

-- 3. Planificación de actividades (Factor 3)
(3, 8, 'Planificación de tareas',
 '¿Con qué frecuencia organiza las actividades antes de comenzar un proyecto?',
 'Evalúa la planificación previa de actividades, recursos y tiempos.',
 FALSE),

(3, 8, 'Organización de recursos',
 '¿Con qué frecuencia contempla los recursos necesarios antes de ejecutar sus tareas?',
 'Evalúa la capacidad para anticipar necesidades y organizar recursos.',
 FALSE),

-- 4. Seguimiento de proyectos (Factor 4)
(4, 1, 'Control de avances',
 '¿Qué nivel de eficacia demuestra al controlar el avance de un proyecto?',
 'Evalúa la capacidad para supervisar avances y detectar desviaciones.',
 FALSE),

-- 5. Manejo de software (Factor 5)
(5, 4, 'Dominio de herramientas',
 '¿Qué nivel de dominio posee sobre las herramientas digitales necesarias para su trabajo?',
 'Evalúa el conocimiento y manejo de aplicaciones informáticas.',
 FALSE),

(5, 4, 'Aplicación de herramientas',
 '¿Qué autonomía demuestra al utilizar herramientas digitales para resolver sus tareas?',
 'Evalúa la capacidad para utilizar software sin depender constantemente de asistencia.',
 FALSE),

-- 6. Seguridad informática (Factor 6)
(6, 4, 'Protección de información',
 '¿Qué nivel de conocimiento demuestra sobre las medidas de protección de información?',
 'Evalúa el conocimiento de prácticas de seguridad informática.',
 FALSE),

-- 7. Selección de personal (Factor 7)
(7, 1, 'Evaluación de candidatos',
 '¿Qué eficacia demuestra al evaluar candidatos según los requisitos de un puesto?',
 'Evalúa la aplicación de criterios y técnicas de selección de personal.',
 FALSE),

(7, 1, 'Técnicas de selección',
 '¿Qué nivel de dominio demuestra al aplicar técnicas de selección de personal?',
 'Evalúa el conocimiento de procedimientos utilizados durante la selección.',
 FALSE),

-- 8. Administración del personal (Factor 8)
(8, 2, 'Gestión administrativa',
 '¿Con qué frecuencia mantiene actualizada la información administrativa del personal?',
 'Evalúa la regularidad y responsabilidad en la gestión de información de empleados.',
 FALSE),

-- 9. Claridad comunicacional (Factor 9)
(9, 9, 'Claridad al comunicar',
 '¿Qué claridad demuestra al transmitir instrucciones e información laboral?',
 'Evalúa la capacidad para comunicar ideas de manera comprensible.',
 FALSE),

(9, 9, 'Comunicación de ideas',
 '¿Qué tan efectivamente adapta su comunicación al interlocutor?',
 'Evalúa la capacidad para adecuar el mensaje a las necesidades de quien lo recibe.',
 FALSE),

-- 10. Escucha activa (Factor 10)
(10, 9, 'Comprensión del interlocutor',
 '¿Qué capacidad demuestra para comprender las opiniones y necesidades de otras personas?',
 'Evalúa la atención y comprensión durante las interacciones laborales.',
 FALSE),

-- 11. Cooperación grupal (Factor 11)
(11, 7, 'Colaboración laboral',
 '¿Qué predisposición demuestra para colaborar con sus compañeros de trabajo?',
 'Evalúa la disposición para cooperar y contribuir a objetivos compartidos.',
 FALSE),

(11, 7, 'Apoyo al equipo',
 '¿Con qué frecuencia ofrece ayuda a sus compañeros cuando lo necesitan?',
 'Evalúa la colaboración y el apoyo dentro del equipo de trabajo.',
 FALSE),

-- 12. Integración de equipos (Factor 12)
(12, 7, 'Integración grupal',
 '¿Qué capacidad demuestra para integrarse a nuevos equipos de trabajo?',
 'Evalúa la adaptación a dinámicas grupales y relaciones laborales.',
 FALSE),

-- 13. Flexibilidad (Factor 13)
(13, 5, 'Adaptación al cambio',
 '¿Qué capacidad demuestra para adaptarse a cambios en sus responsabilidades?',
 'Evalúa la flexibilidad frente a nuevas circunstancias laborales.',
 FALSE),

(13, 5, 'Respuesta ante cambios',
 '¿Con qué rapidez modifica sus métodos de trabajo cuando las circunstancias lo requieren?',
 'Evalúa la capacidad para ajustar comportamientos y métodos ante cambios.',
 FALSE),

-- 14. Aprendizaje continuo (Factor 14)
(14, 5, 'Desarrollo profesional',
 '¿Qué predisposición demuestra para incorporar nuevos conocimientos?',
 'Evalúa el interés por aprender y desarrollar habilidades profesionales.',
 FALSE),

-- 15. Gestión del tiempo (Factor 15)
(15, 8, 'Administración del tiempo',
 '¿Con qué eficacia distribuye el tiempo disponible para cumplir sus responsabilidades?',
 'Evalúa la organización del tiempo y el cumplimiento de actividades.',
 FALSE),

(15, 8, 'Cumplimiento de plazos',
 '¿Con qué frecuencia cumple las tareas dentro de los plazos establecidos?',
 'Evalúa la capacidad para administrar el tiempo y respetar fechas de entrega.',
 FALSE),

-- 16. Priorización de tareas (Factor 16)
(16, 8, 'Organización de prioridades',
 '¿Qué capacidad demuestra para identificar las tareas más importantes?',
 'Evalúa la identificación de prioridades según importancia y urgencia.',
 FALSE),

-- 17. Análisis de situaciones (Factor 17)
(17, 6, 'Identificación de problemas',
 '¿Qué capacidad demuestra para identificar las causas de un problema laboral?',
 'Evalúa la capacidad de análisis y reconocimiento de las causas de una dificultad.',
 FALSE),

(17, 6, 'Evaluación de alternativas',
 '¿Qué eficacia demuestra al analizar distintas alternativas ante un problema?',
 'Evalúa la capacidad para comparar alternativas y anticipar consecuencias.',
 FALSE),

-- 18. Toma de decisiones (Factor 18)
(18, 6, 'Decisiones laborales',
 '¿Qué capacidad demuestra para seleccionar soluciones considerando sus posibles consecuencias?',
 'Evalúa la toma de decisiones a partir del análisis de alternativas y riesgos.',
 FALSE),

-- 19. Cumplimiento de objetivos (Factor 19)
(19, 1, 'Logro de objetivos',
 '¿Qué nivel de cumplimiento demuestra respecto de los objetivos asignados?',
 'Evalúa el compromiso con las metas y responsabilidades establecidas.',
 FALSE),

(19, 1, 'Orientación a resultados',
 '¿Qué eficacia demuestra al alcanzar los resultados esperados?',
 'Evalúa la orientación al logro y el cumplimiento de resultados.',
 FALSE),

-- 20. Calidad del trabajo (Factor 20)
(20, 1, 'Calidad de las tareas',
 '¿Qué nivel de calidad demuestra en las tareas que realiza?',
 'Evalúa el cumplimiento de estándares y requisitos de calidad.',
 FALSE);