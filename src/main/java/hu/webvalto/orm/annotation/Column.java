package hu.webvalto.orm.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    public String name() default "";
    boolean isPrimaryKey() default false;
}
