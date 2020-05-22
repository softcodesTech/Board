package com.example.board;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.board.General_Settings;

import java.io.IOException;

import static com.example.board.Login.MY_PREFS_NAME;

@SuppressWarnings("ALL")
public class Bsu_Settings extends AppCompatActivity {
    private ImageView profileimage;
    private static final int REQUEST_CAMERA=1, SELECT_FILE=0;
    Uri imageUri;

    TextView user_agandi,login_hazel,dont_have_account,register_sett;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bsu__settings);
        profileimage = findViewById(R.id.user_pic);
        user_agandi = findViewById(R.id.welcome_user);
        login_hazel = findViewById(R.id.login_user);
        dont_have_account =findViewById(R.id.dont);
        register_sett = findViewById(R.id.register_settings);
        if (savedInstanceState != null) {
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String name = prefs.getString("userEmail", "welcome");//"No name defined" is the default value.
            String idName = prefs.getString("userPassword", "P");
            user_agandi.setText(name);
            login_hazel.setVisibility(View.INVISIBLE);
            dont_have_account.setVisibility(View.INVISIBLE);
            register_sett.setVisibility(View.INVISIBLE);

        }
        else{
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String name = prefs.getString("userEmail", "Pascal");//"No name defined" is the default value.
            String idName = prefs.getString("userPassword", "P");
            user_agandi.setText("Welcome\n"+name);
            login_hazel.setVisibility(View.VISIBLE);
            dont_have_account.setVisibility(View.VISIBLE);
            register_sett.setVisibility(View.VISIBLE);
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode== REQUEST_CAMERA){

            Bundle bundle= data.getExtras();
            assert bundle != null;
            final Bitmap bitmap = (Bitmap) bundle.get("data");
            profileimage.setImageBitmap(bitmap);
        }

        else  if (requestCode==SELECT_FILE && resultCode==RESULT_OK){
            imageUri=data.getData();
            try {
                Bitmap  bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileimage.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void uploadimage(View view) {
        SelectImage();
    }

    private void SelectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Bsu_Settings.this);
        builder.setTitle("Choose Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);


                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Add Photo"), SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }

        });

        builder.show();
    }


    public void rateus(View view) {

    }

    public void editprofile(View view) {

        Intent view_classes = new Intent(Bsu_Settings.this, EditProfile.class);
        startActivity(view_classes);
        finish();
    }

    public void about(View view) {
        Intent view_classes = new Intent(Bsu_Settings.this, About.class);
        startActivity(view_classes);

    }

    public void dark_cover(View view) {
        Intent view_classes = new Intent(Bsu_Settings.this, General_Settings.class);
        startActivity(view_classes);
        finish();
    }

    public void send_feed_back(View view) {
        Intent feedback = new Intent(getApplicationContext(),FeedbackSuggestion.class);
        startActivity(feedback);
        finish();
    }

    public void logout(View view) {
        Intent logout = new Intent(getApplicationContext(), Logout.class);
        startActivity(logout);
        finish();
    }

    public void try_to_login(View view) {
        Intent login = new Intent(getApplicationContext(), Login.class);
        startActivity(login);
        finish();
    }

    public void try_to_register(View view) {

        Intent register = new Intent(getApplicationContext(), Register.class);
        startActivity(register);
        finish();
    }

    public void get_notified(View view) {
//        Intent register = new Intent(getApplicationContext(), Notification_BSU.class);
//        startActivity(register);

    }
}
