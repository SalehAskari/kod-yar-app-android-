package com.example.kodyar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

public class List_1 extends AppCompatActivity implements RecykelerViewKlick , AdapterView.OnItemSelectedListener {
    private ConstraintLayout constraintLayout;
    private RecyclerView recyclerView;
    private LottieAnimationView add, search, back , save;
    private TextView hear, hear_search , hear_add;
    private EditText subject, kod, about;
    private final int ANIMATION = 1000;
    private int status_back;
    private boolean status_add;
    private boolean status_search;
    private DataBaseList_1 dataBaseList_1 = new DataBaseList_1(this);
    private int address;
    private SubjectList subjectList;
    private ListSubjectAdapter listSubjectAdapter;
    private List<SubjectList> listView;
    private String id;
    private int pos;
    private RecyclerView list_for_search;
    private boolean show_when_search;
    private List<SubjectList> subjectLists;

    private Vibrator vibrator;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);
        initialization();
        start();
        status_back = 0;
        status_add = true;
        status_search = true;
        address = getIntent().getExtras().getInt("address");

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        typeface = ResourcesCompat.getFont(this , R.font.aviny);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager_1 = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        list_for_search.setLayoutManager(layoutManager_1);
        refresh();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status_add) {
                    showAdd();
                    status_add = false;
                    status_back = 0;

                } else {
                    String temp_subject = subject.getText().toString();
                    String temp_kod = kod.getText().toString();
                    String temp_about = about.getText().toString();

                    if(TextUtils.isEmpty(temp_subject) || TextUtils.isEmpty(temp_kod) || TextUtils.isEmpty(temp_about)){
                        vibrator.vibrate(300);
                        myTost_danger("Complete all fields");
                    }
                    else {
                        subjectList = new SubjectList(0, address + "", temp_subject, temp_kod, temp_about);
                        dataBaseList_1.addSubject(subjectList);
                        subject.setText("");
                        kod.setText("");
                        about.setText("");
                        myTost_done();
                        refresh();
                    }

                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status_search) {
                    showSearch();
                    status_back = 1;
                } else {
                    search();

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status_back == 0) {
                    hieAdd();
                    status_add = true;
                }else if(status_back == 1) {
                    hieSearch();
                    status_search = true;

                }
                else if(status_back == 2) {
                    if(!show_when_search) {
                        hie();
                    }
                    else if(show_when_search){
                        hie_when_search();

                    }
                }
                else {
                    if(!show_when_search) {
                        hieEdit();
                    }
                    else if(show_when_search){
                        hieEdit_when_search();
                    }

                }
                if(status_back == 2 && status_search == false){
                    status_back = 1;
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp_subject = subject.getText().toString();
                String temp_kod = kod.getText().toString();
                String temp_about= about.getText().toString();
                if (TextUtils.isEmpty(temp_subject) || TextUtils.isEmpty(temp_kod) || TextUtils.isEmpty(temp_about)){
                    vibrator.vibrate(300);
                    myTost_danger("Complete all fields");
                }
                else {
                    SubjectList s = new SubjectList(Integer.parseInt(id), listView.get(pos).getAddress(), temp_subject, temp_kod, temp_about);
                    dataBaseList_1.updateName(s);
                    refresh();
                    myTost_done();
                }
            }
        });
    }

    private void search(){
        String temp_subject = subject.getText().toString();
        if (TextUtils.isEmpty(temp_subject)){
            vibrator.vibrate(300);
            myTost_danger("Type the subject");
        }
        else {
            subjectLists = dataBaseList_1.getMeBySubject(temp_subject);
            listSubjectAdapter = new ListSubjectAdapter(this, this, subjectLists);
            list_for_search.setAdapter(listSubjectAdapter);
        }
    }


    private void initialization() {
        constraintLayout = findViewById(R.id.bg);
        recyclerView = findViewById(R.id.list);
        add = findViewById(R.id.add);
        search = findViewById(R.id.search);
        back = findViewById(R.id.back_1);
        hear = findViewById(R.id.hear);
        hear_search = findViewById(R.id.hear_search);
        subject = findViewById(R.id.subject);
        kod = findViewById(R.id.kod);
        about = findViewById(R.id.about);
        save = findViewById(R.id.save);
        hear_add = findViewById(R.id.hear_add);
        list_for_search = findViewById(R.id.list_for_search);
    }

    private void start() {
        hear_add.setVisibility(View.INVISIBLE);
        hear_add.setAlpha(0f);
        back.setVisibility(View.INVISIBLE);
        back.setAlpha(0f);
        hear.setVisibility(View.INVISIBLE);
        hear.setAlpha(0f);
        hear_search.setVisibility(View.INVISIBLE);
        hear_search.setAlpha(0f);
        subject.setVisibility(View.INVISIBLE);
        subject.setAlpha(0f);
        kod.setVisibility(View.INVISIBLE);
        kod.setAlpha(0f);
        about.setVisibility(View.INVISIBLE);
        about.setAlpha(0f);
        save.setVisibility(View.INVISIBLE);
        save.setAlpha(0f);
        list_for_search.setVisibility(View.INVISIBLE);
        list_for_search.setAlpha(0f);
    }

    private void showAdd() {
        constraintLayout.setBackground(getDrawable(R.drawable.bg3_b));
        add.animate().x(save.getX()).y(save.getY()).setDuration(ANIMATION);

        search.setVisibility(View.INVISIBLE);
        search.setAlpha(0f);
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setAlpha(0f);

        back.setVisibility(View.VISIBLE);
        back.animate().alpha(1f).setDuration(ANIMATION);
        subject.setVisibility(View.VISIBLE);
        subject.animate().alpha(1f).setDuration(ANIMATION);
        kod.setVisibility(View.VISIBLE);
        kod.animate().alpha(1f).setDuration(ANIMATION);
        about.setVisibility(View.VISIBLE);
        about.animate().alpha(1f).setDuration(ANIMATION);

    }

    private void hieAdd() {
        subject.setText("");
        kod.setText("");
        about.setText("");

        constraintLayout.setBackground(getDrawable(R.drawable.bg3));
        add.animate().x(hear_add.getX()).y(hear_add.getY()).setDuration(ANIMATION);


        back.setVisibility(View.INVISIBLE);
        back.setAlpha(0f);
        subject.setVisibility(View.INVISIBLE);
        subject.setAlpha(0f);
        kod.setVisibility(View.INVISIBLE);
        kod.setAlpha(0f);
        about.setVisibility(View.INVISIBLE);
        about.setAlpha(0f);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.animate().alpha(1f).setDuration(ANIMATION);
        search.setVisibility(View.VISIBLE);
        search.animate().alpha(1f).setDuration(ANIMATION);
    }

    private void showSearch() {
        show_when_search = true;
        status_search = false;
        constraintLayout.setBackground(getDrawable(R.drawable.bg3_b));

        add.setVisibility(View.INVISIBLE);
        add.setAlpha(0f);
        search.animate().x(hear.getX()).y(hear.getY()).setDuration(ANIMATION);
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setAlpha(0f);

        back.setVisibility(View.VISIBLE);
        back.animate().alpha(1f).setDuration(ANIMATION);
        subject.setVisibility(View.VISIBLE);
        subject.animate().alpha(1f).setDuration(ANIMATION);
        list_for_search.setVisibility(View.VISIBLE);
        list_for_search.animate().alpha(1f).setDuration(ANIMATION);

    }

    private void hieSearch() {
        subject.setText("");
        kod.setText("");
        about.setText("");

        show_when_search = false;
        status_search = true;
        constraintLayout.setBackground(getDrawable(R.drawable.bg3));

        add.setVisibility(View.VISIBLE);
        add.animate().alpha(1f).setDuration(ANIMATION);
        search.animate().x(hear_search.getX()).y(hear_search.getY()).setDuration(ANIMATION);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.animate().alpha(1f).setDuration(ANIMATION);

        list_for_search.setVisibility(View.INVISIBLE);
        list_for_search.setAlpha(0f);

        back.setVisibility(View.INVISIBLE);
        back.setAlpha(0f);
        subject.setVisibility(View.INVISIBLE);
        subject.setAlpha(0f);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(int position) {
        pos = position;
        if(!show_when_search) {
            show(listView.get(position).getKod(), listView.get(position).getAbout());
        }
        else if(show_when_search){
            show_when_search(subjectLists.get(position).getKod(), subjectLists.get(position).getAbout());
        }
    }

    @Override
    public void onLongClick(int position) {
        pos = position;
        id = listView.get(position).getId()+"";
        alertDialog();

    }

    private void show(String enter_kod , String enter_about){
        status_back = 2;
        constraintLayout.setBackground(getDrawable(R.drawable.bg3_b));

        search.setVisibility(View.INVISIBLE);
        search.setAlpha(0f);
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setAlpha(0f);
        add.setVisibility(View.INVISIBLE); add.setAlpha(0f);


        back.setVisibility(View.VISIBLE);
        back.animate().alpha(1f).setDuration(ANIMATION);
        kod.setVisibility(View.VISIBLE);
        kod.animate().alpha(1f).setDuration(ANIMATION);
        about.setVisibility(View.VISIBLE);
        about.animate().alpha(1f).setDuration(ANIMATION);
        kod.setText(enter_kod);
        about.setText(enter_about);




    }
    private void hie(){

        constraintLayout.setBackground(getDrawable(R.drawable.bg3));

        subject.setText("");
        kod.setText("");
        about.setText("");

        back.setVisibility(View.INVISIBLE);
        back.setAlpha(0f);
        kod.setVisibility(View.INVISIBLE);
        kod.setAlpha(0f);
        about.setVisibility(View.INVISIBLE);
        about.setAlpha(0f);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.animate().alpha(1f).setDuration(ANIMATION);
        search.setVisibility(View.VISIBLE);
        search.animate().alpha(1f).setDuration(ANIMATION);
        add.setVisibility(View.VISIBLE);
        add.animate().alpha(1f).setDuration(ANIMATION);
    }
    private void showEdit(){

        show(listView.get(pos).getKod() , listView.get(pos).getAbout());
        subject.setVisibility(View.VISIBLE);
        subject.animate().alpha(1f).setDuration(ANIMATION);
        subject.setText(listView.get(pos).getSubject());
        status_back = 3;
        save.setVisibility(View.VISIBLE);
        save.animate().alpha(1f).setDuration(ANIMATION);
    }
    private void hieEdit(){
        subject.setText("");
        kod.setText("");
        about.setText("");
        hie();
        subject.setVisibility(View.INVISIBLE);
        subject.setAlpha(0f);
        save.setVisibility(View.INVISIBLE);
        save.setAlpha(0f);
    }

    private void show_when_search(String enter_kod , String enter_about){
        status_back = 2;
        subject.setVisibility(View.INVISIBLE);
        subject.setAlpha(0f);
        list_for_search.setVisibility(View.INVISIBLE);
        list_for_search.setAlpha(0f);
        search.setVisibility(View.INVISIBLE);
        search.setAlpha(0f);

        kod.setVisibility(View.VISIBLE);
        kod.animate().alpha(1f).setDuration(ANIMATION);

        about.setVisibility(View.VISIBLE);
        about.animate().alpha(1f).setDuration(ANIMATION);

        kod.setText(enter_kod);
        about.setText(enter_about);
    }
    private void hie_when_search(){
        kod.setText("");
        about.setText("");
        subject.setText("");


        kod.setVisibility(View.INVISIBLE);
        kod.setAlpha(0f);

        about.setVisibility(View.INVISIBLE);
        about.setAlpha(0f);

        subject.setVisibility(View.VISIBLE);
        subject.animate().alpha(1f).setDuration(ANIMATION);

        search.setVisibility(View.VISIBLE);
        search.animate().alpha(1f).setDuration(ANIMATION);

        list_for_search.setVisibility(View.VISIBLE);
        list_for_search.animate().alpha(1f).setDuration(ANIMATION);
    }

    private void  showEdit_when_search(){

        status_back = 3;
        search.setVisibility(View.INVISIBLE);
        search.setAlpha(0f);

        list_for_search.setVisibility(View.INVISIBLE);
        list_for_search.setAlpha(0f);

        kod.setVisibility(View.VISIBLE);
        kod.animate().alpha(1f).setDuration(ANIMATION);

        about.setVisibility(View.VISIBLE);
        about.animate().alpha(1f).setDuration(ANIMATION);

        save.setVisibility(View.VISIBLE);
        save.animate().alpha(1f).setDuration(ANIMATION);
        kod.setText(subjectLists.get(pos).getKod());
        about.setText(subjectLists.get(pos).getAbout());
        subject.setText(subjectLists.get(pos).getSubject());
    }

    private void hieEdit_when_search(){

        subject.setText("");
        kod.setText("");
        about.setText("");

        kod.setVisibility(View.INVISIBLE);
        kod.setAlpha(0f);

        about.setVisibility(View.INVISIBLE);
        about.setAlpha(0f);

        save.setVisibility(View.INVISIBLE);
        save.setAlpha(0f);


        search.setVisibility(View.VISIBLE);
        search.animate().alpha(1f).setDuration(ANIMATION);

        list_for_search.setVisibility(View.VISIBLE);
        list_for_search.animate().alpha(1f).setDuration(ANIMATION);

        status_back = 1;
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
                dataBaseList_1.deleteName(id);
                listSubjectAdapter.delete(pos);
                listSubjectAdapter.notifyItemRemoved(pos);
                alertDialog.dismiss();
                myTost_remove();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!show_when_search) {
                    showEdit();
                }
                else if(show_when_search){
                    showEdit_when_search();
                }
                alertDialog.dismiss();
            }
        });
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

    private void refresh(){
        listView = dataBaseList_1.getMeByAddress(address + "");

        listSubjectAdapter = new ListSubjectAdapter(this, this, listView);
        recyclerView.setAdapter(listSubjectAdapter);
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