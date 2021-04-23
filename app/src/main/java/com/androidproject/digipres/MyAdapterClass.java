package com.androidproject.digipres;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;
import java.util.Locale;

public class MyAdapterClass extends RecyclerView.Adapter<MyAdapterClass.MyViewHolder> {

    private  Context context;
    private List<Retrive_Pdf>pdf_list;

    public MyAdapterClass(Context context, List<Retrive_Pdf> pdf_list) {
        this.context = context;
        this.pdf_list = pdf_list;
    }

    @NonNull
    @Override
    public MyAdapterClass.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.demo_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterClass.MyViewHolder holder, int position) {

        Retrive_Pdf retrive_pdf = pdf_list.get(position);

        holder.textView.setText(retrive_pdf.getPdf_name());

        holder.Img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.Img1.getContext(),ViewPdf.class);
                intent.putExtra("pdf_name",retrive_pdf.getPdf_name());
                intent.putExtra("pdf_url",retrive_pdf.getPdf_url());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.Img1.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pdf_list.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView Img1;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Img1 = itemView.findViewById(R.id.img1);
            textView = itemView.findViewById(R.id.pdf_name);
        }
    }
}