package msku.ceng.madlab.readshare; // Kendi paket isminizi buraya yazın

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import msku.ceng.madlab.readshare.R;

public class ReadingDiaryActivity extends AppCompatActivity {

    // Görünüm elemanlarını tanımlıyoruz
    private ImageView btnBack;
    private EditText etDate, etBookName, etPage, etNote;
    private Button btnSaveEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // XML dosyanızın adı neyse buraya onu yazın (örneğin: activity_reading_diary)
        setContentView(R.layout.activity_reading_diary);

        // Elemanları XML'deki ID'lerle eşleştiriyoruz
        initViews();

        // Geri butonu tıklama olayı
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aktiviteyi kapatıp önceki sayfaya döner
                finish();
            }
        });

        // Kaydet butonu tıklama olayı
        btnSaveEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEntry();
            }
        });
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);

        // Tablodaki EditText alanları (XML'de ID verdiğimiz varsayılarak)
        etDate = findViewById(R.id.etDate);
        etBookName = findViewById(R.id.etBookName);
        etPage = findViewById(R.id.etPage);
        etNote = findViewById(R.id.etNote);

        btnSaveEntry = findViewById(R.id.btnSaveEntry);
    }

    private void saveEntry() {
        // Girdileri string olarak alıyoruz
        String date = etDate.getText().toString().trim();
        String bookName = etBookName.getText().toString().trim();
        String page = etPage.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        // Basit bir doğrulama: Kitap adı boş olmamalı
        if (TextUtils.isEmpty(bookName)) {
            etBookName.setError("Book name is required!");
            return;
        }

        // --- VERİTABANI İŞLEMLERİ BURADA YAPILACAK ---
        // Buraya Firebase veya SQLite kaydetme kodlarınızı ekleyebilirsiniz.
        // Şimdilik sadece kullanıcıya bilgi veriyoruz.

        String message = "Saved: " + bookName + " (" + page + " pages)";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // Kaydettikten sonra alanları temizlemek isterseniz:
        // clearFields();
    }

    // Alanları temizleme metodu (İsteğe bağlı)
    private void clearFields() {
        etDate.setText("");
        etBookName.setText("");
        etPage.setText("");
        etNote.setText("");
    }
}