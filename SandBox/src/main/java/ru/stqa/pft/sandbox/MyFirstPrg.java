package ru.stqa.pft.sandbox;

public class MyFirstPrg {
	
	public static void main(String[] args){
		Point a = new Point();
		a.x = 1;
		a.y = 0;
		Point b = new Point();
		b.x = 3;
		b.y = 0;
		System.out.println(distance(a, b));
	}
	
	public static String distance(Point p1, Point p2){
		return ("Расстояние между точками р1 и р2 = " +  Math.sqrt((p2.x- p1.x)*(p2.x- p1.x)+(p2.y- p1.y)*(p2.y- p1.y)));
	}
}