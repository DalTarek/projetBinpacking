package binpacking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Binpacking {

	public static int fractionalPacking(ArrayList<Integer> objets, int taille) {
		int nbBoites = 0;
		int hauteur = 0;
		for (int i = 0; i < objets.size(); i++) {
			hauteur += objets.get(i);
		}
		if (hauteur % taille != 0) {
			nbBoites = hauteur / taille + 1;
		} else {
			nbBoites = hauteur / taille;
		}
		return nbBoites;
	}
	
	public static String toString(ArrayList<Integer> objets, int taille) {
		StringBuilder remplissage = new StringBuilder();
		remplissage.append("(");
		remplissage.append(taille);
		remplissage.append(",[");
		for (int i=0; i<objets.size()-1; i++) {
			remplissage.append(objets.get(i));
			remplissage.append(";");
		}
		if(objets.size() > 0){
			remplissage.append(objets.get(objets.size()-1));
		}
		remplissage.append("]");
		remplissage.append(")");
	
		return remplissage.toString();
	}
	
	public static String firstFitPacking(ArrayList<Integer> objets, int taille) {
		StringBuilder sb = new StringBuilder();
		ArrayList<ArrayList<Integer>> boites = new ArrayList<>(objets.size());
		
		/* On cree autant de boites que d'objets */
		for (int i=0; i<objets.size(); i++) {
			boites.add(new ArrayList<Integer>());
		}
		
		/* On ajoute un champ dans la liste pour indiquer la taille restante de la boite */
		for (ArrayList<Integer> al : boites) {
			al.add(taille);
		}
		
		for (int i=0; i<objets.size(); i++) {
			for (int j=0; j<boites.size(); j++) {
				int reste = boites.get(j).get(0);
				if (reste - objets.get(i) >= 0) {
					boites.get(j).add(objets.get(i));
					reste -= objets.get(i);
					/* Modification de la taille restante de la boite */
					boites.get(j).remove(0);
					boites.get(j).add(0, reste);
					break;
				}
			}
		}
		
		/* On supprime toutes les boites non utilisees */
		int tmp = 0;
		for (int i=0; i<boites.size(); i++) {
			if (boites.get(i).get(0) == taille) {
				if(tmp == 0){
					tmp = i;
				}
				boites.remove(i);
			}
		}
		
		sb.append(tmp);
		sb.append("\n");
		for (ArrayList<Integer> al : boites) {
			int reste = al.get(0);
			al.remove(0);
			sb.append(toString(al, reste));
			sb.append("\n");
		}

		return sb.toString();
	}
	
	public static String bestFitPacking(ArrayList<Integer> objets, int taille) {
		StringBuilder sb = new StringBuilder();
		ArrayList<ArrayList<Integer>> boites = new ArrayList<>(objets.size());
		
		/* On cree autant de boites que d'objets */
		for (int i=0; i<objets.size(); i++) {
			boites.add(new ArrayList<Integer>());
		}
		
		/* On ajoute un champ dans la liste pour indiquer la taille restante de la boite */
		for (ArrayList<Integer> al : boites) {
			al.add(taille);
		}
		
		int choix;
		for (int i=0; i<objets.size(); i++) {
			choix = 0;
			for (int j=0; j<boites.size()-1; j++) {
				int reste1 = boites.get(choix).get(0) - objets.get(i);
				int reste2 = boites.get(j+1).get(0) - objets.get(i);
				if ((reste1 < 0) && (reste2 >=0)) {
					choix = j + 1;
				} else {
					if ((reste1 >= 0) && (reste2 >= 0)) {
						if (reste2 < reste1) {
							choix = j + 1;
						}
					}
				}
			}
			int reste = boites.get(choix).get(0);
			boites.get(choix).add(objets.get(i));
			reste -= objets.get(i);
			/* Modification de la taille restante de la boite */
			boites.get(choix).remove(0);
			boites.get(choix).add(0, reste);
		}
		
		/* On supprime toutes les boites non utilisees */
		int tmp = 0;
		for (int i=0; i<boites.size(); i++) {
			if (boites.get(i).get(0) == taille) {
				if(tmp == 0){
					tmp = i;
				}
				boites.remove(i);
			}
		}
		
		sb.append(tmp);
		sb.append("\n");
		for (ArrayList<Integer> al : boites) {
			int reste = al.get(0);
			al.remove(0);
			sb.append(toString(al, reste));
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	
	public static String firstFitDecreasingPacking(ArrayList<Integer> objets, int taille) {
		Collections.sort(objets, Collections.reverseOrder());
		
		return firstFitPacking(objets, taille);
	}
	
	public static String bestFitDecreasingPacking(ArrayList<Integer> objets, int taille) {
		Collections.sort(objets, Collections.reverseOrder());

		return bestFitPacking(objets, taille);
	}
	
	public static int getNbUtilisées(String s){
		Matcher matcher = Pattern.compile("\\d+").matcher(s);
		matcher.find();
		int nb = Integer.valueOf(matcher.group());
		return nb;		
	}
	
	//Test Q16 
	public static int test(){
		int n, hboites, valeur, taille, fp, ff, bf, ffd, bfd;
		ArrayList<Integer> tabfp = new ArrayList<>();
		ArrayList<Integer> tabff = new ArrayList<>();
		ArrayList<Integer> tabbf = new ArrayList<>();
		ArrayList<Integer> tabffd = new ArrayList<>();
		ArrayList<Integer> tabbfd = new ArrayList<>();
		Random r;
		ArrayList<Integer> objets;
		for(int i = 1; i < 11; i++){
			fp = 0; 
			ff = 0;
			bf = 0; 
			ffd = 0; 
			bfd = 0;
			n = i * 100;
			hboites = (int) (1.5 * n);
			taille = (int) (n * 1.5);
			for(int j = 0; j < 20; j++){
				objets = new ArrayList<>();
				for(int o = 0; o < n; o++){
					r = new Random();
					valeur = (int) (0.2 * n + r.nextInt((int)(0.8 * n - 0.2 * n)));
					objets.add(valeur);
				}
				fp = fp + fractionalPacking(objets,taille);
				ff = ff + getNbUtilisées(firstFitPacking(objets,taille));
				bf = bf + getNbUtilisées(bestFitPacking(objets,taille));
				ffd = ffd + getNbUtilisées(firstFitDecreasingPacking(objets, taille));
				bfd = bfd + getNbUtilisées(bestFitDecreasingPacking(objets, taille));
			}
			tabfp.add(fp / 20); 
			tabff.add(ff / 20); 
			tabbf.add(bf / 20); 
			tabffd.add(ffd / 20); 
			tabbfd.add(bfd / 20); 
		}
		return 0;
	}
	
	public static void main(String[] args) {
		/*ArrayList<Integer> objets = new ArrayList<>();
		objets.add(2);
		objets.add(5);
		objets.add(1);
				
		int nbBoites = fractionalPacking(objets, 6);
		System.out.println(nbBoites);
		
		//String s = toString(objets, 6);
		//System.out.println(s);
		
		System.out.println("First Fit Packing : \n");
		String s1 = firstFitDecreasingPacking(objets, 6);
		System.out.println(s1);
		
		System.out.println("Best Fit Packing : \n");
		String s2 = bestFitDecreasingPacking(objets, 6);
		System.out.println(s2);*/
		test();
	}
}
