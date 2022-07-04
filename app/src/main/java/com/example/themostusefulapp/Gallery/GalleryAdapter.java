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

    private final ArrayList<GalleryData> gallery = new ArrayList<>();
    private final Context context;

//    //커스텀 리스너 인터페이스 정의
//    public interface OnItemClickListener{
//        void onItemClick(View v, int pos);
//    }
//
//    //리스너 객체를 전달하는 메소드 setOnItemClickListener와 전달된 객체를 저장할 변수 mListener 추가
//    private  OnItemClickListener mListener=null;
//    public void setOnItemClickListener(OnItemClickListener listener){
//        this.mListener=listener;
//    }

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new GalleryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GalleryData galleryData=gallery.get(position);

        holder.imageView.setImageBitmap(BitmapFactory.decodeStream(galleryData.getImageView()));

    }

    @Override
    public int getItemCount() {
        return gallery.size();
    }

    public void addImage(GalleryData galleryData) {
        this.gallery.add(galleryData);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
//            //아이템 클릭 이벤트 핸들러 메소드에서 리스너 객체 메소드(onItemClick) 호출
//            itemView.setOnClickListener(v -> {
//                int pos=getAdapterPosition();
//                if(pos!=RecyclerView.NO_POSITION){
//                    if(mListener!=null){
//                        mListener.onItemClick(v, pos);
//                    }
//                }
//            });

        }
    }
}
