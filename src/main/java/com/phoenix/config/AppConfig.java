package com.phoenix.config;

public class AppConfig {

  // =========================================================
  // FRONTLINE
  // =========================================================

  public static final String FRONTLINE_LOGIN_URL =
      "https://login.frontlineeducation.com/login?productId=ABSMGMT&clientId=ABSMGMT#/login";

  public static final String FRONTLINE_HOME_URL =
      "https://absencesub.frontlineeducation.com/Substitute/Home";

  public static final String FRONTLINE_ORGANIZATION =
      "#Kelly Southeast Substitutes";


  // =========================================================
  // MONITORING
  // =========================================================

  public static final long CHECK_INTERVAL_MINUTES = 3;

  public static final long CHECK_INTERVAL_MS =
      CHECK_INTERVAL_MINUTES * 60 * 1000L;


  // =========================================================
  // FRONTLINE CREDENTIALS
  // =========================================================

  public static String getFrontlineUsername() {
    return getRequiredEnvironmentVariable(
        "FRONTLINE_USERNAME"
    );
  }

  public static String getFrontlinePassword() {
    return getRequiredEnvironmentVariable(
        "FRONTLINE_PASSWORD"
    );
  }


  // =========================================================
  // TWILIO
  // =========================================================

  public static String getTwilioAccountSid() {
    return getRequiredEnvironmentVariable(
        "TWILIO_ACCOUNT_SID"
    );
  }

  public static String getTwilioAuthToken() {
    return getRequiredEnvironmentVariable(
        "TWILIO_AUTH_TOKEN"
    );
  }

  public static String getTwilioFrom() {
    return getRequiredEnvironmentVariable(
        "TWILIO_FROM"
    );
  }

  public static String getSmsTo() {
    return getRequiredEnvironmentVariable(
        "SMS_TO"
    );
  }


  // =========================================================
  // EMAIL
  // =========================================================

  public static String getEmailUsername() {
    return getRequiredEnvironmentVariable(
        "EMAIL_USERNAME"
    );
  }

  public static String getEmailPassword() {
    return getRequiredEnvironmentVariable(
        "EMAIL_PASSWORD"
    );
  }

  public static String getEmailTo() {
    return getRequiredEnvironmentVariable(
        "EMAIL_TO"
    );
  }

  public static String getSmtpHost() {
    return getRequiredEnvironmentVariable(
        "SMTP_HOST"
    );
  }

  public static String getSmtpPort() {
    return getRequiredEnvironmentVariable(
        "SMTP_PORT"
    );
  }


  // =========================================================
  // ENVIRONMENT VARIABLE HELPER
  // =========================================================

  private static String getRequiredEnvironmentVariable(
      String name) {

    String value = System.getenv(name);

    if (value == null || value.isBlank()) {

      throw new IllegalStateException(
          "Missing environment variable: " + name
      );
    }

    return value;
  }
}