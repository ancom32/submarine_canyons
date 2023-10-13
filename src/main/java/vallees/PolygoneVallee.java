package vallees;

import java.util.ArrayList;
import java.util.HashMap;

import topologie.Points;
import topologie.Topologie;

public class PolygoneVallee {
	
	private int id;
	
	private ArrayList<Integer> vertexDuFondVallee= new ArrayList<Integer> ();
	
	/**
	 * Le Map des Valeurs de dénivélé entre les points du squelette et la bordure.
	 */
	private HashMap<Integer,Double> MapDeniveleBordure = new HashMap<Integer,Double>();
	
	/**
	 * Le Map des Valeurs de distance entre les points du squelette et la bordure.
	 */
	private HashMap<Integer,Double>  MapDistanceBordure = new HashMap<Integer,Double> ();
	
	private Topologie topo;
	
	private Versant versant;
	
	public PolygoneVallee(int id, ArrayList<Integer> vertexDuFondVallee,
			HashMap<Integer, Double> mapDeniveleBordure,
			HashMap<Integer, Double> mapDistanceBordure,
			Topologie topo, Versant versant) {
		super();
		this.id = id;
		this.vertexDuFondVallee = vertexDuFondVallee;
		this.MapDeniveleBordure = mapDeniveleBordure;
		this.MapDistanceBordure = mapDistanceBordure;
		this.topo=topo;
		this.versant=versant;
	}
	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(int id){
		this.id=id;
	}
		
	public ArrayList<Integer> getVertexDuFondVallee(){
		return this.vertexDuFondVallee;
	}
	
	public HashMap<Integer,Double> getMapDeniveleBordure(){
		return this.MapDeniveleBordure;
	}
	public HashMap<Integer,Double> getMapDistanceBordure(){
		return this.MapDistanceBordure;
	}
	
	public void setVertexDuFondValleeAll(ArrayList<Integer> vertexDuFondVallee){
		this.vertexDuFondVallee=vertexDuFondVallee;
	}
	
	public void setMapDeniveleBordureAll(HashMap<Integer,Double> MapDeniveleBordure ){
		this.MapDeniveleBordure=MapDeniveleBordure;
	}
	public void setMapDistanceBordureAll(HashMap<Integer,Double> MapDistanceBordure){
		this.MapDistanceBordure=MapDistanceBordure;
	}

	/**
	 * Cette methode donne la valeur du dénivélé moyen pour le polygone de la vallée.
	 * @return La valeur du dénivélé moyen pour le polygone de la vallée.
	 */	
	public Double getDeniveleMoyenBordure(){
		double sumDenivele=0;
		double deniveleMoyenBordure;
		for (Double deniveleLocal : this.MapDeniveleBordure.values()) {
			sumDenivele=deniveleLocal+sumDenivele;
		}
		deniveleMoyenBordure=sumDenivele/this.MapDeniveleBordure.size();
		return deniveleMoyenBordure;
	}
	
	/**
	 * Cette methode donne la valeur de la distance moyenne pour le polygone de la vallée.
	 * @return La valeur du distance moyenne pour le polygone de la vallée.
	 */	
	public Double getDistanceMoyenBordure(){
		double sumDistance=0;
		double distanceMoyenBordure;
		for (Double distanceMinimale : this.MapDistanceBordure.values()) {
			sumDistance=distanceMinimale+sumDistance;
		}
		distanceMoyenBordure=sumDistance/this.MapDistanceBordure.size();
		return distanceMoyenBordure;
	}
	
	/**
	 * Cette methode permet de classer les diférentes formes de vallées.
	 * @return Un texte qui indique le type de la vallée. Par exemple "Canyon".
	 */	
	public String classerDesVallees(){
		String typeVallee=null;
		double deniveleMoyenBordure=getDeniveleMoyenBordure();
		double distanceMoyenBordure=getDistanceMoyenBordure();
		
		double relationBordure=deniveleMoyenBordure/distanceMoyenBordure;
		
		if(relationBordure>0.044 && relationBordure<0.12){
			typeVallee="Canyon";
		}
		
		if(relationBordure>0.005 && relationBordure<0.044){
			typeVallee="Chenal";
		}
		
		return typeVallee;
		
	}
	
	public double calculerEcartTypeDist(){
		ArrayList<Integer> supprimerEcartDist= new ArrayList<Integer>();
		int n=0;
		double ecartTypeDist=0;
		double sumEcart=0;
		double distanceMoyenBordure=getDistanceMoyenBordure();
		for (Integer vertex : vertexDuFondVallee) {
			
			if(MapDistanceBordure.containsKey(vertex)){
				
				double distVertex=	MapDistanceBordure.get(vertex);
				double difDistances=Math.abs(distVertex-distanceMoyenBordure);
				if(difDistances<3*distanceMoyenBordure){
					double ecartVertex=Math.pow((difDistances),2);
					//System.out.println(vertex+" distVerex "+distVertex+ " dist Moyenne "+distanceMoyenBordure+" ecartVertex "+ecartVertex);
					sumEcart=sumEcart+ecartVertex;
					n=n+1;
				}
				else{
					//System.out.println(vertex+" distVerex "+distVertex+ " dist Moyenne "+distanceMoyenBordure);
					//if(versant.getListVertexVersant().contains(vertex)==false){
					supprimerEcartDist.add(vertex);
					//}
				}
			}
			else{
				if(versant.getListVertexVersant().contains(vertex)==false){
				supprimerEcartDist.add(vertex);
				}
			}
		}
		System.out.println(supprimerEcartDist);
		double variance=sumEcart/(MapDistanceBordure.size());
		ecartTypeDist=Math.sqrt(variance);
		
		this.vertexDuFondVallee.removeAll(supprimerEcartDist);
		
		return ecartTypeDist;
	}
	
	public double calculerEcartTypeDenivele(){
		ArrayList<Integer> supprimerEcartDenivele= new ArrayList<Integer>();
		int n=0;
		double ecartTypeDenivele=0;
		double sumEcart=0;
		double deniveleMoyenBordure=getDeniveleMoyenBordure();
		for (Integer vertex : vertexDuFondVallee) {
			if(MapDeniveleBordure.containsKey(vertex)){
				
				double deniveleVertex=	MapDeniveleBordure.get(vertex);
				double difDeniveles=Math.abs(deniveleVertex-deniveleMoyenBordure);
				if(difDeniveles<2*deniveleMoyenBordure){
				double ecartVertex=Math.pow((difDeniveles),2);
				//System.out.println(vertex+" deniveleVerex "+deniveleVertex+"denivele Moyen"+deniveleMoyenBordure+" ecartDenivele "+ecartVertex);
				sumEcart=sumEcart+ecartVertex;
				n=n+1;
				}
			else{
				//if(versant.getListVertexVersant().contains(vertex)==false){
				supprimerEcartDenivele.add(vertex);
				//}
			}
			}
			else{
				if(versant.getListVertexVersant().contains(vertex)==false){
				supprimerEcartDenivele.add(vertex);
				}
			}
		}
		System.out.println(supprimerEcartDenivele);
		double variance=sumEcart/(MapDeniveleBordure.size());
		ecartTypeDenivele=Math.sqrt(variance);
		
		this.vertexDuFondVallee.removeAll(supprimerEcartDenivele);
		
		return ecartTypeDenivele;
	}
	
	public double calculerMoyenneElevation(){
		double moyenneElevation=0;
		double sumElevation=0;
		for (Integer vertex : vertexDuFondVallee) {
			Points pointVertex=topo.getTblSommets().get(vertex);
			double elevation=Math.abs(pointVertex.getZ());
			sumElevation=sumElevation+elevation;
		}
		moyenneElevation=sumElevation/vertexDuFondVallee.size();
		return moyenneElevation;
	}
	
	public double supprimerVertexParElevation(){
		
				
		double ecartTypeElevation=0;
		
		double sumEcart=0;
		ArrayList<Integer> supprimerEcartElevation= new ArrayList<Integer>();
		
		double moyenneElevation=calculerMoyenneElevation();	
		
		
		for(Integer vertex : vertexDuFondVallee){
			int n=0;
			Points pointVertex=topo.getTblSommets().get(vertex);
			double elevation=Math.abs(pointVertex.getZ());
			double difElevation=Math.abs(elevation-moyenneElevation);
			
			double mp=0.5*moyenneElevation;
			if(elevation<1.5*moyenneElevation){
				//System.out.println(vertex+" elevationVertex "+elevation+"elevation Moyen"+moyenneElevation+" difElevation "+difElevation);
				double ecartVertex=Math.pow((difElevation),2);
				sumEcart=sumEcart+ecartVertex;
				n=n+1;
			}else{
				
				//if(versant.getListVertexVersant().contains(vertex)==false){
					supprimerEcartElevation.add(vertex);
				//}
			}
			
		}
		System.out.println(supprimerEcartElevation);
		double variance=sumEcart/(MapDeniveleBordure.size());
		ecartTypeElevation=Math.sqrt(variance);
		
		this.vertexDuFondVallee.removeAll(supprimerEcartElevation);
		
		//moyenneElevation=calculerMoyenneElevation();	
		//System.out.println("new moyenen Elevation"+moyenneElevation);
		for(Integer vertex : vertexDuFondVallee){
			int n=0;
			Points pointVertex=topo.getTblSommets().get(vertex);
			double elevation=Math.abs(pointVertex.getZ());
			//if(elevation>moyenneElevation+(1.8*ecartTypeElevation) || elevation<moyenneElevation-(1.8*ecartTypeElevation)){
			if(elevation<moyenneElevation-(2.5*ecartTypeElevation)){
				supprimerEcartElevation.add(vertex);			
			}
		}
	
		this.vertexDuFondVallee.removeAll(supprimerEcartElevation);
		
		if(vertexDuFondVallee.isEmpty()==false){
			if(this.vertexDuFondVallee.get(0)!=this.vertexDuFondVallee.get(this.vertexDuFondVallee.size()-1)){
				this.vertexDuFondVallee.add(this.vertexDuFondVallee.get(0));
			}
		}
		
		
		return ecartTypeElevation;
	}

		
	
	
	

}
