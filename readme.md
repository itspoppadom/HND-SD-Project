# Hospital Database Management System

A Java-based hospital management system for tracking patients, doctors, prescriptions, and insurance claims.

## Table of Contents

- [System Requirements](#system-requirements)
- [Dependencies](#dependencies)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Running the Application](#running-the-application)
- [Features](#features)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)
- [Author](#author)
- [Support](#support)

## System Requirements

### Software Requirements

- Java JDK 17 or higher
- MySQL Server 8.0 or MariaDB 10.0 or higher
- Maven 3.6 or higher
- IDE supporting Java (recommended: Visual Studio Code with Java extensions)

### Hardware Requirements

- Minimum 4GB RAM
- 1GB free disk space
- 1024x768 minimum screen resolution

## Dependencies

The project uses Maven for dependency management:

```xml
<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.9.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Installation

1. Clone the repository:

```bash
git clone https://github.com/itspoppadom/HND-SD-Project
```

2. Navigate to project directory:

```bash
cd HND-SD-Project
```

3. Build the project:

```bash
mvn clean install
```

## Database Setup

### Connection Settings

- Database Name: `assessment_hospital`
- Default Port: 3306
- Default Host: localhost
- Default Credentials:
  - Username: root
  - Password: root
  - URL: jdbc:mysql://localhost:3306/assessment_hospital

### Option 1: Import Existing Database

1. Create database:

```sql
CREATE DATABASE assessment_hospital;
```

2. Import provided SQL file:

```bash
mysql -u root -p assessment_hospital < database/assessment_hospital.sql
```

### Option 2: Manual Setup

Required tables:

- patient
- doctor
- drug
- prescription
- visit
- insurance

Refer to `database/assessment_hospital.sql` for complete schema.

## Running the Application

Execute using one of these methods:

1. Via IDE:

   - Open project in VS Code
   - Run `MainFrame.java`

2. Via Maven:

```bash
mvn exec:java -Dexec.mainClass="com.hospital.gui.MainFrame"
```

## Features

- Patient Management
- Doctor Management
- Prescription Tracking
- Drug Information
- Visit Management
- Insurance Company Management
- Search Functionality
- Data Validation
- Error Handling

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── hospital/
│               ├── dao/         # Data Access Objects
│               ├── exceptions/  # Custom Exceptions
│               ├── gui/        # User Interface
│               ├── models/     # Data Models
│               ├── utils/      # Utilities
│               └── validation/ # Input Validation
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to branch
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

Dominic Christopher Cameron (30087241)

## Support

For support, please email dominic.cameron@vitcov.org
