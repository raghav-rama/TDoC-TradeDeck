package org.example;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvVar {
  private static final Dotenv dotenv = Dotenv.load();
  public static Dotenv getDotenv() {
    return dotenv;
  }
}
