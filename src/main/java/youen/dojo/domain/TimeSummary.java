package youen.dojo.domain;

import java.math.BigDecimal;
import org.joda.time.LocalDate;

/**
 * Created by youen on 25/01/2015.
 */
public class TimeSummary {


    public TimeSummary(LocalDate date, BigDecimal time) {
        this.date = date;
        this.time = time;
    }

    public LocalDate date;
    public BigDecimal time;
}
