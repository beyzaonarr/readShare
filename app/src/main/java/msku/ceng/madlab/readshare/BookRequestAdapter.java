package msku.ceng.madlab.readshare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookRequestAdapter extends RecyclerView.Adapter<BookRequestAdapter.BookViewHolder> {

    private Context context;
    private List<BookRequest> requestList;

    public BookRequestAdapter(Context context, List<BookRequest> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // İŞTE BURASI: Senin tasarladığın 'activity_book_request' dosyasını çağırıyoruz
        View view = LayoutInflater.from(context).inflate(R.layout.activity_book_request, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookRequest request = requestList.get(position);

        // 1. Kitap İsmini Yaz
        holder.tvBookName.setText(request.getBookName());

        // 2. Detayları (Konum, Yaş, Konu) Alt Alta Yaz
        String details = request.getLocation() + "\n" +
                "Age: " + request.getAge() + "\n" +
                "Topic: " + request.getTopic();
        holder.tvBookDetails.setText(details);

        // 3. "Student Profile" Butonu: Öğrenci Detay Sayfasına Gider
        holder.btnStudentProfile.setOnClickListener(v -> {
            Intent intent = new Intent(context, StudentDetailActivity.class);
            // Öğrenciyi tanımak için gerekli bilgileri gönderiyoruz
            intent.putExtra("studentId", request.getStudentId());
            intent.putExtra("name", request.getStudentName());
            intent.putExtra("school", request.getLocation()); // Konumu okul gibi kullanabiliriz
            context.startActivity(intent);
        });

        // 4. "Donate a Book" Butonu: Teslimat/Bağış Ekranına Gider
        holder.btnDonateBook.setOnClickListener(v -> {
            Toast.makeText(context, "Donation process started for: " + request.getBookName(), Toast.LENGTH_SHORT).show();

            // Kargo Takip Ekranına Yönlendirme
            Intent intent = new Intent(context, DeliveryTrackingActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    // TASARIMDAKİ ELEMANLARI TANIMLAYAN SINIF
    public static class BookViewHolder extends RecyclerView.ViewHolder {

        TextView tvBookName, tvBookDetails;
        Button btnStudentProfile, btnDonateBook;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            // activity_book_request.xml içindeki ID'leri burada eşleştiriyoruz
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvBookDetails = itemView.findViewById(R.id.tvBookDetails);
            btnStudentProfile = itemView.findViewById(R.id.btnStudentProfile);
            btnDonateBook = itemView.findViewById(R.id.btnDonateBook);
        }
    }
}