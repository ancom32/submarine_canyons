package vallees;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.geotools.math.Statistics;

import surfaceNetwork.Ravin;
import topologie.Aretes;
import topologie.Points;
import topologie.Topologie;

public class Squelette {

	
	/**
	 * L'id du Squelette.
	 */
	private int id;
	/**
	 * Le talweg du Squelette.
	 */
	private Ravin ravinSquelette;
	/**
	 * La liste d'ar�tes du squelette.
	 */
	private ArrayList<Aretes> listAreteSquelette= new ArrayList<Aretes>();
	
	/**
	 * La liste des Valeurs de d�niv�l� entre les points du squelette et la bordure.
	 */
	private HashMap<Integer,Double> MapDeniveleBordure = new HashMap<Integer,Double>();
	
	/**
	 * La liste des Valeurs de distance entre les points du squelette et la bordure.
	 */
	private HashMap<Integer,Double>  MapDistanceBordure = new HashMap<Integer,Double> ();
	
	/**
	 * La liste des Valeurs de distance entre les points du squelette et la bordure.
	 */
	private HashMap<Integer,Double>  CoordonneesNouveauPointX = new HashMap<Integer,Double> ();
	
	/**
	 * La liste des Valeurs de distance entre les points du squelette et la bordure.
	 */
	private HashMap<Integer,Double>  CoordonneesNouveauPointY = new HashMap<Integer,Double> ();

	public Squelette(int idSquelette,Ravin ravinSquelette) {
		this.ravinSquelette=ravinSquelette;
		this.id=idSquelette;
	}

	public Ravin getRavinSquelette() {
		return this.ravinSquelette;
	}

	public Integer getId(){
		return this.id;
	}
	
	public void setRavinSquelette(Ravin ravinSquelette) {
		this.ravinSquelette = ravinSquelette;
	}

	public ArrayList<Aretes> getListAreteSquelette() {
		return listAreteSquelette;
	}
	
	public HashMap<Integer,Double> getMapDeniveleBordure(){
		return this.MapDeniveleBordure;
	}
	
	public HashMap<Integer,Double> getMapDistanceBordure(){
		return this.MapDistanceBordure;
	}
	

	public void setListAreteSquelette(ArrayList<Aretes> listAreteSquelette) {
		this.listAreteSquelette = listAreteSquelette;
	}
	
	public void setDeniveleMoyenBordure(int pointIdEvaluer,double deniveleLocal){
		this.MapDeniveleBordure.put(pointIdEvaluer,deniveleLocal);
	}
	
	public void setDistanceMoyenBordure(int pointIdEvaluer,double distanceMinimale){
		this.MapDistanceBordure.put(pointIdEvaluer,distanceMinimale);
	}
	
	public Double getDeniveleMoyenBordure(){
		double sumDenivele=0;
		double deniveleMoyenBordure;
		for (Double deniveleLocal : this.MapDeniveleBordure.values()) {
			sumDenivele=deniveleLocal+sumDenivele;
		}
		deniveleMoyenBordure=sumDenivele/this.MapDeniveleBordure.size();
		return deniveleMoyenBordure;
	}
	
	public Double getDistanceMoyenBordure(){
		double sumDistance=0;
		double distanceMoyenBordure;
		for (Double distanceMinimale : this.MapDistanceBordure.values()) {
			sumDistance=distanceMinimale+sumDistance;
		}
		distanceMoyenBordure=sumDistance/this.MapDistanceBordure.size();
		
		return distanceMoyenBordure;
	}
	
	public Double getLongueurSquelette(Topologie topo){
		double sumDistance=0;
		double distanceSquelette;
		for (int i=1;i<this.getRavinSquelette().getListIdVertex().size();i++) {
				int vertex=this.getRavinSquelette().getListIdVertex().get(i);
				int vertexPrec=this.getRavinSquelette().getListIdVertex().get(i-1);
				Points pointVertex=topo.getTblSommets().get(vertex);
				Points pointVertexPrec=topo.getTblSommets().get(vertexPrec);
				double distance=pointVertex.calculerDistance(pointVertexPrec);
				sumDistance=distance+sumDistance;
		}
		//distanceSquelette=sumDistance/this.getRavinSquelette().getListIdVertex().size();
		distanceSquelette=sumDistance;
		return distanceSquelette;
	}
	
	public ArrayList<Double> getListValeursRelieauBordure(Points p1, Points p2, Points p3, double distanceMin, double angleBase,int seuilAngle, Aretes areteBase, Topologie topo){
		//On va parcourir la liste d'ar�tes du squelette
		//En forme ordonn�e, on va r�cup�rer les verex de chaque ar�te. Le point 1 sera celui qui est le plus proche au d�but du squelette. Le point 2 sera le point restant.
		// On va contruire un triangle entre le p1-p2 et le point � analyser(p3). On calcule les ar�tes de ce triangle. Et on calcule ces angles internes par les lois du cosinus et sinus.
		
		
		double anglesBC=180;
		boolean pointA90Degrees=false;
		ArrayList<Double> valeursActualisees=new ArrayList<Double>();
		
		for(int i=0;i<this.getListAreteSquelette().size();i++){
    		Aretes arete=this.getListAreteSquelette().get(i);
    		
//    		Points p1=topo.getTblSommets().get(arete.getS1());
//			Points p2=topo.getTblSommets().get(arete.getS2());
//			Points p3=pointEvaluer;
    		
    		p1=topo.getTblSommets().get(arete.getS1());
			p2=topo.getTblSommets().get(arete.getS2());
			//p3=pointEvaluer;
			
			int indexP1=this.getRavinSquelette().getListIdVertex().indexOf(p1.getId());
			int indexP2=this.getRavinSquelette().getListIdVertex().indexOf(p2.getId());
			
			if(indexP1>indexP2){
				p1=topo.getTblSommets().get(arete.getS2());
				p2=topo.getTblSommets().get(arete.getS1());
				indexP1=this.getRavinSquelette().getListIdVertex().indexOf(p1.getId());
				indexP2=this.getRavinSquelette().getListIdVertex().indexOf(p2.getId());
			}
			
//			if(p3.getId()==1557){
//				System.out.println("index of p1"+this.getRavinSquelette().getListIdVertex().indexOf(p1.getId()));	        		
//				System.out.println("index of p2"+this.getRavinSquelette().getListIdVertex().indexOf(p2.getId()));	
//			}
		
						        			
//			if(ravin.getListIdVertex().contains(p3.getId())){
//				deniveleLocal=0;
//				break;
//			}
					
    		double areteA=p1.calculerDistance(p2);
    		double areteB=p2.calculerDistance(p3);
    		double areteC=p3.calculerDistance(p1);
    		
    		/// la loi du cosinus pour identifier si le triangle bati poss�de un angle sup�rieur de 90
    		//Si c'est le cas, on va ignorer ce triangle parce que le point de la bordure ne donne pas la distance la plus courte 
    		
    		double numerateur=Math.pow(areteA, 2)+Math.pow(areteB, 2)-Math.pow(areteC, 2);
    		double denominateur=2*areteA*areteB;
    		double angleC=Math.acos(numerateur/denominateur);
    		double angleCDegrees=angleC*(180/Math.PI);
    						        									        		
    		//la loi du sinus
    		
    		double loiSinusA=areteA*Math.sin(angleC)/areteC;
    		double angleA = 0;
    		if (loiSinusA>1){
    			angleA= Math.asin(1);
    		}else{
    			angleA= Math.asin(loiSinusA);
    		}
    		
    		double angleADegrees=angleA*(180/Math.PI);
    		
    		double loiSinusB=areteB*Math.sin(angleC)/areteC;
    		double angleB = 0;
    		if (loiSinusB>1){
    			angleB= Math.asin(1);
    		}else{
    			angleB= Math.asin(loiSinusB);
    		}
    		
    		double angleBDegrees=angleB*(180/Math.PI);
    		
    		double sumAngles=angleADegrees+angleBDegrees+angleCDegrees;
    		
    		//Si la somme des angles il n'es pas 180 d�gr�es, il faut chercher l'angle suplemmentaire du triangle pour calculer sa valeur de nouveau.
    		//Ce probl�me se produit parce que le sinus d'un angle peut �tre �gal qu'un angle-180, et pour le cosinus avec le signe inverse.
    		//C'est � dire:
    		//sin (alfa)=sin(180-alfa)
    		//cos alfa=-cos(180-alfa)
    		
    		if(Math.round(sumAngles)!=180){
    			double difADegrees=(180-angleADegrees)+angleBDegrees+angleCDegrees;
    			double difBDegrees=angleADegrees+(180-angleBDegrees)+angleCDegrees;
    			double difCDegrees=angleADegrees+angleBDegrees+(180-angleCDegrees);
    			
    			if(Math.round(difCDegrees)==180){
    				angleCDegrees=180-angleCDegrees;
    			}
    			else if(Math.round(difBDegrees)==180){
    				angleBDegrees=180-angleBDegrees;
    				angleB=angleBDegrees*(Math.PI/180);
    			}
    			else if(Math.round(difADegrees)==180){
    				angleADegrees=180-angleADegrees;
    			}
//    			else{
//    				angleBDegrees=180-angleADegrees-angleCDegrees;
//    				angleB=angleBDegrees*(Math.PI/180);
//    			}
    		}
    		
    		sumAngles=angleADegrees+angleBDegrees+angleCDegrees;
//    		double angleBDegrees=180-angleADegrees-angleCDegrees;
//    		double angleB=angleBDegrees*(Math.PI/180);
    		
//    		if(p3.getId()==6694){
//    		System.out.println("Loi sinus: "+loiSinusA);	
//    		System.out.println("arete: "+arete.getId()+" Angle A: "+angleADegrees+" Angle B: "+angleBDegrees+" Angle C: "+angleCDegrees);
//    		System.out.println("AreteA: "+areteA+" "+p1.getId()+" - "+p2.getId());
//    		System.out.println("AreteB: "+areteB+" "+p2.getId()+" - "+p3.getId());
//    		System.out.println("AreteC: "+areteC+" "+p1.getId()+" - "+p3.getId());
//    		System.out.println("sumAngles "+sumAngles);
//    		}
//    		double difAB=Math.abs(angleADegrees-angleBDegrees);
//    		double difAC=Math.abs(angleADegrees-angleCDegrees);
//    		double difBC=Math.abs(angleBDegrees-angleCDegrees);
    		
    		
    		//sommeDistance=sommeDistance+distanceSqueletteAbsolue;
    						        		
    		//System.out.println("Distance Squelette Absolue"+distanceSqueletteAbsolue);
    					     
//    		double tempSum=difAB+difAC+difBC;
//    		
//    		if(tempSum<sumAngles){
//    			sumAngles=tempSum;
//    			areteBase=arete;
//    			angleBase=angleB;
//    			}
    		
    		
    	//}
    	
//    		System.out.println("Arete Base "+areteBase.getId()+"Point 3: "+p3.getId());
//    		p1=topo.getTblSommets().get(areteBase.getS1());
//    		p2=topo.getTblSommets().get(areteBase.getS2());
//    		
//    		double areteA=p1.calculerDistance(p2);
//    		double areteB=p2.calculerDistance(p3);
//    		double areteC=p3.calculerDistance(p1);
    		
    		//On fait une condition pour �valuer le point si le triangle form� les angles sont inf�rieur au seuil fix�
    		//On calcule la distance du point � �valuer avec l'ar�te, on utilise le produit vectoriel et la magnitud du vectur pour calculer la distance la plus courte au squelette
    		// On va garder dans une liste, le point qui correspond � la distance la plus courte. Les valeurs � stocker sont la distance, l'ar�te et l'angle B.
    		
    		if((angleBDegrees<=seuilAngle && angleCDegrees<=seuilAngle)||(p1.getY()==p3.getY()||p2.getY()==p3.getY())){
    		//if(angleADegrees<=seuilAngle && angleBDegrees<=seuilAngle && angleCDegrees<=seuilAngle){
    		//if(angleADegrees<=90 && angleBDegrees<=90 && angleCDegrees<=90){	
    			//n=n+1;
    			//Maintenant, on va construire les divers vecteurs pour calculer la distance la plus courte du 
        		double vecteurU_I=p1.getX()-p2.getX();
        		double vecteurU_J=p1.getY()-p2.getY();
        		double vecteurV_I=p1.getX()-p3.getX();
        		double vecteurV_J=p1.getY()-p3.getY();
        		
        		double produitVectoriel= (vecteurU_I*vecteurV_J)-(vecteurU_J*vecteurV_I);
        		double distanceSquelette=produitVectoriel/areteA;
        		//double distanceSqueletteAbsolue=distanceSquelette;
        		///******************OJOOOOOOOOOOOO rEVIsar valor Absoluto distancia esqUeletoOO
        		double distanceSqueletteAbsolue=Math.abs(distanceSquelette);
        		//sommeDistance=sommeDistance+distanceSqueletteAbsolue;
        		
        		
        		//System.out.println("Distance Squelette Absolue"+distanceSqueletteAbsolue)
        		double distancePojectee=areteC*Math.cos(angleB);
        		double sommeAnglesBC=Math.abs(90-angleBDegrees)+Math.abs(90-angleCDegrees);   
        		double areteAprojetee=areteA*1.25;
        		
//        		if(Math.round(p1.getY())==Math.round(p3.getY())||Math.round(p2.getY())==Math.round(p3.getY())){
//        			pointA90Degrees=true;
//        			if(sommeAnglesBC<anglesBC){
//        				anglesBC=sommeAnglesBC;
//        				valeursActualisees.clear();
//            			
//            			distanceMin=distanceSqueletteAbsolue;
//            			areteBase=arete;
//            			angleBase=angleB;
//            			
//            			valeursActualisees.add(distanceMin);
//            			valeursActualisees.add((double) areteBase.getId());
//            			valeursActualisees.add(angleBase);
//        			}
//        		}
        		
        		//else if(angleBDegrees<=seuilAngle && angleCDegrees<=seuilAngle && pointA90Degrees==false){
        		
        		//if( distanceSqueletteAbsolue<distanceMin && distanceSqueletteAbsolue>0 ){
        		   //if(distanceSqueletteAbsolue<distanceMin && distanceSqueletteAbsolue>0 && distancePojectee<areteAprojetee){
        			if(distanceSqueletteAbsolue<distanceMin && distanceSqueletteAbsolue>0 && distancePojectee>0){
        				
        			valeursActualisees.clear();
        			
        			distanceMin=distanceSqueletteAbsolue;
        			areteBase=arete;
        			angleBase=angleB;
        			
        			valeursActualisees.add(distanceMin);
        			valeursActualisees.add((double) areteBase.getId());
        			valeursActualisees.add(angleBase);
        			
        			        			
        			}
        		   
//        		   if(p3.getId()==542){
//        				System.out.println("%%% Distance Project�e"+distancePojectee + "Arete A"+ areteA+ "distance squelette"+distanceSqueletteAbsolue);
//        				}
        		   
        		
    		}
//    		else{
//    			tempAreteSquelette.add(arete.getId());
//    		}
    	}
//		if(p3.getId()==542){
//		System.out.println("valeursActualisees"+valeursActualisees);
//		}
		
		//On va parcourir la liste de vertex du squelette pour ces points qui a une distance plus courte � un vertex plut�t qu'une ar�te
		double distanceMinVertex=9999999;
		ArrayList<Double> valeursActualVertex=new ArrayList<Double>();
		//Calculer la distance par rapport aux vertex du squelette. Pour obtenir des points qui se trouvent plus proches aux vertes plut�t que des ar�tes.
		//On stocke la distance et le point dans une nouvelle liste
		for (Integer vertex1 : this.getRavinSquelette().getListIdVertex()) {
			p1=topo.getTblSommets().get(vertex1);
			double distanceSqueletteVertex=p1.calculerDistance(p3);
			
			if (distanceSqueletteVertex<distanceMinVertex && distanceSqueletteVertex>0 && this.getRavinSquelette().getListIdVertex().contains(p3.getId())==false){
				valeursActualVertex.clear();
				distanceMinVertex=distanceSqueletteVertex;
				valeursActualVertex.add(distanceMinVertex);
				valeursActualVertex.add((double)vertex1);
				
			}
		}
		
		//Si la distance obtenue pour les vertex est inf�rieur � celui qui a �t� obtenue pour les ar�tes, actualiser la liste de valeurs qui sera rendre de la fonction.
		if(valeursActualisees.isEmpty()==false && valeursActualVertex.isEmpty()==false){
			if((Math.round(valeursActualVertex.get(0))<Math.round(valeursActualisees.get(0)))){
				//System.out.println("p3: "+p3.getId()+" Vertex: "+valeursActualVertex.get(1)+ " distance Vertex: "+valeursActualVertex.get(0)+" distance Arete: "+valeursActualisees.get(0)+" arete: "+valeursActualisees.get(1));
				valeursActualisees.clear();
				valeursActualisees.addAll(valeursActualVertex);
			}
			
		}
		
//		if(valeursActualisees.isEmpty() && seuilAngle==110){
//			valeursActualisees.addAll(valeursActualVertex);
//		}
		
		if(this.getRavinSquelette().getListIdVertex().contains(p3.getId())==false){
//		if(valeursActualisees.size()==3){
//			System.out.println("p3: "+p3.getId()+" Distance: "+valeursActualisees.get(0)+" arete: "+valeursActualisees.get(1)+" angle B "+valeursActualisees.get(2));
//		}else if(valeursActualisees.size()==2){
//			System.out.println("p3: "+p3.getId()+" Distance: "+valeursActualisees.get(0)+" vertex: "+valeursActualisees.get(1));	
//		}
		}
		
		return valeursActualisees;
				
	}
	
	public ArrayList<Integer> getVoisinAreteauBordure(Points pointBordure,Points pointVerification, Points pointEvaluer, ArrayList<Integer> VertexAnalysesGauche,ArrayList<Integer> pointsVoisins,Topologie topo){
		
		//Des points de bordure(TRUE) et v�rification (FALSE) on va former une ar�te, et chercher les points voisins de cette ar�tes.
		//G�n�ralement, l'ar�te poss�de de deux voisins. Un voisin qui a �t� d�j� trait� (qui se trouve � la liste de point de v�rification) et un voisin qui n'est pas encore trait� (il sera le nouveau point � analyser)
		//Le voisisin qui ne se trouvent pas � la liste de voisins analys�s sera le point � �valuer. On stocke dans une nouvelle liste les voisins de l'ar�te.
		//La fonction rendra une arrayList avec les points voisins de l'are�te TRUE-----FALSE.
		
		Integer idPointEvaluer=pointEvaluer.getId();
		String sTriangle = topo.getTblSommetsTriangles().get(idPointEvaluer).toString().replace('[',' ').replace(']',' ').trim();
		
		//Tokenizer vpermet de lire chacun des valeurs stock�s s�par�es par virgule  
		StringTokenizer st2 = new StringTokenizer(sTriangle,", ");
        	while (st2.hasMoreTokens()){
        	 int numTri= Integer.parseInt(st2.nextToken());
        	 int n1=topo.getTblTriangles().get(numTri).getS1();
        	 int n2=topo.getTblTriangles().get(numTri).getS2();
        	 int n3=topo.getTblTriangles().get(numTri).getS3();
        	 
        	 				       	 
        	 if((n1==pointBordure.getId()&& n2==pointVerification.getId())||(n2==pointBordure.getId()&& n1==pointVerification.getId())){
//        		 if(pointEvaluer.getId()==207610){
//        			 System.out.println("PTOS VOISINS "+n3);
//        		 }
        		 pointEvaluer=topo.getTblSommets().get(n3); 
        		 pointsVoisins.add(n3);				        		 
        		 if(VertexAnalysesGauche.contains(pointEvaluer.getId())==false){
        			 idPointEvaluer=n3;
        		 }
        		 
        	 }
        	 
        	 if((n2==pointBordure.getId()&& n3==pointVerification.getId())||(n3==pointBordure.getId()&& n2==pointVerification.getId())){
//        		 if(pointEvaluer.getId()==207610){
//        			 System.out.println("PTOS VOISINS "+n3);
//        		 }
        		 pointEvaluer=topo.getTblSommets().get(n1); 
        		 pointsVoisins.add(n1);	
        		 if(VertexAnalysesGauche.contains(pointEvaluer.getId())==false){
        		 idPointEvaluer=n1;		
        		 }
        	 }
        	 if((n1==pointBordure.getId()&& n3==pointVerification.getId())||(n3==pointBordure.getId()&& n1==pointVerification.getId())){
//        		 if(pointEvaluer.getId()==207610){
//        			 System.out.println("PTOS VOISINS "+n3);
//        		 }
        		 pointEvaluer=topo.getTblSommets().get(n2); 
        		 pointsVoisins.add(n2);	
        		 if(VertexAnalysesGauche.contains(pointEvaluer.getId())==false){
        		 idPointEvaluer=n2;
        		 }
        	 }
        	 	      
        	}
        	pointsVoisins.add(idPointEvaluer);
        	
		return pointsVoisins;
				
	}
	
	
	public ArrayList<Integer> getPointsBordure(int coteSquelette,double moyenneGlobaleDeniv, double moyenneGlobalePente,double difElevGaucheDroit,int pointAnalyser,Points pointGaucheDroit,Points pointBordure,Points pointVerification,ArrayList<Integer> vertexAnalysesGaucheDroit,ArrayList<Integer> VertexBordure, ArrayList<Integer> boutDuVersant,ArrayList<Integer> listVertexVersant,Topologie topo){
		//La proc�dure faite � continuation s'agit de trouver le point qui sera un vertex du polygone de la vall�e qui sera � la bordure du versant(ligne de cr�te) en servant de base pour analyser des autres points du polygone.
    	// Toujours que la diff�rence d'�l�vation soit plus petite que le d�nivel� moyen g�n�ral, on va r�p�ter la proc�dure suivante:
    	// On va trouver les ar�tes voisins du point � analyser. � partir de ces ar�tes on va calculer celui qui poss�de la pente la plus forte d'entre toutes pour essayer de suivre la bordure du versant.
    	// le point ,qui poss�de la pente la plus forte, permette de suivre le chemin de la ligne de cr�te. Donc, on va stocker ce point comme le nouveau point � analyser.
    	// Finalement, on calcule la diff�rence d'�l�vation entre ce nouveau point et le premier point du squelette, si �ela est inf�rieur au d�niv�l� g�n�ral, le cycle se r�p�te.		 
				
		ArrayList<Integer> supprimerVertexBordure=new ArrayList<Integer> ();
		ArrayList<Integer> supprimerVertexSansAnalyser=new ArrayList<Integer> ();
		
		ArrayList<Integer> tempVertexBordure=new ArrayList<Integer> ();
		boolean polygoneDefectueux=false;
		
		tempVertexBordure.add(pointGaucheDroit.getId());
		Points a=topo.getTblSommets().get(this.getRavinSquelette().getListIdVertex().get(0));
		//coteSquelette=1 Si on analyse le c�t� gauche
		//coteSquelette=2 Si on analyse le c�t� droit
		//PRINT
		//System.out.println("difElevGaucheDroit "+difElevGaucheDroit+" moyenneGlobaleDeniv"+moyenneGlobaleDeniv);
		
	 	//if(pointAnalyser!=6338){
    	while (difElevGaucheDroit<moyenneGlobaleDeniv){
    		pointVerification=pointGaucheDroit;
    		double penteMax=0;
			String arete = topo.getTblSommetsAretes().get(pointAnalyser).toString().replace('[',' ').replace(']',' ').trim();
			//System.out.println("sArete"+sArete);
    		//Tokenizer permet de lire chacun des valeurs stock�s s�par�es par virgule  
        	StringTokenizer st1 = new StringTokenizer(arete,", ");
	        	while (st1.hasMoreTokens()){
	        		int numArete= Integer.parseInt(st1.nextToken());
	        		
	        		int s3=topo.getTblAretes().get(numArete).getS2();
	        		if(s3==pointAnalyser){
	        			s3=topo.getTblAretes().get(numArete).getS1();
	        		}
	        		
	        		Points c= topo.getTblSommets().get(s3);
	        		double pentePtVoisin=Math.abs(a.calculePente(c));
	        		
//	        		if(pointAnalyser==6337){
//	        			System.out.println("s3: "+s3+"penteMax "+penteMax+"pentepTVoisin "+pentePtVoisin);
//	        		}
	        		//if(penteMax<=pentePtVoisin && VertexBordure.contains(c.getId())==false && this.getRavinSquelette().getListIdVertex().contains(c.getId())==false && (vertexAnalysesGaucheDroit.contains(c.getId())==false)){
	        		//if(penteMax<=pentePtVoisin && tempVertexBordure.contains(c.getId())==false && this.getRavinSquelette().getListIdVertex().contains(c.getId())==false && topo.getTblSommetsTriangles().get(c.getId()).size()>1){
	        		
	        		if(penteMax<=pentePtVoisin && tempVertexBordure.contains(c.getId())==false && this.getRavinSquelette().getListIdVertex().contains(c.getId())==false && listVertexVersant.contains(c.getId())){
        				penteMax=pentePtVoisin;
        				pointGaucheDroit=c;
        			}			        		
	        	}	
	        	
	        	//Partie pour sortir du cycle, si on ne peut pas prendre le point � gauche ou � droite pour construire le polygone
	        	if(tempVertexBordure.contains(pointGaucheDroit.getId())){
	        		polygoneDefectueux=true;
	        		break;
	        	}
	        	tempVertexBordure.add(pointGaucheDroit.getId());

	        	//VertexBordure.add(pointGaucheDroit.getId());
	        	vertexAnalysesGaucheDroit.add(pointGaucheDroit.getId());
	        	pointBordure=pointGaucheDroit;
	        	//PRINT
	        	System.out.println("--point gaucheDroite"+pointGaucheDroit.getId()+" a: "+this.getRavinSquelette().getListIdVertex().get(0));
	        	pointAnalyser=pointGaucheDroit.getId();
	        	
	        	
	        	difElevGaucheDroit=Math.abs(a.getZ()-pointGaucheDroit.getZ());
	        	if(topo.getTblSommetsTriangles().get(pointGaucheDroit.getId()).size()<=2){
	        		difElevGaucheDroit=0;
	        	}
	        	//System.out.println("2..difElevGauche: "+difElevGauche+"moyenneGlobaleDeniv"+moyenneGlobaleDeniv);
    	}
    			VertexBordure.add(pointBordure.getId());
    			//VertexBordure.add(pointVerification.getId());
    	
    	//PRINT
    	//System.out.println("pointVerification: "+pointVerification.getId());
    	//System.out.println("pointBordure: "+pointBordure.getId());
    	
    	Points pointEvaluer=pointBordure;
    	Integer idPointEvaluer=pointBordure.getId();
    	double deniveleLocal=0;
    	
    	//Une fois qu'on a trouv� un point sur la ligne de cr�te dont la hauteur est plus �lev�e que le d�niv�l� g�n�ral, on stocke ce point � la liste de bordure
    	// ce point sera le point � la Bordure, �a veut dire que le point est "TRUE" parce que le point est sup�rieur � notre seuil fix�
    	//Maintenant, on doit chercher un point qu'il soit "FALSE", �a veut dire qu'il est inf�rieur au seuil. Ce point sera le point analys� pr�c�dement qui a �t� inf�rieur que le d�niv�l� g�n�ral.
    	//Ainsi, toujours un triangle va poss�der un point bordure(TRUE) et un point Verification(FALSE). 
    	//Le point qui reste sera le point � �valuer la diff�rence d'�l�vation, il correspondra � TRUE ou FALSE.
    	//Pour r�sumer, un triangle poss�dera soit deux points trues et un point false, ou soit deux points falses et un point true.
    	//La direction du chemin � suivre(l'ar�te a pris pour analyser en autre point) sera l'ar�te qui est forme par le  (nouveau/ancien) point bordure(true) et le (nouveau/ancien) point verification(false)
    	//
    	//				TRUE-----------FALSE
    	//					\			/	\
    	//					 \		   /	 \		
    	//					  \		  /		  \
    	//						TRUE---------FALSE
    	//							\		  /
    	//							 \		 /
    	//							  \		/
    	//							   FALSE
    	//
    	
    	
    	//Pour le point � �valuer(pointBordure), on va trouver tous les triangles voisins et extraires ses sommets. 
    	//Le point � analyser sera celui qui se trouve au m�me triangle que les points � la bordure et verification � c�t� droite(pour le voisin � gauche du premier point du squelette) ou � c�t� gauche (pour le voisin � gauche du premier point du squelette)
    	//Pour savoir le c�t� o� le point se trouve, on va utiliser le d�terminant.
    	//On calcul le d�nivel� � ce point pour savoir si le point sera stock� dans la liste de points � la Bordure(TRUE) ou de verification (FALSE). 
    	
    	String sTriangle = topo.getTblSommetsTriangles().get(idPointEvaluer).toString().replace('[',' ').replace(']',' ').trim();
		
		ArrayList<Integer> tempVoisinPtEvaluer=new ArrayList<Integer>();
    	//Tokenizer permet de lire chacun des valeurs stock�s s�par�es par virgule  
    	StringTokenizer st2 = new StringTokenizer(sTriangle,", ");
        	while (st2.hasMoreTokens()){
        	 int numTri= Integer.parseInt(st2.nextToken());
        	 //Obtenir les sommets des triangles
        	 int n1=topo.getTblTriangles().get(numTri).getS1();
        	 int n2=topo.getTblTriangles().get(numTri).getS2();
        	 int n3=topo.getTblTriangles().get(numTri).getS3();
        	 
        	 if((n1==pointBordure.getId()&& n2==pointVerification.getId())||(n2==pointBordure.getId()&& n1==pointVerification.getId())){
        		 tempVoisinPtEvaluer.add(n3);
        		 pointEvaluer=topo.getTblSommets().get(n3); 
        		 double determinant=(pointVerification.getX()*pointBordure.getY())+(pointBordure.getX()*pointEvaluer.getY())+(pointEvaluer.getX()*pointVerification.getY())
	        				-((pointEvaluer.getX()*pointBordure.getY())+(pointVerification.getX()*pointEvaluer.getY())+(pointBordure.getX()*pointVerification.getY()));
        		
        		// d�terminant sup�rieur � z�ro pour trouver les points � gauche de l'ar�te et comme �a on garanti qu'on va descendre. C�t� gauche
        		 if(determinant>0 && coteSquelette==1 && this.getRavinSquelette().getListIdVertex().contains(n3)==false){
        			 idPointEvaluer=n3;
        			 deniveleLocal=Math.abs(pointEvaluer.calculerDenivele(a));

        		 }
        		// d�terminant sup�rieur � z�ro pour trouver les points � droite de l'ar�te et comme �a on garanti qu'on va descendre. C�t� droit
        		if(determinant<0 && coteSquelette==2){
        			 idPointEvaluer=n3;
        			 deniveleLocal=Math.abs(pointEvaluer.calculerDenivele(a));

        		 }
        		        				        		 
        		 
        	 }
        	 if((n2==pointBordure.getId()&& n3==pointVerification.getId())||(n3==pointBordure.getId()&& n2==pointVerification.getId())){
        		 tempVoisinPtEvaluer.add(n1);
        		 pointEvaluer=topo.getTblSommets().get(n1); 
        		 double determinant=(pointVerification.getX()*pointBordure.getY())+(pointBordure.getX()*pointEvaluer.getY())+(pointEvaluer.getX()*pointVerification.getY())
	        				-((pointEvaluer.getX()*pointBordure.getY())+(pointVerification.getX()*pointEvaluer.getY())+(pointBordure.getX()*pointVerification.getY()));
        		 if(determinant>0 && coteSquelette==1){
        			 idPointEvaluer=n1;
        			 deniveleLocal=Math.abs(pointEvaluer.calculerDenivele(a));

        		 }else  if(determinant<0 && coteSquelette==2){
        			 idPointEvaluer=n1;
        			 deniveleLocal=Math.abs(pointEvaluer.calculerDenivele(a));

        		 }
        	 }
        	 if((n1==pointBordure.getId()&& n3==pointVerification.getId())||(n3==pointBordure.getId()&& n1==pointVerification.getId())){
        		 tempVoisinPtEvaluer.add(n2);
        		 pointEvaluer=topo.getTblSommets().get(n2); 
        		 double determinant=(pointVerification.getX()*pointBordure.getY())+(pointBordure.getX()*pointEvaluer.getY())+(pointEvaluer.getX()*pointVerification.getY())
	        				-((pointEvaluer.getX()*pointBordure.getY())+(pointVerification.getX()*pointEvaluer.getY())+(pointBordure.getX()*pointVerification.getY()));
        		 if(determinant>0 && coteSquelette==1){
        			 idPointEvaluer=n2;
        			 deniveleLocal=Math.abs(pointEvaluer.calculerDenivele(a));			        			
        		 }else  if(determinant<0 && coteSquelette==2){
        			 idPointEvaluer=n2;
        			 deniveleLocal=Math.abs(pointEvaluer.calculerDenivele(a));			        			
        		 }
        	 }
        	
        	}
        	//System.out.println("++PRueba PtVoisinsquelette "+tempVoisinPtEvaluer);
        	//Si le point se trouvent sur le squelette, faire le d�niv�l� �gal � z�ro.
        	if(this.getRavinSquelette().getListIdVertex().contains(idPointEvaluer)){
//        		for (Integer ptVoisin : tempVoisinPtEvaluer) {
//					if(this.getRavinSquelette().getListIdVertex().contains(ptVoisin)==false){
//						idPointEvaluer=ptVoisin;
//						pointEvaluer=topo.getTblSommets().get(idPointEvaluer);
//					}
//				}
        		 deniveleLocal=0;
        		
        	}
        	//On analyse le d�niv�l� par rapport au d�niv�l� g�n�ral du squelette. Si celui est plus grand on va le stocker dans la liste de vertex bordure et vertex analys�s. Le point sera stock� comme un point bordure ou un point TRUE.
        	//Sinon, on stocke le point seulement � la list de vertex analys�s.
        	pointEvaluer=topo.getTblSommets().get(idPointEvaluer); 
        	 if(deniveleLocal>moyenneGlobaleDeniv){
				 pointBordure=pointEvaluer;
				 VertexBordure.add(idPointEvaluer);
				 vertexAnalysesGaucheDroit.add(idPointEvaluer);
			 }else{
				 pointVerification=pointEvaluer;
				 vertexAnalysesGaucheDroit.add(idPointEvaluer);
			 }
        	 		        	 
        	//PRINT 
        	//System.out.println("pointEvaluer "+idPointEvaluer);
        	//System.out.println("pointBordure1 "+pointBordure.getId());
        	//System.out.println("pointVerification1 "+pointVerification.getId());
        	
   			        	  	
        	//DEUXI�ME PARTIE
        	//Jusqu'� maintenant, on poss�de comme vertex du polygone de la vall�e, le point qui se trouve sur la cr�te et celui (point TRUE) et celui qui est voisin de ce point en sens descendant (TRUE/FALSE)
        	 //� ce moment, on poss�de des �l�ments pour former notre triangle de v�rification: TRUE-FALSE-POINT � �VALUER.
        	 //Donc, � partir de cette relation, on descende jusqu'� atteindre le bout du versant. 
        	 //On analyse si le point est � point qui forme partie du polygone de la vall�e par le d�niv�l� entre le point et l'ar�te la plus proche � ce point.
        	 
    	int n=0;
   
    	
    	while(boutDuVersant.contains(idPointEvaluer)==false){
   
    	//while (n<100){
    		
    	ArrayList<Integer> pointsVoisins= new ArrayList<Integer>();
    	int idPointPrec=idPointEvaluer;
    	
    	
    	//On appelle � la fonction qui cherche le point voisin de l'arete �valu� (point bordure(TRUE) et point v�rification(FALSE). � partir de cette ar�te on va analyser ses voisins, si le voisin a d�j� analys� pr�c�demment on va l'ignorer et prendre l'autre point voisin. 
    	//Le point � �valuer sera celui qui a �t� rendu par cette fonction
    	pointsVoisins=this.getVoisinAreteauBordure(pointBordure, pointVerification, pointEvaluer, vertexAnalysesGaucheDroit, pointsVoisins, topo);
    	if(pointsVoisins.size()<3){
    		polygoneDefectueux=true;
    		break;
    	}
    	
    	idPointEvaluer=pointsVoisins.get(2);
    	pointEvaluer=topo.getTblSommets().get(idPointEvaluer); 
    	              		      	
    	
    	int m=0;
		ArrayList<Integer> exceptionBordure= new ArrayList<Integer>();
		ArrayList<Integer> exceptionVerification= new ArrayList<Integer>();
		exceptionBordure.add(pointBordure.getId());
		exceptionVerification.add(pointVerification.getId());
		
		//Si le point � �valuer nouveau est �gal au point � �valuer pr�c�dent, c'est � dire, si les points voisins ont �t� d�j� analys�s pr�c�demment.
		//On se trouve qu'on est peut-�tre r�coul� � notre chemin ou qu'on se trouve � un bosse sur le terrain.
		//On va r�p�ter la proc�dure suivante tandis que le point � �valuer actuel est �gal au point � �valuer pr�c�dant.
    	
		while(idPointEvaluer==idPointPrec){
			supprimerVertexBordure.add(pointBordure.getId());
    	//if(idPointEvaluer==idPointPrec ){
    		Points pointBordurePrec=pointBordure;
    		Points pointVerificationPrec=pointVerification;
    		ArrayList<Integer> tempListBordure=new ArrayList<Integer>();
    		ArrayList<Integer> tempListVerification=new ArrayList<Integer>();
    		tempListBordure.add(pointBordure.getId());
    		tempListVerification.add(pointVerification.getId());
    		//while(m<3){
    		
    		//Si le point � �valuer est �gal au point de la bordure(TRUE), on va analyser la liste de points voisins obtenue de la fonction au-dessus.
    		//On va poss�der certains cas: Les points voisins s'agissent d'un point Bordure(TRUE) et un point qui n'est pas trait�
    		//Les points voisins s'agissent d'un point FALSE et un point FALSE, oun un point TRUE et un point FALSE.
    		//On va trouver un nouvel point de bordure pour les cas d�crit, si aucun cas est satisfait on va changer le point de v�rification � analyser.
    		//On reappelle � la fonction pour trouver le nouveau point � �valuer
        	if(pointEvaluer==pointBordure){	
        		//PRINT
        		//System.out.println("Ancien point Verification: "+pointVerificationPrec.getId()+" Ancien point Bordure: "+pointBordurePrec.getId());
        		//Dans les cas o� les voisins sont TRUE(Bordure) et FALSE(Verification)pas trait�
        		for (int i=0;i<2;i++) {
        			int ptVoisin=pointsVoisins.get(i);
					if(VertexBordure.contains(ptVoisin)==false && exceptionVerification.contains(ptVoisin)==false){
						pointVerification=topo.getTblSommets().get(ptVoisin);
					}
        		}
        		//Dans les cas o� les voisins sont FALSE(Verification)pas trait� et FALSE(Verification)d�j� trait� ou TRUE(Bordure) et FALSE(Verification)d�j� trait�
        		if(pointVerificationPrec==pointVerification){
        			for (int i=0;i<2;i++) {
            			int ptVoisin=pointsVoisins.get(i);
            			if(exceptionVerification.contains(ptVoisin)==false){
            				//PRINT
       						//System.out.println("!!!!!!!!!!!HELP!!!!!!!!!");
       					//Dans les cas o� les voisins sont FALSE(Verification) et FALSE(Verification)
       						if(VertexBordure.contains(ptVoisin)==false){       					
       							pointVerification=topo.getTblSommets().get(ptVoisin);
       						}else{
       							pointBordure=topo.getTblSommets().get(ptVoisin);
       							idPointEvaluer=ptVoisin;
       						}
    					}
            		}
        			//PRINT
        			//System.out.println("pointBordure0 "+pointBordure.getId()+"pointVerification0 "+pointVerification.getId()+" pointEvaluer0 "+pointEvaluer.getId());
        		}
        		exceptionVerification.add(pointVerification.getId());
				pointsVoisins.clear();
				idPointPrec=idPointEvaluer;
				pointEvaluer=topo.getTblSommets().get(idPointEvaluer);
				pointsVoisins=this.getVoisinAreteauBordure(pointBordure, pointVerification, pointEvaluer, vertexAnalysesGaucheDroit, pointsVoisins, topo);
				//PRINT
				//System.out.println(pointsVoisins);
				if(pointsVoisins.size()<3){
					polygoneDefectueux=true;
					break;
	        	}
				
				idPointEvaluer=pointsVoisins.get(2);
	        	pointEvaluer=topo.getTblSommets().get(idPointEvaluer);
	        	//PRINT
	        	//System.out.println("Nouvel point Verification: "+pointVerification.getId()+"   Nouvel Point Evaluer"+pointEvaluer.getId());
	        	
        	}
        	//Si le point � �valuer est �gal au point v�rification(FALSE), on va analyser la liste de points voisins obtenue de la fonction au-dessus.
    		//On va poss�der certains cas: Les points voisins s'agissent d'un point v�rification(FALSE) et un point qui n'est pas trait�
    		//Les points voisins s'agissent d'un point pas TRUE et un point TRUE, oun un point TRUE et un point FALSE.
    		//On va trouver un nouvel point de v�rification pour les cas d�crit, si aucun cas est satisfait on va changer le point de bordure � analyser.
    		//On reappelle � la fonction pour trouver le nouveau point � �valuer
        	else if(pointEvaluer==pointVerification){
        		//PRINT
        		//System.out.println("Ancien point Bordure: "+pointBordurePrec.getId()+" Ancien point Verification: "+pointVerificationPrec.getId());
        		//Dans les cas o� les voisins sont TRUE(Bordure) pas trait� et FALSE(Verification)
        		for (int i=0;i<2;i++) {
        			int ptVoisin=pointsVoisins.get(i);
					if(VertexBordure.contains(ptVoisin) && exceptionBordure.contains(ptVoisin)==false){
						pointBordure=topo.getTblSommets().get(ptVoisin);
					}
				}
        		//Dans les cas o� les voisins sont TRUE(Bordure) et TRUE(Bordure)ou TRUE(Bordure) d�j� trait� et FALSE(Verification)
        		
        		if(pointBordurePrec==pointBordure){
        			for (int i=0;i<2;i++) {
            			int ptVoisin=pointsVoisins.get(i);
    					if(exceptionBordure.contains(ptVoisin)==false){
    						//PRINT
    						//System.out.println("!!!!!!!!!!!HELP!!!!!!!!!");
    						if(VertexBordure.contains(ptVoisin)){
    							pointBordure=topo.getTblSommets().get(ptVoisin);
    						}else{
    							pointVerification=topo.getTblSommets().get(ptVoisin);
    							idPointEvaluer=ptVoisin;
    						}
    						
    					}
    				}
        			//PRINT
        			//System.out.println("pointBordure0 "+pointBordure.getId()+"pointVerification0 "+pointVerification.getId()+" pointEvaluer0 "+pointEvaluer.getId());
        		}
        		exceptionBordure.add(pointBordure.getId());
        		pointsVoisins.clear();
				idPointPrec=idPointEvaluer;
				pointEvaluer=topo.getTblSommets().get(idPointEvaluer);
				pointsVoisins=this.getVoisinAreteauBordure(pointBordure, pointVerification, pointEvaluer, vertexAnalysesGaucheDroit, pointsVoisins, topo);
				//PRINT
				//System.out.println(pointsVoisins);
				if(pointsVoisins.size()<3){
					polygoneDefectueux=true;
					break;
	        	}
				
	        	idPointEvaluer=pointsVoisins.get(2);
	        	pointEvaluer=topo.getTblSommets().get(idPointEvaluer);
	        	//PRINT
	        	//System.out.println("Nouvel point Bordure: "+pointBordure.getId()+"   Nouvel Point Evaluer"+pointEvaluer.getId());
	        	

        	}
        	int sizeExceptBordure=exceptionBordure.size();
        	int sizeExceptVerification=exceptionBordure.size();
        	int sizeAretesVoisins=topo.getTblSommetsAretes().get(idPointEvaluer).size();
        	
        	//Si on peut sortir du bosse ou on commence � r�culer on va faire un  break pour �viter une eurreur dans le syst�me
        	//if(sizeExceptBordure==sizeAretesVoisins || sizeExceptVerification==sizeAretesVoisins ){
        	if(m==10){
        		polygoneDefectueux=true;
        		break;
        	}
        	//PRINT
        	//System.out.println("!!!!idPointEvaluer "+idPointEvaluer+"!!!!idPointPrec "+idPointPrec);
        	//System.out.println("M: "+m);
        	m++;
    	//}
    	}
		if(m==10){
			polygoneDefectueux=true;
    		break;
    	}
    	     	//On va chercher l'ar�te ou le point du squelette le plus proche au point � �valuer.
				// On fixe un seuil qui sert � analyser si le point � �valuer forme un angle inf�rieur � ce seuil avec une ar�te du squelette
				//on va appeller une fonction qui cherchera l'ar�te la plus proche au point, la distance ortohogonale  projet� au squelette et un angle qui est forme entre le point et l'ar�te du squelette.
		        	
        		double distanceMin=99999999;
        		Aretes areteBase=this.getListAreteSquelette().get(0);
        		Points pointBase=a;
        		//Aretes areteBase=null;
        		double angleBase=0;
        		Points p1=topo.getTblSommets().get(this.getListAreteSquelette().get(0).getS1());
    			Points p2=topo.getTblSommets().get(this.getListAreteSquelette().get(0).getS2());
    			Points p3=pointEvaluer;
    			int seuilAngle=95;
        		
    			ArrayList<Double> listTempValeurs=new ArrayList<Double> ();
    			
    			listTempValeurs=this.getListValeursRelieauBordure(p1, p2, p3, distanceMin, angleBase, seuilAngle, areteBase, topo);
    			if(listTempValeurs.isEmpty()==false){
    			distanceMin=listTempValeurs.get(0);
    			}
    			double distanceMoyenne=moyenneGlobaleDeniv/moyenneGlobalePente;
    			//if(listTempValeurs.isEmpty() || distanceMin>200){
    			//if(listTempValeurs.isEmpty()|| distanceMin>350){	
    			if(listTempValeurs.isEmpty()|| distanceMin>distanceMoyenne*1.5){	
    			
    				//System.out.println("********************************************angle 110************************************************");
    				listTempValeurs.clear();
    				seuilAngle=110;
    				listTempValeurs=this.getListValeursRelieauBordure(p1, p2, p3, distanceMin, angleBase, seuilAngle, areteBase, topo);
    			}else if(listTempValeurs.isEmpty()){
    				polygoneDefectueux=true;
    			}
    			//Ensuite d'�valuer la fonction avec les diff�rents seuils. On va v�rifier si la liste rendue n'est pas vide.
    			//Dans les cas o� la taille de la liste est 3, la liste correspond � la distance obtenue par rapport aux ar�tes du squelette.
    			//Dans les cas o� la taille de la liste est 2, la liste correspond � la distance obtenue par rapport aux vertex du squelette. La taille est 2 parce qu'il ne rend pas l'angle d'un triangle.
    			//Si la liste est vide, �a veut dire que le point n'a pas �t� �value par aucun des crit�res fix�s par un vall�e. Donc, il  correspond possiblement � une zone plane.
    			
    			if(listTempValeurs.isEmpty()==false){
    			distanceMin=listTempValeurs.get(0);
    			//PRINT
    			//System.out.println("DIstance Minime "+distanceMin);
    			
	    			if(listTempValeurs.size()==3){
	    				int tempIdArete=listTempValeurs.get(1).intValue();
		    			areteBase=topo.getTblAretes().get(tempIdArete);
		    			angleBase=listTempValeurs.get(2);
	    			}else{
	    				int tempIdPoint=listTempValeurs.get(1).intValue();
	    				pointBase=topo.getTblSommets().get(tempIdPoint);
	    				angleBase=-1;
	    			}
    			}else{
    				//supprimerVertexSansAnalyser.add(p3.getId());
    				supprimerVertexBordure.add(p3.getId());
    				
    			}
    			double distanceP=0;
    			double penteLocale=0;
    			//Si la distance � �t� calcul� par rapport � une ar�te du squelette. �a veut dire, si il'y a un valeur d'angle du triangle qui forme l'ar�te et le poin � �valuer, on fera:
    			//Calculer de forme ordon�e les vertex de l'ar�te du squeltte. calculer la distances ou ar�tes du triangle ar�te et point � �valuer.
    			//Faire une int�rpolation lin�ale pour obtenir l'�l�vation au point qui est intersect� l'ar�te avec la projection de la distance du point � �valuer.
    			//Si la distance � �t� calcul� para rapport � un vertex du squelette, on peut faire la diff�rence d'�l�vation de mani�re directe entre le point � �valuer et le vertex du squelette.
    			if(angleBase!=-1){
    				//PRINT
        			//System.out.println("Arete Base "+areteBase.getId()+"Point 3: "+p3.getId());
        			//System.out.println("Arete Base "+areteBase.getId()+"Point 3: "+p3.getId());
    			
        			p1=topo.getTblSommets().get(areteBase.getS1());
        			p2=topo.getTblSommets().get(areteBase.getS2());
        			
        			int indexP1=this.getRavinSquelette().getListIdVertex().indexOf(p1.getId());
        			int indexP2=this.getRavinSquelette().getListIdVertex().indexOf(p2.getId());
        			
        			if(indexP1>indexP2){
        				p1=topo.getTblSommets().get(areteBase.getS2());
        				p2=topo.getTblSommets().get(areteBase.getS1());
        				indexP1=this.getRavinSquelette().getListIdVertex().indexOf(p1.getId());
        				indexP2=this.getRavinSquelette().getListIdVertex().indexOf(p2.getId());
        			}
        		
	        		double areteA=p1.calculerDistance(p2);
	        		double areteB=p2.calculerDistance(p3);
	        		double areteC=p3.calculerDistance(p1);
        					        		
        			
	        	
		        	double z1=p1.getZ();
		        	double z2=p2.getZ();
		        	
		        	double composantK=(z2-z1)/areteA;
		        	distanceP=areteC*Math.cos(angleBase);
		        	double intersectionZ=(composantK*distanceP)+z1;
		        	
		        	
		        	deniveleLocal=Math.abs(p3.getZ()-intersectionZ);
		        	penteLocale=Math.abs(Math.rint(deniveleLocal)/distanceMin);
		        	if(p3.getId()==46465){
			        	System.out.println("1 deniveleLocal: "+deniveleLocal+" moyenneGlobaleDeniv: "+moyenneGlobaleDeniv);
			        	System.out.println("1 distanceMin: "+distanceMin+" AreteBase "+areteBase.getId());
			        	System.out.println("1 penteLocal: "+penteLocale+" moyenneGlobalePente: "+moyenneGlobalePente);
			        	penteLocale=moyenneGlobalePente+1;
			        	}
		        	
    			}else{
    				p1=pointBase;
    				deniveleLocal=Math.abs(p3.calculerDenivele(pointBase));
    				penteLocale=Math.abs(p3.calculePente(pointBase));
    				
    				if(p3.getId()==46465){
			        	System.out.println("2 deniveleLocal: "+deniveleLocal+" moyenneGlobaleDeniv: "+moyenneGlobaleDeniv);
			        	System.out.println("2 distanceP: "+p3.calculerDistance(pointBase));
			        	System.out.println("2 penteLocal: "+penteLocale+" moyenneGlobalePente: "+moyenneGlobalePente);
			        	}
    			}
		        	
		        	
		        	//Partie pour ignorer les points qui se trouvent dans le squelette
		        	if(listVertexVersant.contains(p3.getId())){
		        		deniveleLocal=moyenneGlobaleDeniv+1;
		        		penteLocale=moyenneGlobalePente+1;
		        	}
		        	if(this.getRavinSquelette().getListIdVertex().contains(p3.getId())){
        				deniveleLocal=0 ;
        				distanceMin=0;
        				penteLocale=0;
        				//break;
        			}
		        	
		        	//System.out.println("Id point "+pointEvaluer.getId()+"pente local: "+penteLocale+" penteGlobale"+moyenneGlobalePente);
		        //Ici, on va analyser si le d�niv�l� � ce point est sup�rieur au d�niv�l� g�n�ral. S'il est sup�rieur le point sera un point du polygone de la vall�e (point bordure/TRUE) sinon il sera un point de v�rification(FALSE)
		        //On garde aussi la distance  et le d�niv�l� dans nouvelle listes.
		        if(deniveleLocal>moyenneGlobaleDeniv){
		        //if(penteLocale>moyenneGlobalePente){	    	   
		        	
	    			pointBordure=pointEvaluer;	    			
        			VertexBordure.add(pointEvaluer.getId());        			        			
        			//vertexAnalysesGaucheDroit.add(idPointEvaluer);
        			vertexAnalysesGaucheDroit.add(pointEvaluer.getId());
		        	calculerCoordonneeMoyenne(coteSquelette, p1, p2, p3, angleBase, distanceP, moyenneGlobaleDeniv, distanceMin,listVertexVersant);
		        	//calculerCoordonneeMoyenne(coteSquelette, p1, p2, p3, angleBase, distanceP, moyenneGlobalePente, distanceMin,listVertexVersant);
        			
		        	if(listVertexVersant.contains(pointEvaluer.getId())==false && this.getRavinSquelette().getListIdVertex().contains(pointEvaluer.getId())==false && supprimerVertexBordure.contains(pointEvaluer.getId())==false){

        				setDeniveleMoyenBordure(pointEvaluer.getId(),deniveleLocal);
        				setDistanceMoyenBordure(pointEvaluer.getId(),distanceMin);
        			}
        		 }else{
        			
 		        		
 		        	
        			pointVerification=pointEvaluer;
        			vertexAnalysesGaucheDroit.add(pointEvaluer.getId());
        		}
		        	
		        		        	 
		    //PRINT
    	
//        	System.out.println("pointEvaluer "+pointEvaluer.getId());
//        	System.out.println("pointBordure2 "+pointBordure.getId());
//        	System.out.println("pointVerification2 "+pointVerification.getId());
//        	System.out.println("deniveleLocal: "+deniveleLocal+"moyenneGlobaleDeniv: "+moyenneGlobaleDeniv);
    	 
        	n=n+1;
	} 
    	
    	
    	
    	//Une fois obtenue notres vertex du polygone de la vall�e, on va simplifier en supprimant les d�tails non pertinent de notre liste
    	//On va supprimer les point qui poss�de dans un triangle deux voisins qui sont points de la bordure du polygone. 
    	//Cela nous indique que le point est ne donne pas une valeur ajout�e parce que les points voisins du triangles sont d�j� pris en compte en la d�finition du polygone ( On fait cela pour doucer le polygone)
    	// On parcour la liste de vertex du polygone, On va d�finir des vertex pr�c�dent, actuel et suivant.
    	//On va obtenir les ar�tes du vertex actuel pour obtenir les ar�tes qui forment avec le vertex pr�c�dent et suivant.
    	//De ces ar�tes (pr�c�dent et suivant), on obtient ses triangles voisins. � partir de ces triangles, on trouve les point voisins de ces ar�tes. Si ce point est un point qui fait parti du polygone, on rend une variable comme true
    	//Si pour les ar�te pr�c�dent et suivant, la variable est TRUE, on supprime ce point de la liste de vertex du polygone de la vall�e.
    	
    	ArrayList<Integer> simplifierVertexBordure=new ArrayList<Integer> ();
    	
    	
    	for(int i=1;i<VertexBordure.size()-2;i++){
    		int vertexPrecedent=VertexBordure.get(i-1);
    		int vertexActuel=VertexBordure.get(i);
    		int vertexSuivant=VertexBordure.get(i+1);
    		Aretes aretePrecedente=null;
    		Aretes areteSuivante=null;
    		
    		String arete = topo.getTblSommetsAretes().get(vertexActuel).toString().replace('[',' ').replace(']',' ').trim();
    		
    		//Tokenizer permet de lire chacun des valeurs stock�s s�par�es par virgule  
        	StringTokenizer stArete = new StringTokenizer(arete,", ");
	        	while (stArete.hasMoreTokens()){
	        		int numArete= Integer.parseInt(stArete.nextToken());
	        		
	        		int sommetAuxiliaire=topo.getTblAretes().get(numArete).getS2();
	        		if(sommetAuxiliaire==vertexActuel){
	        			sommetAuxiliaire=topo.getTblAretes().get(numArete).getS1();
	        		}
	        		
	        		if(sommetAuxiliaire==vertexPrecedent){
	        			aretePrecedente=topo.getTblAretes().get(numArete);
	        		}
	        		if(sommetAuxiliaire==vertexSuivant){
	        			areteSuivante=topo.getTblAretes().get(numArete);
	        		}
	        	}
	        	
	        	if(aretePrecedente!=null && areteSuivante!=null){
	        	boolean tvPrecedentVerBordure=false;
	        	String triangleVoisinPrecedent = topo.getTblAretesTriangles().get(aretePrecedente.getId()).toString().replace('[',' ').replace(']',' ').trim();
	    		
	    		
	        	//Tokenizer permet de lire chacun des valeurs stock�s s�par�es par virgule  
	        	StringTokenizer stTriaPrec= new StringTokenizer(triangleVoisinPrecedent,", ");
	            	while (stTriaPrec.hasMoreTokens()){
	            	 int numTri= Integer.parseInt(stTriaPrec.nextToken());
	            	 //Obtenir les sommets des triangles
	            	 int sommetTriPrec=topo.getTblTriangles().get(numTri).getS1();
	            	 if(sommetTriPrec==vertexPrecedent || sommetTriPrec==vertexActuel){
	            		 sommetTriPrec=topo.getTblTriangles().get(numTri).getS2();
	            	 }
	            	 if(sommetTriPrec==vertexPrecedent || sommetTriPrec==vertexActuel){
	            		 sommetTriPrec=topo.getTblTriangles().get(numTri).getS3();
	            	 }
	            	 
	            	 if(VertexBordure.contains(sommetTriPrec)){
	            		 tvPrecedentVerBordure=true;
	            	 }
	            	 
	            	}
	            	
	            	boolean tvSuivantVerBordure=false;
		        	String triangleVoisinSuivant = topo.getTblAretesTriangles().get(areteSuivante.getId()).toString().replace('[',' ').replace(']',' ').trim();

	            	//Tokenizer permet de lire chacun des valeurs stock�s s�par�es par virgule  
		        	StringTokenizer stTriaSui= new StringTokenizer(triangleVoisinSuivant,", ");
		            	while (stTriaSui.hasMoreTokens()){
		            	 int numTri= Integer.parseInt(stTriaSui.nextToken());
		            	 //Obtenir les sommets des triangles
		            	 int sommetTriSui=topo.getTblTriangles().get(numTri).getS1();
		            	 if(sommetTriSui==vertexSuivant || sommetTriSui==vertexActuel){
		            		 sommetTriSui=topo.getTblTriangles().get(numTri).getS2();
		            	 }
		            	 if(sommetTriSui==vertexSuivant || sommetTriSui==vertexActuel){
		            		 sommetTriSui=topo.getTblTriangles().get(numTri).getS3();
		            	 }
		            	 
		            	 if(VertexBordure.contains(sommetTriSui)){
		            		 tvSuivantVerBordure=true;
		            	 }
		            	 
		            			            	 
		            	}
		            	
		            	if(tvPrecedentVerBordure==true && tvSuivantVerBordure==true){
		            		 simplifierVertexBordure.add(vertexActuel);
		            	 }	   
	        	}
    		
    	}
//    	if(supprimerVertexSansAnalyser.contains(144782)==false){
//    		System.out.println("NOOOOO ContienNEEEE eseee HPPP");
//    	}else{
//    		System.out.println("SIII ContienNEEEE eseee HPPP");
//    	}
    	
    	///Apartir d'ici,, reprendre
    	
    	//On supprime les points des traitements au-dessus, simplification et les points qui a �t� � un bosse du terrain.
    	VertexBordure.removeAll(supprimerVertexBordure);
    	VertexBordure.removeAll(simplifierVertexBordure);
    	
    	// On va supprimer la distance et le d�niv�l� des points qui sont reli�s � la listes supprimer et simplifier vertex de la bordure du polygone de la vall�e.
    	for (Integer pointSupprimer : simplifierVertexBordure) {
			if(this.MapDeniveleBordure.containsKey(pointSupprimer)){
				this.MapDeniveleBordure.remove(pointSupprimer);
			}
			if(this.MapDistanceBordure.containsKey(pointSupprimer)){
				this.MapDistanceBordure.remove(pointSupprimer);
			}
		}
    	for (Integer pointSupprimer : supprimerVertexBordure) {
			if(this.MapDeniveleBordure.containsKey(pointSupprimer)){
				this.MapDeniveleBordure.remove(pointSupprimer);
			}
			if(this.MapDistanceBordure.containsKey(pointSupprimer)){
				this.MapDistanceBordure.remove(pointSupprimer);
			}
		}
    	
    	for (Integer pointSupprimer : supprimerVertexSansAnalyser) {
    		if(this.MapDeniveleBordure.containsKey(pointSupprimer)){
				this.MapDeniveleBordure.remove(pointSupprimer);
			}
			if(this.MapDistanceBordure.containsKey(pointSupprimer)){
				this.MapDistanceBordure.remove(pointSupprimer);
			}
			
		}
    	
    	
//    	for (Integer vertex : VertexBordure) {
//			if(CoordonneesNouveauPointX.containsKey(vertex)){
//				double nouveauPointX=CoordonneesNouveauPointX.get(vertex);
//				double nouveauPointY=CoordonneesNouveauPointY.get(vertex);
//				Points p3=topo.getTblSommets().get(vertex);
//				p3.setX(nouveauPointX);
//		        p3.setY(nouveauPointY);
//			}
//		}
    	if(polygoneDefectueux==true){
    		VertexBordure.clear();
    		vertexAnalysesGaucheDroit.clear();
    		//VertexBordure.add(a.getId());
    	}
    	
    	//System.out.println(this.MapDistanceBordure);
    	return VertexBordure;
	}
	
	 public void calculerCoordonneeMoyenne(int coteSquelette,Points p1, Points p2, Points p3, double angleBase, double distanceP,double moyenneGlobaleDeniv, double distanceMin, ArrayList<Integer> listVertexVersant){
	        double diffX=p3.getX()-p1.getX();
	        double diffY=p3.getY()-p1.getY();
	        
	        if(p3.getId()==212165){
	        	System.out.println("**++///--- p1.getId "+p1.getId());
	        }
	        
	        double azimut= Math.atan2(diffX,diffY);
	        
	        if(azimut<0){
	            azimut=azimut+(2*Math.PI);
	        }
	        
	        double angRotation=azimut;
	        double dx=distanceP;
	        double dy=distanceP;
	        double xOrtho=p1.getX();
	        double yOrtho=p1.getY();
	        double zOrtho=p1.getZ();
	        
//	        if(p1.getId()==3311 || p3.getId()==3311){
//	            System.out.println("p1 "+ p1.getId());
//	            System.out.println("p3 "+ p3.getId());
//	            System.out.println("angleBase "+angleBase);
//	            System.out.println("distanceP "+distanceP);
//	            System.out.println("azimut "+azimut);
//	        }
//	        if(angleBase==0){
//	            System.out.println("p3 "+ p3.getId());
//	        }
	        
	        if(angleBase!=-1){
	        
	        if(coteSquelette==2){
	            angRotation=azimut+angleBase;
	            if(angRotation<(Math.PI/2)){
	                dx=distanceP*Math.cos((Math.PI/2)-angRotation);
	                dy=distanceP*Math.sin((Math.PI/2)-angRotation);
	                xOrtho=xOrtho+dx;
	                yOrtho=yOrtho+dy;
	            }
	            else if(angRotation<(Math.PI) && angRotation>(Math.PI/2)){
	                dx=distanceP*Math.sin((Math.PI)-angRotation);
	                dy=distanceP*Math.cos((Math.PI)-angRotation);
	                xOrtho=xOrtho+dx;
	                yOrtho=yOrtho-dy;
	            }
	            else if(angRotation<(1.5*Math.PI) && angRotation>(Math.PI)){
	                dx=distanceP*Math.cos((1.5*Math.PI)-angRotation);
	                dy=distanceP*Math.sin((1.5*Math.PI)-angRotation);
	                xOrtho=xOrtho-dx;
	                yOrtho=yOrtho-dy;
	            }
	            else if(angRotation<(2*Math.PI) && angRotation>(1.5*Math.PI)){
	                dx=distanceP*Math.sin((2*Math.PI)-angRotation);
	                dy=distanceP*Math.cos((2*Math.PI)-angRotation);
	                xOrtho=xOrtho-dx;
	                yOrtho=yOrtho+dy;
	            }
	            else if(angRotation==(Math.PI/2)){
	                xOrtho=xOrtho+dx;
	                yOrtho=yOrtho;
	            }
	            else if(angRotation==(Math.PI)){
	                xOrtho=xOrtho;
	                yOrtho=yOrtho-dy;
	            }
	            else if(angRotation==(1.5*Math.PI)){
	                xOrtho=xOrtho-dx;
	                yOrtho=yOrtho;
	            }
	            else if(angRotation==(2*Math.PI)){
	                xOrtho=xOrtho;
	                yOrtho=yOrtho+dy;
	            }
	        }else if(coteSquelette==1){
	            angRotation=azimut-angleBase;
	            if(angRotation<(Math.PI/2)){
	                dx=distanceP*Math.sin(angRotation);
	                dy=distanceP*Math.cos(angRotation);
	                xOrtho=xOrtho+dx;
	                yOrtho=yOrtho+dy;
	            }
	            else if(angRotation<(Math.PI) && angRotation>(Math.PI/2)){
	                dx=distanceP*Math.cos(angRotation-(Math.PI/2));
	                dy=distanceP*Math.sin(angRotation-(Math.PI/2));
	                xOrtho=xOrtho+dx;
	                yOrtho=yOrtho-dy;
	            }
	            else if(angRotation<(1.5*Math.PI) && angRotation>(Math.PI)){
	                dx=distanceP*Math.sin(angRotation-(Math.PI));
	                dy=distanceP*Math.cos(angRotation-(Math.PI));
	                xOrtho=xOrtho-dx;
	                yOrtho=yOrtho-dy;
	            }
	            else if(angRotation<(2*Math.PI) && angRotation>(1.5*Math.PI)){
	                dx=distanceP*Math.cos(angRotation-(1.5*Math.PI));
	                dy=distanceP*Math.sin(angRotation-(1.5*Math.PI));
	                xOrtho=xOrtho-dx;
	                yOrtho=yOrtho+dy;
	            }
	            else if(angRotation==(Math.PI/2)){
	                xOrtho=xOrtho+dx;
	                yOrtho=yOrtho;
	            }
	            else if(angRotation==(Math.PI)){
	                xOrtho=p1.getX();
	                yOrtho=p1.getY()-dy;
	            }
	            else if(angRotation==(1.5*Math.PI)){
	                xOrtho=xOrtho-dx;
	                yOrtho=yOrtho;
	            }
	            else if(angRotation==(2*Math.PI)){
	                xOrtho=xOrtho;
	                yOrtho=yOrtho+dy;
	            }
	            
	        }
	        //Calcul de la coordonn�e Z pour un point nouveau 
	        zOrtho=(distanceP*(p2.getZ()-p1.getZ())/p1.calculerDistance(p2))+p1.getZ();
	        
	        }
	        
	        double zNP=zOrtho+moyenneGlobaleDeniv;    
	        double PremierComposant=(moyenneGlobaleDeniv)*distanceMin;
	        double PremierComposant1=(zNP-zOrtho)*distanceMin;
	        double DeuxiemeComposant=(p3.getZ()-zOrtho);
	        double distanceNP=Math.abs((moyenneGlobaleDeniv)*distanceMin/(p3.getZ()-zOrtho));
//	        double penteP3=(p3.getZ()-zNP)/distanceMin;
//	        double distanceNP=distanceMin*moyenneGlobaleDeniv/penteP3;
//	        if (distanceNP<0){
//	            //System.out.println("distanceNP "+distanceNP+ "moyenneGlobaleDeniv "+moyenneGlobaleDeniv);
//	            distanceNP=distanceMin;
//	        }
	        
//	        System.out.println("Ver P1: "+p1.getId()+" p3: "+p3.getId()+"moyenneGlobaleDeniv"+moyenneGlobaleDeniv);
//	        System.out.println("distanceNP "+distanceNP+"zOrtho: "+zOrtho+" zNP "+zNP+"p3.getZ(): "+p3.getZ());
//	        System.out.println("PremierComposant "+PremierComposant);
//	        System.out.println("DeuxiemeComposant "+DeuxiemeComposant);
	        
	        diffX=p3.getX()-xOrtho;
	        diffY=p3.getY()-yOrtho;
	        double azimutNP=Math.atan2(diffX,diffY);
	        double xNP=xOrtho;
	        double yNP=yOrtho;
	        
	        if(azimutNP<0){
	            azimutNP=azimutNP+(2*Math.PI);
	        }
	        
	        if(azimutNP<(Math.PI/2)){
	            dx=distanceNP*Math.sin(azimutNP);
	            dy=distanceNP*Math.cos(azimutNP);
	            xNP=xNP+dx;
	            yNP=yNP+dy;
	        }
	        else if(azimutNP<(Math.PI) && azimutNP>=(Math.PI/2)){
	            dx=distanceNP*Math.sin((Math.PI)-azimutNP);
	            dy=distanceNP*Math.cos((Math.PI)-azimutNP);
	            xNP=xNP+dx;
	            yNP=yNP-dy;
	        }
	        else if(azimutNP<(1.5*Math.PI) && azimutNP>=(Math.PI)){
	            dx=distanceNP*Math.sin(azimutNP-(Math.PI));
	            dy=distanceNP*Math.cos(azimutNP-(Math.PI));
	            xNP=xNP-dx;
	            yNP=yNP-dy;
	        }
	        else if(azimutNP<(2*Math.PI) && azimutNP>=(1.5*Math.PI)){
	            dx=distanceNP*Math.sin((2*Math.PI)-azimutNP);
	            dy=distanceNP*Math.cos((2*Math.PI)-azimutNP);
	            xNP=xNP-dx;
	            yNP=yNP+dy;
	        }
	        //CoordonneesNouveauPointX.put(p3.getId(), xNP);       
	        //CoordonneesNouveauPointY.put(p3.getId(), yNP);       
	        
	        double difCorX=Math.abs(p3.getX()-xNP);
	        double difCorY=Math.abs(p3.getY()-yNP);
	        
	        
	        //if(distanceNP<distanceMin ){
	        //if(difCorX<=distanceMin && difCorY<=distanceMin){
	        if(listVertexVersant.contains(p3.getId())==false && this.getRavinSquelette().getListIdVertex().contains(p3.getId())==false){
	        p3.setX(xNP);
	        p3.setY(yNP);
	        p3.setZ(zNP);
	        }
	        
//	        System.out.println("Ver AngBase: "+angleBase);
//	        System.out.println("Ver P1: "+p1.getId());
//	        System.out.println("X: "+xOrtho);
//	        System.out.println("Y: "+yOrtho);
	        //System.out.println(p3.getId()+","+xOrtho+","+yOrtho+","+zOrtho);
	        //System.out.println(p3.getId()+","+xNP+","+yNP+","+zNP);
	        

	        
//	        try(FileWriter fw = new FileWriter("C:/Users/ancom32/Documents/Test.txt", true);
//	                BufferedWriter bw = new BufferedWriter(fw);
//	                PrintWriter out = new PrintWriter(bw))
//	            {
//	                out.println(p3.getId()+","+xNP+","+yNP+","+zNP);
//	               
//	            } catch (IOException e) {
//	                //exception handling left as an exercise for the reader
//	            }
	        
	    }

	
		
	
}
