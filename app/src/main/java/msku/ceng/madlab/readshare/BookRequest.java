package msku.ceng.madlab.readshare;

public class BookRequest {
    private String bookName;
    private String location;
    private String age;
    private String topic;
    private String studentId; // Profili açmak için gerekli
    private String studentName; // Profilde göstermek için
    private String documentId; // Firebase ID'si

    public BookRequest() {
        // Firebase için boş kurucu metod şart
    }

    public BookRequest(String bookName, String location, String age, String topic, String studentId, String studentName) {
        this.bookName = bookName;
        this.location = location;
        this.age = age;
        this.topic = topic;
        this.studentId = studentId;
        this.studentName = studentName;
    }

    // Getter Metotları
    public String getBookName() { return bookName; }
    public String getLocation() { return location; }
    public String getAge() { return age; }
    public String getTopic() { return topic; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }

    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }
}