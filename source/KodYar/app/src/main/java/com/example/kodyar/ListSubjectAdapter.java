package com.example.kodyar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListSubjectAdapter extends RecyclerView.Adapter<ListSubjectAdapter.ViewHolder>{

    private final RecykelerViewKlick recykelerViewKlick;
    private Context context;
    private List<SubjectList> subjectLists;
    private LayoutInflater inflater;


    public ListSubjectAdapter(RecykelerViewKlick recykelerViewKlick, Context context, List<SubjectList> subjectLists) {
        this.recykelerViewKlick = recykelerViewKlick;
        this.context = context;
        this.subjectLists = subjectLists;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view , recykelerViewKlick);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSubjectAdapter.ViewHolder holder, int position) {
        SubjectList subjectList = subjectLists.get(position);
        holder.item.setText(subjectList.getSubject());
    }

    @Override
    public int getItemCount() {
       return subjectLists.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView item;

    public ViewHolder(@NonNull View itemView , RecykelerViewKlick recykelerViewKlick) {
        super(itemView);

        item = itemView.findViewById(R.id.customer_txt);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recykelerViewKlick != null){
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION){
                        recykelerViewKlick.onClick(pos);
                    }

                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (recykelerViewKlick != null){
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION){
                        recykelerViewKlick.onLongClick(pos);
                    }

                }
                return true;
            }
        });
    }
}
    public void delete(int position){
        subjectLists.remove(position);

    }



}
