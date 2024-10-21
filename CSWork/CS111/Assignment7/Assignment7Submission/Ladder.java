
/**
 * Creates a ladder using recursion.
 * The ladder is built starting at the bottom and backs out
 * at the top.
 *
 * ODD levels are denoted by |=|\n (rails/step followed by new line)
 * EVEN levels are denoted by | |\n (rails followed by new line)
 * The TOPMOST level (n = 1) is the end of the ladder: |-|\n
 *
 * ASSUME n >= 1.
 *
 * Compilation: javac Ladder.java
 * Execution: java Ladder
 *
 * @author Kal Pandit
 * @author Mary Buist
 */

public class Ladder {

    static String ladder = "|-|\n";

    public static String createLadder(int n) {

        if ((n > 1) && (n % 2 != 0)) {
            n--;
            ladder = createLadder(n) + "|=|\n";
        } else if ((n > 1) && (n % 2 == 0)) {
            n--;
            ladder = createLadder(n)+ "| |\n";
        } else{
            ladder = "|-|\n"; 
        }
        return ladder;
    }

    /*
     * Client program to test your recursive method.
     * Update as needed.
     * This method is not called from Autolab.
     */
    public static void main(String[] args) {
        System.out.println("createLadder(6):\n" + createLadder(6));
        System.out.println("createLadder(3):\n" + createLadder(3));
        System.out.println("createLadder(2):\n" + createLadder(2));
        System.out.println("createLadder(1):\n" + createLadder(1));
    }
}
