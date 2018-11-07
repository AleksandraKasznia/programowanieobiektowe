package lab1zaddom;

import java.util.Objects;

public class StringHolder extends Value{
    String stringValue;
    private static StringHolder stringHolder = new StringHolder();

    public StringHolder (String x){
        stringValue = x;
    }

    public static StringHolder getInstance(){
        return stringHolder;
    }

    StringHolder(){}

    public String getValue() {
        return stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
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
        if (value instanceof StringHolder){
            return Objects.equals(this.stringValue, ((StringHolder) value).getValue());
        }
        return false;
    }

    @Override
    public boolean lte(Value value) {
        return false;
    }

    @Override
    public boolean gte(Value value) {
        return false;
    }

    @Override
    public boolean neq(Value value) {
        if (value instanceof StringHolder){
            return !Objects.equals(this.stringValue, ((StringHolder) value).getValue());
        }
        return false;
    }

    @Override
    public StringHolder create(String s) {
        stringValue = s;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringHolder)) return false;
        StringHolder that = (StringHolder) o;
        return Objects.equals(stringValue, that.stringValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringValue);
    }
}
