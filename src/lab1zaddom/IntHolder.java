package lab1zaddom;

import java.util.Objects;

public class IntHolder extends Value {
    int intValue;
    private static IntHolder intholder = new IntHolder();

    public IntHolder (int x){
        intValue = x;
    }

    public static IntHolder getInstance(){
        return intholder;
    }

    private IntHolder(){}


    public int getValue() {
        return intValue;
    }

    @Override
    public String toString() {
        return java.lang.Integer.toString(intValue);
    }


    @Override
    public Value add(Value value) {
        if (value instanceof IntHolder){
            this.intValue += ((IntHolder) value).getValue();
        }
        return this;
    }

    @Override
    public Value sub(Value value) {
        if (value instanceof IntHolder){
            this.intValue -= ((IntHolder) value).getValue();
        }
        return this;

    }

    @Override
    public Value mul(Value value) {
        if (value instanceof IntHolder){
            this.intValue *= ((IntHolder) value).getValue();
        }
        return this;

    }

    @Override
    public Value div(Value value) {
        if (value instanceof IntHolder){
            this.intValue /= ((IntHolder) value).getValue();
        }
        return this;

    }

    @Override
    public Value pow(Value value) {
        if (value instanceof IntHolder){
            this.intValue = (int)Math.pow((double)this.intValue,(double)((IntHolder) value).getValue());
        }
        return this;

    }

    @Override
    public boolean eq(Value value) {
        if (value instanceof IntHolder){
            return Objects.equals(this.intValue, ((IntHolder) value).getValue());
        }
        return false;
    }

    @Override
    public boolean lte(Value value) {
        if (value instanceof IntHolder) {
            return this.intValue < ((IntHolder) value).getValue();
        }
        return false;
    }

    @Override
    public boolean gte(Value value) {
        if (value instanceof IntHolder) {
            return this.intValue > ((IntHolder) value).getValue();
        }
        return false;
    }

    @Override
    public boolean neq(Value value) {
        if (value instanceof IntHolder){
            return !Objects.equals(this.intValue, ((IntHolder) value).getValue());
        }
        return false;
    }

    public IntHolder create(String s){
        intValue = Integer.parseInt(s);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntHolder)) return false;
        IntHolder intHolder = (IntHolder) o;
        return intValue == intHolder.intValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(intValue);
    }
}
