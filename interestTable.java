package table;

import java.text.NumberFormat; 

public class driver {
	
	//Simple Interest Calculation 

	 public static String computeSimpleInterest(double principal, double rate, int year) { 
		 double simpleInterestAmount = 0;
		 double product = (principal * (rate/100) * year);
		 
		  simpleInterestAmount += principal + product; 
		  
		  return NumberFormat.getCurrencyInstance().format(simpleInterestAmount);
		  
	 }
	 
	 //Compound Interest Calculation
	 
	 public static String computeCompoundInterest(double principal, double rate, int year) {
		 
		 double compoundInterestAmount = 0; //initial interest 
		 
		 double product = principal * Math.pow(1 + (rate/100), year);
		 
		 compoundInterestAmount += product;
		 
		 String ans = NumberFormat.getCurrencyInstance().format(compoundInterestAmount);
		 
		 return ans;
	 }
	 
	 
	 public static String format(double principal, double rate, int year, int interestType) { 
		 
		 String type;
		 
		 //implements switch statement 
		 
		 switch(interestType) {
		 case 1:
			 type = "Simple Interest Amount";
			 break;
			 
		 case 2:
			 type = "Compound Interest Amount";
			 break;
			 
		 default:
			 type = " Simple Interest Amount, Compound Interest Amount";
		 }

		 String principle =  "Principal: " + "$" ;
		 String rateBit = " Rate: " + rate ;
		 
		 String result = principle + "," + rateBit  + "\n" + "Year" + "," + type; 
		 
		 switch(interestType){
		 case 1:
			 for (int i = 1; i <= year; i++) { 
				  result += "\n" + i + "-->" + computeSimpleInterest(principal, rate, i);
			 }
		 case 2:
			 for (int i = 1; i <= year; i++) { 
				 result += "\n" + i + "-->" + computeCompoundInterest(principal, rate, i);
			 }
		 
		 default:
			 for (int i = 1; i <= year; i++) { 
				  result += "\n" + i + "-->" + computeSimpleInterest(principal, rate, i)  + "-->" + computeCompoundInterest(principal, rate, i); 
			  }
			 
	 }
			 
		 return result; 
	 
	 }
}

	 
