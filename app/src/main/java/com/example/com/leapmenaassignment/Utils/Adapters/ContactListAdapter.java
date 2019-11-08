package com.example.com.leapmenaassignment.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.leapmenaassignment.Model.Models.ContactDetails;
import com.example.com.leapmenaassignment.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactListAdapter extends RecyclerView.Adapter {

    ArrayList<ContactDetails> contactDetails;
    Context context;

    public ContactListAdapter(Context context, ArrayList<ContactDetails> contactDetails) {
        this.context = context;
        this.contactDetails = contactDetails;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == 0) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contact_list_item, parent, false);
            viewHolder = new ContactItemHolder(view);
        } else if (viewType == 1) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_refresh_loadr, parent, false);
            viewHolder = new RefreshProgressItem(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ContactItemHolder) {
            ((ContactItemHolder) holder).textContactName.setText(contactDetails.get(position).getContactName());
            ((ContactItemHolder) holder).textContactNumber.setText(contactDetails.get(position).getContactNumber());

        } else if (holder instanceof RefreshProgressItem) {

            ((RefreshProgressItem) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return contactDetails.size();
    }

    @Override
    public int getItemViewType(int position) {

        int itemType = 0;
        if (contactDetails.get(position).getItemType() == 1) {
            itemType = 1;
        } else {
            itemType = 0;
        }
        return itemType;
    }

    class ContactItemHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView textContactName, textContactNumber;
        LinearLayout linearLayout;

        public ContactItemHolder(@NonNull View itemView) {
            super(itemView);
            textContactName = itemView.findViewById(R.id.tv_contact_name);
            textContactNumber = itemView.findViewById(R.id.tv_contact_number);
            circleImageView = itemView.findViewById(R.id.profile_image);
            linearLayout = itemView.findViewById(R.id.ll_main);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Contact \n" + contactDetails.get(getAdapterPosition()).getContactName() + "\n" + contactDetails.get(getAdapterPosition()).getContactNumber(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class RefreshProgressItem extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public RefreshProgressItem(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.pb_refresh_loader);
        }
    }
}
