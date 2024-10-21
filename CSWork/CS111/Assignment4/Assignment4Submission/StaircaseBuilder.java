/*
 * Write your program inside the main method to build
 * a staicase in a 2D array of characters according
 * to the assignment description
 *
 * To compile:
 *        javac StaircaseBuilder.java
 * 
 * DO NOT change the class name
 * DO NOT use System.exit()
 * DO NOT change add import statements
 * DO NOT add project statement
 * 
 */
public class StaircaseBuilder {
    
    public static void main(String[] args) {

        // WRITE YOUR CODE HERE
        int d = Integer.parseInt(args[0]);
        int bricks = Integer.parseInt(args[1]);
        char[][] brickArray = new char[d][d];
        for(int r =0; r < d; r++){
            for(int c =0; c < d; c++){
                brickArray[r][c]= ' ';
            }
        }

        for(int c = 0; c < d; c++){
            for(int r = d-1; r >= d-c-1; r--){
                brickArray[r][c] = 'X';
                bricks--;
                if(bricks <= 0) break;
            }
            if(bricks <= 0) break;
        }

        for(int r =0; r < d; r++){
            for(int c =0; c < d; c++){
                System.out.print(brickArray[r][c]);
            }
            System.out.println();
        }
        System.out.println("Bricks remaining: " + bricks);
    }
}
