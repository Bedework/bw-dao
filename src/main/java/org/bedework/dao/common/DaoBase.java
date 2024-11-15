package org.bedework.dao.common;

import org.bedework.calfacade.exc.CalFacadeException;
import org.bedework.util.logging.BwLogger;
import org.bedework.util.logging.Logged;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class DaoBase implements Logged {
  /**
   * Defines a regular expression to extract named parameters
   */
  public static final Pattern extractParams =
          Pattern.compile("(?:.*?\\:(\\w*))");

  /**
   * Defines a regular expression to replace named parameters
   */
  public static final Pattern replaceParams =
          Pattern.compile("(?:.*?(\\:\\w*))");

  public static record Param(int pos,
                             String name,
                             String value) {
  }

  private Map<String, Param> paramMap = new HashMap<>();

  protected boolean embeddedDrivers;
  protected String jdbcUrl;
  protected Connection c;
  protected boolean rolledBack;
  private String query;
  protected String psText;
  protected PreparedStatement ps;

  /** Exception from this session. */
  Throwable exc;

  protected abstract void initAppServer();

  protected abstract void initEmbedded();

  /**
   * @return a timestamp from the db
   * @throws CalFacadeException on fatal error
   */
  public abstract Timestamp getCurrentTimestamp();

  /**
   * @return a blob
   */
  public abstract Blob getBlob(byte[] val);

  /**
   * @return a blob
   */
  public abstract Blob getBlob(InputStream val, long length);

  public void initAppServer(final String jdbcUrl) {
    embeddedDrivers = false;
    this.jdbcUrl = jdbcUrl;
  }

  public void initEmbedded(final String jdbcUrl) {
    embeddedDrivers = true;
    this.jdbcUrl = jdbcUrl;
  }

  /** Begin a transaction
   *
   * @throws CalFacadeException on fatal error
   */
  public void beginTransaction() {
    try {
      if (embeddedDrivers) {
        c = DriverManager.getConnection(jdbcUrl);
      } else {
        final Context ctx = new InitialContext();
        final DataSource ds = (DataSource)ctx.lookup(jdbcUrl);
        c = ds.getConnection();
      }

      c.setAutoCommit(false);
    } catch (final NamingException | SQLException e) {
      handleException(e);
    }
  }

  public void createQuery(final String sql) {
    try {
      if (ps != null) {
        ps.close();
      }
      this.query = query;
      paramMap = getParameters(query);

      psText = replaceParameters(query);
      ps = c.prepareStatement(psText);
    } catch (final SQLException se) {
      ps = null;
      handleException(se);
    }
  }

  public static Map<String, Param> getParameters(final String sql) {
    final var m = extractParams.matcher(sql);
    final Map<String, Param> ret = new HashMap<>();
    int pos = 1;

    while (m.find()) {
      ret.put(m.group(1),
              new Param(pos, m.group(1), null));
      pos++;
    }
    return ret;
  }

  public static String replaceParameters(final String sql) {
    final var m = replaceParams.matcher(sql);
    final StringBuilder sb = new StringBuilder();

    while (m.find()) {
      final var m2 = replaceParams.matcher(m.group(0));
      if (m2.find()) {
        final StringBuilder stringBuilder =
                new StringBuilder(m2.group(0));
        final String result =
                stringBuilder.replace(m2.start(1),
                                      m2.end(1), "?")
                             .toString();
        m.appendReplacement(sb, result);
      }
    }
    m.appendTail(sb);

    return sb.toString();
  }

  /**
   *
   * @return String value use to create PreparedStatement
   */
  public String getPsText() {
    return psText;
  }

  /** Set the named parameter with the given value
   *
   * @param parName     String parameter name
   * @param parVal      String parameter value
   * @throws CalFacadeException on fatal error
   */
  public void setString(final String parName, final String parVal) {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      ps.setString(fromName(parName), parVal);
    } catch (final Throwable t) {
      handleException(t);
    }
  }

  /** Set the named parameter with the given value
   *
   * @param parName     String parameter name
   * @param parVal      boolean parameter value
   * @throws CalFacadeException on fatal error
   */
  public void setBool(final String parName, final boolean parVal) {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      ps.setBoolean(fromName(parName), parVal);
    } catch (final Throwable t) {
      handleException(t);
    }
  }

  /** Set the named parameter with the given value
   *
   * @param parName     String parameter name
   * @param parVal      int parameter value
   * @throws CalFacadeException on fatal error
   */
  public void setInt(final String parName, final int parVal) {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      ps.setInt(fromName(parName), parVal);
    } catch (final Throwable t) {
      handleException(t);
    }
  }

  /** Set the named parameter with the given value
   *
   * @param parName     String parameter name
   * @param parVal      long parameter value
   * @throws CalFacadeException on fatal error
   */
  public void setLong(final String parName, final long parVal) {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      ps.setLong(fromName(parName), parVal);
    } catch (final Throwable t) {
      handleException(t);
    }
  }

  /* * Set the named parameter with the given value
   *
   * @param parName     String parameter name
   * @param parVal      Object parameter value
   * @throws CalFacadeException on fatal error
   * /
  public void setEntity(String parName, Object parVal) {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      ps.setEntity(fromName(parName), parVal);
    } catch (final Throwable t) {
      handleException(t);
    }
  }

  /* * Set the named parameter with the given value
   *
   * @param parName     String parameter name
   * @param parVal      Object parameter value
   * @throws CalFacadeException on fatal error
   * /
  public void setParameter(final String parName,
                           final Object parVal) {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      ps.setParameter(fromName(parName), parVal);
    } catch (final Throwable t) {
      handleException(t);
    }
  }

  /* * Set the named parameter with the given Collection
   *
   * @param parName     String parameter name
   * @param parVal      Collection parameter value
   * @throws CalFacadeException on fatal error
   * /
  public void setParameterList(final String parName,
                               final Collection<?> parVal) {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      ps.setParameterList(fromName(parName), parVal);
    } catch (final Throwable t) {
      handleException(t);
    }
  }

  /** Set the first result for a paged batch
   *
   * @param val      int first index
   * @throws CalFacadeException on fatal error
   */
  public void setFirstResult(final int val) {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      ps.setInt(fromName("offset"), val);
    } catch (final Throwable t) {
      handleException(t);
    }
  }

  /** Set the max number of results for a paged batch
   *
   * @param val      int max number
   * @throws CalFacadeException on fatal error
   */
  public void setMaxResults(final int val) {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      ps.setInt(fromName("limit"), val);
    } catch (final Throwable t) {
      handleException(t);
    }
  }

  /* * Return the single object resulting from the query.
   *
   * @return Object          retrieved object or null
   * @throws CalFacadeException on fatal error
   * /
  public Object getUnique() {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      return ps.uniqueResult();
    } catch (final Throwable t) {
      handleException(t);
      return null;  // Don't get here
    }
  }

  /* * Return a list resulting from the query.
   *
   * @return List from query
   * @throws CalFacadeException on fatal error
   * /
  public List<?> getList() {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      final List<?> l = ps.list();

      if (l == null) {
        return new ArrayList<>();
      }

      return l;
    } catch (final Throwable t) {
      handleException(t);
      return null;  // Don't get here
    }
  }

  /**
   * @return int number updated
   * @throws CalFacadeException on fatal error
   */
  public int executeUpdate() {
    if (exc != null) {
      // Didn't hear me last time?
      throw new CalFacadeException(exc);
    }

    try {
      if (ps == null) {
        throw new CalFacadeException("No query for execute update");
      }

      return ps.executeUpdate();
    } catch (final Throwable t) {
      handleException(t);
      return 0;  // Don't get here
    }
  }


  /** Rollback a transaction
   *
   * @throws CalFacadeException on fatal error
   */
  public void rollback() {
    if (rolledBack || (c == null)) {
      return;
    }

    try {
      if (ps != null) {
        try {
          ps.close();
        } catch (final SQLException ignored) {
        }
        
        ps = null;
      }
      
      c.rollback();
    } catch (final SQLException se) {
      throw new CalFacadeException(se);
    } finally {
      rolledBack = true;
    }
  }

  /** Did we rollback the transaction?
   *
   * @return boolean
   */
  public boolean rolledback() {
    return rolledBack;
  }

  /** Commit a transaction
   *
   * @throws CalFacadeException on fatal error
   */
  public void commit() {
    if (rolledBack || (c == null)) {
      return;
    }

    try {
      c.commit();
      if (ps != null) {
        try {
          ps.close();
        } catch (final SQLException ignored) {
        }

        ps = null;
      }
    } catch (final SQLException se) {
      handleException(se);
    }
  }

  protected void close() {
    if (c != null) {
      try {
        c.close();
      } catch (final SQLException se) {
        handleException(se);
      } finally {
        c = null;
        rolledBack = false;
      }
    }
  }
  
  protected void handleException(final Throwable t) {
    if (!rolledBack && (c != null)) {
      rollback();
      throw new CalFacadeException(t);
    }
  }

  private int fromName(final String name) {
    return 0;
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
