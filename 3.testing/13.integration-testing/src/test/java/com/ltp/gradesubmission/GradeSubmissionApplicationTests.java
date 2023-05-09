package com.ltp.gradesubmission;

import com.ltp.gradesubmission.controller.GradeController;
import com.ltp.gradesubmission.pojo.Grade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*@SpringBootTest --> loads the application context for application testing
*
* @AutoConfigureMockMvc --> this will register the MockMvc bean inside the container, and so we
* 		can autowire it
* */
@SpringBootTest
@AutoConfigureMockMvc
class GradeSubmissionApplicationTests {

	/* NOTE --> THE CONTROLLELR IS NOT NEEDED*/
//	@Autowired
//	private GradeController gradeController;

	@Autowired
	private MockMvc mockMvc;

	/*contextLoads() --> it is a life-cycle method. This runs only after spring context loads with beans,
	* and after the grade controller is injected with @autowired
	*
	* contextLoads() -->
	*/
	@Test
	void contextLoads() {
		/*check if beans were actually injected after the context was created*/
//		assertNotNull(gradeController); //the controller is not needed
		assertNotNull(mockMvc);
	}

	/* here we test that the "gradeForm" handler method in the controller handles the request correctly*/
	@Test
	public void testShowGradeForm() throws Exception {
		/* build the GET mock request to the empty path, by using a request builder */
		RequestBuilder request =
				MockMvcRequestBuilders.get("/?id=123");

		/* perform the mocked request and after making the request
			- I expect the status to be successful
			- I expect "form" is the view to be returned
			- I expect the model to have an attribute called "grade"
		*/
		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("form"))
				.andExpect(model().attributeExists("grade"));
	}

	/* test that the "submitForm" handler method with VALID data*/
	@Test
	public void testShowHandSubmitForm_validDataCase() throws Exception {

		/*this request contains POST data as kay/value pairs. When the handler reis adtivated,
		an empty Grade object is created and filled with those data, and then this object is passed
		to the handler where the data is valdiated and handled
		 */
		RequestBuilder request =
				MockMvcRequestBuilders
						.post("/handleSubmit")
							.param("name", "Orgher")
							.param("subject", "Economia")
							.param("score", "B-");

		mockMvc.perform(request)
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/grades"));
	}

	/* test that the "submitForm" handler method with NON-valid data*/
	@Test
	public void testShowHandSubmitForm_notValidDataCase() throws Exception {

		RequestBuilder request =
				MockMvcRequestBuilders
						.post("/handleSubmit")
						.param("name", "") // NOT VALID!
						.param("subject", "Economia")
						.param("score", "B-");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("form"));
	}

	/* test getGrades() handler method */
	@Test
	public void testShowGetGrades() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/grades");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("grades"))
				.andExpect(model().attributeExists("grades"));
	}

}
