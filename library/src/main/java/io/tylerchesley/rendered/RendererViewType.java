package io.tylerchesley.rendered;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface RendererViewType {

    int type();

}
