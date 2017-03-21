package vbit.vfix.activity;

import vbit.vfix.helper.SQLiteHandler;
import vbit.vfix.helper.SessionManager;
import vbit.vfix.R;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private Button Cbutton;
    private Button Ebutton;
    private Button Cmbutton;
    private Button Abutton;
    private Button Phbutton;
    private Button Pbutton;
    private Button btLogout;

    private SQLiteHandler db;
    private SessionManager session;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ebutton = (Button) findViewById(R.id.Ebutton);
        Cbutton = (Button) findViewById(R.id.Cbutton);
        Cmbutton = (Button) findViewById(R.id.Cmbutton);
        Pbutton = (Button) findViewById(R.id.Pbutton);
        Phbutton = (Button) findViewById(R.id.Phbutton);
        Abutton = (Button) findViewById(R.id.Abutton);
        btLogout= (Button) findViewById(R.id.btlogout);


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }



        Ebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n1 =new Intent(MainActivity.this,ElectricianActivity.class);
                startActivity(n1);
                finish();
            }
        });


        Cbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent n =new Intent(MainActivity.this,CarpenterActivity.class);
                startActivity(n);
                finish();
            }
        });


        Pbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent n2 =new Intent(MainActivity.this,PlumberActivity.class);
                startActivity(n2);
                finish();
            }
        });
        Phbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent n3 =new Intent(MainActivity.this,PhotographerActivity.class);
                startActivity(n3);
                finish();
            }
        });
        Abutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent n4 =new Intent(MainActivity.this,AcrepairActivity.class);
                startActivity(n4);
                finish();
            }
        });
        Cmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n5 =new Intent(MainActivity.this,ComputerActivity.class);
                startActivity(n5);
                finish();
            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }

        });



    }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}