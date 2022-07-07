package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class confirmation extends AppCompatActivity
{

    Button btSMS;
    Button btQR;

    TextView tvMenu;
    TextView tvMontant;
    TextView tvAdresse;
    TextView tvName;
    TextView tvTel;

    ImageView qrCode;
    Bitmap bitmap;
    EditText edName;
    EditText edTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        // GUI
        tvMenu = (TextView) findViewById(R.id.tvMenu);
        tvMontant = (TextView) findViewById(R.id.tvMontant);
        tvName = (TextView) findViewById(R.id.tvName);
        tvTel = (TextView) findViewById(R.id.tvTel);
        tvAdresse = (TextView) findViewById(R.id.tvAdresse);

        Intent intent = getIntent();

        tvMenu.setText(tvMenu.getText().toString() + intent.getStringExtra("Menu"));
        tvMontant.setText(tvMontant.getText().toString() + intent.getStringExtra("MontantR"));
        tvName.setText(tvName.getText().toString() + intent.getStringExtra("Name"));
        tvTel.setText(tvTel.getText().toString() + intent.getStringExtra("Telephone"));
        tvAdresse.setText(tvAdresse.getText().toString() + intent.getStringExtra("Adresse"));

        String Name = intent.getStringExtra("Name");
        String Menu = intent.getStringExtra("Menu");
        String Montant = intent.getStringExtra("MontantR");
        String Tel = intent.getStringExtra("Telephone");
        String Adresse = intent.getStringExtra("Adresse");


        btSMS=(Button)findViewById(R.id.btSMS);

        String newLine = System.getProperty("line.separator");


        String strDest = Tel;
        String strSMS = "Bonjour "+newLine+Name+","+newLine+"Votre commande a été validée"+newLine+ Menu + newLine + Montant + "€" + newLine
                + "elle vous sera livrée à " + Adresse ;



        btSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SmsManager smgr = SmsManager.getDefault();
                    smgr.sendTextMessage(strDest,null,strSMS,null,null);
                    Toast.makeText(confirmation.this, "SMS Sent to" +strDest, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(confirmation.this, "Failed to Send to " +  strDest + ". Try again", Toast.LENGTH_SHORT).show();
                }

            }

        });


            // GUI
            qrCode=(ImageView)findViewById(R.id.imageViewQR);
            edName=(EditText)findViewById(R.id.edName);
            edTel=(EditText)findViewById(R.id.edPhone);
            btQR=(Button)findViewById(R.id.btQR);


            // cliquer sur le button pour générer le QR
            btQR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String strQRcode = "";
                    strQRcode= "https://www.dominos.fr/";
                    try
                    {
                        bitmap = TextToImageEncode(strQRcode,600);
                        qrCode.setImageBitmap(bitmap);
                    }
                    catch (Exception e) {
                        Log.d("Tag", "Err");
                    }
                }
            });

        }


        //méthode pour générer l’image sous forme bitmap
    // ressources utilisées : par zxing
    private Bitmap TextToImageEncode(String Value, int TailleQR) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE, TailleQR, TailleQR, null
            );
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.white):getResources().getColor(R.color.black);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, TailleQR, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


}