package com.jtouzy.cv.model.classes;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jtouzy.dao.db.DBTypeConstants;

@Entity
@Table(name = SeasonTeam.TABLE)
public class SeasonTeam {
	public static final String TABLE = "eqs";
	public static final String TEAM_FIELD = "eqieqs";
	public static final String SEASON_FIELD = "saieqs";
	public static final String GYM_FIELD = "gymeqs";
	public static final String STATE_FIELD = "etaeqs";
	public static final int STATE_FIELD_LENGTH = 1;
	public static final String DATE_FIELD = "dateqs";
	public static final String INFO_FIELD = "infeqs";
	public static final int INFO_FIELD_LENGTH = 40;
	
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
	
	@JoinColumn(
		name = GYM_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = Gym.IDENTIFIER_FIELD
	)
	@NotNull(message = "Le gymnase doit être renseigné")
	private Gym gym;
	
	@Column(name = STATE_FIELD, nullable = false, length = STATE_FIELD_LENGTH, columnDefinition = DBTypeConstants.ENUM)
	@NotNull(message = "L'état doit être renseigné")
	private SeasonTeam.State state;
	
	@Column(name = DATE_FIELD, nullable = false, columnDefinition = DBTypeConstants.DATETIME)
	@NotNull(message = "La date doit être renseignée")
	private LocalDateTime date;
	
	@Column(name = INFO_FIELD, length = INFO_FIELD_LENGTH, columnDefinition = DBTypeConstants.VARCHAR)
	@Size(max = INFO_FIELD_LENGTH, message = "La taille de la zone d'informations doit être au maximum de {max}")
	private String information;
	
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
	public Gym getGym() {
		return gym;
	}
	public void setGym(Gym gym) {
		this.gym = gym;
	}
	public SeasonTeam.State getState() {
		return state;
	}
	public void setState(SeasonTeam.State state) {
		this.state = state;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeasonTeam [team=");
		builder.append(team);
		builder.append(", season=");
		builder.append(season);
		builder.append(", gym=");
		builder.append(gym);
		builder.append(", state=");
		builder.append(state);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}
	
	public enum State {
		I, A
	}
}
