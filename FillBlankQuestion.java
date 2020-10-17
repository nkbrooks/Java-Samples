package onlineTest;

import java.util.ArrayList;

import java.util.Collections;

public class FillBlankQuestion extends Question {

	protected String[] answer;

//constructor
	public FillBlankQuestion(int questionNumber, String text, double points, String[] answer) {

		super(questionNumber, text, points);

		this.answer = answer;

	}

	public int getQuestionNumber() {

		return super.getQuestionNumber();

	}

	public double getPoints() {

		return super.getPoints();

	}

	public ArrayList<String> getAnswer() {

		ArrayList<String> temp = new ArrayList<String>();

		for (int i = 0; i < answer.length; i++) {

			temp.add(answer[i]);

		}

		return temp;

	}

	public String toString() {

		ArrayList<String> temp = new ArrayList<String>();

		for (int i = 0; i < answer.length; i++) {

			temp.add(answer[i]);

		}

		Collections.sort(temp);

		String ans = super.toString() + "Correct Answer: " + temp;

		return ans;

	}
	

	@Override
	public int compare(Question o1, Question o2) {
		// TODO Auto-generated method stub
		MultipleChoice s1 = (MultipleChoice) o1;
		MultipleChoice s2 = (MultipleChoice) o2;

		if (super.getQuestionNumber() == s2.getQuestionNumber()) {
			return 0;
		} else if (super.getQuestionNumber() > s2.getQuestionNumber()) {
			return 1;
		} else {
			return -1;
		}

	}


}
