package lab1zaddom;

import java.util.LinkedList;

public class GroupByThread extends Thread{
    String colname;
    DataFrame dataToGroup;
    public final LinkedList<LinkedList<DataFrame>> listOfGrouped;

    public GroupByThread(String colname, DataFrame dataToGroup, final LinkedList<LinkedList<DataFrame>> listOfGrouped){
        this.colname = colname;
        this.dataToGroup = dataToGroup;
        this.listOfGrouped = listOfGrouped;
    }

    public void run(){
        try {
            LinkedList<DataFrame> list = dataToGroup.groupbyOne(colname);
            synchronized (listOfGrouped) {
                listOfGrouped.add(list);
            }
        }
        catch(CustomException e){
            e.printStackTrace();
        }
    }
}
