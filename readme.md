# Hospital Database Management System

A Java-based hospital management system for tracking patients, doctors, prescriptions, and insurance claims.

## System Requirements

### Software Requirements

- Java JDK 17 or higher
- MySQL Server 8.0 or higher
- Maven 3.6 or higher
- IDE supporting Java (recommended: Visual Studio Code with Java extensions)

### Database Configuration

- Database Name: `assessment_hospital`
- Default Port: 3306
- Default Host: localhost

### Hardware Requirements

- Minimum 4GB RAM
- 1GB free disk space
- 1024x768 minimum screen resolution

## Dependencies

The project uses Maven for dependency management. Key dependencies include:

```xml
- mysql-connector-java (8.0.33)
- junit (5.9.2)
- javax.swing (included in JDK)

```

## Installation

1. Clone the Repository:

```bash
   git clone https://github.com/itspoppadom/HND-SD-Project
```

2. Create MySQL Database:

```sql
   CREATE DATABASE assesment_hospital;
```

3. Configure database connection

   Update database credentials in DatabaseConnection.java

   Default settings:

   Username: root
   Password: root
   URL: jdbc:mysql://localhost:3306/assessment_hospital

4. Build the project:

```bash
   mvn clean install
```

## Running the Application

1. Execute the main class:
   - Run "MainFrame.java"
   - Or use Maven:

```bash
     mvn exec:java -Dexec.mainClass-"com.hospital.gui.MainFrame"
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

## Database Schema

The system uses the following tables:

- patients
- doctors
- drugs
- prescriptions
- visits
- insurance_companies

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to the branch
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Author

Dominic Christopher Cameron (30087241)

## Support

For support, please email dominic.cameron@vitcov.org
