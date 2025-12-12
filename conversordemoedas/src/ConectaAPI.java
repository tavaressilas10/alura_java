import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConectaAPI {

    private static final String API_URL_BASE = "https://v6.exchangerate-api.com/v6/";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private final String apiKey;
    private String baseCurrency;
    private String targetCurrency;

    public ConectaAPI (String apiKey, String baseCurrency, String targetCurrency) {
        this.apiKey = apiKey;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
    /**
     * Monta a URL completa para a API
     */
    private String buildUrl() {
        return API_URL_BASE + apiKey + "/latest/" + baseCurrency;
    }
    /**
     * Faz a requisição GET a API e retorna o cropo da resposta
     */
    public String consultarCotacao() {

        String url = buildUrl();
        System.out.println("Chamando URL: " + url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            int status = response.statusCode();
            System.out.println("Status HTTP: " + status);

            if (response.statusCode() != 200) {
                System.out.println("Erro na requisição");
                System.out.println(response.body());
                return null;
            }

            return response.body();

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro na comunicação com a API: " + e.getMessage());
            return null;
        }
    }

    public void resultJson(String body, double valor) {

        try {
            JsonElement parsed = JsonParser.parseString(body);
            JsonObject root = parsed.getAsJsonObject();
            JsonObject rates = root.getAsJsonObject("conversion_rates");

            double rate = rates.get(targetCurrency).getAsDouble();

            System.out.printf("1 %s = %.4f %s%n",
                    baseCurrency, rate, targetCurrency);


            double converted = valor * rate;

            System.out.printf("%.2f %s = %.2f %s%n",
                    valor, baseCurrency, converted, targetCurrency);

        } catch (Exception e) {
            System.out.println("Erro ao processar JSON: " + e.getMessage());
        }
    }
}