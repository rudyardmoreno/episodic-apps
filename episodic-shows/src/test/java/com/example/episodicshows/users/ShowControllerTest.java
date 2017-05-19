package com.example.episodicshows.users;

import com.example.episodicshows.shows.Episode;
import com.example.episodicshows.shows.EpisodeRepository;
import com.example.episodicshows.shows.Show;
import com.example.episodicshows.shows.ShowRepository;
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

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class ShowControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    EpisodeRepository episodeRepository;

    private Gson gson = new GsonBuilder().create();

    @Test
    @Transactional
    @Rollback
    public void testGetShow1() throws Exception{
        Show show = new Show();

        show.setName("Friends");
        this.showRepository.save(show);

        MockHttpServletRequestBuilder request = get("/shows")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", instanceOf(Number.class)))
                .andExpect(jsonPath("$[0].name", is("Friends")))
        ;
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateShow1() throws Exception{
        Show show = new Show();

        show.setName("Friends");

        MockHttpServletRequestBuilder request = post("/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(show));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.name", is("Friends")))
        ;
    }

    @Test
    @Transactional
    @Rollback
    public void testGetEpisodes1() throws Exception{
        Show show = new Show();
        Episode episode = new Episode();

        show.setName("Friends");
        this.showRepository.save(show);

        episode.setShowId(show.getId());
        episode.setSeasonNumber(1);
        episode.setEpisodeNumber(2);
        this.episodeRepository.save(episode);

        MockHttpServletRequestBuilder request = get("/shows/" + show.getId() + "/episodes")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", instanceOf(Number.class)))
                .andExpect(jsonPath("$[0].showId").doesNotExist())
                .andExpect(jsonPath("$[0].seasonNumber", is(1)))
                .andExpect(jsonPath("$[0].episodeNumber", is(2)))
                .andExpect(jsonPath("$[0].title", is("S1 E2")))
        ;
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateEpisode1() throws Exception{
        Show show = new Show();
        Episode episode = new Episode();

        show.setName("Friends");
        this.showRepository.save(show);

        episode.setShowId(show.getId());
        episode.setSeasonNumber(1);
        episode.setEpisodeNumber(2);


        MockHttpServletRequestBuilder request = post("/shows/" + show.getId() + "/episodes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(episode));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class)))
                .andExpect(jsonPath("$.showId").doesNotExist())
                .andExpect(jsonPath("$.seasonNumber", is(1)))
                .andExpect(jsonPath("$.episodeNumber", is(2)))
                .andExpect(jsonPath("$.title", is("S1 E2")))
        ;
    }
}
