package org.raidenjpa.query.executor;

import static org.junit.Assert.assertEquals;
import static org.raidenjpa.query.executor.StackQueryOperation.RESOLVE; 

import org.junit.Test;
import org.raidenjpa.query.parser.QueryParser;
import org.raidenjpa.query.parser.WhereClause;
import org.raidenjpa.query.parser.WhereElement;

public class StackQueryTest {

	@Test
	public void testOneExpression() {
		StackQuery stackQuery = new StackQuery();
		
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :stringValue"; 
		WhereClause where = new QueryParser(jpql).getWhere();
		
		WhereElement firstExpression = where.nextElement();
		assertEquals(RESOLVE, stackQuery.push(firstExpression));
	}
}
