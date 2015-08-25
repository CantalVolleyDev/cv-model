package com.jtouzy.cv.model.classes;

import com.jtouzy.dao.annotations.DAOTableField;
import com.jtouzy.dao.annotations.DAOTableRelation;
import com.jtouzy.dao.db.DBType;

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
}
