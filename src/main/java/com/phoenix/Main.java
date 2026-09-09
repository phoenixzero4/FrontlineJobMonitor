//package com.phoenix;
//
//public class Main {
//
//  public static void main(String[] args) {
//
//    System.out.println();
//    System.out.println("========================================");
//    System.out.println("   FRONTLINE SUBSTITUTE JOB MONITOR");
//    System.out.println("========================================");
//    System.out.println();
//
//    System.out.println("Application started successfully.");
//    System.out.println("Monitoring interval: 3 minutes");
//  }
//}

//package com.phoenix;
//
//import com.phoenix.frontline.FrontlineBrowser;
//
//public class Main {
//
//  public static void main(String[] args) {
//
//    System.out.println(
//        "Starting Frontline monitor..."
//    );
//
//    FrontlineBrowser browser =
//        new FrontlineBrowser();
//
//    try {
//
//      browser.openLoginPage();
//
//      System.out.println(
//          "Chrome should now be displaying Frontline."
//      );
//
//      // Keep browser open for testing.
//      Thread.sleep(30000);
//
//    } catch (Exception e) {
//
//      e.printStackTrace();
//
//    } finally {
//
//      browser.close();
//    }
//  }
//}


//package com.phoenix;
//
//import com.phoenix.frontline.FrontlineBrowser;
//import com.phoenix.frontline.FrontlineLogin;
//
//public class Main {
//
//  public static void main(String[] args) {
//
//    System.out.println("Starting Frontline monitor...");
//
//    FrontlineBrowser browser = new FrontlineBrowser();
//
//    try {
//
//      browser.openLoginPage();
//
//      FrontlineLogin login =
//          new FrontlineLogin(browser.getDriver());
//
//      login.login();
//
//      System.out.println();
//      System.out.println("================================");
//      System.out.println("LOGIN TEST SUCCESSFUL");
//      System.out.println("================================");
//      System.out.println();
//
//      Thread.sleep(30000);
//
//    } catch (Exception e) {
//
//      System.err.println();
//      System.err.println("LOGIN TEST FAILED");
//      System.err.println();
//
//      e.printStackTrace();
//
//    } finally {
//
//      browser.close();
//    }
//  }
//}


package com.phoenix;

import com.phoenix.frontline.*;
import com.phoenix.frontline.FrontlineJob;

import java.util.List;

import static com.phoenix.Colors.RESET;
import static com.phoenix.Colors.*;

public class Main {

  public static void main(String[] args) {

    System.out.println(BRIGHT_MAGENTA + "Starting Frontline monitor..." + RESET);

    FrontlineBrowser browser = new FrontlineBrowser();

    try {

      // 1. Open Frontline
      browser.openLoginPage();

      // 2. Login
      FrontlineLogin login =
          new FrontlineLogin(browser.getDriver());

      login.login();

      // 3. Select organization
      FrontlineOrganization organization =
          new FrontlineOrganization(browser.getDriver());

      organization.selectOrganization();

      // 4. Close Important Notifications
      FrontlineNotifications notifications =
          new FrontlineNotifications(
              browser.getDriver()
          );

      notifications.closeImportantNotifications();

      // 5. Scan Available Jobs
      FrontlineJobScanner scanner =
          new FrontlineJobScanner(
              browser.getDriver()
          );

      List<FrontlineJob> jobs =
          scanner.scanAvailableJobs();

      System.out.println();
      System.out.println( BRIGHT_BLUE + BOLD+"================================"+ RESET);
      System.out.println("JOB SCAN TEST COMPLETE");
      System.out.println("Jobs found: " + jobs.size());
      System.out.println(BRIGHT_BLUE + BOLD + "================================" + RESET);

      System.out.println();
      System.out.println(BRIGHT_BLUE + BOLD + "================================" + RESET);
      System.out.println(
          GREEN + BOLD + "NOTIFICATION POPUP TEST SUCCESSFUL" + RESET
      );
      System.out.println(BRIGHT_BLUE + BOLD + "================================" + RESET);
      System.out.println();

      Thread.sleep(20000);

    } catch (Exception e) {

      System.err.println();
      System.err.println(BRIGHT_RED + "TEST FAILED" + RESET);
      System.err.println();

      e.printStackTrace();

    } finally {

      browser.close();
    }
  }
}