package youen.dojo.service;

import org.assertj.core.groups.Tuple;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import youen.dojo.domain.Project;
import youen.dojo.domain.Time;
import youen.dojo.domain.TimeSummary;
import youen.dojo.domain.User;
import youen.dojo.repository.TimeRepository;
import youen.dojo.repository.WeatherRepository;
import youen.dojo.repository.data.Weather;
import youen.dojo.repository.data.WeatherDetail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeServiceTest {

    @Mock
    TimeRepository timeRepository;

    @InjectMocks
    TimeService timeService=new TimeService();

    @Test
    public void should_return_sunny() {
        //GIVEN
        List<Time> times=new ArrayList<>();
        {
            Time time=new Time();
            time.setDate(LocalDate.parse("2015-01-10"));
            time.setTime(new BigDecimal(0.5));
            time.setProject(new Project());
            time.getProject().setName("Project1");
            time.setUser(new User());
            time.getUser().setLogin("kevin");
            times.add(time);
        }
        {
            Time time=new Time();
            time.setDate(LocalDate.parse("2015-01-10"));
            time.setTime(new BigDecimal(0.5));
            time.setProject(new Project());
            time.getProject().setName("Project2");
            time.setUser(new User());
            time.getUser().setLogin("kevin");
            times.add(time);
        }
        {
            Time time=new Time();
            time.setDate(LocalDate.parse("2015-01-11"));
            time.setTime(new BigDecimal(0.5));
            time.setProject(new Project());
            time.getProject().setName("Project1");
            time.setUser(new User());
            time.getUser().setLogin("kevin");
            times.add(time);
        }
        when(timeRepository.findAllLoggerByUser(anyString())).thenReturn(times);
        //WHEN
        List<TimeSummary> timeSummary=timeService.getStatsByDay("kevin");
        //THEN
        assertThat(timeSummary).extracting("date","time").containsExactly(new Tuple(LocalDate.parse("2015-01-10"),new BigDecimal("1.0")),new Tuple(LocalDate.parse("2015-01-11"),new BigDecimal("0.5")));

    }
}
