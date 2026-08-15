# Aplikasi Makanan Indonesia

Aplikasi Android katalog 10 makanan khas Indonesia (Bakso, Gudeg, Rendang, dll) lengkap dengan foto dan deskripsi. Dibangun menggunakan Kotlin dengan arsitektur **MVVM (Model-View-ViewModel)**, **Repository Pattern**, dan **Room Database (Android Jetpack)**.

---

## 🏛️ Arsitektur Aplikasi (MVVM + Repository + Room)

Aplikasi ini menggunakan pola arsitektur **Modern Android Development (MAD)** yang bersih, reaktif, dan modular:

```
Activity / UI Layer (MainActivity, DetailActivity, AboutActivity)
       │
       ▼  (Mengamati StateFlow / UiState)
ViewModel Layer (MainViewModel, DetailViewModel)
       │
       ▼  (Memanggil Suspend Function / Coroutines)
Repository Layer (MakananRepository -> MakananRepositoryImpl)
       │
       ▼  (Single Source of Truth / Flow)
Local Database (Room Database -> MakananDao -> MakananEntity)
```

### 📂 Struktur Direktori Proyek

```
com.auldy.makananindonesia/
├── data/
│   ├── local/
│   │   ├── entity/
│   │   │   └── MakananEntity.kt       # Entity tabel database Room
│   │   ├── room/
│   │   │   ├── MakananDao.kt          # DAO operasi query Flow & suspend
│   │   │   └── MakananDatabase.kt     # Singleton Room Database + Callback Pre-populate
│   │   └── MakananData.kt             # Data seed 10 makanan khas Indonesia
│   ├── model/
│   │   └── Makanan.kt                 # Domain model murni
│   └── repository/
│       ├── MakananRepository.kt       # Interface Repository
│       └── MakananRepositoryImpl.kt   # Implementasi Repository (SSOT)
├── di/
│   └── Injection.kt                   # Service locator / dependency injection
├── ui/
│   ├── common/
│   │   ├── UiState.kt                 # Sealed interface: Loading, Success, Error
│   │   └── ViewModelFactory.kt        # Factory untuk instansiasi ViewModel
│   ├── main/
│   │   └── MainViewModel.kt           # ViewModel katalog makanan (StateFlow)
│   └── detail/
│       └── DetailViewModel.kt         # ViewModel detail makanan
├── MainActivity.kt                    # ViewBinding + StateFlow collector via repeatOnLifecycle
├── DetailActivity.kt                  # ViewBinding + DetailViewModel
├── AboutActivity.kt                   # Profile Activity
└── ListMakananAdapter.kt              # RecyclerView Adapter dengan bindingAdapterPosition
```

---

## 💾 Kenapa Memilih Room Database (Opsi 1)?

Untuk arsitektur database, aplikasi ini menggunakan **Room Database (Android Jetpack)**:

1. **100% Offline-First & Cepat:** Data tersimpan lokal di perangkat pengguna dalam format SQLite, dapat diakses instan tanpa bergantung pada koneksi internet.
2. **Zero-Configuration:** Siapapun yang meng-clone atau membuka proyek ini dapat langsung melakukan build dan run tanpa memerlukan akun Firebase, API key, atau file `google-services.json`.
3. **Pre-population Otomatis:** Saat aplikasi pertama kali di-install dan database dibuat, Room callback secara otomatis menyemai (seed) 10 data makanan awal ke dalam SQLite.
4. **Reaktif dengan Kotlin Flow:** Setiap perubahan pada tabel Room otomatis dipancarkan melalui `Flow` hingga ke UI secara realtime.
5. **Siap Diperluas (Extensible):** Dengan adanya `MakananRepository`, di masa depan aplikasi dapat dengan mudah menambahkan sinkronisasi Cloud (Firebase / REST API) sebagai Remote DataSource tanpa mengubah kode ViewModel maupun Activity.

---

## 📌 Status Upgrade

Proyek ini awalnya dibuat tahun **2021** dan sudah di-upgrade penuh per **Agustus 2026** agar bisa dibuka dan di-build dengan Android Studio & tooling terbaru. Ringkasan versi:

| Komponen | 2021 (lama) | 2026 (sekarang) |
|---|---|---|
| Arsitektur | Monolitik (Direct List) | **MVVM + Repository + Room Database** |
| Database | Data statis di memori | **Room Database (SQLite) + KSP** |
| State Management | Manual UI update | **Kotlin Coroutines + StateFlow** |
| Kotlin | 1.3.72 | **2.0.21** |
| Kotlin Symbol Processing (KSP) | - | **2.0.21-1.0.28** |
| Android Gradle Plugin (AGP) | 4.1.2 | **8.13.0** |
| Gradle | 6.5 | **8.13** |
| compileSdk / targetSdk | 30 (Android 11) | **36 (Android 15/16)** |
| minSdk | 21 (Android 5.0) | **23 (Android 6.0)** |
| Repository dependency | `jcenter()` ❌ *(sudah tutup)* | `google()` + `mavenCentral()` |
| Java target | 1.8 | **17** |

---

## ❓ Kenapa Versi Lamanya Tidak Bisa Langsung Di-build?

Kalau proyek versi 2021 dibuka apa adanya di Android Studio versi sekarang, build **akan gagal total**. Penyebab utamanya:

1. **JCenter sudah resmi ditutup (shutdown) sejak Mei 2021.** Proyek lama mengambil sebagian dependency dari `jcenter()`. Karena server ini sudah mati, Gradle tidak akan pernah berhasil men-download dependency-nya — sync akan gagal dari langkah paling awal.
2. **AGP 4.1.2 & Gradle 6.5 tidak kompatibel dengan JDK modern** (JDK 17/21 yang dipakai Android Studio versi baru). Kombinasi versi lama ini juga sudah tidak didukung oleh Android Studio terbaru sama sekali.
3. **targetSdk 30 di bawah syarat minimum Google Play.** Untuk publish/update aplikasi ke Play Store, Google mewajibkan targetSdk mengikuti versi Android terbaru (setiap tahun naik).

---

## ✅ Apa yang Sudah Diupgrade

### 1. Build System & Tooling
- **`build.gradle` (root):** dependency `jcenter()` dihapus total, plugin `org.jetbrains.kotlin.android` (2.0.21) dan `com.google.devtools.ksp` (2.0.21-1.0.28) ditambahkan.
- **`settings.gradle`:** repository (`google()`, `mavenCentral()`) dipusatkan di sini lewat `dependencyResolutionManagement`.
- **`gradle-wrapper.properties`:** Gradle dinaikkan dari 6.5 → **8.13**.
- **`gradle.properties`:** ditambah `android.nonTransitiveRClass=true`, `org.gradle.parallel`, dan `org.gradle.caching`.

### 2. `app/build.gradle`
- `namespace 'com.auldy.makananindonesia'`, `compileSdk 36`, `targetSdk 36`, `minSdk 23`, `Java 17`.
- `buildFeatures { viewBinding true }`.
- Menambahkan dependensi Android Jetpack:
  - **Room Database:** `room-runtime:2.6.1`, `room-ktx:2.6.1`, `ksp 'androidx.room:room-compiler:2.6.1'`.
  - **Lifecycle & ViewModel:** `lifecycle-viewmodel-ktx:2.8.7`, `lifecycle-runtime-ktx:2.8.7`, `lifecycle-livedata-ktx:2.8.7`, `activity-ktx:1.10.1`.
  - **Coroutines:** `kotlinx-coroutines-android:1.10.1`.
- Standardisasi circular image view ke `de.hdodenhof:circleimageview:3.1.0` (Maven Central).
- Glide di-update ke versi stabil `5.0.7`.

### 3. `AndroidManifest.xml`
- Atribut `package="..."` dihapus (dipindah jadi `namespace` di Gradle).
- Menambahkan `android:exported="true"/"false"` secara eksplisit di setiap `<activity>`.

---

## 🔧 Cara Menjalankan

1. Buka proyek ini di **Android Studio versi terbaru** (2025.x ke atas).
2. Pastikan Gradle JDK di Android Studio diset ke **JDK 17**:
   - Buka menu: `File` → `Settings` → `Build, Execution, Deployment` → `Build Tools` → `Gradle`.
   - Pada dropdown **Gradle JDK**, pilih **`17` / `Oracle OpenJDK 17`** (misal di `C:\Program Files\Java\jdk-17`).
3. Lakukan **Gradle Sync** (ikon gajah di toolbar atas).
4. Jalankan aplikasi di emulator atau device fisik (**Android 6.0 / API 23 ke atas**).

---

## 🚀 Ide Pengembangan Selanjutnya (opsional)
- Migrasi UI dari XML layout ke **Jetpack Compose**.
- Menambahkan fitur UI tombol Favorite/Bookmark di `DetailActivity` yang langsung meng-update `isFavorite` di Room Database.
- Fitur pencarian makanan (Search Bar) di `MainActivity` memanfaatkan `makananDao.searchMakanan(query)`.
- Menambahkan remote data source (Firebase / REST API) ke dalam `MakananRepository` untuk sinkronisasi cloud.
