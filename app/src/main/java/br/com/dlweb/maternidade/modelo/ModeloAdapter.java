package br.com.dlweb.maternidade.modelo;

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
import br.com.dlweb.maternidade.database.DatabaseHelper;
import br.com.dlweb.maternidade.marca.Marca;

public class ModeloAdapter extends RecyclerView.Adapter<ModeloAdapter.ModeloViewHolder>{
    private final List<Modelo> modelos;
    private int id_modelo;
    private final FragmentActivity activity;

    ModeloAdapter(List<Modelo> modelos, FragmentActivity activity){
        this.modelos = modelos;
        this.activity = activity;
    }

    static class ModeloViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeView;
        private final TextView nomeMarcaView;

        ModeloViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeView = itemView.findViewById(R.id.tvListModeloNome);
            nomeMarcaView = itemView.findViewById(R.id.tvListModeloMarcaNome);
        }
    }

    @NonNull
    @Override
    public ModeloViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_item, parent, false);
        return new ModeloViewHolder(v);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ModeloViewHolder viewHolder, int i) {
        viewHolder.nomeView.setText(modelos.get(i).getNome());
        DatabaseHelper databaseHelper = new DatabaseHelper(activity);
        Marca m = databaseHelper.getByIdMarca(modelos.get(i).getId_marca());
        viewHolder.nomeMarcaView.setText(m.getNome());
        id_modelo = modelos.get(i).getId();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("id", id_modelo);

                br.com.dlweb.maternidade.modelo.EditarFragment editarFragment = new br.com.dlweb.maternidade.modelo.EditarFragment();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                editarFragment.setArguments(b);
                ft.replace(R.id.frameModelo, editarFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelos.size();
    }
}
