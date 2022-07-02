package com.example.themostusefulapp;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class FragmentGallery extends Fragment {

    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private GalleryAdapter adapter;

    public FragmentGallery() {
        super(R.layout.fragment_gallery);
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
                            if (permission.getKey().equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                Log.d("PERMISSION", "Load gallery data from the device for the first time");
                                getGalleryList(context);
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

        adapter = new GalleryAdapter(context);
        RecyclerView galleryRecycleView = view.findViewById(R.id.galleryRecycleView);
        galleryRecycleView.setAdapter(adapter);
        galleryRecycleView.setLayoutManager(new LinearLayoutManager(context));

        /* ==== create new contact ==== */

        ImageView imageView = view.findViewById(R.id.image);

        /* ==== request contacts permission ==== */

        requestPermission(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }


    private void requestPermission(Context context, String[] permissions) {
        final ArrayList<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            // You can use the API that requires the permission.
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                Log.d("PERMISSION", "Permission " + permission + " already granted");
                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Log.d("PERMISSION", "Load gallery data from the device");
                    getGalleryList(context);
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

    private void getGalleryList(Context context) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Contacts.CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Images.CommonDataKinds.Phone.DISPLAY_NAME + " ASC "
        );

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(
                    Images.Contacts._ID
            ));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(
                    Images.Contacts.DISPLAY_NAME
            ));

            Cursor galleryCursor = context.getContentResolver().query(
                    Images.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    Images.CommonDataKinds.Phone.CONTACT_ID + "=?",
                    new String[]{id},
                    null
            );

            if (galleryCursor.moveToNext()) {
                String number = galleryCursor.getString(galleryCursor.getColumnIndexOrThrow(
                        Images.Phone.NUMBER
                ));
                InputStream gallery = getGalleryStream(context, Long.parseLong(id));

                adapter.addContact(new GalleryData(gallery));
            }
            galleryCursor.close();
        }
        cursor.close();
    }

    private InputStream getGalleryStream(Context context, long contactId) {
        Uri contactUri = ContentUris.withAppendedId(Images.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, Images.Photo.CONTENT_DIRECTORY);
        Cursor cursor = context.getContentResolver().query(
                photoUri,
                new String[]{Images.Photo.PHOTO},
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