package com.griddynamics.quizApp;

import com.griddynamics.quizApp.service.QuizService;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class QuizAppApplicationTest extends BaseTest{

	@Test
	void questionsCanBeLoadedTest() {
		SoftAssert softly = new SoftAssert();
		QuizService quizService = getQuizService();
		
		softly.assertEquals(quizService.getQuestionListWithParameter("0").size(), 0);
		softly.assertEquals(quizService.getQuestionListWithParameter("10").size(), 10);
		
		softly.assertAll();
	}

	@Test
	void questionsInUseParamPopulatedTest() {
		SoftAssert softly = new SoftAssert();
		QuizService quizService = getQuizService();

		quizService.getQuestionListWithParameter("0");
		softly.assertEquals(quizService.getQuestionsInUse().size(), 0);

		quizService.getQuestionListWithParameter("10");
		softly.assertEquals(quizService.getQuestionsInUse().size(), 10);
		
		softly.assertAll();
	}
}
