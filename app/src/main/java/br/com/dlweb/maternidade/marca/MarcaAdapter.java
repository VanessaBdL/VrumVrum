package br.com.dlweb.maternidade.marca;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.dlweb.maternidade.R;
import br.com.dlweb.maternidade.marca.EditarFragment;

public class MarcaAdapter extends RecyclerView.Adapter<MarcaAdapter.MarcaViewHolder>{
    private final List<Marca> marcas;
    private int id_marca;
    private final FragmentActivity activity;

    MarcaAdapter(List<Marca> marcas, FragmentActivity activity){
        this.marcas = marcas;
        this.activity = activity;
    }

    static class MarcaViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeView;

        MarcaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeView = itemView.findViewById(R.id.tvListMarcaNome);
        }
    }

    @NonNull
    @Override
    public MarcaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.marca_item, parent, false);
        return new MarcaViewHolder(v);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MarcaViewHolder viewHolder, int i) {
        viewHolder.nomeView.setText(marcas.get(i).getNome());
        id_marca = marcas.get(i).getId();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("id", id_marca);

                br.com.dlweb.maternidade.marca.EditarFragment editarFragment = new EditarFragment();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                editarFragment.setArguments(b);
                ft.replace(R.id.frameMarca, editarFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return marcas.size();
    }
}
