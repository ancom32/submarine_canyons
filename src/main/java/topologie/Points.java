package topologie;

/** 
 * <b>Classe Points</b>
 * <p> 
 * Cette classe represente un Sommet ou un Point dans la triangulation.
 * Un Sommet est construit par trois coordonnées(x,y,z) et un identificateur. 
 *   
 * @author Andrés Camilo Cortés Murcia
 * @since  2015-07-09
 * 
 */
public class Points {
	
	/**
	 * L'identificateur du Point de type entier.
	 */
	private int id;
	/**
	 * La coordonnée dans l'axe X du point.
	 */
    private double x;
    /**
	 * La coordonnée dans l'axe Y du point.
	 */
    private double y;
    /**
	 * La coordonnée dans l'axe Z du point.
	 */
    private double z;
    
    /**
	 * Ce constructeur permet de construire un objet Point à partir des coordonnées X,Y,Z et un identificateur. 
	 * @param X La coordonnée X du point.
	 * @param Y La coordonnée Y du point.
	 * @param Z La coordonnée Z du point.
	 * @param ID Numéro identifiant le point.
	 */
	public Points(double x, double y, double z,int id) {
		//super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.id=id;
	}
	
	/**
	 * Cette methode permet de savoir l'identificateur du point.
	 * @return id Le retour est l'identificateur du point.
	 */
	public int getId() {
		return id;
	}
	/**
	 * Cette méthode permet de savoir la coordonnée X du point.
	 * @return x La coordonnée X du point.
	 */
	public double getX() {
		return x;
	}
	/**
	 * Cette méthode permet de savoir la coordonnée Y du point.
	 * @return y La coordonnée Y du point.
	 */
	public double getY() {
		return y;
	}
	/**
	 * Cette méthode permet de savoir la coordonnée Z du point.
	 * @return z La coordonnée Z du point.
	 */
	public double getZ() {
		return z;
	}
	/** 
	 * Cette methode permet d'assigner un identificateur au Point.
	 * @param id l'identificateur du point.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/** 
	 * Cette methode permet d'assigner une valeur de la coordonnée X au Point.
	 * @param x la nouvelle coordonnée X du point.
	 */
	public void setX(double x) {
		this.x = x;
	}
	/** 
	 * Cette methode permet d'assigner une valeur de la coordonnée Y au Point.
	 * @param y la nouvelle coordonnée Y du point.
	 */
	public void setY(double y) {
		this.y = y;
	}
	/** 
	 * Cette methode permet d'assigner une valeur de la coordonnée Z au Point.
	 * @param z la nouvelle coordonnée Z du point.
	 */
	public void setZ(double z) {
		this.z = z;
	}
    
	
	/** Cette methode permet de faire une comparaison entre deux points (Point Actuel(Point A) et Point Voisin(Point B),
	 *  en identifiant si le point A est plus élévé que le point B.
	 * @param  point Point à comparer avec le point actuel.
	 * @see Points
	 * @return Boolean Si le point A est plus grand que le B, retour la valeur de true.
	 */
	public boolean estPlusGrand(Points point){
		boolean estPlusGrand=false;
		
		if(this.getZ()>point.getZ()){
			estPlusGrand=true;
		}
		else if(this.getZ()<point.getZ()){
			estPlusGrand=false;
		}
		
		else if(this.getZ()==point.getZ()){
			
			if(this.getX()>point.getX()){
				estPlusGrand=true;
			}
			else if(this.getX()<point.getX()){
				estPlusGrand=false;
			}
			else if(this.getX()==point.getX()){
				
				if(this.getY()>point.getY()){
					estPlusGrand=true;
				}
				else if(this.getY()<point.getY()){
					estPlusGrand=false;
				}
							
			}
		}
		
		return estPlusGrand;
	}
	
	/** Cette methode permet de faire une comparaison entre deux points (Point Actuel(Point A) et Point Voisin(Point B),
	 *  en identifiant si le point A est plus bas que le point B.
	 * @param  point Point B à comparer avec le point actuel A.
	 * @see Points
	 * * @return Boolean Si le point A est plus bas que le B, retour la valeur de true.
	 */
	public boolean estPlusPetit(Points point){
		boolean estPlusPetit=false;
		
		if(this.getZ()<point.getZ()){
			estPlusPetit=true;
		}
		else if(this.getZ()>point.getZ()){
			estPlusPetit=false;
		}
		
		else if(this.getZ()==point.getZ()){
			
			if(this.getX()<point.getX()){
				estPlusPetit=true;
			}
			else if(this.getX()>point.getX()){
				estPlusPetit=false;
			}
			else if(this.getX()==point.getX()){
				
				if(this.getY()<point.getY()){
					estPlusPetit=true;
				}
				else if(this.getY()>point.getY()){
					estPlusPetit=false;
				}
							
			}
		}
		
		return estPlusPetit;
	}
	
	/** Cette methode permet de calculer la pente, entre un point voisin(point B) et le point Actuel A
	 * Si la valeur de la pente est positive indique que la pente se trouve en direction de croissance
	 * Si la valeur de la pente est negative indique que la pente se trouve en direction de décroissance
	 * @param  point Point B à comparer avec le point actuel A.
	 * @see Points
	 * @return Valeur de la Pente.
	 */
	public double calculePente(Points point){
		
		double deltaZ=point.getZ()-this.getZ();
		double deltaX=point.getX()-this.getX();
		double deltaY=point.getY()-this.getY();
		double deltaHoriz= Math.sqrt(Math.pow((deltaX), 2)+Math.pow((deltaY), 2));
		double pente=deltaZ/deltaHoriz;
		
		return pente;
	}
	
	/** Cette methode permet de calculer la distance entre un point voisin(point B) et le point Actuel A
	 * @param  point Point B à comparer avec le point actuel A.
	 * @see Points
	 * @return Valeur de la distance.
	 */
	public double calculerDistance(Points point){
		double deltaX=point.getX()-this.getX();
		double deltaY=point.getY()-this.getY();
		double distance= Math.sqrt(Math.pow((deltaX), 2)+Math.pow((deltaY), 2));
		return distance;
	}
	
	public double calculerDenivele(Points point){
		double denivele=this.getZ()-point.getZ();
		return denivele;
	}
}
