package com.example.agenda;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {

    private static Context mContext;
    private ContactItemView.ContactAdapter contactAdapater;
    public void setConfig(RecyclerView recyclerView, Context context, List<Contacto> contactos, List<String> keys){
        mContext = context;
        contactAdapater = new ContactItemView.ContactAdapter(contactos, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(contactAdapater);
    }

    static class ContactItemView extends RecyclerView.ViewHolder{
        private TextView nombre;
        private TextView telefono;
        private String key;

        public ContactItemView (ViewGroup parent){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.contact_list_item, parent,false));

            nombre = (TextView) itemView.findViewById(R.id.tvNombre);
            telefono = (TextView) itemView.findViewById(R.id.tvNumero);
        }

        public void bind (Contacto contact, String key){
            nombre.setText(contact.getNombre());
            telefono.setText(contact.getTelefono());
            this.key = key;
        }

        static class ContactAdapter extends RecyclerView.Adapter<ContactItemView>{
            private List<Contacto> mContactList;
            private List<String> mKeys;

            public ContactAdapter(List<Contacto> mContactList, List<String> keys) {
                this.mContactList = mContactList;
                this.mKeys = keys;
            }

            @NonNull
            @Override
            public ContactItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return  new ContactItemView(parent);
            }

            @Override
            public void onBindViewHolder(@NonNull ContactItemView holder, int position) {
                holder.bind(mContactList.get(position), mKeys.get(position));
            }

            @Override
            public int getItemCount() {
                return mContactList.size();
            }
        }
    }
}
