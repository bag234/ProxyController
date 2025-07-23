package org.mrbag.ProxyController.WShell.Command;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Component
public @interface Command {
	
	@AliasFor(annotation = Component.class)
	String value();
	
}
