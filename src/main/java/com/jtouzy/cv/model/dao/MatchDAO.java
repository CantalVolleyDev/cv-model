package com.jtouzy.cv.model.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.cv.model.classes.Competition;
import com.jtouzy.cv.model.classes.Gym;
import com.jtouzy.cv.model.classes.Match;
import com.jtouzy.cv.model.classes.SeasonTeam;
import com.jtouzy.cv.model.classes.SeasonTeamPlayer;
import com.jtouzy.cv.model.validators.MatchValidator;
import com.jtouzy.dao.builders.model.OrContext;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.model.ModelContext;
import com.jtouzy.dao.query.Query;

public class MatchDAO extends AbstractSingleIdentifierDAO<Match> {
	public MatchDAO() {
		super(Match.class, new MatchValidator());
	}

	public Match getOneWithDetails(Integer matchId) 
	throws QueryException {
		Query<Match> query = queryWithTeamDetails();
		query.context()
		     .addDirectJoin(Championship.class)
		     .addDirectJoin(Gym.class)
		     .addDirectJoin(ModelContext.getTableContext(Competition.class), Championship.TABLE)
		     .addEqualsCriterion(Match.IDENTIFIER_FIELD, matchId);
		return query.one();
	}
	
	public List<Match> getAllPlayedBySeasonTeam(Integer teamId, Integer limitTo)
	throws QueryException {
		Query<Match> query = queryWithTeamDetails();
		query.context().addInCriterion(Match.STATE_FIELD, Arrays.asList(
				Match.State.V, Match.State.R, Match.State.S, Match.State.F));
		if (teamId != null) {
			OrContext orContext = new OrContext(query.context());
			orContext.addEqualsCriterion(Match.FIRST_TEAM_FIELD, teamId);
			orContext.addEqualsCriterion(Match.SECOND_TEAM_FIELD, teamId);
			query.context().addOrCriterion(orContext);
		}
		query.context().addDirectJoin(Championship.class)
	     			   .addDirectJoin(Gym.class)
	     			   .addDirectJoin(ModelContext.getTableContext(Competition.class), Championship.TABLE);
		query.context().limitTo(limitTo);
		query.context().orderBy(Match.DATE_FIELD, false);
		return query.many();
	}
	
	public List<Match> getAllByDate(LocalDate date)
	throws QueryException {
		Query<Match> query = queryWithTeamDetails();
		List<String> dates = new ArrayList<>();
		dates.add(date.toString() + " 00:00");
		dates.add(date.toString() + " 23:59");
		query.context().addDirectJoin(Championship.class)
	     			   .addDirectJoin(ModelContext.getTableContext(Competition.class), Championship.TABLE)
	     			   .addBetweenCriterion(Match.DATE_FIELD, dates);
		query.context().orderBy(Match.DATE_FIELD, true);
		query.context().orderBy(Competition.class, Competition.ORDER_FIELD, true);
		query.context().orderBy(Championship.class, Championship.ORDER_FIELD, true);
		return query.many();
	}
	
	public List<Match> getAllBySeasonAndUser(Integer seasonId, Integer userId)
	throws QueryException {
		Query<Match> query = queryWithTeamDetails();
		if (seasonId != null) {
			query.context()
			     .addDirectJoin(Championship.class)
			     .addDirectJoin(ModelContext.getTableContext(Competition.class), Championship.TABLE)
			     .addEqualsCriterion(Competition.class, Competition.SEASON_FIELD, seasonId);
		}
		if (userId != null) {
			Query<SeasonTeamPlayer> queryTeam = Query.build(this.connection, SeasonTeamPlayer.class);
			queryTeam.context().addEqualsCriterion(SeasonTeamPlayer.PLAYER_FIELD, userId);
			if (seasonId != null) {
				queryTeam.context().addDirectJoin(SeasonTeam.class)
								   .addEqualsCriterion(SeasonTeam.class, SeasonTeam.SEASON_FIELD, seasonId);
			}
			List<SeasonTeamPlayer> teamPlayers = queryTeam.many();
			List<Integer> teamIds = teamPlayers.stream()
					                           .map(sp -> sp.getTeam().getIdentifier())
					                           .collect(Collectors.toList());
			if (teamIds.size() == 0)
				return new ArrayList<>();
			OrContext orContext = new OrContext(query.context());
			orContext.addInCriterion(Match.FIRST_TEAM_FIELD, teamIds);
			orContext.addInCriterion(Match.SECOND_TEAM_FIELD, teamIds);
			query.context().addOrCriterion(orContext);
		}
		query.context().orderBy(Match.DATE_FIELD, true);
		return query.many();
	}
	
	public List<Match> getAllByChampionship(Integer championshipId)
	throws QueryException {
		return queryByChampionship(championshipId).many();
	}
	
	public List<Match> getAllValidateByChampionship(Integer championshipId)
	throws QueryException {
		Query<Match> query = queryByChampionship(championshipId);
		query.context().addInCriterion(Match.STATE_FIELD, Arrays.asList(Match.State.V, Match.State.F));
		return query.many();
	}
	
	private Query<Match> queryByChampionship(Integer championshipId) {
		Query<Match> query = queryWithTeamDetails();
		query.context().addEqualsCriterion(Match.CHAMPIONSHIP_FIELD, championshipId);
		query.context().orderBy(Match.ORDER_FIELD, true);
		return query;
	}
	
	private Query<Match> queryWithTeamDetails() {
		Query<Match> query = super.query();
		query.context()
	         .addDirectJoin(SeasonTeam.class, "eq1", Match.FIRST_TEAM_FIELD)
	         .addDirectJoin(SeasonTeam.class, "eq2", Match.SECOND_TEAM_FIELD)
	         .addDirectJoin(Gym.class);
		return query;
	}
}
