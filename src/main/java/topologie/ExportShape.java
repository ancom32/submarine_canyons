package topologie;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFactorySpi;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureWriter;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.map.MapContent;

import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;


import com.vividsolutions.jts.geom.Geometry;

/** 
 * <b>Classe ExportShape</b>
 * <p> 
 * Cette classe permet de faire l'exportation des couches dans la carte au format de type shapeFile.
 *   
 * @author Andrés Camilo Cortés Murcia
 * @since  2015-07-09
 * 
 */
public class ExportShape{
	
	/**
     * Cette fonction permet de spécifier les paramètres nécessaires pour l'exportation du shpFile
     * 
     * @param map
     *            Le carte où les maps sont affichées 
     */
	public static void exportToShapefile(MapContent map) throws Exception {	
		File sourceFile = JFileDataStoreChooser.showOpenFile("shp", null);
		FileDataStore store = FileDataStoreFinder.getDataStore(sourceFile);

		SimpleFeatureSource featureSource = store.getFeatureSource("sa");
        
		
		SimpleFeatureType schema = featureSource.getSchema();
		JFileDataStoreChooser chooser = new JFileDataStoreChooser("shp");
        chooser.setDialogTitle("Save reprojected shapefile");
        //chooser.setSaveFile(sourceFile);
        
        
        int returnVal = chooser.showSaveDialog(null);
        if (returnVal != JFileDataStoreChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
       
//        if (file.equals(sourceFile)) {
//            JOptionPane.showMessageDialog(null, "Cannot replace " + file);
//            return;
//        }
        
        //CoordinateReferenceSystem dataCRS = schema.getCoordinateReferenceSystem();
        CoordinateReferenceSystem worldCRS = map.getCoordinateReferenceSystem();
        //boolean lenient = true; // allow for some error due to different datums
        //MathTransform transform = CRS.findMathTransform(dataCRS, worldCRS, lenient);
        
        SimpleFeatureCollection featureCollection = featureSource.getFeatures();
        
        DataStoreFactorySpi factory = new ShapefileDataStoreFactory();
        Map<String, Serializable> create = new HashMap<String, Serializable>();
        create.put("url", file.toURI().toURL());
        create.put("create spatial index", Boolean.TRUE);
        DataStore dataStore = factory.createNewDataStore(create);
        
        SimpleFeatureType featureType = SimpleFeatureTypeBuilder.retype(schema,worldCRS);
        //SimpleFeatureType featureType = SimpleFeatureTypeBuilder.retype(schema, worldCRS);
        dataStore.createSchema(featureType);
       

        //Get the name of the new Shapefile, which will be used to open the FeatureWriter
        String createdName = dataStore.getTypeNames()[0];
        
        Transaction transaction = new DefaultTransaction("create");
        FeatureWriter<SimpleFeatureType, SimpleFeature> writer =
                        dataStore.getFeatureWriterAppend(createdName, transaction);
        SimpleFeatureIterator iterator = featureCollection.features();
        try {
            while (iterator.hasNext()) {
                // copy the contents of each feature and transform the geometry
                SimpleFeature feature = iterator.next();
                SimpleFeature copy = writer.next();
                copy.setAttributes(feature.getAttributes());

                Geometry geometry = (Geometry) feature.getDefaultGeometry();
                //Geometry geometry2 = JTS.transform(geometry, transform);

                //copy.setDefaultGeometry(geometry2);
                copy.setDefaultGeometry(geometry);
                writer.write();
            }
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Export to shapefile complete");
        } catch (Exception problem) {
            problem.printStackTrace();
            transaction.rollback();
            JOptionPane.showMessageDialog(null, "Export to shapefile failed");
        } finally {
            writer.close();
            iterator.close();
            transaction.close();
        }
	}
	/**
     * Cette fonction permet de créer un nouveau fichier de type shpFile
     * 
     * @param TYPE
     *            Le type du SimpleFeature
     * @param features
     *            Le type du List<SimpleFeature>
     * @param name
     *            Le nom du fichier
     */
	public static void createShp(SimpleFeatureType TYPE,List<SimpleFeature> features, String name)throws Exception{
		 // Set cross-platform look & feel for compatability
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        
	        File newFile = getNewShapeFile(name);

	        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

	        Map<String, Serializable> params = new HashMap<String, Serializable>();
	        params.put("url", newFile.toURI().toURL());
	        params.put("create spatial index", Boolean.TRUE);

	        ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
	        
	        /*
	         * TYPE is used as a template to describe the file contents
	         */
	        newDataStore.createSchema(TYPE);

	        Transaction transaction = new DefaultTransaction("create");

	        String typeName = newDataStore.getTypeNames()[0];
	        SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
	        SimpleFeatureType SHAPE_TYPE = featureSource.getSchema();
	        /*
	         * The Shapefile format has a couple limitations:
	         * - "the_geom" is always first, and used for the geometry attribute name
	         * - "the_geom" must be of type Point, MultiPoint, MuiltiLineString, MultiPolygon
	         * - Attribute names are limited in length 
	         * - Not all data types are supported (example Timestamp represented as Date)
	         * 
	         * Each data store has different limitations so check the resulting SimpleFeatureType.
	         */
	        System.out.println("TYPE:"+TYPE);
	        System.out.println("SHAPE:"+SHAPE_TYPE);

	        if (featureSource instanceof SimpleFeatureStore) {
	        	SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
	        	/*
	             * SimpleFeatureStore has a method to add features from a
	             * SimpleFeatureCollection object, so we use the ListFeatureCollection
	             * class to wrap our list of features.
	             */
	        	SimpleFeatureCollection collection = new ListFeatureCollection(TYPE,features);
	            
	        	featureStore.setTransaction(transaction);
	            try {
	                featureStore.addFeatures(collection);
	                transaction.commit();

	            } catch (Exception problem) {
	                problem.printStackTrace();
	                transaction.rollback();

	            } finally {
	                transaction.close();
	            }
	            //System.exit(0); // success!
	        } else {
	            System.out.println(typeName + " does not support read/write access");
	            //System.exit(1);
	        }
	}
	/**
     * Cette fonction permet de capturer l'addresse d'où le nouvea fichier de type shpFile sera stocké.
     * 
     * @param name
     *            Le nom du fichier à exporter
     * 
     * @return le nouveau fichier de type 
     */
	 public static File getNewShapeFile(String name) {
//	        String path = csvFile.getAbsolutePath();
//	        String newPath = path.substring(0, path.length() - 4) + ".shp";

	        JFileDataStoreChooser chooser = new JFileDataStoreChooser("shp");
	        chooser.setDialogTitle("Save shapefile "+name);
	        //chooser.setSelectedFile(new File(newPath));

	        int returnVal = chooser.showSaveDialog(null);

	        if (returnVal != JFileDataStoreChooser.APPROVE_OPTION) {
	            // the user cancelled the dialog
	            System.exit(0);
	        }

	        File newFile = chooser.getSelectedFile();
//	        if (newFile.equals(csvFile)) {
//	            System.out.println("Error: cannot replace " + csvFile);
//	            System.exit(0);
//	        }

	        return newFile;
	    }

}
