package graphiques;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.DataUtilities;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.GeometryBuilder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import surfaceNetwork.ReseauSurface;
import surfaceNetwork.PointCritique;



import topologie.Topologie;

import com.vividsolutions.jts.geom.Point;

/** 
 * <b>Classe Graphique</b>
 * <p> 
 * Cette classe permet de dessigner les divers figures dans la carte.
 *   
 * @author Andr�s Camilo Cort�s Murcia
 * @since  2015-07-09
 * 
 */
public class Graphique {
	
	
	/** Cette methode permet de dessiner les points critiques � travers des listes des couches.
	 * @param topo L'instance de l'objet de type Topologie.
	 * @see Topologie
	 * @param sfn L'instance de l'objet de type R�seau de surface.
	 * @see ReseauSurface
	 * @param sfn L'instance de l'objet de type R�seau de surface.
	 * @see ReseauSurface
	 * @param map La carte o� les couches seront ajout�es.
	 * @param arrayList Liste du point critique � dessiner.
	 * @param name Type du point critique.
	 * @param color Le couleur qu'on sohuaite assigner � la couche.
	 * @param featureList Liste des �l�ments g�om�triques qui seront reli�s � une couche.
	 * @return layer La couche qui sera ajout� � la carte.
	 */
	public static Layer dessinerPointsCritiques(Topologie topo, ReseauSurface sfn,MapContent map, ArrayList<?> arrayList, String name, Color color, List<SimpleFeature> featuresList) throws Exception{
		
		//build the type of feature
		final SimpleFeatureType type = typePoint(name);
		

		//create the builder from the type of feature
		SimpleFeatureBuilder builder = new SimpleFeatureBuilder(type);
		GeometryBuilder builder1 = new GeometryBuilder( );
		DefaultFeatureCollection lineCollection = new DefaultFeatureCollection();
	
		

		for(int i=0; i<arrayList.size();i++){
			//Parcourir un array de points pour obtenir leurs coordonn�es
			
			Point point1 = builder1.point(((PointCritique) arrayList.get(i)).getSommet().getX(), ((PointCritique) arrayList.get(i)).getSommet().getY());

			
			builder.add(point1);
			builder.add( name+i );		
		
			builder.add(((PointCritique) arrayList.get(i)).getSommet().getZ() );
		
	
			//build the feature with provided ID
			SimpleFeature feature = builder.buildFeature( "fid."+ ((PointCritique) arrayList.get(i)).getSommet().getId());
			
			lineCollection.add(feature);
			featuresList.add(feature);
		}
		Style style=SLD.createPointStyle("circle",color,color,1f,5f); 
		//Ajoter la couche � la carte
		Layer layer=new FeatureLayer(lineCollection,style);
        //map.addLayer(layer);
        return layer;

	}
	
	/** Cette methode permet de dessiner les points qui se trouvent � la bordure du domaine ou aux trous � leur int�rieur.
	 * @param topo L'instance de l'objet de type Topologie.
	 * @see Topologie
	 * @param sfn L'instance de l'objet de type R�seau de surface.
	 * @see ReseauSurface
	 * @param sfn L'instance de l'objet de type R�seau de surface.
	 * @see ReseauSurface
	 * @param map La carte o� les couches seront ajout�es.
	 * @param arrayList Liste du point critique � dessiner.
	 * @param name Nom du point critique.
	 * @param color Le couleur qu'on sohuaite assigner � la couche.
	 * @param featureList Liste des �l�ments g�om�triques qui seront reli�s � une couche.
	 */
	public static void dessinerPointsBordure(Topologie topo, ReseauSurface sfn,MapContent map, ArrayList<?> arrayList, String name, Color color, List<SimpleFeature> featuresList) throws Exception{
		
		//build the type of feature
		final SimpleFeatureType type = typePoint(name);
		

		//create the builder from the type of feature
		SimpleFeatureBuilder builder = new SimpleFeatureBuilder(type);
		GeometryBuilder builder1 = new GeometryBuilder( );
		DefaultFeatureCollection lineCollection = new DefaultFeatureCollection();
	
		

		for(int i=0; i<arrayList.size();i++){
			//Parcourir un array de points pour obtenir leurs coordonn�es
			
			Point point1 =builder1.point(topo.getTblSommets().get(arrayList.get(i)).getX(), topo.getTblSommets().get(arrayList.get(i)).getY());
				
			builder.add(point1);
			builder.add( name+i );		
			builder.add(topo.getTblSommets().get(arrayList.get(i)).getZ() );
		
			//build the feature with provided ID
			SimpleFeature feature = builder.buildFeature( "fid."+ topo.getTblSommets().get(arrayList.get(i)).getId());
			
			lineCollection.add(feature);
			featuresList.add(feature);
		}
		Style style=SLD.createPointStyle("circle",color,color,1f,5f); 
		//Ajoter la couche � la carte
		Layer layer=new FeatureLayer(lineCollection,style);
        map.addLayer(layer);
        

	}
	
	/** Cette methode permet d'assigner un Type et des attributes � un entit� de type point.
	 * @param name Nom du point critique.
	 * @return type Le type(ensemble des attributs) qui a �t� assign� au point.
	 */
	public static SimpleFeatureType typePoint(String name) throws Exception{
		final SimpleFeatureType type = DataUtilities.createType(name,
                //"the_geom:Point:srid=4326," + // <- the geometry attribute: Point type
                "the_geom:Point:EPSG:26919," + // <- the geometry attribute: Point type
                "name:String," +   // <- a String attribute
                "hight:Double"   // hight of the point
        );
		return type;
	}
	
	/** Cette methode permet d'assigner un Type et des attributes � un entit� de type point.
	 * @param name Nom du point critique.
	 * @return type Le type(ensemble des attributs) qui a �t� assign� au point.
	 */
	public static SimpleFeatureType typeLigne(String name) throws Exception{
			
	    final SimpleFeatureType type = DataUtilities.createType(name,
                //"the_geom:Point:srid=4326," + // <- the geometry attribute: Point type
                "the_geom:LineString:EPSG:26919," + // <- the geometry attribute: Point type
                "name:String," +   // <- a String attribute
                "number:Integer"   // a number attribute
        );
        
        return type;
	}
	
	/** Cette methode permet de dessiner les lignes critiques de type "Cr�te".
	 * @param map La carte o� les couches seront dessin�es.
	 * @creteCollection Ensemble de cr�tes qui seront d�venues une couche.
	 * @return layer La couche qui sera ajout� � la carte.
	 */
	public static Layer dessinerCretes(MapContent map,DefaultFeatureCollection creteCollection){
		Style style=SLD.createLineStyle(Color.BLUE, 2);
		//Ajouter la couche � la carte
		Layer layer=new FeatureLayer(creteCollection,style);
        //map.addLayer(layer);
		return layer;
        
	}
	
	/** Cette methode permet de dessiner les lignes critiques de type "Talweg/Ravin".
	 * @param map La carte o� les couches seront dessin�es.
	 * @creteCollection Ensemble de cr�tes qui seront d�venues une couche.
	 * @return layer La couche qui sera ajout� � la carte.
	 */
	public static Layer dessinerTalweg(MapContent map,DefaultFeatureCollection talwegCollection){
		Style style=SLD.createLineStyle(Color.GREEN, 2);
		//Ajoter la couche � la carte
		Layer layer=new FeatureLayer(talwegCollection,style);
        //map.addLayer(layer);
        return layer;
	}
	
	/** Cette methode permet de dessiner les lignes critiques de type "Fond de la vall�e".
	 * @param map La carte o� les couches seront dessin�es.
	 * @fonValleeCollection Ensemble de lignes du fond de la Vall�e qui seront d�venues une couche.
	 * @return layer La couche qui sera ajout� � la carte.
	 */
	public static Layer dessinerFondVallee(MapContent map,DefaultFeatureCollection fondValleeCollection, Color couleur){
		Style style=SLD.createLineStyle(couleur, 2);
		//Ajouter la couche � la carte
		Layer layer=new FeatureLayer(fondValleeCollection,style);
        //map.addLayer(layer);
        return layer;
	}
	
	
	
}
