package com.jtouzy.cv.model.classes;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jtouzy.dao.annotations.DAOTableRelation;
import com.jtouzy.dao.db.DBType2;

@Entity
@Table(name = "cmp")
public class Competition {
	public static final String IDENTIFIER_FIELD = "numcmp";
	public static final String SEASON_FIELD = "saicmp";
	public static final String CVT_FIELD = "cvtcmp";
	public static final String LABEL_FIELD = "libcmp";
	public static final int LABEL_FIELD_LENGTH = 40;
	public static final String DESCRIPTION_FIELD = "dsccmp";
	public static final int DESCRIPTION_FIELD_LENGTH = 4000;
	public static final String ORDER_FIELD = "ordcmp";
	public static final String CVT_CITY_FIELD = "liecmp";
	public static final int CVT_CITY_FIELD_LENGTH = 80;
	public static final String DATE_FIELD = "datcmp";
	public static final String LATITUDE_FIELD = "latcmp";
	public static final int LATITUDE_FIELD_LENGTH = 50;
	public static final String LONGITUDE_FIELD = "lngcmp";
	public static final int LONGITUDE_FIELD_LENGTH = 50;
	public static final String STATE_FIELD = "etacmp";
	public static final String GROUP_FIELD = "rgpcmp";
	
	@Id
	@GeneratedValue
	@Column(name = IDENTIFIER_FIELD, columnDefinition = DBType2.INTEGER)
	private Integer identifier;
	
	@DAOTableRelation(
		column = @Column(name = SEASON_FIELD, columnDefinition = DBType2.INTEGER), 
		relationColumn = Season.IDENTIFIER_FIELD
	)	
	@NotNull(message = "La saison doit être renseignée")
	private Season season;
	
	@Column(name = CVT_FIELD, columnDefinition = DBType2.BOOLEAN)
	@NotNull(message = "La zone 'CVT' doit être renseignée")
	private Boolean cvt;
	
	@Column(name = LABEL_FIELD, length = LABEL_FIELD_LENGTH, columnDefinition = DBType2.VARCHAR)
	@NotNull(message = "Le libellé doit être renseigné")
	@Size(max = LABEL_FIELD_LENGTH, message = "La taille du libellé doit être au maximum de {max}")
	private String label;
	
	@Column(name = DESCRIPTION_FIELD, length = DESCRIPTION_FIELD_LENGTH, columnDefinition = DBType2.VARCHAR)
	@Size(max = DESCRIPTION_FIELD_LENGTH, message = "La taille de la description doit être au maximum de {max}")
	private String description;
	
	@Column(name = ORDER_FIELD, columnDefinition = DBType2.INTEGER)
	@NotNull(message = "L'ordre doit être renseigné")
	private Integer order;
	
	@Column(name = CVT_CITY_FIELD, length = CVT_CITY_FIELD_LENGTH, columnDefinition = DBType2.VARCHAR)
	@Size(max = CVT_CITY_FIELD_LENGTH, message = "La taille du lieu doit être au maximum de {max}")
	private String cvtCity;
	
	@Column(name = DATE_FIELD, columnDefinition = DBType2.DATETIME)
	private LocalDateTime cvtDate;
	
	@Column(name = LATITUDE_FIELD, length = LATITUDE_FIELD_LENGTH, columnDefinition = DBType2.VARCHAR)
	@Size(max = LATITUDE_FIELD_LENGTH, message = "La taille de la latitude doit être au maximum de {max}")
	private String cvtLatitude;
	
	@Column(name = LONGITUDE_FIELD, length = LONGITUDE_FIELD_LENGTH, columnDefinition = DBType2.VARCHAR)
	@Size(max = LONGITUDE_FIELD_LENGTH, message = "La taille de la longitude doit être au maximum de {max}")
	private String cvtLongitude;
	
	@Column(name = STATE_FIELD, length = 1, columnDefinition = DBType2.ENUM)
	private Competition.State state;
	
	@Column(name = GROUP_FIELD, columnDefinition = DBType2.INTEGER)
	private Integer groupNumber;
	
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public Boolean isCvt() {
		return cvt;
	}
	public void setCvt(Boolean cvt) {
		this.cvt = cvt;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getCvtCity() {
		return cvtCity;
	}
	public void setCvtCity(String cvtCity) {
		this.cvtCity = cvtCity;
	}
	public LocalDateTime getCvtDate() {
		return cvtDate;
	}
	public void setCvtDate(LocalDateTime cvtDate) {
		this.cvtDate = cvtDate;
	}
	public String getCvtLatitude() {
		return cvtLatitude;
	}
	public void setCvtLatitude(String cvtLatitude) {
		this.cvtLatitude = cvtLatitude;
	}
	public String getCvtLongitude() {
		return cvtLongitude;
	}
	public void setCvtLongitude(String cvtLongitude) {
		this.cvtLongitude = cvtLongitude;
	}
	public Competition.State getState() {
		return state;
	}
	public void setState(Competition.State state) {
		this.state = state;
	}
	public Integer getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(Integer groupNumber) {
		this.groupNumber = groupNumber;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Competition [identifier=");
		builder.append(identifier);
		builder.append(", season=");
		builder.append(season);
		builder.append(", cvt=");
		builder.append(cvt);
		builder.append(", label=");
		builder.append(label);
		builder.append(", description=");
		builder.append(description);
		builder.append(", order=");
		builder.append(order);
		builder.append(", cvtCity=");
		builder.append(cvtCity);
		builder.append(", cvtDate=");
		builder.append(cvtDate);
		builder.append(", cvtLatitude=");
		builder.append(cvtLatitude);
		builder.append(", cvtLongitude=");
		builder.append(cvtLongitude);
		builder.append(", state=");
		builder.append(state);
		builder.append(", groupNumber=");
		builder.append(groupNumber);
		builder.append("]");
		return builder.toString();
	}
	
	public enum State {
		C, V
	}
}
