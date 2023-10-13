package surfaceNetwork;

import java.util.ArrayList;


/** 
 * <b> Classe Crete </b>
 * <p>
 * Cette classe represente une Crete. Elle relie un point Col et un Pic dans un SurfaceNetwork.
 *                
 * @author Vincent Dupont et Andrés Cortés.
 * @since 2015-08-12
 * 
 */
public class Crete 
{
	/**
	 * Le Col de cette Crete.
	 */
	private Col col;
	
	/**
	 * Le Pic de cette Crete.
	 */
	private Pic pic;
	
	/**
	 * La liste de l'Id du Vertex qui composent cette Crete.
	 */
	private ArrayList<Integer> listIdVertex = new ArrayList<Integer>();
	
	/**
	 * L'id de cette Crete.
	 */
	private Integer id;
	
	
	
	/**
	 * Ce constructeur permet de construire un objet Crete a partir d'un Col et d'un Pic.
	 * @param p_col Le Col de cette Crete.
	 * @param p_pic Le Pic de cette Crete.
	 */
	public Crete(Col p_col, Pic p_pic)
	{
		this.col = p_col;
		this.pic = p_pic;
	}
	
	/**
	 * Cette methode permet d'avoir acces au Col de cette Crete.
	 * @return Col Le col de la Crete.
	 */
	public Col getCol()
	{
		return this.col;
	}
	
	/**
	 * Cette methode permet d'avoir acces au Pic de cette Crete.
	 * @return Pic Le pic de la Crete.
	 */
	public Pic getPic()
	{
		return this.pic;
	}
	
	/**
	 * Cette methode permet d'obtenir l'Identificateur de la ligne de crête.
	 * @return L'ID de la Crete.
	 */	
	
	public Integer getId() {
		return id;
	}

	/**
	 * Cette methode permet d'avoir acces a la liste des Vertex qui composent cette Crete.
	 * @return ArrayList<Integer> La liste des Vertex.
	 */
	public ArrayList<Integer> getListIdVertex()
	{
		return this.listIdVertex;
	}
	
	/**
	 * Cette methode permet d'assigner un Col a cette Crete.
	 * @param p_col Le nouveau Col de cette Crete.
	 */
	public void setCol(Col p_col)
	{
		this.col = p_col;
	}
	
	/**
	 * Cette methode permet d'assigner un Pic a cette Crete.
	 * @param p_pic Le nouveau Pic de cette Crete.
	 */
	public void setPic(Pic p_pic)
	{
		this.pic = p_pic;
	}
	
	/**
	 * Cette methode permet d'assigner un Identificateur a cette Crete.
	 * @param p_pic Le nouveau ID de cette Crete.
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * Cette methode permet d'ajouter l'ID du Vertex qui compose cette Crete a la liste.
	 * @param Idvertex L'Id du Vertex qui compose cette Crete.
	 */
	public void ajouterIdVertex(Integer p_idvertex)
	{
		this.listIdVertex.add(p_idvertex);
	}
	
	/**
	 * Cette methode permet d'ajouter un Array d'ID des Vertex qui composent cette Crete a la liste.
	 * @param ArrayList<Integer> Array d'Id des Vertex qui composent cette Crete.
	 */
	public void ajouterIdVertexAll(ArrayList<Integer> p_ArrayVertex)
	{
		this.listIdVertex.addAll(p_ArrayVertex);
	}
	
	
	
}
