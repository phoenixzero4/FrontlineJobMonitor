package com.phoenix.frontline;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static com.phoenix.Colors.*;

public class FrontlineJobScanner {

  private final WebDriver driver;
  private final WebDriverWait wait;

  public FrontlineJobScanner(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  public List<FrontlineJob> scanAvailableJobs() {

    System.out.println();
    System.out.println(BLUE + "================================" + RESET);
    System.out.println("Scanning Available Jobs...");
    System.out.println(BLUE + "================================" + RESET);

    openAvailableJobs();

    if (hasNoAvailableJobsMessage()) {

      System.out.println();
      System.out.println("No available assignments.");

      return new ArrayList<>();
    }

    List<FrontlineJob> jobs = readJobs();

    System.out.println();
    System.out.println("Jobs found: " + jobs.size());

    for (FrontlineJob job : jobs) {

      System.out.println("--------------------------------");
      System.out.println("Date:     " + job.getDate());
      System.out.println("Time:     " + job.getTime());
      System.out.println("Duration: " + job.getDuration());
      System.out.println("Location: " + job.getLocation());
      System.out.println("Key:      " + job.getUniqueKey());
    }

    return jobs;
  }

  private void openAvailableJobs() {

    System.out.println("Looking for Available Jobs...");

    By availableJobsLocator = By.xpath(
        "//*[normalize-space()='Available Jobs']"
    );

    WebElement availableJobs = wait.until(
        ExpectedConditions.elementToBeClickable(
            availableJobsLocator
        )
    );

    System.out.println(
        "Found Available Jobs using: "
            + availableJobsLocator
    );

    availableJobs.click();

    wait.until(driver ->
        driver.findElements(
            By.cssSelector("th.date")
        ).size() > 0
            ||
            hasNoAvailableJobsMessage()
    );

    System.out.println("Available Jobs page opened.");
  }

  private boolean hasNoAvailableJobsMessage() {

    String pageText = driver.findElement(
        By.tagName("body")
    ).getText();

    return pageText.contains(
        "There are no available assignments"
    );
  }

  private List<FrontlineJob> readJobs() {

    List<FrontlineJob> jobs = new ArrayList<>();

    /*
     * The actual Available Jobs table has:
     *
     * Date
     * Time
     * Duration
     * Location
     * more
     *
     * We specifically locate that table rather than
     * scanning every <tr> on the page.
     */

    List<WebElement> dateHeaders = driver.findElements(
        By.cssSelector("th.date")
    );

    if (dateHeaders.isEmpty()) {

      System.out.println(
          "Available Jobs table was not found."
      );

      return jobs;
    }

    WebElement dateHeader = dateHeaders.get(0);

    WebElement table = dateHeader.findElement(
        By.xpath("./ancestor::table[1]")
    );

    List<WebElement> rows = table.findElements(
        By.cssSelector("tbody tr")
    );

    System.out.println(
        "Available Jobs table rows found: "
            + rows.size()
    );

    for (WebElement row : rows) {

      if (!row.isDisplayed()) {
        continue;
      }

      List<WebElement> cells = row.findElements(
          By.cssSelector("td")
      );

      /*
       * A real job should have five cells:
       *
       * 0 = Date
       * 1 = Time
       * 2 = Duration
       * 3 = Location
       * 4 = More
       */

      if (cells.size() < 4) {
        continue;
      }

      String date = cells.get(0)
                         .getText()
                         .trim();

      String time = cells.get(1)
                         .getText()
                         .trim();

      String duration = cells.get(2)
                             .getText()
                             .trim();

      String location = cells.get(3)
                             .getText()
                             .trim();

      /*
       * Ignore empty/layout rows.
       */
      if (date.isBlank()
          || time.isBlank()
          || duration.isBlank()
          || location.isBlank()) {

        continue;
      }

      FrontlineJob job = new FrontlineJob(
          date,
          time,
          duration,
          location
      );

      jobs.add(job);
    }

    return jobs;
  }
}