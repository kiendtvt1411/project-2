package dhbkhn.kien.doan2.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by kiend on 5/14/2017.
 */

@Entity(nameInDb = "users")
public class User {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "name")
    private String name;

    @Property(nameInDb = "created_at")
    private String createAt;

    @Property(nameInDb = "updated_at")
    private String updatedAt;

    @Generated(hash = 586692638)
    public User() {
    }

    @Generated(hash = 972972955)
    public User(Long id, String name, String createAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
