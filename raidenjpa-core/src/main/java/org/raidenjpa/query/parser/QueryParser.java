package org.raidenjpa.query.parser;

import org.raidenjpa.util.BadSmell;

public class QueryParser {
	
	private QueryWords words;

	private SelectClause select;
	
	private FromClause from;

	private WhereClause where;
	
	public QueryParser(String jpql) {
		this.words = new QueryWords(jpql);
		
		int position;
		position = prepareSelect();
		position = prepareFrom(position);
		position = prepareWhere(position);
	}

	private int prepareSelect() {
		if (!"SELECT".equalsIgnoreCase(words.get(0))) {
			return 0;
		}
		
		select = new SelectClause(words.get(1));
		
		return 2;
	}

	@BadSmell("This should be inside FromClause")
	private int prepareFrom(int position) {
		from = new FromClause();
		return from.parse(words, position);
	}

	@BadSmell("This if should be inside where.parse (do it when create QueryWords)")
	private int prepareWhere(int position) {
		if (!words.hasMoreWord(position)) {
			return position;
		}
		
		where = new WhereClause();
		return where.parse(words, position);
	}

	public SelectClause getSelect() {
		return select;
	}

	public FromClause getFrom() {
		return from;
	}

	public WhereClause getWhere() {
		return where;
	}
}
