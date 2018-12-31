package lab1zaddom;

import java.util.ArrayList;

public class SumThread extends Thread {
    DataFrame data;
    Value sum;
    int rowIterator;
    final ArrayList<Value> row;
    ArrayList<Value> rowtmp = new ArrayList<>();

    SumThread(DataFrame data, final ArrayList<Value> row){
        this.data = data;
        this.row = row;
    }

    public void run(){
        for(ArrayList<? extends Value> column: data.dataFrame){
            if(data.ifColumnIsNumeric(column)){
                sum = column.get(0);

                for(rowIterator=0; rowIterator<column.size(); rowIterator++){
                    if(rowIterator!=0){
                        sum.add(column.get(rowIterator));
                    }
                }
                rowtmp.add(sum);
            }
            else{
                rowtmp.add(null);
            }
        }
        synchronized (row){
            row.addAll(rowtmp);
        }
    }
}
