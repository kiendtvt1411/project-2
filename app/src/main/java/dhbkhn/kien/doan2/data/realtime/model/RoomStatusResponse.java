package dhbkhn.kien.doan2.data.realtime.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import dhbkhn.kien.doan2.utils.AppLogger;

/**
 * Created by kiend on 5/16/2017.
 */

public class RoomStatusResponse implements Serializable{

    @Expose
    @SerializedName("id_room")
    private int idRoom;

    @Expose
    @SerializedName("lamp_1")
    private boolean lampOne;

    @Expose
    @SerializedName("lamp_2")
    private boolean lampTwo;

    @Expose
    @SerializedName("lamp_3")
    private boolean lampThree;

    @Expose
    @SerializedName("humidity")
    private float humidity;

    @Expose
    @SerializedName("temperature")
    private float temperature;

    public RoomStatusResponse() {
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public boolean isLampOne() {
        return lampOne;
    }

    public void setLampOne(boolean lampOne) {
        this.lampOne = lampOne;
    }

    public boolean isLampTwo() {
        return lampTwo;
    }

    public void setLampTwo(boolean lampTwo) {
        this.lampTwo = lampTwo;
    }

    public boolean isLampThree() {
        return lampThree;
    }

    public void setLampThree(boolean lampThree) {
        this.lampThree = lampThree;
    }

    public String parseJsonToString() {
        JSONObject obj = new JSONObject();
        try{
            obj.put("id_room", this.idRoom);
            obj.put("lamp_1", this.lampOne);
            obj.put("lamp_2", this.lampTwo);
            obj.put("lamp_3", this.lampThree);
            obj.put("humidity", this.humidity);
            obj.put("temperature", this.temperature);
            return obj.toString();
        } catch (JSONException e) {
            AppLogger.e("Parse Json Error", e);
            return null;
        }
    }
}
