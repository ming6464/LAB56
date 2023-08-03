package com.fpoly.mob403_lab56;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.mob403_lab56.Adapter.PictureAdapter;
import com.fpoly.mob403_lab56.DTO.Picture;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements PictureAdapter.EventPictureAdapter {

    private Retrofit retrofit;
    public API_Picture api_picture;

    public PictureAdapter adapter;

    public RecyclerView rc;

    private List<Picture> pictures;

    private static final String TAG = "TAG1231";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pictures = new ArrayList<>();
        retrofit = new Retrofit.Builder().baseUrl(API_Picture.URL).addConverterFactory(GsonConverterFactory.create()).build();
        api_picture = retrofit.create(API_Picture.class);
        rc = findViewById(R.id.ActiMain_recycler_load);
        adapter = new PictureAdapter(this);
        adapter.SetData(pictures);
        rc.setAdapter(adapter);
        rc.setLayoutManager(new LinearLayoutManager(this));
    }

    public void ActionButtonLoad(View view) {
        Call<List<Picture>> call =api_picture.GetAll();
        call.enqueue(new Callback<List<Picture>>() {
            @Override
            public void onResponse(Call<List<Picture>> call, Response<List<Picture>> response) {
                try {
                    pictures = response.body();
                    adapter.SetData(pictures);
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<List<Picture>> call, Throwable t) {

            }
        });
    }

    @Override
    public void UpdateElement(Picture picture) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_update_element,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText ed_name = view.findViewById(R.id.dialogUpdateElement_ed_name);
        EditText ed_url = view.findViewById(R.id.dialogUpdateElement_ed_url);
        Button btn_canle = view.findViewById(R.id.dialogUpdateElement_btn_cancle);
        Button btn_update = view.findViewById(R.id.dialogUpdateElement_btn_udpate);

        ed_name.setText(picture.getName());
        ed_url.setText(picture.getUrl());

        btn_update.setOnClickListener(view1 -> {
            String name =ed_name.getText().toString();
            String url =ed_url.getText().toString();
            if(name.isEmpty() || url.isEmpty()) return;


            Picture picture1 = new Picture(picture.get_id(),name,url);
            Call<Picture> call = api_picture.UpdateElement(picture.get_id(),picture1);
            call.enqueue(new Callback<Picture>() {
                @Override
                public void onResponse(Call<Picture> call, Response<Picture> response) {
                    try {
                        int index = pictures.indexOf(picture);
                        pictures.set(index,picture1);
                        adapter.notifyItemChanged(index);
                        Toast.makeText(MainActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }catch (Exception e){

                    }
                }

                @Override
                public void onFailure(Call<Picture> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });

        btn_canle.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void DeleteElement(String _id) {
        Call<Picture> call = api_picture.DeleteElement(_id);
        call.enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(Call<Picture> call, Response<Picture> response) {

                int index = -1;

                for(Picture p : pictures){
                    index ++;
                    if(p.get_id() == _id){
                        break;
                    }
                }
                pictures.remove(index);
                adapter.notifyItemRemoved(index);
                Toast.makeText(MainActivity.this, "Xoá Thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Picture> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Xoá Thất Bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}