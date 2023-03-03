package com.example.cronometro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private Button Iniciar_Button, Parar_Button, Vuelta_Button, Reiniciar_Button;
    private ListView lapList;

    private long timeWhenParar= 0;
    private boolean running = false;
    private List<String> Vuelta = new ArrayList<>();
    private ArrayAdapter<String> lapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        Iniciar_Button = findViewById(R.id.Iniciar_button);
        Parar_Button = findViewById(R.id.Parar_button);
        Vuelta_Button = findViewById(R.id.Vuelta_button);
        Reiniciar_Button = findViewById(R.id.Reiniciar_button);
        lapList = findViewById(R.id.lap_list);

        lapAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Vuelta);
        lapList.setAdapter(lapAdapter);

        Iniciar_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - timeWhenParar);
                    chronometer.start();
                    running = true;
                }
            }
        });

        Parar_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    chronometer.stop();
                    timeWhenParar = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
            }
        });

        Vuelta_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    String lapTime = chronometer.getText().toString();
                    Vuelta.add(0, lapTime);
                    lapAdapter.notifyDataSetChanged();
                }
            }
        });

        Reiniciar_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                timeWhenParar = 0;
                running = false;
                Vuelta.clear();
                lapAdapter.notifyDataSetChanged();
            }
        });
    }
}
