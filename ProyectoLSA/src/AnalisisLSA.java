import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import com.aliasi.matrix.SvdMatrix;
import com.sun.xml.internal.ws.util.StringUtils;

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
	/**
	 * 
	 * @return
	 */
	public String[] terminosVocabulario() {


		String driver = "org.postgresql.Driver"; //se declara una cadena de texto con el driver del motor de base de datos
		String connectString = "jdbc:postgresql://localhost/vm31ene2014"; //se declara una cadena para hacer la conexión a la base de datos
		String user = "postgres"; //se declara una cadena para conectar con el usuario del motor de base de datos
		String password = "12345"; //se declara una cadena con la contraseña del motor
		
		Licitacion licitacion = new Licitacion(); // se declara un nuevo objeto de la clase Licitación
		String[] vocabulario = null; //se declara un nuevo arreglo de cadenas vacío
		Vocabulario v = new Vocabulario(); //Se declara un nuevo objeto de la clase Vocabulario
		
		try {
			Class.forName(driver); //se llama al driver del motor de base de datos
			Connection con = DriverManager.getConnection(connectString, user, password); //Se hace la conexión a la base de datos
			Statement stmt = con.createStatement(); //Se crea un objeto de clase Statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion LIMIT 10"); //se ejecuta la consulta a la base de datos

			/*
			 * Lo que se hace mientras se recorre la base
			 * de datos
			 * */
			String texto = "";
			while (rs.next()) {
				//TODO - QUITAR LOS STOP WORD
				licitacion.setCodigo(rs.getString("id_licitacion"));
				licitacion.setLicitacion(rs.getString("nombre"));
				licitacion.removerCaracteres(); //para remover caracteres especiales
				licitacion.removerAcentos(); //para remover acentos
				licitacion.convertirAMinuscula(); //para convertir a mayúsculas
				licitacion.setLicitacion(licitacion.quitaEspacios(licitacion
						.getLicitacion())); //Se quitan espacios generados por la remoción de caracteres
				texto += licitacion.getLicitacion();
			}
			vocabulario = v.vocabulario("sustantivo", texto);
			Arrays.sort(vocabulario); //se ordenan los elementos del arreglo Palabras por orden alfabético
			Set<String> todos = new HashSet<String>();
			for (String s : vocabulario) {
			      todos.add(s);
			}
			String[] vocabularioFinal = todos.toArray(new String[0]);
			
   System.out.println("VOCABULARIO =>"+vocabularioFinal.length);
   			for (String s : vocabularioFinal) {
   				System.out.println("=>"+s);
   			}
			/*
			 * Se cierra la conexión y la declaración a la base de datos
			 * */
			stmt.close();
			con.close();

			return vocabularioFinal;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		return null;

	}

	/**
	 * 
	 * @return
	 */
	public String[] terminos() {


		String driver = "org.postgresql.Driver"; //se declara una cadena de texto con el driver del motor de base de datos
		String connectString = "jdbc:postgresql://localhost/vm31ene2014"; //se declara una cadena para hacer la conexión a la base de datos
		String user = "postgres"; //se declara una cadena para conectar con el usuario del motor de base de datos
		String password = "12345"; //se declara una cadena con la contraseña del motor
		
		Licitacion licitacion = new Licitacion(); // se declara un nuevo objeto de la clase Licitación
		String cadena = ""; //se declara una nueva cadena vacía
		String[] vocabulario = null; //se declara un nuevo arreglo de cadenas vacío
		String[] terminos = null; //se declara otro arreglo de cadenas vac´´io
		String[] palabras; //se declara otro arreglo de cadenas
		Vocabulario v = new Vocabulario(); //Se declara un nuevo objeto de la clase Vocabulario
		
		try {
			Class.forName(driver); //se llama al driver del motor de base de datos
			Connection con = DriverManager.getConnection(connectString, user, password); //Se hace la conexión a la base de datos
			Statement stmt = con.createStatement(); //Se crea un objeto de clase Statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion LIMIT 10"); //se ejecuta la consulta a la base de datos

			/*
			 * Lo que se hace mientras se recorre la base
			 * de datos
			 * */
			while (rs.next()) {
				licitacion.setCodigo(rs.getString("id_licitacion"));
				licitacion.setLicitacion(rs.getString("nombre"));
				licitacion.removerCaracteres(); //para remover caracteres especiales
				licitacion.removerAcentos(); //para remover acentos
				licitacion.convertirAMinuscula(); //para convertir a mayúsculas
				licitacion.setLicitacion(licitacion.quitaEspacios(licitacion
						.getLicitacion())); //Se quitan espacios generados por la remoción de caracteres
				
				vocabulario = v.vocabulario("sustantivo", licitacion.getLicitacion()); //se pasa por parámetro el tipo de término a extraer y la licitación actual
				
				/*
				 * Se recorre los elementos del arreglo vocabulario
				 * */
				for(int i = 0; i < vocabulario.length; i++){
					cadena += vocabulario[i] + " "; //se concatena con cada uno de los elementos del arreglo Vocabulario
				}
			}
			
			palabras = cadena.split(" "); //se declara el arreglo de String Palabras separando los elementos del objeto Cadena
			Arrays.sort(palabras); //se ordenan los elementos del arreglo Palabras por orden alfabético

			String[] anuladas = new String[palabras.length]; // se llama a un nuevo arreglo de Strings para anular las palabras repetidas

			/*
			 * para evitar cualquier inconveniente,
			 * se asigna cada elemento del
			 * arreglo palabras al arreglo anuladas
			 */
			
			for (int i = 0; i < anuladas.length; i++) {
				anuladas[i] = palabras[i];
			}

			/*
			 * Para anular las palabras repetidas
			 */
			
			for (int i = 0; i < anuladas.length; i++) {
				for (int j = i + 1; j < palabras.length; j++) {
					if (anuladas[j].equals(anuladas[i])) { // si hay una palabra repetida
						anuladas[j] = ""; // se anula la palabra repetida
					}
				}
			}

			/*
			 * Para eliminar palabras anuladas
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

			/*
			 * Se cierra la conexión y la declaración a la base de datos
			 * */
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		return terminos;

	}
/**
 * 
 * @return
 */
	public String[] licitaciones() {

		Licitacion licitacion = new Licitacion();
		String driver = "org.postgresql.Driver";
		String connectString = "jdbc:postgresql://localhost/vm31ene2014";
		String user = "postgres";
		String password = "12345";
		String[] licitaciones = null;
		//int contador = 0;
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connectString, user,
					password);
			Statement stmt = con.createStatement();
//			ResultSet rs = stmt
//					.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion LIMIT 10");
//
//			while (rs.next()) {
//				licitacion.setCodigo(rs.getString("id_licitacion"));
//				licitacion.setLicitacion(rs.getString("nombre"));
//				System.out.println(licitacion.getLicitacion());
//				contador++;
//			}
			licitaciones = new String[10];
			
			ResultSet rs1 = stmt
					.executeQuery("SELECT * FROM licitacion ORDER BY id_licitacion LIMIT 10");
			int cont = 0;
			while (rs1.next()) {
				licitacion.setCodigo(rs1.getString("id_licitacion"));
				licitacion.setLicitacion(rs1.getString("nombre"));
				licitacion.convertirAMinuscula();
				licitacion.removerAcentos();
				licitacion.removerCaracteres();
				licitacion.setLicitacion(licitacion.quitaEspacios(licitacion.getLicitacion()));
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
/**
 * 
 * @param texto
 * @param termino
 * @return
 */
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
/**
 * 
 * @return
 */
	public double[][] matrizTerminos() {

		String[] terminos = this.terminos();
		String[] licitaciones = this.licitaciones();
		System.out.println(terminos.length);
		System.out.println(licitaciones.length);
		
			double[][] matriz = new double[terminos.length][licitaciones.length];
			
			for (int i = 0; i < terminos.length; i++) {
				for(int j = 0; j < licitaciones.length; j++) {
					matriz[i][j] = this.ocurrencia(licitaciones[j],
							terminos[i]);
				}
				
			}
			
			return matriz;
		
	}
/**
 * 
 * @return
 */
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
	/**
	 * 
	 * @return
	 */
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
		System.out.println("CALCULOS DE TERMINOS");
		String[] terms = this.terminosVocabulario();
		System.out.println("LLENAR MATRIZ ESCALA");
		double[] escalas = this.escalas();
		System.out.println("LLENAR MATRIZ TERMINOS VECTOR");
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
			System.out.println("¡Oh-oh! Algo salió mal");
			System.out.print(e.getMessage());
		}
	}

}
