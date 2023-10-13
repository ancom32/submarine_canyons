package vallees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

import surfaceNetwork.Col;
import surfaceNetwork.Crete;
import surfaceNetwork.Pic;
import surfaceNetwork.Puit;
import surfaceNetwork.Ravin;
import topologie.Aretes;
import topologie.Points;
import topologie.Topologie;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/** 
 * <b> Classe Versant </b>
 * <p> 
 * Cette classe represente le polygone de versant au tour du squelette candidat à canyon. 
 * Le polygone qui va être construit on l'appelera un versant bien qu'il soit composé par: (deux pics, un col, un puit)
 * cette classe herite de PointCritique.
 * 
 *                
 * @author Andrés Camilo Cortés Murcia.
 * @since 2015-08-11
 * 
 */

public class Versant {

	
	/**
	 * Le col associé(principal) au puit restreint du versant.
	 */
	private Col colPrincipal;
	
	/**
	 * Le pic à gauche du col associé au puit.
	 */
	private Pic picGauche;
	
	/**
	 * Le pic à droite du col associé au puit.
	 */
	private Pic picDroite;
	
	/**
	 * Le puit restreint qui sert pour construir le squelette du candidat de canyon.
	 */
	private Puit puit;
	
	/**
	 * L'objet de type topologie du terrain.
	 */
	private Topologie topo;
	
	/**
	 * L'ID du polygone versant.
	 */
	private int ID;
	
	/**
	 * Le col auxiliaire(Secondaire) du versant.
	 */
	private Col colSecondaire;
	
	/**
	 * La liste des Cretes qui composent le versant.
	 */
	private ArrayList<Crete> listCretesVersant = new ArrayList<Crete>();
	
	/**
	 * La liste des vertex qui composent les crêtes du versant.
	 */
	private ArrayList<Integer> listVertexVersant = new ArrayList<Integer>();
	
	/**
	 * La liste des Points verifiés au fond de la vallée.
	 */
	
	private ArrayList<Integer> listVerificationFondVallee = new ArrayList<Integer>();
	
	private ArrayList<Integer> listBordureFondVallee = new ArrayList<Integer>();
	
//	/**
//	 * La liste des talwegs du versant.
//	 */
//	private ArrayList<Ravin> listTalwegsVersant = new ArrayList<Ravin>();
	
	/**
	 * Le talweg principal du versant.
	 */
	private Ravin talwegPrincipal ;
	
	/**
	 * Ce constructeur permet de creer un versant a partir de deux pics, un col, un puit et un objet qui indique la topologie du terrain.
	 * @param col Le col associé au puit restreint.
	 * @param picGauche Le pic à gauche du col associé au puit.
	 * @param picDroite Le pic à droite du col associé au puit.
	 * @param puit Le puit restreint qui sert pour construir le squelette du candidat de canyon.
	 * @param topo L'objet de type topologie du terrain.
	 */	
	public Versant(Col col, Pic picGauche,Pic picDroite,Puit puit,Topologie topo){
		this.colPrincipal=col;
		this.picGauche=picGauche;
		this.picDroite=picDroite;
		this.puit=puit;		
		this.topo=topo;
	}


	
	//Getters 
	/**
	 * Cette methode permet d'obtenir le col associé au puit restreint du versant.
	 * @returncol col le col associé au puit.
	 */
	public Col getCol() {
		return this.colPrincipal;
	}
	
		
	/**
	 * Cette methode permet d'obtenir le pic à gauche du col associé au puit restreint du versant.
	 * @return picGauche pic à gauche du col.
	 */
	public Pic getPicGauche() {
		return this.picGauche;
	}
	/**
	 * Cette methode permet d'obtenir le pic à droite du col associé au puit restreint du versant.
	 * @return picDroite pic à droite du col.
	 */
	public Pic getPicDroite() {
		return this.picDroite;
	}
	/**
	 * Cette methode permet d'obtenir le puit restreint du versant qui sert pour construir le squelette du candidat de canyon.
	 * @return puit puit retreint.
	 */
	public Puit getPuit() {
		return this.puit;
	}
	/**
	 * Cette methode permet d'obtenir la liste des Cretes qui composent le versant.
	 * @return listCretesVersant la liste des Cretes qui composent le versant.
	 */
	public ArrayList<Crete> getListCretesVersant() {
		return this.listCretesVersant;
	}
	
	/**
	 * Cette methode permet d'obtenir la liste des vertex qui composent les crêtes du versant.
	 * @return listVertexVersant la liste des Cretes qui composent le versant.
	 */
	public ArrayList<Integer> getListVertexVersant() {
		return this.listVertexVersant;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste des Points verifiés au fond de la vallée.
	 * @return ArrayList<Points> Une liste de Points.
	 */
	public ArrayList<Integer> getlistVerificationFondVallee()
	{
		return this.listVerificationFondVallee;
	}
	
	public ArrayList<Integer> getlistBordureFondVallee()
	{
		return this.listBordureFondVallee;
	}
	
//	/**
//	 * Cette methode permet d'obtenir la liste des talwegs qui composent le versant.
//	 * @return listTalwegsVersant la liste des Talwegs qui composent le versant.
//	 */
//	public ArrayList<Ravin> getListTalwegsVersant() {
//		return this.listTalwegsVersant;
//	}
	
	/**
	 * Cette methode permet d'obtenir la liste des talwegs qui composent le versant.
	 * @return listTalwegsVersant la liste des Talwegs qui composent le versant.
	 */
	public Ravin getTalwegPrincipal() {
		return this.talwegPrincipal;
	}
	
	/**
	 * Cette methode permet d'obtenir le col auxiliaire du versant.
	 * @returncol col le col auxiliaire.
	 */
	public Col getColSecondaire() {
		return this.colSecondaire;
	}
	
	/**
	 * Cette methode permet d'obtenir l'identificateur du polygone versant.
	 * @return ID Identificateur du versant.
	 */
	public int getID() {
		return this.ID;
	}
	

	
//	public ArrayList<Integer> getTblVertexInter_Talwegs(){
//		return this.tblVertexInter_Talwegs;
//	}
//	
//	public ArrayList<Integer> getTblTalwegs_VertexInter(){
//		return this.tblTalwegs_VertexInter;
//	}
	
	
	//Setters
	/**
	 * Cette methode permet d'assigner un col au versant.
	 * @param col le col associé au puit restreint du versant.
	 */
	public void setColPrincipal(Col col) {
		this.colPrincipal = col;
	}
	/**
	 * Cette methode permet d'assigner un pic qui se trouve à gauche du col.
	 * @param picGauche pic qui se trouve à gauche du col.
	 */
	public void setPicGauche(Pic picGauche) {
		this.picGauche = picGauche;
	}
	/**
	 * Cette methode permet d'assigner un pic qui se trouve à droite du col.
	 * @param picDroite pic qui se trouve à droite du col.
	 */
	public void setPicDroite(Pic picDroite) {
		this.picDroite = picDroite;
	}
	/**
	 * Cette methode permet d'assigner un puit au versant.
	 * @param puit le puit restreint.
	 */
	public void setPuit(Puit puit) {
		this.puit = puit;
	}
	
	/**
	 * Cette methode permet d'assigner à une liste les crêtes du versant.
	 * @param listCretesVersant la liste de crêtes du versant.
	 */
	public void setListCretesVersant(ArrayList<Crete> listCretesVersant) {
		this.listCretesVersant = listCretesVersant;
	}
	/**
	 * Cette methode permet d'assigner à une liste les vertex des crêtes du versant.
	 * @param listVertexVersant la liste de vertex des crêtes du versant.
	 */
	public void setListVertexVersant(ArrayList<Integer> listVertexVersant) {
		this.listVertexVersant = listVertexVersant;
	}
	/**
	 * Cette methode permet d'assigner un identificateur au versant.
	 * @param ID l'identificateur du versant.
	 */
	public void setID(int ID) {
		this.ID = ID;
	}
	
	/**
	 * Cette methode permet d'assigner un identificateur au versant.
	 * @param ID l'identificateur du versant.
	 */
	public void setTalwegPrincipalVersant(Ravin talwegPrincipal) {
		this.talwegPrincipal = talwegPrincipal;
	}
	
	/**
	 * Cette methode permet d'assigner un col Secondaire au versant.
	 * @param col le col auxilaire(Secondaire) du versant.
	 */
	public void setColSecondaire(Col col) {
		this.colSecondaire = col;
	}
	
	/**
	 * Cette methode permet de calculer la distance qu'il y a entre un vertex du talweg avec leurs voisins.
	 * @parametre ravin le talweg pour évaluer la distance d'un vertex avec leurs voisins.
	 * @return La distance moyenne de tous les vertex du talweg.  .
	 */
	public double calculerMoyenneDistanceVoisins(Ravin ravin){
		
	    	//On stocke dans une nouvelle liste chaque vertex du talweg(type point).
	    	HashSet<Points>listVertexIntersection=new HashSet<Points>();
	    	for (Integer intVertex : ravin.getListIdVertex()) {
				Points pointVertex=topo.getTblSommets().get(intVertex);
				listVertexIntersection.add(pointVertex);
			}
	   
	    	//De la liste de vertex du talweg, on supprime le vertex qui correspond au puits parce que ce point est commun pour tous les lignes de talweg
	    	//De cette manière on parcourt la liste des vertex du talweg, et pour chaque vertex on trouve le triangles qui se trouvent à son entourage
	    	//On va extraire les sommets de ces triangles et les ajouter dans une nouvelle liste. Ensuite, on parcourt cette liste et calcule le dénivélé avec le vertex du talweg à évaluer.
	    	// On fait la moyenne(locale) du dénivélé des points voisins avec le vertex du talweg.
	    	//Après, on va prendre chacune de ces moyennes pour calculer une moyenne pour tout le talweg(générale), cela sera la valeur à rendre par la fonction.
	    	
	    	listVertexIntersection.remove(puit.getSommet());
	    	Iterator<Points> iterPoints =listVertexIntersection.iterator();
	    	double nombreVertex=listVertexIntersection.size();
	    	double sumDifElevTotal=0;
	    		    	
	    	while(iterPoints.hasNext()){
	    		
	    		HashSet<Points> setPoints= new HashSet<Points> ();
	    		Points numPoint=iterPoints.next();
	    			    		
	    		String sTriangle = this.topo.getTblSommetsTriangles().get(numPoint.getId()).toString().replace('[',' ').replace(']',' ').trim();
	    		
	    		//Tokenizer permet de lire chacun des valeurs stockés séparées par virgule  
	        	StringTokenizer st = new StringTokenizer(sTriangle,", ");
		        	while (st.hasMoreTokens()){
		        	 int numTri= Integer.parseInt(st.nextToken());
		        	 //Obtenir les sommets des triangles
		        	 int s1=this.topo.getTblTriangles().get(numTri).getS1();
		        	 int s2=this.topo.getTblTriangles().get(numTri).getS2();
		        	 int s3=this.topo.getTblTriangles().get(numTri).getS3();
		        	 
		        	//ajouter les sommets qui composent chaque triangle à une nouvelle liste de points
		        	 setPoints.add(this.topo.getTblSommets().get(s1));
		        	 setPoints.add(this.topo.getTblSommets().get(s2));
		        	 setPoints.add(this.topo.getTblSommets().get(s3));
		        	
		        	}
	        	//System.out.println(ravin.getId()+"-----"+setPoints);
	        	setPoints.remove(numPoint);
	        	double difElev=0;
	        	double sumDifElev=0;
	        	int nombreVoisins=setPoints.size();
	        	for (Points pointVoisin : setPoints) {
	        			//difElev=Math.abs(numPoint.calculerDenivele(pointVoisin));
						difElev=Math.abs(numPoint.calculerDistance(pointVoisin));
						if(difElev>sumDifElev){
							sumDifElev=difElev;
						}
						//sumDifElev=sumDifElev+difElev;
				}
	        	//double moyenneLocal= sumDifElev/nombreVoisins;
	        	double moyenneLocal= sumDifElev;
	        	sumDifElevTotal=sumDifElevTotal+moyenneLocal;
	        	
	        	//System.out.println(ravin.getId()+" "+numPoint.getId()+"-----"+ moyenneLocal);
	    	}   	
	    	double moyenneGlobale=sumDifElevTotal/nombreVertex;
	    	System.out.println("Nombre de voisins Distance: "+nombreVertex);
//	    	System.out.println(ravin.getId()+" moyenneGlobale: "+moyenneGlobale);
//	    	if(moyenneGlobale<=minDifElev){
//	    		minDifElev=moyenneGlobale;
//	    		talwegReference=ravin;
//	    		
//	    	}
		
		return moyenneGlobale;
	}
	
	/**
	 * Cette methode permet de calculer le denivele qu'il y a entre un vertex du talweg avec leurs voisins.
	 * @parametre ravin le talweg pour évaluer le denivele d'un vertex avec leurs voisins.
	 * @return Le denivele moyenne de tous les vertex du talweg.  
	 */
	public double calculerMoyenneDeniveleVoisins(Ravin ravin){
		
	    	//On stocke dans une nouvelle liste chaque vertex du talweg(type point).
	    	HashSet<Points>listVertexIntersection=new HashSet<Points>();
	    	for (Integer intVertex : ravin.getListIdVertex()) {
				Points pointVertex=topo.getTblSommets().get(intVertex);
				listVertexIntersection.add(pointVertex);
			}
	   
	    	//De la liste de vertex du talwe, on supprime le vertex qui correspond au puit parce que ce point est commun pour tous les lignes de talweg
	    	//De cette manière on parcourt la liste des vertex du talweg, et pour chaque vertex on trouve le triangles qui se trouvent à son entourage
	    	//On va extraire les sommets de ces triangles et les ajouter dans une nouvelle liste. Ensuite, on parcourt cette liste et calcule le dénivélé avec le vertex du talweg à évaluer.
	    	// On fait la moyenne(locale) du dénivélé des points voisins avec le vertex du talweg.
	    	//Après, on va prendre chacune de ces moyennes pour calculer une moyenne pour tout le talweg(générale), cela sera la valeur à rendre par la fonction.
	    	
	    	listVertexIntersection.remove(puit.getSommet());
	    	Iterator<Points> iterPoints =listVertexIntersection.iterator();
	    	double nombreVertex=listVertexIntersection.size();
	    	double sumDifElevTotal=0;
	    		    	
	    	while(iterPoints.hasNext()){
	    		
	    		HashSet<Points> setPoints= new HashSet<Points> ();
	    		Points numPoint=iterPoints.next();
	    			    		
	    		String sTriangle = this.topo.getTblSommetsTriangles().get(numPoint.getId()).toString().replace('[',' ').replace(']',' ').trim();
	    		
	    		//Tokenizer permet de lire chacun des valeurs stockés séparées par virgule  
	        	StringTokenizer st = new StringTokenizer(sTriangle,", ");
		        	while (st.hasMoreTokens()){
		        	 int numTri= Integer.parseInt(st.nextToken());
		        	 //Obtenir les sommets des triangles
		        	 int s1=this.topo.getTblTriangles().get(numTri).getS1();
		        	 int s2=this.topo.getTblTriangles().get(numTri).getS2();
		        	 int s3=this.topo.getTblTriangles().get(numTri).getS3();
		        	 
		        	//ajouter les sommets qui composent chaque triangle à une nouvelle liste de points
		        	 setPoints.add(this.topo.getTblSommets().get(s1));
		        	 setPoints.add(this.topo.getTblSommets().get(s2));
		        	 setPoints.add(this.topo.getTblSommets().get(s3));
		        	
		        	}
	        	//System.out.println(ravin.getId()+"-----"+setPoints);
	        	setPoints.remove(numPoint);
	        	double difElev=0;
	        	double sumDifElev=0;
	        	int nombreVoisins=setPoints.size();
	        	for (Points pointVoisin : setPoints) {
	        			difElev=Math.abs(numPoint.calculerDenivele(pointVoisin));
						//difElev=Math.abs(numPoint.calculerDistance(pointVoisin));
//	        			if(difElev>sumDifElev){
//							sumDifElev=difElev;
//						}
						sumDifElev=sumDifElev+difElev;
				}
	        	double moyenneLocal= sumDifElev/nombreVoisins;
	        	//double moyenneLocal= sumDifElev;
	        	sumDifElevTotal=sumDifElevTotal+moyenneLocal;
	        	
	        	//System.out.println(ravin.getId()+" "+numPoint.getId()+"-----"+ moyenneLocal);
	    	}   	
	    	double moyenneGlobale=sumDifElevTotal/nombreVertex;
	    	System.out.println("Nombre de voisins Élévation: "+nombreVertex);
//	    	System.out.println(ravin.getId()+" moyenneGlobale: "+moyenneGlobale);
//	    	if(moyenneGlobale<=minDifElev){
//	    		minDifElev=moyenneGlobale;
//	    		talwegReference=ravin;
//	    		
//	    	}
		
		return moyenneGlobale;
	}

	/**
	 * Cette methode permet de reduire les talwegs associés au versant pour construire le squelette du candidat à canyon.
	 * L'idéé est de laiseer un seul talweg général par versant, qui sera le squelette sur lequel on va construire le canyon.
	 * @return La liste de Ravins redutis pour construire le talweg général et le squelette du canyon .
	 */
	public ArrayList<Ravin> reduireTalwegs(){
		ArrayList<Ravin> talwegsPrincipauxArray= new ArrayList<Ravin>();
	   HashSet<Ravin>talwegsPrincipaux =new HashSet<Ravin>();
	   
		// Multimap<Integer, Integer> TblTalwegPointInter= ArrayListMultimap.create();
		 HashMap<Points,HashSet<Ravin>> tblVertexInter_Talwegs= new HashMap<Points,HashSet<Ravin>>();
		
		 HashMap<Ravin,HashSet<Points>> TblTalwegPointInter= new HashMap<Ravin,HashSet<Points>>();
		 HashSet<Integer>listVertexInterGeneral=new HashSet<Integer>();
		 
		 
//		 //On va comparer un talweg avec chacun des talwegs du versant pour obtenir les vertex en commun entre eux. De cette maniére, on va savoir où les ramifications sont nées vers les autres talwegs.
//		//L'idéé est d'avoir une liste de type HashMap qui montre les points de ramifications pour chaque talweg
//		 for (Ravin ravin : this.getPuit().getListRavins()) {
//			HashSet<Points>listVertexIntersection=new HashSet<Points>();
//			if(ravin.getListIdVertex().get(ravin.getListIdVertex().size()-1)==ravin.getCol().getSommet().getId()){
//				Collections.reverse(ravin.getListIdVertex());
//			}
//				ArrayList<Integer> tempListVertexTalweg= new ArrayList<Integer>();
//				tempListVertexTalweg.addAll(ravin.getListIdVertex());
//				for (Ravin ravinAComparer : this.getPuit().getListRavins()) {
//					if(ravinAComparer!=ravin){
//						for (Integer vertexRavin : ravin.getListIdVertex()) {
//							if(ravinAComparer.getListIdVertex().contains(vertexRavin)){
//								Points ptVertex=topo.getTblSommets().get(vertexRavin);
//								listVertexIntersection.add(ptVertex);
//								listVertexInterGeneral.add(vertexRavin);
//								
//								break;
//							}
//						}
//						
//						
//					}
//				}
//			///////}
//			
//			TblTalwegPointInter.put(ravin, listVertexIntersection);
//			//System.out.println(ravin.getId()+"Points des Talwegs "+TblTalwegPointInter.get(ravin));
//		}
//		
//		 //get the Iterator
//	    Iterator<Integer> itr =listVertexInterGeneral.iterator();
//	    //System.out.println(listVertexInterGeneral);
//	    
//	    //De même au résultat précedant mais au sens inverse, on va obtenir une liste de type Hashmap qui montre les talwegs pour chaque point de ramification
//	    //De la liste précédant, si le ravin à analyser contient le point de ramification on va stocker ce talweg dans une liste temporelle. Après, dans le Hashmap on l'ajoute à l'élément qui identifie le point de ramification.
//	    while(itr.hasNext()){
//	    	int num=itr.next();
//	    	HashSet<Ravin>tempListTalweg=new HashSet<Ravin>();
//	    	Points ptVertex=topo.getTblSommets().get(num);
//			for (Ravin ravin : this.getPuit().getListRavins()) {
//				//System.out.println(num+"Points Intersection Talwegs "+TblTalwegPointInter.get(ravin).contains(num));
//				if(TblTalwegPointInter.get(ravin).contains(ptVertex)){
//					tempListTalweg.add(ravin);
//				}
//			}
//			
//			
//			tblVertexInter_Talwegs.put(ptVertex, tempListTalweg);
//			
//			//System.out.println(this.getID()+" "+ptVertex.getId()+" Talwegs des points"+tblVertexInter_Talwegs.get(ptVertex).size());
//	    }

	    
	    
	    Ravin talwegReference=this.getPuit().getListRavins().get(0);
	    double minDifElev=99999999;
	    
	    
	    //Si le nombre de talwegs du versant est supérieur à deux on analyse chaque talweg dont le col se trouve au tant qu'un vertex du versant, sauf le talweg qui possède le col principal. 
	    //On calcule la distance moyenne de tous les vertex du talweg avec ses voisins. Par la suite, on va laisser le talweg qui a la moyenne de distance la plus petite du versant.
	    if(this.getPuit().getListRavins().size()>2){
	    for(Ravin ravin : this.getPuit().getListRavins()){
	    	
	    	if(ravin.getCol()!=this.colPrincipal && this.getListVertexVersant().contains(ravin.getCol().getSommet().getId())){
	    	//if(ravin.getCol()!=this.col){
	    		    		
	    	double moyenneGlobale=calculerMoyenneDistanceVoisins(ravin);	
	    	//double moyenneGlobale1=calculerMoyenneDeniveleVoisins(ravin);
	    	
	    	//System.out.println(ravin.getId()+" moyenneGlobale: "+moyenneGlobale);
	    	if(moyenneGlobale<minDifElev){
	    	//if(moyenneGlobale<=minDifElev || moyenneGlobale1<=minDifElev){
	    		minDifElev=moyenneGlobale;
	    		talwegReference=ravin;
	    		
	    	}
	    		
//	    	 if(minDifElev<200){
//	 	    	talwegsPrincipaux.add(talwegReference);	
//	 	    }	
	    		
	    		
	    //Sinon on va stocker le talweg dans la liste à rendre par la fonction.
	    }else if(ravin.getCol()==this.colPrincipal){
	    	
//	    	double moyenneGlobale=calculerDistanceVoisins(ravin,talwegReference,minDifElev);	
//    		
//	    	System.out.println(ravin.getId()+" moyenneGlobale: "+moyenneGlobale);
//	    	if(moyenneGlobale<minDifElev){
//	    		minDifElev=moyenneGlobale;
//	    		talwegReference=ravin;
//	    		
//	    	}
	    	
	    	talwegsPrincipaux.add(ravin);
	    }
	    	
//	    	if(minDifElev<200){
//		    	talwegsPrincipaux.add(talwegReference);	
//		    }
	    	
		}
	    //Après d'avoir parcouru tous les talwegs et obtenu le moyenne de distance globale, on va stocker le talweg dans la liste à rendre, si la moyenneGlobale est inférieure de 200 et la distance du talweg est supérieur à 1500 
	    //System.out.println(talwegReference.getId()+"----------------------"+minDifElev);
	    //if(minDifElev<200){
	    //***Revision!!!
	    if(minDifElev<300 && talwegReference.calculerDistanceVertex(talwegReference.getListIdVertex(), topo)>1500){
	    	talwegsPrincipaux.add(talwegReference);	
	    }
	  //Si le col du talweg ne se trouve pas au tant qu'un vertex du versant on va stocker le talweg dans la liste à rendre par la fonction, on va analyser le ravin, sauf celui qui correspond au col principal
	    //On calcule la distance moyenne de tous les vertex du talweg avec ses voisins. Par la suite, on va laisser le talweg qui a la moyenne de distance la plus petite du versant.
	    for(Ravin ravin : this.getPuit().getListRavins()){
	    	
	    	if(ravin.getCol()!=this.colPrincipal && this.getListVertexVersant().contains(ravin.getCol().getSommet().getId())==false){
	    		double moyenneGlobale=calculerMoyenneDistanceVoisins(ravin);
	    		//double moyenneGlobale1=calculerMoyenneDeniveleVoisins(ravin);
	    		
		    	//System.out.println(ravin.getId()+" moyenneGlobale: "+moyenneGlobale);
		    	if(moyenneGlobale<minDifElev){
		    	//if(moyenneGlobale<=minDifElev || moyenneGlobale1<=minDifElev){
		    		minDifElev=moyenneGlobale;
		    		talwegReference=ravin;
		    		
		    	}
	    	}
	    }
	    //Après d'avoir parcouru tous les talwegs et obtenu le moyenne de distance globale, on va stocker le talweg dans la liste à rendre, si la moyenneGlobale est inférieure de 200 et la distance du talweg est supérieur à 1500 
	    //****Revision!!
	    if(minDifElev<300 && talwegReference.calculerDistanceVertex(talwegReference.getListIdVertex(), topo)>1500){
	    //if(minDifElev<200){
	    	talwegsPrincipaux.add(talwegReference);	
	    }
	    
	    //Si le nombre de talwegs du versant est inférieur ou égal à deux, on va calculer la moyenne globale de la distance de voisins aux vertex du talweg. 
	    //Si la valeur de la moyenne globale est inférieure à 200 on va stocker le talweg dans la liste à rendre de la fonction.
	    }else if(this.getPuit().getListRavins().size()<=2){
	    	
	    	 for(Ravin ravin : this.getPuit().getListRavins()){
	    		 
	 	    		double moyenneGlobale=calculerMoyenneDistanceVoisins(ravin);	
	 	    		//double moyenneGlobale1=calculerMoyenneDeniveleVoisins(ravin);
	 	    		
	 		    	//System.out.println(ravin.getId()+" moyenneGlobale: "+moyenneGlobale);
	 		    	//if(moyenneGlobale<200 && talwegReference.calculerDistanceVertex(talwegReference.getListIdVertex(), topo)>1500){
	 		    	if(moyenneGlobale<300){
	 		    		talwegsPrincipaux.add(ravin);
	 		    		
	 		    	}	 	    	
	    	 }	 
	    }
	   talwegsPrincipauxArray.addAll(talwegsPrincipaux);
	   //getListTalwegsVersant().addAll(talwegsPrincipaux);
	   return talwegsPrincipauxArray;
	    		
	}
	
	/**
	 * Cette methode permet de définir le fond de la vallée autour du squelette.
	 * @return La liste des Vertex qui définit le fond de la vallée.
	 */	
	public PolygoneVallee trouverPolygoneVallée(){
	//public ArrayList<Integer> trouverFondVallée(){
		
		listVerificationFondVallee.clear();
		
		Squelette squelette=null;
		
		ArrayList<Integer> VertexBordure=new ArrayList<Integer>();
		ArrayList<Integer> VertexBordureGauche=new ArrayList<Integer>();
		ArrayList<Integer> VertexBordureDroite=new ArrayList<Integer>();
		ArrayList<Integer> VertexAnalysesGauche=new ArrayList<Integer>();
		ArrayList<Integer> VertexAnalysesDroite=new ArrayList<Integer>();
		
		//Cette partie s'agit du calcul d'un dénivélé général pour le squelette et de trouver les vertex du squelette.
		// D'abord, on va calculer la moyenne des vertex pour les vertex du talweg par rapport  au dénivelé de ses voisins.
		//On ajoute les points des vertex du talweg à une nouvelle liste de points à évaluer
		//De cette liste, on va supprimer le puits restreint.
		
		 //for(Ravin ravin : this.getListTalwegsVersant()){
			Ravin ravin=getTalwegPrincipal();
			 if(ravin.getListIdVertex().size()>2){
			 double moyenneGlobaleDeniv=calculerMoyenneDeniveleVoisins(ravin);	
			 
			 double moyenneGlobaleDist=calculerMoyenneDistanceVoisins(ravin);	
			 
			 double moyenneGlobalePente=Math.abs(moyenneGlobaleDeniv/moyenneGlobaleDist);
			 System.out.println(ravin.getId()+"!!!! moyenneGlobale dénivélé: "+moyenneGlobaleDeniv);
			 //System.out.println(ravin.getId()+"//// moyenneGlobale distance: "+moyenneGlobaleDist);
			 //System.out.println(ravin.getId()+"//// moyenneGlobale pente: "+moyenneGlobalePente);
				
			 //On stocke dans une nouvelle liste chaque vertex du talweg(type point).
		    	HashSet<Points>listPointsEvaluer=new HashSet<Points>();
		    	//HashSet<Points>listPointsBordure=new HashSet<Points>();
		    	
		      			    	
		    	for (Integer intVertex : ravin.getListIdVertex()) {
					Points pointVertex=topo.getTblSommets().get(intVertex);
					listPointsEvaluer.add(pointVertex);
				}
		    	
		    	//Comme on connait déjà la liste de vertex qui conforment le squelette. Maintenant, on veut avoir la liste d'aretes du squelette.
		    	//À suivre, on va associer les arêtes aux vertex qui bâtissent le squelette de la vallée
		    	//De la liste de vertex du squelette, on va chercher l'arête qui correspond aux deux vertex adjacent
		    	//On stocke cet arête dans une liste de la classe squelette 
		    	
		    	ArrayList<Aretes> listAreteSquelette=new ArrayList<Aretes> ();
		    	for (int i=0;i<ravin.getListIdVertex().size()-1;i++) {
					Integer vertex1=ravin.getListIdVertex().get(i);
					Integer vertex2=ravin.getListIdVertex().get(i+1);
					
					String sArete = this.topo.getTblSommetsAretes().get(vertex1).toString().replace('[',' ').replace(']',' ').trim();
					//System.out.println("sArete"+sArete);
		    		//Tokenizer permet de lire chacun des valeurs stockés séparées par virgule  
		        	StringTokenizer st = new StringTokenizer(sArete,", ");
			        	while (st.hasMoreTokens()){
			        		int numArete= Integer.parseInt(st.nextToken());
			        		int s1=this.topo.getTblAretes().get(numArete).getS1();
			        		int s2=this.topo.getTblAretes().get(numArete).getS2();
			        		
			        		if(s1==vertex2 || s2==vertex2){
			        			//System.out.println("arete"+numArete);
			        			listAreteSquelette.add(topo.getTblAretes().get(numArete));
			        		}
			        	}				
				}
		    	squelette= new Squelette(ravin.getListIdVertex().get(0),ravin);
		    	squelette.setListAreteSquelette(listAreteSquelette);
		    	
		    	
//		    	System.out.println(ravin.getListIdVertex().size()+"getListIdVertex()"+ravin.getListIdVertex());
//		    	System.out.println(squelette.getListAreteSquelette().size()+"Aretes du Squelette"+squelette.getListAreteSquelette());

		    	listPointsEvaluer.remove(puit.getSommet());
		    			    		    	
		    	// De la première arête de notre squelette, on va analyser les points voisins qui se trouvent à gauche et à droite du premier point du squelette. 
		    	// Ces points vont être ajoutés à une liste de points analysés (point a,b)pour chercher le fond de la vallée. 
		    	//Par la suite, on va chercher les voisins de ce point (point c) dont la hauteur est plus élèvée au dénivelé moyen global
		    	//Le poin c, qui se trouve à la bordure de notre versant, s'agit en réalité de deux points. Un point à gauche et un autre à droite.
		    	// D'abord on va analyser les points qui se trouvent à côté gauche du squelette. Par la suite ceux qui se trouvent à côté droit.
		    
		    	
		    	int s1=	ravin.getListIdVertex().get(0);	    	
		    	int s2=ravin.getListIdVertex().get(1);
		    	Points a= this.topo.getTblSommets().get(s1);
		    	Points b= this.topo.getTblSommets().get(s2);
		    	VertexAnalysesGauche.add(a.getId());
		    	VertexAnalysesDroite.add(a.getId());
		    	
		    	Points pointGauche=a;
		    	double penteGauche=0;
		    	Points pointDroit=a;
		    	double penteDroite=0;
//		    	
		    	//if(a.getId()!=186063 && a.getId()!=159194 && a.getId()!=142249 && a.getId()!=142221 && a.getId()!=145385 && a.getId()!=170613 && a.getId()!=154983 ){	
//		    	if(a.getId()==37129 || a.getId()==81476 || a.getId()==15091 || a.getId()==140826 || a.getId()==262351 || a.getId()==3756 || a.getId()==14972 || a.getId()==14505
//		    			||a.getId()==38278|| a.getId()==211530 || a.getId()==209215 ||a.getId()==108685||a.getId()==146222 ||a.getId()==80213 ||a.getId()==18302 ||a.getId()==18826
//		    			||a.getId()==18838 ||a.getId()==5070 ||a.getId()==13549 ||a.getId()==5097){
		    	//if(a.getId()!=5608){
		    	//if(a.getId()!=2108 && a.getId()!=2097){	
		    	//if(a.getId()!=14505 && a.getId()!=5070 && a.getId()!=18838 && a.getId()!=9653 && a.getId()!=30266 && a.getId()!=13549 && a.getId()!=18302 && a.getId()!=44433
		    	//	 && a.getId()!=87007 && a.getId()!=44341 && a.getId()!=45129 && a.getId()!=80213 && a.getId()!=146222 && a.getId()!=182129 && a.getId()!=217558 && a.getId()!=18160){	
		    	int numPoint=ravin.getCol().getSommet().getId();
				String sArete = this.topo.getTblSommetsAretes().get(numPoint).toString().replace('[',' ').replace(']',' ').trim();
				//System.out.println("sArete"+sArete);
	    		//Tokenizer permet de lire chacun des valeurs stockés séparées par virgule  
	        	StringTokenizer st = new StringTokenizer(sArete,", ");
		        	while (st.hasMoreTokens()){
		        		int numArete= Integer.parseInt(st.nextToken());
		        		
		        		int s3=this.topo.getTblAretes().get(numArete).getS2();
		        		if(s3==numPoint){
		        			s3=this.topo.getTblAretes().get(numArete).getS1();
		        		}
		        		
		        		
		        		Points c= this.topo.getTblSommets().get(s3);
		        				        		
		        		double pentePtVoisin=Math.abs(a.calculePente(c));
		        		
		        		
		        		double determinant=(a.getX()*b.getY())+(b.getX()*c.getY())+(c.getX()*a.getY())
		        				-((c.getX()*b.getY())+(a.getX()*c.getY())+(b.getX()*a.getY()));
		        	
		        		if(s3==81453){
		        			System.out.println("determinant "+determinant);
		        		}
		        	
		        	//System.out.println("c.getId()"+c.getId());
		        	if(topo.getTblSommetsTriangles().get(c.getId()).size()>1){
		        		
			        		if(determinant<0){
			        			//System.out.println("penteVoisin"+pentePtVoisin+"penteGauche"+penteGauche);
			        			if(penteGauche<pentePtVoisin && this.getListVertexVersant().contains(c.getId())){
			        			//if(penteGauche<pentePtVoisin){
			        				
			        				penteGauche=pentePtVoisin;
			        				pointGauche=c;
			        				
			        			}			        			
			        		}else if(determinant>0){	
			        			
			        			//System.out.println("penteVoisin"+pentePtVoisin+"penteDroite"+penteDroite);
			        			if(penteDroite<pentePtVoisin && this.getListVertexVersant().contains(c.getId())){
			        			//if(penteDroite<pentePtVoisin){	
			        				penteDroite=pentePtVoisin;
			        				pointDroit=c;
			        				
			        			}
			        		}
//			        		else if(determinant==0){
//			        			if(penteGauche<pentePtVoisin && this.getListVertexVersant().contains(c.getId())){
//				        			//if(penteGauche<pentePtVoisin){
//				        				
//				        				penteGauche=pentePtVoisin;
//				        				pointGauche=c;
//				        				
//				        			}
//			        			if(penteDroite<pentePtVoisin && this.getListVertexVersant().contains(c.getId())){
//				        			//if(penteDroite<pentePtVoisin){	
//				        				penteDroite=pentePtVoisin;
//				        				pointDroit=c;
//				        				
//				        			}
//			        		}
		        		
			        	}
		        	}	
		        	System.out.println(" pointGauche "+pointGauche.getId()+" pointDroit "+pointDroit.getId());
		        	
		        	//On va analyser les points jusqu'à atteindre à la bordure du bout du versant. 
		        	//Pour cela, on va stocker les vertex qui correspondent à la bout du versant à deux listes.
		        	//On va analyser le dénivélé entre le point c et le point a(debut du squelette). 
		        	//Si ce dénivélé est supérieur au celui de la moyenne du squelette, on va stocker ce point comme un point de la bordure du polygone de la vallée.
		        	ArrayList<Integer> boutDuVersantDroit=new ArrayList<Integer>();
			        ArrayList<Integer> boutDuVersantGauche=new ArrayList<Integer>();
		        	for(int i=1;i<=5;i++){
				      boutDuVersantDroit.add(this.listVertexVersant.get(i));
				    }
				    //int tailleBoutGauche=this.listVertexVersant.size()-5;
				    //System.out.println("***************************lsitVertexVersant***********"+tailleBoutGauche);
				    //for(int i=tailleBoutGauche;i<this.listVertexVersant.size();i++){
				    for(int i=this.listVertexVersant.size()-5;i<this.listVertexVersant.size();i++){
				      boutDuVersantGauche.add(this.listVertexVersant.get(i));
				    }
		        	
		        	//System.out.println("PointGauche"+pointGauche.getId()+"PointDroit"+pointDroit.getId());
		        	///////////CÔTÉ GAUCHE=1
		        	double denivGauche=Math.abs(pointGauche.calculerDenivele(a));
		        	if(denivGauche>moyenneGlobaleDeniv){
		        		VertexBordureGauche.add(pointGauche.getId());
		        		//VertexBordure.add(pointGauche.getId());
		        	}
		        	
		        	VertexAnalysesGauche.add(pointGauche.getId());
		        	
		        	//System.out.println("point droit"+pointDroit.getId()+" Col: "+numPoint);
		        	       	
		        	
		        	//On appelle à la fonction pour obtenir les points qui se trouvent à gauche du squelette.  
		        	//coteSquelette=1 Si on analyse le côté gauche
		    		//coteSquelette=2 Si on analyse le côté droit
		        	    			        	
		        	double difElevGauche=Math.abs(a.getZ()-pointGauche.getZ());
		        	int pointAnalyser=pointGauche.getId();
		        	Points pointVerification=a;
		        	Points pointBordure=pointGauche;		        			      		        	       	
			        		        					      			      			        	        
			        squelette.getPointsBordure(1, moyenneGlobaleDeniv,moyenneGlobalePente, difElevGauche, pointAnalyser, pointGauche, pointBordure, pointVerification, VertexAnalysesGauche, VertexBordureGauche, boutDuVersantGauche,this.listVertexVersant, topo);
			        

			        ///////////CÔTÉ DROIT=2
			        
			      //On va analyser le dénivélé entre le point c et le point a(debut du squelette). 
		        //Si ce dénivélé est supérieur au celui de la moyenne du squelette, on va stocker ce point comme un point de la bordure du polygone de la vallée.
			        
			        double denivDroit=Math.abs(pointDroit.calculerDenivele(a));
			        if(denivDroit>moyenneGlobaleDeniv){
			        	VertexBordureDroite.add(pointDroit.getId());
			        	//VertexBordure.add(pointDroit.getId());
			        	
		        	}
		        	
			        VertexAnalysesDroite.add(pointDroit.getId());
		        				        		    
			        	
			      //On appelle à la fonction pour obtenir les points qui se trouvent à gauche du squelette.  
			      //coteSquelette=1 Si on analyse le côté gauche
		    	  //coteSquelette=2 Si on analyse le côté droit        	
			        	
			        double difElevDroit=Math.abs(a.getZ()-pointDroit.getZ());
			        pointAnalyser=pointDroit.getId();
			        pointVerification=a;
			        pointBordure=pointDroit;			        						        	
			        	
			        squelette.getPointsBordure(2, moyenneGlobaleDeniv,moyenneGlobalePente, difElevDroit, pointAnalyser, pointDroit, pointBordure, pointVerification, VertexAnalysesDroite, VertexBordureDroite, boutDuVersantDroit,this.listVertexVersant, topo);
			      
			        // À continuation, on va prendre les vertex qui se trouvent au debut du versant, pour les associer à ceux qui se trouvent dans la liste de bordure du polygone de la vallée
			        //Grâce à associer ces points, la définition du polygone de la vallée suivra la même forme du versant pour ces vertex de debut.
			        //On va extraire les vertex de la liste de vertex Analysés tandis que le vertex à Analyser soit différent que le premier vertex de la liste de bordure de la vallée.
			        //On va faire la procédure au-dessus pour les côtés gauche et droit.
			        
			        if(VertexBordureGauche.isEmpty()==false && VertexBordureDroite.isEmpty()==false){
			        
			        int tempVerGauche=VertexAnalysesGauche.get(0);
			        int countGauche=0;
			        ArrayList<Integer> tempVertexVersantGauche= new ArrayList<Integer>();
			        
			        while(tempVerGauche!=VertexBordureGauche.get(0)){
			        	tempVertexVersantGauche.add(tempVerGauche);
			        	countGauche=countGauche+1;
			        	tempVerGauche=VertexAnalysesGauche.get(countGauche);
			        	
			        }
			        int tempVerDroite=VertexAnalysesDroite.get(0);
			        int countDroite=0;
			        ArrayList<Integer> tempVertexVersantDroite= new ArrayList<Integer>();
			        while(tempVerDroite!=VertexBordureDroite.get(0)){
			        	tempVertexVersantDroite.add(tempVerDroite);
			        	countDroite=countDroite+1;
			        	tempVerDroite=VertexAnalysesDroite.get(countDroite);
			        	
			        }
		    	
			        
//			        System.out.println("Gauche "+tempVertexVersantGauche);
//					System.out.println("Droite "+tempVertexVersantDroite);
			        
			        System.out.println(" pointGauche "+pointGauche.getId()+" pointDroit "+pointDroit.getId());
					
					VertexBordureGauche.addAll(0, tempVertexVersantGauche);
					VertexBordureDroite.addAll(0, tempVertexVersantDroite);
					
//					 if(pointGauche==a || pointDroit==a){
//						 VertexBordureGauche.clear();
//						 VertexBordureDroite.clear();
//					 }
					
			       }
					System.out.println(squelette.getRavinSquelette().getListIdVertex().get(0)+" ********************* denivele: "+squelette.getDeniveleMoyenBordure()+" distanceBordure: "+squelette.getDistanceMoyenBordure()+ " distanceLongueurSquelette: "+squelette.getLongueurSquelette(topo));
					
//					fondVallee.setId(a.getId());
//					fondVallee.setMapDeniveleBordureAll(squelette.getMapDeniveleBordure());
//					fondVallee.setMapDistanceBordureAll(squelette.getMapDistanceBordure());
		    	
		    	
		    	//}
				        	       		
			 }
		if(VertexBordureGauche.isEmpty()==false && VertexBordureDroite.isEmpty()==false){    	
		VertexBordure.addAll(VertexBordureGauche);
		Collections.reverse(VertexBordureDroite);
		VertexBordure.addAll(VertexBordureDroite);
		
		 listVerificationFondVallee.addAll(VertexAnalysesGauche);
		 listVerificationFondVallee.addAll(VertexAnalysesDroite);
		}
		
		
		
		System.out.println("Taille VertexBordure"+VertexBordure.size());
		//System.out.println(squelette.getRavinSquelette().getListIdVertex().get(0)+" ********************* denivele: "+
		//squelette.getDeniveleMoyenBordure()+" distanceBordure: "+squelette.getDistanceMoyenBordure()+ 
		//" distanceLongueurSquelette: "+squelette.getLongueurSquelette(topo));
        
      
		
		 //listVerificationFondVallee.addAll(VertexAnalysesGauche);
		 //listVerificationFondVallee.addAll(VertexAnalysesDroite);
		 	 
		// listBordureFondVallee.addAll(VertexBordure);
		 
		 //On va créér un nouveau objet de type Polygone vallée à partir d'un Id, la liste de vertex au bordure de la vallée, le Map de dénivélés du squelette au bordure et le map de distances du squelette au bordure.
		 PolygoneVallee polygoneVallee=new PolygoneVallee(squelette.getRavinSquelette().getListIdVertex().get(0), 
				 VertexBordure, 
				 squelette.getMapDeniveleBordure(),
				 squelette.getMapDistanceBordure(),
				 topo,this);
		 
		 
		if(polygoneVallee.getVertexDuFondVallee().isEmpty()){
			polygoneVallee= null;
		}
		 
		 
		if(polygoneVallee!=null){
		 System.out.println(polygoneVallee.classerDesVallees()+"Ecart Distance: "+polygoneVallee.calculerEcartTypeDist()+"Ecart Denivele: "+polygoneVallee.calculerEcartTypeDenivele()+ " moyenElevation "+polygoneVallee.supprimerVertexParElevation());
		// System.out.println(polygoneVallee.classerDesVallees()+" getDeniveleMoyenBordure(): "+polygoneVallee.getDeniveleMoyenBordure()+"getDistanceMoyenBordure(): "+polygoneVallee.getDistanceMoyenBordure());
		} 
	
		// return VertexBordure;
		 return polygoneVallee;
		
	}
	

	
	
}
