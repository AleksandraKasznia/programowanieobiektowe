package lab1zaddom;

import java.util.Date;
import java.util.LinkedList;

public class Main {
    public static void main(String[] argv){
        DataFrame testdata = new DataFrame("test.csv",new Class[]{StringHolder.class, FloatHolder.class, FloatHolder.class, FloatHolder.class},true);
        testdata.divideColumnByColumn("total","val");
        int k =1;
    }
}
