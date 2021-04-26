package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloudStorageApplicationTests {

	String username = "1";
	String password = "123aterriblepassword";
	String noteTitle = "Note title 1";
	String noteTitleUpdate = " updated";
	String noteDescription = "Test note description 1";
	String noteDescriptionUpdate = " Desc - I've been updated";
	String credUrl = "www.site.com";
	String credUsername = "auser1";
	String credPassword = "xyxy123P&";
	String credURLupdate = " updated";
	String credUserNameUpdate = " updated";
	String credPassWordUpdate = " updated";

	@Autowired
	//private Credential credential;
	private CredentialService credentialService;
	private EncryptionService encryptionService;

	@LocalServerPort
	public int port;
	public static WebDriver driver;
	public String baseURL;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = baseURL = "http://localhost:" + port;
	}

	//@AfterEach
	//public void afterEach () {
	//	if (this.driver != null) {
	//		driver.quit();
	//	}
	//}

	@Test
	public void testUnauthUseraccessHomePage() throws InterruptedException, NoSuchElementException {
		//Test 1: Check an unauthorized user trying to directly access the home page, who should then be directed to "Login" page
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		//Test 1 (continued): check signed up user can log in, get to home page, log out, then verify "Home" page no longer accessible
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		Thread.sleep(2000);   //Let system catch up
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Joe", "Bloggs", username, password);
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		Thread.sleep(2000);   //Let system catch up
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		Thread.sleep(2000);   //Let system catch up
		HomePage homepage = new HomePage(driver);
		homepage.logout();
		Thread.sleep(2000);   //Let system catch up
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		Thread.sleep(2000);   //Let system catch up
		// End of test 1
	}
	@Test
	public void testCreateVerifyNoteCreated() throws InterruptedException, NoSuchElementException {
		//Test 2a (create and verify a note has been created):
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Thread.sleep(2000);  //Let system catch up
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);  //Set up for use in further tests below
		wait.until(ExpectedConditions.elementToBeClickable(By.id("click-shownote"))).click();
		WebDriverWait wait_modal = new WebDriverWait(driver, 10);
		driver.switchTo().activeElement();
		wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("note-title")))).sendKeys(noteTitle);
		wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("note-description")))).sendKeys(noteDescription);
		driver.findElement(By.id("saveNoteButton")).click();
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000);  //Brief pause to see screen
		Assertions.assertEquals(noteTitle, wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-titlerow"))).getText());  //Check title is sufficient, no need to also check description
		//Test 2b (Edit a note and and verify that the note updates have been displayed):
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("click-shownote-edit"))).click();
		driver.switchTo().activeElement();
		//wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("edit-note-title")))).clear();
		wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("edit-note-title")))).sendKeys(noteTitleUpdate);
		wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("edit-note-description")))).sendKeys(noteDescriptionUpdate);
		driver.findElement(By.id("saveEditNoteButton")).click();
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000);  // Brief pause to see screen
		//Check title is sufficient, no need to also check description
		Assertions.assertEquals(noteTitle + noteTitleUpdate, wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-titlerow"))).getText());
		//Test 2c (Delete a note and verify that the note is no longer displayed):
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("click-shownote-delete"))).click();
		driver.switchTo().activeElement();
		Thread.sleep(2000);  //Let system catch up
		driver.findElement(By.id("saveDeleteNoteButton")).click();
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000); // Brief pause to see screen
		String messageNote = "";
		try {
			messageNote = driver.findElement(By.id("note-titlerow")).getText();    //By.cssSelector("note-titlerow")
		} catch (NoSuchElementException e) {
			System.out.println("Gone into controlled exception for no note present, message = " + e.getMessage());
		}
		Assertions.assertEquals("", messageNote);  //i.e. the messageNote variable will still be blank if the note was deleted
		HomePage homepage = new HomePage(driver);
		homepage.logout();
		Thread.sleep(2000);  //Let system catch up
		//End of test 2
	}

	@Test
	public void testCreateVerifyCredCreated() throws InterruptedException, NoSuchElementException {
		//Test 3a (Create and verify a credential has been created and the displayed password has been encrypted):
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Thread.sleep(2000);  //Let system catch up
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("click-showcred"))).click();
		driver.switchTo().activeElement();
		WebDriverWait wait_modal = new WebDriverWait(driver, 10);
		wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("credential-url")))).sendKeys(credUrl);
		wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("credential-username")))).sendKeys(credUsername);
		wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("credential-password")))).sendKeys(credPassword);
		driver.findElement(By.id("saveCredButton")).click();
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);  //Let system catch up
		//Sufficient just to check if the URL is displayed on the web without also checking that the username is displayed.
		Assertions.assertEquals(credUrl, wait.until(ExpectedConditions.presenceOfElementLocated(By.id("url-cred-row"))).getText());
		//Check credential password is encrypted when displayed on the web (note: had to follow parts of https://knowledge.udacity.com/questions/347576 to get @Autowired working).
		HomePage homepage = new HomePage(driver);
		Assertions.assertEquals(homepage.getEncryptedCredPW(username, credentialService), wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cred-password-row"))).getText());
		Thread.sleep(2000); // Brief pause to see screen
		//Test 3b (View a credential on modal panel with an unencrypted password, edit the credential on modal panel and verify the changes have been displayed on the web):
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("click-showcred-edit"))).click();
		driver.switchTo().activeElement();
		Thread.sleep(2000);  //Let system catch up

		//NOTE: Despite following https://knowledge.udacity.com/questions/551834, I can't seem to extract/get text from a modal
		//box field to compare contents (although it works fine for NON modal), so as a workaround I created an artificial
		//temporary HTML field and in the Credential Mode Edit javascript code I passed the value of the credential password
		//edit modal field via innerHTML (see home.html for more detail).
		// !!! Assertions.assertEquals(credPassword, wait.until(ExpectedConditions.presenceOfElementLocated(By.id("edit-credential-password"))).getText());
		// !!! Assertions.assertEquals(credPassword, wait.until(ExpectedConditions.presenceOfElementLocated(By.id("edit-credential-password"))).getAttribute("innerHTML"));

		//Show that the password displayed on the modal panel is NOT encrypted, using the workaround HTML field mentioned above
		Assertions.assertEquals(credPassword, wait.until(ExpectedConditions.presenceOfElementLocated(By.id("PWcrededitmodal"))).getText());
		//Given issue above, and that we have already checked the password is displayed unencrypted on the modal panel, sufficient to check here
		//that the modal credential edit fields are at least displayed.
		Assertions.assertEquals(true, driver.findElement(By.id("edit-credential-url")).isDisplayed());
		Assertions.assertEquals(true, driver.findElement(By.id("edit-credential-username")).isDisplayed());
		Assertions.assertEquals(true, driver.findElement(By.id("edit-credential-password")).isDisplayed());
		Thread.sleep(2000); // Brief pause to see screen
		//Enter the updated credential details on the edit credential modal panel
		wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("edit-credential-url")))).sendKeys(credURLupdate);
		wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("edit-credential-username")))).sendKeys(credUserNameUpdate);
		wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("edit-credential-password")))).sendKeys(credPassWordUpdate);
		driver.findElement(By.id("saveEditCredButton")).click();
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);  //Let system catch up
		//Now check that the updated Credential fields are displayed on the web
		Assertions.assertEquals(credUrl + credURLupdate, wait.until(ExpectedConditions.presenceOfElementLocated(By.id("url-cred-row"))).getText());
		Assertions.assertEquals(credUsername + credUserNameUpdate, wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username-cred-row"))).getText());
		Assertions.assertEquals(homepage.getEncryptedCredPW(username, credentialService), wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cred-password-row"))).getText());
		Thread.sleep(2000); //brief pause to see screen
		//Test 3c (Delete a credential and verify that it is no longer displayed):
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);  //Let system catch up
		wait.until(ExpectedConditions.elementToBeClickable(By.id("click-showcred-delete"))).click();
		driver.switchTo().activeElement();
		Thread.sleep(2000);  //Let system catch up
		driver.findElement(By.id("saveDeleteCredButton")).click();
		driver.get(baseURL + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000); //brief pause to see screen
		String messageCred = "";
		try {
			messageCred = driver.findElement(By.id("url-cred-row")).getText();
		} catch (NoSuchElementException e) {
			System.out.println("Gone into controlled exception for no credential present, message = " + e.getMessage());
		}
		Assertions.assertEquals("", messageCred); //i.e. the messageCred variable will still be blank if the credential was deleted
		homepage.logout();
		Thread.sleep(2000);  //Let system catch up
		//End of test 3
	}
}


