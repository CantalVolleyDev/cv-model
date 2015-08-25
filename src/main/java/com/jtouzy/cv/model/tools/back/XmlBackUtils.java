package com.jtouzy.cv.model.tools.back;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.google.common.collect.Lists;
import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.cv.model.classes.Comment;
import com.jtouzy.dao.DAOManager;
import com.jtouzy.dao.db.DBType;
import com.jtouzy.dao.model.ColumnContext;
import com.jtouzy.dao.model.ModelContext;
import com.jtouzy.dao.model.TableContext;
import com.jtouzy.dao.reflect.ObjectUtils;

public class XmlBackUtils {

	//private static Map<String, Map<String, Object>> xmlValues;
	private static final List<String> tableList = Lists.newArrayList(
		"sai", "cmp", "cmt", "chp"
	);
	private static final List<String> excludeColumns = Lists.newArrayList(
		"ufbcmp",
		"eqicmt", "notcmt"
	);
	private static final Map<String, Integer> summary = new LinkedHashMap<>();
	
	public static void main(String[] args)
	throws Exception {
		DAOManager.init("com.jtouzy.cv.model.classes");
		//DropboxAPI.downloadDumpFile();
		XmlBackUtils.load();
	}
	
	public static void load()
	throws Exception {
		//xmlValues = new HashMap<String, Map<String,Object>>();
		Document doc = getBackDocument();
		loadDatabase(doc.getRootElement().getChild("database"));
		System.out.println(summary);
	}
	
	private static Document getBackDocument()
	throws Exception {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("dbdump.xml");
		Document document = (Document) builder.build(xmlFile);
		return document;
	}
	
	private static void loadDatabase(Element databaseElement)
	throws Exception {
		Iterator<String> it = tableList.iterator();
		String tableName;
		Element tableElement;
		while (it.hasNext()) {
			tableName = it.next();
			tableElement = getTableElement(tableName, databaseElement);
			if (tableElement == null) {
				System.out.println("Aucune donnée à charger pour la table " + tableName);
				continue;
			}
			loadTable(tableElement);
		}
	}
	
	private static Element getTableElement(String tableName, Element databaseElement) {
		Iterator<Element> it = databaseElement.getChildren("table_data").iterator();
		Element tableElement;
		while (it.hasNext()) {
			tableElement = it.next();
			if (tableElement.getAttributeValue("name").equals(tableName)) {
				return tableElement;
			}
		}
		return null;
	}
	
	private static void loadTable(Element tableElement)
	throws Exception {
		TableContext tableContext = ModelContext.getTableContext(tableElement.getAttributeValue("name"));
		System.out.println("Chargement des données pour la table " + tableContext.getName() + "...");
		List<Element> rows = tableElement.getChildren("row");
		Iterator<Element> it = rows.iterator();
		while (it.hasNext()) 
			loadRowForTable(tableContext, it.next());
		summary.put(tableContext.getName(), rows.size());
	}
	
	private static void loadRowForTable(TableContext tableContext, Element rowElement)
	throws Exception {
		Object instance = ObjectUtils.newObject(tableContext.getTableClass());
		Iterator<Element> it = rowElement.getChildren("field").iterator();
		Element field;
		ColumnContext columnContext;
		Object value = null;
		String name = null;
		String valueStr = null;
		while (it.hasNext()) {
			field = it.next();
			name = field.getAttributeValue("name");
			if (excludeColumns.contains(name))
				continue;
			columnContext = tableContext.getColumnContext(name);
			value = field.getValue();
			if (String.valueOf(value).length() != 0) {
				switch (columnContext.getType()) {
					case INTEGER:
						value = Integer.parseInt(field.getValue());
						break;
					case BOOLEAN:
						if (value.equals("O") || value.equals("N")) {
							value = value.equals("O");
						} else {
							value = Boolean.parseBoolean(field.getValue());
						}
						break;
					case ENUM:
						switch (columnContext.getName()) {
							case "entcmt":
								switch (String.valueOf(value)) {
									case "NWS": value = Comment.Entity.NWS; break;
									case "MAT": value = Comment.Entity.MAT; break;
								}
								break;
							case "genchp":
								switch (String.valueOf(value)) {
									case "M": value = Championship.Gender.M; break;
									case "X": value = Championship.Gender.X; break;
									case "F": value = Championship.Gender.F; break;
								}
								break;
							case "stachp":
								switch (String.valueOf(value)) {
									case "C": value = Championship.State.C; break;
									case "V": value = Championship.State.V; break;
								}
								break;
							case "typchp":
								switch (String.valueOf(value)) {
									case "CHP": value = Championship.Type.CHP; break;
									case "CUP": value = Championship.Type.CUP; break;
								}
								break;
							default:
								break;
						}
						break;
					case DATE:
						valueStr = String.valueOf(value).replace(" ", "T");
						if (valueStr.equals("0000-00-00T00:00:00")) {
							value = null;
						} else {
							value = LocalDateTime.parse(valueStr);
						}
						break;
					case VARCHAR:
						valueStr = String.valueOf(value);
						valueStr = valueStr.replace("&eacute;", "é");
						valueStr = valueStr.replace("Ã©", "é");
						valueStr = valueStr.replace("&egrave;", "è");
						valueStr = valueStr.replace("Ã¨", "è");
						valueStr = valueStr.replace("&ccedil;", "ç");
						valueStr = valueStr.replace("Ã§", "ç");
						valueStr = valueStr.replace("&acirc;", "â");
						valueStr = valueStr.replace("&ecirc;", "ê");
						valueStr = valueStr.replace("&nbsp;", " ");
						valueStr = valueStr.replace("&agrave;", "à");
						valueStr = valueStr.replace("&ocirc;", "ô");
						value = valueStr;
						break;
					default:
						break;
				}
			} else {
				value = null;
				if (columnContext.getType() == DBType.BOOLEAN)
					value = false;
			}
			ObjectUtils.setValue(instance, columnContext, value);
		}
		System.out.println(instance);
	}
}
