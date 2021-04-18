package ru.stqa.pft.sandbox;

public class MyFirstPrg {

  public static void main(String[] args) {
    // Пример вызова метода 1
    Point a = new Point(1, 0);
    Point b = new Point(4, 0);
    System.out.println("Пример 1" + "\n" + "Расстояние между точками a и b = " + Point.distance(a, b));

    // Пример вызова метода 2
    a.x = 1;
    a.y = 4;
    b.x = 3;
    b.y = 8;
    System.out.println("Пример 2" + "\n" + "Расстояние между точками a и b = " + Point.distance(a, b));

    // Пример вызова метода 3
    a.x = 0.5;
    a.y = 6;
    b.x = 2.2;
    b.y = 4;
    System.out.println("Пример 3" + "\n" + "Расстояние между точками a и b = " + Point.distance(a, b));
  }


}