package com.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        /*double resultado = calculator.sumar(20, 89);
        System.out.println("El resultado de la suma es: " + resultado);
        double resultadoResta = calculator.restar(89, 20);
        System.out.println("El resultado de la resta es: " + resultadoResta);*/

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el primer número: ");
        double num1 = scanner.nextDouble();
        System.out.print("Ingrese el segundo número: ");
        double num2 = scanner.nextDouble();
        
        double resultadoSuma = calculator.sumar(num1, num2);
        System.out.println("El resultado de la suma es: " + resultadoSuma);
        double resultadoResta = calculator.restar(num1, num2);
        System.out.println("El resultado de la resta es: " + resultadoResta);
        double resultadoMultiplicacion = calculator.multiplicar(num1, num2);
        System.out.println("El resultado de la multiplicación es: " + resultadoMultiplicacion);
        double resultadoDivision = calculator.dividir(num1, num2);
        System.out.println("El resultado de la división es: " + resultadoDivision);
    }
} 