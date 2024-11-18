package org.bedework.dao.annotations;

import org.bedework.util.annotations.ProcessState;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class DaoProcessState extends ProcessState {
  public DaoProcessState(final ProcessingEnvironment env) {
    super(env);
  }

  @Override
  public boolean startClass(final TypeElement el) {
    final var process =
            el.getAnnotation(DaoEntity.class) != null;

    if (!process) {
      return false;
    }

    // Emit start of entity handler class.

    return true;
  }
}
