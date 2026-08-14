package com.auldy.makananindonesia

object MakananData {
    private val namaMakanan = arrayOf("Bakso",
        "Gudeg",
        "Mie Ayam",
        "Nasi Goreng",
        "Pecel",
        "Pempek",
        "Rawon",
        "Rendang",
        "Sate",
        "Soto")

    private val makananDetails = arrayOf("Bakso atau baso adalah jenis bola daging yang lazim ditemukan pada masakan Indonesia.Bakso umumnya dibuat dari campuran daging sapi giling dan tepung tapioka, tetapi ada juga bakso yang terbuat dari daging ayam, ikan, atau udang bahkan daging kerbau. Dalam penyajiannya, bakso umumnya disajikan panas-panas dengan kuah kaldu sapi bening, dicampur mi, bihun, taoge, tahu, terkadang telur lalu ditaburi bawang goreng dan seledri. Bakso sangat populer dan dapat ditemukan di seluruh Indonesia, dari gerobak pedagang kaki lima hingga restoran besar. Berbagai jenis bakso sekarang banyak ditawarkan dalam bentuk makanan beku yang dijual di pasar swalayan ataupun mal-mal. Irisan bakso dapat juga dijadikan pelengkap jenis makanan lain seperti mi goreng, nasi goreng, atau capcai.",
        "Gudeg (bahasa Jawa: ꦒꦸꦝꦼꦒ꧀, translit. Gudheg) adalah makanan khas Provinsi Yogyakarta dan Jawa Tengah yang terbuat dari nangka muda yang dimasak dengan santan. Perlu waktu berjam-jam untuk membuat masakan ini. Warna coklat biasanya dihasilkan oleh daun jati yang dimasak bersamaan. Gudeg biasanya dimakan dengan nasi dan disajikan dengan kuah santan kental (areh), ayam kampung, telur, tempe, tahu dan sambal goreng krecek.",
        "Mi ayam atau bakmi ayam adalah masakan Indonesia yang terbuat dari mi kuning direbus mendidih kemudian ditaburi saus kecap khusus beserta daging ayam dan sayuran. Mi ayam terkadang ditambahi dengan bakso, pangsit, dan jamur. Mi berasal dari Tiongkok, tetapi mi ayam yang serupa di Indonesia tidak ditemukan di Tiongkok.Mi ayam aslinya dari Tiongkok Selatan terutama dari daerah-daerah pelabuhan seperti Fujian dan Guandong.Meskipun mi bukan asli Indonesia tapi nyatanya kini mi ayam seakan sudah menjadi makanan tradisional Indonesia. Makanan ini sudah tersebar di seluruh Indonesia, terutama di daerah Jawa makanan ini sangat mudah di temukan. Penjual mi ayam di Indonesia yang populer berasal dari Wonogiri.",
        "Nasi goreng adalah sebuah makanan berupa nasi yang digoreng dan diaduk dalam minyak goreng, margarin, atau mentega. Biasanya ditambah kecap manis, bawang merah, bawang putih, asam jawa, lada dan bumbu-bumbu lainnya; seperti telur, ayam, dan kerupuk. Ada pula nasi goreng jenis lain yang dibuat bersama ikan asin yang juga populer di seluruh Indonesia. Nasi goreng juga dikenal sebagai masakan nasional Indonesia.Dari sekian banyak hidangan dalam khazanah Masakan Indonesia, hanya sedikit yang dapat dianggap sebagai makanan nasional sejati. Masakan nasional Indonesia ini tidak mengenal batasan kelas sosial. Nasi goreng dapat dinikmati secara sederhana di warung tepi jalan, gerobak penjaja keliling, hingga restoran dan meja prasmanan dalam pesta.",
        "Pecel atau pecal (Hanacaraka: ꦥꦼꦕꦼꦭ꧀) merupakan makanan yang dikombinasikan dengan bumbu sambal kacang sebagai bahan utamanya dan dicampur dengan aneka jenis sayuran. Makanan ini populer terutama di wilayah DI Yogyakarta, Jawa Tengah, dan Jawa Timur. Asal nama dan asal daerah dari pecel belum diketahui secara pasti. Namun dapat dimaknai secara etimologi, dalam bahasa Jawa, bahwa pecel dapat diartikan sebagai 'tumbuk' atau 'dihancurkan dengan cara ditumbuk'.",
        "Pempek atau empek-empek adalah makanan yang terbuat dari daging ikan yang digiling lembut yang dicampur tepung kanji atau tepung sagu, serta komposisi beberapa bahan lain seperti telur, bawang putih yang dihaluskan, penyedap rasa, dan garam. Pempek biasanya disajikan dengan kuah cuka yang memiliki rasa asam, manis, dan pedas. Pempek sering disebut sebagai makanan khas Palembang,meskipun hampir semua daerah di Sumatera Selatan memroduksinya.",
        "Rawon adalah masakan Indonesia berupa sup daging berkuah hitam dengan campuran bumbu khas yang menggunakan kluwek. Rawon, meskipun dikenal sebagai masakan khas Jawa Timur (daerah Arekan), dikenal pula oleh masyarakat Jawa Tengah sebelah timur (daerah Surakarta). Daging untuk rawon umumnya adalah daging sapi yang dipotong kecil-kecil, utamanya adalah bagian sandung lamur. Bumbu supnya sangat khas Indonesia, yaitu campuran bawang merah, bawang putih, lengkuas (laos), ketumbar, kemiri, serai, kunir, cabai, kluwek, garam, serta minyak nabati. Semua bahan ini dihaluskan, lalu ditumis sampai harum. Campuran bumbu ini kemudian dimasukkan dalam kaldu rebusan daging bersama-sama dengan daging. Warna gelap khas rawon berasal dari kluwek. Di luar negeri, rawon disebut sebagai black soup.",
        "Rendang atau randang (Jawi: رندڠ) adalah masakan daging asli Indonesia yang berasal dari Minangkabau. Masakan ini dihasilkan dari proses memasak suhu rendah dalam waktu lama menggunakan aneka rempah-rempah dan santan. Proses memasaknya memakan waktu berjam-jam (biasanya sekitar empat jam) hingga yang tinggal hanyalah potongan daging berwarna hitam pekat dan dedak. Dalam suhu ruangan, rendang dapat bertahan hingga berminggu-minggu. Rendang yang dimasak dalam waktu yang lebih singkat dan santannya belum mengering disebut kalio, berwarna cokelat terang keemasan.",
        "Sate atau satai adalah makanan yang terbuat dari daging yang dipotong kecil-kecil dan ditusuk sedemikian rupa dengan tusukan lidi tulang daun kelapa atau bambu, kemudian dipanggang menggunakan bara arang kayu. Sate disajikan dengan berbagai macam bumbu yang bergantung pada variasi resep sate.[1] Daging yang dijadikan sate antara lain daging ayam, kambing, domba, sapi, babi, kelinci, kuda, dan lain-lain.",
        "Soto, sroto, sauto, tauto, atau coto adalah makanan khas Indonesia seperti sop yang terbuat dari kaldu daging dan sayuran. Daging yang paling sering digunakan adalah daging sapi dan ayam, tetapi ada pula yang menggunakan daging babi atau kambing. Berbagai daerah di Indonesia memiliki soto khas daerahnya masing-masing dengan komposisi yang berbeda-beda, misalnya soto Madura, soto Kediri, soto Pemalang, soto Lamongan, soto Jepara, soto Semarang, soto Kudus, soto Betawi, soto Padang, soto Bandung, sroto Sokaraja, soto Banjar, soto Medan, dan coto Makassar. Soto juga diberi nama sesuai isinya, misalnya soto ayam, soto babat, atau soto kambing. Ada pula soto yang dibuat dari daging kaki sapi yang disebut dengan soto sekengkel.")

    private val makananImages = intArrayOf(R.drawable.bakso,
        R.drawable.gudeg,
        R.drawable.mieayam,
        R.drawable.nasigoreng,
        R.drawable.pecel,
        R.drawable.pempek,
        R.drawable.rawon,
        R.drawable.rendang,
        R.drawable.sate,
        R.drawable.soto)

    val listData: ArrayList<Makanan>
        get() {
            val list = arrayListOf<Makanan>()
            for (position in namaMakanan.indices) {
                val makan = Makanan()
                makan.nama = namaMakanan[position]
                makan.detail = makananDetails[position]
                makan.photo = makananImages[position]
                list.add(makan)
            }
            return list
        }

}