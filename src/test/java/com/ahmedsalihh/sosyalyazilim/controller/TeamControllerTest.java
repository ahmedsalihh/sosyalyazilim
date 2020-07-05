package com.ahmedsalihh.sosyalyazilim.controller;

import com.ahmedsalihh.sosyalyazilim.models.Team;
import com.ahmedsalihh.sosyalyazilim.services.TeamService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
public class TeamControllerTest {
    @Autowired
    private TeamController teamController;

    @MockBean
    private TeamService teamService;

    private MockMvc mockMvc;
    Team team1;
    Team team2;

    @Before
    public void setUp() throws Exception {
        team1 = new Team();
        team1.setId(1L);
        team1.setName("test");
        team2 = new Team();
        team2.setId(2L);
        team2.setName("test edit");
        this.mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }

    @Test
    public void findAll() throws Exception {
        Mockito.when(teamService.findAll()).thenReturn(Lists.newArrayList(team1, team2));

        mockMvc.perform(get("/team").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void create() throws Exception {
        ObjectMapper obj = new ObjectMapper();
        String json = "";
        try {
            json = obj.writeValueAsString(team1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.when(teamService.save(Mockito.any(Team .class))).thenAnswer(i -> team1);

        mockMvc.perform(MockMvcRequestBuilders.post("/team").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(teamService.findById(1L)).thenReturn(Optional.of(team1));
        mockMvc.perform(get("/team/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    public void update() throws Exception {
        ObjectMapper obj = new ObjectMapper();
        String json = "";
        try {
            json = obj.writeValueAsString(team2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mockito.when(teamService.save(Mockito.any(Team.class))).thenAnswer(i -> team2);
        mockMvc.perform(put("/team/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("test edit")));
    }

    @Test
    public void deleteTest() throws Exception {
        Mockito.doNothing().when(teamService).deleteById(1L);
        mockMvc.perform(delete("/team/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}