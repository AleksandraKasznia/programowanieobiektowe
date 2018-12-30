package lab1zaddom;

import java.util.ArrayList;

public class MeanThread extends Thread {
    DataFrame data;
    final ArrayList<Value> row;
    ArrayList<Value> rowtmp = new ArrayList<>();

    MeanThread(DataFrame data, final ArrayList<Value> row){
        this.row = row;
        this.data = data;
    }

    public void run(){
        Value sum;
        int rowIterator;
        try{
            for(ArrayList<? extends Value> column: data.dataFrame) {
                if (data.ifColumnIsNumeric(column)) {
                    sum = (Value) column.get(0).clone();

                    for (rowIterator = 0; rowIterator < column.size(); rowIterator++) {
                        if (rowIterator != 0) {
                            sum.add((Value) column.get(rowIterator).clone());
                        }
                    }
                        rowtmp.add(sum.div(new IntHolder(rowIterator)));
                } else {
                    rowtmp.add(null);
                }
            }
        }
        catch (CloneNotSupportedException | CustomException e){
            e.printStackTrace();
        }
        synchronized (row){
            System.out.print(rowtmp);
            row.addAll(rowtmp);
            System.out.print(row);
        }
    }
}
