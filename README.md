# Business Expense & Reimbursement Management Platform

A backend REST API for managing employee expenses and reimbursement workflows.

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Spring Security
- Jakarta Validation
- JUnit 5
- Mockito
- Maven

## Architecture

Controller → Service → Repository → Database

- Controller: Handles HTTP requests and responses
- Service: Contains business logic and workflow rules
- Repository: Handles database operations
- MySQL: Stores application data

## Expense Workflow

PENDING → APPROVED → REIMBURSED

PENDING → REJECTED

Invalid state transitions are rejected by the service layer.

## Roles

### Employee
Can create expenses.

### Manager
Can approve or reject expenses.

### Finance
Can reimburse approved expenses.

## API Endpoints

### Create Expense

POST `/api/expenses`

Example:

```json
{
  "amount": 2500,
  "description": "Client travel"
}