package com.jtouzy.cv.model.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jtouzy.dao.db.DBTypeConstants;

@Entity
@Table(name = Championship.TABLE)
public class Championship {
	public static final String TABLE = "chp";
	public static final String IDENTIFIER_FIELD = "numchp";
	public static final String COMPETITION_FIELD = "cmpchp";
	public static final String LABEL_FIELD = "libchp";
	public static final int LABEL_FIELD_LENGTH = 40;
	public static final String GENDER_FIELD = "genchp";
	public static final int GENDER_FIELD_LENGTH = 1;
	public static final String ORDER_FIELD = "ordchp";
	public static final String TYPE_FIELD = "typchp";
	public static final int TYPE_FIELD_LENGTH = 3;
	public static final String STATE_FIELD = "stachp";
	public static final int STATE_FIELD_LENGTH = 1;
	
	@Id
	@GeneratedValue
	@Column(name = IDENTIFIER_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	private Integer identifier;
	
	@JoinColumn(
		name = COMPETITION_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER, 
		referencedColumnName = Competition.IDENTIFIER_FIELD
	)
	@NotNull(message = "La compétition doit être renseignée")
	private Competition competition;
	
	@Column(name = LABEL_FIELD, length = LABEL_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.VARCHAR)
	@NotNull(message = "Le libellé doit être renseigné")
	@Size(max = LABEL_FIELD_LENGTH, message = "La taille du libellé doit être au maximum de {max}")
	private String label;
	
	@Column(name = GENDER_FIELD, length = GENDER_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.ENUM)
	@NotNull(message = "Le genre doit être renseigné")
	private Championship.Gender gender;
	
	@Column(name = ORDER_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer order;
	
	@Column(name = TYPE_FIELD, length = TYPE_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.ENUM)
	@NotNull(message = "Le type doit être renseigné")
	private Championship.Type type;
	
	@Column(name = STATE_FIELD, length = STATE_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.ENUM)
	@NotNull(message = "L'état doit être renseigné")
	private Championship.State state;
	
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	public Competition getCompetition() {
		return competition;
	}
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Championship.Gender getGender() {
		return gender;
	}
	public void setGender(Championship.Gender gender) {
		this.gender = gender;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Championship.Type getType() {
		return type;
	}
	public void setType(Championship.Type type) {
		this.type = type;
	}
	public Championship.State getState() {
		return state;
	}
	public void setState(Championship.State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Championship [identifier=");
		builder.append(identifier);
		builder.append(", competition=");
		builder.append(competition);
		builder.append(", label=");
		builder.append(label);
		builder.append(", gender=");
		builder.append(gender);
		builder.append(", order=");
		builder.append(order);
		builder.append(", type=");
		builder.append(type);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}

	public enum Gender {
		M, X, F
	}
	public enum Type {
		CHP, CUP
	}
	public enum State {
		C, V 
	}
}
