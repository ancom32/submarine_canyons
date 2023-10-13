package surfaceNetwork;

import topologie.Points;

/**
 * <b> Classe PointCritique (abstract) </b>
 * <p>
 * 	Cette classe abstraite permet de definir et de manipuler les generalitees 
 *  d'un PointCritique dans un Surface Network. Les classes Pic, Puit et Col en herite.
 *  
 *  @author Vincent Dupont et Andrés Cortés
 *  @since 2015-08-11
 * 
 */
public abstract class PointCritique 
{
	
	private boolean estRestreint=false;
	
	/**
	 * Le Sommet du ce PointCritique.
	 */
	private Points sommetPC;
	
	/**
	 * Ce constructeur permet de construire un PointCritique a partir d'un Sommet. De plus, 
	 * seule les classes enfants de PointCritique peuvent l'utiliser.
	 * @param p_sommetPC le Sommet du PointCritique
	 */
	protected PointCritique(Points p_sommetPC)
	{
		this.sommetPC = p_sommetPC;
	}
	
	/**
	 * Cette methode permet d'avoir acces au Sommet de ce PointCritique.
	 * @return Sommet Le sommet du PointCritique.
	 */
	public Points getSommet()
	{
		return this.sommetPC;
	}
	
	/**
	 * Cette methode permet d'avoir acces a l'identidifiant du Sommet de ce PointCritique.
	 * @return double L'ID du PointCritique. 
	 */
	public double getID()
	{
		return this.sommetPC.getId();
	}
	
	/**
	 * Cette methode permet d'avoir acces a l'elevation du Sommet du PointCritique.
	 * @return double L'elevation du PointCritique.
	 */
	public double getElevation()
	{
		return this.sommetPC.getZ();
	}
	
	/**
	 * Cette methode permet de savoir si le point est restreint.
	 * @return Boolean vrai ou faux si le point est restreint
 	 */
	public boolean getEstRestreint(){
		return this.estRestreint;
	}
	
	/**
	 * Cette methode permet d'assigner la valeur de restreinte (vrai) à un point Critique.
	 * @param p_poid Un poid fixé pour le pic.
	 */
	public void setEstRestreint(){
		this.estRestreint=true;
	}
	
	public void setEstPasRestreint(){
		this.estRestreint=false;
	}
	
	/**
	 * Cette methode abstraite permet de connaitre le type du PointCritique. Cette methode doit etre definie dans 
	 * toutes les classes qui heritent de PointCritique.
	 * @return String Un objet String qui represente le type de l'objet (ex : "Col", "Pic" ou "Puit").
	 */
	abstract String getType();
	
	
}
