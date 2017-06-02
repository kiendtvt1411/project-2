package dhbkhn.kien.doan2.data.realtime.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import dhbkhn.kien.doan2.utils.AppLogger;

/**
 * Created by kiend on 5/15/2017.
 */

public class RoomStatusRequest implements Serializable{

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
    @SerializedName("max_humidity")
    private float maxHumidity;

    @Expose
    @SerializedName("max_temperature")
    private float maxTemperature;

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
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

    public float getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(float maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public RoomStatusRequest() {
    }

    public String parseJsonToString(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_room", this.idRoom);
            obj.put("lamp_1", this.lampOne);
            obj.put("lamp_2", this.lampTwo);
            obj.put("lamp_3", this.lampThree);
            obj.put("max_humidity", this.maxHumidity);
            obj.put("max_temperature", this.maxTemperature);
            return obj.toString();
        } catch (JSONException e) {
            AppLogger.e("Parse Json Error", e);
            return null;
        }
    }
}
