package com.jtouzy.cv.model.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jtouzy.dao.db.DBType2;

@Entity
@Table(name = "gym")
public class Gym {
	public static final String IDENTIFIER_FIELD = "idegym";
	public static final String LABEL_FIELD = "libgym";
	public static final int LABEL_FIELD_LENGTH = 40;
	
	@Id
	@GeneratedValue
	@Column(name = IDENTIFIER_FIELD, columnDefinition = DBType2.INTEGER)
	private Integer identifier;
	
	@Column(name = LABEL_FIELD, length = LABEL_FIELD_LENGTH, columnDefinition = DBType2.VARCHAR)
	@NotNull(message = "Le libellé doit être renseigné")
	@Size(max = LABEL_FIELD_LENGTH, message = "La taille du libellé doit être au maximum de {max}")
	private String label;
	
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Gym [identifier=");
		builder.append(identifier);
		builder.append(", label=");
		builder.append(label);
		builder.append("]");
		return builder.toString();
	}
}
