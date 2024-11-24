package org.bedework.dao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface DaoProperty {
  /** Can be used to specify the version the annotated property is
   * used for. A version MUST be an integer value preceded by<ul>
   *   <li>Nothing for a specific version e.g. "4"</li>
   *   <li>"&lt" for a version less than the value e.g. "&lt;4"</li>
   *   <li>"&lt=" for a version less or equal to the value e.g. "&lt;=4"</li>
   *   <li>">" for a version greater than the value e.g. ">4"</li>
   *   <li>">=" for a version greater or equal to the value e.g. ">=4"</li>
   *   <li>"!" for a version not equal to the value e.g. "!4"</li>
   * </ul>
   *
   * @return empty for no version.
   */
  String daoVersion() default "";

  /**
   * @return true if the property is represented in the db by a
   * number of fields
   */
  boolean compound() default false;

  /**
   * @return Column name for this property. Used as a prefix for
   * compound properties
   */
  String column() default "";

  /** Each value is of the form
     "name1=name2"
     Name1 is the default or generated column name, name2 is the actual
   one
     <p>
     e.g. if column="start_" and the compound field date has default column date, then the default column name would be "start_date".
     <p>
     However, an alias value of "start_date=cal_start_date" will override that
     column name

     @return empty for no aliases
   */
  String[] columnAliases() default {};
}
