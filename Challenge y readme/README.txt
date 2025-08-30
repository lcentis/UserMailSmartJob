# User Service

Microservicio de ejemplo para **registro de usuarios** desarrollado con:
 **Spring Boot 3**,
 **Java 17**,
 **Maven**,
 **Eclipse IDE
 **y base de datos H2 en memoria.
  
Cumple con los requisitos: API REST en JSON, validaciones configurables, persistencia en H2, y formato de error `{"mensaje": "..."}`.

---
#***************************************************
## ⚙️ Requisitos

- **Java 17**
- **Maven 3.6+**
- **Eclipse IDE** (o cualquier IDE compatible con Spring Boot)
- **Postman** para probar los endpoints
- **H2 Database** (se configura automáticamente en memoria)

#***************************************************

## 📂 Estructura del proyecto

user-service/
├─ pom.xml
├─ src/
│ ├─ main/
│ │ ├─ java/com/example/userservice/
│ │ │ ├─ UserServiceApplication.java
│ │ │ ├─ controller/UserController.java
│ │ │ ├─ dto/UserRequest.java
│ │ │ ├─ dto/UserResponse.java
│ │ │ ├─ entity/User.java
│ │ │ ├─ entity/Phone.java
│ │ │ ├─ repository/UserRepository.java
│ │ │ ├─ service/UserService.java
│ │ │ └─ exception/ApiExceptionHandler.java
│ │ └─ resources/
│ │ ├─ application.properties
│ │ └─ data.sql (opcional)

#***************************************************
## ▶️ Cómo ejecutar en Eclipse

1. Abrir el proyecto en Eclipse.  
2. Asegurarse que esté configurado con **Java 17** (Project → Properties → Java Build Path).  
3. Click derecho sobre el proyecto → **Maven → Update Project (Alt+F5)**.  
4. Para ejecutar:  
   - Opción A: Click derecho sobre `UserServiceApplication.java` → **Run As → Java Application**.  
   - Opción B: Click derecho sobre el proyecto → **Run As → Spring Boot App** (si tenés Spring Tools).  
   - Opción C: Desde consola (ubicado en la carpeta del proyecto):  
     ```bash
     mvn clean spring-boot:run
     ```

La aplicación quedará disponible en:  
👉 `http://localhost:8080`

---

## 🗄️ Base de datos H2

La app utiliza una base de datos **H2 en memoria**.  
- Consola disponible en: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
- JDBC URL: `jdbc:h2:mem:usersdb`  
- Usuario: `sa`  
- Contraseña: *(vacío)*  

---

## 📬 Endpoints

### POST `/api/users/register`

**Headers**

#***************************************************
Content-Type: application/json
**Request body**
```json
{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "Hunter123",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}

#***************************************************
Response éxito (201)
{
  "id": 1,
  "created": "2025-08-30T10:30:00",
  "modified": "2025-08-30T10:30:00",
  "last_login": "2025-08-30T10:30:00",
  "token": "uuid-generado",
  "isactive": true,
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}


#***************************************************
Listar usuarios – GET /api/users
Request
GET http://localhost:8080/api/users

Response (200)
Si no hay usuarios registrados:
[]

Si existen usuarios:
[
  {
    "id": 1,
    "created": "2025-08-30T10:30:00",
    "modified": "2025-08-30T10:30:00",
    "last_login": "2025-08-30T10:30:00",
    "token": "uuid-uno",
    "isactive": true,
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "phones": [
      {
        "number": "1234567",
        "citycode": "1",
        "contrycode": "57"
      }
    ]
  }
]


#***************************************************
Respuestas de error (formato obligatorio del enunciado):
Email repetido → 409 Conflict
{"mensaje":"El correo ya registrado"}

Email inválido → 400 Bad Request
{"mensaje":"Correo con formato inválido"}

Password inválida → 400 Bad Request
{"mensaje":"Contraseña con formato inválido"}


#***************************************************
Pruebas con Postman
Abrir Postman.
Crear una colección llamada User Service.
Agregar un request POST http://localhost:8080/api/users/register.
Configurar Body → raw → JSON y usar los ejemplos de arriba.
Probar los casos:
Registro exitoso.
Registro con email repetido.
Validación de email inválido.
Validación de password inválida.


