package id.developer.agungaprian.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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
    private List<Riview> results = new ArrayList<>();

    /*public RiviewResponse(int id){
        this.id = id;
    }*/

    protected RiviewResponse(Parcel in) {
        page = in.readInt();
        id = in.readInt();
        results = in.createTypedArrayList(Riview.CREATOR);
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

    public List<Riview> getResults() {
        return results;
    }

    public void setResults(List<Riview> results) {
        this.results = results;
    }
}
