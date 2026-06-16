Requirements

- JDK 17
- Gradle (atau gunakan Gradle Wrapper yang tersedia)

«Catatan: Proyek ini dikembangkan dan diuji menggunakan JDK 17. Versi Java yang lebih lama mungkin tidak kompatibel.»

Build Project

./gradlew build

Run Project

./gradlew run

Development Tools

Proyek ini menyediakan utilitas tambahan bernama "CoordinateFinder.java" untuk membantu proses pengembangan game.

CoordinateFinder

File:

CoordinateFinder.java

Fungsi:

- Digunakan untuk menentukan koordinat (X, Y) pada gambar permainan.
- Membantu pengembang menandai lokasi perbedaan yang harus ditemukan oleh pemain.
- Mempermudah proses pembuatan level tanpa harus memperkirakan koordinat secara manual.

Cara Menggunakan

1. Jalankan file "CoordinateFinder.java".
2. Muat gambar yang ingin digunakan pada level permainan.
3. Klik pada area gambar yang merupakan titik perbedaan.
4. Program akan menampilkan koordinat posisi klik (X, Y).
5. Catat koordinat yang ditampilkan.
6. Masukkan koordinat tersebut ke dalam konfigurasi atau kode game utama sebagai area target.

Contoh Output

X: 245
Y: 132

Catatan

- "CoordinateFinder.java" hanya digunakan selama proses pengembangan.
- File ini tidak dijalankan saat pemain memainkan game.
- Perubahan pada file ini tidak memengaruhi gameplay secara langsung.
- Utilitas ini dibuat untuk mempercepat proses penentuan area perbedaan pada setiap level permainan.
