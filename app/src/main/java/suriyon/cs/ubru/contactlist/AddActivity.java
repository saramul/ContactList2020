package suriyon.cs.ubru.contactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import suriyon.cs.ubru.contactlist.dao.SQLiteHelper;
import suriyon.cs.ubru.contactlist.model.Contact;

public class AddActivity extends AppCompatActivity {
    private EditText edtName, edtMobile;
    private Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        matchView();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String mobile = edtMobile.getText().toString();

                if(name.isEmpty() || mobile.isEmpty()){
                    Toast.makeText(AddActivity.this, "Full fill data before click add button", Toast.LENGTH_SHORT).show();
                }else{
                    Contact contact = new Contact(name, mobile);
                    SQLiteHelper db = new SQLiteHelper(AddActivity.this);
                    boolean result = db.insert(contact);
                    if(result){
                        Toast.makeText(AddActivity.this, "Insert contact successfully!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AddActivity.this, "Unable to insert contact!", Toast.LENGTH_SHORT).show();
                    }
                    clearEditText();
                }
            }
        });
    }

    private void clearEditText() {
        edtName.setText("");
        edtMobile.setText("");
        edtName.requestFocus();
    }

    private void matchView() {
        edtMobile = findViewById(R.id.edt_mobile);
        edtName = findViewById(R.id.edt_name);
        btnAdd = findViewById(R.id.btn_add);
    }
}