#  Sistema Taller Mecánico / Auto Repair Shop System

## 🇪🇸 Español

### Descripción

Sistema de gestión para un taller mecánico desarrollado en Java. El proyecto permite administrar clientes, vehículos, mecánicos, repuestos y órdenes de servicio, además de generar facturas y reportes.

El sistema está desarrollado utilizando una arquitectura por capas, separando la lógica de negocio, el dominio, la persistencia y la presentación.

### Funcionalidades

* Gestión de clientes.
* Gestión de vehículos.
* Gestión de mecánicos.
* Gestión de repuestos y control de stock.
* Creación y gestión de órdenes de servicio.
* Registro de horas trabajadas.
* Cambio de estado de las órdenes.
* Cálculo y generación de facturas.
* Generación de reportes.
* Exportación de información a CSV y JSON.
* Persistencia de datos mediante archivos JSON.
* Soporte para español e inglés.
* Pruebas unitarias con JUnit 5.

### Tecnologías

* **Java 25**
* **Maven**
* **Gson 2.10.1** — manejo de archivos JSON.
* **JUnit 5** — pruebas unitarias.
* **JavaFX** — dependencia utilizada por el proyecto.
* **Git / GitHub** — control de versiones.

### Arquitectura

El proyecto utiliza una arquitectura organizada por capas:

```text
src
├── main
│   ├── java
│   │   └── co.edu.uptc
│   │       ├── application
│   │       │   ├── dto
│   │       │   └── service
│   │       ├── config
│   │       ├── domain
│   │       │   ├── exception
│   │       │   ├── model
│   │       │   └── repository
│   │       ├── enums
│   │       ├── infraestructure
│   │       │   └── persistence
│   │       ├── presentation
│   │       │   └── controller
│   │       └── util
│   └── resources
│       ├── data
│       └── i18n
│
└── test
    └── java
        ├── modelTest
        ├── repositoryTest
        └── serviceTest
```

### Principales módulos

**Clientes**

Permite registrar, consultar, actualizar, listar y eliminar clientes.

**Vehículos**

Permite registrar vehículos, consultar por placa, actualizar información, controlar el kilometraje y eliminar vehículos.

**Mecánicos**

Permite administrar mecánicos, sus especialidades y tarifas por hora.

**Repuestos**

Permite administrar repuestos, consultar su disponibilidad y controlar el inventario.

**Órdenes de servicio**

Permite crear órdenes, registrar diagnósticos, horas de trabajo, repuestos utilizados, descuentos y estados.

**Facturación**

Permite generar facturas a partir de las órdenes de servicio.

**Reportes**

Permite generar reportes relacionados con ingresos, repuestos utilizados y productividad de los mecánicos.

**Internacionalización**

La aplicación permite cambiar entre español e inglés mediante archivos de propiedades:

```text
messages_es.properties
messages_en.properties
```

### Persistencia

La información se almacena en archivos JSON ubicados en:

```text
src/main/resources/data/
```

Se utiliza **Gson** para serializar y deserializar los objetos Java.

### Pruebas

El proyecto utiliza **JUnit 5** para realizar pruebas unitarias.

Las pruebas se encuentran organizadas en:

```text
src/test/java
├── modelTest
├── repositoryTest
└── serviceTest
```

Se realizan pruebas sobre modelos, repositorios y servicios.

### Requisitos

Para ejecutar el proyecto se necesita:

* Java JDK 25.
* Maven.
* IntelliJ IDEA, Visual Studio Code u otro IDE compatible con Java.

### Instalación

Clonar el repositorio:

```bash
git clone URL_DEL_REPOSITORIO
```

Entrar al proyecto:

```bash
cd sistematallermecanico
```

### Compilar

```bash
mvn clean compile
```

### Ejecutar pruebas

```bash
mvn test
```

### Empaquetar

```bash
mvn clean package
```

El archivo JAR se genera dentro de:

```text
target/
```

### Ejecutar

También se puede ejecutar la clase principal desde el IDE:

```text
co.edu.uptc.config.Main
```

### Autores

Proyecto académico desarrollado para la Universidad Pedagógica y Tecnológica de Colombia (UPTC).

---

# 🇺🇸 English

### Description

Auto Repair Shop Management System developed in Java. The project allows users to manage clients, vehicles, mechanics, spare parts, and service orders, as well as generate invoices and reports.

The system uses a layered architecture that separates business logic, domain models, persistence, and presentation.

### Features

* Client management.
* Vehicle management.
* Mechanic management.
* Spare parts and inventory management.
* Service order creation and management.
* Work-hour registration.
* Service order status management.
* Invoice calculation and generation.
* Report generation.
* CSV and JSON data export.
* JSON-based data persistence.
* Spanish and English language support.
* Unit testing with JUnit 5.

### Technologies

* **Java 25**
* **Maven**
* **Gson 2.10.1** — JSON file management.
* **JUnit 5** — unit testing.
* **JavaFX** — project dependency.
* **Git / GitHub** — version control.

### Architecture

The project follows a layered architecture:

```text
src
├── main
│   ├── java
│   │   └── co.edu.uptc
│   │       ├── application
│   │       │   ├── dto
│   │       │   └── service
│   │       ├── config
│   │       ├── domain
│   │       │   ├── exception
│   │       │   ├── model
│   │       │   └── repository
│   │       ├── enums
│   │       ├── infraestructure
│   │       │   └── persistence
│   │       ├── presentation
│   │       │   └── controller
│   │       └── util
│   └── resources
│       ├── data
│       └── i18n
│
└── test
    └── java
        ├── modelTest
        ├── repositoryTest
        └── serviceTest
```

### Main Modules

**Clients**

Allows users to register, search, update, list, and delete clients.

**Vehicles**

Allows users to register vehicles, search by license plate, update information, manage mileage, and delete vehicles.

**Mechanics**

Allows users to manage mechanics, their specialties, and hourly rates.

**Spare Parts**

Allows users to manage spare parts, check availability, and control inventory.

**Service Orders**

Allows users to create orders, register diagnoses, working hours, spare parts, discounts, and order statuses.

**Billing**

Allows the generation of invoices based on service orders.

**Reports**

Allows users to generate reports related to income, spare parts usage, and mechanic productivity.

**Internationalization**

The application supports Spanish and English using property files:

```text
messages_es.properties
messages_en.properties
```

### Persistence

The system stores information in JSON files located in:

```text
src/main/resources/data/
```

**Gson** is used to serialize and deserialize Java objects.

### Testing

The project uses **JUnit 5** for unit testing.

Tests are organized into:

```text
src/test/java
├── modelTest
├── repositoryTest
└── serviceTest
```

The tests cover models, repositories, and services.

### Requirements

To run the project, you need:

* Java JDK 25.
* Maven.
* IntelliJ IDEA, Visual Studio Code, or another Java-compatible IDE.

### Installation

Clone the repository:

```bash
git clone REPOSITORY_URL
```

Enter the project directory:

```bash
cd sistematallermecanico
```

### Compile

```bash
mvn clean compile
```

### Run Tests

```bash
mvn test
```

### Package

```bash
mvn clean package
```

The generated JAR file will be located inside:

```text
target/
```

### Run

The main class can also be executed directly from the IDE:

```text
co.edu.uptc.config.Main
```

### Authors

Academic project developed for the Universidad Pedagógica y Tecnológica de Colombia (UPTC).
