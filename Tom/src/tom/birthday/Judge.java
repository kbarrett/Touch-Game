package tom.birthday;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

public class Judge {

	private ArrayList<Drawing> previousDrawings = new ArrayList<Drawing>();
	private HashMap<String, Integer> failedDrawings = new HashMap<String, Integer>();
	private int maxUntilAccept = 5;
	
	public Judge()
	{
		initialise();
	}
	
	private void initialise()
	{
		previousDrawings.add(new Drawing("000000000"));
		previousDrawings.add(new Drawing("641020305"));
		previousDrawings.add(new Drawing("123040567"));
		previousDrawings.add(new Drawing("010234050"));
		previousDrawings.add(new Drawing("321498567"));
		previousDrawings.add(new Drawing("105240306"));
		previousDrawings.add(new Drawing("105246307"));
		previousDrawings.add(new Drawing("105240306"));
		previousDrawings.add(new Drawing("703642501"));
		previousDrawings.add(new Drawing("123654789"));
		previousDrawings.add(new Drawing("703642501"));
		previousDrawings.add(new Drawing("321040765"));
	}
	
	public Judge(String s, boolean converted)
	{
		initialise();
		if(converted) {previousDrawings.add(new Drawing(s));}
		else {previousDrawings.add(new Drawing(Drawing.fromOrderedToWhere(s)));}
	}
	public Judge(String[] ss, boolean converted)
	{
		initialise();
		if(converted)
		{
			for(String s : ss)
			{
				previousDrawings.add(new Drawing(s));}
			}
		else
		{
			for(String s : ss)
			{
				previousDrawings.add(new Drawing(Drawing.fromOrderedToWhere(s)));
			}
		}
	}
	
	public boolean isNice(String s)
	{
		return isNice(new Drawing(s));
	}
	
	public boolean isNice(Drawing d)
	{
		if(d.toString().length()==0) {return false;}
		
		int iterations = 5;
		int maxNumber = 50;
		
		@SuppressWarnings("unchecked")
		ArrayList<Drawing> currentDrawings = (ArrayList<Drawing>)previousDrawings.clone();
		
		if(symmetrical(d))
		{
			Log.d("SYMMETRICAL", d + " is symmetrical");
			if(Math.floor(Math.random()*3)==0)
			{
				if(!previousDrawings.contains(d))
				{
					previousDrawings.add(d);
					return true;
				}
			}
		}
		else
		{
			Log.d("NOT SYMMETRICAL", d + " is not symmetrical");
		}
		
		for(int j = 0; j<=iterations; j++)
		{	
			ArrayList<Drawing> sameCharacters = new  ArrayList<Drawing>();
			for(Drawing previousDrawing : currentDrawings)
			{
				if(previousDrawing.equals(d))
				{
					if(!previousDrawings.contains(d))
					{
						previousDrawings.add(d); 
					}
					return true;
				}
				
				for(int i=0 ; i<d.getLength(); i++)
				{
					if(d.get(i)==previousDrawing.get(i))
					{
						sameCharacters.add(previousDrawing);
					}
				}
			}
			
			if(j==iterations) {return checkFailure(d);}
			
			int size = Math.min(maxNumber, currentDrawings.size() - (currentDrawings.size()%2));
			Log.d("The size chosen is " + size, "maxNumber = " + maxNumber);
			ArrayList<Drawing> newDrawings = new ArrayList<Drawing>(size);
			
			for(int i = 0; i<size; i++)
			{
				int firstposition = (int)Math.floor(Math.random()*sameCharacters.size());
				Drawing first = sameCharacters.get(firstposition);
				
				int secondposition = (int)Math.floor(Math.random()*sameCharacters.size());
				Drawing second = sameCharacters.get(secondposition);

				crossOver(newDrawings, first, second);
			}
			
			for(Drawing draw : newDrawings)
			{
				if(StringWithProperties.countZeroes(draw.toString())<8)
				{
					draw.mutate();
				}
			}
			currentDrawings = newDrawings;
			
			Log.d("At end of iteration " + j + " currentDrawings", currentDrawings.toString());
		}
		
		return checkFailure(d);
	}
	
	private boolean symmetrical(Drawing d) {
		int max = 0;
		int[] draw = new int[d.getLength()];
		for(int i = 0; i<d.getLength(); i++)
		{ 
			draw[i] = d.get(i);
			max = Math.max(max, draw[i]);
		}
		if(max==0) {return true;}
		Log.d("MAX", ""+max);
		
		return    (same(max,draw[0],draw[8]) && same(max,draw[1],draw[5]) && same(max,draw[3],draw[7])) // diagonal /
				||(same(max,draw[0],draw[2]) && same(max,draw[3],draw[5]) && same(max,draw[6],draw[8])) //vertical
				||(same(max,draw[0],draw[6]) && same(max,draw[1],draw[7]) && same(max,draw[2],draw[8])) //horizontal
				||(same(max,draw[1],draw[3]) && same(max,draw[2],draw[6]) && same(max,draw[5],draw[7])); //diagonal \
	}
	
	private boolean same(int max, int a, int b)
	{
		return     (a==b && a==0)
				|| (max%2==1 && max - a + 1 == b)
				|| (max%2==0 && max - a == b);
	}

	private boolean checkFailure(Drawing draw)
	{
		String d = draw.toString();
		if(failedDrawings.get(d)!=null)
		{
			int num = failedDrawings.get(d);
			if(num>=maxUntilAccept)
			{
				previousDrawings.add(draw);
				return true;
			}
			else
			{
				failedDrawings.remove(d);
				failedDrawings.put(d, num+1);
				return false;
			}
		}
		else
		{
			failedDrawings.put(d, 1);
			return false;
		}
	}
	
	private void crossOver(ArrayList<Drawing> newDrawings, Drawing first, Drawing second)
	{
		int crossoverPoint = (int)Math.floor(Math.random()*9);
		
		String replacementA = first.getUpTo(crossoverPoint) + second.getAfter(crossoverPoint);
		String replacementB = first.getAfter(crossoverPoint) + second.getUpTo(crossoverPoint);
		
		replacementA = correct(replacementA);
		replacementB = correct(replacementB);
		
		newDrawings.add(new Drawing(replacementA));
		newDrawings.add(new Drawing(replacementB));
	}
	
	private String correct(String string)
	{	
		String newString = "000000000";
		
		int current = 1;
		for(int i = 0; i<string.length(); i++)
		{
			int minimum = 10;
			int locofmin = 0;
			for(int j = 0; j<string.length(); j++)
			{
				int propChar = Integer.parseInt(""+string.charAt(j));
				if(propChar!=0 && propChar<minimum)
				{
					minimum = propChar;
					locofmin = j;
				}
			}
			if(minimum<10)
			{
				string = StringWithProperties.replaceChar(string,locofmin, 0);
				newString = StringWithProperties.replaceChar(newString,locofmin, current++);
			}
			else
			{
				break;
			}
		}
		return newString;
	}
	
	public String toString() 
	{
		return previousDrawings.toString();
	}

}
