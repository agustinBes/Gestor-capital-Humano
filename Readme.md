# Bienvenidos

![Banner de github](./imagen/Banner-3.jpg)
***  

Desarrollaremos un proyecto Full Stack de una empresa Gestora de recursos humanos para la materia Desarrollo de Software y en colaboracion con el Trabajo de la materia Diseño de Sistemas

# Configuracion del entorno

configurar una cuenta en Github y aplicar configuraciones de autoria en tu entorno.
```git bash
 git config --global user.name "Tu Nombre"  
git config --global user.email "tu@email.com"
```

realizamos la creacion del proyecto base a traves de [Spring Boot initializer](https://start.spring.io/)  

<img src="imagen/spring-boot.png" width="200" alt="logo de spring boot">  

--- 
## Dependencias del proyecto
1. Spring WEB
2. Spring Data JPA
3. spring Doc
4. PostgreSQL Driver
5. Lombok (opcional)
6. bean validation
7. devtools
8. Flyway 
9. OAuth2 Resource Server(desactivado por el momento)
10. Jackson Csv
11. jackson-datatype-jsr310
12. OpenPdf-html
13. spring-boot-starter-thymeleaf
Otras dependencias viene por defecto con el inicializar de spring como jackson base

--- 
## 🗄️ Configuración de la capa de persistencia

El proyecto utiliza **PostgreSQL 18** como sistema gestor de bases de datos, ejecutándose localmente en el **puerto `5433`** o en donde se haya instalado para correr por defecto el motor.

La aplicación está configurada para crear automáticamente la base de datos `capital_humano` si todavía no existe, por lo que **no es necesario crearla manualmente** antes de iniciar el proyecto.

### ⚙️ Configuración de variables de entorno

Para establecer correctamente la conexión con la base de datos, es necesario configurar las siguientes variables de entorno en el sistema operativo:

```variables de entorno
 `DB_capital_humano`  Usuario de PostgreSQL utilizado para la conexión. 
 `DB_capital_humano_pass`  Contraseña correspondiente al usuario configurado. 
```

Ambas variables deben contener las credenciales establecidas durante la instalación y configuración de PostgreSQL.

### 🔌 Configuración de conexión

Spring Boot utiliza las variables de entorno anteriores para configurar el acceso a la base de datos:

```properties
app.db.port=5433
app.db.name=capital_humano
spring.datasource.url=jdbc:postgresql://localhost:${app.db.port}/${app.db.name}
spring.datasource.username=${DB_capital_humano}
spring.datasource.password=${DB_capital_humano_pass}

```
Por defecto el servidor de Tomcat corre en el puerto **8080** si desean modificarlo pueden hacerlo con una variable que altere el puerto por defecto tambien

### 🔄 Gestión y validación del esquema

La estructura de la base de datos se administra mediante **Flyway**, que permite mantener un esquema migratorio versionado y controlar la evolución de las tablas y relaciones del sistema.

Por otro lado, las anotaciones de persistencia de **JPA/Hibernate** se utilizan para validar que el esquema existente en la base de datos coincida con las entidades declaradas en Java.

En consecuencia:

- **Flyway:** responsable de crear, modificar y versionar el esquema mediante scripts SQL de migración.
- **JPA/Hibernate:** responsable de validar la correspondencia entre las entidades Java y el esquema de la base de datos.
- **`ddl-auto=validate`:** evita que Hibernate genere o modifique automáticamente la estructura de las tablas.

> 💡 **Importante:** Los cambios estructurales de la base de datos deben realizarse mediante nuevas migraciones de Flyway, manteniendo sincronizado el modelo de persistencia con la estructura definida en PostgreSQL.

### Parametros de configuracion del sistema

Establecimos un configuracion por defecto para la generacion de cuestionarios dichos parametros pueden ser modificados 
en el archivo de propiedades.
Los parametros de tiempo establecen una cuenta en dias desde la fecha de creacion o inicio
Alterar la cantidad de preguntas por bloque altera el nro de bloque al que pertenece una pregunta del cuestionario
> 💡 **Importante:** Tener en cuenta que alterarlos requiere un metodo que actualice los plazos, cantidadde accesos y tiempos de todos los cuestionarios ya existentes

```Parametros del cuestionario
cuestionario.tiempo-maximo-activo=7d
cuestionario.tiempo-maximo-realizacion=15d
cuestionario.preguntas-por-bloque=4
cuestionario.instrucciones=Responda todas las preguntas seleccionando la opción que mejor represente su respuesta.
cuestionario.cantidad-de-accesos=3
```

Una vez se hayan configurado estos datos ya esta disponible el servidor para ser iniciado 
Pueden realizar peticiones desde alguna herramienta como INSOMNIA o Postman
Tambien puede usar la que genera swagger-ui 

---
## Capa de Frontend



