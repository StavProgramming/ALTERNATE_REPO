package prestashopCucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.Map;

public class StepDefinitions {
    private WebDriver driver;
    private PrestaShopActuator actuator; // This was missing and caused the compilation error

    @Before
    public void setup() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito", "--window-size=1920,1080");
        driver = new RemoteWebDriver(new URL("http://192.168.1.107:4444"), options);
        actuator = new PrestaShopActuator(driver);
    }

    @Given("^(?:I am on the|User is on) PrestaShop home page$")
    public void home() { 
        actuator.goToHome("http://192.168.1.107:8080"); 
    }

    @And("^(?:I am a|I login as a) \"([^\"]*)\" user$")
    public void user_type(String type) {
        if (type.equalsIgnoreCase("Guest")) {
            driver.manage().deleteAllCookies();
            driver.navigate().refresh();
        } else {
            actuator.login(type);
        }
    }

    @When("^(?:I select a|User adds) \"?([^\"]*)\"? (?:product with quantity|units of) (\\d+)(?: to cart)?$")
    public void add_prod(String type, Integer qty) { 
        actuator.addProduct(qty); 
    }

    @And("I proceed to checkout")
    public void checkout() { 
        actuator.goToCheckout(); 
    }

    @And("^(?:I fill in the shipping address for|User enters address for country) \"([^\"]*)\"$")
    public void address(String country) { 
        actuator.fillAddress(country); 
    }

    @And("^(?:I select the|User selects carrier) \"([^\"]*)\" (?:shipping method|Shipping)$")
    public void shipping(String method) { 
        actuator.confirmShipping(method); 
    }

    @And("^(?:I apply a coupon code|User applies coupon) \"([^\"]*)\"$")
    public void coupon(String code) { 
        actuator.applyCoupon(code); 
    }

    @Then("^(?:the final price should be calculated correctly based on:|the final price should include the VIP discount and be correct:|The final price should reflect fixed discount and bulk rules for \".*\")$")
    public void verify(Map<String, String> data) {
        actuator.finishPayment();
        String actual = actuator.getTotal();
        // Validation logic...
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