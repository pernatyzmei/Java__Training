package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test

public class PointTest {
  public void TestArea() {
    Point a = new Point(7, 7);
    Point b = new Point(10, 11);

    Assert.assertEquals(Point.distance(a, b), 5.0);
  }
}



