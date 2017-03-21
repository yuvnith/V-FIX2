package vbit.vfix.activity;

import vbit.vfix.app.AppConfig;
import vbit.vfix.app.AppController;
import vbit.vfix.helper.SQLiteHandler;
import vbit.vfix.helper.SessionManager;
import vbit.vfix.R;
import android.util.Patterns;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class PhotographerActivity extends Activity {


    private EditText Fullname;
    private EditText Email;
    private EditText Address;
    private EditText Phone;
    private EditText Date;
    private EditText Time;
    private ProgressDialog pDialog;
    Button Book;

    String insertUrl="http://192.168.1.9/android_login_api/photographer.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer);

        Fullname=(EditText) findViewById(R.id.Pname);
        Email=(EditText)findViewById(R.id.Pemail);
        Address=(EditText)findViewById(R.id.Paddress);
        Phone=(EditText)findViewById(R.id.Pphone);
        Date=(EditText)findViewById(R.id.Pdate);
        Time=(EditText)findViewById(R.id.Ptime);
        Book=(Button)findViewById(R.id.Pbook);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // Register Button Click event
        Book.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = Fullname.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String address = Address.getText().toString().trim();
                String phone = Phone.getText().toString().trim();
                String date = Date.getText().toString().trim();
                String time= Time.getText().toString().trim();



                if (!name.isEmpty() && !email.isEmpty() && !address.isEmpty() && !phone.isEmpty())
                {
                    if(isValidEmail(email))
                    {
                        if(phone.length()>=10 && phone.length()<=12)
                        {

                            if (phone.matches("^[789]\\d{9}$"))
                            {

                                if(name.length()<=15)
                                {

                                    Bookuser(name, email, address, phone, date, time);


                                }


                            }
                            else {
                                Toast.makeText(getApplicationContext(),
                                        "enter a correct mobile number", Toast.LENGTH_LONG)
                                        .show();
                            }

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),
                                    "mobile number should be 10 digits", Toast.LENGTH_LONG)
                                    .show();
                        }


                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                "Please enter your email correctly", Toast.LENGTH_LONG)
                                .show();
                    }





                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });


    }

    @Override

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }


    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }





    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private void Bookuser(final String name, final String email,final String phone,
                          final String address,final String date,final String time) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                insertUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");


                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("pname", name);
                params.put("pemail", email);
                params.put("paddress", address);
                params.put("pphone", phone);
                params.put("pdate", date);
                params.put("ptime", time);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }




}