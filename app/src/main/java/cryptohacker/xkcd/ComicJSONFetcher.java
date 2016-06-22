package cryptohacker.xkcd;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by yedhukrishnan on 23/06/16.
 */

public class ComicJSONFetcher {
    private Context context;
    private ComicJSONFetchListener comicJSONFetchListener;

    public ComicJSONFetcher(Context context, ComicJSONFetchListener comicJSONFetchListener) {
        this.comicJSONFetchListener = comicJSONFetchListener;
        this.context = context;
    }

    public void getComicJSON(int number) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getComicJsonUrl(number),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Comic comic = gson.fromJson(String.valueOf(response), Comic.class);
                        comicJSONFetchListener.onSuccess(comic);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        comicJSONFetchListener.onFailure();
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private String getComicJsonUrl(int number) {
        String comicNumberString = (number == 0)? "" : String.valueOf(number) + "/";
        return "http://xkcd.com/" + comicNumberString + "info.0.json";
    }

    public interface ComicJSONFetchListener {
        void onSuccess(Comic comic);
        void onFailure();
    };
}
