package lab1zaddom;

import java.util.Objects;

public class DoubleHolder extends Value{
    double doubleValue;
    private static DoubleHolder doubleholder = new DoubleHolder();

    public DoubleHolder (double x){
        doubleValue = x;
    }

    public static DoubleHolder getInstance(){
        return doubleholder;
    }

    private DoubleHolder(){};


    public double getValue() {
        return doubleValue;
    }

    @Override
    public String toString() {
        return java.lang.Double.toString(doubleValue);
    }


    @Override
    public Value add(Value value) {
        if (value instanceof DoubleHolder){
            this.doubleValue += ((DoubleHolder) value).getValue();
        }
        return this;
    }

    @Override
    public Value sub(Value value) {
        if (value instanceof DoubleHolder){
            this.doubleValue -= ((DoubleHolder) value).getValue();
        }
        return this;

    }

    @Override
    public Value mul(Value value) {
        if (value instanceof DoubleHolder){
            this.doubleValue*= ((DoubleHolder) value).getValue();
        }
        return this;

    }

    @Override
    public Value div(Value value) {
        if (value instanceof DoubleHolder){
            this.doubleValue /= ((DoubleHolder) value).getValue();
        }
        return this;

    }

    @Override
    public Value pow(Value value) {
        if (value instanceof DoubleHolder){
            this.doubleValue = Math.pow(this.doubleValue,((DoubleHolder) value).getValue());
        }
        return this;

    }

    @Override
    public boolean eq(Value value) {
        if (value instanceof DoubleHolder){
            return Objects.equals(this.doubleValue, ((DoubleHolder) value).getValue());
        }
        return false;
    }

    @Override
    public boolean lte(Value value) {
        if (value instanceof DoubleHolder) {
            return this.doubleValue < ((DoubleHolder) value).getValue();
        }
        return false;
    }

    @Override
    public boolean gte(Value value) {
        if (value instanceof DoubleHolder) {
            return this.doubleValue> ((DoubleHolder) value).getValue();
        }
        return false;
    }

    @Override
    public boolean neq(Value value) {
        if (value instanceof DoubleHolder){
            return !Objects.equals(this.doubleValue, ((DoubleHolder) value).getValue());
        }
        return false;
    }

    public DoubleHolder create(String s){
        doubleValue = Double.parseDouble(s);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleHolder)) return false;
        DoubleHolder that = (DoubleHolder) o;
        return Double.compare(that.doubleValue, doubleValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(doubleValue);
    }
}
