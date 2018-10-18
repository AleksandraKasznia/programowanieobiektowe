package lab1zaddom;

public class Main {
    public static void main(String[] argv)throws CustomException{
        DataFrame df = new  DataFrame("test.csv", new String[]{"int","int","int"},true);
        SparseDataFrame sdf =new SparseDataFrame(df,"0");
        DataFrame nedata = sdf.toDense();
        int k;
    }
}
