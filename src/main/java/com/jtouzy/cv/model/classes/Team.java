package com.jtouzy.cv.model.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jtouzy.dao.db.DBTypeConstants;

@Entity
@Table(name = Team.TABLE)
public class Team {
	public static final String TABLE = "eqi";
	public static final String IDENTIFIER_FIELD = "numeqi";
	public static final String INFO_FIELD = "infeqi";
	public static final int INFO_FIELD_LENGTH = 40;
	
	@Id
	@GeneratedValue
	@Column(name = IDENTIFIER_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	private Integer identifier;
	
	@Column(name = INFO_FIELD, length = INFO_FIELD_LENGTH, columnDefinition = DBTypeConstants.VARCHAR)
	private String information;
	
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
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
		builder.append("Team [identifier=");
		builder.append(identifier);
		builder.append(", information=");
		builder.append(information);
		builder.append("]");
		return builder.toString();
	}
}
