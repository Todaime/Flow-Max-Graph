package ro;

import org.graphstream.algorithm.flow.*;
import org.graphstream.graph.*;
import java.util.*;
import ro.*;

public class AlgoFF extends FlowAlgorithmBase{
  private int valeur_un = 1;
  private boolean fin = false;

  public void compute(){
    System.out.println("Algorithme FF : Source S , target t");
    Node S = flowGraph.getNode("S");
    ArrayList<Node>  successeur_origine = successeurs(flowGraph,S);
    ArrayList<Node> chemin = new ArrayList();
    while (trouverChaineAmeliorante(chemin,S)){
      double delta = capaciteMin(chemin);
      majChemin(chemin,delta);
      System.out.print("Chaine améliorante : [");
      for (Node noeudChemin:chemin){
        System.out.print(noeudChemin.getId()+" ");
      }
      System.out.println("]");
      System.out.println("Min res capacité :"+delta);
      System.out.println("");
      chemin.clear();
    }
    for (Node successeur:successeur_origine){
      maximumFlow += getFlow(S,successeur);
    }
  }


  public boolean trouverChaineAmeliorante(ArrayList<Node> chemin,Node noeud){
    chemin.add(noeud);
    if (noeud == flowGraph.getNode("Puit")){
      fin = true;
      return true;
    }
    List<Node> liste_noeuds_suc = successeurs(flowGraph,noeud);
    List<Node> liste_noeuds_pred = predecesseurs(flowGraph,noeud);
    for(Node noeud_successeur:liste_noeuds_suc){
      if (!chemin.contains(noeud_successeur) && (getCapacity(noeud, noeud_successeur)-getFlow(noeud,noeud_successeur))>0){
        if (trouverChaineAmeliorante(chemin,noeud_successeur)==true){
          return true;
        }
      }
    }
    for(Node noeud_predecesseur:liste_noeuds_pred){
      if (!chemin.contains(noeud_predecesseur) && getFlow(noeud_predecesseur, noeud)>0){
        if (trouverChaineAmeliorante(chemin,noeud_predecesseur)==true){
          return true;
        }
      }
    }
    chemin.remove( chemin.size() - 1 );
    return false;
  }

  public double capaciteMin(ArrayList<Node> chemin){
    double delta = Integer.MAX_VALUE;
    for(int i=0;i<chemin.size()-1;i++){
      double augmentation = Integer.MAX_VALUE;
      List<Node> liste_noeuds_suc = successeurs(flowGraph,chemin.get(i));
      List<Node> liste_noeuds_pred = predecesseurs(flowGraph,chemin.get(i));
      if ( liste_noeuds_suc.contains(chemin.get(i+1))){
        augmentation = getCapacity(chemin.get(i), chemin.get(i+1)) - getFlow(chemin.get(i), chemin.get(i+1));
      }
      if (liste_noeuds_pred.contains(chemin.get(i+1))){
        augmentation = getFlow(chemin.get(i+1), chemin.get(i));
      }
      if (augmentation<delta){
        delta = augmentation;
      }
    }
    return delta;
  }

  public void majChemin(ArrayList<Node> chemin,double delta){
    for(int i=0;i<chemin.size()-1;i++){
      List<Node> liste_noeuds_suc = successeurs(flowGraph,chemin.get(i));
      List<Node> liste_noeuds_pred = predecesseurs(flowGraph,chemin.get(i));
      if (liste_noeuds_suc.contains(chemin.get(i+1))){
        double flowActuel = getFlow(chemin.get(i),chemin.get(i+1));
        setFlow(chemin.get(i),chemin.get(i+1), flowActuel+delta);
      }
      if (liste_noeuds_pred.contains(chemin.get(i+1))){
        double flowActuel = getFlow(chemin.get(i+1),chemin.get(i));
        setFlow(chemin.get(i+1),chemin.get(i), flowActuel-delta);
      }
    }
  }

  public ArrayList<Node> predecesseurs(Graph g, Node sommet) {
      ArrayList<Node> preds = new ArrayList<Node>();
      for (Node n:g){
        if (n.hasEdgeToward(sommet.getId())) {
          preds.add(n);
        }
      }
      return preds;
    }

    public ArrayList<Node> successeurs(Graph g, Node sommet) {
      ArrayList<Node> succs = new ArrayList<Node>();
      for (Node n:g){
        if (sommet.hasEdgeToward(n.getId())) {
          succs.add(n);
        }
      }
      return succs;
    }

}
