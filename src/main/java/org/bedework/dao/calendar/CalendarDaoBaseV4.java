package org.bedework.dao.calendar;

import org.bedework.dao.common.DaoBase;

public abstract class CalendarDaoBaseV4 extends DaoBase
        implements CalendarDaoV4 {

  protected void initAppServer() {
    initAppServer("java:comp/env/jdbc/calendarDS");
  }

  protected void initEmbedded() {
    initEmbedded("jdbc:postgresql://localhost/caldb?user=bw&password=bw&ssl=true");
  }
}
