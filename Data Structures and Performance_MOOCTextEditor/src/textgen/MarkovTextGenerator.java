package textgen;

/**
 *  The interface for the MarkovTextGenerator
 *  @author UC San Diego Intermediate Programming MOOC team
 *  
 */
public interface MarkovTextGenerator {
	
	/** Train the generator by adding the sourceText */
    void train(String sourceText);
	
	/** Generate the text with the specified number of words */
    String generateText(int numWords);
	
	/** Retrain the generator from scratch on the source text */
    void retrain(String sourceText);
}
