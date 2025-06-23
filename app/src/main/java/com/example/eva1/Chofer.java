package com.example.eva1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Chofer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chofer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        AutoCompleteTextView autocListaLugares = findViewById(R.id.autocListaLugares);
        autocListaLugares.setOnItemClickListener((parent, view, position, id) -> {

            Map<String, String> datos = new HashMap<String, String>();
            WebService ws= new WebService("https://uteqia.com/api/choferes" + (position+1),
                    datos, Chofer.this, Chofer.this);
            ws.execute("GET");

        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void processFinish(String result) throws JSONException {
        TextView txtSaludo = findViewById(R.id.txtResp);
        String lstLista="";
        JSONObject resultados = new JSONObject(result);
        JSONArray JSONlista = resultados.getJSONArray("data");
        for(int i=0; i< JSONlista.length();i++){
            JSONObject banco= JSONlista.getJSONObject(i);
            lstLista = lstLista + i + ".- " +
                    banco.getString("nombre_lugar").toString()
                    + " - " +
                    banco.getString("categoria").toString() + "\n" ;
        }
        txtSaludo.setText(lstLista );

    }
}