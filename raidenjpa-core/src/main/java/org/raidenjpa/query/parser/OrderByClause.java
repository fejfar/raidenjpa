package org.raidenjpa.query.parser;

import java.util.ArrayList;
import java.util.List;

public class OrderByClause {

	private List<OrderByElement> elements = new ArrayList<OrderByElement>();

	public void parse(QueryWords words) {
		if (!words.hasMoreWord() || !words.current().equalsIgnoreCase("ORDER")) {
			return;
		}
		
		words.next(); // ORDER
		
		do {
			words.next();
			String path = words.next();
			String orientation = "ASC";
			if (words.hasMoreWord() && (words.current().equalsIgnoreCase("ASC") || words.current().equalsIgnoreCase("DESC"))) {
				orientation = words.next();
			}
			elements.add(new OrderByElement(path, orientation));
		} while(words.hasMoreOrderByElements());
	}
	
	public List<OrderByElement> getElements() {
		return elements;
	}

}
