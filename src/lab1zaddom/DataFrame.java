package lab1zaddom;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class DataFrame implements Cloneable{
    ArrayList<ArrayList> dataFrame;
    String[] names;
    Class<? extends Value>[] types;
    ExecutorService es = Executors.newCachedThreadPool();
    Set<GroupByThread> goupbyThreads = new HashSet<>();


    public DataFrame(String[] names, Class<? extends Value>[] types){
        dataFrame = new ArrayList<>();
        this.names = names;
        this.types = types;
        try{
            initiate(types);
        }
        catch(CustomException e){
            e.printStackTrace();
        }

    }

    DataFrame(String[] names, ArrayList<ArrayList> data){
        this.names = names;
        this.dataFrame = data;
    }

    DataFrame(ArrayList<ArrayList> data, String[] names, Class<Value>[] types){
        this.names = names;
        this.dataFrame = data;
        this.types = types;
    }

    DataFrame(DataFrame df){
        names = df.names;
        types = df.types;
        dataFrame = df.dataFrame;
    }

    DataFrame(String fileName, Class<? extends Value>[] types, boolean isHeader){
        try ( BufferedReader br = Files.newBufferedReader(Paths.get(fileName))){
            dataFrame = new ArrayList<>();
            String line = br.readLine();
            if(line.split(",").length!=types.length){
                System.out.print("Amount of given types can't differ from number of columns in file");
            }
            else{
                names = new String[types.length];
                if(isHeader && line!=null){
                    names = line.split(",");
                    line = br.readLine();
                }
                initiate(types);
                this.types = types;

                while(line!=null){
                    String[] attributes = line.split(",");
                    add(attributes);
                    line = br.readLine();
                }
            }
        } catch (IOException | NumberFormatException | CustomException e){
            e.printStackTrace();
        }


    }

    int size(){
        return dataFrame.get(0).size();
    }

    ArrayList get(String colname)throws CustomException{
        int i;
        for (i=0; i<names.length; i++){
            if (names[i].equals(colname)){
                return dataFrame.get(i);
            }
        }
        throw new CustomException("There is no such column");
    }

    DataFrame get(String[] cols, boolean copy) throws CustomException{
        ArrayList list = new ArrayList();
        for (String col : cols) {
            for(int x =0; x<names.length; x++){
                if(col.equals(names[x])){
                    list.add(copy?dataFrame.get(x).clone():dataFrame.get(x));
                }
            }
        }
        if(list.size() == 0){
            throw new CustomException("No such column");
        }
        return new DataFrame(cols, list);
    }

    ArrayList getRow(int indexOfRow){
        ArrayList row = new ArrayList();
        for(int columnIterator=0; columnIterator<names.length; columnIterator++){
            row.add(dataFrame.get(columnIterator).get(indexOfRow));
        }
        return row;
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

    void initiate(Class<? extends Value>[] v) throws CustomException{
        if(v.length == names.length){
                for(int columnIterator=0; columnIterator<names.length; columnIterator++) {
                    if (Value.class.isAssignableFrom(v[columnIterator])) {
                        if (v[columnIterator] == IntHolder.class) {
                            dataFrame.add(new ArrayList<IntHolder>());
                        }
                        if (v[columnIterator] == DoubleHolder.class) {
                            dataFrame.add(new ArrayList<DoubleHolder>());
                        }
                        if (v[columnIterator] == FloatHolder.class) {
                            dataFrame.add(new ArrayList<FloatHolder>());
                        }
                        if (v[columnIterator] == StringHolder.class) {
                            dataFrame.add(new ArrayList<StringHolder>());
                        }
                        if (v[columnIterator] == DateTimeHolder.class) {
                            dataFrame.add(new ArrayList<DateTimeHolder>());
                        }

                    }
                    else{
                        throw new CustomException("Wrong type of input");
                    }
                }
        }
        else{
            throw new CustomException("Number of names must be equal to number of types");
        }
    }

    void add(String [] content) throws NumberFormatException, CustomException{
        ArrayList <Value> values = new ArrayList<>();
        if(content.length > names.length){
            throw new CustomException("Too many arguments to add");
        }
        if(content.length < names.length){
            throw new CustomException("Too few arguments to add");
        }
        try {
            for (int columnIterator = 0; columnIterator < names.length; columnIterator++) {
                values.add(Value.builder(types[columnIterator]).build(content[columnIterator]));
            }
        }
        catch(InstantiationException | IllegalAccessException | CustomException | InvocationTargetException | NoSuchMethodException e){
            e.printStackTrace();
            values.add(null);
        }

            addRow(values);
    }

    void addRow(ArrayList row){
        for(int columnIterator=0; columnIterator<names.length; columnIterator++){
            dataFrame.get(columnIterator).add(row.get(columnIterator));
        }
    }

    void addToColumn(String columnName, Value toAdd){
        try{
            ArrayList column = get(columnName);
            if(!ifColumnIsNumeric(column)){
                throw new CustomException("Values are not numeric in this column");
            }
            int columnSize = column.size();
            for(int iterator=0; iterator<columnSize; iterator++){
                ((Value) column.get(iterator)).add(toAdd);
            }
        }catch (CustomException e){
            e.printStackTrace();
        }

    }

    void substractFromColumn(String columnName, Value toAdd){
        try{
            ArrayList column = get(columnName);
            if(!ifColumnIsNumeric(column)){
                throw new CustomException("Values are not numeric in this column");
            }
            int columnSize = column.size();
            for(int iterator=0; iterator<columnSize; iterator++){
                ((Value) column.get(iterator)).sub(toAdd);
            }
        }catch (CustomException e){
            e.printStackTrace();
        }

    }

    void multiplyColumn(String columnName, Value toAdd){
        try{
            ArrayList column = get(columnName);
            if(!ifColumnIsNumeric(column)){
                throw new CustomException("Values are not numeric in this column");
            }
            int columnSize = column.size();
            for(int iterator=0; iterator<columnSize; iterator++){
                ((Value) column.get(iterator)).mul(toAdd);
            }
        }catch (CustomException e){
            e.printStackTrace();
        }

    }

    void divideColumn(String columnName, Value toAdd){
        try{
            ArrayList column = get(columnName);
            if(!ifColumnIsNumeric(column)){
                throw new CustomException("Values are not numeric in this column");
            }
            int columnSize = column.size();
            for(int iterator=0; iterator<columnSize; iterator++){
                ((Value) column.get(iterator)).div(toAdd);
            }
        }catch (CustomException e){
            e.printStackTrace();
        }

    }

    void addColumToColumn(String columnToAddTo ,String columnToAdd){
        try{
            ArrayList columnTAT = get(columnToAddTo);
            ArrayList columnTA = get(columnToAdd);
            if(!ifColumnIsNumeric(columnTA) || !ifColumnIsNumeric(columnTAT)){
                throw new CustomException("Those are not numeric columns");
            }
            int columnTATSize = columnTAT.size();
            int columnTASize = columnTA.size();
            if(columnTASize != columnTATSize){
                throw new CustomException("Columns are different sizes");
            }
            for(int iterator=0; iterator<columnTASize; iterator++){
                Value toAdd = (Value)((Value)columnTA.get(iterator)).clone();
                ((Value)columnTAT.get(iterator)).add(toAdd);
            }
        }catch (CustomException | CloneNotSupportedException e){
            e.printStackTrace();
        }

    }

    void substractColumnFromColumn(String columnToSubstractFrom ,String columnToSubstract){
        try{
            ArrayList columnTSF = get(columnToSubstractFrom);
            ArrayList columnTS = get(columnToSubstract);
            if(!ifColumnIsNumeric(columnTS) || !ifColumnIsNumeric(columnTSF)){
                throw new CustomException("Those are not numeric columns");
            }
            int columnTAFSize = columnTSF.size();
            int columnTSSize = columnTS.size();
            if(columnTSSize != columnTAFSize){
                throw new CustomException("Columns are different sizes");
            }
            for(int iterator=0; iterator<columnTSSize; iterator++){
                Value toAdd = (Value)((Value)columnTS.get(iterator)).clone();
                ((Value)columnTSF.get(iterator)).sub(toAdd);
            }
        }catch (CustomException | CloneNotSupportedException e){
            e.printStackTrace();
        }

    }

    void multiplyColumByColumn(String columnToBeMultipied ,String columnToMultiplyBy){
        try{
            ArrayList columnTBM = get(columnToBeMultipied);
            ArrayList columnTMB = get(columnToMultiplyBy);
            if(!ifColumnIsNumeric(columnTMB) || !ifColumnIsNumeric(columnTBM)){
                throw new CustomException("Those are not numeric columns");
            }
            int columnTBMSize = columnTBM.size();
            int columnTMBSize = columnTMB.size();
            if(columnTMBSize != columnTBMSize){
                throw new CustomException("Columns are different sizes");
            }
            for(int iterator=0; iterator<columnTMBSize; iterator++){
                Value toAdd = (Value)((Value)columnTMB.get(iterator)).clone();
                ((Value)columnTBM.get(iterator)).mul(toAdd);
            }
        }catch (CustomException | CloneNotSupportedException e){
            e.printStackTrace();
        }

    }

    void divideColumnByColumn(String columnToBeDivided ,String columnToDivideBy){
        try{
            ArrayList columnTBD = get(columnToBeDivided);
            ArrayList columnTDB = get(columnToDivideBy);
            if(!ifColumnIsNumeric(columnTDB) || !ifColumnIsNumeric(columnTBD)){
                throw new CustomException("Those are not numeric columns");
            }
            int columnTBMDSize = columnTBD.size();
            int columnTDBSize = columnTDB.size();
            if(columnTDBSize != columnTBMDSize){
                throw new CustomException("Columns are different sizes");
            }
            for(int iterator=0; iterator<columnTDBSize; iterator++){
                Value toAdd = (Value)((Value)columnTDB.get(iterator)).clone();
                ((Value)columnTBD.get(iterator)).div(toAdd);
            }
        }catch (CustomException | CloneNotSupportedException e){
            e.printStackTrace();
        }

    }

    LinkedList<DataFrame> groupbyOne(String colname) throws CustomException{
        LinkedList<DataFrame> splitDataFrame = new LinkedList<DataFrame>();
            Set<Value> uniqueValues = new HashSet(get(colname));
            Iterator<Value> it = uniqueValues.iterator();
            while(it.hasNext()){
                splitDataFrame.add(createDataFrameForGivenValue(it.next(),colname));
            }
        return splitDataFrame;
    }

    SplitData groupby(String[] colnames){
        LinkedList<LinkedList<DataFrame>> tmp = new LinkedList <>();

        try {
            LinkedList<DataFrame> splitDataFrame = new LinkedList<DataFrame>(groupbyOne(colnames[0]));
            for (int colnamesIterator = 1; colnamesIterator < colnames.length; colnamesIterator++) {
                for (int holderIterator = 0; holderIterator < splitDataFrame.size(); holderIterator++) {
                    es.execute(new GroupByThread(colnames[colnamesIterator],splitDataFrame.get(holderIterator),tmp));

                }
                es.shutdown();
                boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
                if(finished) {
                    splitDataFrame = new LinkedList<DataFrame>(flattenLinkedList(tmp));
                    tmp.clear();
                }
            }
            return new SplitData(colnames,splitDataFrame);
        }
        catch(CustomException | InterruptedException e){
            e.printStackTrace();
            return null;
        }
    }

    DataFrame createDataFrameForGivenValue(Value value, String colname){
        DataFrame dataFrameOfValue = new DataFrame(names,types);
        int indexOfColumn = 0;
        while(!names[indexOfColumn].equals(colname)){
            indexOfColumn++;
        }

        for(int indexOfElement=dataFrame.get(indexOfColumn).indexOf(value); indexOfElement<dataFrame.get(indexOfColumn).size(); indexOfElement++){
            if(dataFrame.get(indexOfColumn).get(indexOfElement).equals(value)){
                dataFrameOfValue.addRow(getRow(indexOfElement));
            }
        }

        return dataFrameOfValue;
    }

    LinkedList<DataFrame> flattenLinkedList(LinkedList<LinkedList<DataFrame>> linkedListOfLinkedLists){
        LinkedList<DataFrame> flattenList = new LinkedList<>();
        for(int listIterator=0; listIterator<linkedListOfLinkedLists.size(); listIterator++){
            flattenList.addAll(linkedListOfLinkedLists.get(listIterator));
        }
        return flattenList;
    }

    public boolean ifColumnIsNumeric(ArrayList column){
        return (column.get(0) instanceof IntHolder | column.get(0) instanceof DoubleHolder | column.get(0)
                instanceof FloatHolder | column.get(0) instanceof DateTimeHolder);
    }

    public class SplitData implements GroupBy {
        LinkedList<DataFrame> listOfSplitDataFrames;
        DataFrame output;
        ArrayList<Value> row;
        ArrayList<Class> typesOfOutput;
        String[] namesToGroupBy;
        ArrayList<String> namesOfOutput;
        ExecutorService esSplitData = Executors.newCachedThreadPool();

        SplitData(String[] colnames, LinkedList<DataFrame> listOfSplitDataFrames){
            namesToGroupBy = colnames;
            this.listOfSplitDataFrames=listOfSplitDataFrames;
            row = new ArrayList<>();
            namesOfOutput = new ArrayList<>();
            typesOfOutput = new ArrayList<>();
            output = new DataFrame(names, types);
        }

        @Override
        public DataFrame max() {
            for (DataFrame data: listOfSplitDataFrames){
                esSplitData.execute(new MaxThread(data,output));
            }
            esSplitData.shutdownNow();
            return output;
        }

        @Override
        public DataFrame min() {
            for (DataFrame data: listOfSplitDataFrames){
                esSplitData.execute(new MaxThread(data,output));
            }
            esSplitData.shutdownNow();
            return output;
        }

        public ArrayList<Value> meanOfColumn(DataFrame data){
            row.clear();
            ExecutorService esSplitData = Executors.newCachedThreadPool();
            esSplitData.execute(new MeanThread(data,row));
            esSplitData.shutdown();
            try {
                boolean finished = esSplitData.awaitTermination(1, TimeUnit.MINUTES);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            return row;
        }

        @Override
        public DataFrame mean() {
            ArrayList<Value> rowOfMeans;
            int index;
                for (DataFrame data : listOfSplitDataFrames) {
                    rowOfMeans = new ArrayList<>(meanOfColumn(data));
                    row.clear();
                    index = 0;
                    for (ArrayList<? extends Value> column : data.dataFrame) {
                        if (Arrays.stream(namesToGroupBy).anyMatch(names[index]::equals)) {
                            row.add(column.get(0));
                        } else {
                            row.add(rowOfMeans.get(index));
                        }
                        index++;
                    }
                    output.addRow(row);
                }

            return output;
        }

        @Override
        public DataFrame std() {
            ArrayList<Value> rowOfMeans;
            int index;
                for (DataFrame data : listOfSplitDataFrames) {
                    rowOfMeans = new ArrayList<>(meanOfColumn(data));
                    row.clear();
                    index = 0;
                    ExecutorService esSplitData = Executors.newCachedThreadPool();
                    esSplitData.execute(new StdThread(index, rowOfMeans, data, row, namesToGroupBy));
                    esSplitData.shutdown();
                    try {
                        boolean finished = esSplitData.awaitTermination(1, TimeUnit.MINUTES);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    output.addRow(row);
                }
            return output;
        }

        @Override
        public DataFrame sum() {

            for (DataFrame data: listOfSplitDataFrames){
                row.clear();
                ExecutorService esSplitData = Executors.newCachedThreadPool();
                esSplitData.execute(new SumThread(data,row));
                esSplitData.shutdown();
                try {
                    boolean finished = esSplitData.awaitTermination(1, TimeUnit.MINUTES);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                output.addRow(row);
            }

            return output;
        }

        @Override
        public DataFrame var() {
            ArrayList<Value> rowOfMeans;
            int index;

                for (DataFrame data : listOfSplitDataFrames) {
                    rowOfMeans = new ArrayList<>(meanOfColumn(data));
                    row.clear();
                    index = 0;
                    ExecutorService esSplitData = Executors.newCachedThreadPool();
                    esSplitData.execute(new VarThread(index, rowOfMeans, data, row, namesToGroupBy));
                    esSplitData.shutdown();
                    try {
                        boolean finished = esSplitData.awaitTermination(1, TimeUnit.MINUTES);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    output.addRow(row);
                }
            return output;
        }

        @Override
        public DataFrame apply(Applyable a) {
            DataFrame output = new DataFrame(names,types);

            for(DataFrame df: listOfSplitDataFrames){
                try {
                    DataFrame oneOfSplitData = a.apply((DataFrame) df.clone());
                    for(int rowIterator=0; rowIterator<df.names.length; rowIterator++){
                        output.addRow(oneOfSplitData.getRow(rowIterator));
                    }
                }
                catch(CloneNotSupportedException e){
                    e.printStackTrace();
                }

            }
            return output;
        }
    }

}
