package lab1zaddom;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateTimeHolder extends Value{
    Date dateValue;
    private static DateTimeHolder dateTimeHolder = new DateTimeHolder();

    public DateTimeHolder (Date x){
        dateValue = x;
    }

    public static DateTimeHolder getInstance(){
        return dateTimeHolder;
    }

    DateTimeHolder(){
        dateValue = new Date();
    };

    public Date getValue() {
        return dateValue;
    }

    @Override
    public String toString() {
        return dateValue.toString();
    }

    @Override
    public Value add(Value value) {
        return null;
    }

    @Override
    public Value sub(Value value) {
        return null;
    }

    @Override
    public Value mul(Value value) {
        return null;
    }

    @Override
    public Value div(Value value) {
        return null;
    }

    @Override
    public Value pow(Value value) {
        return null;
    }

    @Override
    public boolean eq(Value value) {
        if (value instanceof DateTimeHolder){
            return Objects.equals(this.dateValue, ((DateTimeHolder) value).getValue());
        }
        return false;
    }

    @Override
    public boolean lte(Value value) {
        if (value instanceof DateTimeHolder){
            return dateValue.before( ((DateTimeHolder) value).dateValue);
        }
        return false;
    }

    @Override
    public boolean gte(Value value) {
        if (value instanceof DateTimeHolder){
            return dateValue.after( ((DateTimeHolder) value).dateValue);
        }
        return false;
    }

    @Override
    public boolean neq(Value value) {
        if (value instanceof DateTimeHolder){
            return !Objects.equals(this.dateValue, ((DateTimeHolder) value).getValue());
        }
        return false;
    }

    @Override
    public DateTimeHolder create(String s){
        if(s.matches("\\d{4}-\\d{2}-\\d{2}")){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try{
                dateValue = format.parse(s);
            }
            catch (ParseException e){
                return null;
            }
            return this;

        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateTimeHolder)) return false;
        DateTimeHolder that = (DateTimeHolder) o;
        return Objects.equals(dateValue, that.dateValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateValue);
    }
}
