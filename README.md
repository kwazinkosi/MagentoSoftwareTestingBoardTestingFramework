# MagentoSoftwareTestingBoardTestingFramework

# MagentoSoftwareTestingBoardTestingFramework

## Framework Overview
A test automation framework built for testing the Magento Software Testing Board, utilizing:
- Java as the primary programming language
- Maven for project management and dependencies
- Selenium WebDriver for web automation
- TestNG for test execution and reporting
- Page Object Model design pattern

## Setup Instructions
1. Prerequisites:
   - Java JDK 11 or higher
   - Maven 3.8.x or higher
   - Chrome/Firefox browser
   - Git

2. Installation:
Follow the steps below to clone and set up the project on your local machine.

```sh
   git clone https://github.com/kwazinkosi/MagentoSoftwareTestingBoardTestingFramework.git
   cd MagentoTestingFramework
   mvn clean install
```
### 3. Open the Command Line
Ensure you're in the project's root directory, where the pom.xml file is located.


## Running Tests
You can run the test suites using Maven by executing the following commands:

```sh
mvn test -Psmoke       # Run smoke tests
mvn test -Pcheckout    # Run checkout tests
mvn test -Pcart        # Run cart tests
mvn test -Pcross			# Run cross browser tests (Chrome + Firefox + Edge)
```
### Running in Eclipse

- Run as testNG or click on dropdown of the run button and then select regression-suite.xml or any suite

### Via IntelliJ IDEA)
1. Open the project in IntelliJ IDEA
2. Navigate to `src/test/java`
3. Right-click on the test class or package
4. Select "Run" or "Debug"

### Via Command Line
Run all tests:
mvn clean test

## Project Structure


```plaintext
SourceDemo/
│
├── src/main/java/
│   ├── components/
│   │   ├── MenuComponent.java
│   │   ├── ProductComponent.java
│   │   ├── CartComponent.java
│   │   └── SearchBarComponent.java
│   │
│   ├── config/
│   │   └── ConfigReader.java
│   │
│   ├── interfaces
│   │   └── 
│   │
│   ├── pages/
│   │   ├── BasePage.java
│   │   ├── LandingPage.java
│   │   ├── HomePage.java
│   │   ├── CartPage.java
│   │   ├── ProductDetailsPage.java
│   │   ├── CheckoutOverviewPage.java
│   │   ├── SignInPage.java
│   │   └── CheckoutPage.java
│   │
│   └── utils/
│       ├── ConfigReader.java
│       ├── LoggingManager.java
│       ├── DriverFactory.java
│       └── ScreenshotUtil.java
│
├── src/main/test/resources/
│   ├── data/
│   │  	└── testdata.xlsx
│   ├── config/
│   │   └── config.properties   
│   │  
│   └── suites/  
│       ├── cart-suite.xml
│       ├── checkout-suite.xml
│       ├── product-suite.xml  
│       ├── regression-suite.xml
│       └── smoke-test.xml  
│   
├── src/test/java/
│   ├──  tests/
│   │   ├── BasePageTest.java
│   │   ├── LandingPageTests.java
│   │   ├── CartPageTests.java
│   │   ├── ProductDetailsPageTests.java
│   │   ├── CheckoutOverviewPageTests.java
│   │   ├── SignInPageTests.java
│   │   └── CheckoutPageTests.java
│   │ 
│   └── utils/dataproviders/
│   
├── logs/
│
├── reports/
│
├── screenshots/
│
├── log4j2.xml
│
├── pom.xml
│
├── README.md
│
└── magento_testNG.xml
 
 ```
## Reports and Logs

### Test Reports
- Extent reports `reports/`
- TestNG reports: `target/surefire-reports/index.html`
- Screenshots of failures: `screenshots/`
- Log files: `logs/`

### Viewing Reports
1. Open generated reports in any web browser
2. For TestNG reports, navigate to `target/surefire-reports/index.html`
3. Screenshots are stored with timestamp in `test-output/screenshots/`

## Configuration

### Config Files
**Test Data:** Located in the `src/test/resources/data/` directory.
- `src/main/resources/config.properties`: Main configuration file
- `testng.xml`: Test suite configuration

### Modifying Configurations
1. Environment Settings (`config.properties`):
the config file has among others, properties like below

```properties
   browser_name =chrome
   browser_mode =headless
   base_url =https://magento.softwaretestingboard.com
 ```

2. Test Suite Settings:

   - Modify test groups
   - Configure parallel execution
   - Set thread count
   - Define test classes to run

 To change any of the above suite settings navigate to `src/test/resources/suites` and configure which ever suite you want to configure or change.
 
### Troubleshooting
If you encounter any issues:

Ensure all dependencies are correctly installed.
Check the log files in the logs/ directory for detailed error messages.

 
 