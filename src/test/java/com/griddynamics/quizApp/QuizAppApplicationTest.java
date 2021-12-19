package com.griddynamics.quizApp;

import com.griddynamics.quizApp.service.QuizService;
import org.testng.asserts.SoftAssert;

public class QuizAppApplicationTest extends BaseTest{

//	@Test
	void questionsCanBeLoadedTest() {
		SoftAssert softly = new SoftAssert();
		QuizService quizService = getQuizService();
		
//		softly.assertEquals(quizService.generateQuestionListWithParam("0").size(), 0);
//		softly.assertEquals(quizService.generateQuestionListWithParam("10").size(), 10);
		
		softly.assertAll();
	}

//	@Test
	void questionsInUseParamPopulatedTest() {
		SoftAssert softly = new SoftAssert();
		QuizService quizService = getQuizService();

		quizService.generateQuestionListWithParam("0");
		softly.assertEquals(quizService.getDh().getQuestionsInUse().size(), 0);

		quizService.generateQuestionListWithParam("10");
		softly.assertEquals(quizService.getDh().getQuestionsInUse().size(), 10);
		
		softly.assertAll();
	}
}
