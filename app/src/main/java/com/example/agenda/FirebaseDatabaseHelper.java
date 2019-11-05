package com.example.agenda;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceContacts;
    private List<Contacto> contacts = new ArrayList<>();

    public interface DataStatus{
        void dataIsLoaded(List<Contacto> contacts, List<String> keys);
        void dataInserted();
        void dataUpdated();
        void dataDeleted();
    }


    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceContacts = mDatabase.getReference("contactos");
    }

    public void readContacts(final DataStatus dataStatus){
        mReferenceContacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contacts.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Contacto contact = keyNode.getValue(Contacto.class);
                    contacts.add(contact);
                }
                dataStatus.dataIsLoaded(contacts, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
