package lab1zaddom;

import java.util.Date;
import java.util.LinkedList;

public class Main {
    public static void main(String[] argv)throws CustomException{
        DataFrame testdata = new DataFrame("test.csv",new Class[]{StringHolder.class, FloatHolder.class, FloatHolder.class, FloatHolder.class},true);
        String[] str = new String[]{"id"};
        testdata = testdata.groupby(str).std();
        int k =1;
    }
}
