package com.phoenix.frontline;

import com.phoenix.config.AppConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FrontlineOrganization {

  private final WebDriver driver;
  private final WebDriverWait wait;

  public FrontlineOrganization(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // XXX Changed from 30
  }

  public void selectOrganization() {

    System.out.println("Looking for organization...");

    String organization = AppConfig.FRONTLINE_ORGANIZATION;

    By organizationLocator = By.xpath(
        "//*[normalize-space(text())='" + organization + "']"
    );

    WebElement organizationElement = wait.until(
        ExpectedConditions.elementToBeClickable(organizationLocator)
    );

    System.out.println(
        "Selecting organization: " + organization
    );

    organizationElement.click();

    wait.until(driver ->
        driver.getCurrentUrl()
              .contains("absencesub.frontlineeducation.com")
    );

    System.out.println("Organization selected.");

  /* Potentially prints live session token */
    //System.out.println("Current URL: " + driver.getCurrentUrl());

    String URL = driver.getCurrentUrl();
    String[] UrlArray = URL.split("#");
    URL = UrlArray[0];
    System.out.println("Current URL: " + URL);
  }
}
