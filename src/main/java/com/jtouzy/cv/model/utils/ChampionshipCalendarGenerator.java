package com.jtouzy.cv.model.utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.cv.model.classes.ChampionshipTeam;
import com.jtouzy.cv.model.classes.ChampionshipWeeks;
import com.jtouzy.cv.model.classes.Competition;
import com.jtouzy.cv.model.classes.Match;
import com.jtouzy.cv.model.classes.Season;
import com.jtouzy.cv.model.classes.SeasonTeam;
import com.jtouzy.cv.model.classes.Team;
import com.jtouzy.cv.model.dao.ChampionshipDAO;
import com.jtouzy.cv.model.errors.CalendarGenerationException;
import com.jtouzy.dao.DAOManager;
import com.jtouzy.dao.errors.DAOInstantiationException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.ColumnContextNotFoundException;
import com.jtouzy.dao.errors.model.TableContextNotFoundException;
import com.jtouzy.dao.model.ModelContext;
import com.jtouzy.dao.query.Query;
import com.jtouzy.dao.query.QueryCollection;

public class ChampionshipCalendarGenerator {
	private Connection connection;
	private Integer championshipId;
	private Season season;
	private Championship championship;
	private List<ChampionshipTeam> teams;
	private List<ChampionshipWeeks> weeks;
	private Integer teamCount;
	private Integer dayCount;
	private Integer realDayCount;
	private boolean returnMatchs;
	private List<Match> matchs;
	private List<SeasonTeam> seasonTeams;
	
	public static List<Match> listMatchs(Connection connection, Integer championshipId, boolean returnMatchs)
	throws CalendarGenerationException {
		ChampionshipCalendarGenerator generator = 
				new ChampionshipCalendarGenerator(connection, championshipId, returnMatchs);
		generator.buildCalendar();
		return generator.matchs;
	}
	
	private ChampionshipCalendarGenerator(Connection connection, Integer championshipId, boolean returnMatchs) {
		this.connection = connection;
		this.championshipId = championshipId;
		this.returnMatchs = returnMatchs;
	}
	
	private boolean isExemptMatch(ChampionshipTeam team1, ChampionshipTeam team2) {
		return team1.getTeam().getLabel().equals("Exempt") ||
			   team2.getTeam().getLabel().equals("Exempt");
	}
	
	private void buildCalendar()
	throws CalendarGenerationException {
		// -> Recherche des infos championnat + équipes pour le championnat + saison associée
		findChampionshipMainData();
		// -> Contrôle nombre d'équipes + ajout exempt si nécessaire
		controlAndResolveTeams();
		
		// -> Informations
		this.teamCount = this.teams.size();
		this.dayCount  = this.teamCount - 1;
		this.realDayCount = this.returnMatchs ? this.dayCount*2 : this.dayCount;

		// -> Recherche des semaines associées au championnat
		findChampionshipWeeks();
		// -> Contrôle nombre de semaines
		controlWeeks();
		
		// -> Recherche des équipes/saison pour avoir les informations sur les dates
		findSeasonTeams();
		// -> Contrôle des équipes/saison
		controlSeasonTeams();
		
		// -> Génération des matchs
		calculateCalendar();
	}
	
	private void findChampionshipMainData()
	throws CalendarGenerationException {
		try {
			ChampionshipDAO championshipDao = DAOManager.getDAO(this.connection, ChampionshipDAO.class);			
			QueryCollection<Championship, ChampionshipTeam> chpQuery = championshipDao.queryCollection(ChampionshipTeam.class); 
			chpQuery.context()
			        .addDirectJoin(ModelContext.getTableContext(Team.class), ChampionshipTeam.TABLE)
			        .addDirectJoin(ModelContext.getTableContext(Competition.class), Championship.TABLE)
			        .addEqualsCriterion(Championship.class, Championship.IDENTIFIER_FIELD, championshipId);
			this.championship = chpQuery.fillOne();
			if (this.championship == null) {
				throw new CalendarGenerationException("Championnat " + championshipId + " inexistant");
			}
			this.teams = this.championship.getTeams();
			this.season = this.championship.getCompetition().getSeason();
		} catch (DAOInstantiationException | QueryException | TableContextNotFoundException | ColumnContextNotFoundException ex) {
			throw new CalendarGenerationException(ex);
		}
	}
	
	private void controlAndResolveTeams()
	throws CalendarGenerationException {
		int teamCount = this.teams.size();
		if (teamCount == 0) {
			throw new CalendarGenerationException("Aucune équipe dans le championnat");
		}
		if (teamCount%2 != 0) {
			Championship chp = new Championship();
			chp.setIdentifier(this.championshipId);
			Team exempt = new Team();
			exempt.setLabel("Exempt");
			ChampionshipTeam ctExempt = new ChampionshipTeam();
			ctExempt.setChampionship(chp);
			ctExempt.setTeam(exempt);
			ctExempt.setBonus(0);
			ctExempt.setForfeit(0);
			ctExempt.setLoose(0);
			ctExempt.setLoose3By2(0);
			ctExempt.setPlay(0);
			ctExempt.setPoints(0);
			ctExempt.setPointsAgainst(0);
			ctExempt.setPointsFor(0);
			ctExempt.setSetsAgainst(0);
			ctExempt.setSetsFor(0);
			ctExempt.setWin(0);
			this.teams.add(ctExempt);
		}
	}
	
	private void findChampionshipWeeks()
	throws CalendarGenerationException {
		try {
			ChampionshipDAO championshipDao = DAOManager.getDAO(this.connection, ChampionshipDAO.class);	
			QueryCollection<Championship, ChampionshipWeeks> weeksQuery = championshipDao.queryCollection(ChampionshipWeeks.class); 
			weeksQuery.context()
				      .orderBy(Championship.class, Championship.IDENTIFIER_FIELD)
				      .orderBy(ChampionshipWeeks.class, ChampionshipWeeks.WEEK_DATE_FIELD)
			          .addEqualsCriterion(Championship.class, Championship.IDENTIFIER_FIELD, championshipId);
			this.weeks = weeksQuery.fillOne().getWeeks();
		} catch (DAOInstantiationException | QueryException | TableContextNotFoundException | ColumnContextNotFoundException ex) {
			throw new CalendarGenerationException(ex);
		} 
	}
	
	private void controlWeeks()
	throws CalendarGenerationException {
		int weeksCount = this.weeks.size();
		if (weeksCount == 0) {
			throw new CalendarGenerationException("Aucune semaine définie pour la génération");
		}
		if (weeksCount != this.realDayCount) {
			throw new CalendarGenerationException("Le nombre de semaines enregistré n'est pas cohérent : Pour " + teamCount + " équipes (EXEMPT compris), il faut " + realDayCount + " journées. " + weeksCount + " enregistrées actuellement.");
		}
	}
	
	private void calculateCalendar()
	throws CalendarGenerationException {
		// -> Initialisations
		int matchCount = teamCount/2;
		this.matchs = new ArrayList<>();
		int firstTeamIndex, secondTeamIndex;
		ChampionshipTeam team1, team2;
		Match match;
		SeasonTeam seasonTeam;
		// -> Boucle sur les journées
		for (int j = 1; j <= dayCount; j++) {
			firstTeamIndex = 1;
			secondTeamIndex = teamCount-j+1;
			// -> Boucle sur les matchs pour cette journée
			for (int m = 1; m <= matchCount; m++) {
				team1 = j%2 == 0 ? teams.get(firstTeamIndex-1) : teams.get(secondTeamIndex-1);
				team2 = j%2 == 0 ? teams.get(secondTeamIndex-1) : teams.get(firstTeamIndex-1);
				if (!isExemptMatch(team1, team2)) {
					match = new Match();
					match.setFirstTeam(team1.getTeam());
					match.setSecondTeam(team2.getTeam());
					match.setStep(j);
					seasonTeam = getSeasonTeam(match.getFirstTeam());
					match.setDate(weeks.get(j-1) 
							           .getWeekDate()
							           .plusDays(seasonTeam.getDate().getDayOfWeek().getValue() - 1)
							           .plusHours(seasonTeam.getDate().getHour())
							           .plusMinutes(seasonTeam.getDate().getMinute()));
					this.matchs.add(match);
				}
				if (j == 1) {
					firstTeamIndex++;
					secondTeamIndex--;
				} else {
					if (firstTeamIndex == 1 || firstTeamIndex == teamCount)
						firstTeamIndex = firstTeamIndex == 1 ? (teamCount - j + 2) : 2;
					else if (firstTeamIndex != teamCount)
						firstTeamIndex++;
					
					secondTeamIndex--;
					if (secondTeamIndex <= (matchCount - j) || secondTeamIndex == 1)
						secondTeamIndex = teamCount;
				}
				
			}
		}
		// -> Génération des matchs retour si nécessaire
		calculateReturnMatchsIfNeeded();
	}
	
	private void calculateReturnMatchsIfNeeded() {
		if (!this.returnMatchs)
			return;
		
		final List<Match> newMatchs = new ArrayList<>();
		Iterator<Match> it = matchs.iterator();
		Match match, match2;
		int j;
		while (it.hasNext()) {
			match = it.next();
			j = match.getStep() + dayCount;
			match2 = new Match();
			match2.setFirstTeam(match.getSecondTeam());
			match2.setSecondTeam(match.getFirstTeam());
			match2.setStep(j);
			match2.setDate(weeks.get(j-1).getWeekDate());
			newMatchs.add(match2);
		}
		this.matchs.addAll(newMatchs);
	}
	
	private void findSeasonTeams()
	throws CalendarGenerationException {
		try {
			Query<SeasonTeam> qst = Query.build(this.connection, SeasonTeam.class);
			qst.context().addEqualsCriterion(SeasonTeam.SEASON_FIELD, season.getIdentifier());
			this.seasonTeams = qst.many();
		} catch (TableContextNotFoundException | QueryException ex) {
			throw new CalendarGenerationException(ex);
		}
	}
	
	private void controlSeasonTeams()
	throws CalendarGenerationException {
		if (this.seasonTeams.size() == 0)
			throw new CalendarGenerationException("Aucune équipe enregistrée pour la saison");
	}
	
	private SeasonTeam getSeasonTeam(Team team)
	throws CalendarGenerationException {
		Optional<SeasonTeam> opt = seasonTeams.stream()
				                              .filter(st -> st.getTeam().getIdentifier() == team.getIdentifier())
				                              .findFirst();
		if (!opt.isPresent())
			throw new CalendarGenerationException("Equipe " + team.getLabel() + " non enregistré pour la saison");
		return opt.get();
	}
	
	
}
