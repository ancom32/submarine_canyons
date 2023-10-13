package surfaceNetwork;

import topologie.Points;

/** 
 * <b> Classe Col </b>
 * <p> 
 * 	Cette classe represente un Col dans un SurfaceNetwork. Cet objet possede par definition 
 *  2 Cretes et 2 Ravins. De plus, elle herite de la classe PointCritique.
 * @author Vincent Dupont et Andrés Cortés.
 * @since 2015-08-11
 * @see PointCritique
 * 
 */
public class Col extends PointCritique
{
	
	/**
	 * La premiere Crete.
	 */
	private Crete crete1;
	
	/**
	 * La deuxieme Crete.
	 */
	private Crete crete2;
	
	/**
	 * Le premier Ravin
	 */
	private Ravin ravin1;
	
	/**
	 * Le deuxieme Ravin
	 */
	private Ravin ravin2;
	
	/**
	 * Le type de ce PointCritique, soit "Col" .
	 */
	private String type = "Col";
	
	
	/**
	 * Ce constructeur permet de constuire un Col a partir d'un Sommet.
	 * @param p_sommet Le Sommet du Col.
	 */
	public Col(Points p_sommet)
	{
		super(p_sommet);
	}
	
	/**
	 * Ce constructeur permet de constuire un Col a partir d'un Sommet, de 2 Cretes et de 2 Ravins.
	 * @param p_sommet Le Sommet du Col.
	 * @param p_crete1 La Crete1 du Col.
	 * @param p_crete2 La Crete2 du Col.
	 * @param p_ravin1 Le Ravin1 du Col.
	 * @param p_ravin2 Le Ravin2 du Col.
	 */
	public Col(Points p_sommet, Crete p_crete1, Crete p_crete2, Ravin p_ravin1, Ravin p_ravin2)
	{
		super(p_sommet);
		
		this.crete1 = p_crete1;
		this.crete2 = p_crete2;
		this.ravin1 = p_ravin1;
		this.ravin2 = p_ravin2;
		
	}
	
	/**
	 * Cette methode permet d'avoir acces a la crete1 de ce Col.
	 * @return Crete La crete1 de ce Col.
	 */
	public Crete getCrete1()
	{
		return this.crete1;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la crete2 de ce Col.
	 * @return Crete La crete2 de ce Col.
	 */
	public Crete getCrete2()
	{
		return this.crete2;
	}
	
	/**
	 * Cette methode permet d'avoir acces au ravin1 de ce Col.
	 * @return Ravin La ravin1 de ce Col.
	 */
	public Ravin getRavin1()
	{
		return this.ravin1;
	}
	
	/**
	 * Cette methode permet d'avoir acces au ravin2 de ce Col.
	 * @return Ravin La ravin2 de ce Col.
	 */
	public Ravin getRavin2()
	{
		return this.ravin2;
	}
	
	/**
	 * Cette methode permet d'avoir acces au type de cette objet,
	 * @return String Un objet String qui represente le type de l'objet soit "Col".
 	 */
	public String getType()
	{
		return this.type;
	}
	
	/**
	 * Cette methode permet d'assigner une crete1.
	 * @param p_crete1 La nouvelle Crete1 de ce Col.
	 */
	public void setCrete1(Crete p_crete1)
	{
		this.crete1 = p_crete1;
	}
	
	/**
	 * Cette methode permet d'assigner une crete2.
	 * @param p_crete2 La nouvelle Crete2 de ce Col.
	 */
	public void setCrete2(Crete p_crete2)
	{
		this.crete2 = p_crete2;
	}
	
	/**
	 * Cette methode permet d'assigner un ravin1.
	 * @param p_ravin1 Le nouveau Ravin1 de ce Col.
	 */
	public void setRavin1(Ravin p_ravin1)
	{
		this.ravin1 = p_ravin1;
	}
	
	/**
	 * Cette methode permet d'assigner un ravin2.
	 * @param p_ravin2 Le nouveau Ravin2 de ce Col.
	 */
	public void setRavin2(Ravin p_ravin2)
	{
		this.ravin2 = p_ravin2;
	}
	
	public Pic trouverPicGauche(){
		Pic picGauche=this.getCrete1().getPic();
		Pic picEvaluer=this.getCrete2().getPic();
		
		if(picGauche.getSommet().getX()>=picEvaluer.getSommet().getX()){
			picGauche=picEvaluer;
		}
		
		return picGauche;
	}
	
	public Pic trouverPicDroit(){
		Pic picDroit=this.getCrete1().getPic();
		Pic picEvaluer=this.getCrete2().getPic();
		
		if(picDroit.getSommet().getX()<=picEvaluer.getSommet().getX()){
			picDroit=picEvaluer;
		}
		
		return picDroit;
	}
}
