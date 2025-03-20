package com.example.librasgram;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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


public class MainActivity extends AppCompatActivity {

    private Vibrator vibrator;

    private String frase = "escola! eu ir";
    private String frase2 = "eu vou para a escola!";
    private String fraseAtual = "";
    private String resposta = "";
    private GifImageView gifPrincipal;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;

    private EditText campoResposta;

    private ImageButton proximo;
    private ImageButton repetir;
    private ImageButton correcao;

    private TextView fraseCompleta;
    private TextView pontos;
    private TextView tentativas;

    private int pontuacao = 0;
    private int erros = 0;

    private KonfettiView konfettiView = null;
    private Shape.DrawableShape drawableShape = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ////////////////////////////////////////////////////////////////////
        final Drawable drawable =
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heart);
        drawableShape = ImageUtil.loadDrawable(drawable, true, true);

        konfettiView = findViewById(R.id.konfettiView);

        ///////////////////////////////////////////////////////////////////////

        gifPrincipal = findViewById(R.id.gifImageView1);

        fraseCompleta = findViewById(R.id.fraseCompleta);

        pontos = findViewById(R.id.pontuacao);
        tentativas = findViewById(R.id.tentativa);

        campoResposta = findViewById(R.id.campoResposta);
        campoResposta.setEnabled(false);

        repetir = findViewById(R.id.btNovamente);

        correcao = findViewById(R.id.ibCorrecao);
        proximo = findViewById(R.id.ibProximo);
        proximo.setEnabled(false);

        btn1 = findViewById(R.id.bt1);
        btn2 = findViewById(R.id.bt2);
        btn3 = findViewById(R.id.bt3);
        btn4 = findViewById(R.id.bt4);
        btn5 = findViewById(R.id.bt5);
        btn6 = findViewById(R.id.bt6);
        btn7 = findViewById(R.id.bt7);
        btn8 = findViewById(R.id.bt8);

        btn1.setOnClickListener(v -> {
            adicionarPalavra("ir");
            btn1.setEnabled(false);
            btn1.setBackgroundColor(Color.GRAY);
        });
        btn2.setOnClickListener(v -> {
            adicionarPalavra("casa");
            btn2.setEnabled(false);
            btn2.setBackgroundColor(Color.GRAY);
        });
        btn3.setOnClickListener(v -> {
            adicionarPalavra("escola!");
            btn3.setEnabled(false);
            btn3.setBackgroundColor(Color.GRAY);
        });
        btn4.setOnClickListener(v -> {
            adicionarPalavra("pessoas");
            btn4.setEnabled(false);
            btn4.setBackgroundColor(Color.GRAY);
        });
        btn5.setOnClickListener(v -> {
            adicionarPalavra("correr");
            btn5.setEnabled(false);
            btn5.setBackgroundColor(Color.GRAY);
        });
        btn6.setOnClickListener(v -> {
            adicionarPalavra("eu");
            btn6.setEnabled(false);
            btn6.setBackgroundColor(Color.GRAY);
        });
        btn7.setOnClickListener(v -> {
            adicionarPalavra("para");
            btn7.setEnabled(false);
            btn7.setBackgroundColor(Color.GRAY);
        });
        btn8.setOnClickListener(v -> {
            adicionarPalavra("amanhã");
            btn8.setEnabled(false);
            btn8.setBackgroundColor(Color.GRAY);
        });

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

    }// fim do oncreate

    // Método para adicionar palavras ao EditText
    private void adicionarPalavra(String palavra) {
        if (!fraseAtual.isEmpty()) {
            fraseAtual += " "; // Adiciona espaço se já houver texto
        }
        fraseAtual += palavra;
        campoResposta.setText(fraseAtual);
    }

    public void showDialogVoltar(View view){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_voltar);

        // Configurar os botões do dialog
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        // Ação do botão "Cancelar"
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Fecha o pop-up
            }
        });

        // Ação do botão "OK"
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Fecha o pop-up
                finishAffinity();
            }
        });

        // Exibir o dialog
        dialog.show();
    }// FIM DO POPUP 1

    public void showDica(View v){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_custom1);

        // Configurar os botões do dialog
        // Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        // Ação do botão "OK"
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Fecha o pop-up
                // Adicione aqui a lógica para o botão "OK"
            }
        });

        // Exibir o dialog
        dialog.show();
    }// FIM DO POPUP 2

    public void tentarNovamente(View v){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_denovo);

        // Configurar os botões do dialog
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        // Ação do botão "Cancelar"
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Fecha o pop-up
            }
        });

        // Ação do botão "OK"
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fraseAtual = "";
                campoResposta.setText("");
                correcao.setEnabled(true);
                campoResposta.setBackgroundColor(Color.parseColor("#FFFFFF"));
                gifPrincipal.setBackgroundResource(R.drawable.escolaeuvou);

                btn1.setEnabled(true);
                btn2.setEnabled(true);
                btn3.setEnabled(true);
                btn4.setEnabled(true);
                btn5.setEnabled(true);
                btn6.setEnabled(true);
                btn7.setEnabled(true);
                btn8.setEnabled(true);

                btn1.setBackgroundColor(Color.WHITE);
                btn2.setBackgroundColor(Color.WHITE);
                btn3.setBackgroundColor(Color.WHITE);
                btn4.setBackgroundColor(Color.WHITE);
                btn5.setBackgroundColor(Color.WHITE);
                btn6.setBackgroundColor(Color.WHITE);
                btn7.setBackgroundColor(Color.WHITE);
                btn8.setBackgroundColor(Color.WHITE);

                correcao.setBackgroundResource(R.drawable.correcao);

                if(pontuacao > 0) {
                    pontos.setText(Integer.toString(pontuacao - 1));
                }
                dialog.dismiss(); // Fecha o pop-up
            }
        });

        // Exibir o dialog
        dialog.show();
    }// FIM DO POPUP TENTAR NOVAMENTE

    public void showDialogProximo(View view){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_proximo);

        // Configurar os botões do dialog
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        // Ação do botão "Cancelar"
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Fecha o pop-up
            }
        });

        // Ação do botão "OK"
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, Fase1frase2.class);
                t.putExtra("pontos", pontuacao);
                t.putExtra("erros", erros);
                startActivity(t);
            }
        });

        // Exibir o dialog
        dialog.show();
    }// FIM DO POPUP 1

    public void correcao(View v){

        resposta = campoResposta.getText().toString().trim();

        if(resposta.equals("")) {

            Toast.makeText(this, "DIGITE UMA RESPOSTA", Toast.LENGTH_SHORT).show();

        }else{

            // Converte ambas as frases para minúsculas e divide em palavras
            String[] palavrasCorretas = frase.toLowerCase().split("\\s+");
            String[] palavrasInseridas = resposta.toLowerCase().split("\\s+");

            // Cria conjuntos a partir dos arrays de palavras
            Set<String> setCorretas = new HashSet<>(Arrays.asList(palavrasCorretas));
            Set<String> setInseridas = new HashSet<>(Arrays.asList(palavrasInseridas));

            if (setCorretas.equals(setInseridas)) {
                //Toast.makeText(this, "PARABÉNS", Toast.LENGTH_SHORT).show();
                gifPrincipal.setBackgroundResource(R.drawable.parabens);
                //gifPrincipal.setImageDrawable(getResources().getDrawable(R.drawable.parabens));
                pontuacao += 125;
                vibrateDevice();
                pontos.setText(Integer.toString(pontuacao));
                fraseCompleta.setText(frase2);
                repetir.setEnabled(false);
                repetir.setBackgroundResource(R.drawable.tentardesativado);
                proximo.setEnabled(true);
                proximo.setBackgroundResource(R.drawable.proximotela);

                //explode();
                //parade();
                rain();

            } else {
                //Toast.makeText(this, "Tente novamente!", Toast.LENGTH_SHORT).show();
                campoResposta.setBackgroundColor(Color.parseColor("#FF0000"));
                erros += 1;
                vibrarDuasVezes();
                tentativas.setText(Integer.toString(erros));


            }// FIM DO ELSE

            correcao.setEnabled(false);
            correcao.setBackgroundResource(R.drawable.correcaodesativado);

        }// FIM DO ELSE DE VERIFICAÇÃO CORRETA

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
            long[] padrao = {0, 200, 100, 200}; // 200ms vibra, 100ms pausa, 200ms vibra

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

    //////////////////////////////////////////
    /*
    public void explode() {
        EmitterConfig emitterConfig = new Emitter(100L, TimeUnit.MILLISECONDS).max(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .spread(360)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(0f, 30f)
                        .position(new Relative(0.5, 0.3))
                        .build());
    }


    public void parade() {
        EmitterConfig emitterConfig = new Emitter(5, TimeUnit.SECONDS).perSecond(30);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .angle(Angle.RIGHT - 45)
                        .spread(Spread.SMALL)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(10f, 30f)
                        .position(new Relative(0.0, 0.5))
                        .build(),
                new PartyFactory(emitterConfig)
                        .angle(Angle.LEFT + 45)
                        .spread(Spread.SMALL)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(10f, 30f)
                        .position(new Relative(1.0, 0.5))
                        .build());
    }
    */

    public void rain() {
        EmitterConfig emitterConfig = new Emitter(5, TimeUnit.SECONDS).perSecond(100);
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


}// FIM DA CLASSE