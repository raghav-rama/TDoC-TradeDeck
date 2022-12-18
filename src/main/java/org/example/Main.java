package org.example;

public class Main {
  static {
    System.out.println("static");
  }
  public static void main (String[] args) {
    System.out.println("main");
    System.out.println(map(87.79,87.74, 87.82, 60, 100));


  }
  static double map(double x, double in_min, double in_max, double out_min, double out_max) {
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
  }
}
