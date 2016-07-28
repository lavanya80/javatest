package com.disney.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.disney.config.EventsAppApplication;
import com.disney.domain.Event;
import com.disney.domain.User;
import com.disney.repositories.EventRepository;
import com.disney.repositories.UserRepository;
import com.disney.service.EventService;
import com.disney.service.UserService;

@SpringApplicationConfiguration(classes = EventsAppApplication.class)
@WebAppConfiguration
@TestExecutionListeners(inheritListeners = false, listeners = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
public class RestControllerTest extends AbstractTestNGSpringContextTests {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	private Event event;
	private User user;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
		Assert.assertNotNull(this.mappingJackson2HttpMessageConverter, "the JSON message converter must not be null");
	}

	@BeforeClass
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.eventRepository.deleteAllInBatch();
		this.userRepository.deleteAllInBatch();
		LocalTime startTime = LocalTime.now().plusHours(2);
		event = new Event("Event 1", LocalDate.now(), startTime, startTime.plusMinutes(30));
		user = new User("jmari", "John", null, "Marini");
	}

	@Test(priority = 1)
	public void createEvent() throws Exception {
		String eventJson = json(event);
		this.mockMvc.perform(post("/event").contentType(contentType).content(eventJson))
				.andExpect(status().isCreated());
	}

	@Test(priority = 2)
	public void getEvent() throws Exception {
		String eventJson = json(eventService.findOne(1l));
		this.mockMvc.perform(get("/event/1").accept(contentType).content(eventJson)).andExpect(status().isOk());
	}

	@Test(priority = 3)
	public void getAllEvents() throws Exception {
		String allEventsJson = json(eventService.findAll());
		this.mockMvc.perform(get("/event").accept(contentType).content(allEventsJson)).andExpect(status().isOk());
	}

	@Test(priority = 4)
	public void createUser() throws Exception {
		String user1Json = json(user);
		this.mockMvc.perform(post("/user").contentType(contentType).content(user1Json)).andExpect(status().isCreated());
	}

	@Test(priority = 5)
	public void getUser() throws Exception {
		String userJson = json(userService.findOne(1l));
		this.mockMvc.perform(get("/user/1").accept(contentType).content(userJson)).andExpect(status().isOk());
	}

	@Test(priority = 6)
	public void getAllUsers() throws Exception {
		String usersJson = json(userService.findAll());
		this.mockMvc.perform(get("/user").accept(contentType).content(usersJson)).andExpect(status().isOk());
	}

	@Test(priority = 7)
	@Transactional
	public void getAllEventsForUser() throws Exception {
		String eventsJson = json(userService.findOne(1l).getEvents());
		this.mockMvc.perform(get("/user/1/events").accept(contentType).content(eventsJson)).andExpect(status().isOk());
	}

	@Test(priority = 8)
	public void addUserToAnEvent() throws Exception {
		this.mockMvc.perform(put("/event/1/users/1").contentType(contentType)).andExpect(status().isOk());
	}

	@Test(priority = 9)
	@Transactional
	public void getUsersForAnEvent() throws Exception {
		String usersJson = json(eventService.findOne(1l).getUsers());
		this.mockMvc.perform(get("/event/1/users").accept(contentType).content(usersJson)).andExpect(status().isOk());
	}

	@Test(priority = 10)
	public void removeUserFromAnEvent() throws Exception {
		this.mockMvc.perform(delete("/event/1/users/1").contentType(contentType)).andExpect(status().isOk());
	}

	@Test(priority = 11)
	public void updateUser() throws Exception {
		User user = userService.findOne(1l);
		user.setUserName("jmarini");
		String userJson = json(user);
		this.mockMvc.perform(put("/user").contentType(contentType).content(userJson)).andExpect(status().isOk());
	}

	@Test(priority = 12)
	public void updateEvent() throws Exception {
		Event event = eventService.findOne(1l);
		event.setName("Event 1a");
		String eventJson = json(event);
		this.mockMvc.perform(put("/event").contentType(contentType).content(eventJson)).andExpect(status().isOk());
	}
	
	@Test(priority = 13)
	public void userDoesntexist() throws Exception {
		this.mockMvc.perform(get("/user/2").accept(contentType)).andExpect(status().isNotFound());
	}

	@Test(priority = 14)
	public void eventDoesntexist() throws Exception {
		this.mockMvc.perform(get("/event/2").accept(contentType)).andExpect(status().isNotFound());
	}

	@Test(priority = 15)
	public void deleteUser() throws Exception {
		this.mockMvc.perform(delete("/user/1")).andExpect(status().isOk());
	}

	@Test(priority = 16)
	public void deleteEvent() throws Exception {
		this.mockMvc.perform(delete("/event/1")).andExpect(status().isOk());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
