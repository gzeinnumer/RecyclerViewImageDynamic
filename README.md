# RecyclerViewImageDynamic

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

- `DynamicImageAdapter.java`
```java
public class DynamicImageAdapter extends RecyclerView.Adapter<DynamicImageAdapter.MyHolder> {

    private OnItemClick click;

    public interface OnItemClick {
        void onItemClick(int position, String data, ImageView btn);
    }

    public void setOnClickListener(OnItemClick onClick) {
        click = onClick;
    }

    public ArrayList<String> list;

    public DynamicImageAdapter(ArrayList<String> list) {
        this.list = list;
    }

    public void add() {
        list.add("");
        notifyItemInserted(list.size() - 1);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bindData(list.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onItemClick(position, "", holder.btn);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        public ImageView btn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn);
        }

        public void bindData(String s) {

        }
    }
}
```

- `MainActivity.java`
```java
adapter = new DynamicImageAdapter(arrayList);
adapter.setOnClickListener(new DynamicImageAdapter.OnItemClick() {
    @Override
    public void onItemClick(int position, String data, ImageView btn) {
        dispatchTakePictureIntent(btn);
    }
});

adapter.add();

recyclerView.setAdapter(adapter);
recyclerView.hasFixedSize();
recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
```

|![](https://github.com/gzeinnumer/RecyclerViewImageDynamic/blob/master/preview/example1.jpg)|![](https://github.com/gzeinnumer/RecyclerViewImageDynamic/blob/master/preview/example2.jpg)|![](https://github.com/gzeinnumer/RecyclerViewImageDynamic/blob/master/preview/example3.jpg)|
|---|---|---|

---

```
Copyright 2021 M. Fadli Zein
```