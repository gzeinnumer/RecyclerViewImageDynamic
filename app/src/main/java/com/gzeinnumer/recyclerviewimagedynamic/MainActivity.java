package com.gzeinnumer.recyclerviewimagedynamic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gzeinnumer.eeda.helper.FGFile;
import com.gzeinnumer.eeda.helper.imagePicker.FileCompressor;
import com.gzeinnumer.rab.base.BaseBuilderAdapter;
import com.gzeinnumer.rab.helper.BindViewHolder;
import com.gzeinnumer.rab.singleType.AdapterBuilder;
import com.gzeinnumer.rab.singleType.AdapterCreator;
import com.gzeinnumer.recyclerviewimagedynamic.databinding.ItemRvBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    AdapterCreator<String> adapterRV;
    RecyclerView recyclerView;
    Button getAllData;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllData = findViewById(R.id.getAllData);
        recyclerView = findViewById(R.id.recyclerView);
        initAdapter();

        getAllData.setOnClickListener(v -> initGetDataPerIndex());
    }

    private void initAdapter() {

        arrayList.add("1");
        AdapterCreator<String> adapter = new AdapterBuilder<String>(R.layout.item_rv)
                .setList(arrayList)
                .onBind(new BindViewHolder<String>() {
                    @Override
                    public void bind(AdapterCreator<String> adapter, View holder, String data, int position) {
                        //adapter.notifyDataSetChanged();
                        adapterRV = adapter;

                        //R.layout.rv_item = RvItemBinding
                        ItemRvBinding bindingItem = ItemRvBinding.bind(holder);

                        bindingItem.btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dispatchTakePictureIntent(bindingItem.btn);
                            }
                        });
                    }
                });

        recyclerView.setAdapter(adapter);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void initGetDataPerIndex() {
        String str = "";

        int countItem = adapterRV.getItemCount();

        for (int i = 0; i < countItem; i++) {
            BaseBuilderAdapter.MyHolder holder = (BaseBuilderAdapter.MyHolder) recyclerView.findViewHolderForAdapterPosition(i);
            str += i + "_";
        }

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private ImageView imageView;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private File mPhotoFile;
    private FileCompressor mCompressor;

    //3
    //jalankan intent untuk membuka kamera
    @SuppressLint("QueryPermissionsNeeded")
    private void dispatchTakePictureIntent(ImageView btn) {
        imageView = btn;

        mCompressor = new FileCompressor(this);
        // int quality = 50;
        // mCompressor = new FileCompressor(this, quality);
        //   /storage/emulated/0/MyLibsTesting/Foto
        mCompressor.setDestinationDirectoryPath("/Foto");
        //diretori yang dibutuhkan akan lansung dibuatkan oleh fitur ini

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                @SuppressLint("SimpleDateFormat") String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                photoFile = FGFile.createImageFile(getApplicationContext(), fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //4
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                    Glide.with(MainActivity.this).load(mPhotoFile).into(imageView);
                    Toast.makeText(this, "Image Path : " + mPhotoFile.toString(), Toast.LENGTH_SHORT).show();
                    arrayList.add("");
                    adapterRV.setList(arrayList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}