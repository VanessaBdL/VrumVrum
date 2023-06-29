package br.com.dlweb.maternidade.carro;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.dlweb.maternidade.R;
import br.com.dlweb.maternidade.database.DatabaseHelper;
import br.com.dlweb.maternidade.marca.Marca;
import br.com.dlweb.maternidade.marca.MarcaAdapter;

public class ListarFragment extends Fragment {

    public ListarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.carro_fragment_listar, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        RecyclerView recyclerViewCarros = v.findViewById(R.id.recyclerViewCarro);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        recyclerViewCarros.setLayoutManager(manager);
        recyclerViewCarros.addItemDecoration(new DividerItemDecoration(v.getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewCarros.setHasFixedSize(true);

        Cursor dataCarro = databaseHelper.getAllCarro();
        List<Carro> carros = new ArrayList<Carro>();
        while (dataCarro.moveToNext()) {
            Carro c = new Carro();
            int idColumnIndex = dataCarro.getColumnIndex("_id");
            c.setId(Integer.parseInt(dataCarro.getString(idColumnIndex)));
            int nameColumnIndex = dataCarro.getColumnIndex("nome");
            c.setNome(dataCarro.getString(nameColumnIndex));
            int idModeloColumnIndex = dataCarro.getColumnIndex("id_modelo");
            c.setId_modelo(Integer.parseInt(dataCarro.getString(idModeloColumnIndex)));
            carros.add(c);
        }
        dataCarro.close();
        databaseHelper.closeDBConnection();

        CarroAdapter adapterCarros = new CarroAdapter(carros, getActivity());
        recyclerViewCarros.setAdapter(adapterCarros);
        return v;
    }
}