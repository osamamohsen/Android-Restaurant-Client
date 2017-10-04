package osama.restaurant.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by osama on 08/09/17.
 */

public class FirebaseConnection {
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public FirebaseConnection() {
        this.database = FirebaseDatabase.getInstance();
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }


    public void getTable(String referenceName){
        this.reference = this.database.getReference(referenceName);
    }

    public DatabaseReference getReference() {
        return this.reference;
    }
}
