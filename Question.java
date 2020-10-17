package onlineTest;

import java.io.Serializable;

public class Question extends NumberComparator implements Serializable{ // implements comparator

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int questionNumber;

	protected double points;

	protected String text;

	public Question(int questionNumber, String text, double points) {

		this.questionNumber = questionNumber;

		this.text = text;

		this.points = points;

	}

	public int getQuestionNumber() {

		return this.questionNumber;

	}

	public double getPoints() {

		return this.points;

	}

	@Override
	public int compare(Question o1, Question o2) {
		// TODO Auto-generated method stub
		return o1.questionNumber - o2.questionNumber;
	}

	public String toString() {

		String results = "";

		results = "Question Text: " + this.text + "\n";

		results += "Points: " + this.points + "\n";

		return results;

	}
}