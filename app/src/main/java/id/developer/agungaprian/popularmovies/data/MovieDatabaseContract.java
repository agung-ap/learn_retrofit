package id.developer.agungaprian.popularmovies.data;

import android.net.Uri;

/**
 * Created by agungaprian on 03/08/17.
 */

public class MovieDatabaseContract {
    static final String CONTENT_AUTHORITY = "id.developer.agungaprian.popularmovies";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);
}
