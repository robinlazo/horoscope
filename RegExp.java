package Horoscope;

public class RegExp {
	RegExp() {}
	
	public String capitalize(String word) {
		word = quitBacklash(word);
	    return Character.toUpperCase(word.charAt(0)) + word.substring(1);
	}
	
	public String quitBacklash(String word) {
		return word.replace('_', ' ');
	}
}
