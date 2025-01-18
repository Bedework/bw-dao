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
import org.bedework.util.misc.Uid;

/** Base for those classes that can be a property of an event and are all
 * treated in the same manner, being Category, Location and Sponsor.
 *
 * <p>Each has a single field which together with the owner makes a unique
 * key and all operations on those classes are the same.
 *
 * @author Mike Douglass
 * @version 1.0
 *
 * @param <T>
 */
public abstract class FakeEntityProperty<T> extends
        FakeDaoShareableContainedDbentity<T> {
  private String uid;
  private String status;

  /** Constructor
   *
   */
  public FakeEntityProperty() {
    super();
  }

  public final static String statusDeleted = "deleted";

  /**
   * @return String
   */
  public String getStatus() {
    return status;
  }

  public void setStatus(final String val) {
    status = val;
  }

  /**
   * @return String
   */
  public boolean getDeleted() {
    return statusDeleted.equals(getStatus());
  }

  /** Set the uid
   *
   * @param val    String uid
   */
  public void setUid(final String val) {
    uid = val;
  }

  /** Get the uid
   *
   * @return String   uid
   */
  public String getUid() {
    return uid;
  }

  /**
   * fill in the uid.
   *
   * @return this object
   */
  public FakeEntityProperty<T> initUid() {
    setUid(Uid.getUid());

    return this;
  }

  @Override
  public String getColPath(){
    return super.getColPath();
  }

  @Override
  public String getHref(){
    return super.getHref();
  }

  /* ==============================================================
   *                   Convenience methods
   * ============================================================== */

  /** Copy this objects fields into the parameter
   *
   * @param val to copy to
   */
  public void copyTo(final FakeEntityProperty<?> val) {
    val.setUid(getUid());
  }

  @Override
  protected void toStringSegment(final ToString ts) {
    super.toStringSegment(ts);
    ts.append("uid", getUid());
  }
}
