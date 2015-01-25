package youen.dojo.service;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import youen.dojo.domain.Time;
import youen.dojo.domain.TimeSummary;
import youen.dojo.repository.TimeRepository;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

@Service
public class TimeService{

    @Inject
    TimeRepository timeRepository;

    public List<TimeSummary> getStatsByDay(String login) {
        List<Time> times=timeRepository.findAllLoggerByUser(login);


        final List<TimeSummary> collectedMap =
            times.stream()
                .collect(Collectors.groupingBy(
                    Time::getDate,
                    reducing(BigDecimal.ZERO,Time::getTime,BigDecimal::add)
                ))
                .entrySet().stream()
            .map( e -> new TimeSummary(e.getKey(),e.getValue()))
            .collect(toList());


        return collectedMap;
    }


}
