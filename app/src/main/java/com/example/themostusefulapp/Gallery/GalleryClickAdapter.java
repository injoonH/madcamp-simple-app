//package com.example.themostusefulapp.Gallery;
//
//import android.content.Context;
//import android.graphics.BitmapFactory;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.themostusefulapp.R;
//
//import java.util.ArrayList;
//
//public class GalleryClickAdapter extends RecyclerView.Adapter<GalleryClickAdapter.ViewHolder> {
//    private ArrayList<GalleryData> oneImage = null;
//    @NonNull
//    @Override
//    public GalleryClickAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context=parent.getContext();
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View view = inflater.inflate(R.layout.gallery_item, parent, false);
//        GalleryClickAdapter.ViewHolder vh = new GalleryClickAdapter.ViewHolder(view);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull GalleryClickAdapter.ViewHolder holder, int position) {
//        holder.imageView.setImageBitmap(BitmapFactory.decodeStream(oneImage.get(position).getImageView()));
//    }
//
//    @Override
//    public int getItemCount() {
//        return oneImage.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            //뷰 객체에 대한 참조
//            imageView = itemView.findViewById(R.id.image);
//        }
//    }
//
//    GalleryClickAdapter(ArrayList<GalleryData> list){
//        oneImage=list;
//    }
//
//}
