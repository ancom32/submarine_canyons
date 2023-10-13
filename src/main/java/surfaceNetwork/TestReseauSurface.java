package surfaceNetwork;

import java.util.ArrayList;
import java.util.HashSet;



/** 
 * <b> Classe  TestReseauSurface (static) </b>
 * <p> 
 * Une classe static contient des m�thodes pour tester la construction d'un objet de ReseauSurface.
 *  
 * @author Andr�s Camilo Cort�s Murcia.
 * @since 2015/08/12
 *
 */
public class TestReseauSurface 
{
	public static HashSet<Integer> preuvePuitsVirtuels= new HashSet<Integer>(); 
	
	 /**
     * <b>verifierEuler : </b>
     * <p>
     * Il permet de verifier le crit�re topologique d'Eule-Poincar�.
     * @param rsf L'instance de la classe ReseauSurface.
     */
	public static void verifierEuler(ReseauSurface rsf){
		
		
		System.out.println("********V�rification de la relation d'Euler-Poincar�**********");
		int relationEuler=rsf.getlistPics().size()+rsf.getlistPuits().size()-rsf.getlistCols().size();
		System.out.println("PICS ["+rsf.getlistPics().size()+"] + "+"PUITS ["+rsf.getlistPuits().size()+"] - "+"COLS ["+rsf.getlistCols().size()+"] = "
				+relationEuler);
		System.out.println(preuvePuitsVirtuels);
		
		
	}
	/**
     * <b>nombreLignesCritiques : </b>
     * <p>
     * Il permet de savoir le nombre de lignes critiques du mod�le.
     * @param rsf L'instance de la classe ReseauSurface.
     */
	public static void nombreLignesCritiques(ReseauSurface rsf){
		System.out.println("Quantit� de cr�tes"+rsf.getlistCretes().size());
		System.out.println("Quantit� de talwegs"+rsf.getlistRavins().size());
	}
	
	/**
     * <b>montrerColsPics : </b>
     * <p>
     * Il permet d'identifier les cols qui son associ�s � un pic.
     * @param rsf L'instance de la classe ReseauSurface.
     */
	public static void montrerColsPic(ReseauSurface rsf){
		for (Pic pic : rsf.getlistPics()) {
			double pente=0;
			double sumPente=0;
			int count=0;
			for (Crete crete : pic.getListCretes()) {
				System.out.println(pic.getID()+"ID du col de la Cr�te"+crete.getCol().getID());
				pente=pic.getSommet().calculePente(crete.getCol().getSommet());
				//System.out.println("pentes du col"+pente);
				sumPente=Math.abs(pente)+sumPente;
				count++;
			}
			System.out.println(sumPente/count);
		}
	}
	/**
     * <b>montrerColsPuit : </b>
     * <p>
     * Il permet d'identifier les cols qui son associ�s � un puit.
     * @param rsf L'instance de la classe ReseauSurface.
     */
	public static void montrerColsPuit(ReseauSurface rsf){
		for (Puit puit : rsf.getlistPuits()) {
			double pente=0;
			double sumPente=0;
			int count=0;
			for (Ravin ravin : puit.getListRavins()) {
				//System.out.println(pic.getID()+"ID du col de la Cr�te"+crete.getCol().getID());
				pente=puit.getSommet().calculePente(ravin.getCol().getSommet());
				//System.out.println("pentes du col"+pente);
				sumPente=Math.abs(pente)+sumPente;
				count++;
			}
			System.out.println(sumPente/count);
		}
	}
	/**
     * <b>afficherVertexCretes : </b>
     * <p>
     * Il permet d'afficher les vertex qui composent une ligne de cr�te.
     * @param rsf L'instance de la classe ReseauSurface.
     */
	public static void afficherVertexCretes(ReseauSurface rsf){
		for (Crete crete : rsf.getlistCretes()) {
			System.out.println(crete.getId()+"="+crete.getListIdVertex());
		}
		
	}
	/**
     * <b>afficherVertexRavins : </b>
     * <p>
     * Il permet d'afficher les vertex qui composent une ligne de ravin.
     * @param rsf L'instance de la classe ReseauSurface.
     */
	public static void afficherVertexRavin(ReseauSurface rsf){
		for (Ravin ravin : rsf.getlistRavins()) {
			System.out.println(ravin.getId()+"="+ravin.getListIdVertex());
		}
		
	}	
	/**
     * <b>afficherVertexRavins : </b>
     * <p>
     * Il permet d'afficher les points qui poss�dent une restriction pour �tre supprim�s.
     * @param rsf L'instance de la classe ReseauSurface.
     */
	public static void afficherPointsRestreints(ReseauSurface rsf){
		ArrayList<Double> picTemp=new ArrayList<Double>();
		for (Pic pic : rsf.getlistPics()) {
			if(pic.getEstRestreint()==true){
				picTemp.add(pic.getID());
			}
				
		}
		System.out.println("Liste de Pics Restreints"+picTemp);
	}	
	
	
}
