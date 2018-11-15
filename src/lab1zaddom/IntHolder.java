package lab1zaddom;

import java.util.Objects;

public class IntHolder extends Value {
    Integer intValue;

    public IntHolder (int x){
        intValue = x;
    }

    IntHolder(){}


    public Integer getValue() {
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
        if (value instanceof DoubleHolder){
            this.intValue = (int)(intValue + ((DoubleHolder) value).getValue());
        }
        if (value instanceof FloatHolder){
            this.intValue = (int)(intValue + ((FloatHolder) value).getValue());
        }
        return this;
    }

    @Override
    public Value sub(Value value) {
        if (value instanceof IntHolder){
            this.intValue -= ((IntHolder) value).getValue();
        }
        if (value instanceof DoubleHolder){
            this.intValue = (int)(intValue - ((DoubleHolder) value).getValue());
        }
        if (value instanceof FloatHolder){
            this.intValue = (int)(intValue - ((FloatHolder) value).getValue());
        }
        return this;

    }

    @Override
    public Value mul(Value value) {
        if (value instanceof IntHolder){
            this.intValue *= ((IntHolder) value).getValue();
        }
        if (value instanceof DoubleHolder){
            this.intValue = (int)(intValue * ((DoubleHolder) value).getValue());
        }
        if (value instanceof FloatHolder){
            this.intValue = (int)(intValue * ((FloatHolder) value).getValue());
        }
        return this;

    }

    @Override
    public Value div(Value value) throws CustomException{
        if (value instanceof IntHolder){
            if(((IntHolder) value).getValue() == 0) throw new CustomException("You can't divide by 0");
            this.intValue /= ((IntHolder) value).getValue();
        }
        if (value instanceof DoubleHolder){
            if(((DoubleHolder) value).getValue() == 0) throw new CustomException("You can't divide by 0");
            this.intValue = (int)(intValue / ((DoubleHolder) value).getValue());
        }
        if (value instanceof FloatHolder){
            if(((FloatHolder) value).getValue() == 0) throw new CustomException("You can't divide by 0");
            this.intValue = (int)(intValue / ((FloatHolder) value).getValue());
        }
        return this;

    }

    @Override
    public Value pow(Value value) {
        if (value instanceof IntHolder){
            this.intValue = (int)Math.pow((double)this.intValue,(double)((IntHolder) value).getValue());
        }
        if (value instanceof DoubleHolder){
            this.intValue = (int)Math.pow((double)this.intValue,((DoubleHolder) value).getValue());
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
        if (value instanceof DoubleHolder){
            return this.intValue < ((DoubleHolder)value).getValue();
        }
        if (value instanceof FloatHolder){
            return this.intValue < ((FloatHolder)value).getValue();
        }
        return false;
    }

    @Override
    public boolean gte(Value value) {
        if (value instanceof IntHolder) {
            return this.intValue > ((IntHolder) value).getValue();
        }
        if (value instanceof DoubleHolder){
            return this.intValue > ((DoubleHolder)value).getValue();
        }
        if (value instanceof FloatHolder){
            return this.intValue > ((FloatHolder)value).getValue();
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

    @Override
    public int compareTo(Value o) {
        return intValue.compareTo(((IntHolder)o).getValue());
    }
}
