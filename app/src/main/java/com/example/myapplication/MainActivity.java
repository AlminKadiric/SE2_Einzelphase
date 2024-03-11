package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText editTextMatrikelnummer;
    Button buttonSortieren;
    TextView textViewErgebnis;
    Button buttonSenden;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMatrikelnummer = findViewById(R.id.editTextMatrikelnummer);
        buttonSortieren = findViewById(R.id.buttonSortieren);
        buttonSenden = findViewById(R.id.buttonSenden);
        textViewErgebnis = findViewById(R.id.textViewErgebnis);

        buttonSortieren.setOnClickListener(v -> {
            String matrikelnummer = editTextMatrikelnummer.getText().toString();
            String sortedMatrikelnummer = sortiereMatrikelnummer(matrikelnummer); // Correct method call
            textViewErgebnis.setText(sortedMatrikelnummer);
        });

        buttonSenden.setOnClickListener(v -> {
            String dataToSend = textViewErgebnis.getText().toString();
            sendViaTcp(dataToSend);
        });
    }


    String sortiereMatrikelnummer(String nummer) {
        char[] ziffern = nummer.toCharArray();
        Arrays.sort(ziffern);

        StringBuilder gerade = new StringBuilder();
        StringBuilder ungerade = new StringBuilder();

        for (char ziffer : ziffern) {
            if ((ziffer - '0') % 2 == 0) gerade.append(ziffer);
            else ungerade.append(ziffer);
        }

        return gerade.toString() + ungerade.toString();
    }

    private void sendViaTcp(String data) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("se2-submission.aau.at", 20080);
                OutputStream out = socket.getOutputStream();
                PrintWriter output = new PrintWriter(out);

                output.println(data);
                output.flush();

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final String response = input.readLine();

                runOnUiThread(() -> textViewErgebnis.setText(response));

                output.close();
                input.close();
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                runOnUiThread(() -> textViewErgebnis.setText("Error: " + e.getMessage()));
            }
        }).start();
    }

}
