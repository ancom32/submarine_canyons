package topologie;

/** 
 * <b>Classe Triangles</b>
 * <p> 
 * Cette classe represente un triangle dans la triangulation.
 * Un Triangle est construit soit par l'id de trois points ou par l'id de trois points et de trois arêtes.
 *   
 * @author Andrés Camilo Cortés Murcia
 * @since  2015-07-09
 * 
 */
public class Triangles {
	  //déclaration des variables qui défine la classe triangle, un id, trois sommets, trois triangles adjacents
   
	/**
	 * L'identificateur du  triangle.
	 */
    private int id;
	/**
	 * L'identificateur du somemt 1 du triangle.
	 */
    private int s1;
    
    /**
	 * L'identificateur du somemt 2 du triangle.
	 */
    private int s2;
    
    /**
	 * L'identificateur du somemt 3 du triangle.
	 */
    private int s3;
    
    /**
	 * L'identificateur de l'arête 1 du triangle.
	 */
    private int a1;
    /**
	 * L'identificateur de l'arête 2 du triangle.
	 */
    private int a2;
    /**
	 * L'identificateur de l'arête 3 du triangle.
	 */
    private int a3;
    
    /**
   	 * Ce constructeur permet de construire un objet Triangle à partir des identificateurs des trois sommets. 
   	 * @param sommet1 L'id du sommet 1.
   	 * @param sommet2 L'id du sommet 2.
   	 * @param sommet3 L'id du sommet 3.
   	 */
    public Triangles(int id,int sommet1, int sommet2, int sommet3) {
		// TODO Auto-generated constructor stub
    	super();
    	this.id=id;
    	this.s1=sommet1;
    	this.s2=sommet2;
    	this.s3=sommet3;
    	
	}
    /**
   	 * Ce constructeur permet de construire un objet Triangle à partir des identificateurs des trois sommets et des trois arêtes. 
   	 * @param sommet1 L'id du sommet 1.
   	 * @param sommet2 L'id du sommet 2.
   	 * @param sommet3 L'id du sommet 3.
   	 * @param arete1 L'id de l'arête 1.
   	 * @param arete2 L'id de l'arête 2.
   	 * @param arete3 L'id de l'arête 3.
   	 */
    public Triangles(int id,int sommet1, int sommet2, int sommet3, int arete1, int arete2, int arete3) {
		// TODO Auto-generated constructor stub
    	super();
    	this.id=id;
    	this.s1=sommet1;
    	this.s2=sommet2;
    	this.s3=sommet3;
    	this.a1=arete1;
    	this.a2=arete2;
    	this.a3=arete3;
    	    	
	}
    /**
	 * Cette methode permet de savoir l'identificateur du sommet 1.
	 * @return identificateur du triangle.
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
	 * Cette methode permet de savoir l'identificateur du sommet 3.
	 * @return identificateur du sommet 3.
	 */
	public int getS3() {
		return s3;
	}
	/**
	 * Cette methode permet de savoir l'identificateur de l'arête 1.
	 * @return identificateur de l'arête 1.
	 */
	public int getA1() {
		return a1;
	}
	/**
	 * Cette methode permet de savoir l'identificateur de l'arête 2.
	 * @return identificateur de l'arête 2.
	 */
	public int getA2() {
		return a2;
	}
	/**
	 * Cette methode permet de savoir l'identificateur de l'arête 3.
	 * @return identificateur de l'arête 3.
	 */
	public int getA3() {
		return a3;
	}
	/** 
	 * Cette methode permet d'assigner un identificateur au triangle.
	 * @param id l'identificateur du triangle.
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
	/** 
	 * Cette methode permet d'assigner un identificateur au sommet 3.
	 * @param id l'identificateur du sommet 3.
	 */
	public void setS3(int s3) {
		this.s3 = s3;
	}
	/** 
	 * Cette methode permet d'assigner un identificateur à l'arête 1.
	 * @param id l'identificateur de l'arête 1.
	 */
	public void setA1(int a1) {
		this.a1 = a1;
	}
	/** 
	 * Cette methode permet d'assigner un identificateur à l'arête 2.
	 * @param id l'identificateur de l'arête 2.
	 */
	public void setA2(int a2) {
		this.a2 = a2;
	}
	/** 
	 * Cette methode permet d'assigner un identificateur à l'arête 3.
	 * @param id l'identificateur de l'arête 3.
	 */
	public void setA3(int a3) {
		this.a3 = a3;
	}
    

       

}
