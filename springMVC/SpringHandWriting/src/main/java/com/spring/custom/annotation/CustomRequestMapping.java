package com.spring.custom.annotation;

public @interface CustomRequestMapping {
	String value() default "";
}
