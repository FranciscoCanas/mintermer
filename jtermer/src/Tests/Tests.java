package Tests;

import logic.TruthTable;

/**
 * Created by fcanas on 1/23/14.
 */
public class Tests {
    public static void main(String [] args) {
        String [] list = new String [] {
                "00",
                "001",
                "0000",
                "00001",
                "10001000",
                "1000100010001011"
        };


        for (String trial : list) {
            testTable(trial);
        }
    }

    private static void testTable(String out) {
        TruthTable t = new TruthTable(out);
        System.out.println("Testing Table with Input " + out);
        System.out.println("Height: " + t.getHeight());
        System.out.println("Width: " + t.getWidth());
        System.out.println("Table: ");
        System.out.println(t.toString());

        for (int i=0; i<out.length(); i++) {
            testRow(t.getRow(i),t);
        }
    }

    private static void testRow(String row, TruthTable table) {
        System.out.println(row + "->" + table.getOutput(row));
    }
}
