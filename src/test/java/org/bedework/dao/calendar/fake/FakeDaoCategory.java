/* ********************************************************************
    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.
*/
package org.bedework.dao.calendar.fake;

import org.bedework.util.misc.ToString;
import org.bedework.util.misc.Util;

import java.util.Comparator;

/** A category in Bedework. This value object does no consistency or validity
 * checking
 *.
 *  @version 1.0
 */
public class FakeDaoCategory extends FakeEntityProperty<FakeDaoCategory>
        implements Comparator<FakeDaoCategory> {
  private String word;
  private String description;

  /* Not persisted in the db */

  private String name;

  /** Constructor
   */
  public FakeDaoCategory() {
    super();
  }

  /** Set the word
   *
   * @param val    BwString word
   */
  public void setWord(final String val) {
    word = val;
  }

  /** Get the word
   *
   * @return BwString   word
   */
  public String getWord() {
    return word;
  }

  /** Delete the category's keyword - this must be called rather than setting
   * the value to null.
   *
   */
  public void deleteWord() {
    setWord(null);
  }

  /** Set the category's description
   *
   * @param val    String category's description
   */
  public void setDescription(final String val) {
    description = val;
  }

  /** Get the category's description
   *
   *  @return BwString   category's description
   */
  public String getDescription() {
    return description;
  }

  /** Delete the category's description - this must be called rather than setting
   * the value to null.
   *
   */
  public void deleteDescription() {
    setDescription(null);
  }

  /** Set the category's name (indexer)
   *
   * @param val    String name
   */
  public void setName(final String val) {
    name = val;
  }

  /** Get the category's name
   *
   *  @return name
   */
  public String getName() {
    return name;
  }

  /* ==============================================================
   *                        Convenience methods
   * ============================================================== */

  /**
   *
   * @param cat the other one
   * @return true if anything changed
   */
  public boolean updateFrom(final FakeDaoCategory cat) {
    boolean changed = false;

    if (!getWord().equals(cat.getWord())) {
      setWord(cat.getWord());
      changed = true;
    }

    if (Util.cmpObjval(getDescription(), cat.getDescription()) != 0) {
      setDescription(cat.getDescription());
      changed = true;
    }

    return changed;
  }

  /* ==============================================================
   *                        Object methods
   * ============================================================== */

  @Override
  public int compare(final FakeDaoCategory o1, final FakeDaoCategory o2) {
    return o1.compareTo(o2);
  }

  @Override
  public int compareTo(final FakeDaoCategory that) {
    if (that == null) {
      return -1;
    }

    return Util.cmpObjval(getUid(), that.getUid());
  }

  @Override
  public int hashCode() {
    return getUid().hashCode();
  }

  @Override
  public String toString() {
    final ToString ts = new ToString(this);

    toStringSegment(ts);
    ts.append("word", getWord());

    return ts.toString();
  }

  @Override
  public Object clone() {
    final FakeDaoCategory cat = new FakeDaoCategory();

    super.copyTo(cat);

    cat.setWord(getWord());
    cat.setDescription(getDescription());
    cat.setName(getName());
    cat.setStatus(getStatus());

    return cat;
  }
}
