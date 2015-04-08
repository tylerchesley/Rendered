package io.tylerchesley.rendered.samples;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.tylerchesley.rendered.RendererAdapter;
import io.tylerchesley.rendered.factory.DelegatingRendererFactory;
import io.tylerchesley.rendered.factory.RendererFactory;
import io.tylerchesley.rendered.matcher.BooleanViewTypeMatcher;
import io.tylerchesley.rendered.matcher.PropertyGetter;
import io.tylerchesley.rendered.matcher.ViewTypeMatcher;
import io.tylerchesley.rendered.renderer.Renderer;
import io.tylerchesley.rendered.renderer.ViewType;

public class SampleActivity extends ActionBarActivity {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sample);
        ButterKnife.inject(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new Divider(this, Divider.VERTICAL_LIST));

        final RendererFactory<Light> factory = DelegatingRendererFactory.from(
                LightDimmerViewRenderer.class,
                LightSwitchViewRenderer.class);

        final ViewTypeMatcher<Light> matcher = BooleanViewTypeMatcher
                .from(new PropertyGetter<Light, Boolean>() {
                    @Override
                    public Boolean get(Light item) {
                        return item.isDimmable;
                    }
                })
                .trueViewType(R.layout.list_item_light_dimmer)
                .falseViewType(R.layout.list_item_light_switch)
                .build();

        RendererAdapter.from(new LightProvider().getLights())
                .factory(factory)
                .matcher(matcher)
                .into(recyclerView);
    }

    @ViewType(R.layout.list_item_light_switch)
    public static final class LightSwitchViewRenderer extends Renderer<Light> {

        @InjectView(R.id.name)
        public TextView name;

        @InjectView(R.id.light_switch)
        public CompoundButton lightSwitch;

        public LightSwitchViewRenderer(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void bindView(Light light) {
            name.setText(light.name);
            lightSwitch.setChecked(light.isOn());
        }

    }

    @ViewType(R.layout.list_item_light_dimmer)
    public static final class LightDimmerViewRenderer extends Renderer<Light> {

        @InjectView(R.id.name)
        public TextView name;

        @InjectView(R.id.dimmer)
        public SeekBar dimmer;

        public LightDimmerViewRenderer(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void bindView(Light light) {
            name.setText(light.name);
            dimmer.setProgress(light.level);
        }

    }

}
