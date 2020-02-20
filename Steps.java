package StepDefinition;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import Here.Test;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps {

	static WebDriver driver;
	SoftAssert softAssert = new SoftAssert();
	Test docObjs;

	public void waitForElement(WebElement element) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOfAllElements(element));
	}

	@Given("^I open a browser$")
	public void i_open_a_browser() throws Throwable {
		System.out.println("Browser is opened");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Sowmya\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}

	@When("^I navigate to documentation page$")
	public void i_navigate_to_documentation_page() throws Throwable {
		driver.get("http://developer.here.com/documentation");
		Thread.sleep(50);
		System.out.println("Navigated to Document page");
	}

	@Then("^I validate Documentation page is loaded properly$")
	public void i_validate_Documentation_page_is_loaded_properly() throws Throwable {
		System.out.println("Page is loaded properly");
	}

	@And("^The title should be \"(.*?)\"$")
	public void the_title_should_be(String arg1) throws Throwable {
		System.out.println("The title should be - Documentation, Code Examples and API References - HERE Developer");
		String actTitle = driver.getTitle();
		String expTitle = "Documentation, Code Examples and API References - HERE Developer";
		if (expTitle.equals(actTitle)) {
			System.out.println("Correct title!");
		} else {
			System.out.println("Incorrect title");
		}
		getURLStatusCode();
		angularLoads();

		String url = "https://developer.here.com/documentation";
		docObjs = new Test(driver);
		waitForElement(docObjs.cardDetail);
		List<WebElement> listOfcardFooterSections = docObjs.cardFooterSections;
		for (int i = 1; i <= listOfcardFooterSections.size(); i++) {
			List<WebElement> listOfCardFooterLinks = docObjs.cardFooterLinks(i);
			for (int j = 1; j <= listOfCardFooterLinks.size(); j++) {
				boolean isDropDownVariant = docObjs.isdropDownVariant(i, j);
				if (isDropDownVariant == true) {
					docObjs.dropDownVariant(i, j).click();
					List<WebElement> dropDownVariantSubLinks = docObjs.dropDownVariantSubLinks(i, j);
					for (int k = 1; k <= dropDownVariantSubLinks.size(); k++) {
						docObjs.dropDownVariant(i, j).click();
						url = docObjs.dropDownSubLink(i, j, k).getAttribute("href");
						System.out.println("Url: " + url);
						docObjs.dropDownSubLink(i, j, k).click();
						int statusCode = getURLStatusCode();
						softAssert.assertEquals(statusCode, 200,
								"Fail, Error code mimatch, Actual code - " + statusCode);
						boolean isAngulaJsLoaded = angularLoads();
						System.out.println("Is AngularJS loaded? " + isAngulaJsLoaded);
						softAssert.assertEquals(isAngulaJsLoaded, true, "Fail, AngularJS is not loaded");
						driver.navigate().back();
						waitForElement(docObjs.cardDetail);
					}

				} else {
					url = docObjs.cardFooterLink(i, j).getAttribute("href");
					System.out.println("Url: " + url);
					if (url.contains("https://developer.here.com/documentation/")) {
						docObjs.cardFooterLink(i, j).click();
						int statusCode = getURLStatusCode();
						softAssert.assertEquals(statusCode, 200,
								"Fail :: Error code mimatch, Actual code - " + statusCode);
						boolean isAngulaJsLoaded = angularLoads();
						System.out.println("Is AngularJS loaded? " + isAngulaJsLoaded);
						softAssert.assertEquals(isAngulaJsLoaded, true, "Fail :: AngularJS is not loaded");
						driver.navigate().back();
						waitForElement(docObjs.cardDetail);
					} else {
						System.out.println("This url not relative to documenatation ");
					}

				}

			}
		}

	}

	public int getURLStatusCode() throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(new HttpGet("https://developer.here.com/documentation"));
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println("Status Code:" + statusCode);
		return statusCode;
	}

	public static boolean angularLoads() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean isAngularJSLoaded = Boolean.valueOf((Boolean) js.executeScript(
				"return (window.angular !== undefined) && (angular.element(document).injector() !== undefined) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)"));
		return isAngularJSLoaded;
	}
}
