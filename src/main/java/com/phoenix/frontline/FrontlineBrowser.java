package com.phoenix.frontline;

import com.phoenix.config.AppConfig;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FrontlineBrowser {

  private final WebDriver driver;

  public FrontlineBrowser() {

    ChromeOptions options =
        new ChromeOptions();

    // Keep Chrome visible while developing.
    options.addArguments("--start-maximized");

    options.addArguments(
        "--disable-notifications"
    );

    options.addArguments(
        "--disable-popup-blocking"
    );

    driver = new ChromeDriver();
  }


  public void openLoginPage() {

    System.out.println(
        "Opening Frontline login..."
    );

    driver.get(
        AppConfig.FRONTLINE_LOGIN_URL
    );

    System.out.println(
        "Frontline login page opened."
    );
  }


  public WebDriver getDriver() {
    return driver;
  }


  public void close() {

    if (driver != null) {

      driver.quit();
    }
  }
}
