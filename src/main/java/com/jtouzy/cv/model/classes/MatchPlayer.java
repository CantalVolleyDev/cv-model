package com.jtouzy.cv.model.classes;

import com.jtouzy.dao.annotations.DAOTable;
import com.jtouzy.dao.annotations.DAOTableField;
import com.jtouzy.dao.annotations.DAOTableRelation;
import com.jtouzy.dao.db.DBType;

@DAOTable(tableName = "pma")
public class MatchPlayer {
	@DAOTableRelation(
		column = @DAOTableField(id = true, name = "matpma", required = true, type = DBType.INTEGER),
		relationColumn = "nummat"
	)
	private Match match;
	@DAOTableRelation(
		column = @DAOTableField(id = true, name = "eqipma", required = true, type = DBType.INTEGER),
		relationColumn = "numeqi"
	)
	private Team team;
	@DAOTableRelation(
		column = @DAOTableField(id = true, name = "usrpma", required = true, type = DBType.INTEGER),
		relationColumn = "numusr"
	)
	private User player;
	
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public User getPlayer() {
		return player;
	}
	public void setPlayer(User player) {
		this.player = player;
	}
}
