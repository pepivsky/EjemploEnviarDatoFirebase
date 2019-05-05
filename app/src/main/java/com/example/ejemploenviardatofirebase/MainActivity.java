package com.example.ejemploenviardatofirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String PATH_START = "start";
    private static final String PATH_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //FirebaseApp.initializeApp(this);

        final TextView tvMessage = findViewById(R.id.tvMessage);
        final EditText etMesaage = findViewById(R.id.etMessage);
        final Button btnSend = findViewById(R.id.btnSend);


        //Instancia y referencia de la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference(PATH_START).child(PATH_MESSAGE);


        //Método que se ejecuta cuando los datos de la bd cambian
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvMessage.setText(dataSnapshot.getValue(String.class));
            }

            //Método que se ejecuta cuando hay un error
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error al consultar en firebase.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Enviar dato a Firebase
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference(PATH_START).child(PATH_MESSAGE);
                //Se guarda el dato en la variable referencia
                reference.setValue(etMesaage.getText().toString().trim());
                //etMesaage.setText("");
            }
        });
    }
}
