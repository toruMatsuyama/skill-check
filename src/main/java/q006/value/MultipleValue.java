package q006.value;

import java.math.BigDecimal;
import java.util.Stack;

public class MultipleValue implements IValue {
    @Override
    public void execute(Stack<BigDecimal> values) {
        BigDecimal right = values.pop();
        BigDecimal left = values.pop();
        values.push(left.multiply(right));
    }
}
