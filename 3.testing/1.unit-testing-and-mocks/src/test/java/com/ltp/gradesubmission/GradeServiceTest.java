package com.ltp.gradesubmission;

import com.ltp.gradesubmission.pojo.Grade;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.service.GradeService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/*@RunWith --> runs every unit test inside the test class by using the MockitoJUnitRunner
*
* @RunWith --> makes the GradeServiceTest class into a "Runner class"
*
* "Runner class" --> we call it a class that is able to run tests*/
@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {

    /*here we create a Mock of the GradeRepository, which mimics the behavior of
    * the GradeRepository dependendcy in the GradeService class, and this mock
    * does not have any logic on its own (it means for example it can not query any database, while
    * the actual bean repository that is crated when starting the application could do it)
    *
    * @Mock --> creates a mock object of GradeRepository
    *
    * @InjectMocks --> creates an object of the GradeService class we want to test, and injects the mock
    * object defined above into the GradeService object*/

    @Mock
    private GradeRepository gradeRepositoryMock;

    @InjectMocks
    private GradeService gradeService;

    /*UNIT TEST --> Check if GradeService.getGrade() is actually able to fetch the
    grades from the repository*/
    @Test
    public void getGradesFromRepoTest() {

        /* 1) ARRANGE --> mock the data needed to carry out the unit test*/

        /*
        * here we tell the mock what data to return:
        * ---> "WHEN the service object calls gradeRepository.getGrades(), THEN it
        *       should RETURN a LIST of GRADES"
        * */

        when(gradeRepositoryMock.getGrades())
                .thenReturn(Arrays.asList(
                        new Grade("Harry", "Potions", "C-", "1234"),
                        new Grade("Orgher", "Fisica", "A+", "abcd")
                        ));

        /* 2) Call the actual method we want to test*/

        /*We want to test if gradeService.getGrades() is actually able to retrieve the
        * grades from the repo*/

        List<Grade> result = gradeService.getGrades();

        /* 3) ASSERT --> checks if the method is actually behaving correctly*/

        /*here we perform a sanity check that ensures that gradeService.getGrades() is
        * actually getting the grades from gradeRepository.getGrades().
        *
        * */

        /* check the result values at index 0 (1st Grade on the list)*/
        assertEquals("Harry", result.get(0).getName());
        assertEquals("Potions", result.get(0).getSubject());
        assertEquals("C-", result.get(0).getScore());
        assertEquals("1234", result.get(0).getId());

        /* check the result values at index 1 (2nd Grade in the list)*/
        assertEquals("Orgher", result.get(1).getName());
        assertEquals("Fisica", result.get(1).getSubject());
        assertEquals("A+", result.get(1).getScore());
        assertEquals("abcd", result.get(1).getId());

    }

    @Test
    public void getGradeIndexTest() {
        //mock the data
        when(gradeRepositoryMock.getGrades())
                .thenReturn(Arrays.asList(
                        new Grade("Harry", "Potions", "C-", "1234"),
                        new Grade("Orgher", "Fisica", "A+", "abcd")
                ));

        when(gradeRepositoryMock.getGrade(0)).thenReturn(
                new Grade("Harry", "Potions", "C-", "1234")
        );

        when(gradeRepositoryMock.getGrade(1)).thenReturn(
                new Grade("Orgher", "Fisica", "A+", "abcd")
        );

        //test the actual method
        int result_1 = gradeService.getGradeIndex("1234");
        int result_2 = gradeService.getGradeIndex("abcd");
        int result_3 = gradeService.getGradeIndex("bsgtrgbrtghtffthgfthft");

        //check if test succeeds
        assertEquals(0, result_1);
        assertEquals(1, result_2);
        assertEquals(Constants.NOT_FOUND, result_3);
    }

    @Test
    public void getGradeByIdTest() {

        //mock the data
        Grade grade_1 = new Grade("Harry", "Potions", "C-", "1234");
        Grade grade_2 = new Grade("Orgher", "Fisica", "A+", "abcd");

        when(gradeRepositoryMock.getGrades())
                .thenReturn(Arrays.asList(grade_1, grade_2));

        when(gradeRepositoryMock.getGrade(0)).thenReturn(grade_1);

        when(gradeRepositoryMock.getGrade(1)).thenReturn(grade_2);

        //test the actual method
        Grade result_1 = gradeService.getGradeById("1234");
        Grade result_2 = gradeService.getGradeById("abcd");
        Grade result_3 = gradeService.getGradeById("t45hg46h4646h6wh64h");

        //assert (1)
        assertEquals("1234",result_1.getId());
        assertEquals("Harry",result_1.getName());
        assertEquals("C-",result_1.getScore());
        assertEquals("Potions",result_1.getSubject());

        assertEquals(grade_1, result_1);

        //assert (2)
        assertEquals("abcd",result_2.getId());
        assertEquals("Orgher",result_2.getName());
        assertEquals("A+",result_2.getScore());
        assertEquals("Fisica",result_2.getSubject());

        assertEquals(grade_2, result_2);

        //assert (3)
        assertEquals(null,result_3.getName());
        assertEquals(null,result_3.getScore());
        assertEquals(null,result_3.getSubject());
    }

    @Test
    //test adding a grade that does not exist yet
    public void submitGradeTest_add() {
        //mock the data
        Grade grade_1 = new Grade("Harry", "Potions", "C-", "1234");
        Grade grade_2 = new Grade("Orgher", "Fisica", "A+", "abcd");

        when(gradeRepositoryMock.getGrades())
                .thenReturn(Arrays.asList(grade_1, grade_2));

        when(gradeRepositoryMock.getGrade(0)).thenReturn(grade_1);

        when(gradeRepositoryMock.getGrade(1)).thenReturn(grade_2);

        //test the method

        /* the "VERIFY(MOCK, TIMES).METHOD()" assertion can verify how many times something happened
        * MOCK --> the mock to be "monitored"
        * TIMES() --> how many times you expect a mock method to be called
        * METHOD() --> the mock method to be "monitored"
        * */

        Grade newGrade = new Grade("Manu", "Cazzeggio", "B+", "1111");

        //test the method and then verify
        gradeService.submitGrade(newGrade);
        verify(gradeRepositoryMock, times(1)).addGrade(newGrade);
    }

    @Test
    //test replacing a grade (that already exists) with updated values
    public void submitGradeTest_update() {
        //mock the data
        Grade grade_1 = new Grade("Harry", "Potions", "C-", "1234");
        Grade grade_2 = new Grade("Orgher", "Fisica", "A+", "abcd");

        when(gradeRepositoryMock.getGrades())
                .thenReturn(Arrays.asList(grade_1, grade_2));

        when(gradeRepositoryMock.getGrade(0)).thenReturn(grade_1);

        when(gradeRepositoryMock.getGrade(1)).thenReturn(grade_2);

        //test the method

        /* the "VERIFY(MOCK, TIMES).METHOD()" assertion can verify how many times something happened
         * MOCK --> the mock to be "monitored"
         * TIMES() --> how many times you expect a mock method to be called
         * METHOD() --> the mock method to be "monitored"
         * */

        Grade newGrade = new Grade("Orgher", "Fisica", "A++", "abcd");

        //test the method and then verify
        gradeService.submitGrade(newGrade);
        verify(gradeRepositoryMock, times(1)).updateGrade(newGrade, 1);
    }

}
