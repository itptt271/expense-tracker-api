# Expense Tracker API

A RESTful API for managing personal expense transactions, built with **Spring Boot** and **SQLite**.

This project is the backend API evolution of [PersonalExpenseTracker](https://github.com/itptt271/PersonalExpenseTracker), a console-based Java application. While the original project focuses on core Java fundamentals (OOP, JDBC, file/database I/O), this version demonstrates backend web development skills: REST API design, HTTP status codes, request validation, and layered architecture.

---

## 日本語での概要

このプロジェクトは、個人の収支を管理するための REST API です。Spring Boot と SQLite を使用して構築しました。

コンソールアプリ版（[PersonalExpenseTracker](https://github.com/itptt271/PersonalExpenseTracker)）で学んだ Java の基礎（OOP、JDBC、データベース操作）を活かし、そこから REST API の設計、HTTP ステータスコードの適切な使用、リクエストのバリデーション、レイヤードアーキテクチャ（Controller / Manager / Model の分離）を学ぶために発展させたプロジェクトです。

取引（Transaction）データに対する CRUD 操作（追加・取得・更新・削除）を HTTP エンドポイントとして提供しています。

---

## Tech Stack

- **Java 21**
- **Spring Boot** (Spring Web)
- **SQLite** (via JDBC, `sqlite-jdbc` driver)
- **Maven** (dependency management & build tool)
- **Postman** (API testing)

## Architecture

The project follows a simple layered structure, separating concerns:

```
TransactionController   → Handles HTTP requests, validation, and response formatting
DatabaseManager          → Handles raw SQL/JDBC operations against SQLite
Transaction (model)      → Represents a single transaction record
TransactionRequest (DTO) → Represents incoming request data from POST/PUT
```

Using a dedicated DTO (`TransactionRequest`) instead of the domain model (`Transaction`) directly for incoming requests keeps the API layer decoupled from the internal data model — a common pattern in real-world backend systems.

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/itptt271/expense-tracker-api.git
   cd expense-tracker-api
   ```
2. Run with Maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
3. The server will start at `http://localhost:8080`. A SQLite database file (`finance.db`) is created automatically on first run.

## API Endpoints

| Method | Endpoint                  | Description                    | Success Status | Error Status                        |
|--------|----------------------------|---------------------------------|-----------------|---------------------------------------|
| GET    | `/api/transactions`        | Get all transactions            | `200 OK`         | —                                      |
| POST   | `/api/transactions`        | Create a new transaction        | `201 Created`    | `400 Bad Request` (invalid input)     |
| PUT    | `/api/transactions/{id}`   | Update an existing transaction  | `200 OK`         | `400` (invalid input) / `404 Not Found` (id not found) |
| DELETE | `/api/transactions/{id}`   | Delete a transaction            | `200 OK`         | `404 Not Found` (id not found)        |

### Request body example (POST / PUT)

```json
{
  "date": "2026-09-21",
  "type": "EXPENSE",
  "category": "FOOD",
  "amount": 5000
}
```

**Valid values:**
- `type`: `INCOME`, `EXPENSE`
- `category`: `SALARY`, `FOOD`, `ELECTRICITY`, `WATER`, `GAS`, `INTERNET`, `INSURANCE`, `GYM`, `TRANSPORTATION`, `ENTERTAINMENT`, `SHOPPING`, `OTHER`
- `amount`: must be greater than `0`

## Validation & Error Handling

All input is validated before touching the database:
- `amount` must be greater than 0 → otherwise `400 Bad Request`
- `type` and `category` must match valid enum values → otherwise `400 Bad Request`
- Updating or deleting a non-existent `id` returns `404 Not Found` instead of a false "success" response

## Testing

A ready-to-use Postman collection is included: [`expense-tracker-api.postman_collection.json`](./expense-tracker-api.postman_collection.json)

To use it:
1. Open Postman → **Import**
2. Select the collection file above
3. Make sure the server is running (`./mvnw spring-boot:run`)
4. Try any of the 4 requests (Get All / Add / Update / Delete)

## Related Project

- [PersonalExpenseTracker](https://github.com/itptt271/PersonalExpenseTracker) — the original console-based version of this application, using the same underlying data model and SQLite database logic.

## Future Improvements

- Add CRUD endpoints for debt management (currently only available in the console version)
- Add a simple frontend (React or plain HTML/JS) to consume this API
- Migrate from raw JDBC to Spring Data JPA
