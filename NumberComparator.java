package onlineTest;

import java.util.Comparator;

public class NumberComparator implements Comparator<Question> {

	    public int compare(Question a, Question b) 
	    { 
			// TODO Auto-generated method stub
	    	 // Used for sorting in ascending order of question number.
	        return a.questionNumber - b.questionNumber; 
	    }

	}

