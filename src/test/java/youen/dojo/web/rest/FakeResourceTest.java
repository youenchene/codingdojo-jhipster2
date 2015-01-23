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
import youen.dojo.Application;
import youen.dojo.domain.Fake;
import youen.dojo.repository.mongo.FakeRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FakeResource REST controller.
 *
 * @see FakeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FakeResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";


    @Inject
    private FakeRepository fakeRepository;

    private MockMvc restFakeMockMvc;

    private Fake fake;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FakeResource fakeResource = new FakeResource();
        ReflectionTestUtils.setField(fakeResource, "fakeRepository", fakeRepository);
        this.restFakeMockMvc = MockMvcBuilders.standaloneSetup(fakeResource).build();
    }

    @Before
    public void initTest() {
        fakeRepository.deleteAll();
        fake = new Fake();
        fake.setName(DEFAULT_NAME);
    }

    @Test
    public void createFake() throws Exception {
        // Validate the database is empty
        assertThat(fakeRepository.findAll()).hasSize(0);

        // Create the Fake
        restFakeMockMvc.perform(post("/app/rest/fakes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fake)))
                .andExpect(status().isOk());

        // Validate the Fake in the database
        List<Fake> fakes = fakeRepository.findAll();
        assertThat(fakes).hasSize(1);
        Fake testFake = fakes.iterator().next();
        assertThat(testFake.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void getAllFakes() throws Exception {
        // Initialize the database
        fakeRepository.save(fake);

        // Get all the fakes
        restFakeMockMvc.perform(get("/app/rest/fakes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(fake.getId()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getFake() throws Exception {
        // Initialize the database
        fakeRepository.save(fake);

        // Get the fake
        restFakeMockMvc.perform(get("/app/rest/fakes/{id}", fake.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fake.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingFake() throws Exception {
        // Get the fake
        restFakeMockMvc.perform(get("/app/rest/fakes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFake() throws Exception {
        // Initialize the database
        fakeRepository.save(fake);

        // Update the fake
        fake.setName(UPDATED_NAME);
        restFakeMockMvc.perform(post("/app/rest/fakes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fake)))
                .andExpect(status().isOk());

        // Validate the Fake in the database
        List<Fake> fakes = fakeRepository.findAll();
        assertThat(fakes).hasSize(1);
        Fake testFake = fakes.iterator().next();
        assertThat(testFake.getName()).isEqualTo(UPDATED_NAME);
    }

//    @Test
//    public void deleteFake() throws Exception {
//        // Initialize the database
//        fakeRepository.save(fake);
//
//        // Get the fake
//        restFakeMockMvc.perform(delete("/app/rest/fakes/{id}", fake.getId())
//                .accept(TestUtil.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<Fake> fakes = fakeRepository.findAll();
//        assertThat(fakes).hasSize(0);
//    }
}
