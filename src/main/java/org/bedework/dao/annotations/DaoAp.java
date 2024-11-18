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
package org.bedework.dao.annotations;

import org.bedework.util.annotations.AnnotationProcessor;
import org.bedework.util.annotations.ElementVisitor;
import org.bedework.util.annotations.ProcessState;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 * @author douglm
 *
 */
@SupportedAnnotationTypes(value= {
        "org.bedework.dao.annotations.DaoEntity",
        "org.bedework.dao.annotations.DaoProperty"})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class DaoAp extends AnnotationProcessor {
  private DaoProcessState pstate;

  @Override
  public ProcessState getState(final ProcessingEnvironment env) {
    if (pstate == null) {
      pstate = new DaoProcessState(env);
    }
    return pstate;
  }

  @Override
  public ElementVisitor getVisitor() {
    return new ElementVisitor();
  }
}