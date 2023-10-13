package surfaceNetwork;

import graphiques.Graphique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.GeometryBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import topologie.Points;
import topologie.Topologie;
import vallees.PolygoneVallee;
import vallees.Versant;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.vividsolutions.jts.geom.LineString;

//import com.DCEL.DemiArete;


/** 
 * <b> Classe ReseauSurface  </b>
 * <p> 
 * Cette classe permet de construire un ReseauSurface a partir d'un de type objet Topologie. Ce graphe contient
 * une liste de PointCritiques soit des Pics, des Cols et des Puits. De plus, chaque arete de ce graphe est soit
 * un Ravin(talweg) qui permet de la connection entre un puit et un col, ou une Crete, qui permet de la connection
 * entre un pic et un col.
 * 
 * @see Pic 
 * @see Col
 * @see Puit
 * @see Ravin
 * @see Crete
 * 
 * @author Andr�s Camilo Cort�s Murcia
 * @since 2015-08-11
 */
public class ReseauSurface 
{
	/**
	 * La Topologie de ce ReseauSurface.
	 */
	private Topologie topo;
	
	/**
	 * La liste des Pics.
	 */
	private ArrayList<Pic> listPics = new ArrayList<Pic>();
	
	/**
	 * La liste des Puits.
	 */
	private ArrayList<Puit> listPuits = new ArrayList<Puit>();
	
	/**
	 * La liste des Cols.
	 */
	
	private ArrayList<Col> listCols = new ArrayList<Col>();
	
	/**
	 * La liste des Cretes.
	 */
	private ArrayList<Crete> listCretes = new ArrayList<Crete>();
	
	/**
	 * La liste des Ravins.
	 */
	private ArrayList<Ravin> listRavins = new ArrayList<Ravin>();
	
	/**
	 * La liste des Versants.
	 */
	private ArrayList<Versant> listVersants = new ArrayList<Versant>();
	
	/**
	 * La liste des puits Virtuel de type HashMap.Cl�:Identificateur du Puit Virtuel. Valeur:Le point virtuel.
	 */
	private HashMap<Integer, Puit> listPuitsVirtuels= new HashMap<Integer, Puit>();
	
	/**
	 * La liste qui stocke les points voisins � supprim�r qui sont plus �l�v�s que le point � analyser.
	 */
	private ArrayList<Integer> deleteUpper= new ArrayList<Integer> ();
	/**
	 * La liste qui stocke les points voisins � supprim�r qui sont plus bas que le point � analyser.
	 */
	private ArrayList<Integer> deleteDowner= new ArrayList<Integer> ();
	/**
	 * La liste qui stocke les points voisins du point � analyser.
	 */
	private ArrayList<String> voisins= new ArrayList<String>();
	/**
	 * La liste qui stocke les points voisins du point col � traiter.
	 */
	private Multimap<Col, Integer> voisinsCol= ArrayListMultimap.create();
	/**
	 * La liste qui est un ensamble de petits morceaux de cr�tes.
	 */
	private DefaultFeatureCollection creteCollection = new DefaultFeatureCollection();
	/**
	 * La liste qui est un ensamble de petits morceaux de ravins.
	 */
	private DefaultFeatureCollection talwegCollection = new DefaultFeatureCollection();
	

	/**
	 * La liste qui est un ensamble de lignes du fond de la vall�e.
	 */
	private DefaultFeatureCollection fondValleeCollection = new DefaultFeatureCollection();
	
	/**
	 * La liste qui est un ensamble des objets de type canyon.
	 */
	private DefaultFeatureCollection canyonCollection = new DefaultFeatureCollection();
	
	/**
	 * La liste qui est un ensamble des objets de type chenal.
	 */
	private DefaultFeatureCollection chenalCollection = new DefaultFeatureCollection();
	
	/**
	 * La liste qui contient des �l�ments g�om�triques d'une ligne de cr�te.
	 */
	private List<SimpleFeature> featuresListCrete = new ArrayList<SimpleFeature>();
	/**
	 * La liste qui contient des �l�ments g�om�triques d'une ligne de ravin.
	 */
	private List<SimpleFeature> featuresListTalweg = new ArrayList<SimpleFeature>();
	
	/**
	 * La liste qui contient des �l�ments g�om�triques du polygone du fond de la vall�e.
	 */
	private List<SimpleFeature> featuresListFondVallee = new ArrayList<SimpleFeature>();
	
	/**
	 * La liste qui contient des �l�ments g�om�triques d'un pic.
	 */
	private List<SimpleFeature> featuresListPics = new ArrayList<SimpleFeature>();
	/**
	 * La liste qui contient des �l�ments g�om�triques d'un puit.
	 */
	private List<SimpleFeature> featuresListPuits = new ArrayList<SimpleFeature>();
	/**
	 * La liste qui contient des �l�ments g�om�triques d'un col.
	 */
	private List<SimpleFeature> featuresListCols = new ArrayList<SimpleFeature>();
	
	/**
	 * La liste qui contient des �l�ments g�om�triques(points) d'un fond de vall�e.
	 */
	private List<SimpleFeature> featuresListPointFondVallee = new ArrayList<SimpleFeature>();
	
	/**
	 * La liste qui contient des �l�ments g�om�triques(points) d'un canyon.
	 */
	private List<SimpleFeature> featuresListCanyon = new ArrayList<SimpleFeature>();
	
	/**
	 * La liste qui contient des �l�ments g�om�triques(points) d'un chenal.
	 */
	private List<SimpleFeature> featuresListChenal = new ArrayList<SimpleFeature>();	
	
	/**
	 * La liste qui contient des �l�ments g�om�triques(points) verifi�s d'un fond de vall�e.
	 */
	private List<SimpleFeature> featuresListVerificationFondVallee = new ArrayList<SimpleFeature>();
	
	private ArrayList<Puit> puitReelsRestreints= new ArrayList<Puit>();
	
	private ArrayList<Integer> puitsPlaineAbyssale=new ArrayList<Integer>();
		
	/**
	 * La liste des Points au fond de la vall�e.
	 */
	
	private ArrayList<Integer> listPointsFondVallee = new ArrayList<Integer>();
	
	
	/**
	 * La liste des Points verifi�s au fond de la vall�e.
	 */
	
	private ArrayList<Integer> listVerificationFondVallee = new ArrayList<Integer>();
	
		
	/**
	 * Ce constructeur permet de construire un ReseauSurface a partir d'une instance de type Topologie. Lors de la construction,
	 * la methode priv�e extractionPointsCritiques() est appel�e.
	 * @param p_topo La Topologie du mod�le
	 */
	public ReseauSurface(Topologie p_topo)
	{
		this.topo = p_topo;
		this.extractionPointsCritiques();

	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste d'�l�ments g�om�triques de points du fond de la vall�e
	 * @return  List<SimpleFeature> Une liste d'�l�ments g�om�triques de points du fond de la vall�e.
	 */
	public List<SimpleFeature> getFeaturesListPointsFondVallee() {
		return this.featuresListPointFondVallee;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste d'�l�ments g�om�triques de points du fond de la vall�e
	 * @return  List<SimpleFeature> Une liste d'�l�ments g�om�triques de points du fond de la vall�e.
	 */
	public List<SimpleFeature> getFeaturesListVerificationFondValleee() {
		return this.featuresListVerificationFondVallee;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste des Points au fond de la vall�e.
	 * @return ArrayList<Points> Une liste de Points.
	 */
	public ArrayList<Integer> getlistPointsFondVallee()
	{
		return this.listPointsFondVallee;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste des Points verifi�s au fond de la vall�e.
	 * @return ArrayList<Points> Une liste de Points.
	 */
	public ArrayList<Integer> getlistVerificationFondVallee()
	{
		return this.listVerificationFondVallee;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste des Pics.
	 * @return ArrayList<Pic> Une liste de Pics.
	 */
	public ArrayList<Pic> getlistPics()
	{
		return this.listPics;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste des Puits.
	 * @return ArrayList<Puit> Une liste de Puits.
	 */
	public ArrayList<Puit> getlistPuits()
	{
		return this.listPuits;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste des Cols.
	 * @return ArrayList<Col> Une liste de Cols.
	 */
	public ArrayList<Col> getlistCols()
	{
		return this.listCols;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste de Cretes.
	 * @return ArrayList<Crete> Une liste de Cretes.
	 */
	public ArrayList<Crete> getlistCretes()
	{
		return this.listCretes;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste de Ravins.
	 * @return ArrayList<Ravin> Une liste de Ravins.
	 */
	public ArrayList<Ravin> getlistRavins()
	{
		return this.listRavins;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste de Versants.
	 * @return ArrayList<Versant> Une liste de Versants.
	 */
	public ArrayList<Versant> getlistVersants()
	{
		return this.listVersants;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste de puits Virtuels.
	 * @return HashMap<Integer, Puit> Une liste de puitsVirtuels. Cl�:Identificateur du Puit Virtuel. Valeur:Le point virtuel.
	 */
	public HashMap<Integer, Puit> getlistPuitsVirtuels(){
		return listPuitsVirtuels;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste de voisins � supprimer plus Haut que le point � analyser .
	 * @return ArrayList<Ravin> Une liste de Voisins plus haut que le point � analyser.
	 */
	public ArrayList<Integer> getdeleteUpper()
	{
		return this.deleteUpper;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste de voisins � supprimer plus basque le point � analyser .
	 * @return ArrayList<Ravin> Une liste de Voisins plus bas que le point � analyser.
	 */
	public ArrayList<Integer> getdeleteDowner()
	{
		return this.deleteDowner;
	}
	
	/**
	 * Cette methode permet d'avoir acces a l'ensemble de petit morceaux de cr�tes.
	 * @return DefaultFeatureCollection Une collection de l'ensemble de petit morceaux de cr�tes.
	 */
	public DefaultFeatureCollection getCreteCollection() {
		return creteCollection;
	}
	/**
	 * Cette methode permet d'avoir acces a l'ensemble de petit morceaux de talwegs.
	 * @return DefaultFeatureCollection Une collection de l'ensemble de petit morceaux de talwegs.
	 */
	public DefaultFeatureCollection getTalwegCollection() {
		return talwegCollection;
	}
	/**
	 * Cette methode permet d'avoir acces a l'ensemble des lignes qui forme le fond de la Vall�e.
	 * @return DefaultFeatureCollection Une collection de l'ensemble de ligenes du fond de la vall�e.
	 */
	public DefaultFeatureCollection getFondValleeCollection() {
		return fondValleeCollection;
	}
	
	/**
	 * Cette methode permet d'avoir acces a l'ensemble des lignes qui forme les canyons.
	 * @return DefaultFeatureCollection Une collection de l'ensemble de lignes des canyons.
	 */
	public DefaultFeatureCollection getCanyonCollection() {
		return canyonCollection;
	}
	
	/**
	 * Cette methode permet d'avoir acces a l'ensemble des lignes qui forme les chenaux.
	 * @return DefaultFeatureCollection Une collection de l'ensemble de lignes des chenaux.
	 */
	public DefaultFeatureCollection getChenalCollection() {
		return chenalCollection;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste d'�l�ments g�om�triques de cr�tes
	 * @return  List<SimpleFeature> Une liste d'�l�ments g�om�triques de cr�tes.
	 */
	public List<SimpleFeature> getFeaturesListCrete() {
		return featuresListCrete;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste d'�l�ments g�om�triques de ravins
	 * @return  List<SimpleFeature> Une liste d'�l�ments g�om�triques de ravins.
	 */
	public List<SimpleFeature> getFeaturesListTalweg() {
		return featuresListTalweg;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste d'�l�ments g�om�triques du fond de la Vall�e
	 * @return  List<SimpleFeature> Une liste d'�l�ments g�om�triques de fond de la Vall�e.
	 */
	public List<SimpleFeature> getFeaturesListFondVallee() {
		return featuresListFondVallee;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste d'�l�ments g�om�triques des canyons
	 * @return  List<SimpleFeature> Une liste d'�l�ments g�om�triques des canyons.
	 */
	public List<SimpleFeature> getFeaturesListCanyon() {
		return featuresListCanyon;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste d'�l�ments g�om�triques des chenaux
	 * @return  List<SimpleFeature> Une liste d'�l�ments g�om�triques des chenaux.
	 */
	public List<SimpleFeature> getFeaturesListChenal() {
		return featuresListChenal;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste d'�l�ments g�om�triques de pics
	 * @return  List<SimpleFeature> Une liste d'�l�ments g�om�triques de pics.
	 */
	public List<SimpleFeature> getFeaturesListPics() {
		return featuresListPics;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste d'�l�ments g�om�triques de puits
	 * @return  List<SimpleFeature> Une liste d'�l�ments g�om�triques de puits.
	 */
	public List<SimpleFeature> getFeaturesListPuits() {
		return featuresListPuits;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste d'�l�ments g�om�triques de cols
	 * @return  List<SimpleFeature> Une liste d'�l�ments g�om�triques de cols.
	 */
	public List<SimpleFeature> getFeaturesListCols() {
		return featuresListCols;
	}
	
	
	/**
	 * Cette methode private permet d'analyser tous les Sommets de la Topologie pour construire,
	 * les Pics, les Puits et les Cols. Elle s'inspire de l'algorithme 1 de Takahashi 1995 
	 * (Extract of Critical Points). Elle est appele dans le constructeur.
	 */
	private void extractionPointsCritiques()
	{
		
		for (int i=1;i< topo.getTblSommets().size();i++) {
			
		voisins.clear();
	    deleteDowner.clear();
    	deleteUpper.clear();	
		ArrayList<Integer> vertexOrdonnes=topo.rangerVertex(i);	
		
		//System.out.println(i+"-- "+vertexOrdonnes);
		
	    double deltaNeg=0;
	    double deltaPos=0;
	    double delta=0;
	    int nc=0;
	   // int s1=0;
		 //si la liste qui garde les sommets voisins d'i n'est pas vide, il faut la parcourir pour comparer les hauteurs voisines avec la hauteur d'i
     	// On obtient l'�l�vation d'un voisin et du point i. La diff�rence entre eux, elle sera la variable delta
         //on compare la valeur ancienne de delta avec la nouvelle valeur. s'il y a de changement de signe (+/-), on fait un compteur de la transition des signes
         //  On doit faire la somme de toutes les valeurs n�gatives et positives du delta.
         // on stocke dans une nouvelle liste les �l�vations voisines qui poss�de la m�me �l�vation que le point i. Ceux seront les points d�g�ner�s par niveau.
         if (vertexOrdonnes.isEmpty()==false){
        	     		
    		 deltaNeg=0;
    		 deltaPos=0;
    		 delta=0;
    		 nc=0;
    		 int numBoolean=0;
    		 int numBoolean1=0;
    		 
    		 
    		 boolean estPlusGrand=false;
    		 //boolean estPlusGrand1=false;
    		 //La variables minNeg indique la valeur la plus minimale pour les points qui se trouvent plus bas que le point i (valeurs de signe n�gatif), �a sera la valeur qui restera pour calculer les talwegs
    		 //La variables maxPos indique la valeur la plus maximale pour les points qui se trouvent plus �l�v�s que le point i (valeurs de signe positif), �a sera la valeur qui restera pour calculer les cr�tes
    		
    		 double minNeg=topo.getTblSommets().get(vertexOrdonnes.get(0)).getZ()-topo.getTblSommets().get(i).getZ();
    		 double maxPos=topo.getTblSommets().get(vertexOrdonnes.get(0)).getZ()-topo.getTblSommets().get(i).getZ();
    		 int minNum=0;
    		 int maxNum=0;
    		 
    		 for (int num=0;num< vertexOrdonnes.size();num++){
    			double s=topo.getTblSommets().get(vertexOrdonnes.get(num)).getZ();
    			double p=topo.getTblSommets().get(i).getZ();
    			//double delta1=delta;
    			delta=s-p;
    			numBoolean1=numBoolean;
    	    	//estPlusGrand1=estPlusGrand;
    	    	
    	    	Points pointVoisin=topo.getTblSommets().get(vertexOrdonnes.get(num));
    	    	Points pointActuel=topo.getTblSommets().get(i);
    	    	estPlusGrand=pointActuel.estPlusGrand(pointVoisin);
    	    	
    			//estPlusGrand=plusGrand(i, vertexOrdonnes.get(num));
    			
    			// Les entiers 0, 1---true, -1, false
    			if(estPlusGrand==true){
    				numBoolean=1;
    				deltaNeg=deltaNeg+1;
    			}else if(estPlusGrand==false){
    				numBoolean=-1;
    				deltaPos=deltaPos+1;
    			}
    			
    			
    				if (numBoolean1<0 && numBoolean>0){	
    					//System.out.println("MINNUM "+i+": "+vertexOrdonnes.get(minNum));
    					
    					nc=nc+1;
    					minNeg=delta;
     					minNum=num;
    					
    			
    				}
    				if(numBoolean1>0 && numBoolean<0){
    					//System.out.println("MAXNUM "+i+": "+vertexOrdonnes.get(maxNum));
    					
    					nc=nc+1;
    					maxPos=delta;
     					maxNum=num;
    				
    				}
    			
    			//la somme de toutes les valeurs n�gatives et positives des deltas
    				//Pour les points qui se trouvent dans le m�me contour, c'est � dire, les deltas mantient le m�me signe
     				//Dans le cas o� delta1 et delta ne pr�sente pas de changement de signe, c'est-�-dire, la valeur de la diff�rence ant�rieure et l'actuelle continuent � �tre n�gative ou positive
     				//Si delta1(valeur ant�rieur) est plus petit que delta(valeur actuelle)
     				//on garde la valeur actuelle dans une liste pour �tre elimin�e, sinon on garde la valeur ant�rieure dans la liste.
     							
     				
     				//Dans le cas o� les delta sont n�gatifs, les points voisins sont plus bas que le point i. On supprime le point le plus �l�v�, cela veut dire qui on reste le point le plus bas dans la liste
     				//if (delta1<0 && delta<0){
    				if (numBoolean1>0 && numBoolean>0){		
     					 if (minNeg>=delta){
     						deleteDowner.add(vertexOrdonnes.get(minNum));
     						minNeg=delta;
     						minNum=num;
     						//System.out.println("MINNUM "+i+": "+vertexOrdonnes.get(minNum));
     					}
     					 else if(minNeg<delta){
     												
     						deleteDowner.add(vertexOrdonnes.get(num));
     						
     					 }
     				}
     				
     				
     				
     				//Dans le cas o� les delta sont positifs, les points voisins sont plus �l�v�s que le point i. On supprime le point le plus bas, cela veut dire qui on reste le point le plus �l�v� dans la liste
     				//if (delta1>0 && delta>0){
     				if(numBoolean1<0 && numBoolean<0){
     					if (maxPos<=delta){
     						deleteUpper.add(vertexOrdonnes.get(maxNum));
     						maxPos=delta;
     	 					maxNum=num;
     	 					
     					}
     					else if(maxPos>delta){
     						deleteUpper.add(vertexOrdonnes.get(num));
     						
     					}
     					//System.out.println("MAXNUM// "+i+": "+vertexOrdonnes.get(maxNum));
     				}	
    	    }
        
    	
        	
         //On applique de conditions pour d�tecter les pics,puits et cols 
         //selon la somme des deltas positives et negatives. D'ailleurs, le changement du signe de la valeur actuelle et ancienne.
//         System.out.println("nc"+nc);
         //Extraire les sommets, s1==1 les points d�g�n�r�s 
        	 
        	 
         if(deltaPos==0 && Math.abs(deltaNeg)>0){
         //if(deltaPos==0 && Math.abs(deltaNeg)>0 && nc==0 && s1==0){
         	
        	Pic pic=new Pic(topo.getTblSommets().get(i));
         	this.listPics.add(pic);
        	 
			}
         //Extraire les creux, s1==1 les points d�g�n�r�s 
         if(deltaNeg==0 && Math.abs(deltaPos)>0){
         //else if(deltaNeg==0 && Math.abs(deltaPos)>0 && nc==0 && s1==0){
         	
        	 Puit puit=new Puit(topo.getTblSommets().get(i));
        	 this.listPuits.add(puit);
        	 
         }
         
         //Extraire les cols 
         //else if(Math.abs(deltaNeg)>0 && Math.abs(deltaPos)>0&& nc==4){
         if(Math.abs(deltaNeg)>0 && Math.abs(deltaPos)>0 && nc==4){

        	 construireCheminCols(i,vertexOrdonnes);
        	 
        	        	
         	Col col=new Col(topo.getTblSommets().get(i));
         	this.listCols.add(col);
         	
         	//Dans une nouvelle liste de type hashmap, on associe le point id avec leurs 4 voisins pour construire les lignes critiques
          	for(int j=0;j<voisins.size();j++){
          		int k= Integer.parseInt(voisins.get(j));
          		voisinsCol.put(col, k);
          	}
         	
         
         	
         }
          
         
         	//montre si le point i a plus de 4 curbures, si c'est le cas, il faut laisser seulement 4. Un autre cas de col d�g�n�r�
           // REVISER l'assignation d'ID pour les cols d�g�n�r�s
          // Pour le traitement de cols d�g�n�r�s quand il'y a plus de 4 contours qui arrivent � un m�me point, il faut les consid�rer d'une mani�re diff�rente. D'abord on cr�� une liste temporelle qui stockera les 4 voisins pour chaque col.
         //Si le point ne se trouve pas � la bordure. C'est-�-dire, s'il n'y a pas connexion au point virtuel (premier �l�ment de la liste voisins avec valeur z�ro), on s�lectionne les 4 derni�res valeurs comme voisin du col, ajoute le point � la liste de cols et supprime les deux derniers.
        //On r�pite la m�me proc�dure jusqu'� la valeur de m sera plus petit de 0
       // Pour les points qui se trouvent au bordure du domaine(premier �l�ment de la liste voisins avec valeur z�ro), simplement on ajoute le point � la liste de cols.
         	if(Math.abs(deltaNeg)>0 && Math.abs(deltaPos)>0 && nc>4){
         	
         		//System.out.println("COLS DEGNEERESS++++++"+i);
         		ArrayList<Integer> voisinColTemp= new ArrayList<Integer> ();
         		
         		double m=(nc-2)/2.0;
         	
         		construireCheminCols(i,vertexOrdonnes);
    
      		
	         		while (m>0){

	         			int pos1=voisins.size()-4;
	         			
	         			
	         			for(int vt=pos1;vt<=voisins.size()-1;vt++){
	         				
	         				voisinColTemp.add(Integer.parseInt(voisins.get(vt)));
	         			}
	         			Col col=new Col(topo.getTblSommets().get(i));
	                 	this.listCols.add(col);
	                 	
	         			for(int j=0;j<voisinColTemp.size();j++){
	                  		int k= voisinColTemp.get(j);
	                  		voisinsCol.put(col, k);
	                  	}
	         			
	         			if(voisins.size()>5){
		         			voisins.remove(voisins.size()-1);
		         			voisins.remove(voisins.size()-1);
	         			}
	         			else if (voisins.size()==5){
	         				voisins.remove(voisins.size()-1);
	         			}
	         			voisinColTemp.clear(); 
	         			m=m-1;
	         		}
         		}

         		
         	}
         }
         
	}

	public void construireCheminCols(int i,ArrayList<Integer> vertexOrdonnes){
	 	// Cette partie est developp� pour laisser seulement 4 chemins qui connectent le col
	
	 	
		
		
	 	//Supprimer le premier vertex de la liste de voisins
	 	vertexOrdonnes.remove(deleteUpper);
	 	for (int j=0;j<vertexOrdonnes.size()-1;j++) {
	 		String a=Integer.toString(vertexOrdonnes.get(j));
	 		voisins.add(a);
	 	}
	 	//Si la liste pour �liminer les voisins, qui se trouvent plus bas(haut) que le point i, n'est pas vide on doit supprimer des points
	 	//supprimer tous les points qui se trouvent moins(plus) �l�v�s que le point i, ainsi on laisse un seul chemin pour suivre pour chaque courbure
	 	if(deleteDowner.isEmpty()==false){
	        	for (int j=0;j<deleteDowner.size();j++) {
	        		String del=Integer.toString(deleteDowner.get(j));
	        		//System.out.println("del"+del);
	        		voisins.remove(del);
	        		
				}
	 	}
	 	if(deleteUpper.isEmpty()==false){
	        	for (int j=0;j<deleteUpper.size();j++) {
	        		String del=Integer.toString(deleteUpper.get(j));
	        		//System.out.println("del"+del);
	        		voisins.remove(del);
	        		
				}
	 	}
	 	
	 	//Dans le cas o� la liste de voisins du point cols est plus grand que 4 registres, Il faut supprimer les valeurs qui ne correspond pas au chemin des cols.
     	// G�n�ralement dans ce cas-l�, le premier point et le dernier se trouvent sur la m�me contour. Dans la liste la premi�re valeur est la valeur de d�part mais aussi d'arriv�e, ainsi les donn�es forment un circle.
     	// Donc on calcule encore la diff�rence du point i aux points voisins qui se trouvent � la premi�re et derni�re position respectivement. 
     	//Si les differences de la premi�re valeur et de la derni�re valeur sont n�gatives(positives) pour les deux cas, il faut d�terminer le delta qui est le plus grand ou petit.
     	// Dans le cas o� les deltas sont n�gatives dans les deux cas, il faut supprimmer la valeur la plus grande. C'est-�-dire, le point qui represente la valeur plus petite restera comme r�presentant dans le contour. 
     // Dans le cas o� les deltas sont positives dans les deux cas, il faut supprimmer la valeur la plus petite. C'est-�-dire, le point qui represente la valeur plus grande restera comme r�presentant dans le contour.	
     	
     	//System.out.println(i+" SIZE voisin col---- "+voisins.size());
     	if(voisins.size()>4){

     		double s1=topo.getTblSommets().get(Integer.parseInt(voisins.get(0))).getZ();
     		double s2=topo.getTblSommets().get(Integer.parseInt(voisins.get(voisins.size()-1))).getZ();
     		double p=topo.getTblSommets().get(i).getZ();
			double delta1=s1-p;
			double delta2=s2-p;
			
			
			if(delta1<0 && delta2<0){
				if(delta1<=delta2){
					voisins.remove(voisins.size()-1);
				}
				else if(delta1>delta2){
					voisins.remove(voisins.get(0));
				}
			}else if(delta1>0 && delta2>0){
				if(delta1>=delta2){
					voisins.remove(voisins.size()-1);
				}
				else if(delta1<delta2){
					voisins.remove(voisins.get(0));
				}
			}
     	}
    }


	/**
	 * Cette methode private permet de construire les Cretes et les Ravins et de les lier aux Points Critiques. 
	 * @throws Exception 
	 */
public void constructionReseauSurface() throws Exception
	{
		
		HashMap<Integer, Pic> picsHashMap = new HashMap<Integer, Pic>();
	    HashMap<Integer, Puit> puitsHashMap = new HashMap<Integer, Puit>();
	    int idCrete=0;
	    int idTalweg=0;
		
		 for (Pic pic : this.listPics) {
	           picsHashMap.put(pic.getSommet().getId(),pic);
	        }
	        
	        
	        for (Puit puits : this.listPuits) {
	           puitsHashMap.put(puits.getSommet().getId(),puits);
	        }
		
		
		
		//Parcourir tous les �l�ments de la liste de myCols, pour obtenir les 4 changements d'�l�vation (deux plus �l�v�s, deux moins �l�v�s)
				//Parcourir chaque �l�ment du multimap voisinsCol en d�tectant si le point correspond � un point plus �l�v� ou plus bas que le point i (col)
				//En faisant la comparaison entre les �l�vations du point i avec leurs voisins, on peut d�tecter si le voisin correspond � un vertex du talweg ou de la cr�te(vertexCritique)
				//Ajouter � une liste temporelle de cr�tes(talwegs) le point i et son voisin qu'on analyse(vertexCritique)
				//Obtenir le voisin du chaque vertexCritique jusqu'� trouver le sommet(creux)
				//Le point critique se trouve quand le vertex suivant est �gal au m�me vertexCritique.
				//C'est � dire, quand le vertex se devient � un point critique, le vertex suivant est le m�me parce qu'il ne peut pas augmenter(descendre) son �l�vation
				for (Col col : this.listCols) {					
					int i=col.getSommet().getId();
					//myPointTriangle montre les triangles qui sont form�s par le point col, remplacer les caract�res non num�riques
		    		String sCols = voisinsCol.get(col).toString().replace('[',' ').replace(']',' ').trim();
		    		if(voisinsCol.get(col).size()!=4){
		    			
		    			System.out.println(i+"---"+sCols+"--"+voisinsCol.get(col).size());
		    		} 		
		    		int countCretes=0;
		    		int countTalwegs=0;
		    		//Tokenizer permet de lire chacun des valeurs stock�s s�par�es par virgule  
		        	StringTokenizer st = new StringTokenizer(sCols,", ");
		        	while (st.hasMoreTokens()){
		        		int numVoisin= Integer.parseInt(st.nextToken());
		        		
		        		ArrayList<Integer> talwegTemp= new ArrayList<Integer> ();
		        		ArrayList<Integer> creteTemp= new ArrayList<Integer> ();
		        		int vertexCrit=numVoisin;
		        		boolean isCritique=false;
		        		
		        		if(topo.getTblSommets().get(numVoisin).estPlusGrand(topo.getTblSommets().get(i))==true){
		        			creteTemp.add(i);
		        			
		        			do{
		        				creteTemp.add(vertexCrit);
		        				int vertexNext=topo.getVoisinHaut().get(vertexCrit);
		        				
		        				if(vertexNext!=vertexCrit)
		        					vertexCrit=vertexNext;
		        				else
		        					isCritique=true;
		        				
		        			}while(isCritique==false);
		        			
		        			idCrete++;
		        			//PicsHashSet donne le num�ro du point critique dans la ligne de cr�te (le dernier vertex de la cr�te) mais qu'il ne se trouve pas dans la liste de pics extraits
		        			// De cette mani�re on a des vertex qui se trouvent � la fin qu'ils n'ont pas �t� identifi�s pr�cedemment comme des points critiques 
		        			//System.out.println("temp crete"+creteTemp);
		        			 int n=creteTemp.get(creteTemp.size()-1);
		                     if(picsHashMap.get(n)==null){    
		                         System.out.println("ID du point qui n'est pas un point critique"+n);
		                        
		                     }
		                     else{
		                    	 Pic pic=picsHashMap.get(n);
		                    	 Crete ligneCrete=new Crete(col,pic);
		                    	 ligneCrete.setId(idCrete);
		                    	 pic.ajouterCretes(ligneCrete);
		                    	 
		                    	 this.listCretes.add(ligneCrete);
		                    	 
		                		 countCretes++;
		                    	 
		                    	 if(countCretes==1){
		                    		 col.setCrete1(ligneCrete);
		                    	 }
		                    	 else{
		                    		 col.setCrete2(ligneCrete);
		                    	 }
		                    	 
		                    	 for (Integer idVertex : creteTemp) {
				        				ligneCrete.ajouterIdVertex(idVertex);
									}
		                     }
		            
		        			construireLignes(creteCollection,featuresListCrete,creteTemp,"ligne de Crete",idCrete);
		        		}
		        		
		        		
		        		if(numVoisin!=0){
		        		
		        			if(topo.getTblSommets().get(numVoisin).estPlusPetit(topo.getTblSommets().get(i))==true){
		            			talwegTemp.add(i);
		            			
		            			do{
		            				talwegTemp.add(vertexCrit);
		            				int vertexNext=topo.getVoisinBas().get(vertexCrit);
		            				
		            				if(vertexNext!=vertexCrit)
		            					vertexCrit=vertexNext;
		            				else
		            					isCritique=true;
		            				
		            			}while(isCritique==false);
		            			
		            			idTalweg++;
		            		
		            			//PicsHashSet donne le num�ro du point critique dans la ligne de talweg (le dernier vertex du talweg) mais qu'il ne se trouve pas dans la liste de puits extraits
		            			// De cette mani�re on a des vertex qui se trouvent � la fin qu'ils n'ont pas �t� identifi�s pr�cedemment comme des points critiques 
		            			
		            		
		            			   int n=talwegTemp.get(talwegTemp.size()-1);
		                           if(puitsHashMap.get(n)==null){
		                        
		                        	   Puit puitVirtuel=identifierPuitsVirtuels(n);
		                        	
		                        	   Ravin ligneTalweg=new Ravin(col,puitVirtuel);
		                        		ligneTalweg.setId(idTalweg);
		                        		puitVirtuel.ajouterRavin(ligneTalweg);                        		
		                        	   			  	
		                        		this.listRavins.add(ligneTalweg);
		 		                    	countTalwegs++;
				                    	 if(countTalwegs==1){
				                    		 col.setRavin1(ligneTalweg);
				                    	 }
				                    	 else{
				                    		 col.setRavin2(ligneTalweg);
				                    	 }
		                        	   	
		                        	   	for (Integer idVertex : talwegTemp) {
		                        	    	ligneTalweg.ajouterIdVertex(idVertex);
										}
		                        	   
		                           }
		                           else{
		                        	   
		                        	    Puit puit=puitsHashMap.get(n);
		                        	   	Ravin ligneTalweg=new Ravin(col,puit);
		                        	   	ligneTalweg.setId(idTalweg);
		                        	   	puit.ajouterRavin(ligneTalweg);
		                        	   	this.listRavins.add(ligneTalweg);
		                        	   	
		                        	   	countTalwegs++;
				                    	 if(countTalwegs==1){
				                    		 col.setRavin1(ligneTalweg);
				                    	 }
				                    	 else{
				                    		 col.setRavin2(ligneTalweg);
				                    	 }
		                        	   	
		                        	    for (Integer idVertex : talwegTemp) {
		                        	    	ligneTalweg.ajouterIdVertex(idVertex);
										}
		                        	    
		                           }
		            			
		            			
		            			construireLignes(talwegCollection,featuresListTalweg,talwegTemp,"ligne de Talweg",idTalweg);
		            		}
		        		}
		        		else{
		        			idTalweg++;
		        			Puit puitVirtuel=identifierPuitsVirtuels(col.getSommet().getId());
                     	
                     	   Ravin ligneTalweg=new Ravin(col,puitVirtuel);
                     		ligneTalweg.setId(idTalweg);
                     		puitVirtuel.ajouterRavin(ligneTalweg);
                     	   this.listRavins.add(ligneTalweg);
                     	   
//                     	  if(ligneTalweg.getId()==20 || ligneTalweg.getId()==51 || ligneTalweg.getId()==22  ){
//                      		System.out.println(ligneTalweg.getId()+"!!!! Ligne de Talweg "+ligneTalweg.getCol().getSommet().getId()+"--"+ligneTalweg.getPuit().getSommet().getId());
//                      		}	 
                     	   	
                     	   countTalwegs++;
	                    	 if(countTalwegs==1){
	                    		 col.setRavin1(ligneTalweg);
	                    	 }
	                    	 else{
	                    		 col.setRavin2(ligneTalweg);
	                    	 }
                     	   	
	                       ligneTalweg.ajouterIdVertex(i);
                  	
		        		}
		        		
		        	}
		        	
				}
				
				//Pour finir,on va assigner aux pics,qui se trouvent au limit du domaine, la caract�ristique de restreint pour la simplification du r�seau
				fixerRestreints();
				

	}

/**
 * Cette methode private permet de fixer des points restreints qui ne seront pas supprim�s lorsqu'on fait la simplification du r�seau
 * Le calcul des points restreints et leurs param�tres appropri�s sont important pour conserver les caract�ristiques du terrain qui nous int�ressent(canyons sous-marins)
 * � partir de ces points, les squelettes et les bassins des canyons seront construits. 
 * On va consid�rer que la pente continentale (talus) commence � partir d'environ -180m de profondeur et finit environ -280m pour l'estuaire du Saint-Laurent
 */
private void fixerRestreints(){
	
	
	
	//ArrayList<Puit> puitsPlaineAbyssale=new ArrayList<Puit>();
	//ArrayList<Integer> puitsPlaineAbyssale=new ArrayList<Integer>();
	
	//On va assigner aux pics,qui se trouvent au limit du domaine, la caract�ristique de restreint pour la simplification du r�seau
	//Pour tous les pics qui se trouvent � la bordure du domaine total, c'est � dire ceux qui sont reli� au puitVirtuel identifiant par -1
	//Ces pics ne seront pas restreints dans les cas o� ils se trouvent � la m�me �levation des leurs cols, c'est � dire ceux qui sont des cas d�g�n�r�s
	
	
	for (Pic pic : this.getlistPics()) {
		if(topo.getTblsSommetsBordure().get((int)pic.getID())!=null){
			Puit puitVirtuel=identifierPuitsVirtuels(pic.getSommet().getId());
			if(puitVirtuel.getID()==-1){
				pic.setEstRestreint();
//				if(pic.getListCretes().size()==1){
//					pic.setEstPasRestreint();
//				}
				//else {
				if(pic.getListCretes().size()>1){
					Col col1=pic.getColPlusHaut(pic.getListCretes());
					Col col2=pic.getColPlusHautRestreint(col1);
					
					if(pic.getSommet().getZ()==col1.getSommet().getZ() && pic.getSommet().getZ()==col2.getSommet().getZ()){
						pic.setEstPasRestreint();
					}
				}
				
			}
			//pic.setEstRestreint();
			
		}
	}
	
	//Dans cette �tape, on va consid�rer que la pente continentale(talus) commence � partir d'environ -180m de profondeur et finit environ -280m pour l'estuaire du Saint-Laurent.
	//*********Premi�re partie
	//Pour tous les pics que se trouvent � la bordure du domaine dans le plateau continental(avant d'arriver au talus),c'est-�-dire les pics se trouvent au-dessus d'un profondeur de -180m
	//On trouve le cols reli�s � ces pics, puis, on trouve les puits reli�s aux cols. Ces puits seront les puits � analyser pour identifier si le puits est consid�r� comme un puits restreint.
	//On consid�re les puits qui poss�dent plusieurs de lignes de talweg, cela donnera l'apperence des ramifications � talweg, comme s'il s'agissait d'un cours d'eau. 
	//On a fix� une valeur plus grand que 3, comme la quantit� de talwegs qui arrivent au puit.
	//********Deuxi�me partie
	//Les canyons sous-marins sont des vall�es sous-marines profondes, son squelette doit �tre construit par des s�ries de puits et ils devraient aboutir � la plaine abyssale. 
	//On doit s'eloigner du plateau continental, pour cela on va analyser des caract�ristiques g�om�triques apr�s du deuxi�me puit du squelette construit. 
	//On va faire ce type d'analyse pour chaque puit du squelette qu'on essait de construire jusqu'� satisfaire les caract�ristiques g�om�triques(des variables reli�s au constraint �gales � faux)
	//Le chemin � suivre pour construire le squelette, dont le derni�re puit sera mis comme restreint, sera le suivant:
	//Du puit � analyser on prend le col le plus bas de ses voisins, ce r�sultat sera le col suivant. Si le col suivant est �gal au col pr�c�dent (cela correspondra � un cycle), on sort du boucle et met la variable de constraint=vrai
	//On va calculer une relation de distance entre le puit � Analyser et le col obtenu comme le plus bas, cette relation est faite � partir des lignes de talwegs(ravin) qui connectent ces deux points
	//Cette relation de distance, on va montrer la variation de la longueur du talweg par rapport au chemin le plus court entre les puit et col � analyser. C'est-�-dire, on va comparer le distance r�el avec la distance la plus courte entre ces deux points.
	//On fait cette relation de distance r�elle avec la plus courte parce que les points critiques d'un canyon devraient suivre un chemin tout droit ou sans beaucoup courbes entre eux. 
	//On a fix� comme des valeurs acceptables de cette relation, des seuils entre -0.7 et 7.0. Des valeurs en dehors de ce range, ils ne seront pas tenu compte des points restreint. Pourquoi?
	//Parce qu'on a vu que le comportement du r�seau de surface dans les formes candidats aux canyons, les points critiques sont plus proche quand ils vont arriver � son point final et apr�s le prochain point critique se trouve relativement loins par rapport aux pr�cedants.
	//De m�me, on va calculer une relation entre le d�niv�l� du puit/col � analyser et du puit/col pr�c�dant. De cette mani�re, on va analyser le comportement actuel et pr�cedant de la ligne de talweg.
	//On a fix� comme des valeurs acceptables de cette relation, des seuils entre -0.3 et 3.0.Des valeurs en dehors de ce range, ils ne seront pas tenu compte des points restreint. Ici, on a vu le m�me comportement qu'on a expliqu� dans la relation de distace.
	//******Troisi�me partie
	//Cependant, il y a certains de cas o� le squelette du canyon ne pr�sente pas des changements consid�rables jusqu'� atteindre son finale (la plaine abbysale)
	//Pour ces cas o� les points se trouvent au-dessous de -280m(fin du talus), on va stocker ces points dans une liste des puits abbysales. 
	HashSet<Integer> puitEvalueList=new HashSet<Integer>();
	for (Pic pic : this.getlistPics()) {
//		boolean contraint=false;
//		boolean contraintElevation=false;
//		boolean contraintDistance=false;
		
		
		//**********Premi�re Partie
		
		//if(topo.getTblsSommetsBordure().get((int)pic.getID())!=null){
		if((topo.getTblsSommetsBordure().get((int)pic.getID())!=null)&& pic.getElevation()>-180){
		 for (Crete crete : pic.getListCretes()) {
			Col col=crete.getCol();
				
			System.out.println("Col � �valuer "+col.getID());
			Puit puitAnalyse=col.getRavin2().getPuit();
			if(col.getRavin2().getPuit().getID()==-1){
				puitAnalyse=col.getRavin1().getPuit();
			}
			////////////////////////////////////////////////////////////////////////////////////////////
//			if(puitAnalyse.getID()==5526){
//				System.out.println("///////******************SHHHHHHHHHHHHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIIIIUUUUUUUUUU 118132");
//				if(puitReelsRestreints.contains(puitAnalyse)==false){
//					System.out.println("///////******************SHHHHHHHHHHHHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIIIIUUUUUUUUUU 118132");
//					puitAnalyse.setEstRestreint();
//					puitReelsRestreints.add(puitAnalyse);
//				}
//			}
//			/////////////////////////////////////////////////////////////////////////////////////////////
				if(puitAnalyse.getListRavins().size()>3){
				//if(puitAnalyse.getListRavins().size()>0){	
					
					boolean contraint=false;
					boolean contraintElevation=false;
					boolean contraintDistance=false;
					
					System.out.println("PuitAnalyse"+puitAnalyse.getID());
				
					//if(puitEvalueList.contains(puitAnalyse.getSommet().getId())==false){
//						if(puitAnalyse.getSommet().getId()==7117){
//							System.out.println("///////******************SHHHHHHHHHHHHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIIIIUUUUUUUUUU 7117");
//							}
						ArrayList<Col> listeColRestreint= new ArrayList<Col>();
						
						puitEvalueList.add(puitAnalyse.getSommet().getId());
						double DeniveleAnterieur=puitAnalyse.getElevation()-col.getElevation();
						int count=0;
						Col colAnalyseAnterieur=col;
						
						Puit puitAnterieur=puitAnalyse;
						
//						if(puitAnalyse.getSommet().getId()==133){
//						System.out.println("///////******************SHHHHHHHHHHHHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIIIIUUUUUUUUUU 7117");
//						System.out.println("puitAnalyse.getListRavins().size() "+puitAnalyse.getListRavins().size());
//						System.out.println("puitAnalyse.getID() "+puitAnalyse.getID());
//						System.out.println("contraint "+contraint);
//						System.out.println("contraintElevation "+contraintElevation);
//						System.out.println("contraintDistance "+contraintDistance);
//						
//						}
						
						//***********Deuxi�me partie
						
						//while((puitAnalyse.getListRavins().size()>1 && puitAnalyse.getID()>0 ) && contraint==false){
						while((puitAnalyse.getListRavins().size()>1 && puitAnalyse.getID()>0 ) && contraint==false && (contraintElevation==false || contraintDistance==false)){
							
//							if(puitAnalyse.getSommet().getId()==133){
//								System.out.println("///////******************SHHHHHHHHHHHHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIIIIUUUUUUUUUU 133");
//								}
							
							System.out.println("---PuitAnalyse"+puitAnalyse.getID());
						
							Col colAnalyse=puitAnalyse.getColPlusBas(puitAnalyse.getListRavins());
							if(colAnalyse==colAnalyseAnterieur){
								//contraint=true;
								if(puitAnalyse.getListRavins().size()==2){
								System.out.println("Col Analyse"+colAnalyse.getID());
								System.out.println("Col Analyse Anterieur"+colAnalyseAnterieur.getID());
								colAnalyse=puitAnalyse.getColPlusBasContraint(puitAnalyse.getListRavins(),colAnalyseAnterieur);
								System.out.println("Nouveau Col Analyse"+colAnalyse.getID());
								}else{
									contraint=true;
								}
		
							}
							
							System.out.println("---colAnalyse"+colAnalyse.getID());
							Ravin ravinPrecedant=colAnalyse.getRavin1();
							Ravin ravinSuivant=colAnalyse.getRavin2();
							if(colAnalyse.getRavin2().getPuit()==puitAnalyse){
								ravinPrecedant=colAnalyse.getRavin2();
								ravinSuivant=colAnalyse.getRavin1();
							}
							double distHorizDroite=Math.sqrt(Math.pow((ravinPrecedant.getPuit().getSommet().getX()-ravinSuivant.getPuit().getSommet().getX()),2)+
												   Math.pow((ravinPrecedant.getPuit().getSommet().getY()-ravinSuivant.getPuit().getSommet().getY()),2));
							double distHorizRavin=ravinPrecedant.calculerDistanceVertex(ravinPrecedant.getListIdVertex(), topo)+
												  ravinSuivant.calculerDistanceVertex(ravinSuivant.getListIdVertex(), topo);
							double relationDist=distHorizRavin/distHorizDroite;
							
							
							System.out.println("---Relation Dist"+relationDist); 
						
							
							double denivele=ravinPrecedant.getPuit().getSommet().calculerDenivele(ravinSuivant.getPuit().getSommet());
							
							System.out.println("---Diff. Eleva"+denivele+" Count"+count); 
							double relationDenivele=Math.abs(DeniveleAnterieur/denivele);
							
							if(count>2){
							//if(count>1){
							//if(count>0){
								if((relationDenivele>3 || relationDenivele<0.3)){
								//if((relationDenivele>10|| relationDenivele<1)){
									contraintElevation=true;
									//contraint=true;
									puitAnalyse.setEstRestreint();
									if(puitReelsRestreints.contains(puitAnalyse)==false){
									puitReelsRestreints.add(puitAnalyse);
									}
									if(listeColRestreint.contains(colAnalyse)==false){
									listeColRestreint.add(colAnalyse);
									}
									//puitAnterieur.setEstRestreint();
									System.out.println("---colRestreint"+colAnalyse.getID());
								}
								if(relationDist<0.7 || relationDist>7 ){
								//if(relationDist<1|| relationDist>10){
									contraintDistance=true;
									//contraint=true;
									puitAnalyse.setEstRestreint();
									if(listeColRestreint.contains(colAnalyse)==false){
										listeColRestreint.add(colAnalyse);
									}
									if(puitReelsRestreints.contains(puitAnalyse)==false){
										puitReelsRestreints.add(puitAnalyse);
									}
									//puitAnterieur.setEstRestreint();
									System.out.println("---colRestreint"+colAnalyse.getID());
								}
							}
							//*******Troisi�me partie
							//////////////////////////////////////////////////////////////////////////////////
							else if(count==0) {
							//else if(count<=2) {
							if(puitAnalyse.getElevation()<-280){
								
																							
								if(puitReelsRestreints.contains(puitAnalyse)==false){
									puitAnalyse.setEstRestreint();
									if(listeColRestreint.contains(colAnalyse)==false){
										listeColRestreint.add(colAnalyse);
									}
									if(puitReelsRestreints.contains(puitAnalyse)==false){
									puitReelsRestreints.add(puitAnalyse);
									}
									if(puitsPlaineAbyssale.contains(puitAnalyse.getSommet().getId())==false){
									puitsPlaineAbyssale.add(puitAnalyse.getSommet().getId());
									}
									//puitsPlaineAbyssale.add(puitAnalyse);
									
								}
								System.out.println("Puits Plaine Abbyssale"+puitsPlaineAbyssale);
							}
							}
							///////////////////////////////////////////////////////////////////////////////////
							count++;										
							colAnalyseAnterieur=colAnalyse;
							DeniveleAnterieur=denivele;
							puitAnterieur=puitAnalyse;
							
							puitAnalyse=ravinSuivant.getPuit();
							puitEvalueList.add(puitAnalyse.getSommet().getId());
							
						}
						System.out.println("---ListecolRestreint"+listeColRestreint);
						
//						if(listeColRestreint.size()>1){
//							Collections.sort(listeColRestreint,new ElevationColComparator());
//							for(int i=0;i<listeColRestreint.size();i++){
//								if(i>0){
//									Puit puitPasRestreint=listeColRestreint.get(i).getRavin1().getPuit();
//									if(puitPasRestreint.getEstRestreint()==false && puitPasRestreint.getID()>0 ){
//										puitPasRestreint=listeColRestreint.get(i).getRavin2().getPuit();
//										puitPasRestreint.setEstPasRestreint();
//										puitReelsRestreints.remove(puitPasRestreint);
//										
//									}else if(puitPasRestreint.getEstRestreint()==true && puitPasRestreint.getID()>0 ){
//										puitPasRestreint.setEstPasRestreint();
//										puitReelsRestreints.remove(puitPasRestreint);
//									}
//									if(puitPasRestreint.getID()==2207){
//										System.out.println(puitPasRestreint.getID()+"******�tat puit "+puitPasRestreint.getEstRestreint());
//									}
//								}
//							}
//						}

						
					//////////////////////////}	
				}
			}
		}
	}
	
	///////////////////////////////////////////////////////////
//	for (Puit puitAnalyse : listPuits) {
//		
//		if(puitAnalyse.getID()==5526){
//			System.out.println("///////******************SHHHHHHHHHHHHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIIIIUUUUUUUUUU 118132");
//			if(puitReelsRestreints.contains(puitAnalyse)==false){
//				System.out.println("///////******************SHHHHHHHHHHHHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIIIIUUUUUUUUUU 118132");
//				puitAnalyse.setEstRestreint();
//				puitReelsRestreints.add(puitAnalyse);
//			}
//		}
//	}
	//////////////////////////////////////////////////////////////////
	
	
	// Fixation d'un seuil d'�l�vation qui permet de laisser comme constraints les point qui se trouvent dans un interval d'�l�vation
	//Dans cette partie, on va seulement laisser comme des points restreints ceux qui se trouvent � la pente continentale(<-180 et >-280)
	// Les points qui se trouvent en dehors de ce range sera mis comme des points pas restreint, sauf les puits qui ont �t� mis comme des restreint � la plaine abyssale(<-280) 
	
	for (int i=0;i<puitReelsRestreints.size();i++) {
		Puit puitRestreint=puitReelsRestreints.get(i);
		if(puitRestreint.getElevation()>-180 || puitRestreint.getElevation()<-280){
		//if(puitRestreint.getElevation()<-280){
		//if(puitRestreint.getID()!=173045){
		//if(puitsPlaineAbyssale.contains(puitRestreint)==false){
		if(puitsPlaineAbyssale.contains(puitRestreint.getSommet().getId())==false){
			puitRestreint.setEstPasRestreint();
			puitReelsRestreints.remove(puitRestreint);
			i--;
		}
		//}
		}
	}
	
}

/**
 * Cette methode permet de construire les puits virtuels qu'il y a dans le domaine (au bordure et aux trous). 
 */	
public void construirePuitsVirtuels(){
		
	//� partir de la liste de points qui se trouvent � la bordure, on va construire les puits virtuels si le point critique est sous-jacent � la bordure du domaine ou d'un trou. 
	//Le puit virtuel � construire poss�de des coordonn�es virtuels x,y=0,0 et z=-999999
	//L'id�e est de parcourir tous les points � la bordure et de les associer un puit virtuel dans une nouvelle liste. Autrement dit, la nouvelle liste va chercher un point au bordure et rendre le puit virtuel � qui correspond.
	//On parcourt la totalit� des points � la bordure grace � la table de SommetsBordure qui rend le voisin � gauche sur la bordure
	//Chacun de ces points voisins, on les ajoute dans une liste asoci�e avec le puit virtuel cr�� par chaque trous ou domaine. On r�p�te la m�me proc�dure jusque le point d'arriv�e est �gal au point de d�part.
	//Le parcours sera fait pour tous les points qui se trouvent dans la liste de sommets � la bordure.
		Multimap<Double, Integer> sommetBordureMultimap= ArrayListMultimap.create();
		ArrayList<String>sommetsBordureCopie=new ArrayList<String>();
		Iterator<Integer> it=topo.getTblsSommetsBordure().keySet().iterator();
		while(it.hasNext()){
			Integer key=it.next();
			String skey=key.toString();
			sommetsBordureCopie.add(skey);
		}
	
		int count=0;
		
		while(sommetsBordureCopie.size()!=0){
	
	
			Integer sommetBordure=Integer.parseInt(sommetsBordureCopie.get(0));
			//System.out.println(count+"Sommetbordure"+sommetBordure);
			int sommetDepart=sommetBordure;
			count--;
			Points pointVirtuel=new Points(0,0,-999999,count);
			Puit puitVirtuel=new Puit(pointVirtuel);
			 this.listPuits.add(puitVirtuel);
			
			 //Identifier le nouveau puitVirtuel comme un point restreint pour la simplification du r�seau
			 puitVirtuel.setEstRestreint();
			
//			if(sommetBordure==1514 ){
//				System.out.println(count+"Sommetbordure"+sommetBordure);
//				System.out.println(count+"Sommets depart"+topo.getTblsSommetsBordure().get(sommetBordure));
//				System.out.println(count+"Liste de Sommets de la bordure"+sommetsBordureCopie);
//				}
			do{
				
				String stringSommet=sommetBordure.toString();
				sommetsBordureCopie.remove(stringSommet);
				//System.out.println(count+"Liste de Sommets de la bordure"+sommetsBordureCopie);
//				if(sommetBordure==1514 || sommetBordure==1520 ||sommetBordure==1523){
//				System.out.println(count+"Sommetbordure"+sommetBordure);
//				System.out.println(count+"Sommets voisin de la bordure"+topo.getTblsSommetsBordure().get(sommetBordure));
//				System.out.println(count+"Liste de Sommets de la bordure"+sommetsBordureCopie);
//				}
				
				if(topo.getTblsSommetsBordure().get(sommetBordure)==null){
					//sommetBordure=9075;
					//sommetBordure=280;
					//sommetBordure=55576;
					//sommetBordure=9084;
				}else{
					int valeurVoisin=topo.getTblsSommetsBordure().get(sommetBordure);
					sommetBordure=valeurVoisin;
				}
				
				
				//System.out.println(count+"Sommetbordure"+sommetBordure);
				this.listPuitsVirtuels.put(sommetBordure,puitVirtuel);
				sommetBordureMultimap.put(puitVirtuel.getID(), sommetBordure);
			}while(sommetBordure!=sommetDepart);
					
			
			//System.out.println(count+"Sommets de la bordure"+getlistPuitsVirtuels());
			//System.out.println(count+"Sommets de la bordure"+getlistPuitsVirtuels().get(sommetBordure).getID());
			
//			if(sommetsBordureCopie.size()==0){
//				break;
//			}
		}
		
		//System.out.println("Sommets de la bordure"+sommetBordureMultimap);
}

/**
 * Cette methode private permet d'identifier le puit virtuel qui correspond � un point qui se trouve � la bordure du domaine ou d'un trou. 
 * @throws Exception 
 */
public Puit identifierPuitsVirtuels(int n){

	Puit puitVirtuel=getlistPuitsVirtuels().get(n);
	
	return puitVirtuel;
}
/**
 * Cette methode permet de simplifier le r�seau de surface, en supprimant les pics,puits et cols selon le poid de chaque point et leurs constraintes.
 * @param p_facteurSimplificateur Le facteur qui d�termine la proportion de simplification du r�seau
 * @throws Exception 
 */
public void simplifierReseau(double p_facteurSimplificateur) throws Exception{
	
	/* ==============================================================================================
	 * Etape 1 : Traitement pour la contraction  de (pic-col) 
	 * ==============================================================================================
	 */
	
	ArrayList<Pic> tempPicsInterieur= new ArrayList<Pic> ();
	
	//Pr�mierement, on va assigner les poids de chaque pic
	for (Pic pic : this.getlistPics()) {
		pic.setPoid();
		if(pic.getEstRestreint()==false){
			tempPicsInterieur.add(pic);
		}
		
	}	
	
	//� continuation on range les valeurs de la liste de pics par son respective poid. 
	//this.getlistPics().sort(new PoidPicComparator());
	//tempPicsInterieur.sort(new PoidPicComparator());
	Collections.sort(tempPicsInterieur, new PoidPicComparator());
	
//	for (Pic pic : tempPicsInterieur) {
//	System.out.println(pic.getID()+"--Pic weight--"+pic.getPoid());
//	}
	
	int iterationPics=(int) Math.rint(tempPicsInterieur.size()*p_facteurSimplificateur);
	int countPics=0;
	
	
	if(tempPicsInterieur.size()!=0){
	while(countPics<iterationPics){
	//while((tempPicsInterieur.size()!=0)){
		
	boolean picRestreint=false;
				
//		System.out.println("iterationPics"+iterationPics);
//		System.out.println("countPics"+countPics);
		ArrayList<Crete> creteReverse=new ArrayList<Crete>();
		
		
		//On va choisir le premier valeur de la liste rang�e, c'est � dire le point qui a le plus bas poid dans le r�seau
	
		Pic picASupprimer=tempPicsInterieur.get(0);
		
		// On fait la s�lection des cr�tes du pic � supprimer, et on cherche le col voisin le plus haut pour �tre aussi supprimm�
		
		Col colMax=picASupprimer.getListCretes().get(0).getCol();
		for (Crete crete : picASupprimer.getListCretes()) {
	    		Col colVoisin= crete.getCol();
	    		boolean estPlusGrand=colMax.getSommet().estPlusGrand(colVoisin.getSommet());
	    		if(estPlusGrand==false){
	    			colMax=colVoisin;
	    			
	    		}
		}
		
		if(colMax.getEstRestreint()==true){
			picRestreint=true;
		}
		
		//On doit verifier si les puits associ�s au col des ravins � supprimer pr�sentent des relations avec d'autres ravins
		
		if(colMax.getRavin1().getPuit().getListRavins().size()<=1){
			picRestreint=true;
			//colMax.setEstRestreint();
		}else if(colMax.getRavin2().getPuit().getListRavins().size()<=1){
			picRestreint=true;
			//colMax.setEstRestreint();
		}
		
		//On doit aussi verifier si les pics associ�s au col des cretes � supprimer pr�sentent des relations avec d'autres cr�tes		
		if(colMax.getCrete1().getPic()!=picASupprimer){
			if(colMax.getCrete1().getPic().getListCretes().size()<=1){
				picRestreint=true;
			
			}
		}else if(colMax.getCrete2().getPic()!=picASupprimer){
			if(colMax.getCrete2().getPic().getListCretes().size()<=1){
				picRestreint=true;
			}					
		}
		
		ArrayList<Integer> tempVertexCrete= new ArrayList<Integer>();
		ArrayList<Integer> tempVertexCreteAux= new ArrayList<Integer>();
		Pic picAlterne=picASupprimer;
		//Du col voisin le plus �l�v�, on choisit le cr�te qui n'a pas de relation avec le pic � supprimer et stocke leurs vertex dans une liste temporelle, 
		//mais aussi on choisit la cr�te qui a relation avec le pic � supprimer, et si les vertex de cette ligne sont plus grand que deux, on ajouter les valeurs qui ne se trouvent pas aux limites de la ligne.
		//On supprime aussi la cr�te associ� au col voisin le plus �l�v� du point Alterne (point de la cr�te qui n'est pas le point � supprimer)
		if(picRestreint==false){
			if(colMax.getCrete1().getPic()!=picASupprimer){
				
	
					for(int idVertex:colMax.getCrete2().getListIdVertex()){
						if(idVertex!=colMax.getID()&& idVertex!=picASupprimer.getID()){
							tempVertexCreteAux.add(idVertex);
						}
					}
	
				Collections.reverse(tempVertexCreteAux);
				tempVertexCrete.addAll(tempVertexCreteAux);
				tempVertexCrete.addAll(colMax.getCrete1().getListIdVertex());
				picAlterne=colMax.getCrete1().getPic();
				picAlterne.supprimerCretes(colMax.getCrete1());
				creteReverse.add(colMax.getCrete1());
			}else if(colMax.getCrete2().getPic()!=picASupprimer){
				
			
					for(int idVertex:colMax.getCrete1().getListIdVertex()){
						if(idVertex!=colMax.getID()&& idVertex!=picASupprimer.getID()){
							tempVertexCreteAux.add(idVertex);
						}
					}
							
				Collections.reverse(tempVertexCreteAux);
				tempVertexCrete.addAll(tempVertexCreteAux);
				tempVertexCrete.addAll(colMax.getCrete2().getListIdVertex());
				picAlterne=colMax.getCrete2().getPic();
				picAlterne.supprimerCretes(colMax.getCrete2());
				creteReverse.add(colMax.getCrete2());
			}
		}
		
		if(picAlterne==picASupprimer){
			picRestreint=true;
		}
			
		//On parcourt nouvement la liste de cr�tes du point � supprimer. Pour les cr�tes qui ne sont pas compos�s par le point Col voisin le plus �l�v�, on fait:
		//Ajouter les vertex de l'ancienne cr�te du point Col � supprimer pour donner la continuation � la ligne critique et arriver vers un nouveau point critique
		//Associer le col qui ne sera pas supprim� avec le Pic Alterne (Pic qui ne sera pas supprim�) du col qui sera supprim�.
		//On va avoir une nouvelle relation. cretes restants(col restant, Pic Alterne).  
		//for (Crete crete : picASupprimer.getListCretes()) {
			
		if(picRestreint==false){
			for (int i=0;i<picASupprimer.getListCretes().size();i++) {	
				Crete crete=picASupprimer.getListCretes().get(i);
				if(crete.getCol()!=colMax ){
					if(crete.getCol().getCrete1()==crete){
						crete.getCol().getCrete1().ajouterIdVertexAll(tempVertexCrete);
						crete.setPic(picAlterne);
						picAlterne.ajouterCretes(crete);
		
					}else if(crete.getCol().getCrete2()==crete){
						crete.getCol().getCrete2().ajouterIdVertexAll(tempVertexCrete);
						crete.setPic(picAlterne);
						picAlterne.ajouterCretes(crete);
		
					}
				}
			}
		}
		
		
		//Maintenant on va supprimer les lignes critiques associ�es au col voisin le plus �l�v� (le col � supprimer)
		Crete crete1ASupprimerPic=colMax.getCrete1();
		Crete crete2ASupprimerPic=colMax.getCrete2();
		Ravin ravin1ASupprimerPic=colMax.getRavin1();
		Ravin ravin2ASupprimerPic=colMax.getRavin2();
			
		if(picRestreint==false){
				
			colMax.getRavin1().getPuit().supprimerRavin(ravin1ASupprimerPic);
			colMax.getRavin2().getPuit().supprimerRavin(ravin2ASupprimerPic);
		
			this.listCretes.remove(crete1ASupprimerPic);
			this.listCretes.remove(crete2ASupprimerPic);
			this.listRavins.remove(ravin1ASupprimerPic);
			this.listRavins.remove(ravin2ASupprimerPic);
			this.listCols.remove(colMax);
			this.listPics.remove(picASupprimer);
		}else if(picRestreint==true){

			picASupprimer.setEstRestreint();

			if(creteReverse.size()==1){
				//System.out.println("!!Pic Restreint"+picASupprimer.getID()+" Col � Supprimer"+colMax.getID()+ "Pic Alterne "+picAlterne.getID());
				picAlterne.ajouterCretes(creteReverse.get(0));
			}
//			for (Crete crete : picAlterne.getListCretes()) {
//				System.out.println("Valueurs de cr�tes pic Alterne "+crete.getId());
//			}
			
		}	
		
		tempPicsInterieur.remove(0);
		countPics++;
		
	
	 }
	}
	
	
	/* ==============================================================================================
	 * Etape 2 : Traitement pour la contraction  de (puit-col) 
	 * ==============================================================================================
	 */
	
	
	ArrayList<Puit> tempPuits= new ArrayList<Puit> ();
	
	//Pr�mierement, on va assigner les poids de chaque puit
	for (Puit puit: this.getlistPuits()) {
		if(puit.getListRavins().isEmpty()){
		System.out.println("!!!!!!!!Attention Ravins"+puit.getID()+puit.getListRavins());
		}
		puit.setPoid();
		if(puit.getEstRestreint()==false){
			tempPuits.add(puit);		
		}
	}
	
	//� continuation on range les valeurs de la liste de pics par son respective poid. 
	//tempPuits.sort(new PoidPuitComparator());
	
	Collections.sort(tempPuits, new PoidPuitComparator());
	
//	for (Puit puit : tempPuits) {
//	System.out.println(puit.getID()+"-- Puit weight--"+puit.getPoid());
//	}
	
	int iterationPuits=(int) Math.rint(tempPuits.size()*p_facteurSimplificateur);
	int countPuits=0;
	
	if(tempPuits.size()!=0){
	
		while(countPuits<iterationPuits){		
		//while((tempPuits.size()!=0)){		
			
			boolean puitRestreint=false;
			
//			System.out.println("iterationPuits"+iterationPuits);
//			System.out.println("countPuits"+countPuits);
			ArrayList<Ravin> ravinReverse=new ArrayList<Ravin>();
		
			//On va choisir le premier valeur de la liste rang�e, c'est � dire le point qui a le plus bas poid dans le r�seau
			//Puit puitASupprimer=this.getlistPuits().get(0);
			Puit puitASupprimer=tempPuits.get(0);

				// On fait la s�lection des cr�tes du pic � supprimer, et on cherche le col voisin le plus haut pour �tre aussi supprimm�
				

				Col colMin=puitASupprimer.getListRavins().get(0).getCol();
				for (Ravin ravin : puitASupprimer.getListRavins()) {
			    		Col colVoisin= ravin.getCol();
			    		boolean estPlusPetit=colMin.getSommet().estPlusPetit(colVoisin.getSommet());
			    		if(estPlusPetit==false){
			    			colMin=colVoisin;
			    			
			    		}
				}
				
				
				if(colMin.getEstRestreint()==true){
					puitRestreint=true;
				}
				
				//On doit verifier si les pics associ�s au col des cr�tes � supprimer pr�sentent des relations avec d'autres cr�tes
				
				if(colMin.getCrete1().getPic().getListCretes().size()<=1){
					puitRestreint=true;
					//colMin.setEstRestreint();
				}else if(colMin.getCrete2().getPic().getListCretes().size()<=1){
					puitRestreint=true;
					//colMin.setEstRestreint();
				}
				
				//On doit aussi verifier si les puits associ�s au col des talwegs � supprimer pr�sentent des relations avec d'autres talwegs		
				if(colMin.getRavin1().getPuit()!=puitASupprimer){
					if(colMin.getRavin1().getPuit().getListRavins().size()<=1){
						puitRestreint=true;
					
					}
				}else if(colMin.getRavin2().getPuit()!=puitASupprimer){
					if(colMin.getRavin2().getPuit().getListRavins().size()<=1){
						puitRestreint=true;
					}					
				}
					
				
				ArrayList<Integer> tempVertexRavin= new ArrayList<Integer>();
				ArrayList<Integer> tempVertexRavinAux= new ArrayList<Integer>();
				Puit puitAlterne=puitASupprimer;
				//Du col voisin le plus �l�v�, on choisit le ravin qui n'a pas de relation avec le pic � supprimer et stocke leurs vertex dans une liste temporelle
				//mais aussi on choisit la cr�te qui a relation avec le pic � supprimer, et si les vertex de cette ligne sont plus grand que deux, on ajouter les valeurs qui ne se trouvent pas aux limites de la ligne.
			    //On supprime aussi le ravin associ� au col voisin le plus bas du point Alterne (point du ravin qui n'est pas le point � supprimer)
				
				if(puitRestreint==false){
					if(colMin.getRavin1().getPuit()!=puitASupprimer){
						
							for(int idVertex:colMin.getRavin2().getListIdVertex()){
								if(idVertex!=colMin.getID() && idVertex!=puitASupprimer.getID() )
								tempVertexRavinAux.add(idVertex);
							}
						
							Collections.reverse(tempVertexRavinAux);
							tempVertexRavin.addAll(tempVertexRavinAux);
							tempVertexRavin.addAll(colMin.getRavin1().getListIdVertex());
							puitAlterne=colMin.getRavin1().getPuit();
							puitAlterne.supprimerRavin(colMin.getRavin1());
							ravinReverse.add(colMin.getRavin1());
						}else if(colMin.getRavin2().getPuit()!=puitASupprimer){
		
							for(int idVertex:colMin.getRavin1().getListIdVertex()){
								if(idVertex!=colMin.getID() && idVertex!=puitASupprimer.getID() )
								tempVertexRavinAux.add(idVertex);
							}
						
							Collections.reverse(tempVertexRavinAux);
							tempVertexRavin.addAll(tempVertexRavinAux);
							tempVertexRavin.addAll(colMin.getRavin2().getListIdVertex());
							puitAlterne=colMin.getRavin2().getPuit();
							puitAlterne.supprimerRavin(colMin.getRavin2());
							ravinReverse.add(colMin.getRavin2());
						}
				}
				
				
				if(puitAlterne==puitASupprimer){
					puitRestreint=true;
				}
				
				//On parcourt nouvement la liste de ravins du point � supprimer. Pour les ravins qui ne sont pas compos�s par le point Col voisin le plus bas, on fait:
				//Ajouter les vertex de l'ancien ravin du point Col � supprimer pour donner la continuation � la ligne critique et arriver vers un nouveau point critique
				//Associer le col qui ne sera pas supprim� avec le Puit Alterne (Puit qui ne sera pas supprim�) du col qui sera supprim�.
				//On va avoir une nouvelle relation. ravins restants(col restant, Puit Alterne).  
				//for (Ravin ravin : puitASupprimer.getListRavins()) {
							
				
				if(puitRestreint==false){
					for (int i=0;i<puitASupprimer.getListRavins().size();i++) {	
						Ravin ravin=puitASupprimer.getListRavins().get(i);	
						if(ravin.getCol()!=colMin){
							//System.out.println("ravins du col adjacent"+ravin.getCol().getRavin1().getId()+"---"+ravin.getCol().getRavin2().getId());
							if(ravin.getCol().getRavin1()==ravin){
								ravin.getCol().getRavin1().ajouterIdVertexAll(tempVertexRavin);
								ravin.setPuit(puitAlterne);
								puitAlterne.ajouterRavin(ravin);
								

							}else if(ravin.getCol().getRavin2()==ravin){
								ravin.getCol().getRavin2().ajouterIdVertexAll(tempVertexRavin);
								ravin.setPuit(puitAlterne);
								puitAlterne.ajouterRavin(ravin);
			
							}
							
						}
					}
			 }
			
				//Maintenant on va supprimer les lignes critiques associ�es au col voisin le plus bas (le col � supprimer)
				Crete crete1ASupprimerPuit=colMin.getCrete1();
				Crete crete2ASupprimerPuit=colMin.getCrete2();
				Ravin ravin1ASupprimerPuit=colMin.getRavin1();
				Ravin ravin2ASupprimerPuit=colMin.getRavin2();
				
				if(puitRestreint==false){
				
					
					colMin.getCrete1().getPic().supprimerCretes(crete1ASupprimerPuit);
					colMin.getCrete2().getPic().supprimerCretes(crete2ASupprimerPuit);
					
					this.listCretes.remove(crete1ASupprimerPuit);
					this.listCretes.remove(crete2ASupprimerPuit);
					this.listRavins.remove(ravin1ASupprimerPuit);
					this.listRavins.remove(ravin2ASupprimerPuit);
					this.listCols.remove(colMin);
					this.listPuits.remove(puitASupprimer);
				}else if(puitRestreint==true){
					puitASupprimer.setEstRestreint();
					
					if(ravinReverse.size()==1){
					//System.out.println("!!Puit Restreint"+puitASupprimer.getID()+" Col � Supprimer"+colMin.getID()+ "Puit Alterne "+puitAlterne.getID());
					puitAlterne.ajouterRavin(ravinReverse.get(0));
					}
					
				}
							
				tempPuits.remove(0);
				countPuits++;

		}
		
	}
		
		//On va construire nouvellement les lignes critiques, il faut vider les listes des valeurs pr�cedentes.
		creteCollection.clear();
		featuresListCrete.clear();
		talwegCollection.clear();
		featuresListTalweg.clear();

		featuresListPics.clear();
		featuresListPuits.clear();
		featuresListCols.clear();
		
		for (Crete crete : listCretes) {
		
			ArrayList<Integer> creteTemp= new ArrayList<Integer> ();
			creteTemp=crete.getListIdVertex();
			construireLignes(creteCollection,featuresListCrete,creteTemp,"ligne de Crete",crete.getId());
		}
		
		for (Ravin ravin : listRavins) {
			
			if(ravin.getListIdVertex().size()>1){
				ArrayList<Integer> talwegTemp= new ArrayList<Integer> ();
				talwegTemp=ravin.getListIdVertex();
		
				construireLignes(talwegCollection,featuresListTalweg,talwegTemp,"ligne de Talweg",ravin.getId());
			}
		}
		
}

/**
 * Cette methode permet de trouver des formes de terrain �tant des candidats potentiels � �tre des canyons.
 * Cependant, on va trouver des diff�rents formes (pas seulement des canyons), on a nomm� tous cet ensemble comme de vall�es qui correspond � des chenaux, des �boulements, des canyons,des ravins(gullies)
 * @throws Exception 
 */
public void trouverVallees() throws Exception{
	
	//On va parcourir la liste de cols qui ne se trouvent pas au talus continental, c'est-�-dire, au-dessus de -180m et au-dessous de -280m.
	//Si le puit � analyser ne correspond pas � des puits restreint sur le plaine abyssale on va supprimer les talwegs qui arrivent � ce puit.
	
	for (Col col:this.getlistCols()) {	
	
		//if(col.getRavin1().getPuit().getElevation()<-350){
		if(col.getRavin1().getPuit().getElevation()>-180 || col.getRavin1().getPuit().getElevation()<-280){
			Puit puitEvaluer=col.getRavin1().getPuit();
			//if(puitEvaluer.getID()!=173045){
			if(puitsPlaineAbyssale.contains(puitEvaluer.getSommet().getId())==false) {
			for(int i=0;i<puitEvaluer.getListRavins().size();i++){
				this.listRavins.remove(puitEvaluer.getListRavins().get(i));
				puitEvaluer.supprimerRavin(puitEvaluer.getListRavins().get(i));
				i--;
			}
			}
			//}
		}
		
		if(col.getRavin2().getPuit().getElevation()>-180 || col.getRavin2().getPuit().getElevation()<-280){
		//if (col.getRavin2().getPuit().getElevation()<-350){
			Puit puitEvaluer=col.getRavin2().getPuit();
			//if( puitEvaluer.getID()!=173045){
			if(puitsPlaineAbyssale.contains(puitEvaluer.getSommet().getId())==false){	
			for(int i=0;i<puitEvaluer.getListRavins().size();i++){
				this.listRavins.remove(puitEvaluer.getListRavins().get(i));
				puitEvaluer.supprimerRavin(puitEvaluer.getListRavins().get(i));
				i--;
			}
			}
			//}
		}
		
//		if(this.listRavins.contains(col.getRavin1())==false
//				 && this.listRavins.contains(col.getRavin2())==false){
//			System.out.println("Id point col "+col.getID());
//			this.listCretes.remove(col.getCrete1());
//			this.listCretes.remove(col.getCrete2());
//		}
			
	}
	
	//On va parcourir la liste de puits qui sont des puits virtuels en supprimant leurs talwegs associ�s. 
	
	
	for (Puit puit: this.getlistPuits()) {
		if(puit.getID()<0){
			//for(Ravin ravin: puit.getListRavins()){
			for(int i=0;i<puit.getListRavins().size();i++){
//				Col colSupprime=ravin.getCol();
//				Pic picSupprime1=colSupprime.getCrete1().getPic();
				this.listRavins.remove(puit.getListRavins().get(i));
				puit.supprimerRavin(puit.getListRavins().get(i));
				i--;
				
			}
		
		}
		
	}
	
	//On va r�p�ter la proc�dure suivant jusqu'� on trouve des pics isol�s:
	//Si le pic est un pic isol� dans le r�seau, c'est-�-dire, s'il poss�de seulement de connection avec une ligne de cr�te, il sera supprim�
	//D'abord , on va vider les cr�tes associ�s au col du pic et apr�s on va supprimer les cr�tes 
	//Ensuite, dans les cols qui pr�sentent seulement une ligne de cr�te, on va supprimer cette ligne parce que la topologie indique qu'un col doit avoir deux lignes de cr�tes
	//Le col va rester isol� sans aucune connection, donc on va supprimer ces cols et sa cr�te.
	//On parcours encore une fois la liste de pics, pour trouver des pics isol�s, on la parcours jusqu'� trouv� le pic isol�.
	
	boolean picSeuleCrete=false;
	
	do{
		picSeuleCrete=false;
		for(Pic pic:this.getlistPics()){
			if(pic.getListCretes().size()==1){
				
				if(pic.getListCretes().get(0).getCol().getCrete1()==pic.getListCretes().get(0)){
					pic.getListCretes().get(0).getCol().setCrete1(null);
				}
				else if (pic.getListCretes().get(0).getCol().getCrete2()==pic.getListCretes().get(0)){
					pic.getListCretes().get(0).getCol().setCrete2(null);
				}
				this.listCretes.remove(pic.getListCretes().get(0));
				pic.supprimerCretes(pic.getListCretes().get(0));
			}
		}
		
		//for (Col col:this.getlistCols()) {
		for(int i=0;i<this.getlistCols().size();i++){	
			Col col=this.getlistCols().get(i);
			
			
			if(col.getCrete1()==null){
				col.getCrete2().getPic().getListCretes().remove(col.getCrete2());
				this.listCretes.remove(col.getCrete2());
				this.getlistCols().remove(col);
				i--;
			}
			if(col.getCrete2()==null){
				col.getCrete1().getPic().getListCretes().remove(col.getCrete1());
				this.listCretes.remove(col.getCrete1());
				this.getlistCols().remove(col);
				i--;
			}			
		}
		
		for(Pic pic:this.getlistPics()){
//			if(pic.getID()==6467){
//				System.out.println("taille de liste de cr�tes "+pic.getListCretes().size());
//			}
			if(pic.getListCretes().size()==1){
				picSeuleCrete=true;
			}
		}
	}while(picSeuleCrete!=false);
	
	ArrayList<Col> colRestreints= new ArrayList<Col>();
	
	//On poss�de une liste des puits restreints qui ne sont pas de puits virtuels, maintenant on veut obtenir une liste des cols associ�s � ces puits.
	//Le col associ� au puit restreint ,qui nous int�resse, sera le col le plus bas de tous les col associ�s. On ajoute ce puit � une liste de cols restreints.
	for (Puit puitRestreint : puitReelsRestreints) {
		//System.out.println("puitRestreint"+puitRestreint.getID());
		Col colPrincipal=puitRestreint.getColPlusBas(puitRestreint.getListRavins());
		colRestreints.add(colPrincipal);
		System.out.println("Puit restreint "+puitRestreint.getID());
	}
	
	
	//On va parcourir la liste de tous les puits restreints pour obtenir le puit repr�sentatif pour obtenir le candidat au canyon.
	//Le col principal sera le col le plus bas associ� au puit restreint. � partir de ce col principal, on va chercher le pic � gauche et � droite de ce col.
	//Pour savoir qui sont les cols que se trouvent � gauche ou droite, on va utiliser le d�terminant entre le col principal et ses pics. 
	//De cette mani�re, on va construire math�matiquement un triagnle, dont les vertex seront le col et ses pics.
	//On va construire une ligne de base entre le col principal et le pic1. On va analyser la position du pic2 par rapport � cette ligne(col--pic1).
	//Si le d�terminant donne n�gatif, le pic2 se trouve � droite de la ligne de base. Si c'est positif � droite, si c'est z�ro sur la ligne.
	//								   pic1				
	//						*pic2	  /			 *pic2
	//								 /
	//								/
	//                           col
	int count=1;
	for (Puit puitRestreint : puitReelsRestreints) {
		ArrayList<Integer> VertexPicDroite=new ArrayList<Integer>();
		ArrayList<Crete> listeCretesVersant= new ArrayList<Crete>();
		boolean vertexSuivant=true;
		boolean vertexSuivantGauche=true;
		
		
		Col colPrincipal=puitRestreint.getColPlusBas(puitRestreint.getListRavins());
		
		double Xa=colPrincipal.getSommet().getX();
		double Ya=colPrincipal.getSommet().getY();
		double Xb=colPrincipal.getCrete1().getPic().getSommet().getX();
		double Yb=colPrincipal.getCrete1().getPic().getSommet().getY();
		double Xc=colPrincipal.getCrete2().getPic().getSommet().getX();
		double Yc=colPrincipal.getCrete2().getPic().getSommet().getY();
		
		double determ= (Xa*Yb)+(Xb*Yc)+(Xc*Ya)-((Xc*Yb)+(Xa*Yc)+(Xb*Ya));
		Pic picDroite=colPrincipal.getCrete1().getPic();
		Pic picGauche=colPrincipal.getCrete2().getPic();
		
		if(determ<0){
			picDroite=colPrincipal.getCrete2().getPic();
			picGauche=colPrincipal.getCrete1().getPic();
		}
		
		//Une fois qu'on a obtenu la position du pic par rapport � la ligne de base. On souhaite savoir l'angle correspondant entre le pic1 et le pic2. Si l'angle se trouve entre 0 et 90, on peut construire plus facil le versant
		//Pour obtenir l'angle entre les deux pics, on utilise la loi de cosinus, qui relie dans un triangle la longueur d'un c�t� � celles des deux autres et au cosinus de l'angle form� par ces deux c�t�s.
		
		double distAB=Math.sqrt(Math.pow((Xa-Xb), 2)+Math.pow((Ya-Yb), 2));
		double distAC=Math.sqrt(Math.pow((Xa-Xc), 2)+Math.pow((Ya-Yc), 2));
		double distBC=Math.sqrt(Math.pow((Xb-Xc), 2)+Math.pow((Yb-Yc), 2));
		
		//Loi des cosinus 
		double numCosA=Math.pow(distBC, 2)-Math.pow(distAC, 2)-Math.pow(distAB, 2);
		double divCosA=-2*(distAC)*(distAB);
		double cosA=Math.acos(numCosA/divCosA);
		
		//if(puitRestreint.getID()==95062){
		if(cosA*180/Math.PI>=90){
			//*********************R�viser �a
			vertexSuivant=false;
			}
		//}
		
		//if(puitRestreint.getID()==95062){
//		System.out.println("Xa: "+Xa+", Ya: "+Ya);
//		System.out.println("Xb: "+Xb+", Yb: "+Yb);
//		System.out.println("Xc: "+Xc+", Yc: "+Yc);
//		System.out.println("distAB: "+distAB);
//		System.out.println("distAC: "+distAC);
//		System.out.println("distBC: "+distBC);
//		System.out.println("cosA: "+cosA*180/Math.PI);
		//}
		
		//Dans ce moment, on va construire le versant g�n�ral qui enferme le puit restreint,le col principal et ses pics. Pour faire �a, on va parcourir les cr�tes jusqu'au point d'arriv�e(col principal) soit �gal au point de d�part(col principal)
		//La construction du versant g�n�ral sera faite par l'union de lignes de cr�tes � partir de point col principal,en passant par les pics � gauche et � droite jusqu'� arriver au col principal.
		//D'abord, on parcourt en sens horaire(� partir du col � gauche,on se d�place � droite vers le point voisin), apr�s on fait le parcours en sens antihoraire(� partir du col � droite, on se d�place � gauche vers le point voisin)
		//Pour le parcours en sens horaire: On fixe des variables pour le col de d�part(au d�but il s'agit du col principal), le pic d'arriv�e (au d�but il s'agit du pic � gauche). Apr�s, le pic d'arriv� sera devenu le pic de d�part vers le col d'arriv�
		// On fait le parcours jusqu'� le pic de d�part soit �gal au pic � droite, ou jusqu'� arriver vers un pic qui contient des plusieurs chemins � suivre(il poss�de plus de 2 lignes de cr�tes)
		Pic picGaucheDepart=picGauche;
		Col colGaucheDepart=colPrincipal;
		Col colGaucheArrive=colPrincipal;
		Pic picGaucheArrive=picGauche;
		
		
		//if(puitRestreint.getID()==74168){
		
		 
		//Si le pic de d�part poss�de deux lignes de cr�tes:
		//on cherche le col d'arriv�e � sa droite (il sera diff�rent au col de d�part qui se trouve � gauche du pic de d�part)
		//on cherche le pic d'arriv�e � sa droite (il sera diff�rent au pic de d�part qui se trouve � gauche du col de d�part)	
		
		while(vertexSuivantGauche){
				if(picGaucheDepart==picDroite){
					vertexSuivantGauche=false;
				}
				if(picGaucheDepart.getListCretes().size()==2){
					colGaucheArrive=picGaucheDepart.getListCretes().get(0).getCol();
					if (colGaucheArrive==colGaucheDepart){
						colGaucheArrive=picGaucheDepart.getListCretes().get(1).getCol();
					}
					picGaucheArrive=colGaucheArrive.getCrete1().getPic();
					if (picGaucheArrive==picGaucheDepart){
						picGaucheArrive=colGaucheArrive.getCrete2().getPic();
					}	
				}
				
				//Si le pic de d�part poss�de trois lignes de cr�tes (trois chemins diff�rents) et on va juste commencer � faire le parcours(pic de d�part �gal pic � gauche):
				//Le col d'arriv�e sera le col qui se trouve � droite du pic de d�part � gauche. Pour savoir quelle est le col � droite, on calcule d'abord le col qui se trouve plus �l�v� par rapport aux voisin, sauf le col de d�part
				//Dans cette partie, on va utiliser le d�terminant pour savois si le col obtenu est tellement le col qui se trouve � droite du pic de d�part. De la m�me mani�re faite pr�cedement, on va construire un triangle avec le d�terminant.
				//On va �valuer la position du col obtenu par rapport � une ligne de base, cette ligne sera construite par le pic de d�part � gauche et le col de d�part ou un vertex de la ligne de cr�te entre le picde d�part et le col d�part(on a pris arbitrairement le derni�re 5�me vertex de la cr�te)
				//Si le d�terminant est sup�rieur � z�ro le col obtenu se trouve � gauche. Donc, on doit calculer nouvellement le col le plus �l�v�, mais cette fois le param�tre restreint de la fonction sera le m�me col obtenu pr�cedement.
				//De cette mani�re, on peut garantir de trouver le point � droite lorsque le pic de d�part poss�de trois chemins diff�rents.
				//Pour finir le parcours en sens horaire, on remplace les variables de d�part par ceux des arriv�es, comme �a on va commencer de nouveau le boucle tandis que le pic de d�part soit �gal au pic � droit ou il y ait des plusieurs chemins � suivre.
				//								   picGaucheDepart			
				//			*colGaucheArriv�e	  /			 *colGaucheArriv�e
				//								 /
				//								/
				//                           pointAuxiliaire(colGaucheDepart)
				
				
				
				//else if(picGaucheDepart.getListCretes().size()>2 && (picGaucheDepart==picGauche)){
				else if(picGaucheDepart.getListCretes().size()==3 && (picGaucheDepart==picGauche)){
				
					
//						colGaucheArrive=picGaucheDepart.getColPlusHautRestreint(colGaucheDepart);
//						picGaucheArrive=colGaucheArrive.getCrete1().getPic();
//					if (picGaucheArrive==picGaucheDepart){
//						picGaucheArrive=colGaucheArrive.getCrete2().getPic();
//					}
					
				///////////////////////////////////////////////////////////////////////////////////////////////
					
					colGaucheArrive=picGaucheDepart.getColPlusHautRestreint(colGaucheDepart);
					
					
					Crete creteGaucheDepart=colGaucheDepart.getCrete1();
					if(creteGaucheDepart.getPic()!=picGaucheArrive){
						creteGaucheDepart=colGaucheDepart.getCrete2();
						//creteGaucheDepart=colPrincipal.getCrete2();
					}
					
					ArrayList<Integer>tempListIdVertex=new ArrayList<Integer>();
					
					if(creteGaucheDepart.getListIdVertex().get(creteGaucheDepart.getListIdVertex().size()-1)==picGaucheArrive.getSommet().getId()){
						tempListIdVertex.addAll(creteGaucheDepart.getListIdVertex());
					}else{
						Collections.reverse(creteGaucheDepart.getListIdVertex());
						tempListIdVertex.addAll(creteGaucheDepart.getListIdVertex());
					}
					
					int numVertexGauche=colGaucheDepart.getSommet().getId();
					if(tempListIdVertex.size()>7){
					//System.out.println("tempListIdVertex"+tempListIdVertex.size());
					//numVertexGauche=tempListIdVertex.get(Math.round(tempListIdVertex.size()/2));
					numVertexGauche=tempListIdVertex.get(Math.round(tempListIdVertex.size()-5));
					}
					
					//System.out.println("numVertex"+numVertexGauche);
					Points pointAuxiliaire= topo.getTblSommets().get(numVertexGauche);
//					System.out.println("pointAuxiliaire"+pointAuxiliaire.getId());
//					System.out.println("puitRestreint.getID()"+puitRestreint.getID());
					
//					if(puitRestreint.getID()==167599){
//						System.out.println("premi�re "+pointAuxiliaire.getId());
//						
//						System.out.println("**picGaucheArrive "+picGaucheArrive.getID());
//						System.out.println("**colGaucheArrive "+colGaucheArrive.getID());
//						}
					
					//System.out.println("pointAuxiliaire"+pointAuxiliaire.getId());
					
					Xa=pointAuxiliaire.getX();
					Ya=pointAuxiliaire.getY();
					Xb=picGaucheDepart.getSommet().getX();
					Yb=picGaucheDepart.getSommet().getY();
					Xc=colGaucheArrive.getSommet().getX();
					Yc=colGaucheArrive.getSommet().getY();
					
					determ= (Xa*Yb)+(Xb*Yc)+(Xc*Ya)-((Xc*Yb)+(Xa*Yc)+(Xb*Ya));
					
					
					//if(colEvaluerDroite.getSommet().getX()>=picEvaluerDroite.getSommet().getX()){
					if(determ>0){
						colGaucheArrive=picGaucheArrive.getColPlusHautRestreint(colGaucheArrive);
					}
					
					picGaucheArrive=colGaucheArrive.getCrete1().getPic();
					if (picGaucheArrive==picGaucheDepart){
						picGaucheArrive=colGaucheArrive.getCrete2().getPic();
					}
										
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
					
//					if(puitRestreint.getID()==167599){
//						System.out.println("deuxi�me "+puitRestreint.getID());
//						System.out.println("determ "+determ);
//						System.out.println("creteGaucheDepart "+creteGaucheDepart.getId());
//						System.out.println("xa"+Xa+"Ya"+Ya+"Xb"+Xb+"Yb"+Yb+"Xc"+Xc+"Yc"+Yc);
//						System.out.println("**picGaucheArrive "+picGaucheArrive.getID());
//						System.out.println("**colGaucheArrive "+colGaucheArrive.getID());
//						
//						}
									
					
				}
				
				else{
					vertexSuivantGauche=false;
				}
				picGaucheDepart=picGaucheArrive;
				colGaucheDepart=colGaucheArrive;
				
//				if(puitRestreint.getID()==173045){
//				System.out.println("**picGaucheArrive "+picGaucheArrive.getID());
//				System.out.println("**colGaucheArrive "+colGaucheArrive.getID());
//				}
//				
				
		}
	
		
	//}
		
		
		
//		Pic picDroite=colPrincipal.trouverPicDroit();
//		Pic picGauche=colPrincipal.trouverPicGauche();
		
//		if(puitRestreint.getID()!=244769 && puitRestreint.getID()!=244846 && puitRestreint.getID()!=244853
//				 && puitRestreint.getID()!=142274 && puitRestreint.getID()!=142377 && puitRestreint.getID()!=160039
//				 && puitRestreint.getID()!=205159 && puitRestreint.getID()!=205186 && puitRestreint.getID()!=172044
//				 && puitRestreint.getID()!=172092 && puitRestreint.getID()!=172190 && puitRestreint.getID()!=172138
//				 && puitRestreint.getID()!=172129 && puitRestreint.getID()!=142881 && puitRestreint.getID()!=172237
//				 && puitRestreint.getID()!=172210 && puitRestreint.getID()!=173090 && puitRestreint.getID()!=173045
//				 && puitRestreint.getID()!=132908){
		
//		if(puitRestreint.getID()!=167599 && puitRestreint.getID()!=114602 && puitRestreint.getID()!=95331 && puitRestreint.getID()!=153833
//				&& puitRestreint.getID()!=152436 && puitRestreint.getID()!=225663 && puitRestreint.getID()!=219494 && puitRestreint.getID()!=219598
//				&& puitRestreint.getID()!=219600 && puitRestreint.getID()!=225655  && puitRestreint.getID()!=172044 && puitRestreint.getID()!=172092
//				&& puitRestreint.getID()!=172190 && puitRestreint.getID()!=172138 && puitRestreint.getID()!=172129 && puitRestreint.getID()!=172237 
//				&& puitRestreint.getID()!=172210 && puitRestreint.getID()!=173090 && puitRestreint.getID()!=173045 && puitRestreint.getID()!=187783
//				&& puitRestreint.getID()!=153942 && puitRestreint.getID()!=258476  && puitRestreint.getID()!=7057 && puitRestreint.getID()!=7626
//				&& puitRestreint.getID()!=7447){
		
			//if(puitRestreint.getID()!=6077 && puitRestreint.getID()!=6608 && puitRestreint.getID()!=6429){
			
			
//		System.out.println("Puit restreint "+puitRestreint.getID());
//		System.out.println("colPrincipal "+colPrincipal.getID());
//		System.out.println("picDroite "+picDroite.getID());
//		System.out.println("picGauche "+picGauche.getID());
		
		
		// Apr�s d'avoir parcouru les cr�tes en sens horaire, on fait le parcours en sens antihoraire(� partir du col � droite, on se d�place � gauche vers le point voisin)
		//Notre point de d�part sera le col Principal qui arrivera au pic droite.La proc�dure � continuation se r�alise pour construire le versant dans les cas o� les cr�tes ne r�presentent pas de cycles(le pic � gauche est le m�me que le pic � droite)
		//on veut chercher le suivant col � arriver pour continuer � chercher le suivant pic � arriver jusqu'� arriver au col principal de nouveau
		//Le col d'arriv�e sera le col qui se trouve � gauche du pic de d�part � droite. Pour savoir quelle est le col � gauche, on calcule d'abord le col qui se trouve plus �l�v� par rapport aux voisin, sauf le col de d�part(col principal)
		//Dans cette partie, on va utiliser le d�terminant pour savois si le col obtenu est tellement le col qui se trouve � gauche du pic de d�part. De la m�me mani�re faite pr�cedement, on va construire un triangle avec le d�terminant.
		//On va �valuer la position du col obtenu par rapport � une ligne de base, cette ligne sera construite par le pic de d�part � droite et le col de d�part ou un vertex de la ligne de cr�te entre le pic de d�part et le col d�part(on a pris arbitrairement le derni�re 5�me vertex de la cr�te)
		//Si le d�terminant est inf�rieur � z�ro le col obtenu se trouve � gauche. Donc, on doit calculer nouvellement le col le plus �l�v�, mais cette fois le param�tre restreint de la fonction sera le m�me col obtenu pr�cedement.
		//De cette mani�re on va trouver le col qui se trouve � gauche du pic � droite du col principal pour commencer le parcours des cr�tes qui composent le versant. On va stocker dans des listes auntant que les vertex de points critiques comme des cr�tes.
		//								   picEvaluerDroite			
		//			*colEvaluerDroite	  /			 *colEvaluerDroite
		//								 /
		//								/
		//                           pointAuxiliaire(colGaucheDepart)
		
		if(picDroite!=picGauche){
		VertexPicDroite.add(colPrincipal.getSommet().getId());
		VertexPicDroite.add(picDroite.getSommet().getId());
		}else{
			
				VertexPicDroite.add(colPrincipal.getSommet().getId()); 
				listeCretesVersant.add(colPrincipal.getCrete1());
				listeCretesVersant.add(colPrincipal.getCrete2());
				vertexSuivant=false;
			
		}
		
		Crete cretePicDroite=colPrincipal.getCrete1();
		
		if(cretePicDroite.getPic()!=picDroite){
			cretePicDroite=colPrincipal.getCrete2();
		}
		
		listeCretesVersant.add(cretePicDroite);
		
		///////////////
//		if(picDroite==picGauche){
//			//VertexPicDroite.add(colPrincipal.getSommet().getId()); 
//			listeCretesVersant.add(colPrincipal.getCrete1());
//			listeCretesVersant.add(colPrincipal.getCrete2());
//			vertexSuivant=false;
//		}
		//////////////////////
		
		Pic picEvaluerDroite=picDroite;
		//Col colRestreint=picEvaluerDroite.trouverColDroite(colPrincipal);
		
		Col colEvaluerDroite=picEvaluerDroite.getColPlusHautRestreint(colPrincipal);
		
		
		Crete cretePicEvaluerDroite=colPrincipal.getCrete1();
		if(cretePicEvaluerDroite.getPic()!=picEvaluerDroite){
			 cretePicEvaluerDroite=colPrincipal.getCrete2();
		}
		
		int numVertex=cretePicEvaluerDroite.getListIdVertex().get(cretePicEvaluerDroite.getListIdVertex().size()-5);
		
		
		//int numVertex=cretePicEvaluerDroite.getListIdVertex().get(Math.round(cretePicEvaluerDroite.getListIdVertex().size()/2));
		
				
		//System.out.println("numVertex"+numVertex);
		Points pointAuxiliaire= topo.getTblSommets().get(numVertex);
		
		//System.out.println("pointAuxiliaire"+pointAuxiliaire.getId());
		
		Xa=pointAuxiliaire.getX();
		Ya=pointAuxiliaire.getY();
		Xb=picEvaluerDroite.getSommet().getX();
		Yb=picEvaluerDroite.getSommet().getY();
		Xc=colEvaluerDroite.getSommet().getX();
		Yc=colEvaluerDroite.getSommet().getY();
		
		determ= (Xa*Yb)+(Xb*Yc)+(Xc*Ya)-((Xc*Yb)+(Xa*Yc)+(Xb*Ya));
		
		
		//if(colEvaluerDroite.getSommet().getX()>=picEvaluerDroite.getSommet().getX()){
		if(determ<0){
			colEvaluerDroite=picEvaluerDroite.getColPlusHautRestreint(colEvaluerDroite);
		}
		
		//Col colEvaluerDroite=picEvaluerDroite.trouverColPlusHaut(colRestreint);
		
//		System.out.println("--picEvaluerDroite "+ picEvaluerDroite.getID());
//		System.out.println("--colEvaluerDroite"+ colEvaluerDroite.getID());
		//Col colEvaluerDroite=picEvaluerDroite.trouverColGauche();
		
		if(picDroite!=picGauche){
			VertexPicDroite.add(colEvaluerDroite.getSommet().getId()); 
		}
		
		//VertexPicDroite.add(colEvaluerDroite.getSommet().getId()); 
		
		for (Crete crete : picEvaluerDroite.getListCretes()) {
			if(crete.getCol()==colEvaluerDroite){
				cretePicDroite=crete;
			}
		}
		
		listeCretesVersant.add(cretePicDroite);
				
		//On va faire le parcours des lignes de cr�tes jusqu'� arriver au col principal. 
		//Si le pic de d�part poss�de deux lignes de cr�tes:
		//on cherche le col d'arriv�e � sa gauche (il sera diff�rent au col de d�part qui se trouve � droite du pic de d�part)
		//on cherche le pic d'arriv�e � sa gauche(il sera diff�rent au pic de d�part qui se trouve � droite du col de d�part)	
		
		
		while(vertexSuivant){
			
			
			Pic picEvaluerGauche=colEvaluerDroite.getCrete1().getPic();
			if(picEvaluerDroite==picEvaluerGauche){
				picEvaluerGauche=colEvaluerDroite.getCrete2().getPic();
			}
			
			Col colEvaluerGauche=picEvaluerGauche.getListCretes().get(0).getCol();
			if(picEvaluerGauche.getListCretes().size()==2){
				if(colEvaluerDroite==colEvaluerGauche){
					colEvaluerGauche=picEvaluerGauche.getListCretes().get(1).getCol();
				}
			}
			
			//Sinon si le pic de d�part poss�de des plusieurs chemins � suivre(il poss�de plus de 2 cr�tes), on va r�aliser:
			//analyser les cr�tes du pic de d�part: Si le col de la cr�te est �gal au col principal, cela veut dire qu'on a parcouri tout le versant, donc on va sortir du bucle
			//analyser les cr�tes du pic de d�part: Sinon si le col de la cr�te est �gal au col obtenu dans l'�tape pr�c�dante(le parcours en sens horaire), on va assigner une variable boolean qui indique la r�lation de ce col avec le col principal
			//S'il y a de relation entre ce col et le col principal, cela indique qu'on doit arr�ter de chercher le voisin qui se trouve � gauche parce que notre objectif est d'arriver jusqu'au col principal, donc on va sortir du bucle
			//Si ce n'est pas le cas, on continue dans le boucle en obt�niant le col � gauche. On va obtenir le col qui se trouve le plus �l�v�, hormis le col pr�cedant celui qui se trouve � droite. Comme �a, on continue toujours � gauche sans descendre vers un col plus bas.
			//                       
			// --------col---------col � gauche-------------pic � droite------col � droite
			//			|					|						|
			//			|					|						|
			//			|					|						/
			//			|					 \					   /
			//			|					  \					  /
			//           \                     --------col--------
			//			  \										/
			//			   \------------col principal----------/
				
			
			else if(picEvaluerGauche.getListCretes().size()>2){
				
				boolean colEstAssigne=false;
				boolean colReliecolPrincipal=false;
				for(Crete crete:picEvaluerGauche.getListCretes()){
					//System.out.println("crete.getCol() "+crete.getCol().getID());
					if(crete.getCol()==colPrincipal){
						colEvaluerGauche=colPrincipal;
						colEstAssigne=true;
//					}else if(colRestreints.contains(crete.getCol())==true && colEstAssigne==false){
//						System.out.println("colGaucheArrive "+colGaucheArrive.getID());
//						colEstAssigne=true;
					}else if(crete.getCol()==colGaucheArrive){
							colEstAssigne=true;
							colReliecolPrincipal=true;
							//colEvaluerGauche=picEvaluerGauche.trouverColPlusHaut(colEvaluerDroite);
					}
										
//						else{
//							colEvaluerGauche=colGaucheArrive;
//						}
					//}	
				}
				if(colReliecolPrincipal==true){
					colEvaluerGauche=colGaucheArrive;
//				}else if(colReliecolPrincipal==false){
//					colEvaluerGauche=picEvaluerGauche.trouverColPlusHaut(colEvaluerDroite);
				}
				
				if(colEstAssigne==false){
					colEvaluerGauche=picEvaluerGauche.getColPlusHautRestreint(colEvaluerDroite);
					//colEvaluerGauche=picEvaluerGauche.getColPlusBasContraint(picEvaluerGauche.getListCretes(), colEvaluerDroite);
					//colEvaluerGauche=picEvaluerGauche.trouverColPlusHaut(colEvaluerDroite);
				}
				
					

				
				
//				if(colEvaluerGauche!=picEvaluerGauche.getColPlusBasContraint(picEvaluerGauche.getListCretes(), colEvaluerDroite)){
//					colEvaluerGauche=picEvaluerGauche.getColPlusBasContraint(picEvaluerGauche.getListCretes(), colEvaluerDroite);
//				}
//				if(picEvaluerGauche.getID()==5285){
//					System.out.println("picEvaluerGauche/// "+picEvaluerGauche.getID());
//					System.out.println("ColGauche/// "+colEvaluerGauche.getID());
//					System.out.println("ColEvaluerDroite/// "+colEvaluerDroite.getID());
//					}
			
			}
			else{
				vertexSuivant=false;
			}
			
//			if(puitRestreint.getID()==173045){
////			
//			System.out.println("/picGauche "+picEvaluerGauche.getSommet().getId());
//			System.out.println("/colGauche "+colEvaluerGauche.getSommet().getId());
//			System.out.println("/picDroite "+picEvaluerDroite.getSommet().getId());
//			System.out.println("/colDroite "+colEvaluerDroite.getSommet().getId());
////			
////			vertexSuivant=false;
//		}
			
			//Si on ne peut pas trouver un col ou un pic, on sort du bucle. En plus, si le point a �t� d�j� trait�, on sort du bucle.
			//Sinon si on trouve des valeurs pour le col et le pic, on va les ajouter dans la liste de vertex du versant. 
			//De m�me,on ajoute des cr�tes associ�s aux pic et col dans une liste ce cr�tes de versant
			if(colEvaluerDroite.equals(null) || picEvaluerDroite.equals(null)){
				vertexSuivant=false;
			}else if(VertexPicDroite.contains(picEvaluerGauche.getSommet().getId())){
				vertexSuivant=false;
			}
			else if(colEvaluerDroite!=null || picEvaluerDroite!=null){
				//System.out.println("Matrice de vertex "+VertexPicDroite);
				VertexPicDroite.add(picEvaluerGauche.getSommet().getId());
				VertexPicDroite.add(colEvaluerGauche.getSommet().getId());
				
				cretePicDroite=colEvaluerDroite.getCrete1();
				
				if(cretePicDroite.getPic()!=picEvaluerGauche){
					cretePicDroite=colEvaluerDroite.getCrete2();
				}
				
				listeCretesVersant.add(cretePicDroite);
				
				for (Crete crete : picEvaluerGauche.getListCretes()) {
					if(crete.getCol()==colEvaluerGauche){
						cretePicDroite=crete;
					}
				}
				
				listeCretesVersant.add(cretePicDroite);
						
				
			}
			
//			if(picEvaluerDroite==picGauche){
//				vertexSuivant=false;
//			}
			//Si le col � �valuer est �gal au col principal, �a veut dire qu'on a parcouri tout le versant, donc on sort du boucle.
			//Pour finir on va �galer le col � �valuer � droite au col � �valuer � gauche. Aussi, le pic � �valuer � droite au pic �valuer � gauche. Avant de continuer avec le cycle suivant du cycle.
			
			if(colEvaluerGauche==colPrincipal){
				vertexSuivant=false;
			}
			colEvaluerDroite=colEvaluerGauche;
			picEvaluerDroite=picEvaluerGauche;
		}
//		if(puitRestreint.getID()==173045){
//		System.out.println("Matrice de vertex "+VertexPicDroite);
//		}
		
//		for (Crete crete : listeCretesVersant) {
//			System.out.println(crete.getId());
//		}
		
		////////}
		
		int posInit=VertexPicDroite.get(0);
		int posFinale=VertexPicDroite.get(VertexPicDroite.size()-1);
//		System.out.println("posInit"+posInit);
//		System.out.println("posFinale"+posFinale);
		
		//Dans la liste de vertex de versant si la position initiale est �gale au position finale.
		//c'est-�-dire, si on a pu finalment arriver au col principal(qui doit �tre � la premi�re et derni�re position de la liste). On peut construire le versant.
		//Le conteur indiquera l'identificateur qu'on va l'assigner au versant.
		if(posInit==posFinale){
		//construireVersant(colPrincipal,picGauche,picDroite,puitRestreint,VertexPicDroite,count);					
		construireVersant(colPrincipal,picGauche,picDroite,puitRestreint,listeCretesVersant,count);					
		count++;
		}
		
	///////////////////}
		
	}
	
	//On va construire nouvellement les lignes critiques, il faut vider les listes des valeurs pr�cedentes.
	creteCollection.clear();
	featuresListCrete.clear();
	talwegCollection.clear();
	featuresListTalweg.clear();
	

	
//	for (Versant versant : listVersants) {
//	
//	ArrayList<Integer> vertexTemp= new ArrayList<Integer> ();
//	vertexTemp=versant.getListVertexVersant();
//	//System.out.println(vertexTemp);
//	construireLignes(creteCollection,featuresListCrete,vertexTemp,"ligne de Crete",versant.getID());
//	
//	}
//
//	
//	for (Versant versant : listVersants) {
//		for (Ravin ravin : versant.getPuit().getListRavins()) {
//			
//			if(ravin.getListIdVertex().size()>1){
//			//if(topo.getTblsSommetsBordure().get((int)ravin.getCol().getID())!=null){
//				ArrayList<Integer> talwegTemp= new ArrayList<Integer> ();
//				talwegTemp=ravin.getListIdVertex();
//				construireLignes(talwegCollection,featuresListTalweg,talwegTemp,"ligne de Talweg",ravin.getId());
//			}
//		}
//	}
	
	
	//Pour tous les versants dans la liste versant, on va graphiquer leurs talwegs et cr�tes. Donc, on va construire les lignes � graphiquer � partir de la liste de vertex du talweg et du versant
	//Si le versant a deux talwegs, on va laisser seulement un, comme l'axe principal du versant. Sinon on va supprimer ce versant de la liste.
	for (int i=0;i<this.listVersants.size();i++) {
		Versant versant=this.listVersants.get(i);
				//if(topo.getTblsSommetsBordure().get((int)ravin.getCol().getID())!=null){
		ArrayList<Ravin> talwegTemp= versant.reduireTalwegs();
		if(talwegTemp.size()==2){
			ArrayList<Integer> tempVertexRavinSec=new ArrayList<Integer>();
			for (Ravin ravin : talwegTemp) {
//				if(ravin.getId()==12991 || ravin.getId()==12979 || ravin.getId()==12981){
//				
//				System.out.println("******************ravin.getListIdVertex().get(0)"+ravin.getListIdVertex().get(0));
//				System.out.println("************ravin.getListIdVertex().get(ravin.getListIdVertex().size()-1)"+ravin.getListIdVertex().get(ravin.getListIdVertex().size()-1));
//				System.out.println("******************versant.getId()"+versant.getID());
//				System.out.println("******************versant.getCol().getSommet().getId()"+versant.getCol().getSommet().getId());
//				System.out.println("******************versant.getPuit().getSommet().getId()"+versant.getPuit().getSommet().getId());	
//				}
				
				//On va stocker dans une liste temporelle les vertex du talweg qui est reli� au col du versant et au puits du versant.
				if(ravin.getCol()==versant.getCol() && ravin.getPuit()==versant.getPuit()){
					
					tempVertexRavinSec.addAll(ravin.getListIdVertex());
					if(tempVertexRavinSec.get(0)==versant.getCol().getID()){
						Collections.reverse(tempVertexRavinSec);
					}
					tempVertexRavinSec.remove(0);
					//System.out.println(versant.getCol().getID()+"---"+tempVertexRavinSec);
				}
				
				//On va laisser seulement une ligne de talweg par versant,on va ajouter au talweg le plus long(celui qui est li� au puit restreint) l'id du col principal du versant � la fin de la liste de vertex
				//De cette mani�re, on peut assurer qu'il y ait seulement un talweg qui repr�sente le squelette de la vall�e.
				//On met ce talweg comme le talweg principal du versant et le col du talweg(le premier point de la liste de vertex) comme le col secondaire du versant
				
					if(ravin.getListIdVertex().get(0)!=versant.getCol().getSommet().getId() && ravin.getListIdVertex().get(ravin.getListIdVertex().size()-1)!=versant.getCol().getSommet().getId()){
						
						if(ravin.getListIdVertex().get(0)==versant.getPuit().getSommet().getId()){
							Collections.reverse(ravin.getListIdVertex());
							//Ajout du col � la derni�re position de la liste du talweg principal
							//ravin.getListIdVertex().add(versant.getCol().getSommet().getId());
							
							
							
//						}else{
							//Ajout du col � la derni�re position de la liste du talweg principal
//							ravin.getListIdVertex().add(versant.getCol().getSommet().getId());
//						
							
						}
						
						/////////////!!!!!!!Graphique Talwegs, Squelette
						//ArrayList<Integer> talwegVertexTemp= new ArrayList<Integer> ();
//						talwegVertexTemp=ravin.getListIdVertex();
//						//System.out.println("******************talwegVertexTemp"+talwegVertexTemp);
//						construireLignes(talwegCollection,featuresListTalweg,talwegVertexTemp,"ligne de Talweg",ravin.getId());
						
						
						versant.setTalwegPrincipalVersant(ravin);
						versant.setColSecondaire(ravin.getCol());
						
					}
//					ArrayList<Integer> talwegVertexTemp= new ArrayList<Integer> ();
//					talwegVertexTemp=ravin.getListIdVertex();
//					
//					construireLignes(talwegCollection,featuresListTalweg,talwegVertexTemp,"ligne de Talweg",ravin.getId());
					
			}
			//Ajouter les vertex du talweg s�condaire au talweg principal.
			//System.out.println(versant.getCol().getID()+"---"+tempVertexRavinSec);
			versant.getTalwegPrincipal().getListIdVertex().addAll(tempVertexRavinSec);
			
			
			//////////////////!!!!!!Graphique Cr�tes
//			ArrayList<Integer> creteVertexTemp= new ArrayList<Integer> ();
//			creteVertexTemp=versant.getListVertexVersant();
//			construireLignes(creteCollection,featuresListCrete,creteVertexTemp,"ligne de Crete",versant.getID());
			
//			this.listPointsFondVallee.addAll(versant.trouverFondVall�e());
		
		}else{
			this.listVersants.remove(versant);
			i--;
		}
	}
	
	ArrayList<Versant> versantSupprimer= new ArrayList<Versant>();
	
	
	//On va parcourir la liste de versants pour comparer s'il y a de versants qui pr�sente de chevauchements avec un autre(cela indique que le squelette de la vall�e poss�de de successions de talwegs et n'est pas une seule talweg et un seul versant).
	//Donc on va parcourir la liste de versant deux fois. La premi�re pour obtenir un versant � analyser par rapport aux autres versants du deuxi�me parcours. On va faire une relation entre le versant � analyser avec le versant du deuxi�me cycle(auxiliaire).
	//On fait une copie des vertex du versant � analyser dans une liste temporelle. Sur cette liste tempor�lle, on va supprimer les valeurs correspondant aux vertex du versant auxiliaire.
	// On calcule la taille de la liste tempor�lle etde la liste originale des vertex du versant � analyser. Ensuite, on faite une relation entre ces deux valeurs. Si le r�sultat de la relation est proche � 1, les versants poss�de plusieurs point � commun.
	//On va laisser le versant qui a la plus quantit� de vertex et additionner les autres � une liste de versant � supprimer. De m�me, avec leur talweg qui sera ajout� dans une liste de talwegs � supprimer.
	for (Versant versant : listVersants) {
		
		ArrayList<Ravin> talwegSupprimer= new ArrayList<Ravin>();
		Versant versantPrincipal=versant;
		Ravin talwegPrincipal=versant.getTalwegPrincipal();
		double numCretesVersant=versant.getListVertexVersant().size();
		
		
		for (Versant versantAux : listVersants) {
			
			//System.out.println(versant.getPuit().getID()+"**"+versant.getCol().getID()+"versantAux"+versantAux.getPuit().getID()+"**"+versantAux.getCol().getID());
			ArrayList<Integer> versantTemp= new ArrayList<Integer>();
			versantTemp.addAll(versant.getListVertexVersant());
//			if(versant.getPuit().getSommet().getId()==172044)
//			System.out.println(versantTemp);
			versantTemp.retainAll(versantAux.getListVertexVersant());
//			if(versant.getPuit().getSommet().getId()==172044)
//			System.out.println(versantTemp);
			double numCretesTemp=versantTemp.size();
			double relation=numCretesVersant/numCretesTemp;
//			if(versant.getPuit().getSommet().getId()==172044){
//			System.out.println("numCretesVersant "+numCretesVersant);
//			System.out.println("numCretesTemp "+numCretesTemp);
//			System.out.println("relation "+relation);
//			}
			if(relation<1.1 && relation>0.9){
				
				versantSupprimer.add(versantAux);
				
				talwegSupprimer.add(versantAux.getTalwegPrincipal());
				if(versantAux.getListVertexVersant().size()>=versantPrincipal.getListVertexVersant().size()){
					versantPrincipal=versantAux;
				}
				if(versantAux.getTalwegPrincipal().getListIdVertex().size()>=talwegPrincipal.getListIdVertex().size()){
					talwegPrincipal=versantAux.getTalwegPrincipal();
				}
				
				
			}
			//System.out.println("versantAux"+versantAux.getColSecondaire().getSommet().getId());
			//System.out.println("--------------------------------------");
		}
		
		versantSupprimer.remove(versantPrincipal);
		talwegSupprimer.remove(talwegPrincipal);
		
		//if(versant.getPuit().getSommet().getId()==87617){
//		System.out.println(versant.getID()+" : "+versant.getPuit().getID()+"**"+versant.getCol().getID()+"versantPrincipal"+versantPrincipal.getPuit().getID()+"**"+versantPrincipal.getCol().getID());
//		System.out.println("talwegPrincipal"+talwegPrincipal.getId());
		
		Ravin talwegAnalyser=talwegPrincipal;
		int vertexCommun=talwegAnalyser.getListIdVertex().get(talwegAnalyser.getListIdVertex().size()-1);
		boolean talwegSuivant=false;
		boolean talwegConnecte=false;
		
		//Dans ce pas, on va laisser seul un talweg associ� au versant principal. Maintenant, il y a des cas o� il y a une sucession de talwegs pour d�crire le squelette. On va laisser seulement un qui contiendra les vertex de toutes les successions de talwegs.
		//On va trouver un point en commun entre le deux talwegs qui sera le dernier point du talweg � analyser et le premier point du talweg auxiliaire
		//Si on r�ussit � trouver le point en commun, on supprime le talweg Auxiliaire de la liste de talwegs � supprimer.
		//Dans une nouvelle liste on ajoute les vertex du talweg � analyser. Ensuite, cette nouvelle liste sera ajout�e � la liste de vertex du talweg principal. (Ici le talweg principal sera prolong� avec les valeurs de la succ�ssion de talwegs)
		//Avec la prolongation du talweg principal, on va avoir seulement  un talweg et un versant en evitant les chevauchements. On va assigner le talweg principal prolong� au versant principal(des versants avec chevauchement, le versant qui a la taille la plus grande)
		//On va faire l'it�ration tandis qu'il y ait un vertex commun entre deux talwegs pour �tre connect�s ou qu'il y ait une talweg suivant. Dans les cas o� il n'y a pas de point commun on ne fait aucune mofidification et on enl�ve le versant de la liste de versant � supprimer.
		//Finalement, quand on a trouv� le versant principal pour tous les cas. On va enl�ver ce versant de la liste de versant � supprimer.
		// De notre liste originale de versants, on va supprimer tous les versants associ�s � la liste de versant � supprimer.
		
		do{
			talwegSuivant=false;
			for (int i=0;i<talwegSupprimer.size();i++) {
				Ravin talwegAux=talwegSupprimer.get(i);
				//System.out.println(talwegAux.getId()+": "+talwegAux.getListIdVertex()+"talwegSupprimer"+talwegAux.getListIdVertex().get(0));
				if(talwegAux.getListIdVertex().get(0)==vertexCommun || talwegAux.getListIdVertex().get(talwegAux.getListIdVertex().size()-1)==vertexCommun){
					//if(talwegAux.getCol().getSommet().getId()==vertexCommun){
					talwegSuivant=true;
					talwegConnecte=true;
					talwegAnalyser=talwegAux;
					//System.out.println("----------talwegSupprimer"+talwegAux.getId()+talwegAux.getListIdVertex());
					talwegSupprimer.remove(talwegAux);
					i--;
				}
//				if(talwegAnalyser.getListIdVertex().get(0)==vertexCommun){
//					vertexCommun=talwegAnalyser.getListIdVertex().get(talwegAnalyser.getListIdVertex().size()-1);
//				}else if (talwegAnalyser.getListIdVertex().get(talwegAnalyser.getListIdVertex().size()-1)==vertexCommun){
//					vertexCommun=talwegAnalyser.getListIdVertex().get(0);
//				}
		
				
			}
//			System.out.println("vertexCommun1"+vertexCommun);
//			System.out.println("----------talwegAnalyser"+talwegAnalyser.getId());
			
			
			ArrayList<Integer> vertexTalwegAnalyser=new ArrayList<Integer>();
			if(talwegAnalyser.getListIdVertex().get(0)==vertexCommun){
			
				vertexCommun=talwegAnalyser.getListIdVertex().get(talwegAnalyser.getListIdVertex().size()-1);
				if(talwegAnalyser!=talwegPrincipal){
					vertexTalwegAnalyser.addAll(talwegAnalyser.getListIdVertex());
					vertexTalwegAnalyser.remove(0);
					talwegPrincipal.getListIdVertex().addAll(vertexTalwegAnalyser);
					//System.out.println("versantPrincipal"+versantPrincipal.getID());
					versantPrincipal.setTalwegPrincipalVersant(talwegPrincipal);
					//System.out.println("OUIIIIIIIII");
				}
			}else if (talwegAnalyser.getListIdVertex().get(talwegAnalyser.getListIdVertex().size()-1)==vertexCommun){
			
				vertexCommun=talwegAnalyser.getListIdVertex().get(0);
				if(talwegAnalyser!=talwegPrincipal){
					vertexTalwegAnalyser.addAll(talwegAnalyser.getListIdVertex());
					Collections.reverse(vertexTalwegAnalyser);
					vertexTalwegAnalyser.remove(0);
					talwegPrincipal.getListIdVertex().addAll(vertexTalwegAnalyser);
					//System.out.println("versantPrincipal"+versantPrincipal.getID());
					versantPrincipal.setTalwegPrincipalVersant(talwegPrincipal);
					//System.out.println("OUIIIIIIIII");
				}
				
			}
			if (talwegConnecte==false){
				//System.out.println("NooooooooN");
				versantPrincipal=versant;
//				System.out.println("versant"+versantPrincipal.getID());
//				System.out.println("talweg"+versantPrincipal.getTalwegPrincipal().getId());
				versantSupprimer.remove(versantPrincipal);
				
			}
			//vertexCommun=talwegAnalyser.getListIdVertex().get(talwegAnalyser.getListIdVertex().size()-1);
//			System.out.println("vertexCommun"+vertexCommun);
//			System.out.println("talwegPrincipal.getListIdVertex()"+talwegPrincipal.getListIdVertex());
		}while(talwegSuivant!=false);
		
		versantSupprimer.remove(versantPrincipal);
		
//		if(versantSupprimer.contains(versantPrincipal)){
//			versantSupprimer.remove(versantPrincipal);
//		}
		//}
		//if(versant.getID()==71||versant.getID()==72){
//		if(versantPrincipal.getID()==65){
//			System.out.println("ESTE ES SOCIOOOOOOO"+versantSupprimer.remove(versantPrincipal));
//			//versantSupprimer.remove(versant);
//			
//		}
		
		
		//versantPrincipal.setTalwegPrincipalVersant(talwegPrincipal);
	}
	
	listVersants.removeAll(versantSupprimer);
	
	//On va graphiquer le talweg principal(squelette) et ses cr�tes associ�es(versant)
	//On ajoute � la liste de points du fond de la vall�e la liste r�sultante de la fonction de trouver le polygone de la vall�e
	for (Versant versant : listVersants) {
		//On va cr��r un objet du type Polygone de la vall�e par la fonction de trouver le polygone de la vall�e de la classe versant.
		PolygoneVallee polygoneVallee=versant.trouverPolygoneVall�e();
		
		//On va garder la liste de points du versant dans nouvelles liste qui auront seulement les points par classe de vall�e.
		//De cette mani�re, on va avoir les polygones s�par�s par la classification de la vall�e
		//On proc�de � construire les lignes pour dessigner le polygone.
		if(polygoneVallee!=null){
			this.listPointsFondVallee.addAll(polygoneVallee.getVertexDuFondVallee());
		
		ArrayList<Integer> talwegVertexTemp= new ArrayList<Integer> ();
		talwegVertexTemp=versant.getTalwegPrincipal().getListIdVertex();
		construireLignes(talwegCollection,featuresListTalweg,talwegVertexTemp,"ligne de Talweg",versant.getTalwegPrincipal().getId());
		
		ArrayList<Integer> creteVertexTemp= new ArrayList<Integer> ();
		creteVertexTemp=versant.getListVertexVersant();
		//!!!! nouvelle partie pour aditioner les vertex de tous les versants
		topo.getVertexTotalVersant().addAll(creteVertexTemp);
		construireLignes(creteCollection,featuresListCrete,creteVertexTemp,"ligne de Crete",versant.getID());
		
		//On va cr��r un objet du type Polygone de la vall�e par la fonction de trouver le polygone de la vall�e de la classe versant.
		
		//PolygoneVallee polygoneVallee=versant.trouverPolygoneVall�e();
		
		//On va garder la liste de points du versant dans nouvelles liste qui auront seulement les points par classe de vall�e.
		//De cette mani�re, on va avoir les polygones s�par�s par la classification de la vall�e
		//On proc�de � construire les lignes pour dessigner le polygone.
		
//		if(polygoneVallee!=null){
//			this.listPointsFondVallee.addAll(polygoneVallee.getVertexDuFondVallee());
		
		
		//this.listPointsFondVallee.addAll(versant.trouverFondVall�e());
		
//		ArrayList<Integer> tempPointsFondVallee= new ArrayList<Integer> ();
//		tempPointsFondVallee.addAll(this.listPointsFondVallee);
		
		//construireLignes(fondValleeCollection,featuresListFondVallee,versant.getlistBordureFondVallee(), "Fond de la Vall�e",versant.getID());
		
		construireLignes(this.fondValleeCollection,this.featuresListFondVallee,polygoneVallee.getVertexDuFondVallee(), "Fond de la Vall�e",versant.getID());
		if(polygoneVallee.classerDesVallees()=="Canyon"){
			construireLignes(this.canyonCollection,this.featuresListCanyon,polygoneVallee.getVertexDuFondVallee(), "Canyons",versant.getID());
		}
		if(polygoneVallee.classerDesVallees()=="Chenal"){
			construireLignes(this.chenalCollection,this.featuresListChenal,polygoneVallee.getVertexDuFondVallee(), "Chenaux",versant.getID());
		}
		
		
		
		}else{
			//this.listVerificationFondVallee.addAll(versant.getlistVerificationFondVallee());
		}
		
		this.listVerificationFondVallee.addAll(versant.getlistVerificationFondVallee());
	}
	
	
	
	
}
/**
 * Cette methode permet de construire le polygone qui contient des candidats � canyons.
 * @param colPrincipal le col principal du versant.
 * @param picGauche le pic � gauche du col principal.
 * @param picDroite le pic � droite du col principal.
 * @param puitRestreint le puit reli� au col principal, le col qui a �t� restreint pour obtenir le squelette du canyon.
 * @param listCretesVersant liste de cr�tes qui vont former le polygone du versant.
 * @param ID l'identificateur du versant.
 */
public void construireVersant(Col colPrincipal, Pic picGauche, Pic picDroite,Puit puitRestreint,ArrayList<Crete> listCretesVersant,int ID){
	//Pour commencer, on va construire un nouveau objet de type versant. On va fixer comme la liste de cr�tes du versant, la liste du param�tre qu'on a trouv� dans la fonction pr�cedant de trouver le versant.
	//Pour toutes les cr�tes de la liste cr��e, on va ajouter les vertex des cr�tes � une nouvelle liste de vertex. Cette liste permet d'identifier les vertex des cr�tes. Son premi�re �l�ment va correspondre � un vertex de r�f�rence, pour le premier cycle sera le col principal. 
	//Finalement, on va ajouter un identificateur au versant, et on va l'ajouter dans une liste qui stocke les versants dans la classe r�seau de surface.
	ArrayList<Integer> vertexCretes= new ArrayList<Integer> ();
	Versant bassinVersant= new Versant(colPrincipal,picGauche,picDroite,puitRestreint,this.topo);
	
	bassinVersant.setListCretesVersant(listCretesVersant);
	
	int pointReference=colPrincipal.getSommet().getId();
	
	for (Crete crete : listCretesVersant) {
		//System.out.println("crete.getListIdVertex().get(0) "+ crete.getListIdVertex().get(0));
		//System.out.println("pointReference "+ pointReference);
		if(crete.getListIdVertex().get(0)==pointReference){
		vertexCretes.addAll(crete.getListIdVertex());
		//System.out.println("pas inverse "+ crete.getListIdVertex());
		}else{
		//System.out.println("sans inverse "+ crete.getListIdVertex());
		Collections.reverse(crete.getListIdVertex());	
		//System.out.println("inverse "+ crete.getListIdVertex());
		vertexCretes.addAll(crete.getListIdVertex());
		}
		pointReference=vertexCretes.get(vertexCretes.size()-1);
		vertexCretes.remove(vertexCretes.size()-1);
		
	}
	if(picDroite!=picGauche){
	vertexCretes.add(colPrincipal.getSommet().getId());
	}
	bassinVersant.setListVertexVersant(vertexCretes);
	bassinVersant.setID(ID);
	
	this.getlistVersants().add(bassinVersant);
	
	//bassinVersant.reduireTalwegs();
	
}

/**
 * Cette methode permet de construire les lignes Critiques (liste d'�l�ments g�om�triques et moreaux de lignes critiques) pour qu'elles soient post�rieurement dessin�es . 
 * @param lineCollection Ensemble de morceaux de lignes critiques qui servent pour le dessin des graphiques
 * @param featureList Liste d'�l�ments g�om�triques qui servent pour le dessin des graphiques
 * @param arrayList liste des vertex d'une ligne critique
 * @param name Nom de la ligne Critique(soit ligne de Cr�tes ou Ravins)
 * @param id Identificateur de la ligne Critique
 */

public void construireLignes(DefaultFeatureCollection lineCollection,List<SimpleFeature> featuresList,ArrayList<Integer> arrayList, String name, int id) throws Exception{
		

		SimpleFeatureType type=Graphique.typeLigne(name);
		
		SimpleFeatureBuilder builder = new SimpleFeatureBuilder(type);
		GeometryBuilder builder1 = new GeometryBuilder( );

		
			for(int i=1; i<arrayList.size();i++){
			//Parcourir un array de points pour obtenir leurs coordonn�es
			LineString ligne= builder1.lineString(topo.getTblSommets().get(arrayList.get(i)).getX(), topo.getTblSommets().get(arrayList.get(i)).getY(),
												  topo.getTblSommets().get(arrayList.get(i-1)).getX(), topo.getTblSommets().get(arrayList.get(i-1)).getY());

			//add the values
			builder.add(ligne);
			builder.add( name+i );		
			builder.add( id );

			SimpleFeature feature = builder.buildFeature( "fid."+ lineCollection.size());
			
			lineCollection.add(feature);
			featuresList.add(feature);
			
			}

	}


	
}
