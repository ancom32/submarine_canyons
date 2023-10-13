package topologie;


import graphiques.Graphique;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Parameter;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JParameterListWizard;
import org.geotools.swing.wizard.JWizard;
import org.geotools.util.KVP;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import surfaceNetwork.ReseauSurface;
import surfaceNetwork.TestReseauSurface;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;





/** 
 * <b> Classe Principale(Main) </b>
 * <p> 
 * Cette classe represente la clase principale du projet où les instances des objets seront construites.
 * De la même manière, cette clase possede une méthode pour affficher les couches (displayLayers) et construire une carte.
         
 * @author Andrés Camilo Cortés Murcia
 * @since 2015-08-11
 * 
 */

public class Principale{
	
	public static HashMap <Integer,Triangles> myTriangle= new HashMap<Integer,Triangles>();
	public static HashMap<Integer,Points> myPoint= new HashMap<Integer,Points>();
	public static HashMap<Integer,Aretes> myEdge= new HashMap<Integer,Aretes>();

	public static ArrayList<Double> myTempCoordinates= new ArrayList<Double>();
	public static Multimap<Integer, Integer> myPointTriangle= ArrayListMultimap.create();
	
	public static ArrayList<Integer> myPics= new ArrayList<Integer>();
	public static ArrayList<Integer> myPuits= new ArrayList<Integer>();
	public static ArrayList<Integer> myCols= new ArrayList<Integer>();
	
	public static List<SimpleFeature> featuresListCrete = new ArrayList<SimpleFeature>();
	public static List<SimpleFeature> featuresListTalweg = new ArrayList<SimpleFeature>();
	public static List<SimpleFeature> featuresListPics = new ArrayList<SimpleFeature>();
	public static List<SimpleFeature> featuresListPuits = new ArrayList<SimpleFeature>();
	public static List<SimpleFeature> featuresListCols = new ArrayList<SimpleFeature>();
	
	public static Layer layerCretes;
	public static Layer layerRavins;
	public static Layer layerPics;
	public static Layer layerPuits;
	public static Layer layerCols;
	public static Layer layerPointsfondVallee;
	public static Layer layerFondVallee;
	public static Layer LayerCanyon;
	public static Layer LayerChenal;
	
	
    
	/**
     *Variable où on va stocker l'instance d'un objet de type topologie de manière générale pour la classe principale.
     */
	static Topologie topo1;
	/**
     *Variable où on va stocker l'instance de un bojet de type réseau de surface de manière générale pour la classe principale.
     */
	static ReseauSurface rsf1;
	
	
	public static void main(String[] args) throws Exception {
		//Définir les paramètres du fichier à lire:
				//Un fichier de type ShapeFile et extension shp
		        List<Parameter<?>> list = new ArrayList<Parameter<?>>();
		        list.add(new Parameter<File>("shapeT", File.class, "Shapefile",
		                "Shapefile Triangles (Polygones)", new KVP(Parameter.EXT, "shp")));
		        list.add(new Parameter<File>("shapeP", File.class, "Shapefile",
		                "Shapefile Sommets (Points)", new KVP(Parameter.EXT, "shp")));
		        list.add(new Parameter<File>("shapeA", File.class, "Shapefile",
		                "Shapefile Arêtes (Lignes)", new KVP(Parameter.EXT, "shp")));
		        
		        //Édition des étiquettes de la fenêtre principale de l'application
		        // reconaitre le shp comme un fichier et obtenir les paramètres de connection
		        //Invoquer la méthode displayLayers
		        JParameterListWizard wizard = new JParameterListWizard("Affichage de TIN",
		                "Inserer les suivantes couches", list);
		        int finish = wizard.showModalDialog();

		        if (finish != JWizard.FINISH) {
		            System.exit(0);
		        }
		        File shapeFileT = (File) wizard.getConnectionParameters().get("shapeT");
		        File shapeFileP = (File) wizard.getConnectionParameters().get("shapeP");
		        File shapeFileA = (File) wizard.getConnectionParameters().get("shapeA");
		       
		        //construire la topologie des données
		        Topologie topo= new Topologie(shapeFileT,shapeFileP, shapeFileA);	
		        topo1=topo;
		        displayLayers(shapeFileT,shapeFileP, shapeFileA);
//		        TestTopologie.structurePoint(topo1);
//		        TestTopologie.structureArete(topo1);
//		        TestTopologie.StructureTriangle(topo1);         	          	   
//         	    TestTopologie.structurePointArete(topo1);
//         	    TestTopologie.structurePointTriangle(topo1);
    }
		
	
	  /**
     * Cette méthode permet d'afficher les couches dans une cadre graphique d'une carte.  
     * 
     * @param shpFileT
     *            Le shpFile de Polygones(Triangles) du TIN
     * @param shpFileP 
     * 			  le ShpFile de Points(Sommets) du TIN
     * @param shpFileA 
     * 			  le ShpFile de Lignes(Arêtes) du TIN
     */
	
	//Cette méthode permet de visualiser les couches de shp sur une carte
    public static void displayLayers(File shpFileT, File shpFileP, File shpFileA) throws Exception {
    	
    	 // Connecter au shpFile,connecter à la base de donnée, obtenir la source et créer un simpleFeature
        FileDataStore dataStoreT = FileDataStoreFinder.getDataStore(shpFileT);
        SimpleFeatureSource shpTriangle = dataStoreT
                .getFeatureSource();
               
        // Créer une instance de Type MapContent et Mettre un nouveau titre
        final MapContent map = new MapContent();
        map.setTitle("Identification de Canyons Sous-Marins par Reseaux de Surface");
        
     // Créer le style d'affichage de la couche du TIN et ajouter à la carte
        Style shpStyle = SLD.createPolygonStyle(Color.RED, null, 0.0f);
      
        Thread.sleep(1000);
        Layer shpLayerT = new FeatureLayer(shpTriangle, shpStyle);
        Thread.sleep(1500);
        map.addLayer(shpLayerT);
        
        final JMapFrame mapFrame= new JMapFrame(map);
        
        // Activer le tableau de couches
        mapFrame.enableLayerTable( true );
   
        // zoom in, zoom out, pan, show all
        mapFrame.enableToolBar( true );
       
   
        // Permet de localiser le cursor et les limits actuels de la souris
        mapFrame.enableStatusBar( true );

        // À la barre d'outils, on ajoute les boutons pour les processus d'extraction des canyons sous-marins
        
           JToolBar toolBar = mapFrame.getToolBar();
           
           /**
            *Boutton qui permet de faire l'extraction des Points Critiques(Pics,Puits et Cols).
            */
           JButton btn = new JButton("Points");
           toolBar.addSeparator();
           toolBar.add(btn);
           /**
            *Boutton qui permet de faire l'extraction des Lignes Critiques(Pics,Puits et Cols).
            */
           JButton btn1= new JButton("Lignes");
           toolBar.add(btn1);
           /**
            *Boutton qui permet d'extraire les couches à format shpfile.
            */
           JButton btn2= new JButton("Exporter");
           toolBar.add(btn2);
           /**
            *Boutton qui permet d'extraire les points qui se trouvent à la bordure du domaine de données et de leurs trous internes.
            */
           JButton btn3= new JButton("Bordure");
           toolBar.add(btn3);
           /**
            *Boutton qui permet de simplifier le réseau de surface.
            */
           JButton btn4= new JButton("Simplifier");
           toolBar.add(btn4);
           
           /**
            *Boutton qui permet d'identifier les éléments en forme de vallées.
            */
           JButton btn5= new JButton("Vallees");
           toolBar.add(btn5);
           
           /**
            *Boutton qui permet d'identifier la fonde des vallées.
            */
           JButton btn6= new JButton("Export Vallees");
           toolBar.add(btn6);
           
          // On va ajouter une action qui reconnait quand le bouton de canyons a été cliqué 
           //La méthode de calculer les points critiques est invoquée de la classe canyon
           
           btn.addActionListener(new ActionListener() {

               public void actionPerformed(ActionEvent e) {
                 	   
            	   ReseauSurface rsf= new ReseauSurface(topo1);
            	   //Test.obtenirPointsBordure(topo1);
            	
            	   
            	   
            try {
				   layerPics=Graphique.dessinerPointsCritiques(topo1,rsf,map,rsf.getlistPics(),"Pics",Color.BLUE,rsf.getFeaturesListPics());		   
				   layerPuits=Graphique.dessinerPointsCritiques(topo1,rsf,map,rsf.getlistPuits(),"Puits",Color.GREEN,rsf.getFeaturesListPuits());
				   layerCols=Graphique.dessinerPointsCritiques(topo1,rsf,map,rsf.getlistCols(),"Cols",Color.YELLOW, rsf.getFeaturesListCols());
            	              	   
				   map.addLayer(layerPics);
				   Thread.sleep(1000);
				   map.addLayer(layerPuits);
				   Thread.sleep(1000);
				   map.addLayer(layerCols);
				   Thread.sleep(1000);
				   
            	   
				} catch (NullPointerException e2) {
					System.out.println("IL N'Y A PAS DE POINTS CRITIQUES");
				}catch (Throwable ex) {
					
				}
            	   
            	rsf1=rsf;
            	
               }

           
           });
           
           btn1.addActionListener(new ActionListener() {

               public void actionPerformed(ActionEvent e) {
            	   
                 try {
                	 rsf1.construirePuitsVirtuels();	
                	 rsf1.constructionReseauSurface();
                	
                	 Thread.sleep(1000);
                	 layerCretes=Graphique.dessinerCretes(map,rsf1.getCreteCollection());
                	 Thread.sleep(1500);
              	   	 map.addLayer(layerCretes);
              	   	 Thread.sleep(1000);
              	     layerRavins=Graphique.dessinerTalweg(map,rsf1.getTalwegCollection());
              	     Thread.sleep(1000);
              	     map.addLayer(layerRavins);
              	     Thread.sleep(1000);
              	     
     				} catch (Exception e1) {
     					// TODO Auto-generated catch block
     					e1.printStackTrace();
     				}
                 	
            	  
            	   
            	   TestReseauSurface.verifierEuler(rsf1);
            	   TestReseauSurface.nombreLignesCritiques(rsf1);
            	   //TestReseauSurface.afficherPointsRestreints(rsf1);
            	   //TestReseauSurface.afficherVertexRavin(rsf1);
            	   //TestReseauSurface.montrerColsPic(rsf1);
            	   
               }

           
           });
           
           btn2.addActionListener(new ActionListener() {

               public void actionPerformed(ActionEvent e) {
        
            	   try {
            		 SimpleFeatureType typePics=Graphique.typePoint("Pics"); 
            		 SimpleFeatureType typePuits=Graphique.typePoint("Puits"); 
            		 SimpleFeatureType typeCols=Graphique.typePoint("Cols"); 
            		 SimpleFeatureType typeCrete=Graphique.typeLigne("Crete");
            		 SimpleFeatureType typeTalweg=Graphique.typeLigne("Talweg");
            	
            		 ExportShape.createShp(typePics,rsf1.getFeaturesListPics(),"PICS");
            		 ExportShape.createShp(typePuits,rsf1.getFeaturesListPuits(),"PUITS");
            		 ExportShape.createShp(typeCols,rsf1.getFeaturesListCols(),"COLS");
            		 ExportShape.createShp(typeCrete,rsf1.getFeaturesListCrete(),"CRETE");
            		 ExportShape.createShp(typeTalweg,rsf1.getFeaturesListTalweg(),"TALWEG"); 
            		
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            	   
               }

           
           });
           
           btn3.addActionListener(new ActionListener() {

               public void actionPerformed(ActionEvent e) {
            	   
            	   List<SimpleFeature> featuresListBordure = new ArrayList<SimpleFeature>();
            	   ArrayList<Integer> bordureTemp= new ArrayList<Integer> ();
            	   for (Integer i : topo1.getTblsSommetsBordure().values()) {
					bordureTemp.add(i);
				}
            	   
            	   try {
					Graphique.dessinerPointsBordure(topo1,rsf1,map,bordureTemp,"Bordure",Color.BLACK,featuresListBordure);
					//SimpleFeatureType typeBordure=Graphique.typePoint("Bordure"); 
					//ExportShape.createShp(typeBordure,featuresListBordure ,"BORDURE");
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
               }

           
           });

           btn4.addActionListener(new ActionListener() {

               public void actionPerformed(ActionEvent e) {
            	   
                try {
                	 rsf1.simplifierReseau(1);	
     					
     				} catch (Exception e1) {
     					e1.printStackTrace();
     				}
                 
              try {
				 map.removeLayer(layerCretes);
                 map.removeLayer(layerRavins);
                 map.removeLayer(layerPics);
                 map.removeLayer(layerPuits);
                 map.removeLayer(layerCols);
                 
                 layerPics=Graphique.dessinerPointsCritiques(topo1,rsf1,map,rsf1.getlistPics(),"Pics",Color.BLUE,rsf1.getFeaturesListPics());		   
				 layerPuits=Graphique.dessinerPointsCritiques(topo1,rsf1,map,rsf1.getlistPuits(),"Puits",Color.GREEN,rsf1.getFeaturesListPuits());
				 layerCols=Graphique.dessinerPointsCritiques(topo1,rsf1,map,rsf1.getlistCols(),"Cols",Color.YELLOW, rsf1.getFeaturesListCols());
				 
				 Thread.sleep(1000);
				 layerCretes=Graphique.dessinerCretes(map,rsf1.getCreteCollection());
				 Thread.sleep(1000);
				 layerRavins=Graphique.dessinerTalweg(map,rsf1.getTalwegCollection());
				 Thread.sleep(1000);
                 
                 map.addLayer(layerPics);
				 map.addLayer(layerPuits);
				 map.addLayer(layerCols);
				 Thread.sleep(1500);
				 map.addLayer(layerCretes);
				 Thread.sleep(1500);
				 map.addLayer(layerRavins);
				 Thread.sleep(1500);
          	     
          	 //TestReseauSurface.afficherVertexRavin(rsf1);
          	 //TestReseauSurface.afficherVertexCretes(rsf1);
				} catch (Exception e2) {
					// TODO: handle exception
				}
          	     
                 TestReseauSurface.verifierEuler(rsf1); 
                 TestReseauSurface.nombreLignesCritiques(rsf1);
               }

           
           });
           
           btn5.addActionListener(new ActionListener() {

               public void actionPerformed(ActionEvent e) {
            	   
                try {
                	 //rsf1.simplifierReseau(1);	
                	 rsf1.trouverVallees();
     					
     				} catch (Exception e1) {
     					e1.printStackTrace();
     				}
                 
              try {
				 map.removeLayer(layerCretes);
                 map.removeLayer(layerRavins);
                 map.removeLayer(layerPics);
                 map.removeLayer(layerPuits);
                 map.removeLayer(layerCols);
                 
                 //layerPics=Graphique.dessinerPointsCritiques(topo1,rsf1,map,rsf1.getlistPics(),"Pics",Color.BLUE,rsf1.getFeaturesListPics());		   
				 //layerPuits=Graphique.dessinerPointsCritiques(topo1,rsf1,map,rsf1.getlistPuits(),"Puits",Color.GREEN,rsf1.getFeaturesListPuits());
				 //layerCols=Graphique.dessinerPointsCritiques(topo1,rsf1,map,rsf1.getlistCols(),"Cols",Color.YELLOW, rsf1.getFeaturesListCols());
				 //System.out.println("rsf1.getlistPointsFondVallee()"+rsf1.getlistPointsFondVallee());
				 
				 Thread.sleep(1000);
				 layerCretes=Graphique.dessinerCretes(map,rsf1.getCreteCollection());
				 Thread.sleep(1000);
				 layerRavins=Graphique.dessinerTalweg(map,rsf1.getTalwegCollection());
				 Thread.sleep(1000);
				 layerFondVallee=Graphique.dessinerFondVallee(map,rsf1.getFondValleeCollection(),Color.ORANGE);
				 Thread.sleep(1000);
				 LayerCanyon=Graphique.dessinerFondVallee(map,rsf1.getCanyonCollection(),Color.BLACK);
				 Thread.sleep(1000);
				 LayerChenal=Graphique.dessinerFondVallee(map,rsf1.getChenalCollection(),Color.CYAN);
                 
//                 map.addLayer(layerPics);
//				 map.addLayer(layerPuits);
//				 map.addLayer(layerCols);
				 Thread.sleep(1500);
          	     map.addLayer(layerCretes);
          	     Thread.sleep(1500);
          	     map.addLayer(layerRavins);
          	  	 Thread.sleep(1500);	
         	  	 Graphique.dessinerPointsBordure(topo1,rsf1,map,rsf1.getlistVerificationFondVallee(),"Points Verification de la Vallee",Color.GRAY, rsf1.getFeaturesListVerificationFondValleee());
        	  	 Thread.sleep(1500);
        	  	 Graphique.dessinerPointsBordure(topo1,rsf1,map,rsf1.getlistPointsFondVallee(),"Points du Fond de la Vallee",Color.ORANGE, rsf1.getFeaturesListPointsFondVallee());
          	  	 Thread.sleep(1500);
          	  	map.addLayer(layerFondVallee);
          	  	Thread.sleep(1500);
          	  	if(rsf1.getCanyonCollection()!=null){
         	  	 //map.addLayer(LayerCanyon);
          	  	}
         	  	Thread.sleep(1500);
         	  	if(rsf1.getChenalCollection()!=null){
        	  	 //map.addLayer(LayerChenal);
         	  	}
         	  	
          	     
          	 //TestReseauSurface.afficherVertexRavin(rsf1);
          	 //TestReseauSurface.afficherVertexCretes(rsf1);
				} catch (Exception e2) {
					// TODO: handle exception
				}
          	     
//                 TestReseauSurface.verifierEuler(rsf1); 
//                 TestReseauSurface.nombreLignesCritiques(rsf1);
               }

           
           });
           
           btn6.addActionListener(new ActionListener() {

               public void actionPerformed(ActionEvent e) {
        
            	   try {
            		 SimpleFeatureType typePointFondVallee=Graphique.typePoint("PointFondVallee"); 
            		 SimpleFeatureType typeVerificationFondVallee=Graphique.typePoint("VerificationFondVallee"); 
            		 SimpleFeatureType typePolFondVallee=Graphique.typeLigne("PolygoneFondVallee");
            		 SimpleFeatureType typeCanyon=Graphique.typeLigne("Canyons");
            		 SimpleFeatureType typeChenal=Graphique.typeLigne("Chenaux");
            		 
            		 
            		 ExportShape.createShp(typePointFondVallee,rsf1.getFeaturesListPointsFondVallee(),"PointFondVallee");
            		 ExportShape.createShp(typeVerificationFondVallee,rsf1.getFeaturesListVerificationFondValleee(),"VerificationFondVallee");
            		 ExportShape.createShp(typePolFondVallee,rsf1.getFeaturesListFondVallee(),"PolygoneFondVallee"); 
            		 ExportShape.createShp(typeCanyon,rsf1.getFeaturesListCanyon(),"Canyons"); 
            		 ExportShape.createShp(typeChenal,rsf1.getFeaturesListChenal(),"Chenaux"); 
            		
            		 
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            	   
               }

           
           });
       
           //Finalement, on va afficher le cadre de la carte.
           
           mapFrame.setSize(800, 1000);
           mapFrame.setVisible(true);   
        
        
        
    }
   
    
    

    
  
}