package com.example.themostusefulapp.ThreeMenuFragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themostusefulapp.Gallery.GalleryAdapter;
import com.example.themostusefulapp.Gallery.GalleryClickImage;
import com.example.themostusefulapp.Gallery.GalleryData;
import com.example.themostusefulapp.R;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class FragmentGallery extends Fragment {

    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private GalleryAdapter adapter;
//    ImageView imageView1;

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

//        adapter.setOnItemClickListener(
//                new GalleryAdapter.OnItemClickListener(){
//                    @Override
//                    public void onItemClick(View v, int position){
//                        //아이템 클릭 이벤트를 FragmentGallery에서 처리리
//
//                   }
//                }
//        );

        /* ==== handle permission ==== */

        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissionsGranted -> {
                    for (Map.Entry<String, Boolean> permission : permissionsGranted.entrySet()) {
                        if (permission.getValue()) {
                            // Permission is granted. Continue the action or workflow in your app.
                            Log.d("PERMISSION", "requestPermissionLauncher :: " + permission.getKey() + " granted");
                            if (permission.getKey().equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
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
        RecyclerView galleryRecycleView = view.findViewById(R.id.galleryrecyclerview);
        galleryRecycleView.setAdapter(adapter);
        galleryRecycleView.setLayoutManager(new GridLayoutManager(context, 2));

        /* ==== create new contact ==== */

        ImageView imageView = view.findViewById(R.id.image);

        /* ==== request contacts permission ==== */

        requestPermission(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});


//        imageView1 = getView().findViewById(R.id.image);
//        Log.d("Check", "before clicklistener");
//        imageView1.setOnClickListener(v -> {
//            Log.d("Check", "before clicklistener2");
//            Intent intent = new Intent(getActivity(), GalleryClickImage.class);
//            startActivity(intent);
//        });
    }


    private void requestPermission(Context context, String[] permissions) {
        final ArrayList<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            // You can use the API that requires the permission.
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                Log.d("PERMISSION", "Permission " + permission + " already granted");
                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
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
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
                //MediaStore.Images.CommonDataKinds.Phone.DISPLAY_NAME + " ASC "
        );

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(
                    MediaStore.Images.ImageColumns._ID
            ));

            Cursor galleryCursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null,
                    MediaStore.Images.Media._ID + "=?",
                    new String[]{id},
                    null
            );

            if (galleryCursor.moveToNext()) {
                String imagepath = galleryCursor.getString(galleryCursor.getColumnIndexOrThrow(
                        MediaStore.Images.ImageColumns.DATA
                ));

                Log.d("Check DATA", imagepath);


                InputStream gallery;
                try{
                    gallery = new FileInputStream(imagepath);
                } catch (Exception e){
                    galleryCursor.close();
                    cursor.close();
                    return;
                }

                adapter.addImage(new GalleryData(gallery));
            }
            galleryCursor.close();
        }
        cursor.close();
    }

//    private InputStream getGalleryStream(Context context, long imageId) {
//        Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId);
//        Cursor cursor = context.getContentResolver().query(
//                imageUri,
//                new String[]{MediaStore.Images.Media._ID},
//                null,
//                null,
//                null
//        );
//
//        if (cursor == null)
//            return null;
//
//        Log.d("Check", "before moveToFirst");
//        if (cursor.moveToFirst()) {
//            Log.d("Check", "in moveToFirst");
//            byte[] data = cursor.getBlob(0);
//            if (data != null) {
//                Log.d("Check", "in moveToFirst2");
//                cursor.close();
//                return new ByteArrayInputStream(data);
//            }
//        }
//
//        Log.d("Check", "in moveToFirst3");
//        cursor.close();
//        return null;
//    }
}