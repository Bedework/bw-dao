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

import java.io.Serializable;

/** Base type for a database entity. We require an id and the subclasses must
 * implement get/setHref, hashcode and compareTo.
 *
 * @author Mike Douglass
 * @version 1.0
 *
 * @param <T>
 */
public class FakeDaoUnversionedDbentity<T>
        implements Comparable<T>, Serializable {
  private int id = -1;

  private String href;

  /**
   * @param val
   */
  public void setId(final int val) {
    id = val;
  }

  /**
   * @return int id
   */
  @JsonIgnore
  public int getId() {
    return id;
  }

  /**
   * @return true if this entity is not saved.
   */
  public boolean unsaved() {
    return getId() == -1;
  }

  /**
   * Mark this entity as not saved.
   */
  public void markUnsaved() {
    setId(-1);
  }

  /* ==============================================================
   *                   Abstract methods
   * ============================================================== */

  /**
   *
   * @param val the href - may be ignored
   */
  public void setHref(final String val) {
    href = val;
  }

  /**
   *
   * @return non null unique href for the entity
   */
  public String getHref() {
    return href;
  }

  /* ====================================================================
   *                   Convenience methods
   * ==================================================================== */

  /** Add our stuff to the ToString object
   *
   * @param ts    ToString for result
   */
  protected void toStringSegment(final ToString ts) {
    ts.append("id", getId());
  }

  /* ==============================================================
   *                   Object methods
   * The following are required for a db object.
   * ============================================================== */

  @Override
  public int compareTo(final T o) {
    throw new RuntimeException("compareTo must be implemented for a db object");
  }

  @Override
  public int hashCode() {
    throw new RuntimeException("hashcode must be implemented for a db object");
  }

  /* We always use the compareTo method
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }

    return compareTo((T)obj) == 0;
  }
}
