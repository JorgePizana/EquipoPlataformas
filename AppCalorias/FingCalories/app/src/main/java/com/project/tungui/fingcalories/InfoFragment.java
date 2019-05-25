package com.project.tungui.fingcalories;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InfoFragment extends Fragment {

    View view;

    private EditText ed_edad;
    private EditText ed_peso;
    private EditText ed_altura;
    private EditText ed_activo;
    private RadioButton rb_hombre, rb_mujer;

    private Button button_calcular;

    public static ArrayList<Perfil> perfiles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_info, container, false);

        ed_edad = (EditText) view.findViewById(R.id.ed_edad);
        ed_peso = (EditText) view.findViewById(R.id.ed_peso);
        ed_altura = (EditText) view.findViewById(R.id.ed_altura);
        ed_activo = (EditText) view.findViewById(R.id.ed_activo);

        rb_hombre = (RadioButton) view.findViewById(R.id.rb_hombre);
        rb_mujer = (RadioButton) view.findViewById(R.id.rb_mujer);

        button_calcular = (Button) view.findViewById(R.id.button_calcular);
        button_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });

        return view;
    }


    // Method for the button Calcular
    @SuppressLint("SetTextI18n")
    public void calcular() {

        double edad = Double.parseDouble(ed_edad.getText().toString());
        double peso = Double.parseDouble(ed_peso.getText().toString());
        double altura = Double.parseDouble(ed_altura.getText().toString());
        int dias_activo = Integer.parseInt(ed_activo.getText().toString());

        Perfil myPerfil = new Perfil(altura, peso, edad, dias_activo);

        double bmr = 0.0;
        double dailyCaloricIntake = 0.0;

        if (rb_mujer.isChecked()) {

            // Ecuacion de Mifflin-St.Jeor para mujeres
            bmr = (altura * 6.25) + (peso * 9.99) - (edad * 4.92) - 161;
            myPerfil.setGenero("Mujer");

        } else if (rb_hombre.isChecked()) {

            // Ecuacion de Mifflin-St.Jeor para hombres
            bmr = (altura * 6.25) + (peso * 9.99) - (edad * 4.92) + 5;
            myPerfil.setGenero("Hombre");
        }


        myPerfil.setMeta_basal((int)bmr);


        if (dias_activo == 0) {

            dailyCaloricIntake = bmr * 1.2;

        } else if (dias_activo >= 1 && dias_activo <= 2) {

            dailyCaloricIntake = bmr * 1.375;

        } else if (dias_activo >= 3 && dias_activo <= 5) {

            dailyCaloricIntake = bmr * 1.55;

        } else if (dias_activo >= 6 && dias_activo <= 7) {
            dailyCaloricIntake = bmr * 1.725;
        }


        myPerfil.setMantener_cal((int)dailyCaloricIntake);

        myPerfil.setPerder_peso_cal((int)dailyCaloricIntake - 500);

        myPerfil.setGanar_peso_cal((int)dailyCaloricIntake + 500);

        perfiles.add(myPerfil);

        Toast.makeText(this.getContext(), "Perfil Guardado", Toast.LENGTH_SHORT).show();
    }
}
