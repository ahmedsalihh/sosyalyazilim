package com.ahmedsalihh.sosyalyazilim.controller;

import com.ahmedsalihh.sosyalyazilim.models.Player;
import com.ahmedsalihh.sosyalyazilim.services.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerControllerTest {
    @Autowired
    private PlayerController playerController;

    @MockBean
    private PlayerService playerService;

    private MockMvc mockMvc;
    Player player1;
    Player player2;

    @Before
    public void setUp() throws Exception {
        player1 = new Player();
        player1.setId(1L);
        player1.setName("test");
        player2 = new Player();
        player2.setId(2L);
        player2.setName("test edit");
        this.mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    public void findAll() throws Exception {
        Mockito.when(playerService.findAll()).thenReturn(Lists.newArrayList(player1, player2));

        mockMvc.perform(get("/player").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void create() throws Exception {
        ObjectMapper obj = new ObjectMapper();
        String json = "";
        try {
            json = obj.writeValueAsString(player1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.when(playerService.save(Mockito.any(Player.class))).thenAnswer(i -> player1);

        mockMvc.perform(post("/player").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(playerService.findById(1L)).thenReturn(Optional.of(player1));
        mockMvc.perform(get("/player/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    public void update() throws Exception {
        ObjectMapper obj = new ObjectMapper();
        String json = "";
        try {
            json = obj.writeValueAsString(player2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(playerService.save(Mockito.any(Player.class))).thenAnswer(i -> player2);
        mockMvc.perform(put("/player/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("test edit")));
    }

    @Test
    public void deleteTest() throws Exception {
        Mockito.doNothing().when(playerService).deleteById(1L);
        mockMvc.perform(delete("/player/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}