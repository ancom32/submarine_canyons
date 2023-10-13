package topologie;

/** 
 * <b>Classe Arêtes</b>
 * <p> 
 * Cette classe represente un arête dans la triangulation.
 * Un arête est construit par deux sommets.
 *   
 * @author Andrés Camilo Cortés Murcia
 * @since  2015-07-09
 * 
 */
public class Aretes {
	/**
	 * L'identificateur de l'arête.
	 */
	private int id;
	/**
	 * L'identificateur du sommet 1 de l'arête.
	 */
    private int s1;
    /**
	 * L'identificateur du sommet 2 de l'arête.
	 */
    private int s2;
    
    /**
   	 * Ce constructeur permet de construire un objet Arête à partir des identificateurs des deux sommets. 
   	 * @param sommet1 L'id du sommet 1.
   	 * @param sommet2 L'id du sommet 2.
   	 */
	public Aretes(int s1, int s2) {
		super();
		this.s1 = s1;
		this.s2 = s2;
	}
	 /**
	 * Cette methode permet de savoir l'identificateur de l'arête.
	 * @return identificateur de l'arête.
	 */
	public int getId() {
		return id;
	}
	/**
	* Cette methode permet de savoir l'identificateur du sommet 1.
	* @return identificateur du sommet 1.
	*/
	public int getS1() {
		return s1;
	}
	 /**
	* Cette methode permet de savoir l'identificateur du sommet 2.
	* @return identificateur du sommet 2.
	*/
	public int getS2() {
		return s2;
	}
	/** 
	 * Cette methode permet d'assigner un identificateur à l'arête 1.
	 * @param id l'identificateur de l'arête.
	 */	
	public void setId(int id) {
		this.id = id;
	}
	/** 
	* Cette methode permet d'assigner un identificateur au sommet 1.
	* @param id l'identificateur du sommet 1.
	*/
	public void setS1(int s1) {
		this.s1 = s1;
	}
	/** 
	* Cette methode permet d'assigner un identificateur au sommet 2.
	* @param id l'identificateur du sommet 2.
	*/
	public void setS2(int s2) {
		this.s2 = s2;
	}

	
    
}
