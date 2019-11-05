package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etNumero;
    Button Guardar;
    DatabaseReference mbr;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = (EditText) findViewById(R.id.editText);
        etNumero = (EditText) findViewById(R.id.editText2);
        Guardar = (Button) findViewById(R.id.btnGuardar);
        mbr = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = (RecyclerView) findViewById(R.id.rvContactos);

        new FirebaseDatabaseHelper().readContacts(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void dataIsLoaded(List<Contacto> contacts, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView,MainActivity.this,contacts,keys);
            }

            @Override
            public void dataInserted() {

            }

            @Override
            public void dataUpdated() {

            }

            @Override
            public void dataDeleted() {

            }
        });

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombre.getText().toString();
                String telefono = etNumero.getText().toString();

                Map<String, Object> contactos = new HashMap<>();

                contactos.put("nombre", nombre);
                contactos.put("telefono", telefono);

                mbr.child("contactos").push().setValue(contactos);
                clear();
            }
        });


    }

    public void clear(){
        etNumero.setText("");
        etNombre.setText("");
    }
}
