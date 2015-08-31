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
@Table(name = "eqi")
public class Team {
	public static final String IDENTIFIER_FIELD = "numeqi";
	public static final String LABEL_FIELD = "nomeqi";
	public static final int LABEL_FIELD_LENGTH = 40;
	
	@Id
	@GeneratedValue
	@Column(name = IDENTIFIER_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	private Integer identifier;
	
	@Column(name = LABEL_FIELD, length = LABEL_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.VARCHAR)
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
		builder.append("Team [identifier=");
		builder.append(identifier);
		builder.append(", label=");
		builder.append(label);
		builder.append("]");
		return builder.toString();
	}
}
