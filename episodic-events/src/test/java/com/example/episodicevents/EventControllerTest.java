package com.example.episodicevents;

import com.example.episodicevents.events.dataTypes.DataDefault;
import com.example.episodicevents.events.dataTypes.ForwardData;
import com.example.episodicevents.events.dataTypes.ScrubData;
import com.example.episodicevents.events.repository.EventRepository;
import com.example.episodicevents.events.types.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class EventControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    EventRepository eventRepository;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

    @Before
    public void setupTest() {
        eventRepository.deleteAll();
    }

    @Test
    public void testGetEvents() throws Exception{
        Play play = new Play(52,987,456,new Date(),new DataDefault(0));
        Pause pause= new Pause(52,987,456,new Date(),new DataDefault(1023));
        FastForward fastForward = new FastForward(52,987,456,new Date(),new ForwardData(4,408,2.5));
        Rewind rewind = new Rewind(52,987,456,new Date(),new ForwardData(4,408,2.5));
        Progress progress = new Progress(52,987,456,new Date(),new DataDefault(4));
        Scrub scrub = new Scrub(52,987,456,new Date(),new ScrubData(4,408));

        eventRepository.save(play);
        eventRepository.save(pause);
        eventRepository.save(fastForward);
        eventRepository.save(rewind);
        eventRepository.save(progress);
        eventRepository.save(scrub);

        MockHttpServletRequestBuilder request = get("/recent")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].type", is("play")))
                .andDo(print())
        ;
    }

    @Test
    public void testCreatePlayEvent() throws Exception{
        Play event = new Play(52,987,456,new Date(),new DataDefault(0));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(event));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("play")))
                .andExpect(jsonPath("$.userId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.showId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.episodeId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.offset",instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.startOffset").doesNotExist())
                .andExpect(jsonPath("$.data.endOffset").doesNotExist())
                .andExpect(jsonPath("$.data.speed").doesNotExist())
                .andDo(print())
        ;
    }

    @Test
    public void testCreatePauseEvent() throws Exception{
        Pause event = new Pause(52,987,456,new Date(),new DataDefault(0));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(event));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("pause")))
                .andExpect(jsonPath("$.userId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.showId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.episodeId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.offset",instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.startOffset").doesNotExist())
                .andExpect(jsonPath("$.data.endOffset").doesNotExist())
                .andExpect(jsonPath("$.data.speed").doesNotExist())
                .andDo(print())
        ;
    }

    @Test
    public void testCreateFastForwardEvent() throws Exception{
        FastForward event = new FastForward(52,987,456,new Date(),new ForwardData(4,408,2.5));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(event));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("fastForward")))
                .andExpect(jsonPath("$.userId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.showId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.episodeId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.offset").doesNotExist())
                .andExpect(jsonPath("$.data.startOffset",instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.endOffset",instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.speed",instanceOf(Number.class)))
                .andDo(print())
        ;
    }

    @Test
    public void testCreateRewindEvent() throws Exception{
        Rewind event = new Rewind(52,987,456,new Date(),new ForwardData(4,408,2.5));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(event));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("rewind")))
                .andExpect(jsonPath("$.userId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.showId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.episodeId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.offset").doesNotExist())
                .andExpect(jsonPath("$.data.startOffset",instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.endOffset",instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.speed",instanceOf(Number.class)))
                .andDo(print())
        ;
    }

    @Test
    public void testCreateProgressEvent() throws Exception{
        Progress event = new Progress(52,987,456,new Date(),new DataDefault(0));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(event));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("progress")))
                .andExpect(jsonPath("$.userId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.showId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.episodeId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.offset",instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.startOffset").doesNotExist())
                .andExpect(jsonPath("$.data.endOffset").doesNotExist())
                .andExpect(jsonPath("$.data.speed").doesNotExist())
                .andDo(print())
        ;
    }

    @Test
    public void testCreateScrubEvent() throws Exception{
        Scrub event = new Scrub(52,987,456,new Date(),new ScrubData(4,408));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(event));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("scrub")))
                .andExpect(jsonPath("$.userId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.showId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.episodeId", instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.offset").doesNotExist())
                .andExpect(jsonPath("$.data.startOffset",instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.endOffset",instanceOf(Number.class)))
                .andExpect(jsonPath("$.data.speed").doesNotExist())
                .andDo(print())
        ;
    }
}
