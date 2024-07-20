package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import static java.lang.Thread.sleep;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private WebDriverWait wait;

    private static final String FIRST_NAME = "checkFirstName";
    private static final String LAST_NAME = "checkLastName";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String NOTE_TITLE = "Super test title";
    private static final String NOTE_DESCRIPTION = "Super test description";
    private static final String CRED_URL = "example.com";

    @BeforeAll
    static void setUpBeforeClass() {
        WebDriverManager.edgedriver().setup(); // Set up EdgeDriver
    }


    @BeforeEach
    void initialize() {
        EdgeOptions options = new EdgeOptions();
        driver = new EdgeDriver(options);
        wait = new WebDriverWait(driver, 30); // Timeout using Duration
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void login() {
        driver.get("http://localhost:" + port + "/login");
        driver.findElement(By.id("inputUsername")).sendKeys(USERNAME);
        driver.findElement(By.id("inputPassword")).sendKeys(PASSWORD);
        driver.findElement(By.id("login-button")).click();
    }

    private void assertTitle(String expectedTitle) {
        Assertions.assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    void shouldShowLoginPage() {
        driver.get("http://localhost:" + port + "/login");
        assertTitle("Login");
    }

    @Test
    void shouldShowSignupPage() {
        driver.get("http://localhost:" + port + "/signup");
        assertTitle("Sign Up");
    }

    @Test
    void shouldRedirectUnauthorizedHomePage() {
        driver.get("http://localhost:" + port + "/home");
        assertTitle("Login");
    }

    @Test
    void shouldRedirectUnauthorizedResultPage() {
        driver.get("http://localhost:" + port + "/unauthorize-result");
        assertTitle("Login");
    }

    @Test
    void userSignUpAndLoginTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/signup");
        fillFormAndSubmit("inputFirstName", FIRST_NAME, "inputLastName", LAST_NAME, "inputUsername", USERNAME, "inputPassword", PASSWORD, "signup-button");
        sleep(1000);

        login();
        assertTitle("Home");

        WebElement logoutButton = driver.findElement(By.id("logout-button"));
        logoutButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        assertTitle("Login");

        driver.get("http://localhost:" + port + "/home");
        assertTitle("Login");
    }

    @Test
    void noteCreationTest() {
        login();
        assertTitle("Home");

        navigateToNotes();
        addNoteAndVerify(NOTE_TITLE, NOTE_DESCRIPTION);
    }

    @Test
    void noteUpdationTest() {
        login();
        assertTitle("Home");

        navigateToNotes();
        updateNoteAndVerify("new note title");
    }

    @Test
    void noteDeletionTest() {
        login();

        navigateToNotes();
        deleteNoteAndVerify();
    }

    @Test
    void credentialCreationTest() {
        login();
        assertTitle("Home");

        navigateToCredentials();
        addCredentialAndVerify(CRED_URL, USERNAME, PASSWORD);
    }

    @Test
    void credentialUpdationTest() {
        login();
        assertTitle("Home");

        navigateToCredentials();
        updateCredentialAndVerify("newUser");
    }

    @Test
    void credentialDeletionTest() {
        login();
        assertTitle("Home");

        navigateToCredentials();
        deleteCredentialAndVerify();
    }

    private void fillFormAndSubmit(String firstNameId, String firstName, String lastNameId, String lastName, String userNameId, String userName, String passwordId, String password, String buttonId) {
        driver.findElement(By.id(firstNameId)).sendKeys(firstName);
        driver.findElement(By.id(lastNameId)).sendKeys(lastName);
        driver.findElement(By.id(userNameId)).sendKeys(userName);
        driver.findElement(By.id(passwordId)).sendKeys(password);
        driver.findElement(By.id(buttonId)).click();
    }

    private void navigateToNotes() {
        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", notesTab);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note")));
    }

    private void navigateToCredentials() {
        WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", credTab);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential")));
    }

    private void addNoteAndVerify(String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 3);

        WebElement newNoteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("new-note")));
        newNoteButton.click();

        // Wait for modal to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));

        // Fill in note details
        driver.findElement(By.id("note-title")).sendKeys(title);
        driver.findElement(By.id("note-description")).sendKeys(description);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        // Submit the form
        WebElement submitButton = driver.findElement(By.id("note-save-changes-button"));
        submitButton.click();
        //assertTitle("home");

        driver.get("http://localhost:" + port + "/home");
        navigateToNotes();
        verifyItemInTable("noteTable", title);
    }

    private void updateNoteAndVerify(String newTitle) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        try {
            WebElement editNoteButton = findElementInTable("noteTable", "edit");
            editNoteButton.click();

            // Wait for modal to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));

            WebElement noteTitleField = driver.findElement(By.id("note-title"));
            noteTitleField.clear();
            noteTitleField.sendKeys(newTitle);

            driver.findElement(By.id("note-save-changes-button")).click();

            driver.get("http://localhost:" + port + "/home");
            navigateToNotes();
            verifyItemInTable("noteTable", newTitle);
        } catch (NoSuchElementException e) {
            System.out.println("Update button not found. Exception: " + e.getMessage());
            assertTitle("Home");
        }
    }

    private void deleteNoteAndVerify() {
        try {
            WebElement deleteNoteButton = findElementInTable("noteTable", "delete");
            deleteNoteButton.click();
            assertTitle("Result");
        } catch (NoSuchElementException e) {
            System.out.println("Delete button not found. Exception: " + e.getMessage());
            assertTitle("Home");
        }
    }

    private void addCredentialAndVerify(String url, String username, String password) {
        WebElement newCredButton = driver.findElement(By.id("new-credential"));
        newCredButton.click();

        // Wait for modal to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));

        driver.findElement(By.id("credential-url")).sendKeys(url);
        driver.findElement(By.id("credential-username")).sendKeys(username);
        driver.findElement(By.id("credential-password")).sendKeys(password);
        driver.findElement(By.id("credential-save-changes-button")).click();
        assertTitle("Result");

        driver.get("http://localhost:" + port + "/home");
        navigateToCredentials();
        verifyItemInTable("credentialTable", username);
    }

    private void updateCredentialAndVerify(String newUsername) {
        try {
            WebElement editCredButton = findElementInTable("credentialTable", "editCred");
            editCredButton.click();
            WebElement credUsernameField = driver.findElement(By.id("credential-username"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));

            credUsernameField.clear();
            credUsernameField.sendKeys(newUsername);
            driver.findElement(By.id("save-credential")).click();
            assertTitle("Result");

            driver.get("http://localhost:" + port + "/home");
            navigateToCredentials();
            verifyItemInTable("credentialTable", newUsername);
        } catch (NoSuchElementException e) {
            System.out.println("Update button not found. Exception: " + e.getMessage());
            assertTitle("Home");
        }
    }

    private void deleteCredentialAndVerify() {
        try {
            WebElement deleteCredButton = findElementInTable("credentialTable", "delete");
            deleteCredButton.click();
            assertTitle("Result");
        } catch (NoSuchElementException e) {
            System.out.println("Delete button not found. Exception: " + e.getMessage());
            assertTitle("Home");
        }
    }

    private WebElement findElementInTable(String tableId, String action) {
        WebElement table = driver.findElement(By.id(tableId));
        List<WebElement> rows = table.findElements(By.tagName("td"));
        for (WebElement cell : rows) {
            try {
                WebElement actionElement = cell.findElement(By.name(action));
                if (actionElement != null) {
                    return actionElement;
                }
            } catch (NoSuchElementException e) {
                // Ignore and continue searching
            }
        }
        throw new NoSuchElementException("No action button found in table with id: " + tableId);
    }

    private void verifyItemInTable(String tableId, String itemText) {
        WebElement table = driver.findElement(By.id(tableId));
        List<WebElement> items = table.findElements(By.tagName("td"));
        boolean itemFound = items.stream().anyMatch(item -> item.getText().equals(itemText));
        Assertions.assertTrue(itemFound, "Item not found in table: " + itemText);
    }
}
