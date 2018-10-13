package lab1lab;

import java.io.*;

public class Pesel {

    public static boolean check(String numbers){
        if (numbers.length()!=11){
            return false;
        }

        int checksum = 0;

        for (int i=0; i<11; i++){

            if (numbers.charAt(i)<48 || numbers.charAt(i)>57){
                return false;
            }
            int singlenumber = numbers.charAt(i) - 48;
            switch(i+1){
                case 1: case 5: case 9: case 11:
                    checksum += singlenumber;
                    break;
                case 2: case 6: case 10:
                    checksum += 3*singlenumber;
                    break;
                case 3: case 7:
                    checksum += 7*singlenumber;
                    break;
                case 4: case 8:
                    checksum += 9*singlenumber;
                    break;
            }
        }
        if (checksum%10 != 0){
            return false;
        }

        return true;
    }

}
