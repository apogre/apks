package module;

import module.graph.SentenceToGraph;
import module.graph.helper.GraphPassingNode;

public class TestKParser {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SentenceToGraph s= new SentenceToGraph();
        GraphPassingNode gpn = s.extractGraph("My name is apple.", false, true,false);
        for(String k:gpn.getAspGraph()){
            System.out.println(k);
        }
    }

}