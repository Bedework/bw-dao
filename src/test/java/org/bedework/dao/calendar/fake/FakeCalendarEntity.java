package org.bedework.dao.calendar.fake;

import org.bedework.dao.annotations.DaoEntity;
import org.bedework.dao.annotations.DaoProperty;
import org.bedework.util.misc.ToString;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

@DaoEntity(tableName = "Calendars")
public class FakeCalendarEntity
        extends FakeDaoShareableContainedDbentity<FakeCalendarEntity> {
  /** The internal name of the calendar
   */
  private String name;

  /** Path to this calendar - including this one.
   * The names concatenated with intervening '/'
   */
  private String path;

  /** The display name for the collection
   */
  private String summary;

  /** Some sort of description - may be null
   */
  private String description;

  /** This identifies an associated mailing list. Its actual value is set
   * by the mailer interface.
   */
  private String mailListId;

  /* The type of calendar */
  private int calType;

  /** Indicate unknown type */
  public final static int calTypeUnknown = -1;

  /** <em>Normal folder</em> Holds other collections */
  public final static int calTypeFolder = 0;

  /** <em>Normal calendar collection</em> holds events, todos etc */
  public final static int calTypeCalendarCollection = 1;

  /** <em>Trash</em> - don't use  */
  public final static int calTypeTrash = 2;

  /** <em>Deleted</em> Holds annotations which effectively delete events to
   * which the user does not have write access
   */
  public final static int calTypeDeleted = 3;

  /** <em>Busy</em> Used to store busy time - acts as a mask for freebusy */
  public final static int calTypeBusy = 4;

  /** <em>Inbox</em> Mostly used for notification of meeting requests */
  public final static int calTypeInbox = 5;

  /** <em>Outbox</em> Target for scheduling. Normally empty */
  public final static int calTypeOutbox = 6;

  /** <em>Alias</em>  */
  public final static int calTypeAlias = 7;

  /** <em>External subscription</em>  */
  public final static int calTypeExtSub = 8;

  /** <em>Resource collection</em> According to the CalDAV spec a collection may exist
   * inside a calendar collection but no calendar collection must be so
   * contained at any depth. (RFC 4791 Section 4.2) */
  public final static int calTypeResourceCollection = 9;

  /** <em>Notifications collection</em>  */
  public final static int calTypeNotifications = 10;

  /** <em>List of events</em>  */
  public final static int calTypeEventList = 11;

  /** <em>Vpoll entities</em>  */
  public final static int calTypePoll = 12;

  /** <em>Pending Inbox</em> Unprocessed meeting requests */
  public final static int calTypePendingInbox = 13;

  /** <em>managed attachments</em>  */
  public final static int calTypeAttachments = 14;

  /** <em>Tasks</em>  */
  public final static int calTypeTasks = 15;

  /** UTC datetime */
  private String created;

  private FakeDaoCollectionLastmod lastmod;

  private String filterExpr;

  /** Value of filter for a tombstoned collection
   */
  public static final String tombstonedFilter = "--TOMBSTONED--";

  /** Value of suffix on path for a tombstoned collection
   */
  public static final String tombstonedSuffix = "(--TOMBSTONED--)";

  private Set<FakeDaoCategory> categories = null;

  private String aliasUri;

  private boolean display = true;

  private boolean affectsFreeBusy;

  private boolean ignoreTransparency;

  private boolean unremoveable;

  private int refreshRate;

  private String lastRefresh;

  private String lastRefreshStatus;

  private String lastEtag;

  private String remoteId;

  private String remotePw;

  /** Constructor
   */
  public FakeCalendarEntity() {
    super();

    /* Set the lastmod and created */

    final var dt = new Date().toString();
    setLastmod(new FakeDaoCollectionLastmod(this, dt));
    setCreated(dt);
  }

  /** Set the name
   *
   * @param val    String name
   */
  @DaoProperty
  public void setName(final String val) {
    name = val;
  }

  /** Get the name
   *
   * @return String   name
   */
  public String getName() {
    return name;
  }

  /** Set the path
   *
   * @param val    String path
   */
  @DaoProperty
  public void setPath(final String val) {
    path = val;
    if (getLastmod() != null) {
      getLastmod().setPath(val);
    }
  }

  /** Get the path
   *
   * @return String   path
   */
  public String getPath() {
    return path;
  }

  /** Set the summary
   *
   * @param val    String summary
   */
  @DaoProperty
  public void setSummary(final String val) {
    summary = val;
  }

  /** Get the summary
   *
   * @return String   summary
   */
  public String getSummary() {
    if (summary == null) {
      return getName();
    }
    return summary;
  }

  /** Set the description
   *
   * @param val    description
   */
  @DaoProperty
  public void setDescription(final String val) {
    description = val;
  }

  /** Get the description
   *
   *  @return String   description
   */
  public String getDescription() {
    return description;
  }

  /** Set the mail list id
   *
   * @param val    String mail list id
   */
  @DaoProperty
  public void setMailListId(final String val) {
    mailListId = val;
  }

  /** Get the mail list id
   *
   *  @return String   mail list id
   */
  public String getMailListId() {
    return mailListId;
  }

  /** Set the type
   *
   * @param val    type
   */
  @DaoProperty
  public void setCalType(final int val) {
    calType = val;
  }

  /** Get the type
   *
   *  @return int type
   */
  public int getCalType() {
    return calType;
  }

  /**
   * @param val - the created date
   */
  @DaoProperty
  public void setCreated(final String val) {
    created = val;
  }

  /**
   * @return String created
   */
  public String getCreated() {
    return created;
  }

  /**
   * @param val the lastmod
   */
  @DaoProperty
  public void setLastmod(final FakeDaoCollectionLastmod val) {
    lastmod = val;
  }

  /**
   * @return FakeDaoCollectionLastmod lastmod
   */
  public FakeDaoCollectionLastmod getLastmod() {
    return lastmod;
  }

  /**
   * @param val - the filter expression
   */
  @DaoProperty
  public void setFilterExpr(final String val) {
    filterExpr = val;
  }

  /**
   * @return String FilterExpr
   */
  public String getFilterExpr() {
    return filterExpr;
  }

  /** Set the refresh rate in seconds
   *
   * @param val    type
   */
  @DaoProperty
  public void setRefreshRate(final int val) {
    refreshRate = val;
  }

  /** Get the refresh rate in seconds
   *
   *  @return String   description
   */
  public int getRefreshRate() {
    return refreshRate;
  }

  /**
   * @param val - the value
   */
  @DaoProperty
  public void setLastRefresh(final String val) {
    lastRefresh = val;
  }

  /**
   *
   * @return String lastRefresh
   */
  public String getLastRefresh() {
    return lastRefresh;
  }

  /**
   * @param val HTTP status or other appropriate value
   */
  @DaoProperty
  public void setLastRefreshStatus(final String val) {
    lastRefreshStatus = val;
  }

  /**
   * @return String lastRefreshStatus
   */
  public String getLastRefreshStatus() {
    return lastRefreshStatus;
  }

  /**
   * @param val - the value
   */
  @DaoProperty
  public void setLastEtag(final String val) {
    lastEtag = val;
  }

  /**
   * @return String lastRefresh
   */
  public String getLastEtag() {
    return lastEtag;
  }

  /**
   *
   * @param val If non-null we have a remote id and encrypted password
   */
  @DaoProperty
  public void setRemoteId(final String val) {
    remoteId = val;
  }

  /**
   * @return String remoteId
   */
  public String getRemoteId() {
    return remoteId;
  }

  /**
   *
   * @param val If non-null the encrypted password
   */
  @DaoProperty
  public void setRemotePw(final String val) {
    remotePw = val;
  }

  /**
   * @return String encrypted password
   */
  public String getRemotePw() {
    return remotePw;
  }

  /* ==============================================================
   *               CategorisedEntity interface methods
   * ============================================================== */

  @DaoProperty
  public void setCategories(final Set<FakeDaoCategory> val) {
    categories = val;
  }

  public Set<FakeDaoCategory> getCategories() {
    return categories;
  }

  public boolean addCategory(final FakeDaoCategory val) {
    Set<FakeDaoCategory> cats = getCategories();
    if (cats == null) {
      cats = new TreeSet<>();
      setCategories(cats);
    }

    if (!cats.contains(val)) {
      cats.add(val);
      return true;
    }

    return false;
  }

  public boolean removeCategory(final FakeDaoCategory val) {
    final Set<FakeDaoCategory> cats = getCategories();
    if (cats == null) {
      return false;
    }

    return cats.remove(val);
  }

  public boolean hasCategory(final FakeDaoCategory val) {
    final Set<FakeDaoCategory> cats = getCategories();
    if (cats == null) {
      return false;
    }

    return cats.contains(val);
  }

  public Set<FakeDaoCategory> copyCategories() {
    return new TreeSet<>(getCategories());
  }

  public Set<FakeDaoCategory> cloneCategories() {
    if (getCategories() == null) {
      return null;
    }
    final TreeSet<FakeDaoCategory> ts = new TreeSet<>();

    for (final var cat: getCategories()) {
      ts.add((FakeDaoCategory)cat.clone());
    }

    return ts;
  }

  /**
   *
   * @param val   boolean true if the calendar is to be displayed
   */
  @DaoProperty
  public void setDisplay(final boolean val) {
    display = val;
  }

  /**
   *
   * @return boolean  true if the calendar is to be displayed
   */
  public boolean getDisplay() {
    return display;
  }

  /**
   *
   *  @param val    true if the calendar takes part in free/busy calculations
   */
  @DaoProperty
  public void setAffectsFreeBusy(final boolean val) {
    affectsFreeBusy = val;
  }

  /**
   *
   *  @return boolean    true if the calendar takes part in free/busy calculations
   */
  public boolean getAffectsFreeBusy() {
    return affectsFreeBusy;
  }

  /** Set the ignoreTransparency flag
   *
   *  @param val    true if we ignore tranparency in free/busy calculations
   */
  @DaoProperty
  public void setIgnoreTransparency(final boolean val) {
    ignoreTransparency = val;
  }

  /** Do we ignore transparency?
   *
   *  @return boolean    true for ignoreTransparency
   */
  public boolean getIgnoreTransparency() {
    return ignoreTransparency;
  }

  /**
   *
   * @param val   boolean true if the calendar is unremoveable
   */
  @DaoProperty
  public void setUnremoveable(final boolean val) {
    unremoveable = val;
  }

  /**
   *
   * @return boolean  true if the calendar is unremoveable
   */
  public boolean getUnremoveable() {
    return unremoveable;
  }

  /* ==============================================================
   *                   db entity methods
   * ============================================================== */

  @Override
  @DaoProperty
  public void setHref(final String val) {
    setPath(val);
  }

  @Override
  public String getHref() {
    return getPath();
  }

  /* ==============================================================
   *                   Object methods
   * ============================================================== */

  @Override
  public int compareTo(final FakeCalendarEntity that) {
    if (that == this) {
      return 0;
    }

    if (that == null) {
      return -1;
    }

    return getPath().compareTo(that.getPath());
  }

  @Override
  public int hashCode() {
    if (getPath() == null) {
      return 1;
    }
    return getPath().hashCode();
  }

  @Override
  public String toString() {
    final ToString ts = new ToString(this);

    toStringSegment(ts);
    ts.append("name", getName());
    ts.append("path", getPath());
    ts.append("displayName", getSummary());
    ts.newLine();
    ts.append("description", getDescription());
//    ts.append("mailListId", getMailListId());
    ts.append("calType", getCalType());

    /* Forces fetch
    if (hasChildren()) {
      sb.append(",\nchildren(");

      boolean donech = false;

      for (BwCalendar ch: getChildren()) {
        if (!donech) {
          donech = true;
        } else {
          sb.append(",\n");
        }

        sb.append(ch.getPath());
      }
      sb.append(")");
    }
    */

    ts.append("created", getCreated());
    ts.append("lastmod", getLastmod());

    if (getCategories() != null) {
      ts.append("categories",  getCategories());
    }

    return ts.toString();
  }

  @Override
  public Object clone() {
    final FakeCalendarEntity cal = shallowClone();

    cal.setCategories(cloneCategories());

    return cal;
  }

  public FakeCalendarEntity shallowClone() {
    final FakeCalendarEntity cal = new FakeCalendarEntity();

    super.copyTo(cal);

    cal.setName(getName());
    cal.setPath(getPath());

    cal.setSummary(getSummary());
    cal.setDescription(getDescription());
    cal.setMailListId(getMailListId());
    cal.setCalType(getCalType());
    cal.setCreated(getCreated());

    final FakeDaoCollectionLastmod lm = (FakeDaoCollectionLastmod)getLastmod().clone();
    cal.setLastmod(lm);

    cal.setDisplay(getDisplay());
    cal.setAffectsFreeBusy(getAffectsFreeBusy());
    cal.setIgnoreTransparency(getIgnoreTransparency());
    cal.setUnremoveable(getUnremoveable());
    cal.setRefreshRate(getRefreshRate());
    cal.setLastRefresh(getLastRefresh());
    cal.setLastEtag(getLastEtag());
    cal.setFilterExpr(getFilterExpr());

    return cal;
  }
}
