package tom.birthday;

import java.util.ArrayList;
import java.util.HashMap;

public class Judge {

	private ArrayList<Drawing> previousDrawings = new ArrayList<Drawing>();
	
	public Judge()
	{
		previousDrawings.add(new Drawing("000000000"));
		previousDrawings.add(new Drawing("641020305"));
		previousDrawings.add(new Drawing("123040567"));
		previousDrawings.add(new Drawing("010023405"));
		previousDrawings.add(new Drawing("321498567"));
		previousDrawings.add(new Drawing("105240306"));
	}
	
	public Judge(String s)
	{
		//TODO
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
			ArrayList<Drawing> newDrawings = new ArrayList<Drawing>(size);
			for(int i = 0; i<size; i++)
			{
				Drawing first = currentDrawings.get((int)Math.floor(Math.random()*currentDrawings.size()));
				Drawing second = currentDrawings.get((int)Math.floor(Math.random()*currentDrawings.size()));
				
				int crossoverPoint = (int)Math.floor(Math.random()*9);
				newDrawings.add(new Drawing(first.getUpTo(crossoverPoint),second.getAfter(crossoverPoint)));
				newDrawings.add(new Drawing(first.getAfter(crossoverPoint),second.getUpTo(crossoverPoint)));
			}
			
			currentDrawings = newDrawings;
		}
		
		return false;
	}
	
	public String toString() {return previousDrawings.toString();}

}
