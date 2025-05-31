# Product Management App

Este proyecto es una aplicación de escritorio en Java para la gestión de productos agrícolas, utilizando una interfaz gráfica (Swing) y una base de datos MySQL. Permite crear, editar, eliminar y listar productos, almacenando información relevante como nombre, descripción, agricultor y hasta tres imágenes por producto.

## Estructura del Proyecto

- `src/main/java/com/productmanagement/`  
  - `ProductManagementApp.java`: Interfaz gráfica principal (Swing).
  - `controller/ProductController.java`: Lógica de negocio y comunicación entre la vista y la capa DAO.
  - `dao/ProductDAO.java`: Acceso a datos y operaciones CRUD sobre la tabla `product` en MySQL.
  - `model/Product.java`: Modelo de datos del producto.
  - `util/DatabaseUtil.java`: Utilidad para la conexión a la base de datos usando las propiedades de configuración.
- `src/main/resources/config.properties`: Configuración de la conexión a la base de datos.
- `lib/`: Librerías externas necesarias (por ejemplo, `mysql-connector-j-8.0.33.jar`).
- `pom.xml`: Archivo de configuración de Maven y dependencias.

## Requisitos

- Java 8 o superior
- Maven
- MySQL Server

## Configuración de la Base de Datos

1. Crea la base de datos y la tabla `product` en MySQL:

```sql
CREATE DATABASE adminsistem;
USE adminsistem;

CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    farmer VARCHAR(255),
    image1 VARCHAR(255),
    image2 VARCHAR(255),
    image3 VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME
);
```

2. Ajusta el archivo `src/main/resources/config.properties` con tus credenciales de MySQL:

```
db.url=jdbc:mysql://localhost:3306/adminsistem
db.user=martin
db.password=camilo23
```

## Compilación y Ejecución

### Usando Maven

1. Instala las dependencias:

```zsh
mvn clean install
```

2. Ejecuta la aplicación:

```zsh
mvn exec:java -Dexec.mainClass="com.productmanagement.ProductManagementApp"
```

### Usando el JAR Manualmente

1. Compila el proyecto:

```zsh
mvn package
```

2. Ejecuta el JAR generado (ajusta la ruta y el nombre del JAR si es necesario):

```zsh
java -cp target/my-project-1.0-SNAPSHOT.jar:lib/mysql-connector-j-8.0.33.jar com.productmanagement.ProductManagementApp
```

## Notas

- Si usas un IDE como IntelliJ IDEA o Eclipse, importa el proyecto como un proyecto Maven.
- Asegúrate de que el archivo `mysql-connector-j-8.0.33.jar` esté en la carpeta `lib/` o usa Maven para gestionar la dependencia.
- Las imágenes de los productos se almacenan como rutas de archivo.

## Funcionamiento

- Al iniciar la aplicación, se muestra una lista de productos.
- Puedes crear, editar o eliminar productos usando los botones de la interfaz.
- Al crear o editar, puedes seleccionar imágenes desde tu sistema de archivos.

---

**Autor:** martinernestorestrepopalacios

