package com.jtouzy.cv.model.classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.jtouzy.dao.db.DBTypeConstants;

@Entity
@Table(name = MatchPlayer.TABLE)
public class MatchPlayer {
	public static final String TABLE = "pma";
	public static final String MATCH_FIELD = "matpma";
	public static final String TEAM_FIELD = "eqipma";
	public static final String USER_FIELD = "usrpma";
	
	@Id
	@JoinColumn(
		name = MATCH_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = Match.IDENTIFIER_FIELD
	)
	@NotNull(message = "Le match doit être renseigné")
	private Match match;
	
	@Id
	@JoinColumn(
		name = TEAM_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = Team.IDENTIFIER_FIELD
	)
	@NotNull(message = "L'équipe doit être renseignée")
	private Team team;
	
	@Id
	@JoinColumn(
		name = USER_FIELD, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = User.IDENTIFIER_FIELD
	)
	private User player;
	
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public User getPlayer() {
		return player;
	}
	public void setPlayer(User player) {
		this.player = player;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MatchPlayer [match=");
		builder.append(match);
		builder.append(", team=");
		builder.append(team);
		builder.append(", player=");
		builder.append(player);
		builder.append("]");
		return builder.toString();
	}
}
