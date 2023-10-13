package surfaceNetwork;

import java.util.Comparator;

/** 
 * <b> Classe PoidPicComparator </b>
 * <p> 
 * Cette classe permet de faire une comparaison entre les poids assignées aux pics avec le but de ranger les points dans une liste.
 * 
 * @see PointCritique
 *                
 * @author Andrés Camilo Cortés Murcia.
 * @since 2015-08-11
 * 
 */
 class PoidPicComparator implements Comparator<Pic> {
	 /**
	 * Cette methode compare le poid entre deux pics pour les ranger dans une liste.
	 * @return double L'ID du PointCritique. 
	 */
	@Override
	public int compare(Pic pic1, Pic pic2) {
		
		if(pic1.getPoid() < pic2.getPoid()){
			return -1;
		}
		
		else if(pic1.getPoid() > pic2.getPoid()){
			return 1;
		}
		
		else{
			return 0;
		}
		
		//return pic1.getPoid() < pic2.getPoid() ? -1 : pic1.getPoid() == pic2.getPoid()? 0 : 1;
	}
	
}
 
 /** 
  * <b> Classe PoidPicComparator </b>
  * <p> 
  * Cette classe permet de faire une comparaison entre les poids assignées aux puits avec le but de ranger les points dans une liste.
  * 
  * @see PointCritique
  *                
  * @author Andrés Camilo Cortés Murcia.
  * @since 2015-08-11
  * 
  */
 class PoidPuitComparator implements Comparator<Puit> {
	 /**
	* Cette methode compare le poid entre deux puits pour les ranger dans une liste.
	* @return double L'ID du PointCritique. 
	*/
	@Override
	public int compare(Puit puit1, Puit puit2) {
		
		if(puit1.getPoid() < puit2.getPoid()){
			return -1;
		}
		
		else if(puit1.getPoid() > puit2.getPoid()){
			return 1;
		}
		
		else{
			return 0;
		}
		
		//return pic1.getPoid() < pic2.getPoid() ? -1 : pic1.getPoid() == pic2.getPoid()? 0 : 1;
	}
	
}
 
 class ElevationColComparator implements Comparator<Col> {
	 /**
	* Cette methode compare le hauteur entre deux cols pour les ranger dans une liste.
	* @return double L'ID du PointCritique. 
	*/
	@Override
	public int compare(Col col1, Col col2) {
		
		if(col1.getElevation() < col2.getElevation()){
			return -1;
		}
		
		else if(col1.getElevation() > col2.getElevation()){
			return 1;
		}
		
		else{
			return 0;
		}
		
		//return pic1.getPoid() < pic2.getPoid() ? -1 : pic1.getPoid() == pic2.getPoid()? 0 : 1;
	}
	
}
