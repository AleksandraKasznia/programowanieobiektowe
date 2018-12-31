package lab1zaddom;

public class Main {
    public static void main(String[] argv) {
        DataFrame testdata = new DataFrame("test.csv",new Class[]{StringHolder.class, FloatHolder.class,
                FloatHolder.class, FloatHolder.class},true);
        DataFrame k = testdata.groupby(new String[]{"id","date"}).sum();
        int t = 0;
    }
}

