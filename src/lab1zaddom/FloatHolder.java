package lab1zaddom;

import java.util.Objects;

public class FloatHolder extends Value{
    Float floatValue;

    public FloatHolder (float x){
        floatValue = x;
    }


    FloatHolder(){};


    public Float getValue() {
        return floatValue;
    }

    @Override
    public String toString() {
        return java.lang.Float.toString(floatValue);
    }


    @Override
    public Value add(Value value) {
        if (value instanceof FloatHolder){
            this.floatValue += ((FloatHolder) value).getValue();
        }
        return this;
    }

    @Override
    public Value sub(Value value) {
        if (value instanceof FloatHolder){
            this.floatValue -= ((FloatHolder) value).getValue();
        }
        return this;

    }

    @Override
    public Value mul(Value value) {
        if (value instanceof FloatHolder){
            this.floatValue*= ((FloatHolder) value).getValue();
        }
        return this;

    }

    @Override
    public Value div(Value value) {
        if (value instanceof FloatHolder){
            this.floatValue /= ((FloatHolder) value).getValue();
        }
        if (value instanceof IntHolder){
            this.floatValue /= ((IntHolder)value).getValue();
        }
        return this;

    }

    @Override
    public Value pow(Value value) {
        if (value instanceof FloatHolder){
            this.floatValue = (float)Math.pow(this.floatValue,((FloatHolder) value).getValue());
        }
        if (value instanceof DoubleHolder){
            this.floatValue = (float)Math.pow((double)this.floatValue,((DoubleHolder) value).getValue());
        }
        return this;

    }

    @Override
    public boolean eq(Value value) {
        if (value instanceof FloatHolder){
            return Objects.equals(this.floatValue, ((FloatHolder) value).getValue());
        }
        return false;
    }

    @Override
    public boolean lte(Value value) {
        if (value instanceof FloatHolder) {
            return this.floatValue < ((FloatHolder) value).getValue();
        }
        return false;
    }

    @Override
    public boolean gte(Value value) {
        if (value instanceof FloatHolder) {
            return this.floatValue> ((FloatHolder) value).getValue();
        }
        return false;
    }

    @Override
    public boolean neq(Value value) {
        if (value instanceof FloatHolder){
            return !Objects.equals(this.floatValue, ((FloatHolder) value).getValue());
        }
        return false;
    }

    public FloatHolder create(String s){
        floatValue = Float.parseFloat(s);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FloatHolder)) return false;
        FloatHolder that = (FloatHolder) o;
        return Float.compare(that.floatValue, floatValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floatValue);
    }

    @Override
    public int compareTo(Value o) {
        return floatValue.compareTo(((FloatHolder)o).getValue());
    }
}
