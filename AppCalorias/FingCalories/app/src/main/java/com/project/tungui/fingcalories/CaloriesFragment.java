package com.project.tungui.fingcalories;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class CaloriesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinner_perfiles;

    private TextView tv_imc, tv_metabasal, tv_mantener, tv_perder, tv_ganar, tv_imc_info;

    ArrayList<String> datos_perfiles = new ArrayList<>();

    private Button button_noti;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calories, container, false);

        spinner_perfiles = (Spinner) view.findViewById(R.id.spinner_perfil);

        button_noti = (Button) view.findViewById(R.id.button_notification);

        // TextViews
        tv_imc = (TextView) view.findViewById(R.id.tv_imc);
        tv_imc_info = (TextView) view.findViewById(R.id.tv_imc_info);
        tv_metabasal = (TextView) view.findViewById(R.id.tv_metabasal);
        tv_mantener = (TextView) view.findViewById(R.id.tv_mantener);
        tv_perder = (TextView) view.findViewById(R.id.tv_perder);
        tv_ganar = (TextView) view.findViewById(R.id.tv_ganar);

        for (Perfil datos : InfoFragment.perfiles) {

            datos_perfiles.add(datos.getGenero() + "   -   Edad: " + datos.getEdad() + " / Peso: " + datos.getPeso() + " / Altura: " + datos.getAltura());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_item_estilo, datos_perfiles);
        spinner_perfiles.setAdapter(adapter);

        spinner_perfiles.setOnItemSelectedListener(this);

       /* button_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), NotificationActivity.class);

                PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, i, 0);

                Notification no = new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(android.R.drawable.stat_notify_missed_call)
                        .setContentTitle("Hola Guapo")
                        .setContentText("Probando")
                        .setAutoCancel(true)
                        .setContentIntent(pi).build();

                NotificationManager nm = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
                nm.notify(2, no);


            }
        });*/


        final Intent i = new Intent(getActivity(), NotificationActivity.class);

        PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, i, 0);

        List<ActivityTransition> transitions = new ArrayList<>();

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());

        ActivityTransitionRequest request = new ActivityTransitionRequest(transitions);

        Task<Void> task = ActivityRecognition.getClient(getContext())
                .requestActivityTransitionUpdates(request, pi);

        task.addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        // Handle success
                    }
                }
        );

        task.addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Handle error
                    }
                }
        );

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        double peso = InfoFragment.perfiles.get(position).getPeso();
        double altura = InfoFragment.perfiles.get(position).getAltura() / 100;

        double imc = ((double) peso / ( (double) altura * (double) altura));

        tv_imc.setText(String.format("%.03f", imc));
        tv_metabasal.setText(String.valueOf(InfoFragment.perfiles.get(position).getMeta_basal()) + " Calorías");
        tv_mantener.setText(String.valueOf(InfoFragment.perfiles.get(position).getMantener_cal()) + " Calorías");
        tv_perder.setText(String.valueOf(InfoFragment.perfiles.get(position).getPerder_peso_cal()) + " Calorías");
        tv_ganar.setText(String.valueOf(InfoFragment.perfiles.get(position).getGanar_peso_cal()) + " Calorías");

        if (imc < 18.5) {

            tv_imc_info.setText("Según tu IMC, tienes Bajo Peso");

        } else if (imc >= 18.5 && imc <= 24.9) {

            tv_imc_info.setText("Según tu IMC, tienes un Peso Saludable");

        } else if (imc >= 25 && imc <= 29.9) {

            tv_imc_info.setText("Según tu IMC, tienes Sobrepeso");

        } else if (imc >= 30) {

            tv_imc_info.setText("Según tu IMC, tienes Obesidad");

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
