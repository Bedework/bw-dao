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

import org.bedework.base.ToString;

/** Base class for database entities with an owner.
 *
 * @author Mike Douglass
 * @version 1.0
 *
 * @param <T>
 */
public abstract class FakeDaoOwnedDbentity<T> extends FakeDaoEntity<T>
        implements FakeOwnedEntity {
  private String ownerHref;

  private Boolean publick;

  /** No-arg constructor
   *
   */
  public FakeDaoOwnedDbentity() {
    super();
  }

  @Override
  public void setOwnerHref(final String val) {
    ownerHref = val;
  }

  @Override
  public String getOwnerHref() {
    return ownerHref;
  }

  @Override
  public void setPublick(final Boolean val) {
    publick = val;
  }

  @Override
  public Boolean getPublick() {
    return publick;
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
    ts.append("owner", getOwnerHref());
    ts.append("publick", getPublick());
  }

  /** Copy this objects fields into the parameter. Don't clone many of the
   * referenced objects
   *
   * @param val to copy to
   */
  public void shallowCopyTo(final FakeDaoOwnedDbentity<?> val) {
    val.setOwnerHref(getOwnerHref());
    val.setPublick(getPublick());

    // JUst be helpful
    //     val.setOwnerEnt(getOwnerEnt());
  }

  /** Copy this objects fields into the parameter
   *
   * @param val to copy to
   */
  public void copyTo(final FakeDaoOwnedDbentity<?> val) {
    val.setOwnerHref(getOwnerHref());

    //CLONEif (getOwner() != null) {
    //  val.setOwner((BwUser)getOwner().clone());
    //}
    val.setPublick(getPublick());

    // JUst be helpful
    //  val.setOwnerEnt(getOwnerEnt());
  }
}
