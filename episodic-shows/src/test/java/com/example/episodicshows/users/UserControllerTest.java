package com.example.episodicshows.users;

import com.example.episodicshows.shows.Episode;
import com.example.episodicshows.shows.EpisodeRepository;
import com.example.episodicshows.shows.Show;
import com.example.episodicshows.shows.ShowRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Rudyard Moreno on 5/17/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure=false)
public class UserControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    EpisodeRepository episodeRepository;

    @Autowired
    ViewingRepository viewingRepository;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

    @Test
    @Transactional
    @Rollback
    public void testGetUsers1() throws Exception{
        User user = new User();

        user.setEmail("joe@example.com");
        this.userRepository.save(user);

        MockHttpServletRequestBuilder request = get("/users")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", instanceOf(Number.class)))
                .andExpect(jsonPath("$[0].email", is("joe@example.com")))
                .andExpect(jsonPath("$[0].password").doesNotExist())
        ;
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateUsers1() throws Exception{
        /*User user = new User();

        user.setEmail("joe@example.com");*/

        Map<String,Object> payload = new HashMap<String,Object>() {
            {
                put("email","joe@example.com");
            }
        };
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);

        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                //.content(gson.toJson(user));
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.email", is("joe@example.com")))
                .andExpect(jsonPath("$.password").doesNotExist())
        ;
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateViewing1() throws Exception{
        User user = new User();
        Show show = new Show();
        Episode episode = new Episode();
        Viewing viewing=new Viewing();

        String jsonRequest;

        user.setEmail("joe@example.com");
        this.userRepository.save(user);

        show.setName("Friends");
        this.showRepository.save(show);

        episode.setShowId(show.getId());
        episode.setSeasonNumber(1);
        episode.setEpisodeNumber(2);
        this.episodeRepository.save(episode);

        viewing.setEpisodeId(episode.getId());
        viewing.setUpdatedAt(new Date());
        viewing.setTimecode(79);

        jsonRequest=gson.toJson(viewing);

        MockHttpServletRequestBuilder request = patch("/users/" + user.getId() + "/viewings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        this.mvc.perform(request)
                .andExpect(status().isOk())
        ;
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateViewing2() throws Exception{
        User user = new User();
        Show show = new Show();
        Episode episode = new Episode();
        Viewing viewing1=new Viewing();
        Viewing viewing2=new Viewing();

        String jsonRequest;

        user.setEmail("joe@example.com");
        this.userRepository.save(user);

        show.setName("Friends");
        this.showRepository.save(show);

        episode.setShowId(show.getId());
        episode.setSeasonNumber(1);
        episode.setEpisodeNumber(2);
        this.episodeRepository.save(episode);

        viewing1.setUserId(user.getId());
        viewing1.setShowId(show.getId());
        viewing1.setEpisodeId(episode.getId());
        viewing1.setUpdatedAt(new Date());
        viewing1.setTimecode(17);
        this.viewingRepository.save(viewing1);

        viewing2.setEpisodeId(episode.getId());
        viewing2.setUpdatedAt(new Date());
        viewing2.setTimecode(21);

        jsonRequest=gson.toJson(viewing2);

        MockHttpServletRequestBuilder request = patch("/users/" + user.getId() + "/viewings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }

    @Test
    @Transactional
    @Rollback
    public void testRecentlyWatched1() throws Exception{
        User user = new User();
        Show show = new Show();
        Episode episode = new Episode();
        Viewing viewing=new Viewing();

        user.setEmail("joe@example.com");
        this.userRepository.save(user);

        show.setName("Friends");
        this.showRepository.save(show);

        episode.setShowId(show.getId());
        episode.setSeasonNumber(1);
        episode.setEpisodeNumber(2);
        this.episodeRepository.save(episode);

        viewing.setEpisodeId(episode.getId());
        viewing.setShowId(show.getId());
        viewing.setUserId(user.getId());
        viewing.setUpdatedAt(new Date());
        viewing.setTimecode(79);
        this.viewingRepository.save(viewing);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        MockHttpServletRequestBuilder request = get("/users/" + user.getId() + "/recently-watched")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].show.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$[0].show.name", is("Friends")))
                .andExpect(jsonPath("$[0].episode.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$[0].episode.seasonNumber", is(1)))
                .andExpect(jsonPath("$[0].episode.episodeNumber", is(2)))
                .andExpect(jsonPath("$[0].episode.title", is("S1 E2")))
                .andExpect(jsonPath("$[0].updatedAt", is(dateFormat.format(viewing.getUpdatedAt())+"+0000")))
                .andExpect(jsonPath("$[0].timecode", is(79)))
        ;
    }
}
