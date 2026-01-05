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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void goToHome(String url) { 
        driver.get(url); 
    }

    public void login(String type) {
        driver.get("http://192.168.1.107:8080/index.php?controller=authentication");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email"))).sendKeys(
            type.equalsIgnoreCase("VIP") ? "vip_user@testing.com" : "standard_user@testing.com"
        );
        driver.findElement(By.id("field-password")).sendKeys(
            type.equalsIgnoreCase("VIP") ? "V!p_Secure_789#" : "Std_P@ss_2026!"
        );
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
        WebElement step1 = driver.findElement(By.id("checkout-personal-information-step"));
        if (!step1.getAttribute("class").contains("-complete")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname"))).sendKeys("Automation");
            driver.findElement(By.name("lastname")).sendKeys("Tester");
            driver.findElement(By.name("email")).sendKeys("guest" + System.currentTimeMillis() + "@testing.com");
            
            step1.findElements(By.cssSelector("input[type='checkbox']")).forEach(cb -> {
                if (!cb.isSelected()) clickJS(cb);
            });
            clickJS(step1.findElement(By.name("continue")));
        }

        wait.until(ExpectedConditions.attributeContains(By.id("checkout-addresses-step"), "class", "-current"));
        WebElement step2 = driver.findElement(By.id("checkout-addresses-step"));
        
        List<WebElement> addressField = driver.findElements(By.id("field-address1"));
        if (!addressField.isEmpty() && addressField.get(0).isDisplayed()) {
            addressField.get(0).sendKeys("123 Testing Avenue");
            driver.findElement(By.id("field-city")).sendKeys("Miami");
            driver.findElement(By.id("field-postcode")).sendKeys(countryType.contains("USA") ? "33101" : "75001");
            
            Select countrySelect = new Select(driver.findElement(By.id("field-id_country")));
            countrySelect.selectByVisibleText(countryType.equalsIgnoreCase("International") ? "France" : "United States");
            
            if (!countryType.equalsIgnoreCase("International")) {
                wait.until(d -> new Select(d.findElement(By.id("field-id_state"))).getOptions().size() > 1);
                new Select(driver.findElement(By.id("field-id_state"))).selectByVisibleText("Florida");
            }
        }

        clickJS(wait.until(ExpectedConditions.elementToBeClickable(By.name("confirm-addresses"))));
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(By.id("checkout-delivery-step"), "class", "-unreachable")));
    }

    public void confirmShipping(String carrier) {
        wait.until(ExpectedConditions.attributeContains(By.id("checkout-delivery-step"), "class", "-current"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading-overlay")));
        
        try {
            List<WebElement> options = driver.findElements(By.cssSelector(".delivery-option label"));
            for (WebElement opt : options) {
                if (opt.getText().toLowerCase().contains(carrier.toLowerCase())) {
                    clickJS(opt);
                    break;
                }
            }
        } catch (Exception ignored) {}

        clickJS(wait.until(ExpectedConditions.elementToBeClickable(By.name("confirmDeliveryOption"))));
        try { Thread.sleep(1500); } catch (Exception ignored) {}
    }

    public void applyCoupon(String code) {
        if (code == null || code.equalsIgnoreCase("None")) return;
        String actualCode = code.equalsIgnoreCase("Applied") ? "SAVE10" : code;
        try {
            clickJS(wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".display-promo"))));
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("discount_name")));
            input.clear();
            input.sendKeys(actualCode);
            driver.findElement(By.cssSelector("#promo-code button[type='submit']")).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".cart-summary .loading-overlay")));
        } catch (Exception ignored) {}
    }

    public void finishPayment() {
        wait.until(ExpectedConditions.attributeContains(By.id("checkout-payment-step"), "class", "-current"));
    }

    public String getTotal() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading-overlay")));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-total .value"))).getText();
    }

    private void clickJS(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }
}