/*
*
* Write the Buses program inside the main method
* according to the assignment description.
* 
* To compile:
*        javac Buses.java
* To execute:
*        java Buses 7302
* 
* DO NOT change the class name
* DO NOT use System.exit()
* DO NOT change add import statements
* DO NOT add project statement
* 
*/

public class Buses {
    public static void main(String[] args) {

        int num = Integer.parseInt(args[0]);
        if(num<0 || num<999 || num>10000){
            System.out.println("ERROR");
            return;
        }
        int digitSum = 0;
        while(num>0){
            digitSum += num%10;
            num /= 10;
        }

        if(digitSum%2==0){
            System.out.println("LX");
        }else{
            System.out.println("H");
        }
        
        // if(num<0){
        //     System.out.println("ERROR");
        //     return;
        // }
        // System.out.println(num);
    

    }
}
