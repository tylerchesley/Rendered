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
import io.tylerchesley.rendered.RendererViewHolder;
import io.tylerchesley.rendered.SimpleRenderer;

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
                .add(new LightSwitch())
                .add(new LightDimmerRenderer())
                .into(recyclerView);
    }

    public static final class LightSwitch extends SimpleRenderer<Light> {

        public LightSwitch() {
            super(R.layout.list_item_light_switch, R.id.type_list_item_light_switch);
        }

        @Override
        protected RendererViewHolder<Light> onCreateViewHolder(View view) {
            return new LightSwitchViewHolder(view);
        }

        @Override
        public boolean isFor(Light light) {
            return !light.isDimmable;
        }

        public final class LightSwitchViewHolder extends RendererViewHolder<Light> {

            @InjectView(R.id.name)
            public TextView name;

            @InjectView(R.id.light_switch)
            public CompoundButton lightSwitch;

            public LightSwitchViewHolder(View itemView) {
                super(itemView);
                ButterKnife.inject(this, itemView);
            }

            @Override
            public void bindView(Light light) {
                name.setText(light.name);
                lightSwitch.setChecked(light.isOn());
            }

        }

    }

    public static final class LightDimmerRenderer extends SimpleRenderer<Light> {

        public LightDimmerRenderer() {
            super(R.layout.list_item_light_dimmer, R.id.type_list_item_light_dimmer);
        }

        @Override
        protected RendererViewHolder<Light> onCreateViewHolder(View view) {
            return new LightDimmerViewHolder(view);
        }

        @Override
        public boolean isFor(Light light) {
            return light.isDimmable;
        }

        public final class LightDimmerViewHolder extends RendererViewHolder<Light> {

            @InjectView(R.id.name)
            public TextView name;

            @InjectView(R.id.dimmer)
            public SeekBar dimmer;

            public LightDimmerViewHolder(View itemView) {
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

}
