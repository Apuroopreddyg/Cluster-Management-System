# Cluster Management System

A full-stack web application for managing multiple clusters and their associated entities, featuring centralized management, tenant-specific data access, and database-per-tenant multi-tenancy using dynamic MongoDB database routing.

## Overview

The Cluster Management System provides a centralized dashboard to create, manage, and monitor multiple clusters and their associated entities.

It implements a multi-tenant architecture in which each tenant has a dedicated MongoDB database. The backend dynamically routes database operations to the appropriate tenant database while maintaining centralized metadata for cluster and tenant management.

## Tech Stack

### Frontend
- Angular 18
- TypeScript
- HTML5
- CSS3
- Angular Router
- Angular HttpClient

### Backend
- Java 20
- Spring Boot
- Spring Web
- Spring Data MongoDB
- REST APIs
- Maven

### Database
- MongoDB
- MongoDB Compass

## Key Features

### 1. Cluster Management
- Create, view, update, and delete clusters.
- Organize multiple entities under individual clusters.
- Track the number of entities assigned to each cluster.
- Manage cluster details such as name, code, location, and status.
- Prevent deletion of clusters that contain assigned entities.

### 2. Entity Management
- Register entities under specific clusters.
- View entities associated with a selected cluster.
- Manage entity information and status.
- Automatically generate a dedicated database name for each entity.
- Maintain centralized entity metadata.

### 3. Multi-Tenant Database Architecture
- Implemented database-per-tenant architecture.
- Maintain cluster and tenant metadata in a central MongoDB database.
- Assign a separate MongoDB database to each tenant.
- Dynamically route database operations using Spring Data MongoDB's `MongoTemplate`.
- Cache tenant-specific MongoDB templates for reuse.
- Maintain logical data separation between tenants.

### 4. Overview Dashboard
- Display cluster and entity statistics.
- View tenant-specific overview information.
- Retrieve data dynamically from the selected tenant's database.
- Provide a centralized interface for navigating between clusters and tenant workspaces.

### 5. Tenant Workspace
- Dedicated workspace for each tenant.
- Navigation between Overview and additional functional modules.
- Dynamic navigation based on the selected tenant.

## System Architecture

The application uses a centralized metadata database and separate databases for individual tenants.

```text
                  Angular Frontend
                         |
                         | REST APIs
                         |
                 Spring Boot Backend
                         |
                -------------------
                |                 |
         Central Database    Tenant Database
                |                 |
             MongoDB           MongoDB
                |                 |
         ---------------    ---------------
         |             |    |      |      |
      Clusters      Tenants T1     T2    T3
```

### Database Structure

```text
MongoDB Server
│
├── cluster_management
│   ├── clusters
│   └── tenants
│
├── tenant_001
│   └── Tenant-specific data
│
├── tenant_002
│   └── Tenant-specific data
│
└── tenant_003
    └── Tenant-specific data
```

The backend maintains tenant-to-database mappings in the central database. When a tenant-specific operation is requested, the application identifies the corresponding database and dynamically obtains the appropriate `MongoTemplate`.

This architecture uses a single MongoDB server with multiple logical databases. Separate MongoDB server deployments are not required.

## Project Structure

```text
Cluster-Management-System/
│
├── frontend/
│   └── src/
│       └── app/
│           ├── core/
│           │   └── services/
│           ├── models/
│           └── pages/
│               ├── dashboard/
│               ├── tenant-layout/
│               ├── tenant-overview/
│               └── modules/
│
├── backend/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/
│           │       └── ...
│           │           ├── controller/
│           │           ├── service/
│           │           ├── repository/
│           │           ├── model/
│           │           ├── dto/
│           │           ├── exception/
│           │           └── tenant/
│           └── resources/
│               └── application.properties
│
└── README.md
```

## API Overview

The backend exposes REST APIs for:

| Module | Operations |
|---|---|
| Cluster Management | Create, retrieve, update, and delete clusters |
| Entity Management | Register, retrieve, update, and delete entities |
| Cluster Assignment | Retrieve entities associated with a cluster |
| Tenant Overview | Retrieve tenant-specific statistics |

## Getting Started

### Prerequisites

- Java 20
- Node.js and npm
- Angular CLI 18
- Maven
- MongoDB Community Server
- MongoDB Compass (optional)

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd Cluster-Management-System
```

### 2. Start MongoDB

Ensure your MongoDB server is running locally:

```text
mongodb://localhost:27017
```

### 3. Configure the Backend

Navigate to the backend directory:

```bash
cd backend
```

Configure `src/main/resources/application.properties`:

```properties
spring.application.name=cluster-management
server.port=8080
spring.data.mongodb.uri=mongodb://localhost:27017/test
```

Start the Spring Boot application:

```bash
mvn spring-boot:run
```

Backend:

```text
http://localhost:8080
```

### 4. Configure the Frontend

Open another terminal:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the Angular application:

```bash
ng serve
```

Frontend:

```text
http://localhost:4200
```

## Multi-Tenancy Workflow

1. Create a cluster through the management dashboard.
2. Register entities and assign them to the appropriate cluster.
3. Generate a dedicated database name for each tenant.
4. Store tenant metadata and database mappings in the central database.
5. Retrieve tenant information when a tenant-specific request is received.
6. Use `TenantMongoTemplateProvider` to obtain the appropriate `MongoTemplate`.
7. Execute database operations against the selected tenant database.

## Future Enhancements

- Authentication and role-based access control.
- Tenant-aware authorization.
- Advanced cluster monitoring and analytics.
- Additional tenant-specific modules.
- Audit logging and activity tracking.
- Improved error handling and application monitoring.
