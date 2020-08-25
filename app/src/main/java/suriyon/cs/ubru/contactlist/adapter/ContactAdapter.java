package suriyon.cs.ubru.contactlist.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import suriyon.cs.ubru.contactlist.R;
import suriyon.cs.ubru.contactlist.UpdateActivity;
import suriyon.cs.ubru.contactlist.model.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private Context context;
    private List<Contact> contacts;
    private Animation translate_anim;
//    private int position;
    public ContactAdapter(Context context, List<Contact> contacts){
        this.context = context;
        this.contacts = contacts;
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_row, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position) {
//        this.position = position;
        holder.tvName.setText(contacts.get(position).getName());
        holder.tvMobile.setText(contacts.get(position).getMobile());
        holder.layoutContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                Contact contact = contacts.get(position);
                intent.putExtra("id", contact.getId());
                intent.putExtra("name", contact.getName());
                intent.putExtra("mobile", contact.getMobile());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMobile;
        LinearLayout layoutContact;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvMobile = itemView.findViewById(R.id.tv_mobile);
            layoutContact = itemView.findViewById(R.id.layout_contact);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            layoutContact.setAnimation(translate_anim);
        }
    }
}
