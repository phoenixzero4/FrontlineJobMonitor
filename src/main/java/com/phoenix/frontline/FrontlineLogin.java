package com.phoenix.frontline;

import com.phoenix.config.AppConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import static com.phoenix.Colors.*;

public class FrontlineLogin {

  private final WebDriver driver;
  private final WebDriverWait wait;

  public FrontlineLogin(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  public void login() {

    System.out.println(BRIGHT_MAGENTA + "Starting Frontline login...\n" + RESET);

    if (!driver.getCurrentUrl().contains("login.frontlineeducation.com")) {
      driver.get(AppConfig.FRONTLINE_LOGIN_URL);
    }

    WebElement usernameField = findUsernameField();

    System.out.println("Entering username...\n");

    usernameField.clear();
    usernameField.sendKeys(
        AppConfig.getFrontlineUsername()
    );

    WebElement passwordField = findPasswordField();

    System.out.println("Entering password...\n");

    passwordField.clear();
    passwordField.sendKeys(
        AppConfig.getFrontlinePassword()
    );

    WebElement loginButton = findLoginButton();

    System.out.println("Clicking Sign In...\n");

    loginButton.click();

    System.out.println("Waiting for login to complete...");

    waitForLoginCompletion();

    System.out.println(BRIGHT_MAGENTA + "Login completed.\n" + RESET);

    /* This potentially prints a live session token*/
   // System.out.println("Current URL: " + driver.getCurrentUrl());

    System.out.println("Organization selected.");
    System.out.println("Frontline application loaded.");
  }


  // ---------------------------------------------------------
  // USERNAME
  // ---------------------------------------------------------

  private WebElement findUsernameField() {

    System.out.println("Looking for username field...");

    By[] selectors = {

        // Common IDs
        By.id("username"),
        By.id("userName"),
        By.id("Username"),
        By.id("UserName"),

        // Common names
        By.name("username"),
        By.name("userName"),
        By.name("Username"),
        By.name("UserName"),

        // Email inputs
        By.cssSelector("input[type='email']"),

        // Inputs with useful attributes
        By.cssSelector(
            "input[autocomplete='username']"
        ),

        By.cssSelector(
            "input[autocomplete='email']"
        ),

        // Placeholder
        By.cssSelector(
            "input[placeholder*='username' i]"
        ),

        By.cssSelector(
            "input[placeholder*='email' i]"
        )
    };

    return findFirstVisible(selectors);
  }


  // ---------------------------------------------------------
  // PASSWORD
  // ---------------------------------------------------------

  private WebElement findPasswordField() {

    System.out.println("Looking for password field...");

    By[] selectors = {

        By.id("password"),
        By.id("Password"),

        By.name("password"),
        By.name("Password"),

        By.cssSelector(
            "input[type='password']"
        ),

        By.cssSelector(
            "input[autocomplete='current-password']"
        )
    };

    return findFirstVisible(selectors);
  }


  // ---------------------------------------------------------
  // LOGIN BUTTON
  // ---------------------------------------------------------

  private WebElement findLoginButton() {

    System.out.println("Looking for Sign In button...");

    By[] selectors = {

        By.cssSelector(
            "button[type='submit']"
        ),

        By.cssSelector(
            "input[type='submit']"
        ),

        By.xpath(
            "//button[contains(translate(normalize-space(.), " +
                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                "'abcdefghijklmnopqrstuvwxyz'), 'sign in')]"
        ),

        By.xpath(
            "//button[contains(translate(normalize-space(.), " +
                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                "'abcdefghijklmnopqrstuvwxyz'), 'login')]"
        ),

        By.xpath(
            "//input[contains(translate(@value, " +
                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                "'abcdefghijklmnopqrstuvwxyz'), 'sign in')]"
        ),

        By.xpath(
            "//input[contains(translate(@value, " +
                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                "'abcdefghijklmnopqrstuvwxyz'), 'login')]"
        )
    };

    return findFirstClickable(selectors);
  }


  // ---------------------------------------------------------
  // FIND FIRST VISIBLE ELEMENT
  // ---------------------------------------------------------

  private WebElement findFirstVisible(By[] selectors) {

    for (By selector : selectors) {

      try {

        WebElement element = new WebDriverWait(
            driver,
            Duration.ofSeconds(3)
        ).until(
            ExpectedConditions.visibilityOfElementLocated(
                selector
            )
        );

        if (element.isDisplayed()) {
          System.out.println(
              "Found element using: " + selector
          );

          return element;
        }

      } catch (Exception ignored) {
        // Try next selector
      }
    }

    /*
     * Diagnostic information.
     * This helps us determine what Frontline actually
     * presented to Selenium.
     */
    System.out.println();
    System.out.println("------------------------------------------");
    System.out.println(RED + "Could not find username field." + RESET);

    /* Potentially prints a live session token */
    //System.out.println("Current URL:");
    //System.out.println(driver.getCurrentUrl());


    String URL = driver.getCurrentUrl();
    String[] UrlArray = URL.split("#");
    URL = UrlArray[0];
    System.out.println("Current URL: " + URL);

    System.out.println();
    System.out.println("Visible input elements:");

    try {

      List<WebElement> inputs =
          driver.findElements(By.tagName("input"));

      for (WebElement input : inputs) {

        System.out.println(
            "tag=input" +
                " type=" + input.getAttribute("type") +
                " id=" + input.getAttribute("id") +
                " name=" + input.getAttribute("name") +
                " placeholder=" +
                input.getAttribute("placeholder")
        );
      }

    } catch (Exception e) {

      System.out.println(
          "Unable to inspect input elements."
      );
    }

    System.out.println("------------------------------------------");
    System.out.println();

    throw new RuntimeException(
        "Could not find the Frontline username field."
    );
  }


  // ---------------------------------------------------------
  // FIND FIRST CLICKABLE ELEMENT
  // ---------------------------------------------------------

  private WebElement findFirstClickable(By[] selectors) {

    for (By selector : selectors) {

      try {

        WebElement element = new WebDriverWait(
            driver,
            Duration.ofSeconds(3)
        ).until(
            ExpectedConditions.elementToBeClickable(
                selector
            )
        );

        return element;

      } catch (Exception ignored) {
        // Try next selector
      }
    }

    throw new RuntimeException(
        "Could not find the Frontline Sign In button."
    );
  }


  // ---------------------------------------------------------
  // LOGIN COMPLETION
  // ---------------------------------------------------------

  private void waitForLoginCompletion() {

    try {

      wait.until(driver ->
          !driver.getCurrentUrl()
                 .contains("login.frontlineeducation.com")
      );

    } catch (Exception e) {

      System.out.println();
      System.out.println(
          "Frontline may require additional verification."
      );

      System.out.println(
          "Complete MFA/CAPTCHA manually if displayed."
      );

      waitForManualLoginCompletion();
    }
  }


  private void waitForManualLoginCompletion() {

    WebDriverWait manualWait =
        new WebDriverWait(
            driver,
            Duration.ofMinutes(5)
        );

    manualWait.until(driver ->
        !driver.getCurrentUrl()
               .contains("login.frontlineeducation.com")
    );

    System.out.println(
        "Manual verification completed."
    );
  }
}