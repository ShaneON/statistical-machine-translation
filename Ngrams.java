import java.util.*;
import java.io.*;

public class Ngrams
{
	public static void main(String [] args)
	{
		String trans = readInFile("trans.txt");
		String ref = readInFile("ref.txt");
		LinkedList refList = stringToLLNewline(ref);

		System.out.println(calculateBleuScore(4, trans, refList));

	}

	static double calculateBleuScore(int gram, String trans, LinkedList<String> ref)
	{
		double precision = 1.0;

		for(int i = 1; i < gram + 1; i++)
		{
			double maxPrecision = 0.0;

			for(int k = 0; k < ref.size(); k++)
			{	
				LinkedList transList = computeNgrams( stringToLLSpace(trans), i);
				LinkedList refList = computeNgrams( stringToLLSpace(ref.get(k)), i);
				int count = 0;

				for(int j = 0; j < transList.size(); j++)
				{
					if(refList.contains(transList.get(j))) count++;
				}

				double tempPrecision = (double) count / (double) transList.size();
				
				if(maxPrecision < tempPrecision) maxPrecision = tempPrecision;
			}

			precision = precision * maxPrecision;

		}
		double brevity = (double) stringToLLSpace(trans).size() / 
											(double) stringToLLSpace(ref.get(0)).size();
		System.out.println(brevity);									
		double answer = Math.pow(precision, 0.25) * brevity;
		return answer;
	}

	static double calculateBrevity(String trans, String ref)
	{
		LinkedList transList = stringToLLSpace(trans);
		LinkedList refList = stringToLLSpace(ref);

		return 0.0;
	}

	static LinkedList<String> computeNgrams(LinkedList<String> s, int gram)
	{
		LinkedList<String> ngramList = new LinkedList<String>();
		String ngram = "";
		for(int i = 0; i < s.size() - gram + 1; i++)
		{
			ngram = s.get(i);

			for(int j = 1; j < gram; j++)
			{
				ngram = ngram + " " + s.get(i + j);
			}

			ngramList.add(ngram);
		}

		return ngramList;
	}

	static LinkedList<String> stringToLLSpace(String s)
	{
		String [] splitString = s.split(" ");
		LinkedList<String> splitList = new LinkedList<>();
		for(int i = 0; i < splitString.length; i ++)
		{
			splitList.add(splitString[i]);
		}
		return splitList;
	}

		static LinkedList<String> stringToLLNewline(String s)
	{
		
		String [] splitString = s.split("\n");
		LinkedList<String> splitList = new LinkedList<>();
		for(int i = 0; i < splitString.length; i ++)
		{
			splitList.add(splitString[i]);
		}
		return splitList;
	}

	static String readInFile(String path)
	{
		File file = new File(path);
		String s = "";
		try(Scanner scanner = new Scanner(file);)
		{
			s = scanner.nextLine();

			while(scanner.hasNextLine())
			{
				s = s + "\n" + scanner.nextLine();
			}
			scanner.close();
		}
		catch(Exception e){}

		return s;
	}
}