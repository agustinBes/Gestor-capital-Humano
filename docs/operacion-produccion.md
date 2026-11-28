# Operación, API y producción

Esta guía cubre la generación local de artefactos, la configuración necesaria para
ejecutarlos y las operaciones de auditoría e importación de candidatos. Crear los
artefactos no los publica ni reinicia en un servidor remoto.

## Requisitos verificados

- Backend: Maven Wrapper, Java compatible con la versión 25 declarada en `backend/pom.xml`.
  La compilación local se ejecutó con Java 26.
- Frontend: Node.js y npm. La compilación local se ejecutó con Node.js 24.14.0 y npm 11.9.0.
- PostgreSQL disponible para iniciar el backend.

## Configuración del backend

La aplicación escucha en el puerto `8080` por defecto y utiliza PostgreSQL en el puerto
`5433`, con la base `capital_humano`. El usuario y la contraseña se leen de las variables
de entorno `DB_capital_humano` y `DB_capital_humano_pass`.

En un entorno de producción, configurar las credenciales fuera del repositorio y asignar
la URL completa de la base mediante `SPRING_DATASOURCE_URL` cuando PostgreSQL no esté en
el mismo host. También pueden configurarse `SPRING_DATASOURCE_USERNAME`,
`SPRING_DATASOURCE_PASSWORD` y `SERVER_PORT` como variables de entorno de Spring Boot.
No almacenar credenciales en el código ni en archivos versionados.

Las migraciones de esquema están administradas por Flyway. Antes de iniciar la aplicación
contra una base productiva, comprobar su disponibilidad y revisar las migraciones que se
aplicarán.

## Empaquetar e iniciar el backend

Desde PowerShell, en la raíz del repositorio:

```powershell
Set-Location .\backend
$env:DB_capital_humano = 'USUARIO_POSTGRES'
$env:DB_capital_humano_pass = 'CONTRASENA_CONFIGURADA'
.\mvnw.cmd package
java -jar .\target\consultora-rrhh-0.0.1-SNAPSHOT.jar
```

`package` ejecuta las fases previas de Maven y genera el JAR ejecutable en
`backend/target/consultora-rrhh-0.0.1-SNAPSHOT.jar`. Reemplazar los valores de ejemplo por
variables configuradas en el entorno; no guardar contraseñas en esta guía.

## Empaquetar e iniciar el frontend

La aplicación Next.js usa `BACKEND_API_URL` para reenviar las peticiones de `/api/backend`
al backend. Definirla antes del build para apuntar al host correcto:

```powershell
Set-Location '.\Frontend Diseño\my-app'
npm ci
$env:BACKEND_API_URL = 'http://localhost:8080'
npm run start:prod
```

`start:prod` construye la aplicación y luego inicia el servidor Next.js en modo
producción (puerto `3000` por defecto). Para separar ambas etapas, usar `npm run build`
y luego `npm start`.

Para un despliegue real, reemplazar la URL local por la URL base del backend accesible
desde el servidor Next.js y mantener la configuración correspondiente en el entorno donde
se ejecuta el proceso. `npm start` sirve un build existente; no publica el sitio por sí
mismo.

## JavaDoc

Los comentarios existentes pueden tener etiquetas `@param`, `@return` u otras incompletas.
El siguiente comando desactiva DocLint y permite que Maven complete la generación aunque
encuentre errores de documentación:

```powershell
Set-Location .\backend
.\mvnw.cmd 'javadoc:javadoc' '-Ddoclint=none' '-Dmaven.javadoc.failOnError=false'
```

El HTML se genera en `backend/target/reports/apidocs/index.html`. La salida puede contener
advertencias; el comando prioriza producir la documentación a partir de los comentarios
disponibles y no modifica las fuentes Java.

## API

El backend incluye SpringDoc. Con la aplicación en ejecución, consultar:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Operaciones relevantes para las pantallas de auditorías y candidatos:

| Método | Ruta | Uso |
| --- | --- | --- |
| `GET` | `/auditorias/lista` | Devuelve todos los registros de auditoría. |
| `GET` | `/gestionar-candidatos/lista` | Devuelve todos los candidatos registrados. |
| `POST` | `/gestionar-candidatos/import/{idConsultor}` | Importa un archivo CSV multipart en el campo `file`. |

### Formato del CSV de candidatos

La primera fila debe contener estos nombres exactos:

```text
id,nombre,apellido,fecha,mail,tipo,documento,nacionalidad,genero,escolaridad
```

La fecha debe tener formato `AAAA-MM-DD`; `tipo` admite `DNI`, `LE`, `LC` o `PP`, y
`genero` admite `F`, `M` o `X`. El ID y el documento del candidato deben ser únicos.
La importación requiere un ID de consultor válido en la ruta y el archivo debe enviarse
como `multipart/form-data` con el nombre de campo `file`.

## Despliegue remoto

Para pasar estos artefactos a un servidor, configurar allí la base y las variables de
entorno, desplegar el JAR y ejecutar el servidor Next.js de producción con su
`BACKEND_API_URL`. El destino, el método de publicación, TLS, proxy inverso y política de
reinicio dependen de la infraestructura; no se realizan automáticamente al compilar.
