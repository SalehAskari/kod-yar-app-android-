package com.example.kodyar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    private LottieAnimationView add , search , back , tick , add_btn , search_btn , back_for_list;
    ImageView list;
    private EditText name;
    private TextView hear , hear_select;
    private RadioGroup select;
    private RadioButton language , macos , linux , windows , other;
    private final int ANIMATION = 1000;
    private int status_back;
    private ConstraintLayout constraintLayout;

    private DataBaseName dataBaseName = new DataBaseName(this);

    private Vibrator vibrator;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //1:
        initialization();
        //2:
        start();

        status_back = 2;
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        typeface = ResourcesCompat.getFont(this , R.font.aviny);

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


        //btn
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp_name = name.getText().toString();
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
                if (TextUtils.isEmpty(temp_name)){
                    vibrator.vibrate(300);
                    myTost_danger("Type the name");
                }
                else {
                    Name nameClass = new Name(0, temp_name, temp_select);
                    hieAdd();
                    name.setText("");
                    dataBaseName.addName(nameClass);
                    myTost_done();
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
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

                String temp_name = name.getText().toString();

                if (TextUtils.isEmpty(temp_name)){
                    vibrator.vibrate(300);
                    myTost_danger("Type the name");
                }
                else {
                    Intent intent = new Intent(MainActivity.this, List_name.class);
                    intent.putExtra("name", temp_name);
                    intent.putExtra("category", temp_select);
                    intent.putExtra("List", false);
                    name.setText("");
                    hiesearch();
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status_back == 0){
                    hieAdd();
                    status_back = 2;
                }
                else if(status_back == 1) {
                    hiesearch();
                    status_back = 2;
                }
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

                Intent intent = new Intent(MainActivity.this ,List_name.class);
                intent.putExtra("select" , temp_select);
                intent.putExtra("List" , true);
                startActivity(intent);
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd();
                status_back = 0;
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status_back = 1;
                showsearch();

            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList();
            }
        });
        back_for_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hieList();
            }
        });


    }












    /////////////////////////////////////////////fuc////////////////////////////////////////////////
    //1:
    @SuppressLint("WrongViewCast")
    private void initialization(){
        add = findViewById(R.id.add);
        search = findViewById(R.id.search);

        name = findViewById(R.id.sub);
        hear = findViewById(R.id.hear);
        select = findViewById(R.id.select);
        language = findViewById(R.id.language);
        macos = findViewById(R.id.macos);
        windows = findViewById(R.id.windows);
        linux = findViewById(R.id.linux);
        back = findViewById(R.id.back_1);
        other = findViewById(R.id.other);
        tick = findViewById(R.id.tik);

        add_btn = findViewById(R.id.add_btn);
        search_btn = findViewById(R.id.search_btn);
        list = findViewById(R.id.list);
        hear_select = findViewById(R.id.hear_select);
        constraintLayout = findViewById(R.id.bg);
        back_for_list = findViewById(R.id.back_for_list);

    }

    //2:
    private void start(){
        name.setVisibility(View.INVISIBLE); name.setAlpha(0f);
        hear.setVisibility(View.INVISIBLE); hear.setAlpha(0f);
        hear_select.setVisibility(View.INVISIBLE); hear_select.setAlpha(0f);
        select.setVisibility(View.INVISIBLE); select.setAlpha(0f);
        back.setVisibility(View.INVISIBLE); back.setAlpha(0f);
        tick.setVisibility(View.INVISIBLE); tick.setAlpha(0f);
        add.setVisibility(View.INVISIBLE); add.setAlpha(0f);
        search.setVisibility(View.INVISIBLE); search.setAlpha(0f);
        back_for_list.setVisibility(View.INVISIBLE); back_for_list.setAlpha(0f);
    }

    private void showAdd(){
        constraintLayout.setBackground(getDrawable(R.drawable.bg_b));
        name.setVisibility(View.VISIBLE); name.animate().alpha(1f).setDuration(ANIMATION);
        select.setVisibility(View.VISIBLE); select.animate().alpha(1f).setDuration(ANIMATION);
        back.setVisibility(View.VISIBLE); back.animate().alpha(1f).setDuration(ANIMATION);
        add.setVisibility(View.VISIBLE); add.animate().alpha(1f).setDuration(ANIMATION);


        add_btn.setVisibility(View.INVISIBLE); add_btn.setAlpha(0f);
        search_btn.setVisibility(View.INVISIBLE); search_btn.setAlpha(0f);
        search.setVisibility(View.INVISIBLE); search.setAlpha(0f);
        list.setVisibility(View.INVISIBLE); list.setAlpha(0f);
    }
    private void hieAdd(){
        name.setText("");
        constraintLayout.setBackground(getDrawable(R.drawable.bg));
        name.setVisibility(View.INVISIBLE); name.setAlpha(0f);
        select.setVisibility(View.INVISIBLE); select.setAlpha(0f);
        back.setVisibility(View.INVISIBLE); back.setAlpha(0f);
        add.setVisibility(View.INVISIBLE); add.setAlpha(0f);

        search_btn.setVisibility(View.VISIBLE); search_btn.animate().alpha(1f).setDuration(ANIMATION);
        list.setVisibility(View.VISIBLE); list.animate().alpha(1f).setDuration(ANIMATION);
        add_btn.setVisibility(View.VISIBLE); add_btn.animate().alpha(1f).setDuration(ANIMATION);

    }
    private void showsearch(){
        constraintLayout.setBackground(getDrawable(R.drawable.bg_b));
        name.setVisibility(View.VISIBLE); name.animate().alpha(1f).setDuration(ANIMATION);
        select.setVisibility(View.VISIBLE); select.animate().alpha(1f).setDuration(ANIMATION);
        back.setVisibility(View.VISIBLE); back.animate().alpha(1f).setDuration(ANIMATION);
        search.setVisibility(View.VISIBLE); search.animate().alpha(1f).setDuration(ANIMATION);


        add_btn.setVisibility(View.INVISIBLE); add_btn.setAlpha(0f);
        list.setVisibility(View.INVISIBLE); list.setAlpha(0f);
        search_btn.setVisibility(View.INVISIBLE); search_btn.setAlpha(0f);
    }

    private void hiesearch(){
        name.setText("");
        constraintLayout.setBackground(getDrawable(R.drawable.bg));
        name.setVisibility(View.INVISIBLE); name.setAlpha(0f);
        select.setVisibility(View.INVISIBLE); select.setAlpha(0f);
        back.setVisibility(View.INVISIBLE); back.setAlpha(0f);
        search.setVisibility(View.INVISIBLE); search.setAlpha(0f);

        add_btn.setVisibility(View.VISIBLE); add_btn.animate().alpha(1f).setDuration(ANIMATION);
        list.setVisibility(View.VISIBLE); list.animate().alpha(1f).setDuration(ANIMATION);
        search_btn.setVisibility(View.VISIBLE); search_btn.animate().alpha(1f).setDuration(ANIMATION);

    }

    private void showList(){
        constraintLayout.setBackground(getDrawable(R.drawable.bg_b));
        name.setVisibility(View.INVISIBLE); name.setAlpha(0f);
        search_btn.setVisibility(View.INVISIBLE); search_btn.setAlpha(0f);
        list.setVisibility(View.INVISIBLE); list.setAlpha(0f);
        add_btn.setVisibility(View.INVISIBLE); add_btn.setAlpha(0f);


        select.setVisibility(View.VISIBLE); select.animate().alpha(1f).setDuration(ANIMATION);
        select.animate().x(hear.getX()).y(hear.getY()).setDuration(ANIMATION);
        tick.setVisibility(View.VISIBLE); tick.animate().alpha(1f).setDuration(ANIMATION);
        back_for_list.setVisibility(View.VISIBLE); back_for_list.animate().alpha(1f).setDuration(ANIMATION);
    }
    private void hieList(){
        constraintLayout.setBackground(getDrawable(R.drawable.bg));
        select.setVisibility(View.INVISIBLE); select.setAlpha(0f);
        back_for_list.setVisibility(View.INVISIBLE); back_for_list.setAlpha(0f);
        tick.setVisibility(View.INVISIBLE); tick.setAlpha(0f);

        select.animate().x(hear_select.getX()).y(hear_select.getY()).setDuration(ANIMATION);

        search_btn.setVisibility(View.VISIBLE); search_btn.animate().alpha(1f).setDuration(ANIMATION);
        list.setVisibility(View.VISIBLE); list.animate().alpha(1f).setDuration(ANIMATION);
        add_btn.setVisibility(View.VISIBLE); add_btn.animate().alpha(1f).setDuration(ANIMATION);
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

}