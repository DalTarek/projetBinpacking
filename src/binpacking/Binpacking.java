package binpacking;

import java.util.ArrayList;

public class Binpacking {

	public int FractionalPacking(ArrayList<Integer> objets, int taille) {
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
}
