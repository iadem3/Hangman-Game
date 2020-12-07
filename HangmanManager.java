import java.util.*;

public class HangmanManager {
	Set<String> dictionary1;
	Set<Character> guesses = new TreeSet<>();
	Map<String,TreeSet<String>> patternHolder = new HashMap<>();
	String currentPattern = "";
	String empty = "";
	int maxGuesses;
	

	public HangmanManager(Collection<String> dictionary, int length, int max) {
		if(length < 1 || max < 0) {
			throw new IllegalArgumentException();
		}
		else {
			dictionary1 = new TreeSet<>();
			for(String i : dictionary) {
				if(i.length() == length) {
					dictionary1.add(i);
				}
			}
			for(int i = 0; i < length; i++) {
				currentPattern += "- ";
				empty += "- ";
			}
			maxGuesses = max;
			
		}
		
	}
	
	public Set<String> words(){
		return dictionary1;
	}
	
	public int guessesLeft() {
		return maxGuesses;
	}
	
	public Set<Character> guesses(){
		return guesses;
	}
	
	public String pattern() {
		return currentPattern;
		
	}
	
	public int record(char guess) {
		if(maxGuesses < 1 || dictionary1.isEmpty()) {
			throw new IllegalStateException();
		}
		else if(guesses.contains(guess)) {
			throw new IllegalArgumentException();
		}
		else {
			guesses.add(guess);
			
			Iterator<String> k = dictionary1.iterator();
			while(k.hasNext()) {
				String pattern = "";
				String temp = k.next();
				for(int i = 0; i < temp.length(); i++) {
					if(temp.charAt(i) == guess) {
						pattern += guess + " ";
					}
					else {
						pattern += "- ";
					}
				}
				
				if(patternHolder.containsKey(pattern)) {
					patternHolder.get(pattern).add(temp);
				}
				else {
					TreeSet<String> holder = new TreeSet<>();
					holder.add(temp);
					patternHolder.put(pattern, holder);
				}
				
			}
			
			Collection<TreeSet<String>> temp3 = patternHolder.values();
			Iterator<TreeSet<String>> k4 = temp3.iterator();
			
			int maxWords = 0;
			while(k4.hasNext()) {
				TreeSet<String> counter = k4.next();
				int counter1 = counter.size();
				if(counter1 > maxWords) {
					maxWords = counter1;
					dictionary1 = counter;
				}	
			}
			return adjust(patternHolder, guess);
			
		}
	}
	
	private int adjust(Map<String,TreeSet<String>>  patternHolder, char guess) {
		int max1 = 0;
		Iterator<String> k2 = patternHolder.keySet().iterator();
		while (k2.hasNext()) {
			String h = k2.next();
			if(patternHolder.get(h) == dictionary1 ) {
				if(h.equals(empty) && !(currentPattern.equals(empty))) {
					break;
				}
				else {
					for(int j = 0; j < h.length(); j++) {
						if(h.charAt(j) == guess) {
							max1++;
							StringBuilder sb = new StringBuilder(currentPattern);
							//sb.deleteCharAt(j);
							sb.setCharAt(j, guess);
							currentPattern = sb.toString();
						}
				}
				}
			}
		}
		patternHolder.clear();
		if(max1 == 0) {
			maxGuesses--;
		}
		
		return max1;
	}
}
