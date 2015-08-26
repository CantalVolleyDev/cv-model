package com.jtouzy.cv.model.classes;

import com.jtouzy.dao.annotations.DAOTable;
import com.jtouzy.dao.annotations.DAOTableField;
import com.jtouzy.dao.annotations.DAOTableRelation;
import com.jtouzy.dao.db.DBType;

@DAOTable(tableName = "eqs")
public class SeasonTeam {
	@DAOTableRelation(
		column = @DAOTableField(id = true, name = "eqieqs", required = true, type = DBType.INTEGER),
		relationColumn = "numeqi"
	)
	private Team team;
	@DAOTableRelation(
		column = @DAOTableField(id = true, name = "saieqs", required = true, type = DBType.INTEGER),
		relationColumn = "numsai"
	)
	private Season season;
	
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeasonTeam [team=");
		builder.append(team);
		builder.append(", season=");
		builder.append(season);
		builder.append("]");
		return builder.toString();
	}
}
