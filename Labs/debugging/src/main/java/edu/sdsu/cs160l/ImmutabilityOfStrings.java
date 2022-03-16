package edu.sdsu.cs160l;

/**
 * Strings in Java by definition are immutable
 * String are inherently array of characters, but unlike array you can manipulate one character of string in place.
 */
public class ImmutabilityOfStrings {
    public void replaceInString() {
        String text = "Java is a great language";
        String replacement = text.replace("Java", "C++");

        System.out.println(text);
        System.out.println(replacement);
    }

    public void concatenateInString(){
        String a = "Hello";
        a = a + " "+ "World";
        System.out.println(a);
    }

    public void passAndManipulateString(){
        String text = "A";
        String updatedText = iWillAttemptToChangeIncomingString(text);

        System.out.println("Your original text "+text);
        System.out.println("Your updated text "+updatedText);
    }

    private String iWillAttemptToChangeIncomingString(String text){
        text = "B";
        return text;
    }
    
    public static void main(String[] args) {
    	ImmutabilityOfStrings ims = new ImmutabilityOfStrings();
    	ims.passAndManipulateString();
	}
}
