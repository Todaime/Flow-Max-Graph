
package ro;

import org.graphstream.graph.*;

import org.graphstream.graph.implementations.*;
import ro.*;

import java.util.*;
public class Flow {
  public static void main(String args[]){
    Graph graph = new SingleGraph("G");

    addNodes(graph);
    addEdges(graph);

    AlgoFF flow = new AlgoFF();
    flow.init(graph,"S","Puit");
    setCapacitys(flow);

    flow.compute();
    addAttributes(graph,flow);


    graph.display();
    affichageGraphique(graph,flow);
}

public static void affichageGraphique(Graph graph,AlgoFF flow){
    System.out.println("Flot Maximal determiné : "+flow.getMaximumFlow());
    for (Node n1:graph){
      for (Node n2:graph){
          if (n1.hasEdgeToward(n2.getId())){
            System.out.println(n1.getId()+"->"+n2.getId()
              +" Capacité :"+flow.getCapacity(n1.getId(),n2.getId())+" / Flot :"
              +flow.getFlow(n1.getId(),n2.getId()));
          }
      }
    }
}


public static void setCapacitys(AlgoFF flow){
  flow.setCapacity("S","A",6);
  flow.setCapacity("S","B",7);
  flow.setCapacity("S","C",8);
  flow.setCapacity("A","D",4);
  flow.setCapacity("A","E",2);
  flow.setCapacity("B","D",3);
  flow.setCapacity("B","E",5);
  flow.setCapacity("B","F",2);
  flow.setCapacity("C","B",3);
  flow.setCapacity("C","H",4);
  flow.setCapacity("C","F",1);
  flow.setCapacity("D","G",5);
  flow.setCapacity("D","H",1);
  flow.setCapacity("D","Puit",3);
  flow.setCapacity("E","D",4);
  flow.setCapacity("E","H",1);
  flow.setCapacity("F","I",9);
  flow.setCapacity("G","Puit",3);
  flow.setCapacity("H","I",1);
  flow.setCapacity("H","Puit",6);
  flow.setCapacity("I","Puit",10);
}

public static void addNodes(Graph graph){
  graph.addNode("S");
  graph.addNode("A");
  graph.addNode("B");
  graph.addNode("C");
  graph.addNode("D");
  graph.addNode("E");
  graph.addNode("F");
  graph.addNode("G");
  graph.addNode("H");
  graph.addNode("I");
  graph.addNode("Puit");
}

public static void addEdges(Graph graph){
  graph.addEdge("S - A","S","A",true);
  graph.addEdge("S - B","S","B",true);
  graph.addEdge("S - C","S","C",true);
  graph.addEdge("A - D","A","D",true);
  graph.addEdge("A - E","A","E",true);
  graph.addEdge("B - D","B","D",true);
  graph.addEdge("B - E","B","E",true);
  graph.addEdge("B - F","B","F",true);
  graph.addEdge("C - B","C","B",true);
  graph.addEdge("C - H","C","H",true);
  graph.addEdge("C - F","C","F",true);
  graph.addEdge("D - G","D","G",true);
  graph.addEdge("D - H","D","H",true);
  graph.addEdge("D - Puit","D","Puit",true);
  graph.addEdge("E - D","E","D",true);
  graph.addEdge("E - H","E","H",true);
  graph.addEdge("F - I","F","I",true);
  graph.addEdge("G - Puit","G","Puit",true);
  graph.addEdge("H - I","H","I",true);
  graph.addEdge("H - Puit","H","Puit",true);
  graph.addEdge("I - Puit","I","Puit",true);
}

public static void addAttributes(Graph graph,AlgoFF flow){
  for (Node node:graph){
    node.addAttribute("ui.style", "shape:circle;fill-color: blue;size: 20px; text-alignment: center;");
    node.addAttribute("ui.label", node.getId());
    for (Node n2:graph){
        if (node.hasEdgeToward(n2.getId())){
          node.getEdgeToward(n2.getId()).addAttribute("ui.label","["+
          flow.getFlow(node,n2)+"/"+flow.getCapacity(node,n2)+"]");
        }
    }
  }
  graph.getNode("S").addAttribute("ui.style", "shape:circle;fill-color: purple;size: 25px; text-alignment: center;");
  graph.getNode("Puit").addAttribute("ui.style", "shape:circle;fill-color: purple;size: 25px; text-alignment: center;");
}

}
