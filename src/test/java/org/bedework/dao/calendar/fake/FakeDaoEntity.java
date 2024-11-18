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

import com.fasterxml.jackson.annotation.JsonIgnore;

/** Base type for a database entity. We require an id and the subclasses must
 * implement hashcode and compareTo.
 *
 * @author Mike Douglass
 * @version 1.0
 *
 * @param <T>
 */
public abstract class FakeDaoEntity<T>
        extends FakeDaoUnversionedDbentity<T> {
  /* db version number */
  private int seq;

  /** No-arg constructor
   *
   */
  public FakeDaoEntity() {
  }

  /** Set the seq for this entity
   *
   * @param val    int seq
   */
  public void setSeq(final int val) {
    seq = val;
  }

  /** Get the entity seq
   *
   * @return int    the entity seq
   */
  @JsonIgnore
  public int getSeq() {
    return seq;
  }
}
