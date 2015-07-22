import java.util.ArrayList;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class Tagger {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Initialize the tagger
        MaxentTagger tagger = new MaxentTagger(
                "taggers/spanish-distsim.tagger");
 
     // The sample string
        String s = "Mi madre es una hermosa mujer.";
        String sample = s.replaceAll("\\W", " ");

        // The tagged string
        String tagged = tagger.tagTokenizedString(sample);
        System.out.println(tagged);
        
        // Output the result
        // System.out.println(tagged);
        String[] x = tagged.split(" ");
        ArrayList<String> list = new ArrayList<String>();  

        for(int i=0;i<x.length;i++)
        {
            if (x[i].substring(x[i].lastIndexOf("_")+1).startsWith("a"))
            {
                list.add(x[i].split("_")[0]);
            }
        }
        for(int i=0;i<list.size();i++)
        {
            System.out.println(list.get(i));
        }
		
	}

}
