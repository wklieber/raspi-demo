/*
 *     Raspberry PI sample code
 *     Copyright (C) 2016 -  2016  Werner Klieber
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
