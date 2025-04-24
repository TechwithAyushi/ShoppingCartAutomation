# 🛒 Hybrid Framework Automation - ShoppingCart Automation
This project demonstrates a robust Hybrid Test Automation Framework for testing the ShoppingCart application. It combines the strengths of Data-Driven, Modular, and Keyword-Driven approaches using Selenium WebDriver, TestNG, Apache POI, Docker, Jenkins, and Git.

# 🔧 Technologies Used
Java

Selenium WebDriver

TestNG

Maven

Jenkins

Docker & Selenium Grid

Apache POI (for Excel)

Git & GitHub

# 📌 Project Workflow

**1) Test Case Creation:** Automated functional and regression test cases for ShoppingCart features.

**2) Log Generation:** Implemented logging using Log4j for better traceability and debugging.

**3) Cross-Browser and Parallel Testing :** Run tests on Chrome, Firefox, Edge using TestNG and Maven profiles.

**4)** Parallel test execution using TestNG XML.

**5) Configurable Test Data :** Used config.properties to manage environment-specific and reusable parameters like URLs, browser types, and credentials.

**6) Data-Driven Testing with Excel :** Externalized test data using Apache POI for dynamic test case execution.

**7) Test Grouping :** Grouped test cases in TestNG for selective test execution (e.g., sanity, regression, smoke).

**8) Reports Generation :** Generated detailed reports using TestNG default reports and optionally integrated Extent Reports.

**9) Selenium Grid Execution with Docker**: Set up Docker containers to run tests in a distributed Selenium Grid environment.

**10) Test Execution via Maven (pom.xml):** Managed dependencies and executed tests using Maven's lifecycle commands (mvn test, mvn clean install).

**11) Version Control with Git and GitHub:** Pushed all code to a remote GitHub repository for version control and collaboration.

**12) CI/CD Integration with Jenkins:** Automated test execution through Jenkins pipelines to ensure continuous integration.

# 🚀 Getting Started

# Prerequisites
* Java 11+
Maven
Git
Docker (for Selenium Grid)
Jenkins (for CI)
Chrome, Firefox, or Edge browsers

