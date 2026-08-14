# Aplikasi Makanan Indonesia

Aplikasi Android sederhana yang menampilkan katalog 10 makanan khas Indonesia (Bakso, Gudeg, Rendang, dll) lengkap dengan foto dan deskripsi. Dibuat dengan Kotlin, tanpa backend/API — semua data tersimpan langsung di dalam kode (`MakananData.kt`).

Fitur:
- `MainActivity` — daftar makanan (RecyclerView)
- `DetailActivity` — detail satu makanan saat item di-tap
- `AboutActivity` — halaman profil developer

---

## 📌 Status Upgrade

Proyek ini awalnya dibuat tahun **2021** dan sudah di-upgrade penuh per **Agustus 2026** agar bisa dibuka dan di-build dengan Android Studio & tooling terbaru. Ringkasan versi:

| Komponen | 2021 (lama) | 2026 (sekarang) |
|---|---|---|
| Kotlin | 1.3.72 | 2.3.20 |
| Android Gradle Plugin (AGP) | 4.1.2 | 8.13.0 |
| Gradle | 6.5 | 8.13 |
| compileSdk / targetSdk | 30 (Android 11) | 36 (Android 15/16) |
| minSdk | 21 (Android 5.0) | 23 (Android 6.0) |
| Repository dependency | `jcenter()` ❌ *(sudah tutup)* | `google()` + `mavenCentral()` |
| Java target | 1.8 | 17 |

---

## ❓ Kenapa Versi Lamanya Tidak Bisa Langsung Di-build?

Kalau proyek versi 2021 dibuka apa adanya di Android Studio versi sekarang, build **akan gagal total**. Penyebab utamanya:

1. **JCenter sudah resmi ditutup (shutdown) sejak Mei 2021.** Proyek lama mengambil sebagian dependency dari `jcenter()`. Karena server ini sudah mati, Gradle tidak akan pernah berhasil men-download dependency-nya — sync akan gagal dari langkah paling awal.
2. **AGP 4.1.2 & Gradle 6.5 tidak kompatibel dengan JDK modern** (JDK 17/21 yang dipakai Android Studio versi baru). Kombinasi versi lama ini juga sudah tidak didukung oleh Android Studio terbaru sama sekali.
3. **targetSdk 30 di bawah syarat minimum Google Play.** Untuk publish/update aplikasi ke Play Store, Google mewajibkan targetSdk mengikuti versi Android terbaru (setiap tahun naik).

Karena tiga hal ini, "mungkin bisa jalan" itu benar — **kemungkinan besar tidak akan bisa** kalau dipaksa jalan tanpa upgrade.

---

## ✅ Apa yang Sudah Diupgrade

### 1. Build System (paling krusial)
- **`build.gradle` (root):** dependency `jcenter()` dihapus total, format plugin diubah ke gaya deklaratif modern (`plugins { ... }` dengan versi eksplisit).
- **`settings.gradle`:** repository (`google()`, `mavenCentral()`) sekarang dipusatkan di sini lewat `dependencyResolutionManagement`, bukan lagi di `allprojects` pada root `build.gradle` (pola lama sudah deprecated sejak AGP 7).
- **`gradle-wrapper.properties`:** Gradle dinaikkan dari 6.5 → **8.13**.
- **`gradle.properties`:** ditambah `android.nonTransitiveRClass=true` (best practice modern, build lebih cepat & aman dari bentrok R class), `org.gradle.parallel`, dan `org.gradle.caching`. `enableJetifier` dihapus karena sudah tidak relevan (tidak ada lagi dependency Support Library lama).

### 2. `app/build.gradle`
- AGP-related config disesuaikan ke gaya AGP 8.x: `namespace` sekarang dideklarasikan di sini (bukan lagi lewat atribut `package` di `AndroidManifest.xml` — ini **wajib** sejak AGP 8, kalau tidak, build gagal).
- `compileSdk`/`targetSdk`: 30 → **36**.
- `minSdk`: 21 → **23** (Android 6.0 ke atas, ±99% device aktif saat ini masih tercakup; beberapa library AndroidX terbaru sudah mensyaratkan minimum ini).
- `sourceCompatibility`/`jvmTarget`: Java 8 → **Java 17**.
- Menambahkan `buildFeatures { viewBinding true }` untuk mendukung modernisasi kode (lihat bagian di bawah).
- Dependency duplikat (`material` ditulis dua kali dengan versi berbeda) dihapus, `mediarouter` (tidak dipakai sama sekali di kode) dihapus, dan `com.mikhaellopez:circularimageview:3.2.0` (hanya ada di JCenter) dihapus lalu distandarkan ke `de.hdodenhof:circleimageview:3.1.0` yang resmi tersedia di Maven Central.
- Semua versi library dinaikkan ke rilis stabil terbaru: Kotlin stdlib, `core-ktx`, `appcompat`, `material`, `constraintlayout`, `recyclerview`, Glide (4.11 → **5.0.7**), JUnit, Espresso.

### 3. `AndroidManifest.xml`
- Atribut `package="..."` dihapus (dipindah jadi `namespace` di Gradle, sesuai aturan AGP 8+).
- Menambahkan `android:exported="true"/"false"` secara eksplisit di setiap `<activity>`. **Ini wajib** sejak targetSdk 31 ke atas — tanpa ini, aplikasi akan **crash saat instalasi**, bukan sekadar warning.

### 4. Modernisasi Kode Kotlin & Layout
- **`findViewById` manual → ViewBinding.** Semua Activity dan Adapter sekarang pakai `ActivityMainBinding`, `ActivityDetailBinding`, `ActivityAboutBinding`, `ItemRowMakananBinding` yang di-generate otomatis oleh Gradle. Keuntungan: lebih aman dari `NullPointerException` dan error tipe view yang salah (dulu semua itu baru ketahuan saat app di-run, sekarang ketahuan saat compile).
- **Standardisasi Circular Image View:** Komponen `com.mikhaellopez.circularimageview.CircularImageView` di `activity_about.xml` diganti menjadi `de.hdodenhof.circleimageview.CircleImageView` agar seragam dengan list item dan terbebas dari dependensi JCenter yang sudah mati.
- **`AboutActivity`:** override `onKeyDown(KEYCODE_BACK)` dihapus. Kode ini sebenarnya sejak awal tidak melakukan apa-apa yang berbeda dari perilaku default Android (tombol back sistem sudah otomatis `finish()` activity), dan pola ini sudah lama digantikan `OnBackPressedCallback` di Android modern.
- **`DetailActivity`:** ekstra Intent yang nullable (`getStringExtra`) dulu di-`.toString()` (berisiko menampilkan teks literal `"null"` kalau datanya kosong), sekarang pakai `.orEmpty()` yang lebih aman.
- Import yang tidak terpakai (`org.w3c.dom.Text`, sisa copy-paste) dibersihkan.

### 5. File Proyek Lain
- Folder `app/build/`, `.gradle/`, `.idea/`, dan `local.properties` lama dihapus dari paket ini — semua ini adalah file cache/konfigurasi mesin lokal lama yang akan otomatis dibuat ulang oleh Android Studio saat proyek pertama kali dibuka, dan kalau dibiarkan justru berpotensi bikin konflik dengan environment baru.

---

## 🔧 Cara Menjalankan Sekarang

1. Buka proyek ini di **Android Studio versi terbaru** (2025.x ke atas).
2. Biarkan Android Studio melakukan **Gradle Sync** otomatis (akan otomatis download Gradle 8.13 & AGP 8.13 sesuai konfigurasi).
3. Jalankan di emulator/device dengan **Android 6.0 (API 23) ke atas**.

Tidak perlu setup tambahan — tidak ada API key, tidak ada backend, semua data sudah include di dalam kode.

---

## 💡 Kenapa Kotlin/Android Butuh Upgrade Berkala?

Ini contoh nyata kenapa proyek Android tidak bisa "didiamkan" terlalu lama:
- **Repository dependency bisa mati** (kasus JCenter) — proyek yang bergantung padanya otomatis tidak bisa di-build lagi, walau kodenya sendiri tidak salah apa-apa.
- **Google Play punya syarat targetSdk minimum yang naik tiap tahun** — app lama otomatis tidak bisa di-update/publish kalau tidak mengikuti.
- **AGP & Gradle rilis versi major (breaking changes) tiap 1–2 tahun** — banyak konfigurasi lama (seperti `package` di manifest, `allprojects` repositories) dihapus dukungannya seiring waktu.
- **Library pihak ketiga terus rilis versi baru** untuk perbaikan keamanan dan bug — versi lama makin lama makin berisiko dan makin sulit dicari dokumentasinya.

Untuk proyek sekecil ini, effort upgrade relatif ringan (tidak ada arsitektur kompleks, database, atau network layer). Untuk proyek yang jauh lebih besar, disarankan melakukan upgrade bertahap tiap 6–12 bulan agar jaraknya tidak sejauh ini (2021 → 2026 = 5 tahun loncatan).

---

## 🚀 Ide Pengembangan Selanjutnya (opsional)
- Migrasi UI dari XML layout ke **Jetpack Compose**.
- Ganti `de.hdodenhof.circleimageview` dengan transformasi `CircleCrop` bawaan Glide atau `ShapeableImageView` dari Material Components, atau `Modifier.clip(CircleShape)` jika sudah menggunakan Compose.
- Pindahkan data makanan yang hardcoded ke Room database atau file JSON/API, supaya lebih mudah ditambah tanpa perlu rebuild aplikasi.
