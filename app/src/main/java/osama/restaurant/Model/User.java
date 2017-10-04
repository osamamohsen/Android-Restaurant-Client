package osama.restaurant.Model;

/**
 * Created by osama on 04/09/17.
 */

public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String Image;
    private String IsStaff;

    public User() {}

    public User(String Name, String Password , String Phone) {
        this.Name = Name;
        this.Password = Password;
        this.Phone = Phone;
        this.Image = "";
        this.IsStaff = "false";
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPhone() {
        return this.Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getImage() { return Image;}

    public void setImage(String Image) {this.Image = Image;}

    public String getIsStaff() {
        return IsStaff;
    }
    public void setIsStaff(String isStaff) {IsStaff = isStaff;}
}
