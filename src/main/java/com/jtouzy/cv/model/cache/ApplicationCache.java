package com.jtouzy.cv.model.cache;

import com.jtouzy.cv.model.classes.Season;

public class ApplicationCache {
	/**
	 * Saison courante en cache<br>
	 * Cette saison courante est modifiée uniquement lorsqu'on change, donc une fois par an pendant l'été<br>
	 * Lors de l'utilisation de la méthode {@link #getCurrentSeason()}, elle sera recherchée uniquement la première fois<br>
	 */
	private static Season currentSeason;
	
	public static synchronized void setCurrentSeason(Season current) {
		currentSeason = current;
	}
	public static Season getCurrentSeason() {
		return currentSeason;
	}
}
