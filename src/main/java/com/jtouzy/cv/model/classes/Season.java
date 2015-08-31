package com.jtouzy.cv.model.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jtouzy.dao.db.DBTypeConstants;

@Entity
@Table(name = "sai")
public class Season {
	public static final String IDENTIFIER_FIELD = "numsai";
	public static final String START_YEAR_FIELD = "prdsai";
	public static final int START_YEAR_FIELD_LENGTH = 4;
	public static final String END_YEAR_FIELD = "prfsai";
	public static final int END_YEAR_FIELD_LENGTH = 4;
	public static final String CURRENT_FIELD = "encsai";
	public static final String REGISTRATION_OPEN_FIELD = "iscsai"; 
	
	@Id
	@GeneratedValue
	@Column(name = Season.IDENTIFIER_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	private Integer identifier;
	
	@Column(name = Season.START_YEAR_FIELD, length = START_YEAR_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.VARCHAR)
	@NotNull(message = "L'année de commencement doit être renseignée")
	@Size(max = START_YEAR_FIELD_LENGTH, message = "La taille de l'année de commencement doit être au maximum de {max}")
	private String startYear;
	
	@Column(name = Season.END_YEAR_FIELD, length = END_YEAR_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.VARCHAR)
	@NotNull(message = "L'année de fin doit être renseignée")
	@Size(max = END_YEAR_FIELD_LENGTH, message = "La taille de l'année de fin doit être au maximum de {max}")
	private String endYear;
	
	@Column(name = Season.CURRENT_FIELD, nullable = false, columnDefinition = DBTypeConstants.BOOLEAN)
	@NotNull(message = "La zone 'Saison courante' doit être renseignée")
	private Boolean current;
	
	@Column(name = Season.REGISTRATION_OPEN_FIELD, columnDefinition = DBTypeConstants.BOOLEAN)
	private Boolean registrationOpen;

	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	
	public Boolean isCurrent() {
		return current;
	}

	public void setCurrent(Boolean current) {
		this.current = current;
	}
	
	public Boolean isRegistrationOpen() {
		return registrationOpen;
	}

	public void setRegistrationOpen(Boolean registrationOpen) {
		this.registrationOpen = registrationOpen;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Season [identifier=");
		builder.append(identifier);
		builder.append(", startYear=");
		builder.append(startYear);
		builder.append(", endYear=");
		builder.append(endYear);
		builder.append(", current=");
		builder.append(current);
		builder.append(", registrationOpen=");
		builder.append(registrationOpen);
		builder.append("]");
		return builder.toString();
	}
}
