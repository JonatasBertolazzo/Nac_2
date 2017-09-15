package br.com.jonatasbertolazzo.nac_1.Api;

import br.com.jonatasbertolazzo.nac_1.Model.cep;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepAPI {

    @GET(value = "cep/{numerocep}")
    Call<cep> buscaCep(@Path(value = "numerocep") String cep);

}
