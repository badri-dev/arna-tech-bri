# 🏦 Arna Tech BRI
**Developer:** Ghaushil Badri Amin  
**Organization:** Arna Tech

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-Compose-blue.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-SDK-green.svg)](https://developer.android.com/)

Aplikasi Android berbasis **Jetpack Compose** untuk sistem pembayaran mesin EDC (Electronic Data Capture), dikembangkan oleh **Ghaushil Badri Amin** dari **Arna Tech**.  
Aplikasi ini mendukung transaksi dengan **Chip Insert**, **Tap Contactless (NFC)**, serta **Manual Entry**, dan menyediakan fitur **History** serta **Settlement** transaksi.

---

## ✨ Fitur Utama

- 💳 **Pembayaran Multimetode:**
    - 🟢 **Insert (Chip)** → menampilkan status *“Reading Chip…”*
    - 🔵 **Tap (Contactless / NFC)** → menampilkan status *“Reading NFC Card…”*
    - 🟠 **Manual Entry** → input nomor kartu secara manual
- 📜 **Riwayat Transaksi (History):**
    - Menampilkan daftar transaksi lengkap: `ID`, `Merchant`, `Amount`, `Card`, `Status`
    - Status transaksi ditandai warna: 🟢 *Approved*, ⚪ *Settled*
    - Tombol **“Settle Now”** untuk menyelesaikan transaksi yang belum settled
- ⚙️ **Settlement Process**
- 🌈 **UI Modern & Animatif:**
    - Animasi fade-in / fade-out
    - Custom clipping (lengkungan dinamis)
    - Card dengan elevation & shadow lembut
- 🌐 **Integrasi Backend (Opsional):**
    - Dapat dikonfigurasi untuk memproses transaksi dan settlement melalui API

---

## 🏗️ Struktur Proyek

```plaintext
app/
├── manifests/
│   └── AndroidManifest.xml
│
├── kotlin+java/
│   └── com/test/briedc/
│       ├── data/
│       │   ├── models/          # Data model (HistoryModel, Device, dsb.)
│       │   ├── remote/          # API client, Retrofit service, network layer
│       │   └── repository/      # Repository pattern untuk data management
│       │
│       ├── router/              # Navigation & route management antar screen
│       │
│       ├── ui/
│       │   ├── components/      # Komponen UI reusable (Button, Card, ListItem, dsb.)
│       │   ├── screens/         # Screen utama seperti Sale, History, Settlement
│       │   └── theme/           # Konfigurasi tema (color, typography, shape)
│       │
│       ├── utils/               # Utility class seperti AppPreferencesManager & GlobalUtils
│       │
│       ├── viewmodels/          # ViewModel untuk setiap screen (SaleViewModel, HistoryViewModel, dsb.)
│       │
│       └── MainActivity.kt      # Entry point aplikasi (Host Compose & Navigation)
│
├── res/                         # Resource XML, drawable, layout, strings, dsb.
│
└── build.gradle.kts             # Gradle build configuration
⚙️ Setup Instructions

Ikuti langkah-langkah di bawah untuk menjalankan proyek secara lokal:

1. Clone Repository
git clone https://github.com/badri-dev/arna-tech-bri.git
cd arna-tech-bri

2. Buka di Android Studio

Gunakan Android Studio Giraffe atau versi terbaru

Pastikan Kotlin Plugin sudah terinstall

3. Sinkronisasi Gradle

Android Studio biasanya akan otomatis melakukan sinkronisasi.
Jika tidak, klik “Sync Project with Gradle Files” di toolbar atas.

4. Konfigurasi Environment

Pastikan pengaturan berikut sudah sesuai:

compileSdk = 36
minSdk = 24


Pastikan juga koneksi internet aktif untuk mendownload dependencies.

5. Jalankan Aplikasi

Pilih perangkat emulator atau device fisik, lalu tekan ▶️ Run App

🧠 Teknologi yang Digunakan
Teknologi	                    Deskripsi
Kotlin	                        Bahasa utama untuk pengembangan Android
Jetpack Compose	Framework UI    deklaratif modern dari Google
MVVM Architecture	            Arsitektur terstruktur dengan ViewModel
Retrofit	                    HTTP client untuk komunikasi dengan backend
ConstraintLayout Compose	    Layout fleksibel berbasis constraint
Material 3	                    Komponen UI modern dengan gaya Google Material You
Coroutines & Flow	            State management asinkron & reaktif
Preferences DataStore	        Penyimpanan data lokal modern & aman
