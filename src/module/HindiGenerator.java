package module;

import java.util.ArrayList;
import module.graph.helper.GraphPassingNode;

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

}
