package hu.webvalto.user;

import hu.webvalto.database.Column;
import lombok.Data;
import org.omg.CosNaming.IstringHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    String name() default "";
}
