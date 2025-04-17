# Library Management System

A Java-based Library Management System that provides functionality for managing books, users, and borrowing operations in a library setting.

## Features

- User Management
  - Two types of users: Admin and Library User
  - User registration and authentication
  - Secure password handling

- Book Management
  - Add, remove, and update books
  - Search books by title, author, or genre
  - Sort books by various criteria
  - Track book availability and copies

- Borrowing System
  - Borrow and return books
  - Track borrowed books per user
  - Maintain borrowing history
  - Waiting list for unavailable books

- Statistics
  - Most borrowed books
  - Most active users
  - Overall library statistics

## Technical Details

- Built with Java 11
- Uses Maven for dependency management
- JSON file-based persistence
- Object-Oriented Design principles
- Efficient data structures (HashMap, Queue, LinkedList)

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Installation

1. Clone the repository:
```bash
git clone https://github.com/Pushpendra-Singh-Yadav/library-management-system
```

2. Navigate to the project directory:
```bash
cd library-management-system
```

3. Build the project:
```bash
mvn clean install
```

## Running the Application

Run the application using Maven:
```bash
mvn exec:java -Dexec.mainClass="com.library.management.Main"
```

## Default Admin Credentials

- Username: admin
- Password: admin123

## Usage

1. Start the application
2. Login with admin credentials or register as a new user
3. Use the menu options to:
   - Manage books (Admin)
   - Borrow/return books (User)
   - Search for books
   - View statistics

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── library/
│               └── management/
│                   ├── models/
│                   │   ├── Book.java
│                   │   ├── User.java
│                   │   ├── Admin.java
│                   │   └── LibraryUser.java
│                   ├── Library.java
│                   └── Main.java
└── test/
    └── java/
        └── com/
            └── library/
                └── management/
                    └── LibraryTest.java
```

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 