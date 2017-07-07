package ru.whitetigersoft.test_application.Model.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

import ru.whitetigersoft.test_application.Model.Utils.JsonParser;

public class BaseModel implements Parcelable {

    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel in) {
            return new BaseModel(in);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };

    public BaseModel() {

    }


    public BaseModel(Parcel in) {
        String stringObject = in.readString();
        GsonBuilder builder = getGsonBuilder();
        Gson gson = builder.create();
        gson.fromJson(stringObject, this.getClass());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String stringObject = getGsonBuilder().create().toJson(this);
        dest.writeString(stringObject);
    }

    protected GsonBuilder getGsonBuilder() {
        GsonBuilder builder = JsonParser.getBuilder();

        final BaseModel model = this;
        builder.registerTypeAdapter(this.getClass(), new InstanceCreator<BaseModel>() {
            @Override
            public BaseModel createInstance(Type type) {
                return model;
            }
        });
        return builder;
    }
}
