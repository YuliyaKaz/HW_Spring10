package org.example.app.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Пример маркерной аннотации, не содержащей никаких членов
 */

@Target(ElementType.METHOD) //аннотация применяется к методам
@Retention(RetentionPolicy.RUNTIME) // время жизни аннотации = время выполнения программы
public @interface TrackUserAction {
}
