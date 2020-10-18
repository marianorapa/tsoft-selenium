import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.ElementLocator;
import util.ParameterSource;
import util.impl.ElementLocatorImpl;
import util.impl.ParameterSourceImpl;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio1 {

    /*
     * Elements to interact with
     */
    public static final By ARG_LOCATOR = By.id("AR");
    public static final By LOGIN_LINK = By.xpath("//*[@id=\"nav-header-menu\"]/a[2]");
    private static final By LOGIN_USER_INPUT = By.id("user_id");
    private static final By LOGIN_CONTINUE_BTN = By.xpath("//*[@id=\"login_user_form\"]/div[2]/button");
    private static final By LOGIN_PWD_INPUT = By.id("password");
    private static final By VALIDATE_IDENTITY_BTN = By.xpath("//*[@id=\"root-app\"]/div/div[2]/div/div[2]/a[1]");
    private static final By CATEGORY_NAV_BUTTON = By.xpath("/html/body/header/div/div[2]/ul/li[2]/a");
    private static final By TECNO_DROPDOWN_ENTRY = By.linkText("Tecno");
    private static final By CELLPHONE_DROPDOWN_ENTRY = By.partialLinkText("Celulares");
    private static final By VALIDATE_THROUGH_APP_BTN = By.id("channel-push");
    private static final By MOTOROLA_BRAND_BOX = By.xpath("//*[@id=\"root-app\"]/div/section[2]/div[2]/div/div[3]/a/div/div");
    private static final By CATEGORY_ONLY_CHECKBOX = By.id("categorySearch");
    private static final By SEARCH_BAR_INPUT = By.className("nav-search-input");
    private static final By ORDER_BY_BTN = By.cssSelector("#root-app > div > div.ui-search-main.ui-search-main--exhibitor > aside > section.ui-search-view-options > div.ui-search-view-options__group > div.ui-search-sort-filter > div > div");
    private static final By MORE_EXPENSIVE_SORT = By.xpath("//*[@id=\"root-app\"]/div/div[1]/aside/section[1]/div[2]/div[1]/div/div/div/ul/li[3]/a");
    private static final By PRODUCT_ENTRY_LIST = By.className("ui-search-layout__item");
    private static final By PRODUCT_ENTRY_TITLE = By.className("ui-search-item__title");
    private static final By PRODUCT_ENTRY_LINK = By.tagName("a");
    private static final By ADD_TO_CART_BTN = By.className("andes-button--quiet");

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
        driver.manage().window().maximize();
        parameterSource = new ParameterSourceImpl();
        elementLocator = new ElementLocatorImpl(driver);
    }

    /*
    * Startup and authentication
     */

    @Test(priority = 10)
    public void accessML() throws InterruptedException {
        driver.get("https://www.mercadolibre.com/");
    }

    @Test(priority = 20)
    public void chooseArgentina() {
        WebElement element = elementLocator.getElementIdentifiedBy(ARG_LOCATOR);
        element.click();
    }

    @Test(priority = 30)
    public void authenticateIfNotLoggedIn(){
        // If user is not logged in, it does so
        if (userIsNotLoggedIn())
            logInFromHomePage();
    }

    private boolean userIsNotLoggedIn(){
        // Tries to find the "login" element. If it does, user is not logged in
        try {
            elementLocator.getElementIdentifiedBy(LOGIN_LINK);
            return true;
        }
        catch (TimeoutException e){
            return false;
        }
    }

    private void logInFromHomePage() {
        // Click login
        WebElement loginLink = elementLocator.getElementIdentifiedBy(LOGIN_LINK);
        loginLink.click();

        // Input username from param source
        WebElement userInput = elementLocator.getElementIdentifiedBy(LOGIN_USER_INPUT);
        userInput.sendKeys(parameterSource.getUsername());

        WebElement continueBtn = elementLocator.getElementIdentifiedBy(LOGIN_CONTINUE_BTN);
        continueBtn.click();

        WebElement passwordInput = elementLocator.getElementIdentifiedBy(LOGIN_PWD_INPUT);
        passwordInput.sendKeys(parameterSource.getPassword());

        continueBtn = elementLocator.getElementIdentifiedBy(LOGIN_CONTINUE_BTN);
        continueBtn.click();

        checkForDoubleAuthFactor();
    }

    private void checkForDoubleAuthFactor() {
        Actions actions = new Actions(driver);
        try {
            WebElement validateIdentityBtn = elementLocator.getElementIdentifiedBy(VALIDATE_IDENTITY_BTN);
            actions.moveToElement(validateIdentityBtn).click().build().perform();
        }
        catch (TimeoutException ignored){}

        try {
            WebElement throughAppBtn = elementLocator.getElementIdentifiedBy(VALIDATE_THROUGH_APP_BTN);
            actions.moveToElement(throughAppBtn).click();
        }
        catch (TimeoutException ignored){}
    }

    /*
    * Search for products
     */
    @Test(priority = 40)
    public void navigateToCellphoneCategory(){
        try {
            WebElement categoryNavButton = elementLocator.getElementIdentifiedBy(CATEGORY_NAV_BUTTON);
            Actions actions = new Actions(driver);
            actions.moveToElement(categoryNavButton);

            WebElement tecnoEntry = elementLocator.getElementIdentifiedBy(TECNO_DROPDOWN_ENTRY);
            actions.moveToElement(tecnoEntry);

            WebElement cellphonesEntry = elementLocator.getElementIdentifiedBy(CELLPHONE_DROPDOWN_ENTRY);
        }
        catch (Exception e) {
            driver.get("https://www.mercadolibre.com.ar/c/celulares-y-telefonos#menu=categories");
        }
    }


    @Test(priority = 50)
    public void filterBrand(){
        WebElement brand = elementLocator.getElementIdentifiedBy(MOTOROLA_BRAND_BOX);
        brand.click();
    }

    @Test(priority = 60)
    public void searchProduct() {
        // Find chckBox category only
        WebElement checkBoxCategory = elementLocator.getElementIdentifiedBy(CATEGORY_ONLY_CHECKBOX);
        checkBoxCategory.click();

        // Get input field and send data
        WebElement searchInput = elementLocator.getElementIdentifiedBy(SEARCH_BAR_INPUT);
        searchInput.sendKeys(parameterSource.getSearchData());
        searchInput.sendKeys(Keys.ENTER);
    }

    @Test(priority = 70)
    public void sortByMoreExpensive() throws InterruptedException{
        // Sort by more expensive
        Actions actions = new Actions(driver);

        Thread.sleep(1500);

        WebElement orderBy = elementLocator.getElementIdentifiedBy(ORDER_BY_BTN);
        actions.moveToElement(orderBy).click().build().perform();

        WebElement moreExpensive = elementLocator.getElementIdentifiedBy(MORE_EXPENSIVE_SORT);
        actions.moveToElement(moreExpensive);
        actions.click().build().perform();

        String sortedURL = moreExpensive.getAttribute("href");

        driver.get(sortedURL);
    }

    @Test(priority = 80)
    public void addProductsToCart() throws InterruptedException {
        List<WebElement> productList = elementLocator.getElementsBy(PRODUCT_ENTRY_LIST);
        List<String> productUrls = new ArrayList<String>();
        for (WebElement productEntry : productList){
            String url = productEntry.findElement(PRODUCT_ENTRY_LINK).getAttribute("href");
            productUrls.add(url);
        }

        for (int i = 0; i < parameterSource.getAmountOfProductsToAdd(); i++) {
            addOneProductToCart(productUrls.get(i));
        }
    }

    private void addOneProductToCart(String url) {
        driver.get(url);
        WebElement addToCartBtn = elementLocator.getElementIdentifiedBy(ADD_TO_CART_BTN);
        scroll(150);
        Actions action = new Actions(driver);

        action.moveToElement(addToCartBtn).click().build().perform();
    }

    private void scroll(int amt){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,"+ amt +")");
    }
}
