package com.ltp.contacts;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltp.contacts.pojo.Contact;
import com.ltp.contacts.repository.ContactRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.attribute.standard.Media;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContactsApplicationTests {

	@Autowired
    private MockMvc mockMvc;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private Contact[] contacts = new Contact[] {
		new Contact("1", "Jon Snow", "6135342524"),
		new Contact("2", "Tyrion Lannister", "4145433332"),
		new Contact("3", "The Hound", "3452125631"),
	};

	/* this will run BEFORE each test is executed, to populate the repository with data*/
	@BeforeEach
    void setup(){
		for (int i = 0; i < contacts.length; i++) {
			contactRepository.saveContact(contacts[i]);
		}
	}

	/* this will run AFTER each test is executed, tp clear the repository*/
	@AfterEach
	void clear(){
		contactRepository.getContacts().clear();
    }


	@Test
	public void getContactByIdTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/contact/1");

		/* because a Contact with id=2 exists in the repository, response would be successful*/
		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Jon Snow"))
				.andExpect(jsonPath("$.phoneNumber").value("6135342524"));

	}

	@Test
	public void contactNotFoundTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/contact/99");

		/* because a Contact with id=99 does not exist in the repository, response would be 404*/
		mockMvc.perform(request)
				.andExpect(status().isNotFound())
				//check that the content is JSON data
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void getAllContactsTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/contact/all");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//check the returned JSON array size has correct size
				.andExpect(jsonPath("$.size()").value(3))
				//check for the existance of a JSON socument (object) in the array with the following values
				.andExpect(jsonPath("$.[?(@.phoneNumber == \"6135342524\" && @.name == \"Jon Snow\")]").exists());
	}

	@Test
	public void validContactCreation() throws Exception {
		//create a request that contains JSON data representing a Contact object to be stored in the repository
		Contact contactObj = new Contact("Orgher", "1236");
		String contactData = objectMapper.writeValueAsString(contactObj); //stringify

		RequestBuilder request =
				MockMvcRequestBuilders.post("/contact")
						.contentType(MediaType.APPLICATION_JSON)
						.content(contactData);

		//send the request
		mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Orgher"))
				.andExpect(jsonPath("$.phoneNumber").value("1236"));
	}

}
