package View;

import Model.Shift;
import Model.Worker;
import java.io.*;

/**
 * Created by Евгений on 15.04.2016.
 */
public class ConsoleHelper {

    public static void writeCalendarMap(int[][] map) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void writeWorkerMap(Worker[][] map) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (map[i][j] != null) System.out.print(map[i][j].getName() + " ");
                else System.out.print("null ");
            }
            System.out.println();
        }
    }

    public static void writeShiftMap(Shift[][] map) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (map[i][j] != null) System.out.print(map[i][j].getName() + " ");
                else System.out.print("null ");
            }
            System.out.println();
        }
    }
}
