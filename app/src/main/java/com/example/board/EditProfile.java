package com.example.board;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.board.util.PrefManager;
import com.example.board.util.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.board.Login.MY_PREFS_NAME;

@SuppressWarnings("ALL")
public class EditProfile extends AppCompatActivity {
    private static final int REQUEST_CAMERA=1, SELECT_FILE=0;
    Uri imageUri;
    private ImageView profileimage;
    EditText name,email,phone,password;
    String email_holder,password_holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        profileimage = findViewById(R.id.iv_user);
        //update_name,update_email,update_phone_number,update_password
        name = findViewById(R.id.update_name);
        email = findViewById(R.id.update_email);
        phone = findViewById(R.id.update_phone_number);
        password = findViewById(R.id.update_password);



        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("userEmail", "Enter your email");//"No name defined" is the default value.
        String idName = prefs.getString("userPassword", "Enter Password");
        email.setText(name);
        password.setText(idName);

       // init();
    }
    public void update_my_profile(View view) {
        String a_name = name.getText().toString();
        String a_email = email.getText().toString();
        String a_phone = phone.getText().toString();
        String a_password =password.getText().toString();

        if(TextUtils.isEmpty(a_name)){
            name.setError("Required..Name");
            name.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(a_email)){
            email.setError("Email Address is Required");
            email.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(a_email).matches()) {
            email.setError("Please Enter a Valid Email!");
            email.requestFocus();
        }

        if(TextUtils.isEmpty(a_phone)){
            phone.setError("Enter Your Phone Number");
            phone.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(a_password)){
            password.setError("Enter a Password");
            password.requestFocus();
            return;
        }
        new update_user_data().execute();
    }



    @SuppressLint("StaticFieldLeak")
    public class update_user_data extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(EditProfile.this);
            dialog.setMessage("saving, please wait");
            dialog.setTitle("Connecting... ");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            @SuppressLint("WrongThread")
            final String a_name = name.getText().toString();
            final String a_email = email.getText().toString();
            final String a_phone = phone.getText().toString();
            final String a_password = password.getText().toString();
           // final String pic_url = profileimage.getDrawable().toString();

            try {

                List<NameValuePair> insert = new ArrayList<NameValuePair>();
                insert.add(new BasicNameValuePair("user_email", a_email));
                insert.add(new BasicNameValuePair("user_name", a_name));
                insert.add(new BasicNameValuePair("user_password", a_password));
                insert.add(new BasicNameValuePair("user_phone",a_phone));
               // insert.add(new BasicNameValuePair("image_path",pic_url));



                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(
                        "http://newsportal.softcodes.tech/update_user.php"); // link to connect to database
                httpPost.setEntity(new UrlEncodedFormEntity(insert));

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();

                return true;


            } catch (IOException e) {
                e.printStackTrace();

            }


            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();

            androidx.appcompat.app.AlertDialog.Builder ac = new androidx.appcompat.app.AlertDialog.Builder(EditProfile.this);
            ac.setTitle("Update");
            ac.setMessage("Updating info!");
            ac.setCancelable(true);

            ac.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            name.setText("");
                            email.setText("");
                            password.setText("");
                            phone.setText("");

                        }
                    });

            androidx.appcompat.app.AlertDialog alert = ac.create();
            alert.show();
        }

    }

    private void SelectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
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

    public void need_image (View view) {
        SelectImage();
    }


}
