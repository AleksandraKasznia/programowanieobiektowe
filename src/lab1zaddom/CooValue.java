package lab1zaddom;

public class CooValue extends Value{
    public int index;
    public Value content;

    CooValue(int inputIndex, Value inputContent){
        index = inputIndex;
        content = inputContent;
    }

    @Override
    public String toString() {
        return  content.toString();
    }

    @Override
    public Value add(Value value) {
        content = content.add(value);
        return this;
    }

    @Override
    public Value sub(Value value) {
        content = content.sub(value);
        return this;
    }

    @Override
    public Value mul(Value value) {
        content = content.mul(value);
        return this;
    }

    @Override
    public Value div(Value value) {
        content = content.div(value);
        return this;
    }

    @Override
    public Value pow(Value value) {
        content = content.pow(value);
        return this;
    }

    @Override
    public boolean eq(Value value) {
        return content.eq(value);
    }

    @Override
    public boolean lte(Value value) {
        return content.lte(value);
    }

    @Override
    public boolean gte(Value value) {
        return content.gte(value);
    }

    @Override
    public boolean neq(Value value) {
        return content.neq(value);
    }

    @Override
    public boolean equals(Object other) {
        return content.equals(other);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public CooValue create(String s) {
        return new CooValue(index,content.create(s));
    }
}
