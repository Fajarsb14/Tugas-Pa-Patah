import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Kelas untuk merepresentasikan buku
class Book {
    private String title; // Judul buku
    private String author; // Penulis buku
    private boolean isAvailable; // Status ketersediaan buku

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true; // Buku tersedia saat pertama kali ditambahkan
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Judul: " + title + ", Penulis: " + author + ", Tersedia: " + (isAvailable ? "Ya" : "Tidak");
    }
}

// Kelas untuk merepresentasikan anggota perpustakaan
class Member {
    private String name; // Nama anggota
    private int memberId; // ID anggota

    public Member(String name, int memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public int getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "ID Anggota: " + memberId + ", Nama: " + name;
    }
}

// Kelas untuk mengelola koleksi buku dan anggota
class Library {
    private List<Book> books; // Daftar buku di perpustakaan
    private List<Member> members; // Daftar anggota perpustakaan

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Member> getMembers() {
        return members;
    }
}

// Kelas untuk mencatat transaksi peminjaman dan pengembalian buku
class Transaction {
    private Member member; // Anggota yang melakukan transaksi
    private Book book; // Buku yang terlibat dalam transaksi
    private String transactionType; // Jenis transaksi ("borrow" atau "return")

    public Transaction(Member member, Book book, String transactionType) {
        this.member = member;
        this.book = book;
        this.transactionType = transactionType;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public String getTransactionType() {
        return transactionType;
    }
}

// Kelas untuk mengelola operasi sistem perpustakaan
class LibrarySystem {
    private Library library; // Objek perpustakaan
    private List<Transaction> transactions; // Daftar transaksi

    public LibrarySystem() {
        library = new Library();
        transactions = new ArrayList<>();
    }

    public Library getLibrary() {
        return library;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void borrowBook(Member member, Book book) {
        if (book.isAvailable()) { // Cek ketersediaan buku
            book.setAvailable(false); // Tandai buku sebagai tidak tersedia
            transactions.add(new Transaction(member, book, "borrow")); // Tambah transaksi peminjaman
            System.out.println(member.getName() + " meminjam " + book.getTitle());
        } else {
            System.out.println("Buku tidak tersedia.");
        }
    }

    public void returnBook(Member member, Book book) {
        if (!book.isAvailable()) { // Cek apakah buku sedang dipinjam
            book.setAvailable(true); // Tandai buku sebagai tersedia
            transactions.add(new Transaction(member, book, "return")); // Tambah transaksi pengembalian
            System.out.println(member.getName() + " mengembalikan " + book.getTitle());
        } else {
            System.out.println("Buku tidak sedang dipinjam.");
        }
    }
}

// Kelas untuk menampilkan menu dan menangani input pengguna
class Menu {
    private LibrarySystem librarySystem; // Sistem perpustakaan
    private Scanner scanner; // Scanner untuk input pengguna

    public Menu() {
        librarySystem = new LibrarySystem();
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("Sistem Manajemen Perpustakaan");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Tambah Anggota");
            System.out.println("3. Pinjam Buku");
            System.out.println("4. Kembalikan Buku");
            System.out.println("5. Keluar");
            System.out.print("Masukkan pilihan Anda: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Konsumsi newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    addMember();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    System.out.println("Keluar...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        } while (choice != 5);
    }

    private void addBook() {
        System.out.print("Masukkan judul buku: ");
        String title = scanner.nextLine();
        System.out.print("Masukkan penulis buku: ");
        String author = scanner.nextLine();
        librarySystem.getLibrary().addBook(new Book(title, author));
        System.out.println("Buku berhasil ditambahkan.");
    }

    private void addMember() {
        System.out.print("Masukkan nama anggota: ");
        String name = scanner.nextLine();
        System.out.print("Masukkan ID anggota: ");
        int memberId = scanner.nextInt();
        scanner.nextLine(); // Konsumsi newline
        librarySystem.getLibrary().addMember(new Member(name, memberId));
        System.out.println("Anggota berhasil ditambahkan.");
    }

    private void borrowBook() {
        if (librarySystem.getLibrary().getMembers().isEmpty()) {
            System.out.println("Tidak ada anggota terdaftar. Tambahkan anggota terlebih dahulu.");
            return;
        }

        if (librarySystem.getLibrary().getBooks().isEmpty()) {
            System.out.println("Tidak ada buku tersedia. Tambahkan buku terlebih dahulu.");
            return;
        }

        System.out.println("Pilih anggota:");
        List<Member> members = librarySystem.getLibrary().getMembers();
        for (int i = 0; i < members.size(); i++) {
            System.out.println((i + 1) + ". " + members.get(i));
        }
        System.out.print("Masukkan nomor anggota: ");
        int memberIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Konsumsi newline

        if (memberIndex < 0 || memberIndex >= members.size()) {
            System.out.println("Pilihan anggota tidak valid.");
            return;
        }

        Member member = members.get(memberIndex);

        System.out.println("Pilih buku:");
        List<Book> books = librarySystem.getLibrary().getBooks();
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
        System.out.print("Masukkan nomor buku: ");
        int bookIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Konsumsi newline

        if (bookIndex < 0 || bookIndex >= books.size()) {
            System.out.println("Pilihan buku tidak valid.");
            return;
        }

        Book book = books.get(bookIndex);

        librarySystem.borrowBook(member, book);
    }

    private void returnBook() {
        if (librarySystem.getLibrary().getMembers().isEmpty()) {
            System.out.println("Tidak ada anggota terdaftar. Tambahkan anggota terlebih dahulu.");
            return;
        }

        if (librarySystem.getLibrary().getBooks().isEmpty()) {
            System.out.println("Tidak ada buku tersedia. Tambahkan buku terlebih dahulu.");
            return;
        }

        System.out.println("Pilih anggota:");
        List<Member> members = librarySystem.getLibrary().getMembers();
        for (int i = 0; i < members.size(); i++) {
            System.out.println((i + 1) + ". " + members.get(i));
        }
        System.out.print("Masukkan nomor anggota: ");
        int memberIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Konsumsi newline

        if (memberIndex < 0 || memberIndex >= members.size()) {
            System.out.println("Pilihan anggota tidak valid.");
            return;
        }

        Member member = members.get(memberIndex);

        System.out.println("Pilih buku:");
        List<Book> books = librarySystem.getLibrary().getBooks();
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
        System.out.print("Masukkan nomor buku: ");
        int bookIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Konsumsi newline

        if (bookIndex < 0 || bookIndex >= books.size()) {
            System.out.println("Pilihan buku tidak valid.");
            return;
        }

        Book book = books.get(bookIndex);

        librarySystem.returnBook(member, book);
    }
}

// Kelas utama untuk menjalankan aplikasi
public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu(); // Buat objek menu
        menu.displayMenu(); // Tampilkan menu
    }
}
