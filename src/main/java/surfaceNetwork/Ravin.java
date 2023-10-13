package surfaceNetwork;

import java.util.ArrayList;

import topologie.Points;
import topologie.Topologie;




/**
 * <b> Classe Ravin </b>
 * <p>
 * Cette classe represente un Ravin. Celui-ci relie un point Col et un Puit dans un SurfaceNetwork.
 *                
 * @author Vincent Dupont et Andrés Cortés
 * @since 2015-08-12
 * 
 */
public class Ravin 
{
	/**
	 * Le Col de ce Ravin.
	 */
	private Col col;
	
	/**
	 * Le Puit de ce Ravin.
	 */
	private Puit puit;
	
	/**
	 * La liste des Vertex qui composent ce Ravin.
	 */
	private ArrayList<Integer> listIdVertex = new ArrayList<Integer>(); 
	
	/**
	 * L'id de ce Ravin.
	 */
	private Integer id;
	
	/**
	 * Ce constructeur permet de construire un objet Ravin a partir d'un Col et d'un Puit.
	 * @param p_col Le Col de ce Ravin.
	 * @param p_puit Le Puit de ce Ravin.
	 */
	public Ravin(Col p_col, Puit p_puit)
	{
		this.col = p_col;
		this.puit = p_puit;
	}
	
	/**
	 * Cette methode permet d'avoir acces au Col de ce Ravin.
	 * @return Col Le col de ce Ravin.
	 */
	public Col getCol()
	{
		return this.col;
	}
	
	/**
	 * Cette methode permet d'avoir acces au Puit de ce Ravin.
	 * @return Puit Le puit de ce Ravin.
	 */
	public Puit getPuit()
	{
		return this.puit;
	}
	
	/**
	 * Cette methode permet d'obtenir l'Identificateur du Ravin.
	 * @return L'ID du Ravin.
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Cette methode permet d'avoir acces a la liste des Vertex qui composent ce Ravin.
	 * @return ArrayList<Integer> La liste des Vertex.
	 */
	public ArrayList<Integer> getListIdVertex()
	{
		return this.listIdVertex;
	}
	
	/**
	 * Cette methode permet d'assigner un Col a ce Ravin.
	 * @param p_col Le nouveau Col de ce Ravin.
	 */
	public void setCol(Col p_col)
	{
		this.col = p_col;
	}
	
	/**
	 * Cette methode permet d'assigner un Puit a ce Ravin.
	 * @param p_puit Le nouveau Puit de ce Ravin.
	 */
		public void setPuit(Puit p_puit)
		{
			this.puit = p_puit;
		}
		
		/**
		 * Cette methode permet d'assigner un Identificateur a ce Ravin.
		 * @param p_pic Le nouveau ID de ce Ravin.
		 */
		public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Cette methode permet d'ajouter l'ID du vertex qui compose ce Ravin a la liste.
	 * @param p_idVertex L'Id du Vertex qui compose ce Ravin.
	 */
	public void ajouterIdVertex(Integer p_idVertex)
	{
		this.listIdVertex.add(p_idVertex);
	}
	
	/**
	 * Cette methode permet d'ajouter un Array des vertex qui composent ce Ravin a la liste.
	 * @param ArrayList<Integer>  L'Array de l'Id des Vertex qui composent ce Ravin.
	 */
	public void ajouterIdVertexAll(ArrayList<Integer> p_ArrayVertex)
	{
		this.listIdVertex.addAll(p_ArrayVertex);
	}
	
	public double calculerDistanceVertex(ArrayList<Integer> p_ArrayVertex, Topologie topo){
		double sumDist=0;
		for(int i=1;i<p_ArrayVertex.size();i++){
			Points VertexActuel=topo.getTblSommets().get(p_ArrayVertex.get(i));
			Points VertexAnterieur=  topo.getTblSommets().get(p_ArrayVertex.get(i-1));
			double dist=VertexActuel.calculerDistance(VertexAnterieur);
			sumDist=sumDist+dist;
		}
		return sumDist;
	}
	
	
}
