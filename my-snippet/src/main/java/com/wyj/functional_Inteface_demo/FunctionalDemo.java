package com.wyj.functional_Inteface_demo;

public class FunctionalDemo {
    public static void main(String[] args) {

        double a=10;
        double b=20;
        System.out.println(getAreaSquare((double c,double d)->c+d,a,b));

    }


    private static double getAreaSquare(Square s,double a,double b){
        return s.calculate(a,b);
    }
}
