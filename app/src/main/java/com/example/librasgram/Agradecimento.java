package com.example.librasgram;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Agradecimento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agradecimento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }//fim do oncreate

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Finaliza a atividade e encerra o aplicativo

        //finishAffinity();
        Dialog dialog = new Dialog(Agradecimento.this);
        dialog.setContentView(R.layout.dialog_sair);

        // Configurar os botões do dialog
        Button btnSair = dialog.findViewById(R.id.btnSair);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        // Ação do botão "Cancelar"
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                dialog.dismiss(); // Fecha o pop-up
            }
        });

        // Ação do botão "OK"
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(Agradecimento.this, MainActivity.class);
                startActivity(t);
                dialog.dismiss(); // Fecha o pop-up
                // Adicione aqui a lógica para o botão "OK"
            }
        });

        // Exibir o dialog
        dialog.show();

    }

}