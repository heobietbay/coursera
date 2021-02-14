package textgen;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	/*
	set "starter" to be the first word in the text
    set "prevWord" to be starter

    for each word "w" in the source text starting at the second word
     check to see if "prevWord" is already a node in the list
       if "prevWord" is a node in the list
         add "w" as a nextWord to the "prevWord" node
       else
         add a node to the list with "prevWord" as the node's word
         add "w" as a nextWord to the "prevWord" node
     set "prevWord" = "w"

    add starter to be a next word for the last word in the source text.
 */
	@Override
	public void train(String sourceText)
	{
		if(sourceText == null || sourceText.trim().length() == 0)
			return;
		List<String> words = getTokens("[a-zA-Z]+",sourceText);
		starter = words.get(0);
		String prevWord = starter;
		for(int i = 1; i < words.size(); i++) {
			String w = words.get(i);
			ListNode node = hasWord(wordList, prevWord);
			if( node != null) {
				node.addNextWord(w);
			}
			else {
				ListNode newNode = new ListNode(prevWord);
				newNode.addNextWord(w);
				wordList.add(newNode);
			}
			prevWord = w;
		}
		ListNode lastNode = new ListNode(words.get(words.size() - 1));
		lastNode.addNextWord(starter);
		wordList.add(lastNode);
	}

	ListNode hasWord (List<ListNode> lst, String word)
	{
		for(ListNode lnode : lst)
		{
			if( word.equals( lnode.getWord()))
			{
				return lnode;
			}
		}
		return null;
	}
	
	/** 
	 * Generate the number of words requested.
	 * set "currWord" to be the starter word
	   set "output" to be ""
	   add "currWord" to output
	   while you need more words
	      find the "node" corresponding to "currWord" in the list
	      select a random word "w" from the "wordList" for "node"
	      add "w" to the "output"
	      set "currWord" to be "w"
	      increment number of words added to the list
	 */
	@Override
	public String generateText(int numWords) {
		if(starter == null || starter == "") {
			return null;
		}
	    StringBuilder output = new StringBuilder("");
	    String curWord = starter;
	    output.append(starter);
		output.append(" ");
	    for(int i = 0; i < numWords - 1; ) {
			ListNode listNode = hasWord(wordList, curWord);
			String randomNextWord = listNode.getRandomNextWord(rnGenerator);
			output.append(randomNextWord);
			output.append(" ");
			curWord = randomNextWord;
			i++;
		}
		return output.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList = new LinkedList<ListNode>();
		starter = "";
		train(sourceText);
	}
	
	private List<String> getTokens(String pattern, String text)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);

		while (m.find()) {
			tokens.add(m.group());
		}

		return tokens;
	}
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int randomIdx = generator.nextInt(nextWords.size());
		return nextWords.get(randomIdx);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


