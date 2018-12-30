package lab1zaddom;

import java.util.ArrayList;
import java.util.Collections;

public class MaxThread extends Thread{
    DataFrame dataToCount;
    ArrayList<Value> row;
    public final DataFrame output;

    MaxThread(DataFrame dataToCount, final DataFrame output){
        this.output = output;
        row = new ArrayList<>();
        this.dataToCount = dataToCount;
    }

    public void run(){
        for(ArrayList<? extends Value> column: dataToCount.dataFrame){
            row.add(Collections.max(column));
        }
        synchronized (output){
            output.addRow(row);
        }
    }
}
