package org.bedework.dao.common;

import org.bedework.calfacade.exc.CalFacadeException;
import org.bedework.util.logging.BwLogger;
import org.bedework.util.logging.Logged;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class DaoUtil implements Logged {
  protected boolean embeddedDrivers;
  protected String jdbcUrl;

  protected abstract void initAppServer();

  protected abstract void initEmbedded();

  protected void initAppServer(final String jdbcUrl) {
    embeddedDrivers = false;
    this.jdbcUrl = jdbcUrl;
  }

  protected void initEmbedded(final String jdbcUrl) {
    embeddedDrivers = true;
    this.jdbcUrl = jdbcUrl;
  }

  protected Connection getConnection() {
    try {
      if (embeddedDrivers) {
        return DriverManager.getConnection(jdbcUrl);
      } else {
        final Context ctx = new InitialContext();
        final DataSource ds = (DataSource)ctx.lookup(jdbcUrl);
        return ds.getConnection();
      }
    } catch (final NamingException | SQLException ne) {
      throw new CalFacadeException(ne);
    }
  }

  /* ==============================================================
   *                   Logged methods
   * ============================================================== */

  private final BwLogger logger = new BwLogger();

  @Override
  public BwLogger getLogger() {
    if ((logger.getLoggedClass() == null) && (logger.getLoggedName() == null)) {
      logger.setLoggedClass(getClass());
    }

    return logger;
  }
}
