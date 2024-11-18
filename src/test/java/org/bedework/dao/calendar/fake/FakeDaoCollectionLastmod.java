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

/** Concrete class so we can map this in hibernate
 *
 * @author Mike Douglass
 * @version 1.0
 */
public class FakeDaoCollectionLastmod
        extends FakeDaoLastMod<FakeCalendarEntity, FakeDaoCollectionLastmod> {
  /* Set from the entity value */
  private String path;

  /** No arg constructor for hibernate
   *
   */
  public FakeDaoCollectionLastmod() {
    super(null);
  }

  /** No date constructor
   *
   * @param dbEntity collection
   */
  public FakeDaoCollectionLastmod(final FakeCalendarEntity dbEntity) {
    super(dbEntity);

    if (dbEntity != null) {
      setPath(dbEntity.getPath());
    } else {
      setPath(null);
    }
  }

  /** Constructor to set last mod
   * @param dbEntity collection
   * @param dt Date lastmod
   */
  public FakeDaoCollectionLastmod(final FakeCalendarEntity dbEntity,
                                  final String dt) {
    super(dbEntity, dt);

    if (dbEntity != null) {
      setPath(dbEntity.getPath());
    } else {
      setPath(null);
    }
  }

  /**
   * @param val collection
   */
  @Override
  public void setDbEntity(final FakeCalendarEntity val) {
    super.setDbEntity(val);

    if (val != null) {
      setPath(val.getPath());
    } else {
      setPath(null);
    }
  }

  /** Set the path
   *
   * @param val    String path
   */
  public void setPath(final String val) {
    path = val;
  }

  /** Get the path
   *
   * @return String   path
   */
  public String getPath() {
    return path;
  }

  @Override
  public int compareTo(final FakeDaoCollectionLastmod that) {
    if (this == that) {
      return 0;
    }

    final int res = getTimestamp().compareTo(that.getTimestamp());

    if (res != 0) {
      return res;
    }

    return getPath().compareTo(that.getPath());
  }

  @Override
  public Object clone() {
    final FakeDaoCollectionLastmod lm = new FakeDaoCollectionLastmod();

    lm.setPath(getPath());
    lm.setSequence(getSequence());

    return lm;
  }
}
