import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import com.aliasi.matrix.SvdMatrix;

public class AnalisisLSA {

	/*
	 * public ArrayList<String> licitaciones(){
	 * 
	 * Licitacion licitacion = new Licitacion(); String driver =
	 * "org.postgresql.Driver"; String connectString =
	 * "jdbc:postgresql://localhost/vm31ene2014"; String user = "postgres";
	 * String password = "12345"; ArrayList<String> textos = new
	 * ArrayList<String>();
	 * 
	 * try { Class.forName(driver); Connection con =
	 * DriverManager.getConnection(connectString, user, password); Statement
	 * stmt = con.createStatement();
	 * 
	 * ResultSet rs =
	 * stmt.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion");
	 * while (rs.next()) { licitacion.setCodigo(rs.getString("id_licitacion"));
	 * licitacion.setLicitacion(rs.getString("nombre"));
	 * textos.add(licitacion.getLicitacion()); }
	 * 
	 * stmt.close(); con.close();
	 * 
	 * } catch (Exception e) { System.out.print(e.getMessage()); }
	 * 
	 * return textos;
	 * 
	 * }
	 */
	
	public String[] terminos() {

		Licitacion licitacion = new Licitacion();
		String driver = "org.postgresql.Driver";
		String connectString = "jdbc:postgresql://localhost/vm31ene2014";
		String user = "postgres";
		String password = "12345";
		String cadena = "";
		String[] terminos = null;
		String[] palabras;
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connectString, user,
					password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion");

			while (rs.next()) {
				licitacion.setCodigo(rs.getString("id_licitacion"));
				licitacion.setLicitacion(rs.getString("nombre"));
				licitacion.eliminarStopWords();
				licitacion.convertirAMinuscula();
				licitacion.removerCaracteres();
				licitacion.setLicitacion(licitacion.quitaEspacios(licitacion
						.getLicitacion()));
				cadena += licitacion.getLicitacion() + " ";
			}

			palabras = cadena.split(" ");
			Arrays.sort(palabras);

			String[] anuladas = new String[palabras.length]; // se llama a un nuevo arreglo de Strings para anular las palabras repetidas

			for (int i = 0; i < anuladas.length; i++) {
				anuladas[i] = palabras[i]; // para evitar cualquier inconveniente, se asigna cada elemento del arreglo palabras al arreglo anuladas
			}

			for (int i = 0; i < anuladas.length; i++) {
				for (int j = i + 1; j < palabras.length; j++) {
					if (anuladas[j].equals(anuladas[i])) { // si hay una palabra repetida
						anuladas[j] = ""; // se anula la palabra repetida
					}
				}
			}

			/*
			 * Para eliminar palabras anuladas repetidas
			 */

			terminos = new String[] {}; // se declara un arreglo de Strings nuevo, vacío

			for (int i = 0; i < palabras.length; i++) {
				String palabra = palabras[i]; // se declara un String con el elemento actual del arreglo de Strings palabra
				boolean palabraYaExiste = false; // un boolean para determinar si la palabra ya existe

				for (int j = 0; j < terminos.length; j++) {
					if (terminos[j].equals(palabra)) { // si el elemento actual coincide con el String analizado
						palabraYaExiste = true; // palabraYaExiste se declara verdadero
						break;
					}
				}

				if (palabraYaExiste == false) { // si la palabra no existe

					String[] vectorTemp = new String[terminos.length + 1]; // se declara un vector temporal

					for (int j = 0; j < terminos.length; j++) {
						vectorTemp[j] = terminos[j]; // a cada elemento del vector temporal se le asigna el arreglo actual
					}

					vectorTemp[terminos.length] = palabra; // al último elemento del vector temporal se le asigna el String analizado
					terminos = vectorTemp; // el vector a poblar se iguala con el Vector Temporal
				}
			}

			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		return terminos;

	}

	public String[] licitaciones() {

		Licitacion licitacion = new Licitacion();
		String driver = "org.postgresql.Driver";
		String connectString = "jdbc:postgresql://localhost/vm31ene2014";
		String user = "postgres";
		String password = "12345";
		String[] licitaciones = null;
		int contador = 0;
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connectString, user,
					password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion");
			licitaciones = new String[] {};

			while (rs.next()) {
				licitacion.setCodigo(rs.getString("id_licitacion"));
				licitacion.setLicitacion(rs.getString("nombre"));
				contador++;
			}

			ResultSet rs1 = stmt
					.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion");
			licitaciones = new String[contador];
			int cont = 0;
			while (rs1.next()) {
				licitacion.setCodigo(rs1.getString("id_licitacion"));
				licitacion.setLicitacion(rs1.getString("nombre"));
				licitaciones[cont] = licitacion.getLicitacion();
				cont++;
			}

			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		return licitaciones;

	}

	public int ocurrencia(String texto, String termino) {

		String cadena = texto;

		String[] palabras = cadena.split(" ");

		int contador = 0;

		for (int i = 0; i < palabras.length; i++) {
			if (palabras[i].equals(termino)) {
				contador++; 
			}
		}

		return contador;

	}

	public double[][] matrizTerminos() {

		String[] terminos = this.terminos();

		Licitacion licitacion = new Licitacion();
		String driver = "org.postgresql.Driver";
		String connectString = "jdbc:postgresql://localhost/vm31ene2014";
		String user = "postgres";
		String password = "12345";
		int contador = 0;
		double[][] matriz = null;
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connectString, user,
					password);
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion");
			while (rs.next()) {
				licitacion.setCodigo(rs.getString("id_licitacion"));
				licitacion.setLicitacion(rs.getString("nombre"));
				contador++;
			}

			matriz = new double[terminos.length][contador];
			int cont2 = 0;
			ResultSet rs1 = stmt
					.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion");
			while (rs1.next()) {
				licitacion.setCodigo(rs1.getString("id_licitacion"));
				licitacion.setLicitacion(rs1.getString("nombre"));
				licitacion.eliminarStopWords();
				licitacion.convertirAMinuscula();
				licitacion.removerCaracteres();
				licitacion.setLicitacion(licitacion.quitaEspacios(licitacion
						.getLicitacion()));
				for (int i = 0; i < terminos.length; i++) {
					matriz[i][cont2] = this.ocurrencia(licitacion.getLicitacion(),
							terminos[i]);
				}
				cont2++;
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		return matriz;
	}

	public SvdMatrix matrizSVD() {

		int numFactors = 2;
		double featureInit = 0.01;
		double initialLearningRate = 0.005;
		int annealingRate = 1000;
		double regularization = 0.00;
		double minImprovement = 0.0000;
		int minEpochs = 10;
		int maxEpochs = 50000;

		SvdMatrix matrix = SvdMatrix.svd(this.matrizTerminos(), numFactors,
				featureInit, initialLearningRate, annealingRate,
				regularization, null, minImprovement, minEpochs, maxEpochs);
		
		return matrix;
	}
	
	public double[] escalas() {

		SvdMatrix matriz = this.matrizSVD();
		double[] escalas = matriz.singularValues();
		
		return escalas;
	}
	
	public double[][] vectorTermLSA() {

		SvdMatrix matriz = this.matrizSVD();
		
		double[][] termVectors = matriz.leftSingularVectors();
		
		return termVectors;
	}
	
	public double[][] vectorDocLSA(String[] terminos) {

		SvdMatrix matriz = this.matrizSVD();
		
		double[][] docVectors = matriz.rightSingularVectors();
		
		return docVectors;
	}
	
	public double coseno(double[] xs, double[] ys, double[] scales) {
		double product = 0.0;
		double xsLengthSquared = 0.0;
		double ysLengthSquared = 0.0;
		for (int k = 0; k < xs.length; ++k) {
			double sqrtScale = Math.sqrt(scales[k]);
			double scaledXs = sqrtScale * xs[k];
			double scaledYs = sqrtScale * ys[k];
			xsLengthSquared += scaledXs * scaledXs;
			ysLengthSquared += scaledYs * scaledYs;
			product += scaledXs * scaledYs;
		}
		return product / Math.sqrt(xsLengthSquared * ysLengthSquared);
	}
	
	public void cosenoTerms(){
		
		String driver = "org.postgresql.Driver";
		String connectString = "jdbc:postgresql://localhost/bd_semantica";
		String user = "postgres";
		String password = "12345";
		String[] terms = this.terminos();
		double[] escalas = this.escalas();
		double[][] terminosVectores = this.vectorTermLSA();
		double cos = 0;
		double[] xs = new double[2];
		double[] ys = new double[2];
		
		
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connectString, user,
					password);
			Statement stmt = con.createStatement();
			String stm = "INSERT INTO vector_coseno" +
            		" (palabra1, palabra2, coseno) " +
            		" VALUES(?,?, ?)";
			PreparedStatement pst = con.prepareStatement(stm);
			for(int i = 0; i < terms.length; i++){
				for(int j = i+1; j < terms.length; j++){
					xs[0] = terminosVectores[i][0];
					xs[1] = terminosVectores[j][0];
					ys[0] = terminosVectores[i][1];
					ys[1] = terminosVectores[j][1];
					cos = this.coseno(xs, ys, escalas);
					System.out.println("Coseno entre " + terms[i] + " y " + terms[j] + " = " + cos);
					pst.setString(1, terms[i]);
		            pst.setString(2, terms[j]);
		            pst.setDouble(3, cos);
		            pst.executeUpdate();
				}
			}

			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

}
