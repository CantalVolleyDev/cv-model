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
@Table(name = "nws")
public class News {
	public final static String IDENTIFIER_FIELD = "numnws";
	public final static String TITLE_FIELD = "titnws";
	public final static int TITLE_FIELD_LENGTH = 30;
	public final static String CONTENT_FIELD = "cntnws";
	public final static int CONTENT_FIELD_LENGTH = 4000;
	public final static String AUTHOR_FIELD = "autnws";
	public final static String CREATION_DATE_FIELD = "dcrnws";
	public final static String PUBLISH_DATE_FIELD = "dpunws";
	public final static String STATE_FIELD = "stanws";
	
	@Id
	@GeneratedValue
	@Column(name = IDENTIFIER_FIELD, columnDefinition = DBType2.INTEGER)
	private Integer identifier;
	
	@Column(name = TITLE_FIELD, length = TITLE_FIELD_LENGTH, columnDefinition = DBType2.VARCHAR)
	@NotNull(message = "Le titre doit être renseigné")
	@Size(max = TITLE_FIELD_LENGTH, message = "La taille du titre doit être au maximum de {max}")
	private String title;
	
	@Column(name = CONTENT_FIELD, length = CONTENT_FIELD_LENGTH, columnDefinition = DBType2.VARCHAR)
	@NotNull(message = "Le contenu doit être renseigné")
	@Size(max = CONTENT_FIELD_LENGTH, message = "La taille du contenu doit être au maximum de {max}")
	private String content;
	
	@DAOTableRelation(
		column = @Column(name = "autnws", columnDefinition = DBType2.INTEGER),
		relationColumn = User.IDENTIFIER_FIELD
	)
	@NotNull(message = "L'auteur doit être renseigné")
	private User author;
	
	@Column(name = CREATION_DATE_FIELD, columnDefinition = DBType2.DATETIME)
	@NotNull(message = "La date de création doit être renseignée")
	private LocalDateTime creationDate;
	
	@Column(name = PUBLISH_DATE_FIELD, columnDefinition = DBType2.DATETIME)
	private LocalDateTime publishDate;
	
	@Column(name = STATE_FIELD, length = 1, columnDefinition = DBType2.ENUM)
	@NotNull(message = "L'état doit être renseigné")
	private News.State state;
	
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public LocalDateTime getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(LocalDateTime publishDate) {
		this.publishDate = publishDate;
	}
	public News.State getState() {
		return state;
	}
	public void setState(News.State state) {
		this.state = state;
	}

	public enum State {
		C, V
	}
}
