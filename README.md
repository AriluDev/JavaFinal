# 📚 Sistema de Gestión de Biblioteca Universitaria

## 🧩 Descripción

Este proyecto consiste en el desarrollo de una API RESTful para la gestión de bibliotecas universitarias. Permite administrar libros, usuarios, préstamos y devoluciones, asegurando el acceso controlado mediante autenticación y autorización basada en roles.

El sistema está construido con Java y Spring Boot, utilizando una base de datos relacional (PostgreSQL) y seguridad basada en JWT (JSON Web Token).

---

## 🚀 Características

- 📖 Registro y gestión de libros
- 👤 Administración de usuarios
- 🔄 Control de préstamos y devoluciones
- 🔐 Autenticación y autorización con JWT
- 🛡️ Control de acceso por roles (ADMIN, USUARIO)
- ✅ Validación de datos
- ⚠️ Manejo centralizado de errores
- 📑 Documentación de API
- 🧪 Pruebas de endpoints

---

## 🛠️ Tecnologías utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Spring Security
- JWT (JSON Web Token)
- Postman
- Swagger / OpenAPI

---

## 🗄️ Modelo de Datos

### Entidades principales

- Libro
- Usuario
- Préstamo
- Rol

### Relaciones

- Un usuario puede tener múltiples préstamos (1:N)
- Un usuario puede tener múltiples roles (N:N)
- Un préstamo pertenece a un usuario y a un libro

---

## 🔐 Seguridad

El sistema implementa autenticación basada en JWT:

1. El usuario inicia sesión
2. El servidor genera un token JWT
3. El cliente envía el token en cada petición
4. El servidor valida el token y autoriza el acceso

### Roles

**ADMIN**
- Crear, editar y eliminar libros
- Registrar préstamos

**USUARIO**
- Consultar catálogo de libros
- Ver historial de préstamos

---

## 🔌 Endpoints principales

### 🔑 Autenticación
- POST /api/auth/login
- POST /api/auth/register

### 📚 Libros
- GET /api/libros
- POST /api/libros (ADMIN)
- PUT /api/libros/{id} (ADMIN)
- DELETE /api/libros/{id} (ADMIN)

### 👤 Usuarios
- GET /api/usuarios (ADMIN)
- GET /api/usuarios/{id}

### 🔄 Préstamos
- POST /api/prestamos (ADMIN)
- GET /api/prestamos/mis (USUARIO)
- PUT /api/prestamos/devolver/{id}

---

## ⚙️ Configuración del proyecto

### 📌 Requisitos

- Java 17 o superior
- Maven o Gradle
- PostgreSQL

---

### 🔧 Configuración de base de datos

Editar el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca
spring.datasource.username=postgres
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true