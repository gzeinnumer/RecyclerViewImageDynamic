# RecyclerViewImageDynamic

- `gradle`
```gradle
//maven { url 'https://jitpack.io' }
implementation 'com.github.gzeinnumer:EasyExternalDirectoryAndroid:2.2.0'
implementation 'com.github.gzeinnumer:RecyclerViewAdapterBuilder:2.2.0'
```

 - Take Image From Camera [Tutorial](https://github.com/gzeinnumer/EasyExternalDirectoryAndroid/blob/master/README_4.md#take-image-from-camera-and-compress)

- `item_rv.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="8dp"
    android:gravity="center"
    android:orientation="horizontal">

    <ImageView
        android:id="@+id/btn"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:scaleType="center"
        android:src="@drawable/ic_baseline_add_a_photo_24" />
</LinearLayout>
```

- [MainActivity.java](https://github.com/gzeinnumer/RecyclerViewImageDynamic/blob/master/app/src/main/java/com/gzeinnumer/recyclerviewimagedynamic/MainActivity.java)
```java

public class MainActivity extends AppCompatActivity {

    private AdapterCreator<String> adapterRV;

    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ...

        initAdapter();
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

        for (int i=0; i<countItem; i++){
            BaseBuilderAdapter.MyHolder holder = (BaseBuilderAdapter.MyHolder) recyclerView.findViewHolderForAdapterPosition(i);
            str += i +"_";
        }

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private ImageView imageView;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private File mPhotoFile;
    private FileCompressor mCompressor;

    @SuppressLint("QueryPermissionsNeeded")
    private void dispatchTakePictureIntent(ImageView btn) {
        imageView = btn;

        ...
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ...

                    Glide.with(MainActivity.this).load(mPhotoFile).into(imageView);
        ...
    }
}
```

|![](https://github.com/gzeinnumer/RecyclerViewImageDynamic/blob/master/preview/example1.jpg)|![](https://github.com/gzeinnumer/RecyclerViewImageDynamic/blob/master/preview/example2.jpg)|![](https://github.com/gzeinnumer/RecyclerViewImageDynamic/blob/master/preview/example3.jpg)|
|---|---|---|

---

```
Copyright 2021 M. Fadli Zein
```