package id.developer.agungaprian.popularmovie.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by agungaprian on 31/07/17.
 */

public class TrailerResponse implements Parcelable{
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<Movie> results;

    protected TrailerResponse(Parcel in) {
        id = in.readInt();
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrailerResponse> CREATOR = new Creator<TrailerResponse>() {
        @Override
        public TrailerResponse createFromParcel(Parcel in) {
            return new TrailerResponse(in);
        }

        @Override
        public TrailerResponse[] newArray(int size) {
            return new TrailerResponse[size];
        }
    };

    public int getPage() {
        return id;
    }

    public void setPage(int id) {
        this.id = id;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
