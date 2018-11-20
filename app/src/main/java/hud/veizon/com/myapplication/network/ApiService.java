package hud.veizon.com.myapplication.network;


import hud.veizon.com.myapplication.model.MostViewedResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    String API_KEY = "2a416c9d65d344b99257f34654ce8aac";
    String BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/";

    @GET("mostviewed/{section}/{time-period}.json")
    Call<MostViewedResponse> getMostView(
            @Path("section") String section,
            @Path("time-period") String timeperiod);
}
