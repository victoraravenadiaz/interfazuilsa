import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;


public class Licitacion {
	private int id;
	private String codigo;
	private String licitacion;
	
	public Licitacion() {
	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	    String caracteres = ",.:+-'¡!¿?/()[]";
	    // Cadena de caracteres ASCII que reemplazarán los originales.
	    for (int i=0; i<caracteres.length(); i++) {
	        // Reemplazamos los caracteres especiales.
    		this.licitacion = this.licitacion.replaceAll("\\"+caracteres.charAt(i), " ");
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
	
	@Override
	public String toString() {
		return this.codigo + ": " + this.licitacion;
	}
	
}
