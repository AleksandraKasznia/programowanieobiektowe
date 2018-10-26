package lab1zaddom;

import java.util.ArrayList;

public class SparseDataFrame extends DataFrame {
    ArrayList<ArrayList<CooValue>> sparseDataFrame;
    int numberOfColumns;
    int sizeOfColumn;
    String toHide;

    public SparseDataFrame(String[] namesOfColumns, Class<? extends Value>[] typesOfColumns, String hide ) {
        super(namesOfColumns, typesOfColumns);
        numberOfColumns = namesOfColumns.length;
        names = namesOfColumns;
        types = typesOfColumns;
        toHide = hide;
    }

    public SparseDataFrame(DataFrame df, String hide){
        super(df);
        numberOfColumns = names.length;
        sizeOfColumn = df.get(df.names[0]).size();
        toHide = hide;
        sparseDataFrame = new ArrayList<>();

        for(int columnIterator=0; columnIterator<numberOfColumns; columnIterator++){
            ArrayList temp = df.get(names[columnIterator]);
            sparseDataFrame.add(new ArrayList<>(1));
            for(int rowIterator=0; rowIterator<temp.size(); rowIterator++){
                if(!(temp.get(rowIterator).toString()).equals(toHide)){
                    sparseDataFrame.get(columnIterator).add(new CooValue(rowIterator,(Value)temp.get(rowIterator)));
                }
            }
        }
    }


    DataFrame toDense()throws CustomException{
        DataFrame standardDataFrame = new DataFrame(names, types);
        String[] temp = new String[numberOfColumns];

        for(int rowIterator=0; rowIterator<sizeOfColumn; rowIterator++){
            int sparseDfRowIterator = 0;
            for(int columnIterator=0; columnIterator<names.length; columnIterator++){
                    if (sparseDataFrame.get(columnIterator).get(sparseDfRowIterator).index != rowIterator) {
                        temp[columnIterator] = toHide;
                    }
                    else {
                        temp[columnIterator] = sparseDataFrame.get(columnIterator).get(rowIterator).content.toString();
                        sparseDfRowIterator++;
                    }
            }
            standardDataFrame.add(temp);
        }
        return standardDataFrame;
    }

    void add(Value[] values) throws CustomException{
        if(values.length == numberOfColumns){
            int rowIterator;

            for(int columnIterator=0; columnIterator<numberOfColumns; columnIterator++){
                rowIterator=0;
                while (sparseDataFrame.get(columnIterator).get(rowIterator)!=null){
                    rowIterator++;
                }
                if(!values[columnIterator].toString().equals(toHide)){
                    sparseDataFrame.get(columnIterator).add(new CooValue(rowIterator,values[columnIterator]));
                }
            }

        }
        else{
            throw new CustomException("Number of adding objects can't differ from the number of columns");
        }
    }
}
