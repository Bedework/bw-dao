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
  private int daoVersion;

  public DaoProcessState(final ProcessingEnvironment env) {
    super(env);
  }

  @Override
  public void option(final String name,
                     final String value) {
    if ("daoVersion".equals(name)) {
      daoVersion = Integer.parseInt(value);
    }
  }

  @Override
  public ElementVisitor getVisitor() {
    return new ElementVisitor();
  }

  public int getDaoVersion() {
    return daoVersion;
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
    if ((daoPropertyAnn == null) ||
            !testVersion(daoPropertyAnn)) {
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

    /* For each setter method in the entity we generate:
        - A method to get the value from the result
        - A method to set the value in a prepared statement
        - Info to build the complete population of the entity
        - Info to build the complete update of the entity
     */

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

  public enum VersionModifier {
    lt, lte, gt, gte, eq
  }
  public record VersionSpecifier(
          int version,
          VersionModifier modifier) {}

  public VersionSpecifier getVersionSpecifier(final String val) {
    final int pos;
    final VersionModifier mod;

    if (val.startsWith("<")) {
      mod = VersionModifier.lt;
      pos = 1;
    } else if (val.startsWith(">")) {
      mod = VersionModifier.gt;
      pos = 1;
    } else if (val.startsWith("<=")) {
      mod = VersionModifier.lte;
      pos = 2;
    } else if (val.startsWith(">=")) {
      mod = VersionModifier.gte;
      pos = 2;
    } else {
      mod = VersionModifier.eq;
      pos = 0;
    }

    final int versionNumber = Integer.parseInt(val.substring(pos));
    if (versionNumber < 0) {
      throw new IllegalArgumentException(
              format("Version < 0: %s", val));
    }

    return new VersionSpecifier(versionNumber, mod);
  }

  /**
   *
   * @param ann for property
   * @return true to process method
   */
  public boolean testVersion(final DaoProperty ann) {
    final var annV = ann.daoVersion();

    if ((daoVersion <= 0) || annV.isEmpty()) {
      return true;
    }

    final var vspec = getVersionSpecifier(annV);
    return switch (vspec.modifier) {
      case lt -> vspec.version < daoVersion;
      case lte -> vspec.version <= daoVersion;
      case gt -> vspec.version > daoVersion;
      case gte -> vspec.version >= daoVersion;
      case eq -> vspec.version == daoVersion;
    };
  }
}
