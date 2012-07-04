package tom.birthday;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

public class Judge {

	private ArrayList<Drawing> previousDrawings = new ArrayList<Drawing>();
	
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
		
		int iterations = 1;
		int maxNumber = 20;
		for(int j = 0; j<=iterations; j++)
		{

			ArrayList<Drawing> currentDrawings = (ArrayList<Drawing>)previousDrawings.clone();
			
			HashMap<Drawing, Byte> sameCharacters = new  HashMap<Drawing, Byte>();
			int total = 0;
			for(Drawing previousDrawing : currentDrawings)
			{
				if(previousDrawing.equals(d)) {previousDrawings.add(d); return true;}
				
				byte score = 0;
				
				for(int i=0 ; i<d.getLength(); i++)
				{
					if(d.get(i)==previousDrawing.get(i)) {++score;}
				}
				
				sameCharacters.put(previousDrawing, new Byte(score));
				total += score;
			}
			
			if(j==iterations) {return false;}
			
			int size = Math.max(maxNumber, currentDrawings.size() - (currentDrawings.size()%2));
			Log.d("The size chosen is " + size, "maxNumber = " + maxNumber);
			ArrayList<Drawing> newDrawings = new ArrayList<Drawing>(size);
			for(int i = 0; i<size; i++)
			{
				Drawing first = currentDrawings.get((int)Math.floor(Math.random()*currentDrawings.size()));
				Drawing second = currentDrawings.get((int)Math.floor(Math.random()*currentDrawings.size()));
				
				crossOver(newDrawings, first, second);
			}
			
			for(Drawing draw : newDrawings)
			{
				if(!draw.equals("000000000"))
				{
					draw.mutate();
				}
			}
			currentDrawings = newDrawings;
			
			Log.d("At end of iteration " + j + " currentDrawings", currentDrawings.toString());
		}
		
		return false;
	}
	
	private void crossOver(ArrayList<Drawing> newDrawings, Drawing first, Drawing second)
	{
		int crossoverPoint = (int)Math.floor(Math.random()*9);
		
		String replacementA = first.getUpTo(crossoverPoint) + second.getAfter(crossoverPoint);
		String replacementB = first.getAfter(crossoverPoint) + second.getUpTo(crossoverPoint);
		
		correct(replacementA);
		correct(replacementB);
		
		newDrawings.add(new Drawing(replacementA));
		newDrawings.add(new Drawing(replacementB));
	}
	
	private void correct(String string)
	{
		
		//NOT WORKING
		Log.d("precorrecting", string);
		
		StringWithProperties propString = new StringWithProperties(string);
		StringWithProperties newString = new StringWithProperties("000000000");
		
		int numberofzeroes = 0;
		for(int i = 0; i<propString.getString().length(); i++)
		{
			if(numberofzeroes>=propString.getNumberOfZeroes())
			{
				break;
			}
			int minimum = 10;
			int locofmin = 0;
			for(int j = 0; j<propString.getString().toCharArray().length; j++)
			{
				if(numberofzeroes>=propString.getNumberOfZeroes())
				{
					break;
				}
				char propChar = propString.getString().toCharArray()[j];
				if(propChar=='0')
				{
					Log.d("ZERO", "number of zeros " + numberofzeroes);
					numberofzeroes++; continue;
				}
				if(propChar<minimum)
				{
					minimum = propChar;
					locofmin = j;
				}
			}
			Log.d("PREREPLACE", "propString" + propString.getString() + " locofmin: " + locofmin + " newSTring: " + newString + " minimum: " + minimum);
			propString.replaceChar(locofmin, '0');
			newString.replaceChar(locofmin, (char)minimum);
			Log.d("POSTREPLACE", "propString" + propString.getString() + " newSTring: " + newString);
		}
		
		string = newString.getString();
		Log.d("postcorrecting", string);
	}
	
	public String toString() 
	{
		return previousDrawings.toString();
	}

}
