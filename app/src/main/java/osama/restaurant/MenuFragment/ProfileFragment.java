package osama.restaurant.MenuFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.widget.FButton;
import osama.restaurant.Common.Common;
import osama.restaurant.R;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    View rootView;
    private final int PICK_IMAGE = 100;
    String imageUrl = "";
    CircleImageView img_profile;     Uri imageUri;
    MaterialEditText edt_name,edt_password;
    FButton btn_update_profile;
    FirebaseDatabase database;
    DatabaseReference tbl_users;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        img_profile = (CircleImageView) rootView.findViewById(R.id.img_profile);
        edt_name = (MaterialEditText) rootView.findViewById(R.id.edt_name);
        edt_password = (MaterialEditText) rootView.findViewById(R.id.edt_password);
        btn_update_profile = (FButton) rootView.findViewById(R.id.btn_update_profile);

        if(Common.currentUser.getImage() != ""){

//            Picasso mPicasso = Picasso.with(getActivity());
//            mPicasso.with(getActivity()).setIndicatorsEnabled(true);
//            mPicasso.with(getActivity()).setLoggingEnabled(true);
            Picasso.with(getActivity()).load(Common.currentUser.getImage()).into(img_profile);
        }
        //Init Firebase Connection

        database = FirebaseDatabase.getInstance();
        tbl_users = database.getReference("User");

        //Init Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = Common.currentUser.getPassword();
                if(edt_name.getText() != null && edt_name.getText().toString().length() > 0 &&
                        edt_password.getText() != null && edt_password.getText().toString().length() > 0){
                    updateImageUrl();
                    Common.currentUser.setName(edt_name.getText().toString());
                    Common.currentUser.setPassword(edt_password.getText().toString());
                }else{
                    Toast.makeText(getActivity(), "please check your data again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        return rootView;
    }

    private void update_profile() {
        String phone = Common.currentUser.getPhone().toString();
        tbl_users.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("name").setValue(edt_name.getText().toString());
                dataSnapshot.getRef().child("password").setValue(edt_password.getText().toString());
                dataSnapshot.getRef().child("image").setValue(imageUrl);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateImageUrl() {

        if(imageUri != null){

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Updating.....");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"+ Common.currentUser.getPhone());
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    imageUrl = taskSnapshot.getDownloadUrl().toString();// get image url
                    update_profile();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progres_time = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progres_time+" %");
                }
            });
        }
    }


    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null && data.getData() != null){
            imageUri = data.getData();
            img_profile.setImageURI(imageUri);
        }
    }
}