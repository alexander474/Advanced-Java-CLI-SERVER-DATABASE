package no.kristiania.pgr200.common;
import no.kristiania.pgr200.common.DateHandler;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DateHandlerTest {


    @Test
    public void testValidDate(){
        String validDate = "25-06-2009";
        assertThat(LocalDate.parse(validDate, DateHandler.dateTimeFormatter).toString()).isEqualTo("2009-06-25");
    }

    @Test
    public void testInvalidDate(){
        String invalidDate = "40402005";
        assertThatThrownBy(() -> {
            LocalDate.parse(invalidDate, DateHandler.dateTimeFormatter);
        }).isInstanceOf(DateTimeParseException.class)
        .hasMessageContaining("");
    }

    @Test
    public void testInvalidTime(){
        String invalidDate = "40402005";
        assertThatThrownBy(() -> {
            LocalDate.parse(invalidDate, DateHandler.TimeFormatter);
        }).isInstanceOf(DateTimeParseException.class)
                .hasMessageContaining("");
    }

    @Test
    public void testDateToEpoch(){
        String[] dates = {"25-06-2009", "06-06-2006", "25-10-2018", "16-02-2019"};
        assertThat(DateHandler.convertDateToEpoch(dates[0])).isEqualTo(1245888000000L);
        assertThat(DateHandler.convertDateToEpoch(dates[1])).isEqualTo(1149552000000L);
        assertThat(DateHandler.convertDateToEpoch(dates[2])).isEqualTo(1540425600000L);
        assertThat(DateHandler.convertDateToEpoch(dates[3])).isEqualTo(1550275200000L);
    }

}
