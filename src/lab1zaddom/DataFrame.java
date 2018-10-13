package lab1zaddom;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;


public class DataFrame {
    protected ArrayList<ArrayList> dataFrame;
    protected String[] names;
    protected String[] types;


    public DataFrame(String[] names, String[] types){
        dataFrame = new ArrayList<>();
        int size = names.length;
        this.names = names;
        this.types = types;
        for (int i=0; i<size; i++){
            dataFrame.add(new ArrayList());
        }

    }

    DataFrame(String[] names, ArrayList<ArrayList> data){
        this.names = names;
        this.dataFrame = data;
    }

    DataFrame(DataFrame df){
        names = df.names;
        types = df.types;
        dataFrame = df.dataFrame;
    }

    DataFrame(String fileName, String[] types, boolean isHeader){
        try ( BufferedReader br = Files.newBufferedReader(Paths.get(fileName))){
            dataFrame = new ArrayList<>();
            String line = br.readLine();
            if(isHeader && line!=null){
                names = line.split(",");
                line = br.readLine();
            }

            while(line!=null){
                String[] attributes = line.split(",");
                add(attributes);
                line = br.readLine();

            }

        } catch (Exception exception){
            exception.printStackTrace();
        }


    }

    int Size(){
        return dataFrame.get(0).size();
    }

    ArrayList get(String colname){
        int i;
        for (i=0; i<names.length; i++){
            if (names[i].equals(colname)){
                return dataFrame.get(i);
            }
        }
        return null;
    }

    DataFrame get(String[] cols, boolean copy){
        ArrayList list = new ArrayList();
        for (String col : cols) {
            for(int x =0; x<names.length; x++){
                if(col.equals(names[x])){
                    list.add(copy?dataFrame.get(x).clone():dataFrame.get(x));
                }
            }
        }
        return new DataFrame(cols, list);
    }

    DataFrame iloc(int i){
        ArrayList list = new ArrayList();
        for (int k=0; k<dataFrame.size(); k++){
            if(dataFrame.get(k).size()>i){
                list.add(dataFrame.get(k).get(i));
            }
            else{
                return null;
            }
        }
        return new DataFrame(names, list);
    }

    DataFrame iloc(int from, int to){
        ArrayList list = new ArrayList();
        ArrayList df = new ArrayList();
        for (int i=0; i<dataFrame.size(); i++){
            for (int k=from; k<to; k++){
                if(dataFrame.get(i).size()>k){
                    list.add(dataFrame.get(i).get(k));
                }
                else{
                    return null;
                }
            }
            df.add(list);
        }
        return new DataFrame(names, df);
    }

    void add(Object[] values)throws CustomException{
        if(values.length == names.length){
            if (dataFrame.size()==0){
                for (int i=0; i<names.length; i++){
                    ArrayList temp = new ArrayList();
                    temp.add(values[i]);
                    dataFrame.add(temp);
                }
            }
            else{
                for(int columnIterator=0; columnIterator<names.length; columnIterator++){
                    dataFrame.get(columnIterator).add(values[columnIterator]);
                }
            }
        }
        else {
            throw new CustomException("Number of adding objects can't differ from the number of columns");
        }
    }
}



