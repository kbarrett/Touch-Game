package tom.birthday;

import java.util.List;

public class StringWithProperties {
	
	public static int countZeroes(String d)
	{
		int zeroes = 0;
		for(char chars : d.toCharArray())
		{
			if(chars=='0')
			{
				zeroes ++;
			}
		}
		return zeroes;
	}
	static public String replaceChar(String str, int loc, int withWhat)
	{
		String newString = "";
		if(loc<str.length())
		{
			newString = newString.concat(str.substring(0,loc));
			newString = newString.concat(""+withWhat);
			newString = newString.concat(str.substring(loc+1));
		}
		return newString;
	}

}
