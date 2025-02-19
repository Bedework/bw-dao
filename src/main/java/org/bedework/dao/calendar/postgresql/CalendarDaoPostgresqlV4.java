package org.bedework.dao.calendar.postgresql;

import org.bedework.dao.calendar.CalendarDaoBaseV4;

import java.sql.Timestamp;

public class CalendarDaoPostgresqlV4 extends CalendarDaoBaseV4 {
  protected String getSynchInfoQuery() {
    return """
            select lm.timestamp, lm.sequence from
             bw_collection_lastmods lm
             where path=:path
            """;
  }

  @Override
  public String getSynchInfo(final String path) {
    createQuery(getSynchInfoQuery());
    setString("path", path);

    return "";
  }

  @Override
  public Timestamp getCurrentTimestamp() {
    return null;
  }
}
