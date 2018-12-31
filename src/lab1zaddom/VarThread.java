package lab1zaddom;

import java.util.ArrayList;
import java.util.Arrays;

public class VarThread extends Thread{
    Value sum;
    Value distance;
    Value mean;
    Value val;
    int index;
    int rowIterator;
    DataFrame data;
    final ArrayList<Value> row;
    String[] namesToGroupBy;
    ArrayList<Value> rowtmp = new ArrayList<>();
    ArrayList<Value> rowOfMeans;

    VarThread(int index, ArrayList<Value> rowOfMeans, DataFrame data, final ArrayList<Value> row, String[] namesToGroupBy){
        this.index = index;
        this.rowOfMeans = rowOfMeans;
        this.data = data;
        this.row = row;
        this.namesToGroupBy = namesToGroupBy;
    }

    public void run(){
        try {
            for (ArrayList<? extends Value> column : data.dataFrame) {
                if (Arrays.stream(namesToGroupBy).anyMatch(data.names[index]::equals)) {
                    rowtmp.add((Value) column.get(0).clone());
                } else {
                    if (rowOfMeans.get(index) != null) {
                        mean = (Value) rowOfMeans.get(index).clone();
                        val = (Value) column.get(0).clone();
                        distance = val.sub(mean);
                        sum = (Value) distance.clone();
                        sum = sum.mul(sum);
                        for (rowIterator = 0; rowIterator < column.size(); rowIterator++) {
                            if (rowIterator != 0) {
                                val = (Value) column.get(rowIterator).clone();
                                distance = val.sub(mean);
                                sum.add(distance.mul(distance));
                            }
                        }
                        if (rowIterator <= 1) {
                            rowIterator = 2;
                        }
                            rowtmp.add(sum.div(new IntHolder(rowIterator - 1)));
                    } else {
                        rowtmp.add(null);
                    }
                }
                index++;
            }
        }
        catch(CloneNotSupportedException | CustomException e){
            e.printStackTrace();
        }
        synchronized (row){
            row.addAll(rowtmp);
        }
    }
}
