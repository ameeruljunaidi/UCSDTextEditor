package document;

/** 
 * A class that represents a text document
 * @author UC San Diego Intermediate Programming MOOC team
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

	private String text;

	/**
	 * Create a new document from the given text. Because this class is abstract,
	 * this is used only from subclasses.
	 * 
	 * @param text The text of the document.
	 */
	protected Document(String text) {
		this.text = text;
	}

	/**
	 * Returns the tokens that match the regex pattern from the document text
	 * string.
	 * 
	 * @param pattern A regular expression string specifying the token pattern
	 *                desired
	 * @return A List of tokens from the document text that match the regex pattern
	 */
	protected List<String> getTokens(String pattern) {
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);

		while (m.find()) {
			tokens.add(m.group());
		}

		return tokens;
	}

	/**
	 * This is a helper function that returns the number of syllables in a word. You
	 * should write this and use it in your BasicDocument class.
	 * 
	 * You will probably NOT need to add a countWords or a countSentences method
	 * here. The reason we put countSyllables here because we'll use it again next
	 * week when we implement the EfficientDocument class.
	 * 
	 * For reasons of efficiency you should not create Matcher or Pattern objects
	 * inside this method. Just use a loop to loop through the characters in the
	 * string and write your own logic for counting syllables.
	 * 
	 * @param word The word to count the syllables in
	 * @return The number of syllables in the given word, according to this rule:
	 *         Each contiguous sequence of one or more vowels is a syllable, with
	 *         the following exception: a lone "e" at the end of a word is not
	 *         considered a syllable unless the word has no other syllables. You
	 *         should consider y a vowel.
	 */
	protected int countSyllables(String word) {
		// Implement this method so that you can call it from the
		// getNumSyllables method in BasicDocument (module 2) and
		// EfficientDocument (module 3).

		// Logic adapted from https://skeoop.github.io/labs/Lab-Syllable-Counter.pdf
		// 1. Groups of consecutive vowels counts as one syllable, y counts as vowel if
		// first vowel in group
		// 2. Final 'e' as a single vowel is not counted, unless if it is the only vowel
		// 3. 'y' counts as a vowel if it is the first vowel in a vowel group, consonant
		// otherwise
		// 4. A '-' in the middle of word acts like a consonant and divides vowel
		// sequence
		// 5. Ignore apostrophe anywhere in the word

		Character ch[] = { 'a', 'e', 'i', 'o', 'u', 'y', 'A', 'E', 'I', 'O', 'U', 'Y' };
		Set<Character> vowels = new HashSet<>(Arrays.asList(ch));
		int count = 0;

		// If it is a one letter word and that word is a vowel, just return 1
		if (word.length() == 1 && vowels.contains(word.charAt(0))) return 1;

		// Loop through every character if word is longer than one character
		for (int i = 0; i < word.length() - 1; ++i) {
			Character previous = word.charAt(i), current = word.charAt(i + 1);

			// Check for the first character, just continue it is a vowel
			if (vowels.contains(previous) && i == 0) {
				++count;
				continue;
			}

			// Ignore apostrophes
			if (current == '\'') continue;

			// Check the current and previous character
			// Inelegant, but it works, so it's fine
			if (vowels.contains(current) && !vowels.contains(previous)) {
				if (current == 'e') {
					if ((i + 1) == word.length() - 1) {
						if (count == 0) ++count;
						else continue;
					} else {
						++count;
					}
				} else {
					++count;
				}
			}

		}
		return count;
	}

	/**
	 * A method for testing
	 * 
	 * @param doc       The Document object to test
	 * @param syllables The expected number of syllables
	 * @param words     The expected number of words
	 * @param sentences The expected number of sentences
	 * @return true if the test case passed. False otherwise.
	 */
	public static boolean testCase(Document doc, int syllables, int words, int sentences) {
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound + ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound + ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound + ", expected " + sentences);
			passed = false;
		}

		if (passed) {
			System.out.println("passed.\n");
		} else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}

	/** Return the number of words in this document */
	public abstract int getNumWords();

	/** Return the number of sentences in this document */
	public abstract int getNumSentences();

	/** Return the number of syllables in this document */
	public abstract int getNumSyllables();

	/** Return the entire text of this document */
	public String getText() {
		return this.text;
	}

	/** return the Flesch readability score of this document */
	public double getFleschScore() {
		// You will play with this method in week 1, and
		// then implement it in week 2

		double wordCount = (double) getNumWords();
		return 206.836 - (1.015 * (wordCount / ((double) getNumSentences())))
				- (84.6 * (((double) getNumSyllables()) / wordCount));
	}

}
