package com.kozitski.pufar.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Annotation AspectValid.
 * Developed for marking methods, arguments
 * of witch marked with annotations from com.kozitski.pufar.validation package.
 * Without this annotations arguments of method
 * will not processed by AnnotationValidation engine
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectValid {

}
