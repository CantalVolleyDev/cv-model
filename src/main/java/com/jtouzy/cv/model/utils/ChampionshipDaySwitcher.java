package com.jtouzy.cv.model.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.jtouzy.cv.model.classes.ChampionshipWeeks;
import com.jtouzy.cv.model.classes.Match;
import com.jtouzy.cv.model.dao.ChampionshipWeeksDAO;
import com.jtouzy.cv.model.dao.MatchDAO;
import com.jtouzy.cv.model.errors.UtilsToolException;
import com.jtouzy.dao.DAOManager;
import com.jtouzy.dao.errors.DAOException;

public class ChampionshipDaySwitcher {
	private Connection connection;
	private Integer firstDay;
	private Integer dayToSwitch;
	private Integer championshipId;
	
	public static void switchDay(Connection connection, Integer championshipId, Integer firstDay, Integer dayToSwitch)
	throws UtilsToolException {
		ChampionshipDaySwitcher switcher = 
				new ChampionshipDaySwitcher(connection, championshipId, firstDay, dayToSwitch);
		switcher.execute();
	}
	
	private ChampionshipDaySwitcher(Connection connection, Integer championshipId, Integer firstDay, Integer dayToSwitch) {
		this.connection = connection;
		this.championshipId = championshipId;
		this.firstDay = firstDay;
		this.dayToSwitch = dayToSwitch;
	}
	
	private void execute()
	throws UtilsToolException {
		try {
			MatchDAO dao = DAOManager.getDAO(this.connection, MatchDAO.class);
			List<Match> matchs = dao.getAllByChampionship(this.championshipId);
			List<Match> firstDayMatchs = matchs.stream()
					                           .filter(m -> m.getStep() == firstDay)
					                           .collect(Collectors.toList());
			List<Match> switchDayMatchs = matchs.stream()
                    							.filter(m -> m.getStep() == dayToSwitch)
                								.collect(Collectors.toList());
			List<ChampionshipWeeks> weeks = DAOManager.getDAO(this.connection, ChampionshipWeeksDAO.class)
					                                  .getAllByChampionship(this.championshipId);
			firstDayMatchs.forEach(m -> {
				LocalDateTime date = m.getFirstTeam().getDate();
				m.setStep(dayToSwitch);
				m.setDate(weeks.get(dayToSwitch-1)
						       .getWeekDate()
						       .plusDays(date.getDayOfWeek().getValue() - 1)
						       .plusHours(date.getHour())
						       .plusMinutes(date.getMinute()));
			});
			switchDayMatchs.forEach(m -> {
				LocalDateTime date = m.getFirstTeam().getDate();
				m.setStep(firstDay);
				m.setDate(weeks.get(firstDay-1)
						       .getWeekDate()
						       .plusDays(date.getDayOfWeek().getValue() - 1)
						       .plusHours(date.getHour())
						       .plusMinutes(date.getMinute()));
			});
			firstDayMatchs.addAll(switchDayMatchs);
			
			try {
				this.connection.setAutoCommit(false);
				Iterator<Match> it = firstDayMatchs.iterator();
				Match match;
				while (it.hasNext()) {
					match = it.next();
					dao.update(match);
				}
				this.connection.commit();
				this.connection.setAutoCommit(true);
			} catch (SQLException ex) {
				try {
					if (!this.connection.getAutoCommit()) {
						this.connection.rollback();
						this.connection.setAutoCommit(true);
					}
				} catch (SQLException ex2) {
					throw new UtilsToolException(ex2);
				}
				throw new UtilsToolException(ex);
			}
			
		} catch (DAOException ex) {
			throw new UtilsToolException(ex);
		}
	}
}
