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
@Table(name = News.TABLE)
public class News {
	public static final String TABLE = "nws";
	public final static String IDENTIFIER_FIELD = "numnws";
	public final static String TITLE_FIELD = "titnws";
	public final static int TITLE_FIELD_LENGTH = 30;
	public final static String CONTENT_FIELD = "cntnws";
	public final static int CONTENT_FIELD_LENGTH = 4000;
	public final static String AUTHOR_FIELD = "autnws";
	public final static String CREATION_DATE_FIELD = "dcrnws";
	public final static String PUBLISH_DATE_FIELD = "dpunws";
	public final static String STATE_FIELD = "stanws";
	public final static String CATEGORY_FIELD = "catnws";
	public final static String IMAGE_FIELD = "imgnws";
	public final static String IMAGE_VERSION_FIELD = "imvnws";
	
	@Id
	@GeneratedValue
	@Column(name = IDENTIFIER_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	private Integer identifier;
	
	@Column(name = TITLE_FIELD, length = TITLE_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.VARCHAR)
	@NotNull(message = "Le titre doit être renseigné")
	@Size(max = TITLE_FIELD_LENGTH, message = "La taille du titre doit être au maximum de {max}")
	private String title;
	
	@Column(name = CONTENT_FIELD, length = CONTENT_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.VARCHAR)
	@NotNull(message = "Le contenu doit être renseigné")
	@Size(max = CONTENT_FIELD_LENGTH, message = "La taille du contenu doit être au maximum de {max}")
	private String content;
	
	@JoinColumn(
		name = AUTHOR_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = User.IDENTIFIER_FIELD
	)
	@NotNull(message = "L'auteur doit être renseigné")
	private User author;
	
	@Column(name = CREATION_DATE_FIELD, nullable = false, columnDefinition = DBTypeConstants.DATETIME)
	@NotNull(message = "La date de création doit être renseignée")
	private LocalDateTime creationDate;
	
	@Column(name = PUBLISH_DATE_FIELD, columnDefinition = DBTypeConstants.DATETIME)
	private LocalDateTime publishDate;
	
	@Column(name = STATE_FIELD, length = 1, nullable = false, columnDefinition = DBTypeConstants.ENUM)
	@NotNull(message = "L'état doit être renseigné")
	private News.State state;
	
	@Column(name = CATEGORY_FIELD, length = 40, nullable = false, columnDefinition = DBTypeConstants.VARCHAR)
	@NotNull(message = "La catégorie doit être renseignée")
	private String category;
	
	@Column(name = IMAGE_FIELD, length = 5, nullable = true, columnDefinition = DBTypeConstants.VARCHAR)
	private String image;
	
	@Column(name = IMAGE_VERSION_FIELD, length = 15, nullable = true, columnDefinition = DBTypeConstants.VARCHAR)
	private String imageVersion;
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImageVersion() {
		return imageVersion;
	}
	public void setImageVersion(String imageVersion) {
		this.imageVersion = imageVersion;
	}

	public enum State {
		C, V
	}
}
