package prestashopCucumber;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class PrestaShopActuator {
    private WebDriver driver;
    private WebDriverWait wait;

    public PrestaShopActuator(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void goToHome(String url) {
        driver.get(url);
    }

    public void login(String type) {
        // Direct navigation to ensure the login form is ready
        driver.get("http://192.168.1.107:8080/index.php?controller=authentication");
        
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email")));
        WebElement passField = driver.findElement(By.id("field-password"));
        
        emailField.clear();
        passField.clear();

        // Using your specific SUT Data
        if (type.equalsIgnoreCase("VIP")) {
            emailField.sendKeys("vip_user@testing.com");
            passField.sendKeys("V!p_Secure_789#");
        } else {
            emailField.sendKeys("standard_user@testing.com");
            passField.sendKeys("Std_P@ss_2026!");
        }
        
        driver.findElement(By.id("submit-login")).click();
        driver.get("http://192.168.1.107:8080/index.php");
    }

    public void addProduct(int qty) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product-miniature"))).click();
        WebElement qtyField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quantity_wanted")));
        qtyField.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        qtyField.sendKeys(String.valueOf(qty));
        driver.findElement(By.cssSelector(".add-to-cart")).click();
    }

    public void goToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".cart-content-btn a"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".cart-detailed-actions a"))).click();
    }

    public void fillAddress(String countryType) {
        // Step 1: Personal Info (Skipped if logged in)
        if (driver.findElements(By.cssSelector(".logout")).isEmpty()) {
            WebElement s1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("checkout-personal-information-step")));
            if (!driver.findElement(By.id("field-firstname")).isDisplayed()) clickWithJS(s1.findElement(By.cssSelector("h1")));
            driver.findElement(By.id("field-firstname")).sendKeys("Automation");
            driver.findElement(By.id("field-lastname")).sendKeys("Tester");
            driver.findElement(By.id("field-email")).sendKeys("test" + System.currentTimeMillis() + "@example.com");
            for (WebElement cb : s1.findElements(By.cssSelector("input[type='checkbox']"))) { if (!cb.isSelected()) clickWithJS(cb); }
            clickWithJS(s1.findElement(By.name("continue")));
        }

        // Step 2: Address
        WebElement s2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("checkout-addresses-step")));
        if (driver.findElements(By.cssSelector(".address-item.selected")).isEmpty()) {
            if (!driver.findElement(By.id("field-address1")).isDisplayed()) clickWithJS(s2.findElement(By.cssSelector("h1")));
            driver.findElement(By.id("field-address1")).sendKeys("123 Test St");
            driver.findElement(By.id("field-city")).sendKeys("Miami");
            driver.findElement(By.id("field-postcode")).sendKeys("33101");
            Select c = new Select(driver.findElement(By.id("field-id_country")));
            c.selectByVisibleText("United States");
            wait.until(d -> new Select(d.findElement(By.id("field-id_state"))).getOptions().size() > 1);
            new Select(driver.findElement(By.id("field-id_state"))).selectByVisibleText("Florida");
        }
        clickWithJS(driver.findElement(By.name("confirm-addresses")));
    }

    public void confirmShipping() {
        clickWithJS(wait.until(ExpectedConditions.elementToBeClickable(By.name("confirmDeliveryOption"))));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout-payment-step")));
        try { Thread.sleep(2500); } catch (Exception ignored) {}
    }

    public void applyCoupon(String code) {
        if (code == null || code.isEmpty()) return;
        boolean applied = false;
        int attempts = 0;
        while (!applied && attempts < 5) {
            try {
                List<WebElement> inputs = driver.findElements(By.name("discount_name"));
                if (!inputs.isEmpty() && inputs.get(0).isDisplayed()) {
                    inputs.get(0).clear();
                    inputs.get(0).sendKeys(code);
                    clickWithJS(driver.findElement(By.cssSelector("#promo-code button[type='submit']")));
                    applied = true;
                    Thread.sleep(1500);
                } else {
                    // Using your confirmed working selector list
                    WebElement promoLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector(".promo-code-button .display-promo, .promo-link, a.promo-code-button")));
                    clickWithJS(promoLink);
                    Thread.sleep(1000); 
                }
            } catch (Exception e) { attempts++; }
        }
    }

    public void finishPayment() {
        try {
            WebElement terms = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("conditions_to_approve[terms-and-conditions]")));
            if (!terms.isSelected()) clickWithJS(terms);
            List<WebElement> opts = driver.findElements(By.cssSelector("input[name='payment-option']"));
            if (!opts.isEmpty()) clickWithJS(opts.get(0));
        } catch (Exception ignored) {}
    }

    public String getTotal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-total .value"))).getText();
    }

    private void clickWithJS(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }
}