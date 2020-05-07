package q006.value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

public class DivisionValue implements IValue {
    @Override
    public void execute(Stack<BigDecimal> values) {
        BigDecimal right = values.pop();
        BigDecimal left = values.pop();
        values.push(left.divide(right, 2, RoundingMode.HALF_UP));
    }
}
