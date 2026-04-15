package com.example.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class MainTests {
    @Autowired
    private MockMvc mvc;

    @Test
    public void helloUnauthenticated() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "john")
    public void helloAuthenticated() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(content().string("Hello, World!"))
                .andExpect(status().isOk());
    }
    @Test
    public void hello() throws Exception {
        mvc.perform(get("/hello")
                .with(user("john").roles("USER")))
                .andExpect(status().isOk());
        mvc.perform(get("/hello"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithUserDetails("john")
    public void helloUser() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }
}
