package com.example.episodicshows.users;

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
import java.util.HashMap;
import java.util.Map;

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
public class UsersControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository repository;

    private Gson gson = new GsonBuilder().create();

    @Test
    @Transactional
    @Rollback
    public void testGetUsers1() throws Exception{
        User user = new User();

        user.setEmail("joe@example.com");
        this.repository.save(user);

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
}
