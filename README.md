# Expense Tracker

A full-stack personal finance web application for tracking income, expenses, and debts, built with **Java 21**, **Spring Boot**, **SQLite**, and a vanilla **HTML/CSS/JavaScript** frontend.

This project is the web-based evolution of [PersonalExpenseTracker](https://github.com/itptt271/PersonalExpenseTracker), a console-based Java application. I expanded the original project to practice REST API development, database access with JDBC, request validation, DTOs, and frontend–backend communication.

---

## 日本語での概要

個人の収支と借金を管理するための Web アプリケーションです。**Java 21 / Spring Boot / SQLite / HTML / CSS / JavaScript** を使用して開発しました。

コンソールアプリ版の PersonalExpenseTracker をベースに、REST API、データベース操作、リクエストのバリデーション、DTO、Web UI などを追加し、ブラウザから実際に操作できるアプリケーションへ発展させました。

取引（Transaction）と借金（Debt）の登録・更新・削除、カテゴリー・日付による検索、カテゴリー別の支出集計、返済履歴の記録・表示などに対応しています。

---

## Tech Stack

- **Java 21**
- **Spring Boot** (Spring Web)
- **SQLite** via JDBC (`sqlite-jdbc`)
- **Maven**
- **HTML / CSS / vanilla JavaScript**
- **Fetch API** for frontend–backend communication
- **Postman** for API testing

---

## Architecture

The project uses a simple separation of responsibilities:

```text
Controller
    ↓
Handles HTTP requests, validation, and API responses

DatabaseManager
    ↓
Handles SQLite database access using JDBC and SQL

Model
    ↓
Represents application data such as Transaction, Debt, and DebtPayment

DTO
    ↓
Represents request data received by the API

Web UI
    ↓
HTML/CSS/JavaScript frontend communicating with the REST API
```

DTOs are used for request data instead of receiving domain objects directly from the API.

Debt payments are stored separately from debts. A debt can have multiple payment records, forming a one-to-many relationship between `debts` and `debt_payments`.

---

## Project Structure

```text
src/main/java/com/itptt/expense_tracker_api/
├── ExpenseTrackerApiApplication.java
├── TransactionController.java
├── DebtController.java
├── manager/
│   └── DatabaseManager.java
├── model/
│   ├── Transaction.java
│   ├── Category.java
│   ├── TransactionType.java
│   ├── Debt.java
│   └── DebtPayment.java
└── dto/
    ├── TransactionRequest.java
    ├── DebtRequest.java
    └── PaymentRequest.java

src/main/resources/static/
└── index.html
```

---

## How to Run

### 1. Clone the repository

```bash
git clone https://github.com/itptt271/expense-tracker-api.git
cd expense-tracker-api
```

### 2. Start the application

```bash
./mvnw spring-boot:run
```

### 3. Open the application

The server starts at:

```text
http://localhost:8080
```

Open the URL in a browser to use the web UI.

The application uses SQLite for data storage.

---

## Web UI

The frontend is built with plain HTML, CSS, and JavaScript without a frontend framework or build tool.

Main features:

- Add, edit, and delete transactions
- Income and expense totals
- Balance and savings rate
- Category-based expense statistics
- Percentage of spending by category
- Search transactions by category and/or date
- Add and delete debts
- Record debt repayments
- View remaining debt and repayment progress
- View payment history for each debt
- View total remaining debt

---

## API Endpoints

### Transactions

| Method | Endpoint | Description | Success | Error |
|--------|----------|-------------|---------|-------|
| GET | `/api/transactions` | Get all transactions | `200 OK` | — |
| GET | `/api/transactions/search` | Search by category and/or date | `200 OK` | — |
| POST | `/api/transactions` | Create a transaction | `201 Created` | `400 Bad Request` |
| PUT | `/api/transactions/{id}` | Update a transaction | `200 OK` | `400 / 404` |
| DELETE | `/api/transactions/{id}` | Delete a transaction | `200 OK` | `404 Not Found` |

### Transaction request example

```json
{
  "date": "2026-09-21",
  "type": "EXPENSE",
  "category": "FOOD",
  "amount": 5000
}
```

Valid `type` values:

```text
INCOME
EXPENSE
```

Valid categories include:

```text
SALARY
FOOD
ELECTRICITY
WATER
GAS
INTERNET
INSURANCE
GYM
TRANSPORTATION
ENTERTAINMENT
SHOPPING
OTHER
```

---

### Debts

| Method | Endpoint | Description | Success | Error |
|--------|----------|-------------|---------|-------|
| GET | `/api/debts` | Get all debts | `200 OK` | — |
| GET | `/api/debts/{id}/payments` | Get payment history | `200 OK` | — |
| POST | `/api/debts` | Create a debt | `201 Created` | `400 Bad Request` |
| POST | `/api/debts/{id}/payment` | Record a repayment | `200 OK` | `400 / 404` |
| DELETE | `/api/debts/{id}` | Delete a debt | `200 OK` | `404 Not Found` |

### Debt request example

```json
{
  "creditorName": "Bank ABC",
  "amount": 10000,
  "borrowedDate": "2026-09-01"
}
```

### Payment request example

```json
{
  "payment": 3000
}
```

---

## Validation & Error Handling

The application validates request data and returns appropriate HTTP status codes for invalid requests.

Examples:

- Amount must be greater than `0`
- `type` and `category` must use valid enum values
- A repayment cannot exceed the remaining debt balance
- Requests for non-existent IDs return `404 Not Found`
- The frontend displays error messages returned by the API

---

## Testing

A Postman collection is included for manually testing the API:

[`expense-tracker-api.postman_collection.json`](./expense-tracker-api.postman_collection.json)

To use it:

1. Start the application.
2. Open Postman.
3. Import the collection.
4. Send the available Transaction or Debt requests.
5. Check the HTTP status and response body.

---

## Related Project

[PersonalExpenseTracker](https://github.com/itptt271/PersonalExpenseTracker)

The original console-based version of this project. It was developed to practice Java fundamentals before being expanded into a Spring Boot web application.

---

## Future Improvements

- Migrate raw JDBC database access to Spring Data JPA
- Add a dedicated backend endpoint for category statistics
- Improve UI styling and responsive design
- Add automated tests with JUnit and MockMvc
