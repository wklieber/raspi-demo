package wklieber.raspi.tools;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Created by wklieber on 25.12.2016.
 */
public class MiscTools {
    /**
  *
     */
    public static String formatNumber(double number, Integer minimumIntegerDigits) {
        String returnValue = "NA";

       java.text.NumberFormat df = DecimalFormat.getInstance(Locale.GERMANY);
        if (minimumIntegerDigits != null) {
            df.setMinimumIntegerDigits(minimumIntegerDigits);
        }

        returnValue = df.format(number);

        return returnValue;
    }
}
