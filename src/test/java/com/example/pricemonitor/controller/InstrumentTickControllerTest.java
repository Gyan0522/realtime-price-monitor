package com.example.pricemonitor.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class InstrumentTickControllerTest {

	private MockMvc mockMvc;

	@Mock
	private InstrumentTickController tickService;

	// @InjectMocks
	// private HelloResource helloResource;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(tickService).build();
	}

	@Test
	public void testTickStatistics() throws Exception {

		when(tickService.getallStatistics()).thenReturn("avg");

		mockMvc.perform(get("/statistics")).andExpect(status().isOk()).andExpect(content().string("avg"));

		verify(tickService).getallStatistics();
	}

	/*
	 * @Test public void testStatisticsforInstrument() throws Exception {
	 * 
	 * String str = null;
	 * when(tickService.getStatisticsforInstrument(str)).thenReturn("avg");
	 * 
	 * mockMvc.perform(get("/statistics/instrument_identifier"))
	 * .andExpect(status().isOk()) .andExpect(content().string("500"));
	 * 
	 * verify(tickService).getStatisticsforInstrument(str); }
	 */

}