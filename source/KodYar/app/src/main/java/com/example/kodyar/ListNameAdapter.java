package com.example.kodyar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ListNameAdapter extends RecyclerView.Adapter<ListNameAdapter.ViewHolder>{

    private final RecykelerViewKlick recykelerViewKlick;
    private Context context;
    private List<Name> nameList;
    private LayoutInflater inflater;

    public ListNameAdapter(RecykelerViewKlick recykelerViewKlick, Context context, List<Name> customerViewList) {
        this.recykelerViewKlick = recykelerViewKlick;
        this.context = context;
        this.nameList = customerViewList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view , recykelerViewKlick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Name name = nameList.get(position);
        holder.item.setText(name.getName());

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView item;

        public ViewHolder(@NonNull View itemView ,RecykelerViewKlick recykelerViewKlick) {
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
        nameList.remove(position);

    }



}
