package topologie;

/** 
 * <b>Classe Points</b>
 * <p> 
 * Cette classe represente un Sommet ou un Point dans la triangulation.
 * Un Sommet est construit par trois coordonn�es(x,y,z) et un identificateur. 
 *   
 * @author Andr�s Camilo Cort�s Murcia
 * @since  2015-07-09
 * 
 */
public class Points {
	
	/**
	 * L'identificateur du Point de type entier.
	 */
	private int id;
	/**
	 * La coordonn�e dans l'axe X du point.
	 */
    private double x;
    /**
	 * La coordonn�e dans l'axe Y du point.
	 */
    private double y;
    /**
	 * La coordonn�e dans l'axe Z du point.
	 */
    private double z;
    
    /**
	 * Ce constructeur permet de construire un objet Point � partir des coordonn�es X,Y,Z et un identificateur. 
	 * @param X La coordonn�e X du point.
	 * @param Y La coordonn�e Y du point.
	 * @param Z La coordonn�e Z du point.
	 * @param ID Num�ro identifiant le point.
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
	 * Cette m�thode permet de savoir la coordonn�e X du point.
	 * @return x La coordonn�e X du point.
	 */
	public double getX() {
		return x;
	}
	/**
	 * Cette m�thode permet de savoir la coordonn�e Y du point.
	 * @return y La coordonn�e Y du point.
	 */
	public double getY() {
		return y;
	}
	/**
	 * Cette m�thode permet de savoir la coordonn�e Z du point.
	 * @return z La coordonn�e Z du point.
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
	 * Cette methode permet d'assigner une valeur de la coordonn�e X au Point.
	 * @param x la nouvelle coordonn�e X du point.
	 */
	public void setX(double x) {
		this.x = x;
	}
	/** 
	 * Cette methode permet d'assigner une valeur de la coordonn�e Y au Point.
	 * @param y la nouvelle coordonn�e Y du point.
	 */
	public void setY(double y) {
		this.y = y;
	}
	/** 
	 * Cette methode permet d'assigner une valeur de la coordonn�e Z au Point.
	 * @param z la nouvelle coordonn�e Z du point.
	 */
	public void setZ(double z) {
		this.z = z;
	}
    
	
	/** Cette methode permet de faire une comparaison entre deux points (Point Actuel(Point A) et Point Voisin(Point B),
	 *  en identifiant si le point A est plus �l�v� que le point B.
	 * @param  point Point � comparer avec le point actuel.
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
	 * @param  point Point B � comparer avec le point actuel A.
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
	 * Si la valeur de la pente est negative indique que la pente se trouve en direction de d�croissance
	 * @param  point Point B � comparer avec le point actuel A.
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
	 * @param  point Point B � comparer avec le point actuel A.
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
