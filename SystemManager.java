package onlineTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

import java.io.Serializable;

public class SystemManager implements Manager, Serializable {

	private static final long serialVersionUID = 2376669673449675905L;
	// tree maps
	private Map<Integer, String> listOfExams = new TreeMap<Integer, String>();
	private Map<String, Double> gradeCutOff = new TreeMap<String, Double>();

	// hash map
	protected Map<Integer, ArrayList<Question>> exams = new HashMap<Integer, ArrayList<Question>>();
	// linked hash map
	protected Map<String, Map<Integer, ArrayList<Double>>> scores = new LinkedHashMap<String, Map<Integer, ArrayList<Double>>>();

	/**
	 * Adds the specified exam to the database.
	 * 
	 * @param examId
	 * @param title
	 * @return false is exam already exists.
	 */

	public boolean addExam(int examId, String title) {
		boolean flag = false;
		if (!exams.containsKey(examId)) {

			exams.put(examId, new ArrayList<Question>());

			listOfExams.put(examId, title); // The java.util.HashMap.put() method of HashMap is used to insert a mapping
											// into a map
			flag = true;

		}

		return (flag ? true : false);

	}

	/**
	 * Adds a multiple choice question to the specified exam. If the question
	 * already exists it is overwritten.
	 * 
	 * @param examId
	 * @param questionNumber
	 * @param text           Question text
	 * @param points         total points
	 * @param answer         expected answer
	 */

	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {

		boolean flag = false;

		if (!exams.containsKey(examId)) {

			return;

		} else {

			flag = true;
		}

		try {
			if (flag) {

				for (int i = 0; i < exams.get(examId).size(); i++) {

					if (equals(i, questionNumber)) {

						remove(exams.get(examId), (i)); // add void method

						exams.get(examId).add(new MultipleChoice(questionNumber, text, points, answer));

						sort(exams.get(examId), new NumberComparator());

						return;

					}

				}

			}
		} finally {

			exams.get(examId).add(new MultipleChoice(questionNumber, text, points, answer));

			sort(exams.get(examId), new NumberComparator());
		}
	}

	/**
	 * Adds a true and false question to the specified exam. If the question already
	 * exists it is overwritten.
	 * 
	 * @param examId
	 * @param questionNumber
	 * @param text           Question text
	 * @param points         total points
	 * @param answer         expected answer
	 */

	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		boolean flag = false;
		if (!exams.containsKey(examId)) {

			return; // Methods with return type void returns nothing.

		} else {

			flag = true;
		}

		if (flag) {
			if (exams.containsKey(examId)) {
				if (exams.get(examId).size() != 0) {
					for (int i = 0; i < exams.get(examId).size(); i++) {

						if (equals(i, questionNumber)) {

							remove(exams.get(examId), i);

							exams.get(examId).add(new Boolean(questionNumber, text, points, answer));

							sort(exams.get(examId), new NumberComparator());

							return;

						}
					}
				}

			}

			exams.get(examId).add(new Boolean(questionNumber, text, points, answer));

			sort(exams.get(examId), new NumberComparator());

		}
	}

	/**
	 * Adds a fill-in-the-blanks question to the specified exam. If the question
	 * already exits it is overwritten. Each correct response is worth
	 * points/entries in the answer.
	 * 
	 * @param examId
	 * @param questionNumber
	 * @param text           Question text
	 * @param points         total points
	 * @param answer         expected answer
	 */
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		boolean flag = false;

		if (!exams.containsKey(examId)) {

			return;

		} else {

			flag = true;
		}

		if (flag) {

			for (int i = 0; i < exams.get(examId).size(); i++) {

				if (equals(i, questionNumber)) {

					remove(exams.get(examId), i);

					exams.get(examId).add(new FillBlankQuestion(questionNumber, text, points, answer));

					sort(exams.get(examId), new NumberComparator());

					return;

				}

			}
		}

		exams.get(examId).add(new FillBlankQuestion(questionNumber, text, points, answer));

		sort(exams.get(examId), new NumberComparator());

	}

	/**
	 * Returns a string with the following information per question:<br />
	 * "Question Text: " followed by the question's text<br />
	 * "Points: " followed by the points for the question<br />
	 * "Correct Answer: " followed by the correct answer. <br />
	 * The format for the correct answer will be: <br />
	 * a. True or false question: "True" or "False"<br />
	 * b. Multiple choice question: [ ] enclosing the answer (each entry separated
	 * by commas) and in sorted order. <br />
	 * c. Fill in the blanks question: [ ] enclosing the answer (each entry
	 * separated by commas) and in sorted order. <br />
	 * 
	 * @param examId
	 * @return "Exam not found" if exam not found, otherwise the key
	 */
	public String getKey(int examId) {

		boolean flag = false;
		String key = "";

		if (!exams.containsKey(examId)) {

			return "Exam not found";

		} else {
			flag = true;
		}

		int length = exams.get(examId).size();

		if (flag) {

			if (exams.get(examId).size() != 0) {

				for (int i = 0; i < length; i++) {

					key += exams.get(examId).get(i).toString() + "\n";

				}
			}
		}

		return key;

	}

	/**
	 * Adds the specified student to the database. Names are specified in the format
	 * LastName,FirstName
	 * 
	 * @param name
	 * @return false if student already exists.
	 */

	public boolean addStudent(String name) {

		boolean flag = false;

		if (!scores.containsKey(name)) {

			addName(name);

			flag = true;

		}

		return (flag ? true : false);
	}

	/**
	 * Enters the question's answer into the database.
	 * 
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */

	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {

		boolean flag = false;

		if (!scores.get(studentName).containsKey(examId)) {
			flag = true;
		}

		if (flag) {

			scores.get(studentName).put(examId, new ArrayList<Double>());

		}
		boolean incorrect = false;
		if (exams.get(examId).size() != 0) {
			for (int i = 0; i < exams.get(examId).size(); i++) {

				Question temp = (Question) exams.get(examId).get(i);

				if (equals(temp.getQuestionNumber(), questionNumber)) { // Protected equals method.

					Boolean ans = (Boolean) temp;

					if (answer != ans.getAnswer()) { // True if answer is wrong.
						incorrect = true;
					}

					if (answer == ans.getAnswer()) { // True if answer is correct.
						scores.get(studentName).get(examId).add(ans.getPoints());
					}

				}

			}
		}

		if (incorrect) {
			scores.get(studentName).get(examId).add(0.0);

		}
	}

	/**
	 * Enters the question's answer into the database.
	 * 
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {

		boolean flag = false;

		if (!scores.get(studentName).containsKey(examId)) {

			flag = true;

		}

		if (flag) {
			scores.get(studentName).put(examId, new ArrayList<Double>());

		}

		ArrayList<String> answerKey = new ArrayList<String>();

		List<String> studentKey = Arrays.asList(answer); // Returns a fixed-size list backed by the specified array.
		// This class also contains a static factory that allows arrays to be viewed as
		// lists.
		double points = 0.0;

		int length = exams.get(examId).size();

		try {
			if (length != 0) {
				for (int i = 0; i < length; i++) {

					Question temp = (Question) exams.get(examId).get(i);

					if (equals(questionNumber, temp.getQuestionNumber())) {

						MultipleChoice question = (MultipleChoice) temp;

						points = question.getPoints();

						for (int j = 0; j < question.getAnswer().size(); j++) {

							if (scores.get(studentName).containsKey(examId)) {

								answerKey.add(question.getAnswer().get(j));

							}
						}

					}
				}
			}

			if (answerKey.size() != 0) {
				for (int m = 0; m < answerKey.size(); m++) {

					if (!studentKey.contains(answerKey.get(m))) {

						scores.get(studentName).get(examId).add(0.0);

						return; // Return value for void methods.

					}

				}
			}

			if (answerKey.size() != 0) {

				if (answerKey.size() != studentKey.size()) {

					scores.get(studentName).get(examId).add(0.0);

					return; // Return value for void methods.

				}
			}
		} finally {

		}

		scores.get(studentName).get(examId).add(points);

	}

	/**
	 * Enters the question's answer into the database.
	 * 
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */

	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {

		boolean flag = false;

		if (!scores.get(studentName).containsKey(examId)) {

			flag = true;

		}

		if (flag) {
			scores.get(studentName).put(examId, new ArrayList<Double>());

		}

		ArrayList<String> studentKey = new ArrayList<String>();

		ArrayList<String> list = new ArrayList<String>();

		double score = 0.0, finalScore = 0.0;

		if (answer.length != 0) {
			for (int i = 0; i < answer.length; i++) {
				studentKey.add(answer[i]);

			}
		}

		for (int i = 0; i < exams.get(examId).size(); i++) {

			Question temp = (Question) exams.get(examId).get(i);

			if (equals(questionNumber, temp.getQuestionNumber())) {

				FillBlankQuestion question = (FillBlankQuestion) temp; // Casts question to FillBlankQuestion.

				score = question.getPoints() / question.getAnswer().size();

				for (int j = 0; j < question.getAnswer().size(); j++) {

					list.add(question.getAnswer().get(j));

				}

			}

		}

		try {
			for (int i = 0; i < studentKey.size(); i++) {

				if (list.contains(studentKey.get(i))) {

					finalScore += score;

				}

			}
		}

		finally {

			scores.get(studentName).get(examId).add(finalScore);
		}
	}

	/**
	 * Returns the score the student got for the specified exam.
	 * 
	 * @param studentName
	 * @param examId
	 * @return score
	 */

	public double getExamScore(String studentName, int examId) {

		double score = 0.0;

		int length = scores.get(studentName).get(examId).size();
		if (length != 0) {
			for (int i = 0; i < length; i++) {

				score += scores.get(studentName).get(examId).get(i);

			}
		}
		return (double) score;

	}

	/**
	 * Generates a grading report for the specified exam. The report will include
	 * the following information for each exam question:<br />
	 * "Question #" {questionNumber} {questionScore} " points out of "
	 * {totalQuestionPoints}<br />
	 * The report will end with the following information:<br />
	 * "Final Score: " {score} " out of " {totalExamPoints};
	 * 
	 * @param studentName
	 * @param examId
	 * @return report
	 */
	public String getGradingReport(String studentName, int examId) {

		String report = "";

		int counter = 0;

		double studentScore = 0.0, totalScore = 0.0;

		try {
			counter++;
			
			for (int i = 0; i < scores.get(studentName).get(examId).size(); i++) {
				totalScore += exams.get(examId).get(i).getPoints();

				studentScore += scores.get(studentName).get(examId).get(i);

				String question = "Question #";
				String points = " points out of ";

				report += question + counter++ + " " + scores.get(studentName).get(examId).get(i);

				report += points + exams.get(examId).get(i).getPoints();

				report += "\n";

			}
		} finally {
			report += "Final Score: " + studentScore + " out of " + totalScore;
		}

		return report;

	}

	/**
	 * Sets the cutoffs for letter grades. For example, a typical curve we will have
	 * new String[]{"A","B","C","D","F"}, new double[] {90,80,70,60,0}. Anyone with
	 * a 90 or above gets an A, anyone with an 80 or above gets a B, etc. Notice we
	 * can have different letter grades and cutoffs (not just the typical curve).
	 * 
	 * @param letterGrades
	 * @param cutoffs
	 */

	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {

		if (!(letterGrades.length != cutoffs.length)) {
			if (letterGrades.length != 0) {
				for (int i = 0; i < letterGrades.length; i++) {

					gradeCutOff.put(letterGrades[i], cutoffs[i]); // If the map previously contained a mapping for the
																	// // key,
																	// the old value is replaced by the specified value
				}
			}
		}
	}

	/**
	 * Computes a numeric grade (value between 0 and a 100) for the student taking
	 * into consideration all the exams. All exams have the same weight.
	 * 
	 * @param studentName
	 * @return grade
	 */

	public double getCourseNumericGrade(String studentName) {

		double initial = 0.0, examTotal = 0.0;

		if (scores.get(studentName).keySet() != null) {

			for (Integer x : scores.get(studentName).keySet()) {

				for (int i = 0; i < exams.get(x).size(); i++) {

					examTotal += exams.get(x).get(i).getPoints();

				}

				initial += (getExamScore(studentName, x) / examTotal);

				examTotal = 0.0;

			}
		}
		examTotal = 0.0;

		double ans = average(initial, exams.size());
		return ans;

	}

	/**
	 * Computes a letter grade based on cutoffs provided. It is assumed the cutoffs
	 * have been set before the method is called.
	 * 
	 * @param studentName
	 * @return letter grade
	 */

	public String getCourseLetterGrade(String studentName) {

		String ans = "";

		String letterGrade = "";

		for (String s : gradeCutOff.keySet()) {

			if (gradeCutOff.get(s) <= getCourseNumericGrade(studentName)) {

				ans = s;

				return ans;

			} else {

				ans = letterGrade;
			}

		}

		return ans;

	}

	/**
	 * Returns a listing with the grades for each student. For each student the
	 * report will include the following information: <br />
	 * {studentName} {courseNumericGrade} {courseLetterGrade}<br />
	 * The names will appear in sorted order.
	 * 
	 * @return grades
	 */
	public String getCourseGrades() {

		ArrayList<String> courseGrades = new ArrayList<String>();

		String grades = "";
		if (scores.keySet() != null) {
			for (String student : scores.keySet()) {

				courseGrades.add(student);
			}
		}

		Collections.sort(courseGrades); // Sorts grades in order by taking in the array list.

		if (courseGrades.size() != 0) {
			for (int i = 0; i < courseGrades.size(); i++) {

				String numericGrade = " " + getCourseNumericGrade(courseGrades.get(i));
				String letterGrade = getCourseLetterGrade(courseGrades.get(i));

				grades += courseGrades.get(i) + " " + numericGrade + " " + letterGrade + "\n";

			}
		}
		return grades;

	}

	/**
	 * Returns the maximum score (among all the students) for the specified exam.
	 * 
	 * @param examId
	 * @return maximum
	 */

	public double getMaxScore(int examId) {

		ArrayList<Double> totalScores = new ArrayList<Double>();

		double currentStudentScore = 0.0, start = 0.0;

		if (scores.keySet() != null) {

			for (String student : scores.keySet()) {
				int size = scores.get(student).get(examId).size();
				if (size != 0) {

					for (int i = 0; i < size; i++) {

						currentStudentScore += scores.get(student).get(examId).get(i);

					}
				}
				totalScores.add(currentStudentScore);

				currentStudentScore = start;

			}
		}

		return Collections.max(totalScores); // Returns the maximum element of the given collection.

	}

	/**
	 * Returns the minimum score (among all the students) for the specified exam.
	 * 
	 * @param examId
	 * @return minimum
	 */
	public double getMinScore(int examId) {

		ArrayList<Double> totalScores = new ArrayList<Double>();

		double currentStudentScore = 0.0, start = 0.0;

		if (scores.keySet() != null) {

			for (String student : scores.keySet()) {
				int size = scores.get(student).get(examId).size();
				if (size != 0) {

					for (int i = 0; i < size; i++) {

						currentStudentScore += scores.get(student).get(examId).get(i);

					}
				}

				totalScores.add(currentStudentScore);

				currentStudentScore = start;

			}
		}

		return Collections.min(totalScores); // Returns the minimum element of the given collection.

	}

	/**
	 * Returns the average score for the specified exam.
	 * 
	 * @param examId
	 * @return average
	 */
	public double getAverageScore(int examId) {

		double average = 0.0;
		int studentCounter = 0;

		if (scores.keySet() != null) {

			for (String student : scores.keySet()) {

				int size = scores.get(student).get(examId).size();
				if (size != 0) {
					for (int i = 0; i < size; i++) {

						average += scores.get(student).get(examId).get(i);

					}
				}

				studentCounter++;

			}
		}

		double ans = average / studentCounter; // method

		return ans;

	}

	@Override

	/**
	 * It will serialize the Manager object and store it in the specified file.
	 */
	public void saveManager(Manager manager, String fileName) {

// TODO Auto-generated method stub

	}

	/**
	 * It will return a Manager object based on the serialized data found in the
	 * specified file.
	 */
	public Manager restoreManager(String fileName) {

// TODO Auto-generated method stub

		Manager manager = null;
		try {
			FileInputStream inputFile = new FileInputStream(fileName);
			ObjectInputStream objectInput = new ObjectInputStream(inputFile);
			manager = (Manager) objectInput.readObject();
			closedFiles(inputFile, objectInput);
		} catch (IOException e) { // checks possible exception
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException f) {
			f.printStackTrace();
			return null;
		}
		return manager;

	}

	/********** Protected/Private methods ***************/
	//Method that closes inputs.
	protected void closedFiles(FileInputStream inputFile, ObjectInputStream objectInput) {
		try {
			objectInput.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			inputFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//This is a equals method.
	protected boolean equals(int a, int b) {

		if (a == b) {
			return true;
		} else {
			return false;
		}

	}
//Sorts by NumberComparator.
	private void sort(ArrayList<Question> arrayList, NumberComparator numberComparator) {
		Collections.sort(arrayList, new NumberComparator()); // Sorts the specified list according to the order induced
																// by the specified comparator

	}
//Protected method that adds the name of the student.
	protected void addName(String name) {
		if (name != null) {
			scores.put(name, new HashMap<Integer, ArrayList<Double>>());
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	protected void remove(ArrayList<Question> arrayList, int i) {
		exams.get(arrayList).remove(i);
	}
//Private method that calculates the average.
	private double average(double initial, double size) {
		return (initial * 100) / size;
	}

}