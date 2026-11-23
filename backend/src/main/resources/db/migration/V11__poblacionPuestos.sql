
-- =============================================
-- PUESTOS
-- =============================================

INSERT INTO puesto (codigo, nombre, descripcion, empresa)
VALUES
    ('PST-001', 'Desarrollador Backend',
     'Responsable del desarrollo, mantenimiento y optimización de servicios y aplicaciones del lado del servidor.',
     'TechSolutions'),

    ('PST-002', 'Desarrollador Frontend',
     'Encargado de implementar interfaces de usuario, desarrollar componentes visuales y mejorar la experiencia de navegación.',
     'TechSolutions'),

    ('PST-003', 'Analista de Recursos Humanos',
     'Gestiona procesos de selección, administración de personal y seguimiento del desarrollo profesional de los empleados.',
     'Grupo Empresarial Norte'),

    ('PST-004', 'Administrador de Base de Datos',
     'Diseña, administra y optimiza bases de datos garantizando su disponibilidad, seguridad e integridad.',
     'DataSystems'),

    ('PST-005', 'Project Manager',
     'Coordina equipos de trabajo, planifica proyectos y supervisa el cumplimiento de objetivos, recursos y plazos.',
     'Innovatech'),

    ('PST-006', 'Analista Contable',
     'Realiza registros contables, conciliaciones bancarias y elaboración de informes para el control financiero organizacional.',
     'Finanzas Integrales'),

    ('PST-007', 'Especialista en Marketing Digital',
     'Planifica campañas digitales, analiza métricas de rendimiento y gestiona canales de comunicación comercial.',
     'Marketing Plus'),

    ('PST-008', 'Soporte Técnico',
     'Brinda asistencia técnica a usuarios y resuelve incidentes relacionados con hardware, software y plataformas informáticas.',
     'Servicios IT'),

    ('PST-009', 'Analista de Datos',
     'Procesa y analiza conjuntos de datos para identificar tendencias y generar informes que faciliten la toma de decisiones.',
     'DataSystems'),

    ('PST-010', 'Gerente Comercial',
     'Dirige estrategias comerciales, supervisa equipos de ventas y gestiona relaciones con clientes y socios estratégicos.',
     'Grupo Empresarial Norte');


-- =============================================
-- ASOCIACIONES PUESTO - COMPETENCIA
-- =============================================

INSERT INTO puesto_competencia
(id_puesto, id_competencia, ponderacion)
VALUES
-- Desarrollador Backend (1)
(1, 3, 4),
(1, 9, 3),
(1, 10, 3),

-- Desarrollador Frontend (2)
(2, 3, 5),
(2, 5, 2),
(2, 6, 3),

-- Analista de Recursos Humanos (3)
(3, 4, 5),
(3, 5, 2),
(3, 6, 3),

-- Administrador de Base de Datos (4)
(4, 1, 4),
(4, 3, 4),
(4, 9, 2),

-- Project Manager (5)
(5, 2, 4),
(5, 6, 3),
(5, 10, 3),

-- Analista Contable (6)
(6, 1, 4),
(6, 8, 3),
(6, 9, 3),

-- Especialista en Marketing Digital (7)
(7, 1, 3),
(7, 3, 3),
(7, 5, 2),
(7, 10, 2),

-- Soporte Técnico (8)
(8, 3, 4),
(8, 5, 3),
(8, 9, 3),

-- Analista de Datos (9)
(9, 1, 6),
(9, 3, 2),
(9, 10, 2),

-- Gerente Comercial (10)
(10, 2, 2),
(10, 5, 3),
(10, 6, 2),
(10, 10, 3);
