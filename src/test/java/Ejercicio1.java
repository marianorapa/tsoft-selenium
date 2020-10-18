import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ElementLocator;
import util.ParameterSource;
import util.impl.ElementLocatorImpl;
import util.impl.ParameterSourceImpl;

public class Ejercicio1 {

    /*
     * Elements to interact with
     */
    public static final By ARG_LOCATOR = By.id("AR");
    public static final By LOGIN_LINK = By.xpath("//*[@id=\"nav-header-menu\"]/a[2]");

    /*
     * End of elements to interact with
     */

    // Source of parameterized values
    ParameterSource parameterSource;

    // Element locator
    ElementLocator elementLocator;

    WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        parameterSource = new ParameterSourceImpl();
        elementLocator = new ElementLocatorImpl();
    }

    @Test(priority = 10)
    public void accessML() throws InterruptedException {
        driver.get("https://www.mercadolibre.com/");
    }

    @Test(priority = 20)
    public void chooseArgentina() {
        WebElement element = elementLocator.waitForElementIdentified(ARG_LOCATOR, driver);
        element.click();
    }

    @Test(priority = 30)
    public void authenticateIfNotLoggedIn(){
        // Verify if user is logged in
        if (userIsNotLoggedIn())
            // In case she isn't
            logInFromHomePage();
    }

    private boolean userIsNotLoggedIn(){
        WebElement 
    }

    private void logInFromHomePage() {

        // Click login
        WebElement loginLink = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(LOGIN_LINK));

        loginLink.click();

        // Input username from param source
        WebElement userInput = new WebDriverWait((driver, 10))
        parameterSource.getUsername();


    }
}
