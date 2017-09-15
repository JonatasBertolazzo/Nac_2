package br.com.jonatasbertolazzo.nac_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.jonatasbertolazzo.nac_1.Api.CepAPI;
import br.com.jonatasbertolazzo.nac_1.Model.cep;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etBuscaCep;
    private TextView tvMostraCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBuscaCep = (EditText) findViewById(R.id.etBuscaCep);
        tvMostraCep = (TextView) findViewById(R.id.tvMostraCep);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.pesquisar:
                Pesquisar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Retrofit getRetrofit() {

        return new Retrofit.Builder()
                .baseUrl("http://correiosapi.apphb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void Pesquisar(){

        CepAPI service = getRetrofit().create(CepAPI.class);
        service.buscaCep(etBuscaCep.getText().toString()).enqueue(new Callback<cep>() {
            @Override
            public void onResponse(Call<cep> call, Response<cep> response) {
                switch (response.code()){
                    case 200:
                        if (response.body() != null){
                            MostraCep(response.body());
                        }
                        break;
                    case  404:
                        Toast.makeText(MainActivity.this, "Cep não encontrado!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<cep> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Não foi possivel encontrar os dados!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void MostraCep(cep cep){
        tvMostraCep.setText(cep.getCep() + "\n" + cep.getTipoDeLogradouro() + "\n" + cep.getLogradouro() + "\n" + cep.getBairro() + "\n" + cep.getCidade() + "\n" + cep.getEstado());
    }
}
