package id.developer.agungaprian.popularmovie.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by agungaprian on 31/07/17.
 */

public class RiviewResponse implements Parcelable{
    @SerializedName("page")
    private int page;
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<Movie> results;

    protected RiviewResponse(Parcel in) {
        page = in.readInt();
        id = in.readInt();
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(id);
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RiviewResponse> CREATOR = new Creator<RiviewResponse>() {
        @Override
        public RiviewResponse createFromParcel(Parcel in) {
            return new RiviewResponse(in);
        }

        @Override
        public RiviewResponse[] newArray(int size) {
            return new RiviewResponse[size];
        }
    };

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
