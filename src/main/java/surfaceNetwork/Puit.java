package surfaceNetwork;

import java.util.ArrayList;



import topologie.Points;

/** 
 * <b> Classe Puit </b>
 * <p> 
 * Cette classe represente un Puit dans un SurfaceNetwork. Cet objet possede par definition la plus 
 * faible elevation parmis tous ses Sommets voisins. De plus, cette classe herite de PointCritique.
 *                
 * @author Vincent Dupont et Andrés Cortés.
 * @since 2015-08-11
 * 
 */
public class Puit extends PointCritique
{
	/**
	 * La liste des Ravins qui sont lies a ce Puit.
	 */
	private ArrayList<Ravin> listRavins = new ArrayList<Ravin>(); 
	
	/**
	 * Un attribut qui indique le poid du puit dans le réseau.
	 */
	private double poid;
	/**
	 * Le type de PointCritique, soit "Puit".
	 */
	private String type = "Puit";                          
	
	/**
	 * Ce constructeur permet de construire un objet Puit a partir de son Sommet.
	 * @param p_sommet Le Sommet de ce Puit.
	 */
	public Puit(Points p_sommet)
	{
		super(p_sommet);
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste des Ravins de ce Puit.
	 * @return ArrayList<Ravin> La liste de tous les Ravins lies à ce Puit.
	 */
	public ArrayList<Ravin> getListRavins()
	{
		return this.listRavins;
	}
	
	/**
	 * Cette methode permet d'avoir acces au type de cette objet.
	 * @return String Un objet String qui represente le type de l'objet soit "Puit".
 	 */
	public String getType()
	{
		return this.type;
	}
	
	/**
	 * Cette methode permet d'avoir acces au poid de cette objet.
	 * @return Double la valeur de pondération qui represente le Puit dans le réseau.
 	 */
	public Double getPoid(){
		return this.poid;
	}
	
	/**
	 * Cette methode permet d'assigner une pondération pour le puit.
	 */
	public void setPoid(){
		double pente=0;
		double sumPente=0;
		int count=0;
		for (Ravin ravin : this.getListRavins()) {
			pente=this.getSommet().calculePente(ravin.getCol().getSommet());
			sumPente=Math.abs(pente)+sumPente;
			count++;
		}
		this.poid=sumPente/count;
		
	}
	
	/**
	 * Cette methode permet d'ajouter un Ravin a la liste des Ravins lies a ce Puit.
	 * @param p_ravin Le nouveau Ravin.
	 */
	public void ajouterRavin(Ravin p_ravin)
	{
		this.listRavins.add(p_ravin);
	}
	
	/**
	 * Cette methode permet de supprimer un Ravin de la liste des Ravins lies a ce Puit.
	 * @param p_ravin Le Ravin à supprimer.
	 */
	public void supprimerRavin(Ravin p_ravin)
	{
		this.listRavins.remove(p_ravin);
	}
	
	public Col getColPlusBas(ArrayList<Ravin> p_listRavins){
		Col colPlusBas=p_listRavins.get(0).getCol();
		for (Ravin ravin : p_listRavins) {
			if(ravin.getCol().getSommet().getZ()<colPlusBas.getSommet().getZ()){
				colPlusBas=ravin.getCol();
			}
		}
		return colPlusBas;
	}
	
	public Col getColPlusBasContraint(ArrayList<Ravin> p_listRavins,Col colContraint){
		Col colPlusBas=p_listRavins.get(0).getCol();
		if(colPlusBas==colContraint){
			colPlusBas=p_listRavins.get(1).getCol();
		}
		for (Ravin ravin : p_listRavins) {
			if (ravin.getCol()!=colContraint){
				if(ravin.getCol().getSommet().getZ()<colPlusBas.getSommet().getZ()){
					colPlusBas=ravin.getCol();
				}
			}
		}
		return colPlusBas;
	}
	
	
}
