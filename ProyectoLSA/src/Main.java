import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 *
 * @author shankar
 */
public class Main {
	/**
	 * @param args
	 *            the command line arguments
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		AnalisisLSA a = new AnalisisLSA();
		
		a.cosenoTerms();
		
	}
}