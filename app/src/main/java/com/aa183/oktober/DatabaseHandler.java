package com.aa183.oktober;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_SUTRADARA = "Sutradara";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault());
    private Context context;

    public DatabaseHandler(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_SUTRADARA + " TEXT, "
                + KEY_SINOPSIS + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahFilm(film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(film dataFilm, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdfilm())});

        db.close();
    }

    public void hapusFilm(int idfilm){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idfilm)});
        db.close();
    }

    public ArrayList<film> getAllFilm(){
        ArrayList<film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                }catch (ParseException er){
                    er.printStackTrace();
                }

                film tempFilm = new film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5)
                );
                dataFilm.add(tempFilm);
            }while (csr.moveToNext());
        }

        return dataFilm;
    }

    private String storeImageFile(int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInteralStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db){
        int idfilm = 0;
        Date tempDate = new Date();

        //Menambah data film ke-1
        try {
            tempDate = sdFormat.parse("04/12/2019");
        }catch (ParseException er){
            er.printStackTrace();
        }

        film film1 = new film(
                idfilm,
                "Warkop DKI Reborn: Jangkrik Boss! Part 1",
                tempDate,
                storeImageFile(R.drawable.film1),
                "Anggy Umbara",
                "Dono (Abimana Aryasatya), Kasino (Vino Bastian), dan Indro (Tora Sudiro) adalah tiga orang sahabat yang bekerja sebagai petugas keamanaan di organisasi CHIIPS (Cara Hebat Ikut Ikutan Pelayanan Sosial) dimana tugas mereka adalah membantu menertibkan dan menjaga keamanan masyarakat. Namun, tingkah mereka yang konyol dan bermasalah selalu membuat jengkel dan marah atasan mereka, Pak Boss (Ence Bagus), walaupun mereka berhasil lolos dari ancaman pemecatan.\n" +
                        "\n" +
                        "Dikarenakan mereka bertiga merupakan anggota CHIIPS yang memiliki rekor paling buruk, Boss memasukkan anggota CHIIPS dari Paris bernama Sophie (Hannah Al Rashid) untuk membantu mereka. Patroli pertama mereka berakhir buruk setelah mereka gagal mengejar seorang Copet (Arie Kriting), merusak warung warga, dan menyebabkan kebakaran pada sebuah pameran lukisan. Mereka ditangkap dan dibawa ke pengadilan di mana mereka bertiga dituntut untuk mengganti rugi dengan membayar denda sebesar 8 miliar rupiah atau mereka akan dipenjara.\n" +
                        "\n" +
                        "Dono, Kasino, dan Indro yang kebingungan mencari uang, mengunjungi paman Dono yaitu Pak Selamet (Tarzan) untuk meminjam uang. Rencana mereka gagal setelah mereka menyadari bahwa koper pemberian Pak Selamet berisi uang mainan dan hampir diamuk warga karena dikira mengedarkan uang palsu. Stress, Indro marah-marah dengan Indro dari masa depan (Indro (Warkop)) yang hanya ada di kepalanya. Sophie yang merasa kasihan mengajak mereka bertiga ke pesta. Namun di perjalanan, mereka tidak sengaja melihat seorang pria (Bene Dion) ditabrak oleh mobil misterius. Mereka kemudian membawa pria itu ke rumah sakit, saat sekarat, pria itu menyerahkan sebuah buku berisi peta harta karun pada mereka bertiga. Dono, Kasino, dan Indro pun akhirnya menerima peta tersebut dan berniat untuk mencari harta tersebut agar bisa membayar denda 8 miliar.\n" +
                        "\n" +
                        "Mereka pergi ke Malaysia sesuai petunjuk kode dalam peta dengan bantuan Sophie dan menjual barang-barang mereka. Sesampainya di bandara Malaysia, mereka menyadari bahwa tas berisi buku harta karun tersebut tertukar. Sempat berpencar untuk mencari tas tersebut, Kasino menemukan layar CCTV bandara yang menunjukkan tas mereka tertukar dengan tas seorang wanita berbaju merah (Nur Fazura). Sempat kejar-kejaran dengan menggunakan taksi hingga ke China Town, mereka kehilangan jejak wanita tersebut karena banyak wanita-wanita di sana yang juga menggunakan baju merah."
        );
        tambahFilm(film1, db);
        idfilm++;

        //Menambah data film ke-2
        try {
            tempDate = sdFormat.parse("06/12/2019");
        }catch (ParseException er){
            er.printStackTrace();
        }

        film film2 = new film(
                idfilm,
                "Hangout",
                tempDate,
                storeImageFile(R.drawable.film2),
                "Raditya Dika",
                "seorang pria misterius bernama Toni Sacalu, dengan inisial P. Toni Sacalu mengundang 9 publik figur untuk ‘Hangout’ di villa di sebuah pulau terpencil,\n" +
                        "\n" +
                        "Hangout ini bertujuan untuk membicarakan sebuah proyek dengan sejumlah uang besar. Mereka pun berangkat memenuhi undangan tersebut.\n" +
                        "\n" +
                        "Setibanya di sana, masalah muncul sejak malam pertama ketika Mathias Muchus mati diracun di hadapan mereka. Kendala berikutnya, mereka tidak bisa segera kembali karena perahu penjemput mereka, akan tiba lima hari kemudian. Mereka pun terjebak dalam pulau tersebut.\n" +
                        "\n" +
                        "Mereka berusaha untuk bertahan hidup, tapi satu persatu malah mati. Ketika tinggal berempat, salah seorang dari mereka berhasil menemukan siapa pembunuhnya. Sayang belum sempat memberitahu siapa pembunuhnya, dia pun malah mati.\n" +
                        "\n" +
                        "Mengapa Pak Toni Sacalu mengundang mereka semua, kemudian mereka harus dibunuh? Siapa sajakah mereka yang tinggal berempat? Siapakah pembunuh sebenarnya, apakah Pak Toni Sacalu atau satu di antara mereka berempat?"
        );
        tambahFilm(film2, db);
        idfilm++;

        //Menambah data film ke-3
        try {
            tempDate = sdFormat.parse("05/08/2019");
        }catch (ParseException er){
            er.printStackTrace();
        }

        film film3 = new film(
                idfilm,
                "Dilan 1990",
                tempDate,
                storeImageFile(R.drawable.film3),
                "Fajar Bustomi,Pidi Baiq",
                "Pada September 1990, Milea dan keluarganya pindah dari Jakarta ke Bandung. Saat hendak masuk di sebuah SMA, Milea bertemu dengan Dilan sang panglima geng motor. Dilan tak memperkenalkan dirinya, namun dengan sangat percaya diri segera meramal kalau Milea akan naik motor bersamanya dan menjadi pacarnya. Dilan, entah bagaimana caranya, mengetahui segala tentang Milea, bahkan alamat rumah dan nomor teleponnya. Singkat cerita, Dilan merayu-rayu Milea dengan memberikan berbagai hadiah yang bermakna, misalnya buku teka-teki silang yang sudah diisi supaya \"tidak perlu pusing karena harus mengisinya.\" Pada titik ini, Milea masih memiliki seorang pacar bernama Benni, yang ia tinggalkan secara fisik di Jakarta. Milea sendiri merasa tidak nyaman karena Benni adalah lelaki yang pencemburu dan kasar. Kepercayaan diri Dilan yang berlebih sempat membuat Nandan, sang sahabat yang juga menyukai Milea, tidak nyaman. Meski begitu, Milea mulai menyukai Dilan.\n" +
                        "\n" +
                        "Saat kelompok Milea maju ke lomba Cerdas Cermat antar sekolah yang dihelat di kantor pusat TVRI di Jakarta, tiba-tiba saja Benni muncul ke hadapan Milea. Milea, yang sedang makan berdua saja dengan Nandan karena ditinggal teman mereka yang pergi ke kamar mandi, terlibat cekcok dengan Benni yang mengira Nandan merusak hubungan asmaranya. Benni menghajar Nandan sebelum dilerai oleh Milea. Benni mengata-ngatai Milea dengan sebutan genit berkali-kali, sehingga Milea memutuskan hubungan mereka. Benni marah besar, melanjutkan makiannya dengan menyebut Milea \"pelacur\". Sekembalinya ke Bandung, Milea ditelepon Benni, yang kemudian memohon maaf. Milea sudah memaafkannya, namun menolak ajakan untuk kembali berpacaran. Benni mengeluarkan lagi makian \"setan\" dan \"pelacur\", yang dibalas Milea dengan menutup telepon. Setelahnya, hubungan Dilan dan Milea makin dekat saja, walau belum pernah ada kata cinta terucap. Mereka pulang sekolah berboncengan, sesekali bergandengan tangan, dan bertelepon malam-malam. Saking dekatnya, Milea berhasil membujuk Dilan supaya tidak lagi terlibat dalam tawuran antar geng."
        );
        tambahFilm(film3, db);
        idfilm++;

        //Menambah data film ke-4
        try {
            tempDate = sdFormat.parse("31/07/2019");
        }catch (ParseException er){
            er.printStackTrace();
        }

        film film4 = new film(
                idfilm,
                "My Stupid Boss",
                tempDate,
                storeImageFile(R.drawable.film4),
                "Upi Avianto",
                "Karena krisis kekurangan karyawan pabrik, akhirnya Bossman berniat untuk mencari karyawan pabrik baru di Vietnam. Berangkatlah Bossman, Diana, Mr.Kho, dan Adrian ke Vietnam. Di Vietnam alih-alih mendapatkan karyawan, justru mereka mendapatkan masalah demi masalah bertubi-tubi karena ulah Bossman."
        );
        tambahFilm(film4, db);
        idfilm++;
    }
}
