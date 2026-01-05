package prestashopCucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class StepDefinitions {
    private WebDriver driver;
    private PrestaShopActuator actuator;
    private final String BASE_URL = "http://192.168.1.107:8080";

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito", "--window-size=1920,1080");
        driver = new RemoteWebDriver(new URL("http://192.168.1.107:4444"), options);
        actuator = new PrestaShopActuator(driver);
    }

    @Given("I am on the PrestaShop home page")
    public void home() { actuator.goToHome(BASE_URL); }

    @And("I am a {string} user")
    public void user_type(String type) {
        if (type.equalsIgnoreCase("Guest")) {
            driver.manage().deleteAllCookies();
            driver.navigate().refresh();
        } else { 
            actuator.login(type); 
        }
    }

    @When("I select a {string} product with quantity {int}")
    public void add_prod(String type, Integer q) { actuator.addProduct(q); }

    @And("I proceed to checkout")
    public void checkout() { actuator.goToCheckout(); }

    @And("I fill in the shipping address for {string}")
    public void address(String country) { actuator.fillAddress(country); }

    @And("I select the {string} shipping method")
    public void shipping(String method) { actuator.confirmShipping(); }

    @And("I apply a coupon code {string}")
    public void coupon(String status) {
        if (status.equalsIgnoreCase("Applied")) actuator.applyCoupon("SAVE10");
    }

    @Then("the final price should be calculated correctly based on:")
    public void verify(Map<String, String> data) {
        // Move to the final summary step
        actuator.finishPayment();
        
        String actualTotal = actuator.getTotal().trim();
        String expectedTotal = data.get("total").trim();
        
        System.out.println("Expected: " + expectedTotal + " | Actual: " + actualTotal);

        // Verification: Fails the test if the price is wrong
        if (!actualTotal.equals(expectedTotal)) {
            throw new AssertionError("Price Mismatch! Expected " + expectedTotal + " but found " + actualTotal);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Error_Screenshot");
        }
        if (driver != null) driver.quit();
    }
}