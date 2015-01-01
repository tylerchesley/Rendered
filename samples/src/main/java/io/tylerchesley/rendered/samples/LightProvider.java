package io.tylerchesley.rendered.samples;

import java.util.ArrayList;
import java.util.List;

public class LightProvider {

    public List<Light> getLights() {
        final List<Light> lights = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            final Light light = new Light();
            light.name = "Light " + (i + 1);
            light.isDimmable = Math.random() > 0.5;
            light.level = (int) (light.isDimmable ? Math.random() * 100 : (Math.random() > 0.5 ? 0 : 100));
            lights.add(light);
        }
        return lights;
    }

}
