import java.util.ArrayList;
import java.util.Arrays;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class Vocabulario {

	private Licitacion licitacion;

	public Vocabulario() {
	}
	
	public Vocabulario(Licitacion licitacion) {
		this.licitacion = licitacion;
	}

	public Licitacion getLicitacion() {
		return licitacion;
	}

	public void setLicitacion(Licitacion licitacion) {
		this.licitacion = licitacion;
	}
	
	public ArrayList<String> vocabulario(String opcion){	
		this.licitacion.removerCaracteres(); //para remover caracteres especiales
		this.licitacion.convertirAMinuscula(); //para convertir a minúscila
		this.licitacion.removerAcentos(); //para remover acentos
		this.licitacion.setLicitacion(this.licitacion.quitaEspacios(this.licitacion.getLicitacion())); //para quitar espacios generados al quitar caracteres
		
		String vocabulario = "";
		
		// TODO Auto-generated method stub

		// Initialize the tagger
        MaxentTagger tagger = new MaxentTagger(
                "taggers/spanish-distsim.tagger");
        
     // The sample string
        String sample;

        // The tagged string
        String tagged;
        
        // Output the result
        // System.out.println(tagged);
        String[] palabras;
        ArrayList<String> list = new ArrayList<String>();  
 
        
        if(opcion.equals("sustantivo")){
        	
        	// The sample string
            sample = this.licitacion.getLicitacion().replaceAll("\\W", " ");

            // The tagged string
            tagged = tagger.tagTokenizedString(sample);
            
            // Output the result
            // System.out.println(tagged);
            palabras = tagged.split(" ");
            Arrays.sort(palabras); //ordena el arreglo de Strings por orden alfabético
            list = new ArrayList<String>();  
        	
        	for(int i=0;i<palabras.length;i++)
            {
                if (palabras[i].substring(palabras[i].lastIndexOf("_")+1).startsWith("n"))
                {
                    list.add(palabras[i].split("_")[0]);
                }
            }
            for(int i=0;i<list.size();i++)
            {
                vocabulario += list.get(i) + "\n";
            }
        }else if(opcion.equals("adjetivo")){
        	
        	// The sample string
            sample = this.licitacion.getLicitacion().replaceAll("\\W", " ");

            // The tagged string
            tagged = tagger.tagTokenizedString(sample);
            
            // Output the result
            // System.out.println(tagged);
            palabras = tagged.split(" ");
            Arrays.sort(palabras); //ordena el arreglo de Strings por orden alfabético
            list = new ArrayList<String>();  
        	
        	for(int i=0;i<palabras.length;i++)
            {
                if (palabras[i].substring(palabras[i].lastIndexOf("_")+1).startsWith("a"))
                {
                    list.add(palabras[i].split("_")[0]);
                }
            }
            for(int i=0;i<list.size();i++)
            {
                vocabulario += list.get(i) + "\n";
            }
        }else if(opcion.equals("verbo")){
        	
        	// The sample string
            sample = this.licitacion.getLicitacion().replaceAll("\\W", " ");

            // The tagged string
            tagged = tagger.tagTokenizedString(sample);
            
            // Output the result
            // System.out.println(tagged);
            palabras = tagged.split(" ");
            Arrays.sort(palabras); //ordena el arreglo de Strings por orden alfabético
            list = new ArrayList<String>();  
        	
        	for(int i=0;i<palabras.length;i++)
            {
                if (palabras[i].substring(palabras[i].lastIndexOf("_")+1).startsWith("v"))
                {
                    list.add(palabras[i].split("_")[0]);
                }
            }
            for(int i=0;i<list.size();i++)
            {
                vocabulario += list.get(i) + "\n";
            }
        }else {
    		this.licitacion.eliminarStopWords();
        	
    		// The sample string
            sample = this.licitacion.getLicitacion().replaceAll("\\W", " ");

            // The tagged string
            tagged = tagger.tagTokenizedString(sample);
            
            // Output the result
            // System.out.println(tagged);
            palabras = tagged.split(" ");
            Arrays.sort(palabras); //ordena el arreglo de Strings por orden alfabético
            list = new ArrayList<String>();  
    		
    		for(int i=0;i<palabras.length;i++)
            {
        		list.add(palabras[i].split("_")[0]);
            }
    		/*for(int i=0;i<list.size();i++)
            {
                vocabulario += list.get(i) + "\n";
            }*/
        }
        return list;

	}
	
	
}
