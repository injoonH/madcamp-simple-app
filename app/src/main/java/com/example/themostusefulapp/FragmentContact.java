package com.example.themostusefulapp;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;


public class FragmentContact extends Fragment {
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private ContactsRecViewAdapter adapter;

    public FragmentContact() {
        super(R.layout.fragment_contact);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Context context = view.getContext();

        /* ==== handle permission ==== */

        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissionsGranted -> {
                    for (Map.Entry<String, Boolean> permission : permissionsGranted.entrySet()) {
                        if (permission.getValue()) {
                            // Permission is granted. Continue the action or workflow in your app.
                            Log.d("PERMISSION", "requestPermissionLauncher :: " + permission.getKey() + " granted");
                            if (permission.getKey().equals(Manifest.permission.READ_CONTACTS)) {
                                Log.d("PERMISSION", "Load contacts data from the device for the first time");
                                getContactList(context);
                            }
                        } else {
                            // Explain to the user that the feature is unavailable because the
                            // features requires a permission that the user has denied. At the
                            // same time, respect the user's decision. Don't link to system
                            // settings in an effort to convince the user to change their
                            // decision.
                            Log.d("PERMISSION", "requestPermissionLauncher :: " + permission.getKey() + " unavailable");
                        }
                    }
                });

        /* ==== create RecyclerView ==== */

        adapter = new ContactsRecViewAdapter(context);
        RecyclerView contactsRecView = view.findViewById(R.id.contactsRecView);
        contactsRecView.setAdapter(adapter);
        contactsRecView.setLayoutManager(new LinearLayoutManager(context));

        /* ==== create new contact ==== */

        EditText editName = view.findViewById(R.id.editName);
        EditText editNumber = view.findViewById(R.id.editNumber);
        Button contactAddBtn = view.findViewById(R.id.contactAddBtn);

        // TODO: Get photo from user
        contactAddBtn.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String number = editNumber.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (number.isEmpty()) {
                Toast.makeText(context, "Number is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            adapter.addContact(new Contact(name, number, null));
        });

        /* ==== request contacts permission ==== */

        requestPermission(context, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE});
    }

    private void requestPermission(Context context, String[] permissions) {
        final ArrayList<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            // You can use the API that requires the permission.
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                Log.d("PERMISSION", "Permission " + permission + " already granted");
                if (permission.equals(Manifest.permission.READ_CONTACTS)) {
                    Log.d("PERMISSION", "Load contacts data from the device");
                    getContactList(context);
                }
                continue;
            }

            if (shouldShowRequestPermissionRationale(permission)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                Log.d("PERMISSION", "Show rationale for " + permission);
            } else {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                Log.d("PERMISSION", "Request " + permission);
            }
            permissionsToRequest.add(permission);
        }

        if (!permissionsToRequest.isEmpty())
            requestPermissionLauncher.launch(permissionsToRequest.toArray(new String[0]));
    }

    private void getContactList(Context context) {
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC "
        );

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(
                    ContactsContract.Contacts._ID
            ));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(
                    ContactsContract.Contacts.DISPLAY_NAME
            ));

            Cursor phoneCursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                    new String[]{id},
                    null
            );

            if (phoneCursor.moveToNext()) {
                String number = phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                ));
                InputStream photo = getPhotoStream(context, Long.parseLong(id));

                adapter.addContact(new Contact(name, number, photo));
            }
            phoneCursor.close();
        }
        cursor.close();
    }

    private InputStream getPhotoStream(Context context, long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = context.getContentResolver().query(
                photoUri,
                new String[]{ContactsContract.Contacts.Photo.PHOTO},
                null,
                null,
                null
        );

        if (cursor == null)
            return null;

        if (cursor.moveToFirst()) {
            byte[] data = cursor.getBlob(0);
            if (data != null) {
                cursor.close();
                return new ByteArrayInputStream(data);
            }
        }

        cursor.close();
        return null;
    }
}