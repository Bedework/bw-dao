package org.bedework.dao.annotations;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import static java.lang.String.format;

@SupportedAnnotationTypes(
        "com.baeldung.annotation.processor.BuilderProperty")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class DaoAnnotationProcessor extends AbstractProcessor {

  @Override
  public boolean process(final Set<? extends TypeElement> annotations,
                         final RoundEnvironment renv) {
    for (final var annotation: annotations) {
      final Set<? extends Element> annotatedElements =
              renv.getElementsAnnotatedWith(annotation);

      final var annotatedMethods =
              annotatedElements
                      .stream()
                      .collect(
                              Collectors.partitioningBy(element -> (
                                      (ExecutableType)element.asType())
                                      .getParameterTypes().size() == 1 && element.getSimpleName().toString().startsWith("set")));

      final var setters = annotatedMethods.get(true);
      final var otherMethods = annotatedMethods.get(false);

      otherMethods.forEach(
              element -> processingEnv.getMessager()
                                      .printMessage(
              Diagnostic.Kind.ERROR, "@BuilderProperty must be applied to a setXxx method with a single argument", element));

      if (setters.isEmpty()) {
        continue;
      }

      final String className = ((TypeElement)setters.get(0)
                                                    .getEnclosingElement())
              .getQualifiedName().toString();

      final var setterMap =
              setters.stream()
                     .collect(Collectors.toMap(
                             setter -> setter.getSimpleName().toString(),
                             setter -> ((ExecutableType)setter.asType())
                                     .getParameterTypes()
                                     .get(0).toString()));

      try {
        makeDaoHelperClass(className, setterMap);
      } catch (final IOException e) {
        e.printStackTrace();
        return false;
      }

    }

    return true;
  }

  private void makeDaoHelperClass(final String className,
                                  final Map<String, String> setterMap) throws IOException {

    final int pos = className.lastIndexOf('.');
    if (pos < 0) {
      throw new IllegalArgumentException("Invalid class name: " +
                                                 className);
    }

    final var packageName = className.substring(0, pos);

    final var simpleClassName = className.substring(pos + 1);
    final var daoHelperClassName = className + "DaoHelper";
    final var daoHelperSimpleClassName =
            daoHelperClassName.substring(pos + 1);

    final JavaFileObject outFile =
            processingEnv.getFiler()
                         .createSourceFile(daoHelperClassName);
    try (final PrintWriter out =
                 new PrintWriter(outFile.openWriter())) {
      out.println(format("package %s;", packageName));
      out.println(
              format("public class %s {", daoHelperSimpleClassName));
      out.println(
              format("  private final %s entity; ", simpleClassName));
      out.println();
      out.println(format("  public %s(final %s entity) {",
                         daoHelperClassName, simpleClassName));
      out.println("    this.entity = entity;");
      out.println("  }");

      /* Iterate through methods to create fetch and put
         prepared statement strings
       */

      final var fetcher = new StringBuilder();
      final var putter = new StringBuilder();
      setterMap.forEach((methodName, argumentType) -> {

      });

      setterMap.forEach((methodName, argumentType) -> {
        out.print(format("  public %s %s(final %s val) {",
                         daoHelperSimpleClassName, methodName,
                         argumentType));
        out.print("        object.");
        out.print(methodName);
        out.println("(value);");
        out.println("        return this;");
        out.println("    }");
        out.println();
      });

      out.println("}");

    }
  }
}