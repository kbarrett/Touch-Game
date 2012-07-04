package tom.birthday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringWithProperties {
	
	private String string;
	private int numberofzeroes = 0;
	private List<Character> listchars;
	
	StringWithProperties(String string)
	{
		this.string = string;
		update();
	}
	
	private void countZeroes()
	{
		for(char chars : string.toCharArray())
		{
			if(chars=='0')
			{
				numberofzeroes ++;
			}
		}
	}
	
	private void update()
	{
		countZeroes();
		sortLetters();
	}
	
	private void sortLetters()
	{
		char[] stringchars = string.toCharArray();
		listchars = new ArrayList<Character>(stringchars.length);
		for(char char1 : stringchars)
		{
			getSortedListOfChars().add(char1);
		}
		Collections.sort(getSortedListOfChars());
	}

	public String getString() {
		return string;
	}
	
	public String toString()
	{
		return string;
	}

	public int getNumberOfZeroes() {
		return numberofzeroes;
	}

	private List<Character> getSortedListOfChars() {
		return listchars;
	}
	
	public void replaceChar(int loc, char withWhat)
	{
		String newString = "";
		if(loc<string.length())
		{
			for(int i = 0; i<loc; i++)
			{
				newString += string.charAt(i);
			}
			newString += withWhat;
			for(int i = loc + 1; i<string.length(); i++)
			{
				newString += string.charAt(i);
			}
		}
		string = newString;
		update();
	}

}
