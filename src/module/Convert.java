package module;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONObject;

import module.graph.SentenceToGraph;
import module.graph.helper.GraphPassingNode;
import module.graph.helper.JAWSutility;

public class Convert {
	
	//private ArrayList<String> test = new ArrayList();
	
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SentenceToGraph s= new SentenceToGraph();
        GraphPassingNode gpn = s.extractGraph("My name is apple.", false, true,false);
    
        GraphPassingNode newgpn = convert(gpn, "hin");
       
        
        for(String k:newgpn.getAspGraph()){
    		System.out.println(k);
    		}
        
        
    }
    
    public static GraphPassingNode convert(GraphPassingNode gpn, String lang){
    	ArrayList<String> transAspGraph = new ArrayList<String>();
    	for(String k:gpn.getAspGraph()){
    		//System.out.println(k);
    		String processStr = k.replace("has(", "");
			processStr = processStr.replace(").","");
			String rules[] = processStr.split(",");
			String roots[] = rules[0].split("-");
			String roles[] = rules[2].split("-");
			String knew = k;
			String knew2 = null;
			JAWSutility j = new JAWSutility();
			HashMap<String, String> posMap = gpn.getposMap();
			
			//System.out.println(j.getBaseForm("birds", "n"));
			if(rules[1].equals("instance_of")||rules[1].equals("is_subclass_of")||rules[1].equals("semantic_role")){
				
			}else{
				String left = roots[0];
				String right = roles[0];
				
				try {
					String pos1 = posMap.get(rules[0]);
					//System.out.println(pos1);
					
					if(pos1.equals("NNS")||pos1.equals("NNPS")){
						String single = j.getBaseForm(roots[0],"n");
						String single1 = translate(single.toLowerCase(), lang);
						knew2 = "has("+single1+"-"+roots[1]+","+pos1+","+left+"-"+roots[1]+").";
						//System.out.println(knew2);
						if(transAspGraph.indexOf(knew2)==-1){
							transAspGraph.add(knew2);
						}
						/*
						if(single.equals(roots[0])){
							single = j.getBaseForm(roots[0],"v");
						
							
						}
						
						*/
						roots[0]=single;
					}
					
					else if(pos1.equals("VBD")||pos1.equals("VBG")||
							pos1.equals("VBN")||pos1.equals("VBP")||pos1.equals("VBZ")){
						roots[0] = j.getBaseForm(roots[0],"v");
						String single1 = translate(roots[0].toLowerCase(), lang);
						knew2 = "has("+single1+"-"+roots[1]+","+pos1+","+left+"-"+roots[1]+").";
						//System.out.println(knew2);
						if(transAspGraph.indexOf(knew2)==-1)
							transAspGraph.add(knew2);
					
					}
					
					
					left = translate(roots[0].toLowerCase(), lang);
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					String pos2 = posMap.get(rules[2]);
					if(pos2.equals("null")||pos2.equals("NNS")){
						roles[0] = j.getBaseForm(roles[0],"n");
						String single1 = translate(roles[0].toLowerCase(), lang);
						knew2 = "has("+single1+"-"+roles[1]+","+pos2+","+right+"-"+roles[1]+").";
						//System.out.println(knew2);
						if(transAspGraph.indexOf(knew2)==-1)
							transAspGraph.add(knew2);
						
					}else if(pos2.equals("VBD")||pos2.equals("VBG")||
							pos2.equals("VBN")||pos2.equals("VBP")||pos2.equals("VBZ")){
						roles[0] = j.getBaseForm(roles[0],"v");
						String single1 = translate(roles[0].toLowerCase(), lang);
						knew2 = "has("+single1+"-"+roles[1]+","+pos2+","+right+"-"+roles[1]+").";
						//System.out.println(knew2);
						if(transAspGraph.indexOf(knew2)==-1)
							transAspGraph.add(knew2);
						
						
					}
					right = translate(roles[0].toLowerCase(), lang);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				knew = "has("+left+"-"+roots[1]+","+rules[1]+","+right+"-"+roles[1]+").";
				//System.out.println(knew);
			}
			transAspGraph.add(knew);
    	}
    	GraphPassingNode newgpn = new GraphPassingNode(transAspGraph,gpn.getposMap(),gpn.getSentence(),gpn.getWordSenseMap());
    	
    	return newgpn;
    }
    
    //Function to translate each word with id e.g run - 4 in target language
    public static String translate(String phrase, String lang) throws Exception
    {
    	 JSONObject phras;
         String res1;
         
        // build a URL
    	StringBuilder sb = new StringBuilder();
    	sb.append("https://glosbe.com/gapi/translate?from=eng&dest=");
    	
    	//As of now, change language manually
    	//String lang = "hin"; //"jpn"
    	
    	sb.append(lang);
    	sb.append("&format=json&phrase=");
    	//String phrase = "cat";
    	sb.append(phrase);
    	sb.append("&pretty=true");
        String s = sb.toString();
        //s += URLEncoder.encode(addr, "UTF-8");
        URL url = new URL(s);
     
        // read from the URL
        Scanner scan = new Scanner(url.openStream());
        String str = new String();
        while (scan.hasNext())
            str += scan.nextLine();
        scan.close();
     
        // build a JSON object
        JSONObject obj = new JSONObject(str);
        if (! obj.getString("result").equals("ok"))
        { System.out.println("Object not found"); return null;}
            //return;
     
        // get the first result
        JSONObject res = obj.getJSONArray("tuc").getJSONObject(0);
        
       
        
        if(res.getJSONObject("phrase") !=null ){
        	 phras = res.getJSONObject("phrase");
	         res1 = phras.getString("text");
        }
        else 
        	res1 = null;
        	
		return res1;
		
    }

}

