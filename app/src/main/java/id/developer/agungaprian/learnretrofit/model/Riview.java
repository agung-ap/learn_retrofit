package id.developer.agungaprian.popularmovie.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by agungaprian on 29/07/17.
 */

public class Riview implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("url")
    private String url;


    protected Riview(Parcel in) {
        id = in.readInt();
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    public static final Creator<Riview> CREATOR = new Creator<Riview>() {
        @Override
        public Riview createFromParcel(Parcel in) {
            return new Riview(in);
        }

        @Override
        public Riview[] newArray(int size) {
            return new Riview[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
    }
}
