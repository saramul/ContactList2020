package suriyon.cs.ubru.contactlist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import suriyon.cs.ubru.contactlist.dao.SQLiteHelper;
import suriyon.cs.ubru.contactlist.model.Contact;

public class UpdateActivity extends AppCompatActivity {
    private EditText edtName, edtMobile;
    private Button btnUpdate, btnDelete;
    private SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        db = new SQLiteHelper(UpdateActivity.this);
        matchView();


        final Intent intent = this.getIntent();
        final int id = intent.getIntExtra("id", 0);
        final String name = intent.getStringExtra("name");
        final String mobile = intent.getStringExtra("mobile");

        ActionBar ab = getSupportActionBar();
        ab.setTitle(name);

        edtName.setText(name);
        edtMobile.setText(mobile);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtName.getText().toString();
                String mobile = edtMobile.getText().toString();
                Contact contact = new Contact(id, name, mobile);
                boolean result = db.update(contact);
                if(result){
                    Toast.makeText(UpdateActivity.this, "Update contact successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdateActivity.this, "Unable to update contact!", Toast.LENGTH_SHORT).show();
                }
                clearEditText();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog(id);
            }
        });
    }

    private void clearEditText() {
        edtMobile.setText("");
        edtName.setText("");
        edtName.requestFocus();
    }

    private void matchView() {
        edtName = findViewById(R.id.edt_name_edit);
        edtMobile = findViewById(R.id.edt_mobile_edit);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
    }

    public void confirmDialog(final int id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(UpdateActivity.this);
        alert.setTitle("Confirmation Message");
        alert.setMessage("Do you want to delete contact?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db = new SQLiteHelper(UpdateActivity.this);
                boolean result = db.delete(id);
                if(result)
                    Toast.makeText(UpdateActivity.this, "Delete contact successfully!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(UpdateActivity.this, "Unable to delete contact", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.create().show();
    }
}