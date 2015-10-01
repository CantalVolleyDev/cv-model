package com.jtouzy.cv.model.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.jtouzy.dao.db.DBTypeConstants;

@Entity
@Table(name = "esj")
public class SeasonTeamPlayer {
	public static final String TABLE = "esj";
	public static final String PLAYER_FIELD = "jouesj";
	public static final String SEASON_TEAM_FIELD = "eqiesj";
	public static final String MANAGER_FIELD = "resesj";
	
	@Id
	@JoinColumn(
		name = PLAYER_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = User.IDENTIFIER_FIELD
	)
	private User player;
	
	@Id
	@JoinColumn(
		name = SEASON_TEAM_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = SeasonTeam.IDENTIFIER_FIELD
	)
	@NotNull(message = "L'équipe doit être renseignée")
	private SeasonTeam team;
	
	@Column(name = MANAGER_FIELD, nullable = false, columnDefinition = DBTypeConstants.BOOLEAN)
	@NotNull(message = "La zone responsable doit être renseignée")
	private Boolean manager;

	public User getPlayer() {
		return player;
	}
	public void setPlayer(User player) {
		this.player = player;
	}
	public SeasonTeam getTeam() {
		return team;
	}
	public void setTeam(SeasonTeam team) {
		this.team = team;
	}
	public Boolean getManager() {
		return manager;
	}
	public void setManager(Boolean manager) {
		this.manager = manager;
	}
}
