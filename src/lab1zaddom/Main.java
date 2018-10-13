package lab1zaddom;

public class Main {
    public static void main(String[] argv){
        DataFrame df = new  DataFrame("test.csv", new String[]{"int","int","int"},true);
        SparseDataFrame sdf =new SparseDataFrame(df,0);
    }
}
