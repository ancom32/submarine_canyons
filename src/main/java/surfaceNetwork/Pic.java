package surfaceNetwork;

import topologie.Points;

import java.util.ArrayList;


/** 
 * <b> Classe Pic </b>
 * <p> 
 * Cette classe represente un Pic dans un SurfaceNetwork. Cet objet possede par 
 * definition la plus haute elevation parmis tous ses Sommets voisins. De plus, 
 * cette classe herite de PointCritique.
 * 
 * @see PointCritique
 *                
 * @author Vincent Dupont et Andrés Cortés.
 * @since 2015-08-11
 * 
 */

public class Pic extends PointCritique
{
	
	
	/**
	 * La liste des Crete avec lesquelles ce Pic est relie.
	 */
	private ArrayList<Crete> listCretes = new ArrayList<Crete>(); 
	/**
	 * Un attribut qui indique le poid du pic dans le réseau.
	 */
	private double poid;
	
	/**
	 * Le type de PointCritique, soit "Pic".
	 */
	private String type = "Pic";      
		
	/**
	 * Ce constructeur permet de creer un Pic a partir d'un Sommet.
	 * @param p_sommet Le sommet de ce Pic.
	 */
	public Pic(Points p_sommet)
	{
		super(p_sommet);
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste des Cretes de ce Pic.
	 * @return ArrayList<Crete> La liste de tous les Cretes liees à ce Pic
	 */
	public ArrayList<Crete> getListCretes()
	{
		return this.listCretes;
	}
	
	/**
	 * Cette methode permet d'avoir acces au type de cette objet.
	 * @return String Un objet String qui represente le type de l'objet soit "Pic".
 	 */
	public String getType()
	{
		return this.type;
	}
	
	/**
	 * Cette methode permet d'avoir acces au poid de cette objet.
	 * @return Double la valeur de pondération qui represente le Pic dans le réseau
 	 */
	public Double getPoid(){
		return this.poid;
	}
	

	
	
	/**
	 * Cette methode permet de calculer une pondération pour le pic.
	 */
	public void setPoid(){
		double pente=0;
		double sumPente=0;
		int count=0;
		for (Crete crete : this.getListCretes()) {
			pente=this.getSommet().calculePente(crete.getCol().getSommet());
			sumPente=Math.abs(pente)+sumPente;
			count++;
		}	
		this.poid=sumPente/count;

		
	}
	
	
	/**
	 * Cette methode permet d'assigner une pondération fixe pour le pic.
	 * @param p_poid Un poid fixé pour le pic.
	 */
	public void setPoid(double p_poid){
		this.poid=p_poid;
	}
	
	
	/**
	 * Cette methode permet d'ajouter une Crete a ce Pic.
	 * @param p_crete Un Crete qui a pour origine ce Pic.
	 */
	public void ajouterCretes(Crete p_crete)
	{
		this.listCretes.add(p_crete);
	}
	
	/**
	 * Cette methode permet de supprimer une Crete a ce Pic.
	 * @param p_crete la crête à supprimer.
	 */
	public void supprimerCretes(Crete p_crete)
	{
		this.listCretes.remove(p_crete);
	}
	
	/**
	 * Cette methode permet de trouver le pic à gauche à partir des lignes de crêtes du col
	 * @return le col à gauche.
 	 */
	public Col getColGauche(){
		Col colGauche=this.getListCretes().get(0).getCol();
		for (Crete crete : this.getListCretes()) {
		Col colEvaluer=crete.getCol();
		if(colEvaluer.getSommet().getX()<=colGauche.getSommet().getX()){
			colGauche=colEvaluer;
			}
			
		}
		return colGauche;
	}
	
	/**
	 * Cette methode permet de trouver le col le plus élévé par rapport à leurs voisins, excepté le col de paramètre.
	 * @param colRestreint col qui sera excepté pour le calcul du col le plus élévé.
	 * @return le col le plus élévé.
 	 */
	public Col getColPlusHautRestreint(Col colRestreint){
		Col colPlusHaut=this.getListCretes().get(0).getCol();
		if(colPlusHaut==colRestreint){
			colPlusHaut=this.getListCretes().get(1).getCol();
		}
		for (Crete crete : this.getListCretes()) {
		Col colEvaluer=crete.getCol();
		if(colEvaluer!=colRestreint){
		if(colEvaluer.getSommet().getZ()>colPlusHaut.getSommet().getZ()){
					colPlusHaut=colEvaluer;
			}
		}
		}
		return colPlusHaut;
	}
	
	/**
	 * Cette methode permet de trouver le col le plus élévé par rapport à une liste de crêtes.
	 * @param p_listCrete liste de crêtes pour trouver le col le plus élévé.
	 * @return le col le plus élévé.
 	 */
	public Col getColPlusHaut(ArrayList<Crete> p_listCrete){
		Col colPlusHaut=p_listCrete.get(0).getCol();
		for (Crete crete : p_listCrete) {
			if(crete.getCol().getSommet().getZ()>colPlusHaut.getSommet().getZ()){
				colPlusHaut=crete.getCol();
			}
		}
		return colPlusHaut;
	}
	
	/**
	 * Cette methode permet de trouver le col à droite par rapport à leurs voisins, excepté le col de paramètre.
	 * @param colRestreint col qui sera excepté pour le calcul du col à droite.
	 * @return le col à droite.
 	 */
	
	public Col getColDroite(Col colRestreint){
		Col colDroite=this.getListCretes().get(0).getCol();
		if(colDroite==colRestreint){
			colDroite=this.getListCretes().get(1).getCol();
		}
		for (Crete crete : this.getListCretes()) {
		Col colEvaluer=crete.getCol();
		if(colEvaluer!=colRestreint && (colEvaluer.getRavin1().getPuit().getID()<0 || colEvaluer.getRavin2().getPuit().getID()<0)){
			if(colEvaluer.getSommet().getX()>colDroite.getSommet().getX()){
				colDroite=colEvaluer;
			}
		}
		}
		return colDroite;
	}
	
	
	/**
	 * Cette methode permet de trouver le col le plus bas par rapport à une liste de crêtes.
	 * @param p_listCrete liste de crêtes pour trouver le col le moins élévé.
	 * @return le col le moins élévé.
 	 */
	public Col getColPlusBas(ArrayList<Crete> p_listCrete){
		Col colPlusBas=p_listCrete.get(0).getCol();
		for (Crete crete : p_listCrete) {
			if(crete.getCol().getSommet().getZ()<colPlusBas.getSommet().getZ()){
				colPlusBas=crete.getCol();
			}
		}
		return colPlusBas;
	}
	
	/**
	 * Cette methode permet de trouver le col le plus bas par rapport à une liste de crêtes, excepté le col de paramètre.
	 * @param p_listCrete liste de crêtes pour trouver le col le moins élévé.
	 * @param colContraint col qui sera excepté pour le calcul du col le plus bas.
	 * @return le col le moins élévé.
 	 */
	public Col getColPlusBasContraint(ArrayList<Crete> p_listCrete,Col colContraint){
		Col colPlusBas=p_listCrete.get(0).getCol();
		if(colPlusBas==colContraint){
			colPlusBas=p_listCrete.get(1).getCol();
		}
		for (Crete crete : p_listCrete) {
			if (crete.getCol()!=colContraint){
				if(crete.getCol().getSommet().getZ()<colPlusBas.getSommet().getZ()){
					colPlusBas=crete.getCol();
				}
			}
		}
		return colPlusBas;
	}
	
}
