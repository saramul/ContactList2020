package suriyon.cs.ubru.contactlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import suriyon.cs.ubru.contactlist.adapter.ContactAdapter;
import suriyon.cs.ubru.contactlist.dao.SQLiteHelper;
import suriyon.cs.ubru.contactlist.model.Contact;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvContact;
    private FloatingActionButton fabAdd;
    private SQLiteHelper db;
    private List<Contact> contacts;
    private ContactAdapter adapter;
    private ImageView imgNoData;
    private TextView tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQLiteHelper(MainActivity.this);
        matchView();

        displayContacts();

        contacts = db.select();
        adapter = new ContactAdapter(MainActivity.this, contacts);
        rcvContact.setAdapter(adapter);
        rcvContact.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });
    }

    private void displayContacts() {
        contacts = db.select();
        if(contacts.size() == 0){
            imgNoData.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
//            Toast.makeText(MainActivity.this, "No Data!", Toast.LENGTH_SHORT).show();
        }else {
            imgNoData.setVisibility(View.GONE);
            tvDescription.setVisibility(View.GONE);
        }
    }

    private void matchView() {
        rcvContact = findViewById(R.id.rcv_contact);
        fabAdd = findViewById(R.id.fab_add);
        imgNoData = findViewById(R.id.img_no_data);
        tvDescription = findViewById(R.id.tv_description);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayContacts();

        contacts = db.select();
        adapter = new ContactAdapter(MainActivity.this, contacts);
        rcvContact.setAdapter(adapter);
        rcvContact.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_delete_all){
            confirmDialog();
           // onResume();
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirmDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Confirmation Message");
        alert.setMessage("Do you want to delete all contact?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db = new SQLiteHelper(MainActivity.this);
                db.delete();
                Toast.makeText(MainActivity.this, "Delete all contact successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //finish();
            }
        });
        alert.create().show();
    }
}