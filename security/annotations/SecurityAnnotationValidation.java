package br.com.branetlogistica.core.security.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityAnnotationValidation {
    public boolean validUser() default true ;
    public boolean validCoastCenter() default true;
    
    
}