package com.jtouzy.cv.model.classes;

import java.time.LocalDateTime;

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
@Table(name = Comment.TABLE)
public class Comment {
	public static final String TABLE = "cmt";
	public static final String IDENTIFIER_FIELD = "numcmt";
	public static final String ENTITY_FIELD = "entcmt";
	public static final int ENTITY_FIELD_LENGTH = 3;
	public static final String ENTITY_VALUE_FIELD = "valcmt";
	public static final String USER_FIELD = "usrcmt";
	public static final String TEAM_FIELD = "eqicmt";
	public static final String DATE_FIELD = "datcmt";
	public static final String TEXT_FIELD = "txtcmt";
	public static final int TEXT_FIELD_LENGTH = 4000;
	
	@Id
	@GeneratedValue
	@Column(name = IDENTIFIER_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	private Integer identifier;
	
	@Column(name = ENTITY_FIELD, length = ENTITY_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.ENUM)
	@NotNull(message = "L'entité doit être renseignée")
	private Comment.Entity entity;
	
	@Column(name = ENTITY_VALUE_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "La valeur de l'entité doit être renseignée")
	private Integer entityValue;
	
	@JoinColumn(
		name = USER_FIELD, columnDefinition = DBTypeConstants.INTEGER, nullable = false,
		referencedColumnName = User.IDENTIFIER_FIELD
	)
	@NotNull(message = "L'utilisateur doit être renseigné")
	private User user;

	@JoinColumn(
		name = TEAM_FIELD, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = Team.IDENTIFIER_FIELD
	)
	private Team team;
	
	@Column(name = DATE_FIELD, nullable = false, columnDefinition = DBTypeConstants.DATETIME)
	@NotNull(message = "La date doit être renseignée")
	private LocalDateTime date;
	
	@Column(name = TEXT_FIELD, length = TEXT_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.VARCHAR)
	@NotNull(message = "Le commentaire doit être renseigné")
	@Size(max = TEXT_FIELD_LENGTH, message = "La taille du commentaire doit être au maximum de {max}")
	private String content;
	
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	public Comment.Entity getEntity() {
		return entity;
	}
	public void setEntity(Comment.Entity entity) {
		this.entity = entity;
	}
	public Integer getEntityValue() {
		return entityValue;
	}
	public void setEntityValue(Integer entityValue) {
		this.entityValue = entityValue;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comment [identifier=");
		builder.append(identifier);
		builder.append(", entity=");
		builder.append(entity);
		builder.append(", entityValue=");
		builder.append(entityValue);
		builder.append(", user=");
		builder.append(user);
		builder.append(", date=");
		builder.append(date);
		builder.append(", content=");
		builder.append(content);
		builder.append("]");
		return builder.toString();
	}

	public enum Entity {
		NWS, MAT
	}
}
