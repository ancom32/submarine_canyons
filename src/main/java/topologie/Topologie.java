package topologie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.vividsolutions.jts.geom.Geometry;

/** 
 * <b>Classe TopologieL</b>
 * <p>
 * Cette classe represente la topologie d'une surface a partir d'une triangulation.
 * La construction de la topologie se fait à partir des fichiers shpFiles(triangles,points et lignes).
 * L'architecture de cette classe stockent les valeurs des sommets et des arêtes qui conforment chacun des triangles.
 * Par consequent, cette classe contient une liste d'hachage des Sommets, des Aretes et des Triangles. 
 * Lors de la creation de cet objet, tous les liens entre les differents objets des listes sont crees. 
 *              
 * @author Andrés Camilo Cortés Murcia
 * @since 2015-07-09
 *
 */
public class Topologie {
	
	/**
	 * Un tableau de type HashMap <Integer,Triangles>,où Integer est l'ID de l'élément, contenant les objets de type Triangles.
	 * @see Triangles
	 */
	private HashMap <Integer,Triangles> tblTriangles= new HashMap<Integer,Triangles>();
	/**
	 * Un tableau de type HashMap<Integer,Points>,où Integer est l'ID de l'élément,contenant les objets de type Points.
	 * @see Points
	 */
	private HashMap<Integer,Points> tblSommets= new HashMap<Integer,Points>();
	/**
	 * Un tableau de type HashMap<Integer,Aretes>,où Integer est l'ID de l'élément, contenant les objets de type Arêtes.
	 * @see Points
	 */
	private HashMap<Integer,Aretes> tblAretes= new HashMap<Integer,Aretes>();
	/**
	 * Un tableau de type Multimap<Integer, Integer> ,où Integer est l'ID de l'élément, contenant les triangles qui sont touchés par chacun des points.
	 */
	private Multimap<Integer, Integer> tblSommetsTriangles= ArrayListMultimap.create();
	
	/**
	 * Un tableau de type Multimap<Integer, Integer> ,où Integer est l'ID de l'élément, contenant les aretes qui sont touchés par chacun des points.
	 */
	private Multimap<Integer, Integer> tblAretesTriangles= ArrayListMultimap.create();
	
	/**
	 * Un tableau de type Multimap<Integer, Integer> ,où Integer est l'ID de l'élément, contenant les arêtes qui sont touchés par chacun des points.
	 */
	private Multimap<Integer, Integer> tblSommetsAretes= ArrayListMultimap.create();
	/**
	 * Un tableau de type HashMap<Integer,Integer> ,où Integer est l'ID de l'élément, contenant le Voisin les plus haut (Selon la pente la plus forte) pour chacun des points.
	 */
	private HashMap<Integer,Integer> voisinHaut= new HashMap<Integer,Integer>();
	/**
	 * Un tableau de type HashMap<Integer,Integer>,où Integer est l'ID de l'élément, contenant le Voisin les plus bas (Selon la pente la plus forte) pour chacun des points.
	 */
	private HashMap<Integer,Integer> voisinBas= new HashMap<Integer,Integer>();
	
	/**
	 * Un tableau de type HashMap<Integer,Integer>,où Integer est l'ID de l'élément, contenant les sommets qui se trouvent à la bordure du domaine ou de trous à l'intérieur du domaine .
	 */
	private HashMap<Integer,Integer> tblSommetsBordure= new HashMap<Integer,Integer>();
	
	private ArrayList<Integer> vertexTotalVersant=new ArrayList<Integer>();
		
	/** 
	 * Ce constructeur permet de construire un objet Topologie à partir de fichiers de type ShpFile(triangles,point et lignes).
	 * Lors de la construction de cet objet, la methode private construireTopologie() est appelee. 
	 * Celle-ci creer les liens entre les Sommets, les Aretes et les Triangles. 
	 * @param  shpFileTriangle Fichier shapeFile des Triangles.
	 * @see Triangles
	 * @param  shpFilePoint Fichier shapeFile des Points.
	 * @see Points	
	 * @param  shpFileLigne Fichier shapeFile des Lignes.
	 * @see Lignes
	 */
	public Topologie(File shpFileTriangle, File shpFilePoint, File shpFileArete ) {
	
		
		try {
			this.construireTopologie(shpFileTriangle, shpFilePoint,shpFileArete);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste des triangles.
	 * @return HashMap<Integer, Triangles> La liste des Sommets(Clé:Id, Valeur:Triangle).
	 */
	public HashMap<Integer, Triangles> getTblTriangles() {
		return tblTriangles;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste des Points.
	 * @return HashMap<Integer, Points> La liste des Sommets(Clé:Id, Valeur:Point).
	 */
	public HashMap<Integer, Points> getTblSommets() {
		return tblSommets;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste des Arêtes.
	 * @return HashMap<Integer, Arêtes> La liste des Arêtes(Clé:Id, Valeur:Arêtes).
	 */
	public HashMap<Integer, Aretes> getTblAretes() {
		return tblAretes;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste des Points-Triangles.
	 * @return Multimap<Integer, Integer> La liste des Sommets-Triangles(Clé:Id Point, Valeur:Id Triangles).
	 */
	public Multimap<Integer, Integer> getTblSommetsTriangles() {
		return tblSommetsTriangles;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste des Aretes-Triangles.
	 * @return Multimap<Integer, Integer> La liste des Aretes-Triangles(Clé:Id Arete, Valeur:Id Triangles).
	 */
	public Multimap<Integer, Integer> getTblAretesTriangles() {
		return tblAretesTriangles;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste des Points-Aretes.
	 * @return Multimap<Integer, Integer> La liste des Sommets-Aretes(Clé:Id Point, Valeur:Id Triangles).
	 */
	public Multimap<Integer, Integer> getTblSommetsAretes() {
		return tblSommetsAretes;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste du voisin les plus haut pour chaque point.
	 * @return HashMap<Integer, Integer> La liste des Voisins les plus Hauts(Clé:Id Point, Valeur:Id Point voisin les plus haut).
	 */
	public HashMap<Integer, Integer> getVoisinHaut() {
		return voisinHaut;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste du voisin les plus bas pour chaque point.
	 * @return HashMap<Integer, Integer> La liste des Voisins les plus bas(Clé:Id Point, Valeur:Id Point voisin les plus bas).
	 */
	public HashMap<Integer, Integer> getVoisinBas() {
		return voisinBas;
	}
	/**
	 * Cette methode permet d'avoir acces a la liste du voisin au bordure du domaine pour chaque point.
	 * @return HashMap<Integer, Integer> La liste des points au Bordure(Clé:Id Point, Valeur:Id Point voisin au bordure).
	 */
	public HashMap<Integer, Integer> getTblsSommetsBordure() {
		return tblSommetsBordure;
	}
	
	public ArrayList<Integer> getVertexTotalVersant()
	{
		return this.vertexTotalVersant;
	}

	/** Cette methode permet de construire la topologie d'une surface en etablissant les liens entre les Sommets, les Aretes et les Triangles
	 *  a partir des fichiers de type shp file pour les Triangles,les points et les arêtes, 
	 *  en créant des liens entre ces objets. 
	 * @param  shpFileTriangle Fichier shapeFile des Triangles.
	 * @see Triangles
	 * @param  shpFilePoint Fichier shapeFile des Points.
	 * @see Points	
	 * @param  shpFileLigne Fichier shapeFile des Lignes.
	 * @see Lignes
	 */
	private void construireTopologie(File shpFileT, File shpFileP, File shpFileA ) throws IOException
	{
		/* ==============================================================================================
		 * Etape 1 : creation des éléments points à partir des Aretes. 
		 * ==============================================================================================
		 */
	
 		// Parcourir tous les éléments du feaure 2(Points)
	   //Calculer les coordonnées pour tous les éléments qui se trouvent au feature 2 avec 5 décimaux de précision
		 // Connect to the shapefile,connecter à la base de donnée, obtenir la source et créer un simpleFeature
        FileDataStore dataStoreT = FileDataStoreFinder.getDataStore(shpFileT);
        SimpleFeatureSource shpTriangle = dataStoreT
                .getFeatureSource();
        FileDataStore dataStoreP = FileDataStoreFinder.getDataStore(shpFileP);
        SimpleFeatureSource shpPoint = dataStoreP
                .getFeatureSource();
        FileDataStore dataStoreA = FileDataStoreFinder.getDataStore(shpFileA);
        SimpleFeatureSource shpArete = dataStoreA
                .getFeatureSource();
		
		FeatureCollection<SimpleFeatureType, SimpleFeature> collection1 = shpTriangle.getFeatures(); 	   
  	    FeatureCollection<SimpleFeatureType, SimpleFeature> collection2 = shpPoint.getFeatures();
  	    FeatureCollection<SimpleFeatureType, SimpleFeature> collection3 = shpArete.getFeatures();
 	    
  	    FeatureIterator<SimpleFeature> features1 = collection1.features();
  		FeatureIterator<SimpleFeature> features2 = collection2.features();	
  		FeatureIterator<SimpleFeature> features3 = collection3.features();
  		
  		HashMap<String,Integer> myTempCoorPoint= new HashMap<String,Integer>();
  		HashMap<String,Integer> myTempCoorLine= new HashMap<String,Integer>();
        
  		// Parcourir tous les éléments du feaure 2(Points)
 	   //Calculer les coordonnées pour tous les éléments qui se trouvent au feature 2 avec 5 décimaux de précision
 	    try {
	            while(features2.hasNext()) {
	            
	            	SimpleFeature feature2 = features2.next();       	            		
	            	Geometry geometry2=(Geometry) feature2.getDefaultGeometry();

	            	double CoorX=Math.round(geometry2.getCoordinate().x*100000)/100000.0;
	            	double CoorY=Math.round(geometry2.getCoordinate().y*100000)/100000.0;
//	            	double CoorX=Math.round(geometry2.getCoordinate().x*1000)/1000.0;
//	            	double CoorY=Math.round(geometry2.getCoordinate().y*1000)/1000.0;
	            	
	            	//Obtenir l'identificateur de chaque élément
	            	// appliquer une relation qui permet de relier les coordonnées X,Y. Et obtenir une valeur unique pour chaque point (variable dist)
	            	//ajouter à la liste de HashMap el identificateur de l'élément point. Ici, l'id de chaque élément du Hashmap va être la variable dist
	            	//créer un nouveau objet de type Point avec les coordonnées du Feature point, et l'ajouter dans une liste de type Point.
	            	int index=feature2.getID().indexOf('.');
	            	int id=Integer.parseInt(feature2.getID().substring(index+1,feature2.getID().length()));
	            	
	            	String coordPoint=String.valueOf(CoorX)+","+String.valueOf(CoorY);
	            	myTempCoorPoint.put(coordPoint, id);
	            	Points point= new Points(geometry2.getCoordinate().x,geometry2.getCoordinate().y,geometry2.getCoordinate().z,id);
	            	this.tblSommets.put(id, point);
	            	
	            	
	            }	
	            }finally {
	            	features2.close();
	            	dataStoreP.dispose();
 	    	    }
 	   /* ==============================================================================================
		 * Etape 2 : creation des éléments Arêtes à partir des points. 
		 * ==============================================================================================
		 */
 	    try {
 	    	// Parcourir tous les éléments du feaure 3(aretes)
 	    	//Calculer les coordonnées pour les points 1 et 2 qui composent chaque arête du feature3 avec 5 décimaux de précision
	            while(features3.hasNext()) {
	        
	            	ArrayList<Integer> myTempAreteP= new ArrayList<Integer>();
	     
	            	SimpleFeature feature3 = features3.next();       	            		
	            	Geometry geometry3=(Geometry) feature3.getDefaultGeometry();
	            	
	            	double  X1=Math.round(geometry3.getCoordinates()[0].x*100000)/100000.0;
	            	double  X2=Math.round(geometry3.getCoordinates()[1].x*100000)/100000.0;
	            	double  Y1=Math.round(geometry3.getCoordinates()[0].y*100000)/100000.0;
	            	double  Y2=Math.round(geometry3.getCoordinates()[1].y*100000)/100000.0;
	            	
//	            	double  X1=Math.round(geometry3.getCoordinates()[0].x*1000)/1000.0;
//	            	double  X2=Math.round(geometry3.getCoordinates()[1].x*1000)/1000.0;
//	            	double  Y1=Math.round(geometry3.getCoordinates()[0].y*1000)/1000.0;
//	            	double  Y2=Math.round(geometry3.getCoordinates()[1].y*1000)/1000.0;
	       
	            	
	            	// obtenir les coordonnées du centroïde de chaque arête. DeltaX, deltaY.
	            	//Obtenir l'identificateur de chaque élément
	            	// appliquer une relation qui permet de relier les coordonnées X,Y des sommets des arêtes. 
	            	//À partir de cette relation, nous pouvons obtenir une valeur unique pour chaque arête (variable dist) et chaque sommet de l'arête(dist1 et dist 2)
	            	//Ajouter dans un HashMap, l'identificateur de la feature arête. Ici, l'id de chaque élément du Hashmap va être la variable distL
	            	//ajouter dans une ArrayList, l'id des points qui construisent chaque arêtes, à travers la relation des coordonnées du sommet (dist 1 et dist2)
	            	// dist 1 et dist 2 seront insérées comme des paramètres dans le HashMap de la feature précedente (Points), ainsi on obtient l'id pour chaque point de la feature3
	            	//Finalement, on crée un nouveau objet de type arête. On ajoute le nouveau objet au Hashmap. Ici, l'id de chaque élément du Hashmap va être l'id de l'arête.
	            	            	
	            	
	            	int index=feature3.getID().indexOf('.');
	            	int id=Integer.parseInt(feature3.getID().substring(index+1,feature3.getID().length()));
	            	
	            	
	            	String coordLigne=String.valueOf(X1)+","+String.valueOf(Y1)+"+"+String.valueOf(X2)+","+String.valueOf(Y2);
	            	String coordPoint1=String.valueOf(X1)+","+String.valueOf(Y1);
	            	String coordPoint2=String.valueOf(X2)+","+String.valueOf(Y2);
	            	
	          
	            	
	            	myTempCoorLine.put(coordLigne, id);
	            	myTempAreteP.add(myTempCoorPoint.get(coordPoint1));
	            	myTempAreteP.add(myTempCoorPoint.get(coordPoint2));
	            	
	                Aretes arete= new Aretes(myTempAreteP.get(0),	myTempAreteP.get(1));
	                arete.setId(id);
 	            this.tblAretes.put(id, arete);
 	           this.tblSommetsAretes.put(myTempAreteP.get(0), id);
 	           this.tblSommetsAretes.put(myTempAreteP.get(1), id);
	            }
	            
	            }finally {
	            	features3.close();
	            	dataStoreA.dispose();
 	    	    }
 	   /* ==============================================================================================
		 * Etape 3 : creation des éléments Triangles à partir des arêtes et des points. 
		 * ==============================================================================================
		 */
 	    
 	    try {
 	        //Parcourir tous les éléments du feaure 1(triangles)
 	       //Calculer les coordonnées pour chaque sommet du triangle feature1 avec 5 décimaux de précision
 	       //pour chaque sommet on doit appliquer une relation qui permet de relier les coordonnées X,Y. Ainsi, on peut obtenir une valeur unique pour chaque sommet du triangle (variable dist)
 	       ////ajouter dans une ArrayList, l'id des points de chaque triangle, à traves la relation des coordonnées du sommet (dist)
 	    	
 	    	while (features1.hasNext()) {
 	            SimpleFeature feature1 = features1.next();           
 	            
 	            Geometry geometry1=(Geometry)feature1.getDefaultGeometry();
 	            
 	            ArrayList<Integer> myTempPoint= new ArrayList<Integer>();
 	        	ArrayList<Integer> myTempAreteT= new ArrayList<Integer>();

 	            //Permet de recuperer et afficher les sommets
 	            for (int i = 0; i<geometry1.getCoordinates().length-1;i++){
	   	            	 double CoorX=Math.round(geometry1.getCoordinates()[i].x*100000)/100000.0;
	   	            	 double CoorY=Math.round(geometry1.getCoordinates()[i].y*100000)/100000.0;
 	            	
// 	            	double CoorX=Math.round(geometry1.getCoordinates()[i].x*1000)/1000.0;
//  	            	 double CoorY=Math.round(geometry1.getCoordinates()[i].y*1000)/1000.0;
	   	            	 
	   	            	String coordPoint=String.valueOf(CoorX)+","+String.valueOf(CoorY);
	   	            	 myTempPoint.add(myTempCoorPoint.get(coordPoint));
	   	            	
	   	            	 //Calculer les coordonnées pour les points 1 et 2 qui composent chaque arête du triangle avec 5 décimaux de précision
	   	            	 // obtenir le centroïde de chaque arête. DeltaX, deltaY.
	   	            	 // appliquer une relation qui permet de relier les coordonnées X,Y des sommets des arêtes. 
	 	            	 //À partir de cette relation, nous pouvons obtenir une valeur unique pour chaque arête (variable distL) 
	   	            	 //Obtenir l'identificateur de chaque triangle
	   	            	 //créer un nouveau objet de type triangle et l'ajouter à un Hashmap
	   	            	 //Relier les triangles qui possèdent un point dans un'objet Multimap
	   	            	 //on met l'id du triangle et on cherche l'id de chaque sommet du triangle.
	   	            	 //Ainsi l'objet multimap va garder pour chaque point les triangles qui appartient à ce point
	   	            	
	   	            	double X1=Math.round(geometry1.getCoordinates()[i].x*100000)/100000.0;
		            	double X2=Math.round(geometry1.getCoordinates()[i+1].x*100000)/100000.0;
		            	double Y1=Math.round(geometry1.getCoordinates()[i].y*100000)/100000.0;
		            	double Y2=Math.round(geometry1.getCoordinates()[i+1].y*100000)/100000.0;
	   	            	 
//	   	            	double X1=Math.round(geometry1.getCoordinates()[i].x*1000)/1000.0;
//		            	double X2=Math.round(geometry1.getCoordinates()[i+1].x*1000)/1000.0;
//		            	double Y1=Math.round(geometry1.getCoordinates()[i].y*1000)/1000.0;
//		            	double Y2=Math.round(geometry1.getCoordinates()[i+1].y*1000)/1000.0;
	   	            	   	            	
		            	String coordLigne=String.valueOf(X1)+","+String.valueOf(Y1)+"+"+String.valueOf(X2)+","+String.valueOf(Y2);
		            	

	   	            	if(myTempCoorLine.get(coordLigne)==null){
	   	            	
	   	            	coordLigne=String.valueOf(X2)+","+String.valueOf(Y2)+"+"+String.valueOf(X1)+","+String.valueOf(Y1);
	   	         
	   	            		
	   	            	}
	   	            	
	   	            	myTempAreteT.add(myTempCoorLine.get(coordLigne));
	   	          
 	            }

	            int index=feature1.getID().indexOf('.');	          
 	            int IdTriangle=Integer.parseInt(feature1.getID().substring(index+1,feature1.getID().length()));
 	            //System.out.println(IdTriangle);
 	            Triangles triangle= new Triangles(IdTriangle,myTempPoint.get(0),myTempPoint.get(1),myTempPoint.get(2),
 	                 				myTempAreteT.get(0),myTempAreteT.get(1),myTempAreteT.get(2));
 	           
 	            this.tblTriangles.put(IdTriangle, triangle);
 	            this.tblSommetsTriangles.put(myTempPoint.get(0), IdTriangle);
 	            this.tblSommetsTriangles.put(myTempPoint.get(1), IdTriangle);
 	            this.tblSommetsTriangles.put(myTempPoint.get(2), IdTriangle);
 	            
 	            this.tblAretesTriangles.put(myTempAreteT.get(0), IdTriangle);
	            this.tblAretesTriangles.put(myTempAreteT.get(1), IdTriangle);
	            this.tblAretesTriangles.put(myTempAreteT.get(2), IdTriangle);
 	            
     	        }
 	    }finally {
 	    	features1.close();
 	    	dataStoreT.dispose();
 	    	
 	    }
 	    Points pInfinite= new Points(0,0,-999999,0);
 		this.tblSommets.put(0,pInfinite);
	}

	
	/** Cette methode permet de ranger les vertex voisins au tour d'un point avec un ordre en sens antihoraire. De cette manière on peut faire un tour entier avec les vertex voisins.
	 *  À partir de la liste qui fait la rélation entre points-triangle, on peut savoir quels sont les triangles qui touchent à un point. 
	 *  On extrait les vertex des triangles et on les range en sens antihoraire.
	 *  @param i Id du point à évaluer et ranger leurs voisins.
	 */
	public ArrayList<Integer> rangerVertex(int i){
		
		 HashSet<Integer> setPoints= new HashSet<Integer> ();
		 HashSet<Integer> setAretes= new HashSet<Integer> ();
		 ArrayList<Integer> vertexOrdonnes= new ArrayList<Integer> ();
	     ArrayList<Aretes> aretesOrdonnees= new ArrayList<Aretes>();
		 
		//Parcours tout point du modèle 
		//this.tblSommetsTriangle montre les triangles qui sont formés par le point i, remplacer les caractères pas numériques
		String sTriangle = this.tblSommetsTriangles.get(i).toString().replace('[',' ').replace(']',' ').trim();
		
		//Tokenizer permet de lire chacun des valeurs stockés séparées par virgule  
    	StringTokenizer st = new StringTokenizer(sTriangle,", ");
    	while (st.hasMoreTokens()){
    	 int numTri= Integer.parseInt(st.nextToken());
    	 //Obtenir les sommets des triangles
    	 int s1=this.tblTriangles.get(numTri).getS1();
    	 int s2=this.tblTriangles.get(numTri).getS2();
    	 int s3=this.tblTriangles.get(numTri).getS3(); 
    	 
    	 //Obtenir les aretes des triangles
    	 int a1=this.tblTriangles.get(numTri).getA1();
    	 int a2=this.tblTriangles.get(numTri).getA2();
    	 int a3=this.tblTriangles.get(numTri).getA3();
    	
    	 //ajouter les sommets qui composent chaque triangle à une nouvelle liste de points
    	 setPoints.add(s1);
    	 setPoints.add(s2);
    	 setPoints.add(s3);
    	 
    	 //ajouter les arêtes qui composent chacun des triangles à une nouvelle liste d'arêtes
    	 //les arêtes sont ceux qui sont indépendentes du point à évaluer (i).
    	 if(s1!=i && s2!=i)
    	 setAretes.add(a1);
    	 else if(s2!=i && s3!=i)
    	 setAretes.add(a2);
    	 else if(s3!=i && s1!=i)
    	 setAretes.add(a3);
 
    	      	         	 
    	}
    	
    	// a partir de la liste des sommets de chaque triangle (setPoints), on doit trouver les valeurs les plus élevées ou les plus bas.
    	// Ce pas servira pour savoir le chemin a suivre d´un col vers un pic/puits
    	   	
    	int idMin=this.tblSommets.get(i).getId();
    	int idMax=this.tblSommets.get(i).getId();
    	
    	double min=0;
    	double max=0;
    	
    	Points pointActuel= this.tblSommets.get(i);
    	
    	for(int numPoint:setPoints){
    		
    		Points pointVoisin= this.tblSommets.get(numPoint);
    		double elevation= pointActuel.calculePente(pointVoisin);

    		if(elevation<min){
    			min=elevation;
    			idMin=numPoint;
    		}
    		if(elevation>max){
    			max=elevation;
    			idMax=numPoint;
    			
    		}
    		if(elevation==0){
    			boolean estPlusGrand=pointActuel.estPlusGrand(pointVoisin);
    			boolean estPlusPetit=pointActuel.estPlusPetit(pointVoisin);
    			if(estPlusGrand==false){
    				idMax=numPoint;
    			}
    			if(estPlusPetit==false){
    				idMin=numPoint;
    			}
    			
    		}
    	}    	
    	
    	
    	//Calcul avec le critère du point le plus Haut(Bas) , le point avec l'élévation las plus haute(basse)
//    	int idMin=this.tblSommets.get(i).getId();
//    	int idMax=this.tblSommets.get(i).getId();
//    	
//    	Points min=this.tblSommets.get(i);
//    	Points max=this.tblSommets.get(i);
//    	
//    
//    	
//    	for(int numPoint:setPoints){
//    		
//    		Points pointVoisin= this.tblSommets.get(numPoint);
//    		
//    		boolean estPlusGrand=max.estPlusGrand(pointVoisin);
//    		boolean estPlusPetit=min.estPlusPetit(pointVoisin);
//    		
//    		if(estPlusPetit==false){
//    			min=pointVoisin;
//    			idMin=numPoint;
//    		}
//    		if(estPlusGrand==false){
//    			max=pointVoisin;
//    			idMax=numPoint;
//    			
//    		}
//    	}
    	  	
    	
    	
    	//on va stockes le point le plus bas et le plus élevé dans deux Hashmaps, dont l'identificateur sera le point i
    	voisinBas.put(i, idMin);
    	voisinHaut.put(i, idMax);
    	
    	//si un point possède plus d'un triangle,on doit identifier le premier triangle de la liste
    	if(this.tblSommetsTriangles.get(i).size()>1){
    	//à partir de la liste de triangles tokenizer, on trouve le premier triangle
    	int premierTriangle=Integer.parseInt(sTriangle.substring(0,sTriangle.indexOf(",")));
    	//On cherche de trouver les voisins du point i, en sens antihoraire.
    	//les variables n, m indiquent: n= point de départ, m= l'autre point qui se trouve dans la même arête de n(à droite de n)
    	// n point suivant d'i. m point précedant d'i.
    	//la séquence de sommets du triangle suive une diréction horaire. s1-s2-s3 (ex: s2 à droite de s1)
    	//       *  	i		*
    	//		  \	  /	   \    /
    	//		   \ /		\  /
    	// 		   	/________\/
    	//          n		 m
    	int m=0,n=0;
    	if(this.tblTriangles.get(premierTriangle).getS1()==i){
    		m=this.tblTriangles.get(premierTriangle).getS3(); 
    		n=this.tblTriangles.get(premierTriangle).getS2();         		       		
    	}
    	else if (this.tblTriangles.get(premierTriangle).getS2()==i){
    		m=this.tblTriangles.get(premierTriangle).getS1(); 
    		n=this.tblTriangles.get(premierTriangle).getS3();
    	}
    	else if (this.tblTriangles.get(premierTriangle).getS3()==i){
    		m=this.tblTriangles.get(premierTriangle).getS2(); 
    		n=this.tblTriangles.get(premierTriangle).getS1();        		
    	}
    	
    
    	
    	vertexOrdonnes.add(n);
    	
    	int n1=n;
    	
    	//cycle qui trouve les voisins au tour d'i en sens antihoraire. 
    	do{
    	aretesOrdonnees.clear();
    	//cycle qui permet de trouver les arêtes du point n, puis elles seront stockées dans une nouvelle liste
    	for(int numArete:setAretes){
    		if(this.tblAretes.get(numArete).getS1()==n || this.tblAretes.get(numArete).getS2()==n){
    			aretesOrdonnees.add(this.tblAretes.get(numArete));
    			
    		}
    	}
    	
//    	if(i==289){
//        	System.out.println("**************************************i "+i+" n"+n+"m "+m);
//        	System.out.println("aretes ordonnes size"+aretesOrdonnees.size());
//        	}
    	//Si la liste d'arêtes qui partagent le point n est égal à deux (dans cette liste, on ne garde pas l'arête relié au sommet i)
    	//trouver l'arête qui ne possède pas de relation avec le point m, ça sera l'arête où on va localiser le prochain point à parcourir (*)
    	// Ici, la variable m aura la valeur ancienne de n, et la variable n aura la valeur du nouveau point (*)
//      		*_____ i		
       	//		 \	  /	  \    
       	//		  \  /	   \  
       	// 		   \/_______\
       	//          n		 m
    	//System.out.println("tamano arete"+aretesOrdonnees.size());
    			if(aretesOrdonnees.size()==2){
    				for(Aretes aO:aretesOrdonnees){
    					if(aO.getS1()!=m && aO.getS2()!=m){
    						if(aO.getS1()==n){
    							m=aO.getS1();
    							n=aO.getS2();
    							vertexOrdonnes.add(n);
    						}else if(aO.getS2()==n){
    							m=aO.getS2();
    							n=aO.getS1();
    							vertexOrdonnes.add(n);
    						}
    					}
    				}
//    				if(i==289){
//  					  System.out.println("Vertex Ordonnes avec Zéro "+i+"-----"+vertexOrdonnes);
//  					  }
    			}else if(aretesOrdonnees.size()==1){
    				// Si la liste d'arêtes est différente de 2, ça nous indique que le point i se trouve
    				// à la bordure de notre domaine, pour ce que le point sera ignorée
    				// on asigne la valeur originale de n pour terminer le cycle
    				
    		    	 // ajouter les arêtes qui ont un lien avec le point à évaluer (i)
    		    	 //	dans la liste d'aretesTemp, on va garder les arêtes qui touche le point i.
    		    	 //la structure d'arêtes est: a1: s1-s2, a2: s2-s3 , a3: s3-s1
    		    	
    		    	tblSommetsBordure.put(n,i);  		 
//    		    	if(i==289 ){
//    					  System.out.println("Vertex Ordonnes avec Zéro "+i+"-----"+vertexOrdonnes);
//    					  }		
    				
    				vertexOrdonnes.clear();
    				ArrayList<Integer> tempVertexOrdonnes= new ArrayList<Integer> ();
    				
    				aretesOrdonnees.clear();
    				
    				for(int numArete:setAretes){
    	        		if(this.tblAretes.get(numArete).getS1()==n || this.tblAretes.get(numArete).getS2()==n){
    	        			aretesOrdonnees.add(this.tblAretes.get(numArete));	
    	        		}
    	        	}
    				
//    				IF(i==289){
//    		        	SYSTEM.OUT.PRINTLN("SETARETES "+SETARETES);
//    		        	SYSTEM.OUT.PRINTLN("ARETES ORDONNES SIZE"+ARETESORDONNEES.SIZE());
//    		        	}    				
    				if(aretesOrdonnees.size()==1){

    					tempVertexOrdonnes.add(0);
    					tempVertexOrdonnes.add(n);
    					tempVertexOrdonnes.add(m);
    					
    					int m1=n;
    					n=m;        					
    					m=m1;
    					int count=0;
    					
    					do{
    					aretesOrdonnees.clear();
    					for(int numArete:setAretes){
        	        		if(this.tblAretes.get(numArete).getS1()==n || this.tblAretes.get(numArete).getS2()==n){
        	        			aretesOrdonnees.add(this.tblAretes.get(numArete));    			
        	        		}
        	        	}
    					count++;
    					//System.out.println(i+" size aretesOrdonnees"+aretesOrdonnees.size()+ "size SetAretes"+setAretes.size());
    					for(Aretes aO:aretesOrdonnees){
        					if(aO.getS1()!=m && aO.getS2()!=m){
        						if(aO.getS1()==n){
        							m=aO.getS1();
        							n=aO.getS2();
        							tempVertexOrdonnes.add(n);
        							//vertexOrdonnes.add(n);
        						}else if(aO.getS2()==n){
        							m=aO.getS2();
        							n=aO.getS1();
        							tempVertexOrdonnes.add(n);
        							//vertexOrdonnes.add(n);
        						}
        					}
        					
        				}
    					}while(aretesOrdonnees.size()!=1);
    					if(count!=setAretes.size()){
    						System.out.println("Warning! deux ou plus trous"+i);
    						TestTopologie.preuveTrous.add(i);
    						tempVertexOrdonnes.add(0);
    						
    						
    					}
    					tempVertexOrdonnes.add(0);
    					//vertexOrdonnes.add(0);
    					//System.out.println("Vertex Ordonnes avec Zéro "+i+"-----"+tempVertexOrdonnes);
    					
    					  for(int j=tempVertexOrdonnes.size()-1;j>=0;j--){
    	    		       		 vertexOrdonnes.add(tempVertexOrdonnes.get(j));
    	    		       	 }
    	    					
//    					  if(i==289){
//    					  System.out.println("Vertex Ordonnes avec Zéro "+i+"-----"+vertexOrdonnes);
//    					  }
    					
    				}else{
    					System.out.println("Warning! Point bordure avec 2 arêtes autour de line 621 Topologie"+i);
    					    				}
    				        				
    				n=n1;

    			}
    			else{
    				System.out.println("Warning! Point bordure avec 2 arêtes autour de line 628 topologie"+i);
    				vertexOrdonnes.clear();
    				n=n1;
    			}
    			
    	}while(n!=n1);
    		
    	
    	}
    	
    	else if(this.tblSommetsTriangles.get(i).size()==1){
    		ArrayList<Integer> IndexTemp= new ArrayList<Integer> ();
    	
    		IndexTemp.add(i);
    		int triUni = Integer.parseInt(sTriangle);
    		 
    		int m=0,n=0;
        	if(this.tblTriangles.get(triUni).getS1()==i){
        		m=this.tblTriangles.get(triUni).getS3(); 
        		n=this.tblTriangles.get(triUni).getS2();         		       		
        	}
        	else if (this.tblTriangles.get(triUni).getS2()==i){
        		m=this.tblTriangles.get(triUni).getS1(); 
        		n=this.tblTriangles.get(triUni).getS3();
        	}
        	else if (this.tblTriangles.get(triUni).getS3()==i){
        		m=this.tblTriangles.get(triUni).getS2(); 
        		n=this.tblTriangles.get(triUni).getS1();        		
        	}
        	
    		vertexOrdonnes.add(0);
    		vertexOrdonnes.add(m);
    		vertexOrdonnes.add(n);
    		vertexOrdonnes.add(0);
    		
    	    tblSommetsBordure.put(n,i);  
    		
    		//System.out.println("Vertex Ordonnes avec Zéro "+i+"-----"+vertexOrdonnes);
    	}
    	else {
    		System.out.println(this.tblSommetsTriangles.get(i).size()+" num point "+i);
    	}
    	
    	return vertexOrdonnes;
    	//System.out.println(vertexOrdonnes);
		
	}
		

}
