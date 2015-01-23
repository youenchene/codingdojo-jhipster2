package youen.dojo.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

import youen.dojo.Application;
import youen.dojo.domain.Time;
import youen.dojo.repository.TimeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TimeResource REST controller.
 *
 * @see TimeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TimeResourceTest {

    private static final LocalDate DEFAULT_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE = new LocalDate();
    
    private static final BigDecimal DEFAULT_TIME = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_TIME = BigDecimal.ONE;
    

    @Inject
    private TimeRepository timeRepository;

    private MockMvc restTimeMockMvc;

    private Time time;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeResource timeResource = new TimeResource();
        ReflectionTestUtils.setField(timeResource, "timeRepository", timeRepository);
        this.restTimeMockMvc = MockMvcBuilders.standaloneSetup(timeResource).build();
    }

    @Before
    public void initTest() {
        time = new Time();
        time.setDate(DEFAULT_DATE);
        time.setTime(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createTime() throws Exception {
        // Validate the database is empty
        assertThat(timeRepository.findAll()).hasSize(0);

        // Create the Time
        restTimeMockMvc.perform(post("/app/rest/times")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(time)))
                .andExpect(status().isOk());

        // Validate the Time in the database
        List<Time> times = timeRepository.findAll();
        assertThat(times).hasSize(1);
        Time testTime = times.iterator().next();
        assertThat(testTime.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTime.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void getAllTimes() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        // Get all the times
        restTimeMockMvc.perform(get("/app/rest/times"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(time.getId().intValue()))
                .andExpect(jsonPath("$.[0].date").value(DEFAULT_DATE.toString()))
                .andExpect(jsonPath("$.[0].time").value(DEFAULT_TIME.intValue()));
    }

    @Test
    @Transactional
    public void getTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        // Get the time
        restTimeMockMvc.perform(get("/app/rest/times/{id}", time.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(time.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTime() throws Exception {
        // Get the time
        restTimeMockMvc.perform(get("/app/rest/times/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        // Update the time
        time.setDate(UPDATED_DATE);
        time.setTime(UPDATED_TIME);
        restTimeMockMvc.perform(post("/app/rest/times")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(time)))
                .andExpect(status().isOk());

        // Validate the Time in the database
        List<Time> times = timeRepository.findAll();
        assertThat(times).hasSize(1);
        Time testTime = times.iterator().next();
        assertThat(testTime.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTime.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void deleteTime() throws Exception {
        // Initialize the database
        timeRepository.saveAndFlush(time);

        // Get the time
        restTimeMockMvc.perform(delete("/app/rest/times/{id}", time.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Time> times = timeRepository.findAll();
        assertThat(times).hasSize(0);
    }
}
