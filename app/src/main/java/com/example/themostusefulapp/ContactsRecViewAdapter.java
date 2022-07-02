package com.example.themostusefulapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ContactsRecViewAdapter extends RecyclerView.Adapter<ContactsRecViewAdapter.ViewHolder> {
    private final ArrayList<Contact> contacts = new ArrayList<>();
    private final Context context;

    public ContactsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.contacts_list_item,
                        parent,
                        false
                );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);

        holder.txtName.setText(contact.getName());
        holder.txtNumber.setText(contact.getNumber());
        holder.photo.setImageBitmap(BitmapFactory.decodeStream(contact.getPhoto()));

        holder.parent.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(context, "Phone permission denied", Toast.LENGTH_SHORT).show();
                return;
            }

            /* ==== Intent phone call ==== */

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + contact.getNumber()));
            context.startActivity(callIntent);
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
        notifyDataSetChanged();
    }

    /* ==== ViewHolder ==== */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView photo;
        private final TextView txtName, txtNumber;
        private final CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            photo = itemView.findViewById(R.id.photo);
            txtName = itemView.findViewById(R.id.txtName);
            txtNumber = itemView.findViewById(R.id.txtNumber);
        }
    }
}
