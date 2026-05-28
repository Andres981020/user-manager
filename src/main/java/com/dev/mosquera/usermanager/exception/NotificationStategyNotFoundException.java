package com.dev.mosquera.usermanager.exception;

public class NotificationStategyNotFoundException extends RuntimeException {
  public NotificationStategyNotFoundException(String message) {
    super(message);
  }

  public NotificationStategyNotFoundException(String message, Throwable cause) {
      super(message, cause);
  }
}
