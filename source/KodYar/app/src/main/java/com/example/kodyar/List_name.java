package com.example.kodyar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;
import java.util.Objects;

public class List_name extends AppCompatActivity implements RecykelerViewKlick , AdapterView.OnItemSelectedListener {
    private DataBaseName dataBaseName = new DataBaseName(this);
    private DataBaseList_1 dataBaseList_1 = new DataBaseList_1(this);
    private RecyclerView recyclerView;
    private ListNameAdapter listNameAdapter;
    private int pos;
    private int id;
    private String select;
    private LottieAnimationView back , tick;
    private EditText name_editText;
    private RadioGroup radioGroup;
    private RadioButton language , macos , linux , windows , other;

    private ConstraintLayout constraintLayout;
    private final int ANIMATION = 2000;
    private boolean status;
    private String name;
    private String category;
    private Vibrator vibrator;
    private Typeface typeface;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_name);
        initialization();
        start();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        typeface = ResourcesCompat.getFont(this , R.font.aviny);
        //
        ColorStateList colorStateList = new ColorStateList(
                new int[][]
                        {
                                new int[]{-android.R.attr.state_enabled}, // Disabled
                                new int[]{android.R.attr.state_enabled}   // Enabled
                        },
                new int[]
                        {
                                Color.BLACK, // disabled
                                Color.RED   // enabled
                        }
        );
        language.setButtonTintList(colorStateList); // set the color tint list
        windows.setButtonTintList(colorStateList); // set the color tint list
        macos.setButtonTintList(colorStateList); // set the color tint list
        linux.setButtonTintList(colorStateList); // set the color tint list
        other.setButtonTintList(colorStateList); // set the color tint list
        //

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        select = getIntent().getExtras().getString("select");
        status = getIntent().getExtras().getBoolean("List");


        if (!status) {
            name = getIntent().getExtras().getString("name");
            category = getIntent().getExtras().getString("category");
        }

       refresh();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_editText.setText("");
                hieEdit();
            }
        });
        tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp_select = "language";
                if (language.isChecked()){
                    temp_select = "language";
                }
                else if (macos.isChecked()) {
                    temp_select = "macos";
                }
                else if (linux.isChecked()) {
                    temp_select = "linux";
                }
                else if (windows.isChecked()){
                    temp_select = "windows";

                }
                else if (other.isChecked()){
                    temp_select = "other";

                }
                String temp_name= name_editText.getText().toString();
                if (TextUtils.isEmpty(temp_name)){
                    vibrator.vibrate(300);
                    myTost_danger("Type the name");
                }
                else {
                    Name newName = new Name(id, temp_name, temp_select);
                    dataBaseName.updateName(newName);
                    myTost_done();
                    refresh();
                }
            }
        });
    }

    private void refresh() {
        if (status) {
            listNameAdapter = new ListNameAdapter(this, this, dataBaseName.getMe(select));
        } else {
            listNameAdapter = new ListNameAdapter(this, this, dataBaseName.searchName(name, category));
        }
        recyclerView.setAdapter(listNameAdapter);
    }

    private void initialization() {
        recyclerView = findViewById(R.id.list);
        constraintLayout = findViewById(R.id.bg);
        name_editText = findViewById(R.id.sub);
        back = findViewById(R.id.back_1);
        tick = findViewById(R.id.tick);
        radioGroup = findViewById(R.id.select);
        language = findViewById(R.id.language);
        macos = findViewById(R.id.macos);
        windows = findViewById(R.id.windows);
        linux = findViewById(R.id.linux);
        other = findViewById(R.id.other);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(int position) {
        pos = (position);
        if (status) {
            id = dataBaseName.getMe(select).get(pos).getId();
        }
        else {
            id = dataBaseName.searchName(name , category).get(pos).getId();
        }
        Intent intent = new Intent(List_name.this ,List_1.class);
        intent.putExtra("address" , id);
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {
        pos = (position);
        alertDialog();
        if (status) {
            id = dataBaseName.getMe(select).get(pos).getId();
        }
        else {
            id = dataBaseName.searchName(name , category).get(pos).getId();

        }
    }

    @SuppressLint("MissingInflatedId")
    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null);
        ImageView delete, edit;
        delete = view.findViewById(R.id.delete_btn);
        edit = view.findViewById(R.id.edit_btn);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<SubjectList> subjectLists = dataBaseList_1.getMeByAddress(id+"");
                for (int i = 0 ; i < subjectLists.size() ; i++){
                    dataBaseList_1.deleteName(subjectLists.get(i).getId()+"");
                }
              dataBaseName.deleteName(id+"");
              listNameAdapter.delete(pos);
              listNameAdapter.notifyItemRemoved(pos);
              alertDialog.dismiss();
              myTost_remove();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit();
                String temp_select = dataBaseName.getMe(select).get(pos).getCategory();
                if (Objects.equals(temp_select, "language")){
                    language.setChecked(true);
                }
                else if (Objects.equals(temp_select, "macos")) {
                    macos.setChecked(true);
                }
                else if (Objects.equals(temp_select, "linux")) {
                    linux.setChecked(true);
                }
                else if (Objects.equals(temp_select, "windows")){
                    windows.setChecked(true);
                }
                else if (Objects.equals(temp_select, "other")) {
                    other.setChecked(true);
                }
                name_editText.setText(dataBaseName.getMe(select).get(pos).getName());
                alertDialog.dismiss();
            }
        });
    }
    private void start(){
        name_editText.setVisibility(View.INVISIBLE); name_editText.setAlpha(0f);
        radioGroup.setVisibility(View.INVISIBLE); radioGroup.setAlpha(0f);
        back.setVisibility(View.INVISIBLE); back.setAlpha(0f);
        tick.setVisibility(View.INVISIBLE); tick.setAlpha(0f);
    }
    private void showEdit(){
        constraintLayout.setBackground(getDrawable(R.drawable.bg3_b));
       recyclerView.setVisibility(View.INVISIBLE); recyclerView.setAlpha(0f);

        name_editText.setVisibility(View.VISIBLE); name_editText.animate().alpha(1f).setDuration(ANIMATION);
        radioGroup.setVisibility(View.VISIBLE); radioGroup.animate().alpha(1f).setDuration(ANIMATION);
        back.setVisibility(View.VISIBLE); back.animate().alpha(1f).setDuration(ANIMATION);
        tick.setVisibility(View.VISIBLE); tick.animate().alpha(1f).setDuration(ANIMATION);
    }
    private void hieEdit(){
        constraintLayout.setBackground(getDrawable(R.drawable.bg3));
        recyclerView.setVisibility(View.VISIBLE); recyclerView.animate().alpha(1f).setDuration(ANIMATION);

        name_editText.setVisibility(View.INVISIBLE); name_editText.setAlpha(0f);
        radioGroup.setVisibility(View.INVISIBLE); radioGroup.setAlpha(0f);
        back.setVisibility(View.INVISIBLE); back.setAlpha(0f);
        tick.setVisibility(View.INVISIBLE); tick.setAlpha(0f);
    }

    private void myTost_danger(String s) {
        View toastView = LayoutInflater.from(this).inflate(R.layout.toast , null);
        Toast tost = new Toast(this);
        tost.setView(toastView);
        TextView textToast = toastView.findViewById(R.id.textTost);
        textToast.setText(s);
        textToast.setTypeface(typeface);
        tost.setDuration(Toast.LENGTH_LONG);
        tost.show();
    }
    private void myTost_done() {
        View toastView = LayoutInflater.from(this).inflate(R.layout.toast_done , null);
        Toast tost = new Toast(this);
        tost.setView(toastView);
        tost.setDuration(Toast.LENGTH_LONG);
        tost.show();
    }
    private void myTost_remove() {
        View toastView = LayoutInflater.from(this).inflate(R.layout.toast_remove , null);
        Toast tost = new Toast(this);
        tost.setView(toastView);
        tost.setDuration(Toast.LENGTH_LONG);
        tost.show();
    }
}