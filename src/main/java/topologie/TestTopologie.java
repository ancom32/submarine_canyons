package topologie;

import java.util.ArrayList;
import java.util.HashMap;


import com.google.common.collect.Multimap;
/** 
 * <b>Classe TestTopologie (static) </b>
 * <p> 
 * Une classe static qui contient des methodes pour tester la construction d'un objet Topologie. Les 
 * methodes retournent differentes information et sortie dans la console.           
 * @author Andrés Camilo Cortés Murcia
 * @since 2015-08-06
 *
 */
public class TestTopologie {
	
	public static ArrayList<Integer> preuveTrous= new ArrayList<Integer>(); 
	
	 /**
     * <b>Test : </b>
     * <p>
     * Il permet d'afficher la structure de la topologie. À partir du tableau triangle, on peut visualiser les relations de chaque objet.
     * @param topo L'instance de la classe topologie.
     */
    public static void StructureTriangle(Topologie topo){
    	System.out.println("\t"+"S1"+"\t"+"S2"+"\t"+"S3"+"\t"+"A1"+"\t"+"A2"+"\t"+"A3");
    	HashMap <Integer,Triangles> myTriangle=topo.getTblTriangles();
    	for(int i=1;i<=myTriangle.size();i++){
    		System.out.print("\t"+myTriangle.get(i).getId());
    		System.out.print("\t"+myTriangle.get(i).getS1());
    		System.out.print("\t"+myTriangle.get(i).getS2());
    		System.out.print("\t"+myTriangle.get(i).getS3());
    		System.out.print("\t"+myTriangle.get(i).getA1());
    		System.out.print("\t"+myTriangle.get(i).getA2());
    		System.out.print("\t"+myTriangle.get(i).getA3()+"\n");
    	}	    	
    }
    /**
     * <b>Test : </b>
     * <p>
     * Il permet d'afficher la structure du tableau arête.
     * @param topo L'instance de la classe topologie.
     */
    public static void structureArete(Topologie topo){
    	HashMap<Integer, Aretes> myEdge=topo.getTblAretes();
    	System.out.println("\t"+"S1"+"\t"+"S2");
    	for(int i=1;i<=myEdge.size();i++){
    		System.out.print("\t"+myEdge.get(i).getId());
    		System.out.print("\t"+myEdge.get(i).getS1());
    		System.out.print("\t"+myEdge.get(i).getS2()+"\n");
    	}        	
    }
    /**
     * <b>Test : </b>
     * <p>
     * Il permet d'afficher la structure du tableau point.
     * @param topo L'instance de la classe topologie.
     */
    public static void structurePoint(Topologie topo){
    	HashMap<Integer, Points> myPoint=topo.getTblSommets();
    	System.out.println("\t"+"X"+"\t"+"Y"+"\t"+"Z");
    	for(int i=1;i<myPoint.size();i++){
    		System.out.print("\t"+myPoint.get(i).getId());
    		System.out.print("\t"+myPoint.get(i).getX());
    		System.out.print("\t"+myPoint.get(i).getY());
    		System.out.print("\t"+myPoint.get(i).getZ()+"\n");
    	} 
    }
    /**
     * <b>Test : </b>
     * <p>
     * Il permet d'afficher la structure du tableau point-triangle.
     * @param topo L'instance de la classe topologie.
     */
    public static void structurePointTriangle(Topologie topo){
    	HashMap<Integer, Points> myPoint=topo.getTblSommets();
    	Multimap<Integer, Integer> myPointTriangle=topo.getTblSommetsTriangles();
    	for (int i=1;i< myPoint.size();i++) {
    		System.out.println("\t"+myPointTriangle.get(i)+"\n");
			
		}
    }
    
    /**
     * <b>Test : </b>
     * <p>
     * Il permet d'afficher la structure du tableau point-arête.
     * @param topo L'instance de la classe topologie.
     */
    public static void structurePointArete(Topologie topo){
    	HashMap<Integer, Points> myPoint=topo.getTblSommets();
    	Multimap<Integer, Integer> myPointArete=topo.getTblSommetsAretes();
    	for (int i=1;i< myPoint.size();i++) {
    		System.out.println("\t"+i+myPointArete.get(i)+"\n");
			
		}
    }
    
    /**
     * <b>Test : </b>
     * <p>
     * Il permet d'afficher les point qui se trouve à la bordure et des points qui correspondent à trous.
     * @param topo L'instance de la classe topologie.
     */
    public static void obtenirPointsBordure(Topologie topo){
    	System.out.println("Liste de points au bordure "+topo.getTblsSommetsBordure());
    	System.out.println("Warning trous bordure "+preuveTrous);
    }
    

}
