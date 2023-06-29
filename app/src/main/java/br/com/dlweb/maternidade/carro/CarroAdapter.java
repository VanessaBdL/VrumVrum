package br.com.dlweb.maternidade.carro;

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
import br.com.dlweb.maternidade.carro.EditarFragment;
import br.com.dlweb.maternidade.carro.Carro;
import br.com.dlweb.maternidade.database.DatabaseHelper;
import br.com.dlweb.maternidade.marca.Marca;
import br.com.dlweb.maternidade.modelo.Modelo;
import br.com.dlweb.maternidade.carro.Carro;

public class CarroAdapter extends RecyclerView.Adapter<br.com.dlweb.maternidade.carro.CarroAdapter.CarroViewHolder>{
    private final List<Carro> carros;
    private int id_carro;
    private final FragmentActivity activity;

    CarroAdapter(List<Carro> carros, FragmentActivity activity){
        this.carros = carros;
        this.activity = activity;
    }

    static class CarroViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeView;
        private final TextView nomeModeloView;
        private final TextView nomeModeloMarcaView;

        CarroViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeView = itemView.findViewById(R.id.tvListCarroNome);
            nomeModeloView = itemView.findViewById(R.id.tvListCarroModeloNome);
            nomeModeloMarcaView = itemView.findViewById(R.id.tvListCarroModeloMarcaNome);
        }
    }

    @NonNull
    @Override
    public br.com.dlweb.maternidade.carro.CarroAdapter.CarroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carro_item, parent, false);
        return new br.com.dlweb.maternidade.carro.CarroAdapter.CarroViewHolder(v);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(br.com.dlweb.maternidade.carro.CarroAdapter.CarroViewHolder viewHolder, int i) {
        viewHolder.nomeView.setText(carros.get(i).getNome());
        DatabaseHelper databaseHelper = new DatabaseHelper(activity);
        Modelo m = databaseHelper.getByIdModelo(carros.get(i).getId_modelo());
        viewHolder.nomeModeloView.setText(m.getNome());

        Marca marca = databaseHelper.getByIdMarca(m.getId_marca());
        viewHolder.nomeModeloMarcaView.setText(marca.getNome());

        id_carro = carros.get(i).getId();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("id", id_carro);

                br.com.dlweb.maternidade.carro.EditarFragment editarFragment = new br.com.dlweb.maternidade.carro.EditarFragment();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                editarFragment.setArguments(b);
                ft.replace(R.id.frameCarro, editarFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return carros.size();
    }
}
