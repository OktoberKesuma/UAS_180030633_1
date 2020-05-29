package com.aa183.oktober;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private Context context;
    private ArrayList<film> dataFilm;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");

    public FilmAdapter(Context context, ArrayList<film> dataFilm) {
        this.context = context;
        this.dataFilm = dataFilm;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_film, parent,false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        film tempFilm = dataFilm.get(position);
        holder.idFilm = tempFilm.getIdfilm();
        holder.tvJudul.setText(tempFilm.getJudul());
        holder.tvHeadline.setText(tempFilm.getSutradara());
        holder.tanggal = sdFormat.format(tempFilm.getTanggal());
        holder.gambar = tempFilm.getGambar();
        holder.tvsinopsis.setText(tempFilm.getSinopsis());

        try {
            File file = new File(holder.gambar);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgFilm.setImageBitmap(bitmap);
            holder.imgFilm.setContentDescription(holder.gambar);
        }catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(context, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return dataFilm.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private ImageView imgFilm;
        private TextView tvJudul,tvHeadline, tvsinopsis;
        private int idFilm;
        private String tanggal, gambar;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFilm = itemView.findViewById(R.id.iv_film);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvHeadline = itemView.findViewById(R.id.tv_headline);
            tvsinopsis = itemView.findViewById(R.id.tv_sinopsis);


            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent bukaFilm = new Intent(context, TampilActivity.class);
            bukaFilm.putExtra("ID", idFilm);
            bukaFilm.putExtra("JUDUL", tvJudul.getText().toString());
            bukaFilm.putExtra("TANGGAL", tanggal);
            bukaFilm.putExtra("GAMBAR", gambar);
            bukaFilm.putExtra("SUTRADARA", tvHeadline.getText().toString());
            bukaFilm.putExtra("SINOPSIS", tvsinopsis.getText().toString());
            context.startActivity(bukaFilm);
        }

        @Override
        public boolean onLongClick(View v) {

            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("OPERASI","update");
            bukaInput.putExtra("ID", idFilm);
            bukaInput.putExtra("JUDUL", tvJudul.getText().toString());
            bukaInput.putExtra("TANGGAL", tanggal);
            bukaInput.putExtra("GAMBAR", gambar);
            bukaInput.putExtra("SUTRADARA", tvHeadline.getText().toString());
            bukaInput.putExtra("SINOPSIS", tvsinopsis.getText().toString());
            context.startActivity(bukaInput);

            return true;
        }
    }
}
