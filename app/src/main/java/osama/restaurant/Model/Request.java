package osama.restaurant.Model;

import java.util.List;

/**
 * Created by osama on 13/09/17.
 */

public class Request {
    private String phone;
    private String Name;
    private String address;
    private String total;
    private List<Order> foods;
    private String status;


    public Request() {
    }

    public Request(String phone, String name, String address, String total, List<Order> foods) {
        this.phone = phone;
        Name = name;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.status = "0"; // Default is 0 , 0: placed , 1: shipping , 2: shipped
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
