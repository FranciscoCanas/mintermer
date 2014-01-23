package logic;

import com.sun.org.apache.xml.internal.utils.IntVector;
import java.math.*;
import java.util.HashMap;

/**
 * Represents a Truth Table.
 * Created by fcanas on 1/23/14.
 */
public class TruthTable {
    String output;
    char [] outputVector;
    private Integer length;
    private Integer width;
    private Integer height;
    private HashMap<Integer,String> table;


    private static final char TRUE = '1';
    private static final char FALSE = '0';

    /**
     * Given a string containing the truth table's desired output vector, it generates a
     * truth table of the correct size.
     *
     * ie.: 01 ->   A | Out
     *              0   0
     *              1   1
     *
     *    : 0110 -> A   B   Out
     *              0   0   0
     *              0   1   1
     *              1   0   1
     *              1   1   0
     *
     *              ...and so forth.
     *
     * @param outputVector the String containing an output of at least >= 2 length.
     */
    public TruthTable(String outputVector) {
        if ((length = outputVector.length()) < 2) {
            System.out.println("Truth Table must have output length of at least 2");
            return;
        }

        output = outputVector;
        initDimensions();
        initOutputVector();
        initTable();
    }

    private void initDimensions() {
        double log = Math.log(length.doubleValue()) / Math.log(2);
        width = (int)Math.ceil(log);
        height = (int)Math.pow(2, width);

    }

    private void initOutputVector() {
        outputVector = new char[height];
        char [] outputChars = output.toCharArray();

        for (int i=0; i<height; i++) {
            if (i > output.length() - 1) {
                outputVector[i] = '0';
            } else {
                outputVector[i] = outputChars[i];
            }
        }
    }

    private void initTable() {
        table = new HashMap<Integer,String>();
        for (int row=0; row < height; row++) {
            table.put(row, makeRow(row) );
        }
    }

    /**
     * Since we create the Table ourselves we are sure that there is
     * always a 1 to 1 mapping of key <-> value
     * @param value
     */
    private int getKey(String value) {
        for (int key : table.keySet()) {
            if (table.get(key).equals(value)) {
                return key;
            }
        }
        return -1;
    }

    private String makeRow(int rowNum) {
        StringBuilder sb = new StringBuilder();
        String vec = Integer.toBinaryString(rowNum);
        int padLength = width - vec.length();

        for (int i=0; i<padLength; i++){
            sb.append("0");
        }

        sb.append(vec);
        return sb.toString();
    }

    public String getRow(int num) {
        if (table.containsKey(num)) {
            return table.get(num);
        } else {
            return "";
        }
    }

    public boolean getOutput(String input) {
        if (table.containsValue(input)) {
            return (outputVector[getKey(input)]==TRUE);
        }
        return false;

    }

    public Integer getLength() {
        return length;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();


        for (int key : table.keySet()) {
            sb.append(table.get(key) +  "|" + outputVector[key] + System.lineSeparator());
        }
        return sb.toString();
    }

}