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
                "1000100010001011",
                "11111101111011111111111100000000"
        };

        boolean pass = true;
        for (String trial : list) {
            printTable(trial);
            pass = testTableMapping(trial) ? pass : false;
            pass = testTableOrdering(trial) ? pass : false;
        }

        /**
         * Big test here:
         */
        String out = randomOutputVector(65536);
        pass = testTableMapping(out) ? pass : false;
        pass = testTableOrdering(out) ? pass : false;

        if (!pass) {
            System.out.println("Failed.");
        } else {
            System.out.println("Passed.");
        }
    }

    private static String randomOutputVector(int sizeOfOutputVector) {
        StringBuilder sb = new StringBuilder(sizeOfOutputVector + 1);
        Double rand;
        for (int i=0; i < sizeOfOutputVector; i++) {
            rand = (Math.random() * 100);
            sb.append(Integer.toString(rand.intValue() % 2));
        }
        return sb.toString();
    }

    private static boolean testTableMapping(String outVector) {
        TruthTable table = new TruthTable(outVector);
        System.out.println("Testing Table Mapping");

        for (int i=0; i < table.getHeight() && i < outVector.length(); i++) {
            String in = makeRow(i,table.getWidth());

            Boolean out = outVector.charAt(i) == table.TRUE;
            if ((testMapping(table, in, out) == false)) {
                System.out.println("Table Mapping Test Failed.");
                return false;
            }
        }
        return true;
    }

    private static boolean testTableOrdering(String outVector) {
        TruthTable table = new TruthTable(outVector);
        System.out.println("Testing Table Ordering");

        for (int i=0; i < table.getHeight() && i < outVector.length(); i++) {
            String expected = makeRow(i,table.getWidth());
            String actual = table.getRow(i);
            String pass = expected.equals(actual) ? "PASS" : "FAIL";
            System.out.println("[" + pass + "] " + i + ") " + expected + "->" +  actual);
            if (!expected.equals(actual)) {
                System.out.println("Table Ordering Test Failed.");
                return false;
            }
        }
        return true;
    }

    private static void printTable(String out) {
        TruthTable t = new TruthTable(out);
        System.out.println("Testing Table with Input " + out);
        System.out.println("Height: " + t.getHeight());
        System.out.println("Width: " + t.getWidth());
        System.out.println("Table: ");
        System.out.println(t.toString());

        for (int i=0; i<out.length(); i++) {
            printRow(t.getRow(i), t);
        }
    }

    private static void printRow(String row, TruthTable table) {
        System.out.println(row + "->" + table.getOutput(row));
    }

    private static boolean testMapping(TruthTable table, String input, boolean expectedOutput) {
        boolean output = table.getOutput(input);
        String pass = output == expectedOutput ? "PASS" : "FAIL";
        System.out.println("[" + pass + "] " + input + "->" +  output);
        return output == expectedOutput;
    }

    private static String makeRow(int rowNum, int width) {
        StringBuilder sb = new StringBuilder();
        String vec = Integer.toBinaryString(rowNum);
        int padLength = width - vec.length();

        for (int i=0; i<padLength; i++){
            sb.append("0");
        }

        sb.append(vec);
        return sb.toString();
    }
}
