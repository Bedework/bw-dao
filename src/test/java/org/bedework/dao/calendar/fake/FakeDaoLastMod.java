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

import com.fasterxml.jackson.annotation.JsonIgnore;

/** This is used to store the last modification times for some entities. We do
 * this to avoid the overhead and errors caused by versioning when not needed.
 *
 * <p>For example, we frequently update a calendar collections last mod when
 * events are changed. We need this lastmod to flag collection that have changed,
 * on the other hand we don't need the possibility of StaleStateExceptions.
 *
 * <p>The touch method will update this object only and save it avoiding most
 * of tha overhead.
 *
 * @author Mike Douglass
 * @version 1.0
 * @param <T>     type we are a lastmod for
 * @param <T1>    the actual class.
 */
public class FakeDaoLastMod<T extends FakeDaoEntity, T1>
        extends FakeDaoUnversionedDbentity<T1> {
  private int id = -1;

  private T dbEntity;

  /** UTC datetime */
  private String timestamp;

  /** Ensure uniqueness - lastmod only down to second.
   */
  private int sequence;

  /** No date constructor
   *
   * @param dbEntity related db entity
   */
  public FakeDaoLastMod(final T dbEntity) {
    this.dbEntity = dbEntity;
  }

  /** Constructor to set last mod
   * @param dbEntity related db entity
   * @param dt datetime
   */
  public FakeDaoLastMod(final T dbEntity, final String dt) {
    this(dbEntity);
    setTimestamp(dt);
  }

  /**
   * @param val the id
   */
  @Override
  public void setId(final int val) {
    id = val;
  }

  /**
   * @return int id
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * @param val related db entity
   */
  public void setDbEntity(final T val) {
    dbEntity = val;
  }

  /**
   * @return T
   */
  @JsonIgnore
  public T getDbEntity() {
    return dbEntity;
  }

  /**
   * @param val timestamp of change
   */
  public void setTimestamp(final String val) {
    timestamp = val;
  }

  /**
   * @return String lastmod
   */
  public String getTimestamp() {
    return timestamp;
  }

  /** Set the sequence
   *
   * @param val    sequence number
   */
  public void setSequence(final int val) {
    sequence = val;
  }

  /** Get the sequence
   *
   * @return int    the sequence
   */
  public int getSequence() {
    return sequence;
  }

  /**
   * @return true if this entity is not saved.
   */
  @Override
  public boolean unsaved() {
    return getId() == -1;
  }

  /* ==============================================================
   *                   Abstract methods
   * ============================================================== */

  /** Set the href - ignored
   *
   * @param val    String href
   */
  public void setHref(final String val) { }

  public String getHref() {
    return dbEntity.getHref();
  }

  /* ==============================================================
   *                   Convenience methods
   * ============================================================== */

  /** Add our stuff to the StringBuilder
   *
   * @param ts    for result
   */
  @Override
  protected void toStringSegment(final ToString ts) {
    ts.append("id", getId());
    ts.append("timestamp", getTimestamp());
    ts.append("sequence", getSequence());
  }

  /* ====================================================================
   *                   Object methods
   * The following are required for a db object.
   * ==================================================================== */

  @Override
  public int hashCode() {
    return getTimestamp().hashCode();
  }
}
