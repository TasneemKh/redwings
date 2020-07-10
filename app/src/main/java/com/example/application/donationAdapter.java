package com.example.application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class donationAdapter extends RecyclerView.Adapter<donationAdapter.donationVh>{
    Context context ;
    List<donationHistory> donationHistory ;
    public  donationAdapter(Context context,List<donationHistory> donationHistory ){
        this.context = context;
        this.donationHistory=donationHistory;
    }
    @NonNull
    @Override
    public donationVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_row ,parent , false );
        return new donationVh(view);    }

    @Override
    public void onBindViewHolder(@NonNull donationVh holder, int position) {
        holder.setData(donationHistory.get(position));

    }

    @Override
    public int getItemCount() {
        return 0;
    }



    class donationVh extends RecyclerView.ViewHolder {
        TextView don_id,Donation_type,bloodbank,date;
        public donationVh(@NonNull View itemView) {
            super(itemView);
            //don_id = itemView.findViewById(R.id.std_id);
            Donation_type = itemView.findViewById(R.id.Donation_type);
            bloodbank = itemView.findViewById(R.id.bloodbank);
            date = itemView.findViewById(R.id.date);
        }

        public void setData(final donationHistory donationHistory) {
         //   std_id.setText(donationHistory.getId());
            Donation_type.setText(donationHistory.getDonationType());
            bloodbank.setText(donationHistory.getPlaceOfDonation());
            //date.setText((toString())donationHistory.getDateOfDonation());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 /*   Toast.makeText(context, donationHistory.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(itemView.getContext() ,stdActivity.class);
                  //  intent.putExtra("id",donationHistory.getId());
                    intent.putExtra("name",donationHistory.getName());
                    intent.putExtra("level",donationHistory.getLevel());
                    intent.putExtra("Avg",donationHistory.getAvg());
                    itemView.getContext().startActivity(intent);*/
                }
            });

        }
    }
}
