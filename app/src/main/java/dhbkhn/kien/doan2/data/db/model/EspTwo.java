package dhbkhn.kien.doan2.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by kiend on 5/13/2017.
 */

@Entity(nameInDb = "esp_room_two")
public class EspTwo {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "lamp_one")
    private boolean lampOne;

    @Property(nameInDb = "lamp_two")
    private boolean lampTwo;

    @Property(nameInDb = "lamp_three")
    private boolean lampThree;

    @Property(nameInDb = "temperature")
    private float temperature;

    @Property(nameInDb = "humidity")
    private float humidity;

    @Property(nameInDb = "max_temp")
    private float maxTempurature;

    @Property(nameInDb = "max_humid")
    private float maxHumidity;

    @Property(nameInDb = "date")
    private String date;

    @Generated(hash = 1731982281)
    public EspTwo() {
    }

    @Generated(hash = 439645725)
    public EspTwo(Long id, boolean lampOne, boolean lampTwo, boolean lampThree, float temperature, float humidity, float maxTempurature, float maxHumidity, String date) {
        this.id = id;
        this.lampOne = lampOne;
        this.lampTwo = lampTwo;
        this.lampThree = lampThree;
        this.temperature = temperature;
        this.humidity = humidity;
        this.maxTempurature = maxTempurature;
        this.maxHumidity = maxHumidity;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getMaxTempurature() {
        return maxTempurature;
    }

    public void setMaxTempurature(float maxTempurature) {
        this.maxTempurature = maxTempurature;
    }

    public float getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(float maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getLampOne() {
        return this.lampOne;
    }

    public boolean getLampTwo() {
        return this.lampTwo;
    }

    public boolean getLampThree() {
        return this.lampThree;
    }
}
