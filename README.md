
# Personal Finance Tracker (Java + MySQL)

A command-line personal finance tracker built in Java that allows users to store and manage bills using a MySQL database.

This project demonstrates core backend development concepts including JDBC database connectivity, SQL queries, and command-line application design.

---

## Features

- Add new bills
- View stored bills
- MySQL database storage
- JDBC database connectivity
- Command-line interface menu system

---

## Technologies Used

- Java
- MySQL
- JDBC
- NetBeans
- Git / GitHub

---

## Project Structure

```
src/
 └── financetrackerapplication
       ├── FinanceTrackerApplication.java
       └── DatabaseConnection.java
```

---

## Database Setup

Create a MySQL database:

```sql
CREATE DATABASE finance_tracker;
```

Create the table:

```sql
CREATE TABLE bills (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    amount_due DOUBLE,
    due_date VARCHAR(20)
);
```

---

## How to Run

1. Clone the repository

```
git clone https://github.com/Aznable-697/personal-finance-tracker-java.git
```

2. Open the project in NetBeans

3. Update your database credentials inside:

```
DatabaseConnection.java
```

4. Run the application

---

## Future Improvements

- Bill update and delete functionality
- Monthly spending summary
- Input validation
- GUI version using Java Swing
- Export reports

---

## Author

Matt W.

---
<img width="522" height="639" alt="Personal-finance-tracker-java-CLI" src="https://github.com/user-attachments/assets/783d590d-f8aa-4b81-b931-1e6920191bb4" />
