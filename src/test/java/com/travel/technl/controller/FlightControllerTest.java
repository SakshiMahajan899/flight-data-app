package com.travel.technl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.technl.model.Flight;
import com.travel.technl.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FlightControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    private Flight testFlight;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();

        testFlight = new Flight();
        testFlight.setId(1L);
        testFlight.setAirline("MockAir");
        testFlight.setDepartureAirport("FRA");
        testFlight.setDestinationAirport("JFK");
    }

    @Test
    void testCreateFlight() throws Exception {
        when(flightService.saveFlight(any(Flight.class))).thenReturn(testFlight);

        mockMvc.perform(post("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFlight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airline").value("MockAir"));

        verify(flightService, times(1)).saveFlight(any(Flight.class));
    }

    @Test
    void testUpdateFlight() throws Exception {
        when(flightService.updateFlight(eq(1L), any(Flight.class))).thenReturn(testFlight);

        mockMvc.perform(put("/api/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFlight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airline").value("MockAir"));

        verify(flightService, times(1)).updateFlight(eq(1L), any(Flight.class));
    }

    @Test
    void testDeleteFlight() throws Exception {
        doNothing().when(flightService).deleteFlight(1L);

        mockMvc.perform(delete("/api/flights/1"))
                .andExpect(status().isNoContent());

        verify(flightService, times(1)).deleteFlight(1L);
    }

    @Test
    void testSearchFlights() throws Exception {
        when(flightService.searchFlights("FRA", "JFK", "MockAir", "10:00", "18:00"))
                .thenReturn(List.of(testFlight));

        mockMvc.perform(get("/api/flights")
                        .param("from", "FRA")
                        .param("to", "JFK")
                        .param("airline", "MockAir")
                        .param("departureTime", "10:00")
                        .param("arrivalTime", "18:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].airline").value("MockAir"));

        verify(flightService, times(1)).searchFlights("FRA", "JFK", "MockAir", "10:00", "18:00");
    }

}

