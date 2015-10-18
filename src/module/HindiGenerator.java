package module;

import java.util.ArrayList;
import module.graph.helper.GraphPassingNode;
import module.graph.SentenceToGraph;

public class HindiGenerator {
	 public static String hinGenerator(GraphPassingNode gpn){
	        String translation = new String();
	       
	        GenNode hin = GenNodeConverter.genNodeConverter(gpn);
	        translation = GenerateHinSent(hin);
	        return translation;
	    }

	    private static String GenerateHinSent(GenNode hin) {
	        // Working on it
	        return null;
	    }
	    public static void main(String[] args) {
	        // TODO Auto-generated method stub
	        SentenceToGraph s= new SentenceToGraph();
	        GraphPassingNode gpn = s.extractGraph("My name is apple.", false, true,false);
	    
	        GraphPassingNode newgpn = Convert.convert(gpn, "hin");
	       
	        
	        for(String k:newgpn.getAspGraph()){
	    		System.out.println(k);
	    		}
	        
	        
	    }

}
