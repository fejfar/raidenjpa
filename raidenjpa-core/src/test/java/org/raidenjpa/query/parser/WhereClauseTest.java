package org.raidenjpa.query.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class WhereClauseTest {

	@Test
	public void testOneExpression() {
		String jpql = "SELECT a FROM A a WHERE a.stringValue = :a ORDER BY a.stringValue";

		QueryParser parser = new QueryParser(jpql);
		List<LogicExpressionElement> elements = parser.getWhere().getLogicExpression().getElements();

		Condition condition = (Condition) elements.get(0);
		assertExpression(condition, "a.stringValue", "=", "a");
		assertEquals(1, elements.size());
	}

	@Test
	public void testAndExpression() {
		String jpql;
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.stringValue = :stringValue AND a.intValue = :intValue ORDER BY a.stringValue";

		QueryParser parser = new QueryParser(jpql);
		List<LogicExpressionElement> elements = parser.getWhere().getLogicExpression().getElements();
		
		Condition firstExpression = (Condition) elements.get(0);
		assertExpression(firstExpression, "a.stringValue", "=", "stringValue");
		
		LogicOperator logicOperator = (LogicOperator) elements.get(1);
		assertEquals("AND", logicOperator.getOperator());
		
		Condition secondExpression = (Condition) elements.get(2);
		assertExpression(secondExpression, "a.intValue", "=", "intValue");
	}
	
	@Test
	public void testInOperator() {
		String jpql;
		jpql = "SELECT a FROM A a";
		jpql += " WHERE a.intValue IN (:values)";

		QueryParser parser = new QueryParser(jpql);
		List<LogicExpressionElement> elements = parser.getWhere().getLogicExpression().getElements();
		assertEquals(1, elements.size());
		Condition firstExpression = (Condition) elements.get(0);
		assertExpression(firstExpression, "a.intValue", "IN", "values");
	}
	
	private void assertExpression(Condition condition, String leftSide, String operator, String parameterName) {
		ConditionPath left = (ConditionPath) condition.getLeft();
		String[] paths = leftSide.split("\\.");
		assertEquals(paths.length, left.getPath().size());
		assertEquals(paths[0], left.getPath().get(0));
		assertEquals(paths[1], left.getPath().get(1));

		assertEquals(operator, condition.getOperator());

		ConditionParameter right = (ConditionParameter) condition.getRight();
		assertEquals(parameterName, right.getParameterName());
	}
}
