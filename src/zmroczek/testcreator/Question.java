package zmroczek.testcreator;

public class Question {
	private String question;
	private String answerA;
	private String answerB;
	private String answerC;
	private Boolean isCorrectA;
	private Boolean isCorrectB;
	private Boolean isCorrectC;
	private String test;

	public Question() {
		setQuestion("");
		setAnswerA("");
		setAnswerB("");
		setAnswerC("");
		setTest("");
	}

	public Question(String question, String answerA, String answerB,
			String answerC, Boolean isCorrectA, Boolean isCorrectB,
			Boolean isCorrectC, String test) {
		setQuestion(question);
		setAnswerA(answerA);
		setAnswerB(answerB);
		setAnswerC(answerC);
		setIsCorrectA(isCorrectA);
		setIsCorrectB(isCorrectB);
		setIsCorrectC(isCorrectC);
		setTest(test);
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswerA() {
		return answerA;
	}

	public void setAnswerA(String answerA) {
		this.answerA = answerA;
	}

	public String getAnswerB() {
		return answerB;
	}

	public void setAnswerB(String answerB) {
		this.answerB = answerB;
	}

	public Boolean isCorrectAnswer(Boolean answerA, Boolean answerB,
			Boolean answerC) {
		return (isCorrectA == answerA) && (isCorrectB == answerB)
				&& (isCorrectC == answerC);
	}

	public String getAnswerC() {
		return answerC;
	}

	public void setAnswerC(String answerC) {
		this.answerC = answerC;
	}

	public Boolean getIsCorrectA() {
		return isCorrectA;
	}

	public void setIsCorrectA(Boolean isCorrectA) {
		this.isCorrectA = isCorrectA;
	}

	public Boolean getIsCorrectB() {
		return isCorrectB;
	}

	public void setIsCorrectB(Boolean isCorrectB) {
		this.isCorrectB = isCorrectB;
	}

	public Boolean getIsCorrectC() {
		return isCorrectC;
	}

	public void setIsCorrectC(Boolean isCorrectC) {
		this.isCorrectC = isCorrectC;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

}
