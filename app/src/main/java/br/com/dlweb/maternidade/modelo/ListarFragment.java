package br.com.dlweb.maternidade.modelo;

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
        View v = inflater.inflate(R.layout.modelo_fragment_listar, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        RecyclerView recyclerViewModelos = v.findViewById(R.id.recyclerViewModelo);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        recyclerViewModelos.setLayoutManager(manager);
        recyclerViewModelos.addItemDecoration(new DividerItemDecoration(v.getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewModelos.setHasFixedSize(true);

        Cursor dataModelo = databaseHelper.getAllModelo();
        List<Modelo> modelos = new ArrayList<Modelo>();
        while (dataModelo.moveToNext()) {
            Modelo b = new Modelo();
            int idColumnIndex = dataModelo.getColumnIndex("_id");
            b.setId(Integer.parseInt(dataModelo.getString(idColumnIndex)));
            int nameColumnIndex = dataModelo.getColumnIndex("nome");
            b.setNome(dataModelo.getString(nameColumnIndex));
            int idMarcaColumnIndex = dataModelo.getColumnIndex("id_marca");
            b.setId_marca(Integer.parseInt(dataModelo.getString(idMarcaColumnIndex)));
            modelos.add(b);
        }
        dataModelo.close();
        //No método getAllBebe() apenas abre a conexão
        databaseHelper.closeDBConnection();

        ModeloAdapter adapterModelos = new ModeloAdapter(modelos, getActivity());
        recyclerViewModelos.setAdapter(adapterModelos);
        return v;
    }
}