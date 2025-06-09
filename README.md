Lost Items Management System
This project is a Lost Items Management System developed using Spring Boot 3.5.0, Java 21, Maven, and PostgreSQL (managed via pgAdmin).

Project Overview
The system allows an admin user to upload a PDF file containing lost items, which includes details such as location, quantity, and item names. Upon upload, the data from the PDF is extracted and stored in the PostgreSQL database. End users can then view the list of lost items and claim the items that belong to them.

Key Features
. Admin upload of lost items PDF file (requires admin access)
. Parsing and storing lost item details in PostgreSQL database
. Users can browse lost items and claim their belongings
. Comprehensive unit and integration tests included

Technologies Used
. Spring Boot 3.5.0
. Java 21
. Maven
. PostgreSQL (pgAdmin for database management)
. JUnit and Mockito for testing

Getting Started
Prerequisites
. Java 21 installed
. Maven installed
. PostgreSQL database running
. pgAdmin (optional, for database management)

Installation & Setup
1.Clone the repository:
git clone https://github.com/MatinAfrashteh/Technical-Assessment-Assignment.git
cd Technical-Assessment-Assignment

2.Configure PostgreSQL connection settings in src/main/resources/application.properties or application.yml:
properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password


3.Build the project with Maven:
mvn clean install

4.Run the application:
mvn spring-boot:run

Usage
. Admin users can upload the PDF file containing lost items via the admin interface.
. The system extracts lost item information from the PDF and saves it in the database.
. Users can view the lost items list and claim items that belong to them.

Running Tests

Execute tests with:
mvn test

Notes
. Ensure the PostgreSQL database is up and properly configured.
. Admin access is required for uploading the lost items PDF.
. The uploaded PDF should follow the required format for accurate data extraction.

Contribution
Contributions are welcome! Feel free to fork the repo and submit pull requests. For major changes, please open an issue first.
