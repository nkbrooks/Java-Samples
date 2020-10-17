package onlineTest;

public class Boolean extends Question  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean answer;

	public Boolean(int questionNumber, String text, double points, boolean answer) {

		super(questionNumber, text, points);

		this.answer = answer;

	}

	public double getPoints() {

		return super.getPoints(); //Gets points from base class.

	}

	public boolean getAnswer() {

		return this.answer;

	}

	public int getQuestionNumber() {

		return super.getQuestionNumber();

	}

	public String toString() {
		
		return(answer? super.toString() + "Correct Answer: True": super.toString() + "Correct Answer: False" );

		
	}

	public int compareTo(Boolean obj) {

		if (super.getQuestionNumber() > obj.getQuestionNumber()) {

			return 1;

		} else if (super.getQuestionNumber() == obj.getQuestionNumber()) {

			return 0;

		}

		return -1;

	}

}