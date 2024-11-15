package org.bedework.dao.calendar;

import org.bedework.dao.common.DaoBase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CalendarDaoTest {
  @Test
  public void testBasics() {
//    final var aDao = new CalendarDaoPostgresqlV4();

    final var aQuery = """
            select lm.path, lm.timestamp, lm.sequence from
               bw_collection_lastmods lm,
               bw_calendars col
             where col.colPath=:path and lm.path=col.path
               and (col.filterExpr is null
               or col.filterExpr <> :tsfilter)
            """;

    final var aPs = """
            select lm.path, lm.timestamp, lm.sequence from
               bw_collection_lastmods lm,
               bw_calendars col
             where col.colPath=? and lm.path=col.path
               and (col.filterExpr is null
               or col.filterExpr <> ?)
            """;
    final var params = DaoBase.getParameters(aQuery);
    assertNotNull(params);
    if (params.isEmpty()) {
      out("No parameters for " + aQuery);
    }
    for (final var k: params.keySet()) {
      out(k + ": " + params.get(k));
    }

    final var psText = DaoBase.replaceParameters(aQuery);
    assertEquals(aPs, psText, "Expected correct ps text");
  }

  private void out(final String s) {
    System.out.println(s);
  }
}
