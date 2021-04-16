import java.util.Scanner;
import java.util.Arrays;

public class WordWrapProblem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of Words : ");
        int n = scanner.nextInt();
        System.out.println("Enter the Length of lines : ");
        long M = scanner.nextInt();
        String[] words = new String[n];
        long[] length = new long[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter the word " + (i + 1));
            words[i] = scanner.next();
            length[i] = words[i].length();
        }
        findMinCost(length, words, n, M);
    }

    public static void findMinCost(long[] length, String[] words, int n, long M) {
        long[][] extraSpaces = new long[n + 1][n + 1];//for [i][j] ,extra spaces in the end of line for words i to j in it
        long[][] costs = new long[n + 1][n + 1];//for [i][j] ,the cost for keeping these words from i to j in one line
        long[] leastCost = new long[n + 1];//for [i],the least cost for words from 1 to i in the paragraph
        int[] print = new int[n + 1];//keep indexs for printing the line
        for (int i = 1; i <= n; i++) {
            extraSpaces[i][i] = M - length[i - 1];
            for (int j = i + 1; j <= n; j++) {
                extraSpaces[i][j] = extraSpaces[i][j - 1] - length[j - 1] - 1;
            }
        }
        //c[i][j] = (M-v[k])^3
        // note that in formula v[i][j] =(j-i) + zigma(k =from i to j )length[k],v[i][j] is realy the spaces which are
        // full when we put words i to j in one line and so on,c[i][j] is (extra spaces at end)^3
        //(j-i) is the spaces betwenn words
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j++) {
                if (extraSpaces[i][j] < 0) {
                    costs[i][j] = Long.MAX_VALUE;
                } else if (extraSpaces[i][j] >= 0 && j == n) {
                    costs[i][j] = 0;
                } else {
                    costs[i][j] = extraSpaces[i][j] * extraSpaces[i][j] * extraSpaces[i][j];
                }

            }
        }
        leastCost[0] = 0;
        for (int j = 1; j <= n; j++) {
            leastCost[j] = Long.MAX_VALUE;
            for (int i = 1; i <= j; i++) {
                if (leastCost[i - 1] != Long.MAX_VALUE && costs[i][j] != Long.MAX_VALUE &&
                        (leastCost[i - 1] + costs[i][j] < leastCost[j])) {
                    leastCost[j] = leastCost[i - 1] + costs[i][j];
                    print[j] = i;
                }
            }
        }
        System.out.println("Total cost : " + leastCost[n]);
        printSolution(print, n, words, costs);
    }

    public static int printSolution(int print[], int n, String[] words, long[][] costs) {
        int k;
        if (print[n] == 1)
            k = 1;
        else
            k = printSolution(print, print[n] - 1, words, costs) + 1;
        long cost = costs[print[n]][n];
        System.out.print("Line " + " " + k + " (cost = " + cost + "): " + "From word " + " " + print[n] + " " + "to" + " " + n + ", the line : ");
        for (int j = print[n] - 1; j <= n - 1; j++) {
            System.out.print(words[j] + " ");
        }
        System.out.println();
        return k;
    }
}