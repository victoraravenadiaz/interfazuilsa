import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
		
		String driver = "org.postgresql.Driver";
		String connectString = "jdbc:postgresql://localhost/vm31ene2014";
		String user = "postgres";
		String password = "12345";
		Licitacion licitacion = new Licitacion();
		Vocabulario v = new Vocabulario();
		
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connectString, user,
					password);
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion");

			while (rs.next()) {
				licitacion.setCodigo(rs.getString("id_licitacion"));
				licitacion.setLicitacion(rs.getString("nombre"));
				System.out.println(licitacion.toString());
				v.setLicitacion(licitacion);
				System.out.println(v.vocabulario(""));
			}
			
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
	}
}