package com.jtouzy.cv.model.classes;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.jtouzy.dao.db.DBTypeConstants;

@Table(name = SeasonTeam.TABLE)
public class SeasonTeam {
	public static final String TABLE = "eqs";
	public static final String TEAM_FIELD = "eqieqs";
	public static final String SEASON_FIELD = "saieqs";
	
	@Id
	@JoinColumn(
		name = TEAM_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = Team.IDENTIFIER_FIELD
	)
	@NotNull(message = "L'équipe doit être renseignée")
	private Team team;
	
	@JoinColumn(
		name = SEASON_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = Season.IDENTIFIER_FIELD
	)
	@NotNull(message = "La saison doit être renseignée")
	private Season season;
	
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeasonTeam [team=");
		builder.append(team);
		builder.append(", season=");
		builder.append(season);
		builder.append("]");
		return builder.toString();
	}
}
