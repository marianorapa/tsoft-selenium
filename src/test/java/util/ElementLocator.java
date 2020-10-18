package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface ElementLocator {

    WebElement getElementIdentifiedBy(By locator);

    WebElement getElementClickableIdentifiedBy(By locator);

}
