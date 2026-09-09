package com.phoenix.frontline;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FrontlineNotifications {

  private final WebDriver driver;
  private final WebDriverWait wait;

  public FrontlineNotifications(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
  }

  public void closeImportantNotifications() {

    System.out.println("Checking for Important Notifications popup...");

    try {

      // Look for the notification dialog/modal.
      By modal = By.xpath(
          "//*[contains(normalize-space(.), " +
              "'Important Notifications')]"
      );

      WebElement notificationText =
          wait.until(
              ExpectedConditions.visibilityOfElementLocated(modal)
          );

      System.out.println(
          "Important Notifications popup detected."
      );

      // Find a close X associated with the popup.
      WebElement closeButton =
          findCloseButton(notificationText);

      if (closeButton != null) {

        System.out.println(
            "Closing Important Notifications with X..."
        );

        closeButton.click();

      } else {

        // If X wasn't found, look for Dismiss.
        WebElement dismissButton =
            findDismissButton();

        if (dismissButton != null) {

          System.out.println(
              "Closing Important Notifications with Dismiss..."
          );

          dismissButton.click();

        } else {

          throw new RuntimeException(
              "Notification popup found, but " +
                  "no X or Dismiss button was found."
          );
        }
      }

      // Give the modal a moment to disappear.
      wait.until(
          ExpectedConditions.invisibilityOfElementLocated(modal)
      );

      System.out.println(
          "Important Notifications popup closed."
      );

    } catch (Exception e) {

      /*
       * The popup may not appear every time.
       * If it isn't present, that's okay.
       */
      System.out.println(
          "No Important Notifications popup detected."
      );
    }
  }


  private WebElement findCloseButton(
      WebElement notificationText) {

    try {

      /*
       * Start at the notification element and work upward
       * through its ancestors looking for buttons.
       */
      WebElement container =
          notificationText.findElement(
              By.xpath(
                  "./ancestor::*[" +
                      ".//button or .//*[@role='button']" +
                      "][1]"
              )
          );

      List<WebElement> buttons =
          container.findElements(
              By.cssSelector(
                  "button, [role='button']"
              )
          );

      for (WebElement button : buttons) {

        if (!button.isDisplayed()) {
          continue;
        }

        String ariaLabel =
            button.getAttribute("aria-label");

        String title =
            button.getAttribute("title");

        String text =
            button.getText();

        if (isCloseControl(
            ariaLabel,
            title,
            text)) {

          return button;
        }
      }

    } catch (Exception ignored) {
    }

    /*
     * Fallback: search visible buttons for close/X controls.
     */
    try {

      List<WebElement> buttons =
          driver.findElements(
              By.cssSelector(
                  "button, [role='button']"
              )
          );

      for (WebElement button : buttons) {

        if (!button.isDisplayed()) {
          continue;
        }

        String ariaLabel =
            button.getAttribute("aria-label");

        String title =
            button.getAttribute("title");

        String text =
            button.getText();

        if (isCloseControl(
            ariaLabel,
            title,
            text)) {

          return button;
        }
      }

    } catch (Exception ignored) {
    }

    return null;
  }


  private boolean isCloseControl(
      String ariaLabel,
      String title,
      String text) {

    String combined =
        ((ariaLabel == null ? "" : ariaLabel) + " " +
            (title == null ? "" : title) + " " +
            (text == null ? "" : text))
            .trim()
            .toLowerCase();

    return combined.equals("x")
        || combined.equals("×")
        || combined.contains("close");
  }


  private WebElement findDismissButton() {

    By[] selectors = {

        By.xpath(
            "//button[normalize-space()='Dismiss']"
        ),

        By.xpath(
            "//*[@role='button' and " +
                "normalize-space()='Dismiss']"
        ),

        By.xpath(
            "//input[@value='Dismiss']"
        )
    };

    for (By selector : selectors) {

      try {

        return new WebDriverWait(
            driver,
            Duration.ofSeconds(3)
        ).until(
            ExpectedConditions.elementToBeClickable(
                selector
            )
        );

      } catch (Exception ignored) {
      }
    }

    return null;
  }
}
