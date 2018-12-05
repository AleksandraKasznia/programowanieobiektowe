package lab1zaddom;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFrameDB extends DataFrame{
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private int size = 4;

    public DataFrameDB(DataFrame df){
        super(df);
        connect();
        addToDataBase(df);
    }

    public void insert(ArrayList<Value> values)throws CustomException, SQLException{
        String sql = "Insert into tbl1(id,x,y,z)VALUES (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        if (values.size() != 4) {
            throw new CustomException("Wrong number of input elements");
        }
        ps.setString(1,values.get(0).toString());
        ps.setString(2,values.get(1).toString());
        ps.setString(3,values.get(2).toString());
        ps.setString(4,values.get(3).toString());
        ps.executeBatch();
        ps.executeUpdate();
        ps.close();
    }

    public void addToDataBase(DataFrame df){
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:C:/Program Files/sqlite/sqlite-tools-win32-x86-3260000/test.db");
            for(int rowIterator=0; rowIterator<df.size(); rowIterator++) {
                ArrayList<Value> row = new ArrayList(df.getRow(rowIterator));
                insert(row);
            }
        }
        catch(SQLException | CustomException e){
            e.printStackTrace();
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
                stmt = null;
            }
        }
    }

    public static DataFrame select(String sql){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:C:/Program Files/sqlite/sqlite-tools-win32-x86-3260000/test.db");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            int numberOfColumns = metadata.getColumnCount();
            String[] names = new String[numberOfColumns];
            Class[] types = new Class[numberOfColumns];
            String[] row = new String[numberOfColumns];
            ArrayList<ArrayList> dataframe = new ArrayList<>();

                for(int i=0; i<numberOfColumns; i++){
                    int sqli = i + 1;
                    names[i] = metadata.getColumnName(sqli);
                    dataframe.add(new ArrayList());
                    if((metadata.getColumnType(sqli)) == Types.DOUBLE){
                        types[i] = DoubleHolder.class;
                    }
                    else{
                        if((metadata.getColumnType(sqli)) == Types.VARCHAR || (metadata.getColumnType(sqli)) == Types.CHAR){
                            types[i] = StringHolder.class;
                        }
                        else {
                            if((metadata.getColumnType(sqli)) == Types.FLOAT){
                                types[i] = FloatHolder.class;
                            }
                            else{
                                if((metadata.getColumnType(sqli)) == Types.INTEGER){
                                    types[i] = IntHolder.class;
                                }
                                else{
                                    if((metadata.getColumnType(sqli)) == Types.DATE){
                                        types[i] = DateTimeHolder.class;
                                    }
                                    else{
                                        throw new CustomException("wrong class");
                                    }
                                }
                            }
                        }
                    }
                }
                DataFrame df = new DataFrame(names,dataframe);
                df.types = types;

            while (rs.next()) {
                for(int columnIterator=0; columnIterator<numberOfColumns; columnIterator++){
                    row[columnIterator] = rs.getString(columnIterator+1);
                }
                df.add(row);
            }

            return df;
        } catch (SQLException | CustomException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
        }
        return null;
    }

    public DataFrame min(String colname){
        String statement = "select min("+colname+") from tbl1;";
        return select(statement);
    }

    public DataFrame max(String colname){
        String statement = "select max("+colname+") from tbl1;";
        return select(statement);
    }

    DataFrame groupby(String[] colnames, String agrFunction){
        String[] columnToPrint = {"id","x", "y","z"};
        List<String> columnList = Arrays.asList(colnames);
        String statement = "select ";
        for(String column:columnToPrint){
            if(columnList.contains(column)){
                statement += column;
            }
            else{
                statement += agrFunction+"("+column+")";
            }
            if(!column.equals("z")){
                statement += ",";
            }

        }
        statement += " from tbl1 group by ";

        for(int i=0; i<columnList.size(); i++){
            statement += columnList.get(i);
            if(i!=columnList.size()-1){
                statement += ", ";
            }
        }
        statement += ";";
        return select(statement);
    }

    public boolean connect() {
        try {
                conn = DriverManager.getConnection("jdbc:sqlite:C:/Program Files/sqlite/sqlite-tools-win32-x86-3260000/test.db");
        } catch (Exception ex) {
            return false;
        }
        finally {
            try{
                if (conn!=null){
                    conn.close();
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        if (conn != null) return true;
        return false;
    }
}
