package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface ElementLocator {

    WebElement waitForElementIdentified(By locator, WebDriver driver);

}
