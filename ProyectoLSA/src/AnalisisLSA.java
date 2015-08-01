import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.sql.*;

import com.aliasi.matrix.SvdMatrix;

public class AnalisisLSA {

	public AnalisisLSA() {
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
			ResultSet rs = stmt
				.executeQuery("SELECT id,id_licitacion,nombre FROM licitacion ORDER BY id");

			
			licitaciones = new String[27204];
			
			int cont = 0;
			while (rs.next()) {
				licitacion.setCodigo(rs.getString("id_licitacion"));
				licitacion.setId(rs.getInt("id"));
				// OTRO SELECT PARA TRAER EL TEXTO DE LOS PRODYUCTOS /SERVICIOS
				String textoLicitacion = "";
				
				System.out.println("SELECT  nombre,descripcion FROM producto_servicio "
						+ "	WHERE licitacion_id='"+licitacion.getId()+"' ORDER BY id ");
				Statement stmtProductoServicio = con.createStatement(); 
				ResultSet rsProdServ = stmtProductoServicio.executeQuery("SELECT  nombre,descripcion FROM producto_servicio "
						+ "	WHERE licitacion_id='"+licitacion.getId()+"' ORDER BY id");
				while (rsProdServ.next()) {
					textoLicitacion += rsProdServ.getString("nombre")+ " "+ rsProdServ.getString("descripcion");
				}
				stmtProductoServicio.close();
			        
				textoLicitacion = rs.getString("nombre") + " " + textoLicitacion;
				licitacion.setLicitacion(textoLicitacion);
				licitacion.convertirAMinuscula();
				licitacion.removerAcentos();
				licitacion.removerCaracteres();
				licitacion.setLicitacion(licitacion.quitaEspacios(licitacion.getLicitacion()));
				licitaciones[cont] = licitacion.getLicitacion();
				System.out.println(cont);
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
	 * Este método retorna un arreglo de String
	 * con los términos de un vocabulario según
	 * el tipo de término que se le asigne
	 * (ej. sustantivo, adjetivo, verbo)
	 * 
	 * @return
	 */
		
	public String[] terminosVocabulario(String[] licitaciones) {

		
		String[] vocabulario = null; //se declara un nuevo arreglo de cadenas vacío
		Vocabulario v = new Vocabulario(); //Se declara un nuevo objeto de la clase Vocabulario
		String texto = "";
		
		for (int i = 0; i < licitaciones.length; i++) {
			texto += licitaciones[i] + " ";
			System.out.println(licitaciones[i]);
		}
		
		vocabulario = v.vocabulario("sustantivo", texto);
		Arrays.sort(vocabulario); //se ordenan los elementos del arreglo Palabras por orden alfabético
		Set<String> todos = new HashSet<String>();
			
		for (String s : vocabulario) {
			todos.add(s);
			System.out.println(s);
		}
			
		String[] vocabularioFinal = todos.toArray(new String[0]);
			
		System.out.println("VOCABULARIO =>"+vocabularioFinal.length);
		for (String s : vocabularioFinal) {
			System.out.println("=>"+s);
			System.out.println(s);
		}
			/*
			 * Se cierra la conexión y la declaración a la base de datos
			 * */
		return vocabularioFinal;

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
	public double[][] matrizTerminos(String[] terms, String[] lics) {

		String[] terminos = terms;
		String[] licitaciones = lics;
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
	public SvdMatrix matrizSVD(String[] terms, String[] lics) {

		int numFactors = 2;
		double featureInit = 0.01;
		double initialLearningRate = 0.005;
		int annealingRate = 1000;
		double regularization = 0.00;
		double minImprovement = 0.0000;
		int minEpochs = 10;
		int maxEpochs = 50000;
		double[][] matriz = this.matrizTerminos(terms, lics);

		SvdMatrix matrix = SvdMatrix.svd(matriz, numFactors,
				featureInit, initialLearningRate, annealingRate,
				regularization, null, minImprovement, minEpochs, maxEpochs);

		
		return matrix;
	}
	/**
	 * 
	 * @return
	 */
	public double[] escalas(String[] terms, String[] lics) {

		SvdMatrix matriz = this.matrizSVD(terms, lics);
		double[] escalas = matriz.singularValues();
		
		return escalas;
	}
	
	public double[][] vectorTermLSA(String[] terms, String[] lics) {

		SvdMatrix matriz = this.matrizSVD(terms, lics);
		
		double[][] termVectors = matriz.leftSingularVectors();
		
		return termVectors;
	}
	
	public double[][] vectorDocLSA(String[] terms, String[] lics) {

		SvdMatrix matriz = this.matrizSVD(terms, lics);
		
		double[][] docVectors = matriz.rightSingularVectors();
		
		return docVectors;
	}
	
	
	
	
	public void cosenoTerms(){
		
		String driver = "org.postgresql.Driver";
		String connectString = "jdbc:postgresql://localhost/bd_semantica";
		String user = "postgres";
		String password = "12345";
		;
		System.out.println("Licitaciones...");
		String[] lics = this.licitaciones();
		System.out.println("CALCULOS DE TERMINOS");
		String[] terms = this.terminosVocabulario(lics);
		System.out.println("Escalas...");
		double[] escalas = this.escalas(terms, lics);
		System.out.println("LLENAR MATRIZ TERMINOS VECTOR");
		double[][] terminosVectores = this.vectorTermLSA(terms, lics);
		double cos = 0;
		double prod_cruz = 0;
		
		double[] xs = new double[2];
		double[] ys = new double[2];		
		
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connectString, user,
					password);
			Statement stmt = con.createStatement();

			PreparedStatement dropTable = con.prepareStatement(
					String.format("DROP TABLE IF EXISTS vector_coseno CASCADE"));
			dropTable.execute();
			
			String sql = "CREATE TABLE vector_coseno"
					+ "( id serial NOT NULL,"
					+ "palabra1 character varying(255) NOT NULL,"
					+ "palabra2 character varying(255),"
					+ "coseno double precision NOT NULL,"
					+ "producto_cruz double precision NOT NULL,"
					+ "CONSTRAINT id PRIMARY KEY (id) )";
			
			stmt.executeUpdate(sql);
			
			String stm = "INSERT INTO vector_coseno" +
            		" (palabra1, palabra2, coseno, producto_cruz) " +
            		" VALUES(?, ?, ?, ?)";
			PreparedStatement pst = con.prepareStatement(stm);
			for(int i = 0; i < terms.length; i++){
				for(int j = i+1; j < terms.length; j++){
					xs[0] = terminosVectores[i][0];
					xs[1] = terminosVectores[j][0];
					ys[1] = terminosVectores[i][1];
					ys[0] = terminosVectores[j][1];
					cos = CalculoVector.cosine(xs, ys, escalas);
					prod_cruz = CalculoVector.dotProduct(xs, ys, escalas);
					if(cos > 0.5) {
						System.out.println("Coseno/P.Cruz entre " + terms[i] + " - " + terms[j] + " = " + cos +"  " + prod_cruz);
						pst.setString(1, terms[i]);
			            pst.setString(2, terms[j]);
			            pst.setDouble(3, cos);
			            pst.setDouble(4, prod_cruz);
			            pst.executeUpdate();
					}
					
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
