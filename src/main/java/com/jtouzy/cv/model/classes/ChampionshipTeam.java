package com.jtouzy.cv.model.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.jtouzy.dao.db.DBTypeConstants;

@Entity
@Table(name = ChampionshipTeam.TABLE)
public class ChampionshipTeam {
	public static final String TABLE = "ech";
	public static final String SEASON_TEAM_FIELD = "eqiech";
	public static final String CHAMPIONSHIP_FIELD = "chpech";
	public static final String POINTS_FIELD = "ptsech";
	public static final String BONUS_FIELD = "bnsech";
	public static final String PLAY_FIELD = "jouech";
	public static final String WIN_FIELD = "vicech";
	public static final String LOOSE_FIELD = "prdech";
	public static final String LOOSE3BY2_FIELD = "p32ech";
	public static final String FORFEIT_FIELD = "forech";
	public static final String SETSFOR_FIELD = "stmech";
	public static final String SETSAGAINST_FIELD = "steech";
	public static final String POINTSFOR_FIELD = "ptmech";
	public static final String POINTSAGAINST_FIELD = "pteech";
	
	@Id
	@JoinColumn(
		name = CHAMPIONSHIP_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = Championship.IDENTIFIER_FIELD
	)
	@NotNull(message = "Le championnat doit être renseigné")
	private Championship championship;
	@Id
	@JoinColumn(
		name = SEASON_TEAM_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = SeasonTeam.IDENTIFIER_FIELD
	)
	@NotNull(message = "L'équipe doit être renseignée")
	private SeasonTeam team;
	@Column(name = POINTS_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "Le nombre de points doit être renseigné")
	private Integer points;
	@Column(name = BONUS_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "Le nombre de bonus doit être renseigné")
	private Integer bonus;
	@Column(name = PLAY_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "Le nombre de matchs joués doit être renseigné")
	private Integer play;
	@Column(name = WIN_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "Le nombre de victoires doit être renseigné")
	private Integer win;
	@Column(name = LOOSE_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "Le nombre de défaites doit être renseigné")
	private Integer loose;
	@Column(name = LOOSE3BY2_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "Le nombre de défaites 3 à 2 doit être renseigné")
	private Integer loose3By2;
	@Column(name = FORFEIT_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "Le nombre de forfaits doit être renseigné")
	private Integer forfeit;
	@Column(name = SETSFOR_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "Le nombre de sets marqués doit être renseigné")
	private Integer setsFor;
	@Column(name = SETSAGAINST_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "Le nombre de sets encaissés doit être renseigné")
	private Integer setsAgainst;
	@Column(name = POINTSFOR_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "Le nombre de points marqués doit être renseigné")
	private Integer pointsFor;
	@Column(name = POINTSAGAINST_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "Le nombre de points encaissés doit être renseigné")
	private Integer pointsAgainst;
	
	public Championship getChampionship() {
		return championship;
	}
	public void setChampionship(Championship championship) {
		this.championship = championship;
	}
	public SeasonTeam getTeam() {
		return team;
	}
	public void setTeam(SeasonTeam team) {
		this.team = team;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Integer getBonus() {
		return bonus;
	}
	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
	public Integer getPlay() {
		return play;
	}
	public void setPlay(Integer play) {
		this.play = play;
	}
	public Integer getWin() {
		return win;
	}
	public void setWin(Integer win) {
		this.win = win;
	}
	public Integer getLoose() {
		return loose;
	}
	public void setLoose(Integer loose) {
		this.loose = loose;
	}
	public Integer getLoose3By2() {
		return loose3By2;
	}
	public void setLoose3By2(Integer loose3By2) {
		this.loose3By2 = loose3By2;
	}
	public Integer getForfeit() {
		return forfeit;
	}
	public void setForfeit(Integer forfeit) {
		this.forfeit = forfeit;
	}
	public Integer getSetsFor() {
		return setsFor;
	}
	public void setSetsFor(Integer setsFor) {
		this.setsFor = setsFor;
	}
	public Integer getSetsAgainst() {
		return setsAgainst;
	}
	public void setSetsAgainst(Integer setsAgainst) {
		this.setsAgainst = setsAgainst;
	}
	public Integer getPointsFor() {
		return pointsFor;
	}
	public void setPointsFor(Integer pointsFor) {
		this.pointsFor = pointsFor;
	}
	public Integer getPointsAgainst() {
		return pointsAgainst;
	}
	public void setPointsAgainst(Integer pointsAgainst) {
		this.pointsAgainst = pointsAgainst;
	}
}
