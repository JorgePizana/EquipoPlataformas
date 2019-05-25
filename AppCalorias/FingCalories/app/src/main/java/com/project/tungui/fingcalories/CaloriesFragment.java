package com.project.tungui.fingcalories;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class CaloriesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinner_perfiles;

    private TextView tv_imc, tv_metabasal, tv_mantener, tv_perder, tv_ganar, tv_imc_info;

    ArrayList<String> datos_perfiles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calories, container, false);

        spinner_perfiles = (Spinner) view.findViewById(R.id.spinner_perfil);

        // TextViews
        tv_imc = (TextView) view.findViewById(R.id.tv_imc);
        tv_imc_info = (TextView) view.findViewById(R.id.tv_imc_info);
        tv_metabasal = (TextView) view.findViewById(R.id.tv_metabasal);
        tv_mantener = (TextView) view.findViewById(R.id.tv_mantener);
        tv_perder = (TextView) view.findViewById(R.id.tv_perder);
        tv_ganar = (TextView) view.findViewById(R.id.tv_ganar);

        for (Perfil datos : InfoFragment.perfiles) {

            datos_perfiles.add(datos.getGenero() + "  -   Edad: " + datos.getEdad() + " / Peso: " + datos.getPeso() + " / Altura: " + datos.getAltura());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_item_estilo, datos_perfiles);
        spinner_perfiles.setAdapter(adapter);

        spinner_perfiles.setOnItemSelectedListener(this);

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
