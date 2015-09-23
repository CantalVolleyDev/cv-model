package com.jtouzy.cv.model.validators;

import com.jtouzy.cv.model.classes.Match;
import com.jtouzy.dao.errors.validation.DataValidationException;
import com.jtouzy.dao.validation.AbstractDataValidator;

public class MatchValidator extends AbstractDataValidator<Match> {	
	@Override
	protected void validateInsertUpdate(Match object) 
	throws DataValidationException {
		super.validateInsertUpdate(object);
		validateScore(object);
	}
	
	protected void validateScore(Match object)
	throws DataValidationException {
		// Aucun contrôle de score si le match n'est pas validé,
		// ou refusé, ou bien saisi (création ou forfait)
		if (object.getState() != Match.State.V && 
			object.getState() != Match.State.R &&
			object.getState() != Match.State.S) {
			return;
		}
		
		Integer sc1 = object.getSc1();
		if (sc1 == null)
			throw new DataValidationException("Le score de la première équipe doit être renseigné");
		Integer sc2 = object.getSc2();
		if (sc2 == null)
			throw new DataValidationException("Le score de la seconde équipe doit être renseigné");
		
		if (sc1 > 3 || sc2 > 3) {
			throw new DataValidationException("Aucun des deux scores ne doit être supérieur à 3");
		} else if (sc1 < 0 || sc2 < 0) {
			throw new DataValidationException("Aucun des deux scores ne doit être inférieur à 0");
		} else if (sc1 != 3 && sc2 != 3) {
			throw new DataValidationException("Un des deux scores doit être égal à 3");
		} else if (sc1 == 3 && sc2 == 3) {
			throw new DataValidationException("Les deux scores ne peuvent être égal à 3");
		}
		
		Integer scoreCumulated = sc1 + sc2;
		Integer team1Cumulated = 0, team2Cumulated = 0;
		if (scoreCumulated >= 3) {
			validateSet(object, 1);
			team1Cumulated = object.getS11() > object.getS12() ? 1 : 0;
			team2Cumulated = object.getS12() > object.getS11() ? 1 : 0;
			validateSet(object, 2);
			team1Cumulated += (object.getS21() > object.getS22() ? 1 : 0);
			team2Cumulated += (object.getS22() > object.getS21() ? 1 : 0);
			validateSet(object, 3);
			team1Cumulated += (object.getS31() > object.getS32() ? 1 : 0);
			team2Cumulated += (object.getS32() > object.getS31() ? 1 : 0);
		}
		if (scoreCumulated >= 4) {
			validateSet(object, 4);
			team1Cumulated += (object.getS41() > object.getS42() ? 1 : 0);
			team2Cumulated += (object.getS42() > object.getS41() ? 1 : 0);
		}
		if (scoreCumulated == 5) {
			validateSet(object, 5);
			team1Cumulated += (object.getS51() > object.getS52() ? 1 : 0);
			team2Cumulated += (object.getS52() > object.getS51() ? 1 : 0);
		}
		
		if (sc1 != team1Cumulated || sc2 != team2Cumulated) {
			throw new DataValidationException("Incohérence entre les sets et le score général");
		}
	}
	
	protected void validateSet(Match object, Integer setIndex)
	throws DataValidationException {
		Set set = getSet(object, setIndex);
		Integer maxScore = ((setIndex == 5) ? 15 : 25);
		if (set.firstValue == null || set.secondValue == null)
			throw new DataValidationException("Le score du set " + setIndex + " doit être renseigné");
		if (set.firstValue < 0 || set.secondValue < 0)
			throw new DataValidationException("Les scores ne peuvent pas être négatifs!");
		if ((set.firstValue < maxScore && set.secondValue < maxScore || set.firstValue == maxScore && set.secondValue == maxScore))
			throw new DataValidationException("Un seul des deux score doit être d'au moins " + maxScore + " points pour le set " + setIndex);
		if ((set.firstValue > maxScore && set.firstValue > set.secondValue && set.secondValue != set.firstValue-2) || (set.secondValue > maxScore && set.secondValue > set.firstValue && set.firstValue != set.secondValue-2))
			throw new DataValidationException("Le score gagnant est supérieur à " + maxScore + " : Il doit y avoir 2 points d'écart pour le set " + setIndex);
		if ((set.firstValue == maxScore && set.firstValue > set.secondValue && set.secondValue == maxScore-1) || (set.secondValue == maxScore && set.secondValue > set.firstValue && set.firstValue == maxScore-1))
			throw new DataValidationException("Score " + maxScore + "-" + (maxScore-1) + " impossible pour le set " + setIndex);
	}
	
	protected Set getSet(Match object, Integer setIndex) {
		switch(setIndex) {
			case 1:
				return new Set(object.getS11(), object.getS12());
			case 2:
				return new Set(object.getS21(), object.getS22());
			case 3:
				return new Set(object.getS31(), object.getS32());
			case 4:
				return new Set(object.getS41(), object.getS42());
			case 5:
				return new Set(object.getS51(), object.getS52());
			default:
				return null;
		}
		
	}
	
	private class Set {
		public Integer firstValue;
		public Integer secondValue;
		public Set(Integer firstValue, Integer secondValue) {
			this.firstValue = firstValue;
			this.secondValue = secondValue;
		}
	}
}
