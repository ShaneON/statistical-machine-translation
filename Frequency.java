import java.util.*;
import java.io.*;

public class Frequency{
	
	static LinkedList<String> uniqueList = new LinkedList<>();
	static int currentGram;
	static int uniqueWords;

	public static void main(String [] args){
		String sentence = "<s> a cat sat on the car </s>";
		String file = readInFile("sentences.txt");
		int gram = 2;
		uniqueWords = numUniques(file);
		System.out.println(computeNgramProbability(computeNgrams(stringToLLSpace(sentence), gram), 
												computeNgrams(stringToLLSpace(file), gram), 2, 0));

	}

	static String readInFile(String path){
		File file = new File(path);
		String s = "";
		try(Scanner scanner = new Scanner(file);){
			s = scanner.nextLine();

			while(scanner.hasNextLine()){
				s = s + " " + scanner.nextLine();
			}
			scanner.close();
		}
		catch(Exception e){}

		return s;
	}

	static int numUniques(String file){
		Set<String> uniqueSet = new HashSet<>();
		LinkedList<String> uniqueList = stringToLLSpace(file);
		uniqueSet.addAll(uniqueList);
		uniqueList.clear();
		uniqueList.addAll(uniqueSet);
		return uniqueList.size();
	}

	static LinkedList<String> stringToLLSpace(String s){
		String [] splitString = s.split(" ");
		LinkedList<String> splitList = new LinkedList<>();
		for(int i = 0; i < splitString.length; i ++){
			splitList.add(splitString[i]);
		}
		return splitList;
	}

	static double frequency(String sentence, LinkedList<String> file){
		int countGrams = 0;
		int countGramsLessOne = 0;
		for(int i = 0; i < file.size(); i ++){
			if(file.get(i).equals(sentence)) countGrams++;
		}
		for(int i = 0; i < file.size(); i ++){
			if(cutGram(file.get(i)).equals(cutGram(sentence))) countGramsLessOne++;
		}

		return (double) (countGrams + 1) / (double) (countGramsLessOne + uniqueWords);
	}

	static String cutGram(String s){
		return s.substring(0, s.lastIndexOf(" "));
	}

	static double computeNgramProbability(LinkedList<String> sentence, LinkedList<String> file, int gram, int i){
		if(i == sentence.size()) return 1.0;
		else if(uniqueList.contains(sentence.get(i))) return computeNgramProbability(sentence, file, gram, i + 1);
		else {
			uniqueList.add(sentence.get(i));
			return frequency(sentence.get(i), file) * computeNgramProbability(sentence, file, gram, i + 1);
		}	
	}

	static LinkedList<String> computeNgrams(LinkedList<String> s, int gram){
		LinkedList<String> ngramList = new LinkedList<String>();
		String ngram = "";
		for(int i = 0; i < s.size() - gram + 1; i++){
			ngram = s.get(i);

			for(int j = 1; j < gram; j++) ngram = ngram + " " + s.get(i + j);

			ngramList.add(ngram);
		}

		return ngramList;
	}
}