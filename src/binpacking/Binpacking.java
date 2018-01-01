package binpacking;

import java.util.ArrayList;

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
		remplissage.append(objets.get(objets.size()-1));
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
		for (int i=0; i<boites.size(); i++) {
			if (boites.get(i).get(0) == taille) {
				boites.remove(i);
			}
		}
		
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
		for (int i=0; i<boites.size(); i++) {
			if (boites.get(i).get(0) == taille) {
				boites.remove(i);
			}
		}
		
		for (ArrayList<Integer> al : boites) {
			int reste = al.get(0);
			al.remove(0);
			sb.append(toString(al, reste));
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> objets = new ArrayList<>();
		objets.add(2);
		objets.add(5);
		objets.add(1);
		
		/*int nbBoites = fractionalPacking(objets, 6);
		System.out.println(nbBoites);*/
		
		//String s = toString(objets, 6);
		//System.out.println(s);
		
		System.out.println("First Fit Packing : \n");
		String s1 = firstFitPacking(objets, 6);
		System.out.println(s1);
		
		System.out.println("Best Fit Packing : \n");
		String s2 = bestFitPacking(objets, 6);
		System.out.println(s2);
	}
}
