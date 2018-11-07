package lab1zaddom;

public interface GroupBy {
    DataFrame max();
    DataFrame min();
    DataFrame mean();
    DataFrame std();
    DataFrame sum();
    DataFrame var();
    DataFrame apply(Applyable a);
}
