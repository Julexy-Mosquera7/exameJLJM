package com.example.examejljm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.exameJLJM.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRevistas;
    private RevistaAdapter revistaAdapter;
    private List<Revista> listaRevistas = new ArrayList<>();
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.exameJLJM.R.layout.activity_main);

        recyclerViewRevistas = findViewById(R.id.recyclerRevistas);
        recyclerViewRevistas.setLayoutManager(new LinearLayoutManager(this));

        revistaAdapter = new RevistaAdapter(this, listaRevistas);
        recyclerViewRevistas.setAdapter(revistaAdapter);

        client = new OkHttpClient();

        obtenerRevistas();
    }

    private void obtenerRevistas() {
        String url = "https://revistas.uteq.edu.ec/ws/journals.php";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error al obtener revistas", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    runOnUiThread(() -> procesarRespuesta(responseData));
                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Respuesta no exitosa", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void procesarRespuesta(String json) {
        try {
            JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                Revista revista = new Gson().fromJson(element, Revista.class);
                listaRevistas.add(revista);
            }
            revistaAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(this, "Error procesando JSON", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Error JSON", e);
        }
    }
}
