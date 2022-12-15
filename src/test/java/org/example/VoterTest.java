package org.example;

import static org.junit.jupiter.api.Assertions.*;

class VoterTest {

  @org.junit.jupiter.api.BeforeEach
  void setUp () {
    System.out.println("before testing");
  }

  @org.junit.jupiter.api.AfterEach
  void tearDown () {
    System.out.println("after testing");
  }

  @org.junit.jupiter.api.Test
  void isVote () {
    System.out.println("isvote");
  }

  @org.junit.jupiter.api.Test
  void setVote () {
    System.out.println("setvote");
  }

  @org.junit.jupiter.api.Test
  void getName () {
    System.out.println("getname");
  }

  @org.junit.jupiter.api.Test
  void setName () {
    System.out.println("setname");
  }

  @org.junit.jupiter.api.Test
  void getAge () {
    System.out.println("getage");
  }

  @org.junit.jupiter.api.Test
  void setAge () {
    System.out.println("setage");
  }
}