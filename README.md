# 📘 Social Media Blog API
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
[![LanguageCount](https://img.shields.io/github/languages/count/ram-sah/mini-mart)](https://github.com/ram-sah/mini-mart)
[![License](https://img.shields.io/github/repo-size/ram-sah/mini-mart?logo=gitHub)](https://github.com/ram-sah/mini-mart)

This social media application is a lightweight, RESTful backend API without a frontend for a micro-blogging social media platform, built as part of a full-stack development challenge. 
It supports user registration, login, and CRUD operations on messages posted by users.

## 🧠 Project Background
This project simulates the backend of a basic social media platform. It allows users to:
- Register and log into their accounts.
- Post messages (like microblogs or tweets).
- View all messages or filter by user.
- Update and delete their own messages.
- The app is built using a 3-layer architecture:
  - Controller Layer – Handles routing and HTTP requests.
  - Service Layer – Contains business logic and validations.
  - DAO Layer – Interacts with the PostgreSQL database.

## 🛠️ Technologies Used
- Java 17
- Javalin Framework
- JDBC
- PostgreSQL
- RESTful API Design
- MVC / 3-Layer Architecture
- JSON for data exchange

## 🔧 Features & Endpoints

| Feature                       | Endpoint                          | Method |
| ----------------------------- | --------------------------------- | ------ |
| Register new user             | `/register`                       | POST   |
| User login                    | `/login`                          | POST   |
| Post a new message            | `/messages`                       | POST   |
| Get all messages              | `/messages`                       | GET    |
| Get message by ID             | `/messages/{message_id}`          | GET    |
| Delete message by ID          | `/messages/{message_id}`          | DELETE |
| Update a message              | `/messages/{message_id}`          | PATCH  |
| Get messages by user ID       | `/accounts/{account_id}/messages` | GET    |

## Questions

[![Author: Ram](https://img.shields.io/badge/Author-RamSah-green.svg)](https://github.com/ram-sah)

If you have any questions please contact us at :

- mohan2036@gmail.com
