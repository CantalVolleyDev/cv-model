package com.jtouzy.cv.model.dao;

import java.util.List;
import java.util.stream.Collectors;

import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.cv.model.classes.Competition;
import com.jtouzy.cv.model.classes.Match;
import com.jtouzy.cv.model.classes.SeasonTeamPlayer;
import com.jtouzy.cv.model.classes.Team;
import com.jtouzy.cv.model.validators.MatchValidator;
import com.jtouzy.dao.builders.model.OrContext;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.ColumnContextNotFoundException;
import com.jtouzy.dao.errors.model.TableContextNotFoundException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.model.ModelContext;
import com.jtouzy.dao.query.Query;

public class MatchDAO extends AbstractSingleIdentifierDAO<Match> {
	public MatchDAO()
	throws DAOException {
		super(Match.class, new MatchValidator());
	}
	
	public List<Match> getMatchs(Integer seasonId, Integer userId)
	throws QueryException {
		try {
			Query<Match> query = query();
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
					queryTeam.context().addEqualsCriterion(SeasonTeamPlayer.SEASON_FIELD, seasonId);
				}
				List<SeasonTeamPlayer> teamPlayers = queryTeam.many();
				List<Integer> teamIds = teamPlayers.stream()
						                           .map(sp -> sp.getTeam().getIdentifier())
						                           .collect(Collectors.toList());
				OrContext orContext = new OrContext(query.context());
				orContext.addInCriterion(Match.FIRST_TEAM_FIELD, teamIds);
				orContext.addInCriterion(Match.SECOND_TEAM_FIELD, teamIds);
				query.context().addOrCriterion(orContext);
			}
			query.context()
			     .addDirectJoin(Team.class, "eq1", Match.FIRST_TEAM_FIELD)
			     .addDirectJoin(Team.class, "eq2", Match.SECOND_TEAM_FIELD);
			return query.many();
		} catch (TableContextNotFoundException | ColumnContextNotFoundException ex) {
			throw new QueryException(ex);
		}
	}
}
