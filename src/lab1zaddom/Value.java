package lab1zaddom;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public abstract class Value implements Cloneable,Comparable<Value>{
    public abstract String toString();
    public abstract Value add(Value value);
    public abstract Value sub(Value value);
    public abstract Value mul(Value value);
    public abstract Value div(Value value)throws CustomException;
    public abstract Value pow(Value value);
    public abstract boolean eq(Value value);
    public abstract boolean lte(Value value);
    public abstract boolean gte(Value value);
    public abstract boolean neq(Value value);
    public abstract boolean equals(Object other);
    public abstract int hashCode();
    public abstract Value create(String s) throws CustomException;
    public abstract Object getValue();

    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }

    public static ValueBuilder builder(Class<? extends Value> type){
        return new ValueBuilder(type);
    }

    @Override
    public abstract int compareTo(Value o);

    public static class ValueBuilder{
        Class<? extends Value> classOfValue;

        public ValueBuilder(Class<? extends Value> classToBuild){
            classOfValue = classToBuild;
        }

        public Value build(String content) throws InstantiationException, IllegalAccessException,
                InvocationTargetException, NoSuchMethodException, CustomException{
                return (Value) classOfValue.getMethod("create", String.class).invoke(classOfValue.newInstance(), content);
        }
    }
}
