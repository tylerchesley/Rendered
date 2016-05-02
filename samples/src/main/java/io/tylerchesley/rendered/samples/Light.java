package io.tylerchesley.rendered.samples;

public class Light {

    public String name;
    public boolean isDimmable;
    public int level;

    public boolean isOn() {
        return level > 0;
    }

}
