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

/** Base class for shareable database entities.
 *
 * @author Mike Douglass
 * @version 1.0
 *
 * @param <T>
 */
public abstract class FakeDaoShareableDbentity<T>
        extends FakeDaoOwnedDbentity<T>
        implements FakeShareableEntity {
  private String creatorHref;

  /** Encoded access rights
   */
  private String access;

  /** No-arg constructor
   *
   */
  public FakeDaoShareableDbentity() {
    super();
  }

  @Override
  public void setCreatorHref(final String val) {
    creatorHref = val;
  }

  @Override
  public String getCreatorHref() {
    return creatorHref;
  }

  @Override
  public void setAccess(final String val) {
    access = val;
  }

  @Override
  public String getAccess() {
    return access;
  }

  /* ==============================================================
   *                   Convenience methods
   * ============================================================== */

  /** Add our stuff to the ToString object
   *
   * @param ts    ToString for result
   */
  @Override
  protected void toStringSegment(final ToString ts) {
    super.toStringSegment(ts);
    ts.newLine();
    ts.append("creator", getCreatorHref());
    ts.append("access", getAccess());
  }

  /** Copy this objects fields into the parameter. Don't clone many of the
   * referenced objects
   *
   * @param val Object to copy into
   */
  public void shallowCopyTo(final FakeDaoShareableDbentity<?> val) {
    super.shallowCopyTo(val);
    val.setCreatorHref(getCreatorHref());
    val.setAccess(getAccess());
  }

  /** Copy this objects fields into the parameter
   *
   * @param val Object to copy into
   */
  public void copyTo(final FakeDaoShareableDbentity<?> val) {
    super.copyTo(val);
    val.setCreatorHref(getCreatorHref());
    // CLONE val.setCreator((BwUser)getCreator().clone());
    val.setAccess(getAccess());
  }
}
