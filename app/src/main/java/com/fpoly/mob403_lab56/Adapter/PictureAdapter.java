package com.fpoly.mob403_lab56.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.mob403_lab56.DTO.Picture;
import com.fpoly.mob403_lab56.GeneralFunc;
import com.fpoly.mob403_lab56.R;

import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder>{

    private EventPictureAdapter event;
    private List<Picture> listData;

    public PictureAdapter(EventPictureAdapter event){
        this.event = event;
    }

    public void SetData(List<Picture> listData){
        this.listData = listData;
        this.notifyDataSetChanged();
    }

    public interface EventPictureAdapter{
        void UpdateElement(Picture picture);
        void DeleteElement(String _id);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picture picture = listData.get(position);
        GeneralFunc.loadImg(picture.getUrl(),holder.img_avatar);
        holder.tv_name.setText(picture.getName());
        holder.btn_update.setOnClickListener(v -> event.UpdateElement(picture));
        holder.btn_delete.setOnClickListener(v -> event.DeleteElement(picture.get_id()));
    }

    @Override
    public int getItemCount() {
        if(listData == null) return 0;
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Button btn_update;
        private Button btn_delete;
        private ImageView img_avatar;
        private TextView tv_name;

        public ViewHolder(@NonNull View it) {
            super(it);
            btn_update = it.findViewById(R.id.itemImage_btn_edit);
            btn_delete = it.findViewById(R.id.itemImage_btn_delete);
            img_avatar = it.findViewById(R.id.itemImage_img_icon);
            tv_name = it.findViewById(R.id.itemImage_tv_name);
        }
    }
}
