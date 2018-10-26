package lab1zaddom;

import java.util.Date;

public class Main {
    public static void main(String[] argv)throws CustomException{
        CooValue x = new CooValue(1, new IntHolder(11));
        IntHolder intHolder = new IntHolder(6);
        StringHolder stringholder = new StringHolder("blabla");
        DateTimeHolder data = new DateTimeHolder(new Date());
        data = DateTimeHolder.getInstance().create("1998-09-30");
        DataFrame testdata = new DataFrame("test.csv",new Class[]{FloatHolder.class, FloatHolder.class,FloatHolder.class},true);
        int k =1;
    }
}
