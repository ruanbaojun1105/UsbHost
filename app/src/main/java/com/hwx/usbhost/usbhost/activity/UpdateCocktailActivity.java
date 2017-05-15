package com.hwx.usbhost.usbhost.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hwx.usbhost.usbhost.R;
import com.hwx.usbhost.usbhost.adapter.GlassListAdapter;
import com.hwx.usbhost.usbhost.adapter.ImageListAdapter;
import com.hwx.usbhost.usbhost.adapter.TextListAdapter;
import com.hwx.usbhost.usbhost.db.AccessoriesMore;
import com.hwx.usbhost.usbhost.db.Cocktail;
import com.hwx.usbhost.usbhost.db.Glass;
import com.hwx.usbhost.usbhost.db.OrnamentMore;
import com.hwx.usbhost.usbhost.db.manager.AccessoriesMoreDaoManager;
import com.hwx.usbhost.usbhost.db.manager.CocktailManager;
import com.hwx.usbhost.usbhost.db.manager.GlassDaoManager;
import com.hwx.usbhost.usbhost.db.manager.OrnamentMoreDaoManager;
import com.hwx.usbhost.usbhost.util.DialogUtil;
import com.hwx.usbhost.usbhost.util.DrawableUtil;
import com.hwx.usbhost.usbhost.widget.CommentLinearLayout;
import com.hwx.usbhost.usbhost.widget.ViewReUseFaceListener;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class UpdateCocktailActivity extends BaseActivity implements View.OnClickListener {
    private Cocktail cocktail;
    private EditText name_tv;
    private ImageView image_img;
    private ImageView image_glass;
    private LinearLayout accessories_lin;
    private HorizontalScrollView accessories_hor;
    private LinearLayout ornament_lin;
    private HorizontalScrollView ornament_hor;
    private Button save_data;

    private BottomSheetBehavior behavior;
    private RecyclerView recyclerView;
    private TextListAdapter adapter;
    private TextView reduce_tv;
    private TextView number_tv;
    private TextView add_tv;
    private View number_head;


    private List<AccessoriesMore> accessoriesList;
    private List<OrnamentMore> ornamentList;
    private TextView add_accessories;
    private TextView add_ornament;
    private LinearLayout behavior_lin;

    private boolean isNew;

    public static void openIntance(Context context, Cocktail cocktail) {
        Intent intent = new Intent(context, UpdateCocktailActivity.class);
        if (cocktail != null)
            intent.putExtra("cocktail", cocktail);//�༭�䷽ģʽ
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cocktail);
        initView();
        initData();
    }

    @Override
    public void onBackPressed() {
        if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            finish();
        } else {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                onBackPressed();
            } else {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            return true;
        }
        return false;
    }

    private void initData() {
        if (getIntent().getExtras() != null) {
            cocktail = (Cocktail) getIntent().getExtras().getSerializable("cocktail");
        }
        if (cocktail == null) {
            isNew=true;
            cocktail = new Cocktail();
            accessoriesList = new ArrayList<>();
            ornamentList = new ArrayList<>();
        } else {
            isNew=false;
            accessoriesList = AccessoriesMoreDaoManager.getAccessoriesMoreDaoManager().queryList(cocktail.getName());
            ornamentList = OrnamentMoreDaoManager.getOrnamentMoreDaoManager().queryList(cocktail.getName());
        }
        if (!TextUtils.isEmpty(cocktail.getGlass())) {
            try {
                DrawableUtil.displayImage(this,image_glass,GlassListAdapter.getTypeUrl(Integer.parseInt(cocktail.getGlass())));
                //image_glass.setImageResource(Integer.parseInt(cocktail.getGlass()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        name_tv.setText(cocktail.getName());
        if (!TextUtils.isEmpty(cocktail.getPreviewImage())) {
            if (TextUtils.isDigitsOnly(cocktail.getPreviewImage())) {
                DrawableUtil.displayImage(this, image_img, ImageListAdapter.getImageTypeUrl(Integer.parseInt(cocktail.getPreviewImage())));
            } else {
                DrawableUtil.displayImage(this, image_img, cocktail.getPreviewImage());
            }
        }
        initAccessories(accessoriesList);
        initOrnament(ornamentList);
    }

    private void initOrnament(List<OrnamentMore> ornamens) {
        ornament_hor.setVisibility(ornamens.isEmpty() ? View.GONE : View.VISIBLE);
        CommentLinearLayout.setLinGreat(this, ornament_lin, ornamens.toArray(), new ViewReUseFaceListener() {
            @Override
            public int backViewRes() {
                return 0;
            }

            @Override
            public View backView(Context context) {
                View itemView = getLayoutInflater().inflate(R.layout.add_item_update, ornament_lin, false);
                return itemView;
            }

            @Override
            public void justItemToDo(Object data, View itemView, int position, Context context) {
                TextView item_del = (TextView) itemView.findViewById(R.id.item_del);
                TextView item_text = (TextView) itemView.findViewById(R.id.item_text);
                OrnamentMore aa = (OrnamentMore) data;
                item_text.setText(aa.getOrnament());
                item_del.setOnClickListener(view -> {
                    ornamentList.remove(position);
                    initOrnament(ornamentList);
                });
            }
        });
    }

    private void initAccessories(List<AccessoriesMore> accessories) {
        accessories_hor.setVisibility(accessories.isEmpty() ? View.GONE : View.VISIBLE);
        CommentLinearLayout.setLinGreat(this, accessories_lin, accessories.toArray(), new ViewReUseFaceListener() {
            @Override
            public int backViewRes() {
                return 0;
            }

            @Override
            public View backView(Context context) {
                View itemView = getLayoutInflater().inflate(R.layout.add_item_update, accessories_lin, false);
                return itemView;
            }

            @Override
            public void justItemToDo(Object data, View itemView, int position, Context context) {
                TextView item_del = (TextView) itemView.findViewById(R.id.item_del);
                TextView item_text = (TextView) itemView.findViewById(R.id.item_text);
                AccessoriesMore aa = (AccessoriesMore) data;
                item_text.setText(aa.getAccessories() + "*" + aa.getAccessoriesNumber());
                item_del.setOnClickListener(view -> {
                    accessoriesList.remove(position);
                    initAccessories(accessoriesList);
                });
            }
        });
    }

    private void initView() {
        name_tv = (EditText) findViewById(R.id.name_tv);
        image_img = (ImageView) findViewById(R.id.image_img);
        image_glass = (ImageView) findViewById(R.id.image_glass);
        accessories_lin = (LinearLayout) findViewById(R.id.accessories_lin);
        accessories_hor = (HorizontalScrollView) findViewById(R.id.accessories_hor);
        ornament_lin = (LinearLayout) findViewById(R.id.ornament_lin);
        ornament_hor = (HorizontalScrollView) findViewById(R.id.ornament_hor);
        save_data = (Button) findViewById(R.id.save_data);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        add_accessories = (TextView) findViewById(R.id.add_accessories);
        behavior_lin = (LinearLayout) findViewById(R.id.behavior_lin);
        number_head = findViewById(R.id.number_head);
        add_accessories.setOnClickListener(this);
        add_ornament = (TextView) findViewById(R.id.add_ornament);
        add_ornament.setOnClickListener(this);

        save_data.setOnClickListener(this);
        image_img.setOnClickListener(this);
        image_glass.setOnClickListener(this);

        add_tv = (TextView) findViewById(R.id.add_tv);
        reduce_tv = (TextView) findViewById(R.id.reduce_tv);
        number_tv = (TextView) findViewById(R.id.number_tv);
        add_tv.setOnClickListener(view -> {
            int a = Integer.parseInt(number_tv.getText().toString()) + 1;
            number_tv.setText(String.valueOf(a >= 30 ? 30 : a));
        });
        reduce_tv.setOnClickListener(view -> {
            int a = Integer.parseInt(number_tv.getText().toString()) - 1;
            number_tv.setText(String.valueOf(a > 0 ? a : 1));
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new TextListAdapter(new ArrayList<>());

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        behavior = BottomSheetBehavior.from(behavior_lin);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    image_img.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
                image_img.setVisibility(View.VISIBLE);
                ViewCompat.setAlpha(image_img, 1 - slideOffset);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getExtras().getString(FilePickerActivity.RESULT_FILE_PATH, "");
            cocktail.setPreviewImage(filePath);
            DrawableUtil.displayImage(this, image_img, cocktail.getPreviewImage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_accessories:
                if (TextUtils.isEmpty(name_tv.getText().toString().trim())) {
                    Toast.makeText(this, R.string.inputnan, Toast.LENGTH_SHORT).show();
                    return;
                }
                number_head.setVisibility(View.VISIBLE);
                adapter.setOnRecyclerViewItemClickListener((view, i) -> {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    accessoriesList.add(new AccessoriesMore(null, cocktail.getName(), adapter.getItem(i), number_tv.getText().toString()));
                    initAccessories(accessoriesList);
                });
                adapter.setNewData(AccessoriesMoreDaoManager.getAccessoriesMoreDaoManager().queryListNew());
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            case R.id.add_ornament:
                if (TextUtils.isEmpty(name_tv.getText().toString().trim())) {
                    Toast.makeText(this, R.string.inputnan, Toast.LENGTH_SHORT).show();
                    return;
                }
                number_head.setVisibility(View.GONE);
                adapter.setOnRecyclerViewItemClickListener((view, i) -> {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    ornamentList.add(new OrnamentMore(null, cocktail.getName(), adapter.getItem(i)));
                    initOrnament(ornamentList);
                });
                adapter.setNewData(OrnamentMoreDaoManager.getOrnamentMoreDaoManager().queryListNew());
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            case R.id.save_data:
                save_data();
                onBackPressed();
                break;
            case R.id.image_img:
                openImageLibrary(1);
                break;
            case R.id.image_glass:
                DialogUtil.showGlassEditDialog(this,new GlassListAdapter(GlassDaoManager.getGlassManager().queryList()), item -> {
                    //image_glass.setImageResource(GlassListAdapter.getTypeUrl(glass.getType()));
                    if (item instanceof Glass) {
                        Glass glass= (Glass) item;
                        //Toast.makeText(UpdateCocktailActivity.this, glass.getName() + glass.getType(), Toast.LENGTH_SHORT).show();
                        DrawableUtil.displayImage(UpdateCocktailActivity.this,image_glass,GlassListAdapter.getTypeUrl(glass.getType()));
                        cocktail.setGlass(String.valueOf(glass.getType()));
                    }
                });
                break;
        }
    }

    private void openImageLibrary(int code) {
        DialogUtil.showListDialog(this, null, new String[]{"File choose","Built in image"}, which -> {
            switch (which){
                case 0:
                    new MaterialFilePicker()
                            .withActivity(this)
                            .withRequestCode(code)
                            //.withFilter(Pattern.compile(".*\\.png$")) // Filtering files and directories by file name using regexp
                            .withFilterDirectories(false) // Set directories filterable (false by default)
                            .withHiddenFiles(false) //��ʾ���ص��ļ��к��ļ�
                            .withRootPath(Environment.getExternalStorageDirectory().getPath())
                            .start();
                /*Intent intent = new Intent(PassWordActivity.this, FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.ARG_FILTER, Pattern.compile(".*\\.txt$"));
                intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, true);
                intent.putExtra(FilePickerActivity.ARG_SHOW_HIDDEN, true);
                startActivityForResult(intent, 1);*/
                    break;
                case 1:
                    List<ImageListAdapter.ImageItem> list= new ArrayList<>();
                    for (int i = 1; i < 36; i++) {
                        list.add(new ImageListAdapter.ImageItem(i,String.valueOf(i)));
                    }
                    DialogUtil.showGlassEditDialog(this,new ImageListAdapter(list), item -> {
                        //image_glass.setImageResource(GlassListAdapter.getTypeUrl(glass.getType()));
                        if (item instanceof ImageListAdapter.ImageItem) {
                            ImageListAdapter.ImageItem glass= (ImageListAdapter.ImageItem) item;
                            cocktail.setPreviewImage(String.valueOf(glass.getType()));
                            DrawableUtil.displayImage(this,image_img,ImageListAdapter.getImageTypeUrl(glass.getType()));
                        }
                    });
                    break;
            }
        });
    }

    private void save_data() {
        if (submitCheck()) {
            if (isNew) {
                CocktailManager.getCocktailManager().addCocktail(cocktail);
            }else {
                CocktailManager.getCocktailManager().updateCocktail(cocktail);
            }
            AccessoriesMoreDaoManager.getAccessoriesMoreDaoManager().saveOrnamentMoreList(accessoriesList, cocktail.getName(), true);
            OrnamentMoreDaoManager.getOrnamentMoreDaoManager().saveOrnamentMoreList(ornamentList, cocktail.getName(), true);
        }
    }

    private boolean submitCheck() {
        // validate
        String tv = name_tv.getText().toString().trim();
        if (TextUtils.isEmpty(tv)) {
            Toast.makeText(this, R.string.nameisnull, Toast.LENGTH_SHORT).show();
            return false;
        }
        cocktail.setName(tv);
        // TODO validate success, do something
        return true;
    }
}
