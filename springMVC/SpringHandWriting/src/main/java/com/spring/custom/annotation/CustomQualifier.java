package com.spring.custom.annotation;

public @interface CustomQualifier {
	String value() default "";
}
