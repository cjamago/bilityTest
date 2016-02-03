package com.wordpressTest.scala

import org.scalatest.testng.TestNGWrapperSuite

class Blog extends TestNGWrapperSuite(
  List("src/test/resources/xml/blogtest.xml")
)