package com.example.myfirstapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {

    EditText edName;
    EditText edTel;
    EditText edAdresse;
    Button btOk;
    CheckBox boxMenu1, boxMenu2, boxMenu3;
    EditText ed;
    Spinner s1;
    ImageButton btAppeler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //GUI

        edName = (EditText) findViewById(R.id.edName);
        edTel = (EditText) findViewById(R.id.edPhone);
        edAdresse = (EditText) findViewById(R.id.edAdresse);
        btOk = (Button) findViewById(R.id.btOk);
        boxMenu1 = (CheckBox) findViewById(R.id.boxMenu1);
        boxMenu2 = (CheckBox) findViewById(R.id.boxMenu2);
        boxMenu3 = (CheckBox) findViewById(R.id.boxMenu3);
        btAppeler = (ImageButton) findViewById(R.id.btAppeler);



        // click on button Order
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int montant = 0;
                int menu = 0;
                // la méthode isChecked pour vérifier si un checkbox/radiobutton est choisi
                if (boxMenu1.isChecked()) {
                    montant = montant + 10;
                    menu = 1;
                }
                if (boxMenu2.isChecked()) {
                    montant = montant + 14;
                    menu = 2;
                }
                if (boxMenu3.isChecked()) {
                    montant = montant + 15;
                    menu = 3;
                }

                String strMenu;
                strMenu = "Menu : " + String.valueOf(menu);

                String strMontant;
                int montantR = montant;
                strMontant = "Montant : " + String.valueOf(montantR);


                String strName;
                strName = "Nom : " + edName.getText().toString();

                String strTel;
                strTel = "Telephone : " + edTel.getText().toString();

                String strAdresse;
                strAdresse = "Adresse : " + edAdresse.getText().toString();


                Intent intent = new Intent(MainActivity.this, confirmation.class);
                intent.putExtra("Name", strName);
                intent.putExtra("Telephone", strTel);
                intent.putExtra("Menu", strMenu);
                intent.putExtra("Adresse", strAdresse);
                intent.putExtra("MontantR", strMontant);// send text(ed)
                intent.putExtra("Name", strName);

                startActivity(intent);


            }


        });

        btAppeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT > 22) { // nécessaire pour les "récents" Android
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    }
                    String strNumber = "0695295988";
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + strNumber));
                    startActivity(callIntent);
                }
            }
        });
    }
}