package org.bedework.dao.common;

public class DaoException extends RuntimeException {

  /** Constructor
   *
   */
  public DaoException() {
    super();
  }

  /**
   * @param t exception
   */
  public DaoException(final Throwable t) {
    super(t);
  }

  /**
   * @param s message
   */
  public DaoException(final String s) {
    super(s);
  }
}
