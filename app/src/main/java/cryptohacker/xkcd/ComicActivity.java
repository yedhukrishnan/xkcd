package cryptohacker.xkcd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ComicActivity extends AppCompatActivity {
    private ImageLoader imageLoader;
    private NetworkImageView comicImageView;
    private Comic comic;
    private int comicNumber = 900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        fetchAndShowComic(0);
    }

    public void loadComic(Comic comic) {
        this.comic = comic;
        comicImageView = (NetworkImageView) findViewById(R.id.comic_image_view);
        imageLoader = MySingleton.getInstance(this).getImageLoader();
        comicImageView.setImageUrl(comic.getImg(), imageLoader);
    }

    public void nextComic(View view) {
        comicNumber += 1;
        Log.d("xkcd", String.valueOf(comicNumber));
        fetchAndShowComic(comicNumber);
    }

    public void previousComic(View view) {
        comicNumber -= 1;
        Log.d("xkcd", String.valueOf(comicNumber));
        fetchAndShowComic(comicNumber);
    }

    public void showAltText(View view) {
        Log.d("xkcd", comic.getAlt());
        Toast.makeText(this, comic.getAlt(), Toast.LENGTH_LONG).show();
    }

    public void fetchAndShowComic(int number) {
        new ComicJSONFetcher(this, new ComicJSONFetcher.ComicJSONFetchListener() {
            @Override
            public void onSuccess(Comic comic) {
                loadComic(comic);
            }

            @Override
            public void onFailure() {
                Log.d("xkcd", "error");
            }
        }).getComicJSON(number);
    }
}
