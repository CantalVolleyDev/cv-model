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
@Table(name = Match.TABLE)
public class Match {
	public static final String TABLE = "mat";
	public static final String IDENTIFIER_FIELD = "nummat";
	public static final String CHAMPIONSHIP_FIELD = "chpmat";
	public static final String STEP_FIELD = "etpmat";
	public static final String ORDER_FIELD = "ordmat";
	public static final String FIRST_TEAM_FIELD = "eq1mat";
	public static final String SECOND_TEAM_FIELD = "eq2mat";
	public static final String DATE_FIELD = "datmat";
	public static final String STATE_FIELD = "stamat";
	public static final String SCORE_SETTER_FIELD = "estmat";
	public static final String FORFEIT_FIELD = "format";
	public static final String FIRST_SCORE_FIELD = "sc1mat";
	public static final String SECOND_SCORE_FIELD = "sc2mat";
	public static final String S11_FIELD = "s11mat";
	public static final String S12_FIELD = "s12mat";
	public static final String S21_FIELD = "s21mat";
	public static final String S22_FIELD = "s22mat";
	public static final String S31_FIELD = "s31mat";
	public static final String S32_FIELD = "s32mat";
	public static final String S41_FIELD = "s41mat";
	public static final String S42_FIELD = "s42mat";
	public static final String S51_FIELD = "s51mat";
	public static final String S52_FIELD = "s52mat";
	public static final String DESCRIPTION_FIELD = "dscmat";
	public static final int DESCRIPTION_FIELD_LENGTH = 4000;
	public static final String GYM_FIELD = "gymmat";
	
	@Id
	@GeneratedValue
	@Column(name = IDENTIFIER_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	private Integer identifier;
	
	@JoinColumn(
		name = CHAMPIONSHIP_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = Championship.IDENTIFIER_FIELD
	)
	@NotNull(message = "Le championnat doit être renseigné")
	private Championship championship;
	
	@Column(name = STEP_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER)
	@NotNull(message = "L'étape doit être renseignée")
	private Integer step;
	
	@Column(name = ORDER_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer order;
	
	@JoinColumn(
		name = FIRST_TEAM_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = SeasonTeam.IDENTIFIER_FIELD
	)
	@NotNull(message = "L'équipe à domicile doit être renseignée")
	private SeasonTeam firstTeam;
	
	@JoinColumn(
		name = SECOND_TEAM_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = SeasonTeam.IDENTIFIER_FIELD
	)
	@NotNull(message = "L'équipe à l'extérieur doit être renseignée")
	private SeasonTeam secondTeam;
	
	@Column(name = DATE_FIELD, nullable = false, columnDefinition = DBTypeConstants.DATETIME)
	@NotNull(message = "La date doit être renseignée")
	private LocalDateTime date;
	
	@Column(name = STATE_FIELD, nullable = false, length = 1, columnDefinition = DBTypeConstants.ENUM)
	@NotNull(message = "L'état doit être renseigné")
	private Match.State state;
	
	@JoinColumn(
		name = SCORE_SETTER_FIELD, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = SeasonTeam.IDENTIFIER_FIELD
	)
	private SeasonTeam scoreSettingTeam;
	
	@Column(name = FORFEIT_FIELD, nullable = false, columnDefinition = DBTypeConstants.BOOLEAN)
	@NotNull(message = "La zone 'Forfait' doit être renseignée")
	private Boolean forfeit;
	
	@Column(name = FIRST_SCORE_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer firstScore;
	@Column(name = SECOND_SCORE_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer secondScore;
	@Column(name = S11_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer s11;
	@Column(name = S12_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer s12;
	@Column(name = S21_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer s21;
	@Column(name = S22_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer s22;
	@Column(name = S31_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer s31;
	@Column(name = S32_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer s32;
	@Column(name = S41_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer s41;
	@Column(name = S42_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer s42;
	@Column(name = S51_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer s51;
	@Column(name = S52_FIELD, columnDefinition = DBTypeConstants.INTEGER)
	private Integer s52;
	
	@Column(name = DESCRIPTION_FIELD, length = DESCRIPTION_FIELD_LENGTH, columnDefinition = DBTypeConstants.VARCHAR)
	@Size(max = DESCRIPTION_FIELD_LENGTH, message = "La taille de la description doit être au maximum de {max}")
	private String description;
	
	@JoinColumn(
		name = GYM_FIELD, nullable = false, columnDefinition = DBTypeConstants.INTEGER,
		referencedColumnName = Gym.IDENTIFIER_FIELD
	)
	private Gym gym;
	
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	public Championship getChampionship() {
		return championship;
	}
	public void setChampionship(Championship championship) {
		this.championship = championship;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public SeasonTeam getFirstTeam() {
		return firstTeam;
	}
	public void setFirstTeam(SeasonTeam firstTeam) {
		this.firstTeam = firstTeam;
	}
	public SeasonTeam getSecondTeam() {
		return secondTeam;
	}
	public void setSecondTeam(SeasonTeam secondTeam) {
		this.secondTeam = secondTeam;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Match.State getState() {
		return state;
	}
	public void setState(Match.State state) {
		this.state = state;
	}
	public SeasonTeam getScoreSettingTeam() {
		return scoreSettingTeam;
	}
	public void setScoreSettingTeam(SeasonTeam scoreSettingTeam) {
		this.scoreSettingTeam = scoreSettingTeam;
	}
	public Boolean isForfeit() {
		return forfeit;
	}
	public void setForfeit(Boolean forfeit) {
		this.forfeit = forfeit;
	}
	public Integer getFirstScore() {
		return firstScore;
	}
	public void setFirstScore(Integer sc1) {
		this.firstScore = sc1;
	}
	public Integer getSecondScore() {
		return secondScore;
	}
	public void setSecondScore(Integer sc2) {
		this.secondScore = sc2;
	}
	public Integer getS11() {
		return s11;
	}
	public void setS11(Integer s11) {
		this.s11 = s11;
	}
	public Integer getS12() {
		return s12;
	}
	public void setS12(Integer s12) {
		this.s12 = s12;
	}
	public Integer getS21() {
		return s21;
	}
	public void setS21(Integer s21) {
		this.s21 = s21;
	}
	public Integer getS22() {
		return s22;
	}
	public void setS22(Integer s22) {
		this.s22 = s22;
	}
	public Integer getS31() {
		return s31;
	}
	public void setS31(Integer s31) {
		this.s31 = s31;
	}
	public Integer getS32() {
		return s32;
	}
	public void setS32(Integer s32) {
		this.s32 = s32;
	}
	public Integer getS41() {
		return s41;
	}
	public void setS41(Integer s41) {
		this.s41 = s41;
	}
	public Integer getS42() {
		return s42;
	}
	public void setS42(Integer s42) {
		this.s42 = s42;
	}
	public Integer getS51() {
		return s51;
	}
	public void setS51(Integer s51) {
		this.s51 = s51;
	}
	public Integer getS52() {
		return s52;
	}
	public void setS52(Integer s52) {
		this.s52 = s52;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Gym getGym() {
		return gym;
	}
	public void setGym(Gym gym) {
		this.gym = gym;
	}

	public enum State {
		C, S, V, R, F
	}	
}
