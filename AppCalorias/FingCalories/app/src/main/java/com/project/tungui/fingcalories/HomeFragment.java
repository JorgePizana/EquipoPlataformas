package com.project.tungui.fingcalories;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.DecoDrawEffect;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

public class HomeFragment extends Fragment implements SensorEventListener {

    SensorManager sensorManager;
    TextView tv_steps;
    DecoView arcView;
    View view;

    private int mBackIndex;
    private int mSeries1Index;

    public static float evsteps;
    public static int cont = 0;

    boolean running = false;

    public int meta_pasos = 5000;

    public static double calorias_perdidas;

    private Button reset_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        //reset_button = (Button) view.findViewById(R.id.button_reset);

        tv_steps = (TextView) view.findViewById(R.id.tv_steps);

        arcView = (DecoView) view.findViewById(R.id.dynamicArcView);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        if (meta_pasos > 0) {
            Log.d("mSeries out in", (String.valueOf(meta_pasos)));
            // Create required data series on the DecoView
            createBackSeries();
            createDataSeries1();

            // Setup events to be fired on a schedule
            createEvents();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this.getContext(), "Sensor not Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        running = false;

        // if you unregister the hardware will stop detecting steps
        // sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (running) {
            tv_steps.setText(String.valueOf(event.values[0]));
            evsteps = event.values[0];

            calorias_perdidas = event.values[0] / 20;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void createBackSeries() {
        SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, meta_pasos, 0)
                .setInitialVisibility(true)
                .build();

        mBackIndex = arcView.addSeries(seriesItem);
    }

    private void createDataSeries1() {
        final SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                .setRange(0, meta_pasos, 0)
                .setInitialVisibility(false)
                .build();

        Log.d("mSeries Data1", (String.valueOf(meta_pasos)));


        final TextView textToGo = (TextView) view.findViewById(R.id.textRemaining);
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                textToGo.setText(String.format("%d Pasos para la meta", (int) (seriesItem.getMaxValue() - currentPosition)));

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        final TextView distance = (TextView) view.findViewById(R.id.tv_distancia);
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {

                double distancia_recorrida = (currentPosition * 0.7) / 1000;
                distance.setText(String.format("%.03f", distancia_recorrida) + " km recorridos");

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        final TextView calories = (TextView) view.findViewById(R.id.tv_calories);
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {

                double distancia_recorrida = (currentPosition * 0.7) / 1000;
                calories.setText(String.format("%.03f", calorias_perdidas) + " cal quemadas");

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        mSeries1Index = arcView.addSeries(seriesItem);
    }

    private void createEvents() {
        cont++;
        arcView.executeReset();

        if (cont == 1) {
            resetText();
            arcView.addEvent(new DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_EXPLODE)
                    .setIndex(mSeries1Index)
                    .setDelay(0)
                    .setDuration(1000)
                    .setDisplayText("")
                    .setListener(new DecoEvent.ExecuteEventListener() {
                        @Override
                        public void onEventStart(DecoEvent decoEvent) {

                        }

                        @Override
                        public void onEventEnd(DecoEvent decoEvent) {
                            createEvents();
                        }
                    })
                    .build());
        }
        arcView.addEvent(new DecoEvent.Builder(meta_pasos)
                .setIndex(mBackIndex)
                .setDuration(3000)
                .setDelay(100)
                .build());

        arcView.addEvent(new DecoEvent.Builder(evsteps)
                .setIndex(mSeries1Index)
                .setDelay(3250)
                .build());

        arcView.addEvent(new DecoEvent.Builder(DecoDrawEffect.EffectType.EFFECT_SPIRAL_EXPLODE)
                .setIndex(mSeries1Index)
                .setDelay(20000)
                .setDuration(3000)
                .setDisplayText("")
                .setListener(new DecoEvent.ExecuteEventListener() {
                    @Override
                    public void onEventStart(DecoEvent decoEvent) {

                    }

                    @Override
                    public void onEventEnd(DecoEvent decoEvent) {
                        createEvents();
                    }
                })
                .build());

    }

    private void resetText() {
        ((TextView) view.findViewById(R.id.textRemaining)).setText("");
    }


}
