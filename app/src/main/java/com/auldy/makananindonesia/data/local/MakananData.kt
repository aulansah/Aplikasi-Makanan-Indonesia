package com.auldy.makananindonesia.data.local

import com.auldy.makananindonesia.data.local.entity.MakananEntity

object MakananData {

    private data class Seed(
        val nama: String,
        val detail: String,
        val photoKey: String,
        val category: String,
        val rating: Double,
        val asalDaerah: String,
        val estimasiKalori: Int,
        val waktuMasakMenit: Int,
        val jumlahUlasan: String,
        val hargaEstimasi: Int,
        val isFeatured: Boolean = false
    )

    private val seeds = listOf(
        Seed(
            nama = "Bakso",
            detail = "Bakso atau baso adalah jenis bola daging yang lazim ditemukan pada masakan Indonesia. Bakso umumnya dibuat dari campuran daging sapi giling dan tepung tapioka, disajikan panas-panas dengan kuah kaldu sapi bening, dicampur mi, bihun, taoge, tahu, terkadang telur lalu ditaburi bawang goreng dan seledri. Bakso sangat populer dan dapat ditemukan di seluruh Indonesia, dari gerobak pedagang kaki lima hingga restoran besar.",
            photoKey = "bakso", category = "Berkuah", rating = 4.8,
            asalDaerah = "Populer di seluruh Indonesia · asal-usul dari budaya Tionghoa-Indonesia",
            estimasiKalori = 260, waktuMasakMenit = 45, jumlahUlasan = "1.8k ulasan", hargaEstimasi = 18000
        ),
        Seed(
            nama = "Gudeg",
            detail = "Gudeg adalah makanan khas Provinsi Yogyakarta dan Jawa Tengah yang terbuat dari nangka muda yang dimasak dengan santan. Perlu waktu berjam-jam untuk membuat masakan ini. Warna coklat biasanya dihasilkan oleh daun jati yang dimasak bersamaan. Gudeg biasanya dimakan dengan nasi dan disajikan dengan kuah santan kental (areh), ayam kampung, telur, tempe, tahu dan sambal goreng krecek.",
            photoKey = "gudeg", category = "Manis", rating = 4.7,
            asalDaerah = "Yogyakarta & Jawa Tengah",
            estimasiKalori = 350, waktuMasakMenit = 240, jumlahUlasan = "1.2k ulasan", hargaEstimasi = 22000
        ),
        Seed(
            nama = "Mie Ayam",
            detail = "Mi ayam atau bakmi ayam adalah masakan Indonesia yang terbuat dari mi kuning direbus mendidih kemudian ditaburi saus kecap khusus beserta daging ayam dan sayuran. Mi ayam terkadang ditambahi dengan bakso, pangsit, dan jamur. Meskipun mi berasal dari Tiongkok, mi ayam kini sudah menjadi makanan sehari-hari yang tersebar di seluruh Indonesia.",
            photoKey = "mieayam", category = "Berkuah", rating = 4.6,
            asalDaerah = "Populer di seluruh Indonesia · sentra terkenal di Wonogiri",
            estimasiKalori = 380, waktuMasakMenit = 30, jumlahUlasan = "2.4k ulasan", hargaEstimasi = 15000
        ),
        Seed(
            nama = "Nasi Goreng",
            detail = "Nasi goreng adalah sebuah makanan berupa nasi yang digoreng dan diaduk dalam minyak goreng, margarin, atau mentega. Biasanya ditambah kecap manis, bawang merah, bawang putih, asam jawa, lada dan bumbu-bumbu lainnya; seperti telur, ayam, dan kerupuk. Nasi goreng juga dikenal sebagai masakan nasional Indonesia yang dapat dinikmati dari warung tepi jalan hingga restoran mewah.",
            photoKey = "nasigoreng", category = "Pedas", rating = 4.9,
            asalDaerah = "Masakan nasional Indonesia",
            estimasiKalori = 420, waktuMasakMenit = 20, jumlahUlasan = "3.1k ulasan", hargaEstimasi = 20000
        ),
        Seed(
            nama = "Pecel",
            detail = "Pecel atau pecal merupakan makanan yang dikombinasikan dengan bumbu sambal kacang sebagai bahan utamanya dan dicampur dengan aneka jenis sayuran. Makanan ini populer terutama di wilayah DI Yogyakarta, Jawa Tengah, dan Jawa Timur. Dalam bahasa Jawa, pecel dapat diartikan sebagai 'tumbuk' atau 'dihancurkan dengan cara ditumbuk'.",
            photoKey = "pecel", category = "Sayuran", rating = 4.5,
            asalDaerah = "Yogyakarta, Jawa Tengah & Jawa Timur",
            estimasiKalori = 210, waktuMasakMenit = 25, jumlahUlasan = "980 ulasan", hargaEstimasi = 12000
        ),
        Seed(
            nama = "Pempek",
            detail = "Pempek atau empek-empek adalah makanan yang terbuat dari daging ikan yang digiling lembut yang dicampur tepung kanji atau tepung sagu, serta beberapa bahan lain seperti telur, bawang putih yang dihaluskan, penyedap rasa, dan garam. Pempek biasanya disajikan dengan kuah cuka yang memiliki rasa asam, manis, dan pedas. Pempek sering disebut sebagai makanan khas Palembang.",
            photoKey = "pempek", category = "Asam", rating = 4.7,
            asalDaerah = "Palembang, Sumatera Selatan",
            estimasiKalori = 290, waktuMasakMenit = 50, jumlahUlasan = "1.5k ulasan", hargaEstimasi = 25000
        ),
        Seed(
            nama = "Rawon",
            detail = "Rawon adalah masakan Indonesia berupa sup daging berkuah hitam dengan campuran bumbu khas yang menggunakan kluwek. Rawon dikenal sebagai masakan khas Jawa Timur (daerah Arekan), dan juga dikenal oleh masyarakat Jawa Tengah sebelah timur. Warna gelap khas rawon berasal dari kluwek, dan di luar negeri rawon disebut sebagai black soup.",
            photoKey = "rawon", category = "Berkuah", rating = 4.8,
            asalDaerah = "Jawa Timur",
            estimasiKalori = 310, waktuMasakMenit = 90, jumlahUlasan = "1.1k ulasan", hargaEstimasi = 24000
        ),
        Seed(
            nama = "Rendang",
            detail = "Rendang atau randang adalah masakan daging asli Indonesia yang berasal dari Minangkabau. Masakan ini dihasilkan dari proses memasak suhu rendah dalam waktu lama menggunakan aneka rempah-rempah dan santan. Proses memasaknya memakan waktu berjam-jam (biasanya sekitar empat jam) hingga yang tinggal hanyalah potongan daging berwarna hitam pekat dan dedak. Dalam suhu ruangan, rendang dapat bertahan hingga berminggu-minggu.",
            photoKey = "rendang", category = "Berdaging", rating = 4.9,
            asalDaerah = "Masakan Minangkabau · Sumatera Barat",
            estimasiKalori = 320, waktuMasakMenit = 240, jumlahUlasan = "2.1k ulasan", hargaEstimasi = 28000,
            isFeatured = true
        ),
        Seed(
            nama = "Sate",
            detail = "Sate atau satai adalah makanan yang terbuat dari daging yang dipotong kecil-kecil dan ditusuk sedemikian rupa dengan tusukan lidi tulang daun kelapa atau bambu, kemudian dipanggang menggunakan bara arang kayu. Sate disajikan dengan berbagai macam bumbu yang bergantung pada variasi resep sate. Daging yang dijadikan sate antara lain daging ayam, kambing, domba, dan sapi.",
            photoKey = "sate", category = "Berdaging", rating = 4.8,
            asalDaerah = "Populer di seluruh Indonesia · asal-usul dari Jawa",
            estimasiKalori = 280, waktuMasakMenit = 35, jumlahUlasan = "2.7k ulasan", hargaEstimasi = 25000
        ),
        Seed(
            nama = "Soto",
            detail = "Soto, sroto, sauto, tauto, atau coto adalah makanan khas Indonesia seperti sop yang terbuat dari kaldu daging dan sayuran. Daging yang paling sering digunakan adalah daging sapi dan ayam. Berbagai daerah di Indonesia memiliki soto khas daerahnya masing-masing dengan komposisi berbeda-beda, misalnya soto Madura, soto Lamongan, soto Betawi, soto Padang, dan coto Makassar.",
            photoKey = "soto", category = "Berkuah", rating = 4.7,
            asalDaerah = "Beragam daerah di seluruh Indonesia",
            estimasiKalori = 270, waktuMasakMenit = 40, jumlahUlasan = "1.9k ulasan", hargaEstimasi = 17000
        )
    )

    val initialMakananList: List<MakananEntity>
        get() = seeds.mapIndexed { index, s ->
            MakananEntity(
                id = index + 1,
                nama = s.nama,
                detail = s.detail,
                photoKey = s.photoKey,
                category = s.category,
                rating = s.rating,
                asalDaerah = s.asalDaerah,
                estimasiKalori = s.estimasiKalori,
                waktuMasakMenit = s.waktuMasakMenit,
                jumlahUlasan = s.jumlahUlasan,
                hargaEstimasi = s.hargaEstimasi,
                isFavorite = false,
                isFeatured = s.isFeatured
            )
        }
}
