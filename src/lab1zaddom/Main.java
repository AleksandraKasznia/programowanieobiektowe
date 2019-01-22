package lab1zaddom;

public class Main {
    public static void main(String[] argv) {
        DataFrame testdata = new DataFrame("C:\\Users\\okasz\\IdeaProjects\\programowanieobiektowe\\src\\large_groupby.csv",new Class[]{FloatHolder.class, DateTimeHolder.class, StringHolder.class, FloatHolder.class,
                FloatHolder.class},true);
        DataFrame k = testdata.groupby(new String[]{"id"}).sum();

        int t = 0;
    }
}

