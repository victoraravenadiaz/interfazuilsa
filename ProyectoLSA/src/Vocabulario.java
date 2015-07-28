import java.util.ArrayList;
import java.util.Arrays;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class Vocabulario {

	public Vocabulario() {
	}
	
	public String[] vocabulario(String opcion, String texto){	
			
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
        ArrayList<String> lista = new ArrayList<String>();
		String[] vocabulario = null;
        
        if(opcion.equals("sustantivo")){
        	
        	// The sample string
            sample = texto.replaceAll("\\W", " ");

            // The tagged string
            tagged = tagger.tagTokenizedString(sample);
            
            // Output the result
            // System.out.println(tagged);
            palabras = tagged.split(" ");
            Arrays.sort(palabras); //ordena el arreglo de Strings por orden alfabético
        	
        	for(int i=0;i<palabras.length;i++)
            {
                if (palabras[i].substring(palabras[i].lastIndexOf("_")+1).startsWith("n"))
                {
                	lista.add(palabras[i].split("_")[0]);
                }
            }
        	
        	vocabulario = new String[lista.size()];
        	
        	for(int i=0;i<vocabulario.length;i++)
            {
                vocabulario[i] = lista.get(i);
            }
        }else if(opcion.equals("adjetivo")){
        	
        	// The sample string
            sample = texto.replaceAll("\\W", " ");

            // The tagged string
            tagged = tagger.tagTokenizedString(sample);
            
            // Output the result
            // System.out.println(tagged);
            palabras = tagged.split(" ");
            Arrays.sort(palabras); //ordena el arreglo de Strings por orden alfabético
        	
            for(int i=0;i<palabras.length;i++)
            {
                if (palabras[i].substring(palabras[i].lastIndexOf("_")+1).startsWith("n"))
                {
                	lista.add(palabras[i].split("_")[0]);
                }
            }
            
            vocabulario = new String[lista.size()];
        	
        	for(int i=0;i<vocabulario.length;i++)
            {
                vocabulario[i] = lista.get(i);
            }
        }else if(opcion.equals("verbo")){
        	
        	// The sample string
            sample =texto.replaceAll("\\W", " ");

            // The tagged string
            tagged = tagger.tagTokenizedString(sample);
            
            // Output the result
            // System.out.println(tagged);
            palabras = tagged.split(" ");
            Arrays.sort(palabras); //ordena el arreglo de Strings por orden alfabético

            for(int i=0;i<palabras.length;i++)
            {
                if (palabras[i].substring(palabras[i].lastIndexOf("_")+1).startsWith("n"))
                {
                	lista.add(palabras[i].split("_")[0]);
                }
            }
            
            vocabulario = new String[lista.size()];
        	
        	for(int i=0;i<vocabulario.length;i++)
            {
                vocabulario[i] = lista.get(i);
            }
        }else {
        	
    		// The sample string
            sample = texto.replaceAll("\\W", " ");

            // The tagged string
            tagged = tagger.tagTokenizedString(sample);
            
            // Output the result
            // System.out.println(tagged);
            palabras = tagged.split(" ");
            Arrays.sort(palabras); //ordena el arreglo de Strings por orden alfabético
    		
    		for(int i=0;i<palabras.length;i++)
            {
    			lista.add(palabras[i].split("_")[0]);
            }

    		vocabulario = new String[lista.size()];
        	
        	for(int i=0;i<vocabulario.length;i++)
            {
                vocabulario[i] = lista.get(i);
            }
        }
        return vocabulario;

	}
	
	
}
