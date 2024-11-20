package org.bedework.dao.annotations;

import org.bedework.util.annotations.ElementVisitor;
import org.bedework.util.annotations.ProcessState;

import java.util.ArrayList;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static java.lang.String.format;

public class DaoProcessState extends ProcessState {
  public DaoProcessState(final ProcessingEnvironment env) {
    super(env);
  }

  @Override
  public ElementVisitor getVisitor() {
    return new ElementVisitor();
  }

  @Override
  public boolean startClass(final TypeElement el) {
    final var daoEntityAnn = el.getAnnotation(DaoEntity.class);
    if (daoEntityAnn == null) {

      return false;
    }

    if (debug()) {
      dumpElement("Class: ", el);
    }

    final var process =
            el.getAnnotation(DaoEntity.class) != null;

    if (!process) {
      return false;
    }

    // Emit start of entity handler class.
    final var className = el.getQualifiedName().toString();
    final var cw = getClassHandler(el.asType(),
                                   className + "DaoHelper");
    cw.generateClassStart();

    return true;
  }

  @Override
  public boolean shouldProcessSuperMethods(final TypeMirror tm) {
    return tm.toString().startsWith("org.bedework.dao");
  }

  @Override
  public void endClass(final TypeElement el) {
    final var cw = getClassHandler();
    if (cw != null) {
      cw.end();
    }
    closeClassHandler();
  }

  public void processMethod(final ExecutableElement el) {
    final var daoPropertyAnn =
            el.getAnnotation(DaoProperty.class);
    if (daoPropertyAnn == null) {
      return;
    }

    if (el.getParameters().size() != 1) {
      warn("Only a single parameter allowed");
      return;
    }

    if (debug()) {
      dumpElement("Executable: ", el);
    }

    final var split = getClassHandler().getSplitMethodName(el);

    if (!split.setter()) {
      return;
    }

    // Make getter and setter for field.

    final var setsig = getClassHandler().generateSignature(
            "set" + split.ucFieldName(),
            el.getParameters(),
            el.getReturnType(),
            el.getThrownTypes());
    getClassHandler().addMethod(setsig + format(
            """
                entity.set%s(getString("%s"));
              }
            """, split.ucFieldName(), split.fieldName()));


    final var getsig = getClassHandler().generateSignature(
            "get" + split.ucFieldName(),
            new ArrayList<>(),
            el.getParameters().get(0).asType(),
            el.getThrownTypes());
    getClassHandler().addMethod(getsig + format(
            """
                setString("%s", entity.get%s());
              }
            """, split.fieldName(), split.ucFieldName()));

  }
}
