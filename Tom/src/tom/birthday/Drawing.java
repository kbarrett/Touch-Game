package tom.birthday;

import android.util.Log;

public class Drawing {

	private String s;
	
	Drawing(String s) {this.s = s;}
	Drawing(String a, String b) {this.s = a.concat(b);}
	
	int getLength() {return s.length();}
	int get(int i) {return Integer.parseInt(""+s.charAt(i));}
	
	String getUpTo/*not inclusive*/(int i) {return s.substring(0, i);}
	String getAfter(int i) {return s.substring(i);}
	
	public String toString()
	{
		return s.toString();
	}
	public boolean equals(Object other)
	{
		if(other instanceof Drawing)
		{
			return s.equals(other.toString());
		}
		return false;
	}
	
	public static String fromOrderedToWhere(String s)
	{
		if(s.length()>9) {return null;}
		
		String newString = "";
		newString += (s.indexOf("1")+1);
		newString += (s.indexOf("2")+1);
		newString += (s.indexOf("3")+1);
		newString += (s.indexOf("4")+1);
		newString += (s.indexOf("5")+1);
		newString += (s.indexOf("6")+1);
		newString += (s.indexOf("7")+1);
		newString += (s.indexOf("8")+1);
		newString += (s.indexOf("9")+1);
		
		return newString;
	}
	public void mutate() {
		for(int i = 0; i<=s.length(); i++)
		{
			if((Math.floor(Math.random()*1000))==0)
			{
				char first = '0';
				while(first=='0')
				{
					first = s.charAt((int)Math.floor(Math.random()*9));
				}
				char second = '0';
				while(second=='0'&&second==first)
				{
					second = s.charAt((int)Math.floor(Math.random()*9));
				}
				
				Log.d("MUTATION", s + " swapping " + first + " & " + second);
				
				s.replace(first, 'a');
				s.replace(second, 'b');
				s.replace('a', second);
				s.replace('b', first);
			}
		}
	}
}
