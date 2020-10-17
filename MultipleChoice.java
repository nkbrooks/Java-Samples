package onlineTest;

import java.util.ArrayList;

import java.util.Collections;

public class MultipleChoice extends Question {

	protected String[] answer;

//constructor 
	public MultipleChoice(int questionNumber, String text, double points, String[] answer) {

		super(questionNumber, text, points);

		this.answer = answer;

	}

	public double getPoints() {

		return super.getPoints();

	}

	public int getQuestionNumber() {

		return super.getQuestionNumber();

	}

	

	public ArrayList<String> getAnswer() {

		ArrayList<String> temp = new ArrayList<String>();

		for (int i = 0; i < answer.length; i++) {
			temp.add(answer[i]);
			
		}

		return temp;

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

	public String toString() {

		ArrayList<String> al = new ArrayList<String>();

		for (int i = 0; i < answer.length; i++) { 

			al.add(answer[i]);

		}

		Collections.sort(al); //Sorts the specified list into ascending order, according to the natural ordering of its elements.
		
		String ans = super.toString() + "Correct Answer: " + al;

		return ans;

	}

}
