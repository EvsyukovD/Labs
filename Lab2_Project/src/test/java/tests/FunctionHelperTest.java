package tests;

import utils.FunctionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FunctionHelperTest {
    String l;
    String i;
    String f;

    @BeforeEach
    void setUp() {
        l = Long.toString(Long.MAX_VALUE);
        i = Integer.toString(Integer.MAX_VALUE);
        f = Float.toString(0.5f);
    }

    @Test
    void isLong() {
        Assertions.assertTrue(FunctionHelper.isLong(l), "Правда");
        Assertions.assertFalse(FunctionHelper.isLong(f), "Ложь");
        Assertions.assertTrue(FunctionHelper.isLong(i), "Правда");
    }

    @Test
    void isInt() {
        Assertions.assertFalse(FunctionHelper.isInt(l), "Ложь");
        Assertions.assertTrue(FunctionHelper.isInt(i), "Правда");
        Assertions.assertFalse(FunctionHelper.isInt(f), "Ложь");
    }
}