package com.jtouzy.cv.model.classes;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.jtouzy.dao.db.DBTypeConstants;

@Entity
@Table(name = ChampionshipWeeks.TABLE)
public class ChampionshipWeeks {
	public static final String TABLE = "wsem";
	public static final String CHAMPIONSHIP_FIELD = "chpsem";
	public static final String WEEK_NUMBER_FIELD = "numsem";
	public static final String WEEK_DATE_FIELD = "fsdsem";

	@Id
	@JoinColumn(
		name = CHAMPIONSHIP_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = Championship.IDENTIFIER_FIELD
	)
	@NotNull(message = "Le championnat doit être renseigné")
	private Championship championship;
	@Id
	@Column(name = WEEK_DATE_FIELD, nullable = false, columnDefinition = DBTypeConstants.DATETIME)
	@NotNull(message = "Le date de la semaine doit être renseignée")
	private LocalDateTime weekDate;
	
	public Championship getChampionship() {
		return championship;
	}
	public void setChampionship(Championship championship) {
		this.championship = championship;
	}
	public LocalDateTime getWeekDate() {
		return weekDate;
	}
	public void setWeekDate(LocalDateTime weekDate) {
		this.weekDate = weekDate;
	}
}
