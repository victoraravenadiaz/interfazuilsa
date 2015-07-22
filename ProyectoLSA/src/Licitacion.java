import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;


public class Licitacion {

	private String codigo;
	private String licitacion;
	
	public Licitacion() {
	
	}

	public Licitacion(String codigo, String licitacion) {
		this.codigo = codigo;
		this.licitacion = licitacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getLicitacion() {
		return licitacion;
	}

	public void setLicitacion(String licitacion) {
		this.licitacion = licitacion;
	}
	
	public void convertirAMinuscula() {
		this.licitacion = this.licitacion.toLowerCase();
	}
	
	public void removerAcentos() {
		// Descomposición canónica
	    String normalized = Normalizer.normalize(this.licitacion, Normalizer.Form.NFD);
	    // Nos quedamos únicamente con los caracteres ASCII
	    Pattern pattern = Pattern.compile("\\P{ASCII}+");
	    this.licitacion = pattern.matcher(normalized).replaceAll("");
	}
	
	public void removerCaracteres() {
		 // Cadena de caracteres original a sustituir.
	    String caracteres = ",.:-'¡!¿?/()[]";
	    // Cadena de caracteres ASCII que reemplazarán los originales.
	    for (int i=0; i<caracteres.length(); i++) {
	        // Reemplazamos los caracteres especiales.
	        this.licitacion = this.licitacion.replaceAll("\\"+caracteres.charAt(i), "");

	    }
	}
	
	public String quitaEspacios(String texto) {
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(texto);
        texto = "";
        while(tokens.hasMoreTokens()){
            texto += " "+tokens.nextToken();
        }
        texto = texto.toString();
        texto = texto.trim();
        return texto;
    }
	
public void eliminarStopWords() {
		
		String content = null;
		File file = new File("src/stopwords.txt"); //ruta completa al fichero que deseamos leer
        try {
            //leyendo el archivo completo
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
           
            //cerramos el reader
            reader.close();
           
            //asignamos cada valor al nuevo vector (usamos como separador la coma)
            String[] vector = content.split("\r\n");
            
            //removemos tildes
            for(int i = 0; i < vector.length; i++){
            	// Descomposición canónica
        	    String normalized = Normalizer.normalize(vector[i], Normalizer.Form.NFD);
        	    // Nos quedamos únicamente con los caracteres ASCII
        	    Pattern pattern = Pattern.compile("\\P{ASCII}+");
        	    vector[i] = pattern.matcher(normalized).replaceAll("");
            }
            
            this.convertirAMinuscula();
    		this.removerAcentos();
            
            //ahora a remover
            for(int i = 0; i < vector.length; i++){
            	this.licitacion = this.licitacion.replaceAll("\\b"+vector[i]+"\\b", "");
            }
                
			this.setLicitacion(this.quitaEspacios(this.getLicitacion()));
                
        } catch (IOException e) {
                e.printStackTrace();
        }
	}
	
	public String ocurrencia(){		
			this.removerCaracteres();
			this.eliminarStopWords();
			this.removerAcentos();
			this.setLicitacion(this.quitaEspacios(this.getLicitacion()));
	        
	        String[] palabras = this.getLicitacion().split(" "); //convierte el String en un arreglo de Strings separándolo por espacios
	        Arrays.sort(palabras); //ordena el arreglo de Strings por orden alfabético
	        
	        /*
	         * Para anular palabras repetidas
	         */
	        
	        String[] anuladas = new String[palabras.length]; //se llama a un nuevo arreglo de Strings para anular las palabras repetidas
			int[] contadores = new int[palabras.length]; //se almacena en un arreglo de enteros las veces que se repite cada palabra
			
			for(int i = 0; i < anuladas.length; i++){
				anuladas[i] = palabras[i]; //para evitar cualquier inconveniente, se asigna cada elemento del arreglo palabras al arreglo anuladas
				
			}
	
			int contador = 1;
			
			for(int i = 0; i < anuladas.length; i++){
				for(int j = i+1; j < palabras.length; j++){
					if(anuladas[j].equals(anuladas[i])){ //si hay una palabra repetida
						contador++; //se aumenta a 1 el contador
						anuladas[j] = ""; //se anula la palabra repetida
					}
				}
				
				if(anuladas[i].equals("")){ //si la palabra está anulada por haber sido repetida
					contadores[i] = 0; //se anula a 0 el contador actual, ya que la palabra, si está repetida, ya ha sido contada
				}else{
					contadores[i] = contador; //se agrega el valor actual del contador 
				}
				
				contador = 1; //para el siguiente ciclo, el contador vuelve a 1
			}
			
			/*
			 * Para eliminar palabras anuladas repetidas
			 */
			
			String[] nuevoPalabras = new String[]{}; //se declara un arreglo de Strings nuevo, vacío
			
			for (int i = 0; i < palabras.length; i++) {
				String palabra = palabras[i]; //se declara un String con el elemento actual del arreglo de Strings palabra 
				boolean palabraYaExiste = false; //un boolean para determinar si la palabra ya existe
				
				for (int j = 0; j < nuevoPalabras.length; j++) {
					if (nuevoPalabras[j].equals(palabra)) { //si el elemento actual coincide con el String analizado
						palabraYaExiste = true; //palabraYaExiste se declara verdadero
						break;
					}
				}
				
				if (palabraYaExiste == false) { //si la palabra no existe
					
					String[] vectorTemp = new String[nuevoPalabras.length + 1]; //se declara un vector temporal
					
					for (int j = 0; j < nuevoPalabras.length; j++) {
						vectorTemp[j] = nuevoPalabras[j]; //a cada elemento del vector temporal se le asigna el arreglo actual
					}
					
					vectorTemp[nuevoPalabras.length] = palabra; //al último elemento del vector temporal se le asigna el String analizado 
					nuevoPalabras = vectorTemp; //el vector a poblar se iguala con el Vector Temporal
				}
			}
			
			/*
			 * Para eliminar los 0 del arreglo de contadores,
			 * cada 0 se refiere a las palabras repetidas ya contadas
			 */
			
			int[] nuevoContadores = new int[]{}; //se declara un arreglo vacío de números enteros
	
			for (int i = 0; i < contadores.length; i++) {
				int cont = contadores[i]; //se declara un entero con el elemento actual del arreglo de enteros que se analiza 
				boolean contadorEsCero = false; //se declara un booleano para ver si el contador actual es cero
				
				for (int j = 0; j < nuevoContadores.length; j++) {
					if (cont == 0) { //si el contador actual es cero
						contadorEsCero = true; //el booleano se declara verdadero
						break;
					}
				}
				
				if (contadorEsCero == false) { //si el booleano es falso
					
					int[] vectorTemp = new int[nuevoContadores.length + 1]; //se asigna un vector temporal de enteros
					
					for (int j = 0; j < nuevoContadores.length; j++) {
						vectorTemp[j] = nuevoContadores[j]; //a cada elemento del vector temporal se le asigna el actual del vector que se va rellenando
					}
					
					vectorTemp[nuevoContadores.length] = cont; //el contador actual es asignado al último elemento del arreglo temporal
					nuevoContadores = vectorTemp; //el arreglo temporal es asignado al arreglo vacío es asignado
				}
			}
			
			String ocurrencia = "Ocurrencia de palabras: \n";
			for(int i = 0; i < nuevoPalabras.length; i++){
				ocurrencia += nuevoPalabras[i] + ": " + nuevoContadores[i] + "\n";
			}
			
			return ocurrencia;
		}
	
	@Override
	public String toString() {
		return this.codigo + ": " + this.licitacion;
	}
	
}
