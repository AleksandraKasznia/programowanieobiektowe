package lab1zaddom;

import java.util.Date;
import java.util.LinkedList;

public class Main {
    public static void main(String[] argv)throws CustomException{
        CooValue x = new CooValue(1, new IntHolder(11));
        IntHolder intHolder = new IntHolder(6);
        StringHolder stringholder = new StringHolder("blabla");
        DateTimeHolder data = new DateTimeHolder(new Date());
        DataFrame testdata = new DataFrame("test.csv",new Class[]{FloatHolder.class, FloatHolder.class,FloatHolder.class},true);
        LinkedList lis = new LinkedList();
        String[] str = new String[]{"x"};
        testdata = testdata.groupby(str).max();
        int k =1;
    }
}
