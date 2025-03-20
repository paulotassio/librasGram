package com.example.librasgram;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;

import static nl.dionsegijn.konfetti.core.Position.Relative;
import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;
import nl.dionsegijn.konfetti.xml.image.ImageUtil;

public class Pontuacao extends AppCompatActivity {

    private final int delay = 7000;

    private Vibrator vibrator;

    private TextView pontos;
    private TextView tentativas;

    int pontuacao = 0;
    int erros = 0;

    private KonfettiView konfettiView = null;
    private Shape.DrawableShape drawableShape = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pontuacao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ////////////////////////////////////////////////////////////////////
        final Drawable drawable =
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heart);
        drawableShape = ImageUtil.loadDrawable(drawable, true, true);

        konfettiView = findViewById(R.id.konfettiViewFinal);

        ///////////////////////////////////////////////////////////////////////


        pontos = findViewById(R.id.telapontuacao);
        tentativas = findViewById(R.id.telatentativa);

        if (vibrator != null && vibrator.hasVibrator()) {
            //Toast.makeText(this, "SEU DISPOSITIVO NÃO SUPORTA VIBRAR", Toast.LENGTH_SHORT).show();
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pontuacao = bundle.getInt("pontos");
            erros = bundle.getInt("erros");

            pontos.setText(String.valueOf(pontuacao));
            tentativas.setText(String.valueOf(erros));
        }

        rain();
        vibrarDuasVezes();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent t = new Intent(Pontuacao.this, Agradecimento.class);
                startActivity(t);
            }
        }, delay);//DEPOIS DE 5 SEGUNDOS

    }

    public void rain() {
        EmitterConfig emitterConfig = new Emitter(7, TimeUnit.SECONDS).perSecond(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .angle(Angle.BOTTOM)
                        .spread(Spread.ROUND)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(0f, 15f)
                        .position(new Relative(0.0, 0.0).between(new Relative(1.0, 0.0)))
                        .build());
    }

    private void vibrateDevice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Para API 26 ou superior
            VibrationEffect effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(effect);
        } else {
            // Para versões abaixo da API 26
            vibrator.vibrate(500);
        }
    }

    public void vibrarDuasVezes() {
        // Obter o serviço de vibração
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Verificar se o dispositivo suporta vibração
        if (vibrator != null && vibrator.hasVibrator()) {
            // Padrão de vibração: {pausa inicial, vibração1, pausa, vibração2}
            long[] padrao = {0, 200, 100, 200, 0, 200, 100, 200, 0, 200, 100, 200}; // 200ms vibra, 100ms pausa, 200ms vibra

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Para API 26 ou superior
                VibrationEffect efeito = VibrationEffect.createWaveform(padrao, -1); // -1 significa sem repetição
                vibrator.vibrate(efeito);
            } else {
                // Para versões abaixo da API 26
                vibrator.vibrate(padrao, -1); // -1 significa sem repetição
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancelar a vibração ao destruir a Activity
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

}