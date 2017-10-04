package osama.restaurant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import osama.restaurant.Common.Common;
import osama.restaurant.Model.User;

public class SignIn extends AppCompatActivity {

    EditText edtPhone,edtPassword;
    Button btnSignInSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSignInSubmit = (Button) findViewById(R.id.btnSignInSubmit);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignInSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtPhone.getText().toString().length() > 0 && edtPassword.getText().toString().length() > 0){
                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("Please Waiting....");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mDialog.dismiss();
                            if(dataSnapshot.child(edtPhone.getText().toString()).exists()){
                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                if(user.getPassword().equals(edtPassword.getText().toString())){
                                    user.setPhone(edtPhone.getText().toString());// set Phone Number
                                    Toast.makeText(SignIn.this, "Sign In Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignIn.this,Home.class);
                                    Common.currentUser = user; // static
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(SignIn.this, "Sign In False", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(SignIn.this, "User Not Exist in Database", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else{
                    Toast.makeText(SignIn.this, "Please enter your phone and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
