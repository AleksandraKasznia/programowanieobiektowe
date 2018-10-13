package lab1lab;

import java.util.Scanner;

import static lab1lab.Pesel.check;


public class Main {
    public static void main(String[] argv){
        String pesel;
        Scanner input = new Scanner(System.in);
        pesel = input.nextLine();
        System.out.println(pesel);
        System.out.print(check(pesel));
    }
}
