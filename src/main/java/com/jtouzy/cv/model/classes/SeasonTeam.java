package com.jtouzy.cv.model.classes;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.jtouzy.dao.annotations.DAOTableRelation;
import com.jtouzy.dao.db.DBType2;

@Table(name = "eqs")
public class SeasonTeam {
	public static final String TEAM_FIELD = "eqieqs";
	public static final String SEASON_FIELD = "saieqs";
	
	@Id
	@DAOTableRelation(
		column = @Column(name = TEAM_FIELD, columnDefinition = DBType2.INTEGER),
		relationColumn = Team.IDENTIFIER_FIELD
	)
	@NotNull(message = "L'équipe doit être renseignée")
	private Team team;
	
	@DAOTableRelation(
		column = @Column(name = SEASON_FIELD, columnDefinition = DBType2.INTEGER),
		relationColumn = Season.IDENTIFIER_FIELD
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
