package lab1zaddom;

import java.util.ArrayList;

public class SparseDataFrame extends DataFrame {
    ArrayList<ArrayList<CooValue>> sparseDataFrame;
    int numberOfColumns;
    int sizeOfColumn;
    Object toHide;

    public SparseDataFrame(String[] namesOfColumns, String[] typesOfColumns, Object hide ) {
        super(namesOfColumns, typesOfColumns);
        numberOfColumns = namesOfColumns.length;
        names = namesOfColumns;
        types = typesOfColumns;
        toHide = hide;
    }

    public SparseDataFrame(DataFrame df, Object hide){
        super(df);
        numberOfColumns = names.length;
        sizeOfColumn = df.get(df.names[0]).size();
        toHide = hide;
        sparseDataFrame = new ArrayList<>();

        for(int columnIterator=0; columnIterator<numberOfColumns; columnIterator++){
            ArrayList temp = df.get(names[columnIterator]);
            for(int rowIterator=0; rowIterator<temp.size(); rowIterator++){
                if(temp.get(rowIterator)!=toHide){
                    if(rowIterator==0){
                        sparseDataFrame.add(new ArrayList<>());
                    }
                    sparseDataFrame.get(columnIterator).add(new CooValue(rowIterator,temp.get(rowIterator)));
                }
            }
        }
    }


    DataFrame toDense() throws CustomException{
        DataFrame standardDataFrame = new DataFrame(names, types);
        Object[] temp = new Object[numberOfColumns];

        for(int rowIterator=0; rowIterator<sizeOfColumn; rowIterator++){
            for(int columnIterator=0; columnIterator<names.length; columnIterator++){
                if(sparseDataFrame.get(columnIterator).get(rowIterator).index!=rowIterator){
                    temp[columnIterator] = toHide;
                }
                else{
                    temp[columnIterator] = sparseDataFrame.get(columnIterator).get(rowIterator).content;
                }
            }
            standardDataFrame.add(temp);
        }
        return standardDataFrame;
    }

    void add(Object[] values) throws CustomException{
        if(values.length == numberOfColumns){
            int rowIterator;

            for(int columnIterator=0; columnIterator<numberOfColumns; columnIterator++){
                rowIterator=0;
                while (sparseDataFrame.get(columnIterator).get(rowIterator)!=null){
                    rowIterator++;
                }
                if(values[columnIterator]!=toHide){
                    sparseDataFrame.get(columnIterator).add(new CooValue(rowIterator,values[columnIterator]));
                }
            }

        }
        else{
            throw new CustomException("Number of adding objects can't differ from the number of columns");
        }
    }
}
