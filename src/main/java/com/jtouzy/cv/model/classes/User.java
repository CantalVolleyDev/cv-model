package com.jtouzy.cv.model.classes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jtouzy.dao.db.DBTypeConstants;

@Entity
@Table(name = "usr")
public class User {
	public static final String IDENTIFIER_FIELD = "numusr";
	public static final String MAIL_FIELD = "maiusr";
	public static final int MAIL_FIELD_LENGTH = 100;
	public static final String NAME_FIELD = "nomusr";
	public static final int NAME_FIELD_LENGTH = 50;
	public static final String FIRST_NAME_FIELD = "preusr";
	public static final int FIRST_NAME_FIELD_LENGTH = 50;
	public static final String PASSWORD_FIELD = "pwdusr";
	public static final int PASSWORD_FIELD_LENGTH = 130;
	public static final String BIRTHDATE_FIELD = "dnsusr";
	public static final String PHONE_FIELD = "telusr";
	public static final int PHONE_FIELD_LENGTH = 20;
	public static final String ADMINISTRATOR_FIELD = "admusr";
	
	@Id
	@GeneratedValue
	@Column(name = IDENTIFIER_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	private Integer identifier;
	
	@Column(name = MAIL_FIELD, length = MAIL_FIELD_LENGTH, columnDefinition = DBTypeConstants.VARCHAR)
	@Size(max = MAIL_FIELD_LENGTH, message = "La taille de l'e-mail doit être au maximum de {max}")
	private String mail;
	
	@Column(name = NAME_FIELD, length = NAME_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.VARCHAR)
	@NotNull(message = "Le nom doit être renseigné")
	@Size(max = NAME_FIELD_LENGTH, message = "La taille du nom doit être au maximum de {max}")
	private String name;
	
	@Column(name = FIRST_NAME_FIELD, length = FIRST_NAME_FIELD_LENGTH, nullable = false, columnDefinition = DBTypeConstants.VARCHAR)
	@NotNull(message = "Le prénom doit être renseigné")
	@Size(max = FIRST_NAME_FIELD_LENGTH, message = "La taille du prénom doit être au maximum de {max}")
	private String firstName;
	
	@Column(name = PASSWORD_FIELD, length = PASSWORD_FIELD_LENGTH, columnDefinition = DBTypeConstants.VARCHAR)
	@Size(max = PASSWORD_FIELD_LENGTH, message = "La taille du mot de passe doit être au maximum de {max}")
	private String password;

	@Column(name = BIRTHDATE_FIELD, columnDefinition = DBTypeConstants.DATE)
	private LocalDate birthDate;
	
	@Column(name = PHONE_FIELD, length = PHONE_FIELD_LENGTH, columnDefinition = DBTypeConstants.VARCHAR)
	@Size(max = PHONE_FIELD_LENGTH, message = "La taille du téléphone doit être au maximum de {max}")
	private String phone;
	
	@Column(name = ADMINISTRATOR_FIELD, nullable = false, columnDefinition = DBTypeConstants.BOOLEAN)
	@NotNull(message = "La zone 'Administrateur' doit être renseignée")
	private Boolean administrator;
	
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean isAdministrator() {
		return administrator;
	}
	public void setAdministrator(Boolean administrator) {
		this.administrator = administrator;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [identifier=");
		builder.append(identifier);
		builder.append(", mail=");
		builder.append(mail);
		builder.append(", name=");
		builder.append(name);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", birthDate=");
		builder.append(birthDate);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", administrator=");
		builder.append(administrator);
		builder.append("]");
		return builder.toString();
	}
}
