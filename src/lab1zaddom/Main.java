package lab1zaddom;

import java.util.Date;
import java.util.LinkedList;

public class Main {
    public static void main(String[] argv){
        DataFrame testdata = new DataFrame("test.csv",new Class[]{StringHolder.class, FloatHolder.class, FloatHolder.class, FloatHolder.class},true);
        DataFrameDB db = new DataFrameDB(testdata);
        db.connect();
        DataFrame x = db.max("x");
        DataFrame b = db.groupby(new String[]{"id","x"},"count");
        int k =1;
    }
}
