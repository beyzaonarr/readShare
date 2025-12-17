package msku.ceng.madlab.readshare;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import msku.ceng.madlab.readshare.databinding.ActivityDonorDiscoveryBinding;

public class DonorDiscoveryActivity extends AppCompatActivity {

    private ActivityDonorDiscoveryBinding binding;
    private FirebaseFirestore db;
    private BookRequestAdapter adapter;
    private List<BookRequest> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonorDiscoveryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        // 1. RecyclerView Kurulumu
        binding.rvBookRequests.setLayoutManager(new LinearLayoutManager(this));
        requestList = new ArrayList<>();
        adapter = new BookRequestAdapter(this, requestList);
        binding.rvBookRequests.setAdapter(adapter);

        // 2. Verileri Çek
        fetchStudentsFromFirebase();

        // Geri Tuşu
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void fetchStudentsFromFirebase() {
        db.collection("students")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        requestList.clear(); // Listeyi temizle (tekrar eklemeyi önlemek için)

                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            Student student = snapshot.toObject(Student.class);

                            if (student != null) {
                                // --- VERİ DÖNÜŞÜMÜ ---
                                // Firebase'den gelen 'Student' nesnesini,
                                // Tasarımımızdaki 'BookRequest' modeline çeviriyoruz.

                                String bookName = student.getBookNeed();
                                if (bookName == null || bookName.isEmpty()) bookName = "General Reading Book";

                                // Student verilerini BookRequest formatına sokuyoruz
                                BookRequest request = new BookRequest(
                                        bookName,                  // Kitap Adı (İhtiyaç)
                                        student.getCity(),         // Konum
                                        student.getAge(),          // Yaş (Age: 10 (Medium))
                                        "Education",               // Konu (Sabit veya need'den alabilirsin)
                                        snapshot.getId(),          // Student ID (Firebase ID'si önemli!)
                                        student.getName()          // Öğrenci Adı
                                );

                                requestList.add(request);
                            }
                        }
                        // Adaptöre "Veriler değişti, listeyi yenile" diyoruz
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(this, "No requests found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}