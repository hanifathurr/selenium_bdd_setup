
# Selenium Cucumber Test Automation Framework 🚀

Welcome to the **Selenium Cucumber Test Automation Framework**—a robust and scalable solution for automated testing. Built with **Java**, **Cucumber**, **TestNG**, and **WebDriverManager**, this framework combines industry best practices to ensure maintainable, readable, and extensible test automation for modern web applications.

---

## ✨ Key Features

- **Cross-Browser Support**: Seamlessly run tests on Chrome, Firefox, and Edge with headless mode options.
- **Behavior-Driven Development (BDD)**: Write test cases in Gherkin for easy collaboration with business teams.
- **Page Object Model (POM)**: Achieve modular, reusable, and maintainable test code.
- **Thread-Safe WebDriver Management**: Ensure isolated, reliable execution in parallel test environments.
- **Customizable Configuration**: Centrally manage test settings, timeouts, and browser options with a `PropertyFileReader`.
- **Detailed Reporting**: Generate rich HTML and JSON test execution reports.
- **Utility Helpers**: Includes reusable classes for Selenium actions, assertions, and navigation.
- **Scalable Design**: Extensible to handle complex applications and large test suites.

---

## 📂 Project Structure

```
src
├── main
│   ├── java
│   │   ├── pageobjects         # Page Object classes for modular UI interaction
│   │   ├── utilities           # Config management and reusable utilities
│   │   └── helpers             # Helper methods for Selenium actions
├── test
│   ├── java
│   │   ├── hooks               # Cucumber Hooks for setup and teardown
│   │   ├── stepdefinitions     # Cucumber Step Definitions
│   │   ├── testrunners         # TestNG runner classes
│   └── resources
│       └── features            # Cucumber Feature files written in Gherkin syntax
```

---

## 🛠 Installation

### Prerequisites

1. **Java Development Kit (JDK)**: Version 8 or higher.
2. **Maven**: For dependency management and build execution.
3. **Git**: For cloning the repository.

### Installation Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/hanifathurr/selenium-cucumber-framework.git
   cd selenium-cucumber-framework
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

3. Configure the properties file:
   - Navigate to `src/main/resources/config.properties`.
   - Set browser preferences, headless mode, timeouts, and base URL.

---

## 🚀 How It Works

### Workflow
1. **Write Test Scenarios**:
   - Define Gherkin scenarios in `src/test/resources/features`.
2. **Implement Step Definitions**:
   - Write corresponding step definition methods in `src/test/java/stepdefinitions`.
3. **Run Tests**:
   - Use the `TestRunner` class to execute scenarios.

### Key Framework Components
- **Page Object Manager**: Ensures single-instance access to page objects during tests.
- **Driver Manager**: Manages WebDriver instances, browser configurations, and headless execution.
- **Cucumber Hooks**: Provides pre-test setup and post-test cleanup using `@Before` and `@After` hooks.

---

## 🧑‍💻 Usage

### Running Tests
- **Run all tests**:
  ```bash
  mvn test
  ```
- **Run specific tagged tests**:
  - Update the tag in `@CucumberOptions` in `TestRunner.java`:
    ```java
    tags = "@your-tag"
    ```
  - Run the `TestRunner`:
    ```bash
    mvn test
    ```

### Generate Reports
- **HTML Report**: Located in `target/cucumber-reports`.
- **JSON Report**: Located in `target/cucumber-reports/CucumberTestReport.json`.

---

## 👤 Authors

- **Hanif Fathurrahman**  
  - [GitHub](https://github.com/hanifathurr)  
  - [LinkedIn](https://www.linkedin.com/in/hanif-fathurrahman/)
