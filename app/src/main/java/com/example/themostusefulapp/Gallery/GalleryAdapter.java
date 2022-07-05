package com.example.themostusefulapp.Gallery;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themostusefulapp.R;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private ArrayList<GalleryData> gallery = new ArrayList<>();
    private final Context context;

//    //커스텀 리스너 인터페이스 정의
//    public interface OnItemClickListener {
//        void onItemClick(View v, int pos);
//    }
//
//    //리스너 객체를 전달하는 메소드 setOnItemClickListener와 전달된 객체를 저장할 변수 mListener 추가
//    private OnItemClickListener mListener = null;
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.mListener = listener;
//    }

    public GalleryAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.gallery_item,
                        parent,
                        false
                );
        return new ViewHolder(view);
    }


//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
//        ViewHolder holder=new ViewHolder(view);
////        return new GalleryAdapter.ViewHolder(view);
//        return holder;
//        Context context = parent.getContext();
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View view = inflater.inflate(R.layout.gallery_item, parent, false);
//        GalleryAdapter.ViewHolder vh = new GalleryAdapter.ViewHolder(view);
//        return vh;
//}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GalleryData galleryData=gallery.get(position);

        holder.imageView.setImageBitmap(BitmapFactory.decodeStream(galleryData.getImageView()));

//        GalleryData galleryData = gallery.get(position);
////        holder.itemView.setTag(position);
//        Bitmap bigphoto = BitmapFactory.decodeStream(galleryData.getImageView());
//
//        Log.d("Check HERE", "before holder " + (holder.imageView==null));
//        holder.imageView.setImageBitmap(bigphoto);
//
//        holder.imageView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, GalleryClickImageActivity.class);
//            Log.d("Check", "before imageview");
//            Log.d("Check", "before putExtra");
//            intent.putExtra("imageview", encodeToBase64(bigphoto, Bitmap.CompressFormat.JPEG, 1));
//            context.startActivity(intent);
//        });


    }

//    private String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
//    {
//        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
//        image.compress(compressFormat, quality, byteArrayOS);
//        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
//    }

    @Override
    public int getItemCount() {

        return gallery.size();
    }

    public void addImage(GalleryData galleryData) {
        this.gallery.add(galleryData);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagePhoto);
//            Log.d("Check", "image"+(imageView == null) + imageView);
        }


    }
}
