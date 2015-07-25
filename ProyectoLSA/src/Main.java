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
		double[][] m = a.matrizTerminos();
		String c = "Matriz términos-licitaciones: \n";
		
		for(int i = 0; i < m.length; i++){
			c += "(";
			for(int j = 0; j < m[0].length; j++){
				c += (int) m[i][j];
				if(j < m[0].length-1) {
					c += " ";
				}
			}
			c += ")\n";
		}
		
		System.out.println(c);
		a.analisisLSA();
		
		/*try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connectString, user,
					password);
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion");

			ArrayList<String> voc = new ArrayList<String>();
			int cont;
			
			while (rs.next()) {
				licitacion.setCodigo(rs.getString("id_licitacion"));
				licitacion.setLicitacion(rs.getString("nombre"));
				System.out.println(licitacion.toString());
				v.setLicitacion(licitacion);
				voc = v.vocabulario("");
				for(int i = 0; i < voc.size(); i++){
					cont = a.ocurrencia(licitacion, voc.get(i));
					System.out.println(voc.get(i) + ": " + cont);
				}
			}
			
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}*/
	}
}