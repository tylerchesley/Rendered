package io.tylerchesley.rendered.samples;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.security.InvalidParameterException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.tylerchesley.rendered.Renderer;
import io.tylerchesley.rendered.RendererAdapter;
import io.tylerchesley.rendered.RendererFactory;

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

        RendererAdapter.from(new LightProvider().getLights())
                .factory(new LightsRendererFactory())
                .into(recyclerView);
    }

    public static final class LightsRendererFactory implements RendererFactory<Light> {

        @Override
        public Renderer<Light> createRenderer(ViewGroup parent, int viewType) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            switch (viewType) {

                case R.id.type_list_item_light_dimmer:
                    return new LightDimmerViewRenderer(inflater
                            .inflate(R.layout.list_item_light_dimmer, parent, false));

                case R.id.type_list_item_light_switch:
                    return new LightSwitchViewRenderer(inflater
                            .inflate(R.layout.list_item_light_switch, parent, false));

                default:
                    throw new InvalidParameterException("Invalid view type " + viewType);

            }
        }

        @Override
        public int getViewType(Light item) {
            return item.isDimmable ? R.id.type_list_item_light_dimmer
                    : R.id.type_list_item_light_switch;
        }

    }

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
