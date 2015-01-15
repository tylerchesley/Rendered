package io.tylerchesley.rendered.renderer;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface Layout {

    int value();

}
