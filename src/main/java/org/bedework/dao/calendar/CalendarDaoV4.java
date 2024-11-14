package org.bedework.dao.calendar;

import org.bedework.util.logging.Logged;

public interface CalendarDaoV4 extends Logged {
  /* ==============================================================
   *                   Collections methods
   * ============================================================== */

/** Until we have a synch mechanism in place this provides a partial
 * mechanism. The returned token is equivalent to the Apple ctag and, for the
 * time being, is also the etag value.
 *
 * @author douglm
 *
 * @return String token
 */
String getSynchInfo(final String path);
}
