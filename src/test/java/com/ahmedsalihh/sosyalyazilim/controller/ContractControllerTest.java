package com.ahmedsalihh.sosyalyazilim.controller;

import com.ahmedsalihh.sosyalyazilim.models.Contract;
import com.ahmedsalihh.sosyalyazilim.models.Player;
import com.ahmedsalihh.sosyalyazilim.models.Team;
import com.ahmedsalihh.sosyalyazilim.services.ContractService;
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
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractControllerTest {

    @Autowired
    private ContractController contractController;

    @MockBean
    private ContractService contractService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(contractController).build();
    }

    @Test
    public void findAll() throws Exception {

        Contract contract1 = new Contract();
        contract1.setId(1L);
        contract1.setStartDate(new Date(01 - 01 - 2018));
        contract1.setEndDate(new Date(01 - 01 - 2022));
        contract1.setContractFee("1000 dollar");

        Contract contract2 = new Contract();
        contract2.setId(2L);
        contract2.setStartDate(new Date(01 - 01 - 2018));
        contract2.setEndDate(new Date(01 - 01 - 2022));
        contract2.setContractFee("2000 dollar");

        Mockito.when(contractService.findAll()).thenReturn(Lists.newArrayList(contract1, contract2));

        mockMvc.perform(get("/contract").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void create() throws Exception {
        Player player1 = new Player();
        player1.setId(1L);
        Team team1 = new Team();
        team1.setId(1L);
        Contract contract1 = new Contract();
        contract1.setId(1L);
        contract1.setStartDate(new Date(01 - 01 - 2018));
        contract1.setEndDate(new Date(01 - 01 - 2022));
        contract1.setContractFee("1000 dollar");
        contract1.setPlayer(player1);
        contract1.setTeam(team1);

        Contract contractData = new Contract();
        contractData.setStartDate(new Date(01 - 01 - 2018));
        contractData.setEndDate(new Date(01 - 01 - 2023));

        ObjectMapper obj = new ObjectMapper();
        String json = "";
        try {
            json = obj.writeValueAsString(contractData);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Mockito.when(contractService.save(Mockito.any(Contract.class), Mockito.any(Long.class), Mockito.any(Long.class))).thenAnswer(i -> contract1);

        mockMvc.perform(post("/contract?playerId=1&teamId=1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("player.id", is(1)))
                .andExpect(jsonPath("team.id", is(1)));
        ;
    }

    @Test
    public void getActiveTeamPlayers() throws Exception {
        Player player1 = new Player();
        player1.setId(1L);
        Mockito.when(contractService.getActiveTeamPlayers(2020, 1L)).thenReturn(Lists.newArrayList(player1));

        mockMvc.perform(get("/contract/getActiveTeamPlayers?year=2020&teamId=1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getPlayerTeams() throws Exception {
        Team team1 = new Team();
        team1.setId(1L);
        Mockito.when(contractService.getPlayerTeams(1L)).thenReturn(Lists.newArrayList(team1));

        mockMvc.perform(get("/contract/getPlayerTeams?playerId=1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}