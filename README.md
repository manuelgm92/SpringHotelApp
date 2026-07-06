
# SpringHotelApp

## Descripción del Proyecto

Spring Hotel es una aplicación web de gestión hotelera desarrollada con Java con Spring MVC, Hibernate y JSP. Permite administrar las operaciones principales de un hotel: gestión de huéspedes, habitaciones, reservas e incidencias, con control de acceso según el perfil del usuario (recepcionista o supervisor).

La aplicación cuenta con un sistema de autenticación propio y diferencia las acciones disponibles según el rol: los recepcionistas pueden consultar, crear, editar y eliminar registros, mientras que los supervisores tienen acceso de solo lectura a la mayor parte del sistema, además de gestionar los usuarios.

---

## Requisitos previos
- Java 11 instalado.
- MySQL 8 o superior.
- Apache Tomcat 9.x (u otro contenedor de servlets compatible con Java 11).
- Maven 3.x para compilar y empaquetar el proyecto.
- Navegador moderno para acceder a la interfaz web.

---

## Instalación y despliegue: pasos para clonar el repositorio, crear la base de datos (en
qué orden ejecutar los scripts SQL) y desplegar la aplicación en el servidor.

1. **Clonar el repositorio:**

    ```bash
        git clone https://github.com/PrimeraEdicionFlexible/ProyectoFinalEquipoJ.git
    ```

2. **Crear la base de datos:**  
   Ejecutar los scripts SQL en el siguiente orden desde MySQL Workbench o línea de comandos:  

    

    ORDEN DE EJECUCIÓN
    
    1. [setup](src/main/resources/sql/setup.sql)
        
    2. [usuarios](src/main/resources/sql/usuarios.sql)        

    3. [habitaciones](src/main/resources/sql/habitaciones.sql)        

    4. [huespedes](src/main/resources/sql/huespedes.sql) 

    5. [reservas](src/main/resources/sql/reservas.sql) 

    6. [incidencias](src/main/resources/sql/incidencias.sql) 
    

3. **Configurar la conexión a la base de datos:**  

    Editar el archivo `src/main/resources/db.properties` con los datos de vuestra instalación local:
    ```properties
        db.url=jdbc:mysql://localhost:3306/hoteldb
        db.username=[vuestro_username_de_conexion]
        db.password=[vuestra_contraseña]
    ```

4. **Compilar y empaquetar la aplicación con Maven::**
    ```bash
        mvn clean package
    ```

5. **Desplegar en Tomcat:**  
   Desplegar el archivo `target/SpringHotelApp.war` en Tomcat.

6. **Acceder a la aplicación:**  
   Abrir el navegador en `http://localhost:8080/SpringHotelApp/`

---

## Tecnologías utilizadas: 

- Java 11
- Spring MVC
- Spring ORM / Hibernate
- Spring JDBC
- MySQL Connector/J
- JSP + JSTL
- Hibernate Validator
- Log4j
- Maven
- Servlet API
- JUnit 4 (para tests)


--- 

## Estructura del proyecto

- `src/main/java/com/springhotel/config` - configuración de Spring MVC y Hibernate.
- `src/main/java/com/springhotel/controller` - controladores web que manejan las rutas y peticiones.
- `src/main/java/com/springhotel/service` - lógica de negocio y validaciones.
- `src/main/java/com/springhotel/dao` - acceso a datos con Hibernate.
- `src/main/java/com/springhotel/entity` - entidades mapeadas a las tablas de la base de datos.
- `src/main/java/com/springhotel/exception` - excepciones personalizadas y manejo centralizado.
- `src/main/resources` - propiedades de configuración y scripts SQL.
- `src/main/webapp/WEB-INF/views` - vistas JSP.
- `src/main/webapp/css` - estilos.

---

### Reparto de tareas: 

* Ana Laura Izquierdo Becerra: 
    - Habitaciones + Incidencias
    - Reservas (métodos de consulta (listado, detalle) y eliminación, más las
        vistas JSP Reservas.jsp y DetalleReserva.jsp.)

* Manuel González Martínez
    - Huéspedes + Login
    - Reservas (métodos de alta y modificación, más la vista JSP
        FormularioReserva.jsp (unificado para alta y modificación).)

---

### Conflictos de merge: 

Durante la integración de las ramas se resolvieron conflictos principalmente en:

- Formularios y vistas de reservas, donde se combinaron dos versiones de la estructura de datos.
- Controladores de reservas y la carga de selectores de habitaciones/huéspedes.
- Vistas JSP compartidas por varias ramas, ajustando nombres de campos y mensajes de error.

La resolución se hizo comparando el comportamiento esperado en cada rama y manteniendo la lógica más completa de cada funcionalidad.

Al trabajar colaborativamente en la rama feature/reservas sobre los mismos archivos, tuvimos conflictos en el código, que hemos logrado resolver sobre todo gracias a una buena comunicación durante todo el proceso entre ambas partes, avisando previamente y posteriormente sobre los cambios que ibamos a realizar.

--- 

### Funcionalidades extra: 

* **Sistema de logs profesional (Log4j):** Se han sustituido todos los `System.out.println` y se ha implementado un sistema de mensajes según su nivel (`DEBUG`, `INFO`, `WARN`, `ERROR`), lo que facilita saber qué pasa en la aplicación en cada momento.

* **Interceptor de seguridad**

Se ha añadido un interceptor de Spring MVC para proteger todas las rutas de la aplicación:

- `com.springhotel.interceptores.AutenticacionInterceptor`
- `com.springhotel.config.SpringMvcConfig`

El interceptor comprueba el atributo de sesión `usuarioSesion`. Si no existe, redirige a `/login` y bloquea el acceso.

*Rutas excluidas*
- `/login`
- `/css/**`

*Prueba*
1. Abrir `/principal` sin iniciar sesión → debe ir a `/login`
2. Iniciar sesión
3. Volver a `/principal` → debe mostrarse normalmente

**Cifrado de contraseñas con BCrypt**

Se deja de almacenar las contraseñas en texto plano en la base de datos. Las contraseñas ya no se almacenan en texto plano en la base de datos. Se ha integrado la librería `at.favre.lib:bcrypt` y creado la clase `com.springhotel.utils.BCryptUtil` con dos métodos estáticos:
  - `hashPassword(String)` — genera un hash BCrypt con coste 12.
  - `verificarPassword(String, String)` — compara texto plano contra el hash almacenado.

*Cambios realizados*
  - Los `INSERT INTO` del script SQL usan hashes BCrypt en lugar de texto plano.
  - El login ya no filtra por contraseña en la consulta SQL; busca solo por nombre
    de usuario, recupera el hash y lo verifica con `BCryptUtil`.
  - Al dar de alta o actualizar un usuario, la contraseña se hashea en el Service.

*Prueba*
  1. Abrir MySQL Workbench → la columna `password_usuario` debe mostrar hashes `$2a$12$...`
  2. Iniciar sesión con las credenciales habituales → debe funcionar con normalidad.
  3. Introducir una contraseña incorrecta → debe denegar el acceso.
  
**Búsqueda y filtrado en listados**

Añadir funcionalidad de búsqueda en las páginas de listado para localizar registros sin recorrer toda la tabla visualmente.

*Cambios realizados*
 Se ha añadido funcionalidad de búsqueda en los siguientes listados:

   - Huéspedes → filtrado por apellido

   - Incidencias → filtrado por prioridad

*Prueba*
  Abrir el listado de huéspedes.

  - Introducir apellido → muestra solo los registros coincidentes.

  - Dejar el campo vacío → muentra todos los huéspedes.

  Abrir el listado de incidencias.

  - Seleccionar una prioridad (Alta, Media, Baja) → muestra las indicencias con la prioridad elegida.
  
  - No seleccionar ninguna →  muestran todas.
  
  **SonarQube**
  *Refactorización de Vistas JSP*
  
  Para evitar duplicación de código y mejorar el mantenimiento se añade:
  -views.jsp
  -navbar.jsp
  
  Cambios realizados

Se centraliza la barra de navegación.
Se reutilizan fragmentos comunes.
Se actualizan botones, estilos y estructura para mayor consistencia visual.
Se añaden atributos integrity y crossorigin en recursos externos para mayor seguridad.

 *Contructor de injection* 
 
   Hace explícitas las dependencias
   Facilita testing(En caso de tenerlos)
   Evita estados inválidos
   Mejora la legibilidad

 *DTOs, Mappers y AbstractDAOImpl*
 
   DTOs añadidos
 Separan la capa de transporte de datos del modelo principal, evitando exponer entidades        directamente.

   Mapper implementado
 Convierte entidades ↔ DTOs.

   Nueva clase base DAO: AbstractDAOImpl
 Centraliza operaciones comunes:

CRUD genérico

Manejo de sesiones Hibernate

Reducción de duplicación en DAOs concretos.

*Lombok Integrado*

Se añaden anotaciones:

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor


Beneficios

Código más limpio.

Menos repetición.

Mayor mantenibilidad.
 
---

*Credenciales de prueba:* 

Usuarios y contraseñas para poder probar la aplicación: 

- **Recepcionista:** `recepcionista` / `recep123`
- **Supervisor:** `supervisor` / `super123`

---

*Estructura rápida de navegación*

- `/` → página de inicio.
- `/login` → acceso de usuario.
- `/principal` → panel principal tras login.
- `/usuarios` → gestión de usuarios.
- `/habitaciones` → gestión de habitaciones.
- `/reservas` → gestión de reservas.
- `/incidencias` → gestión de incidencias.
- `/huespedes` → gestión de huéspedes.
