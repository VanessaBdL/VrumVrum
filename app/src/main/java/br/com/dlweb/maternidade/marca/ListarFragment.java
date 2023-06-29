package br.com.dlweb.maternidade.marca;

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
        View v = inflater.inflate(R.layout.marca_fragment_listar, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        RecyclerView recyclerViewMarcas = v.findViewById(R.id.recyclerViewMarcas);

        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        recyclerViewMarcas.setLayoutManager(manager);
        recyclerViewMarcas.addItemDecoration(new DividerItemDecoration(v.getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewMarcas.setHasFixedSize(true);

        Cursor dataMarca = databaseHelper.getAllMarca();
        List<Marca> marcas = new ArrayList<Marca>();
        while (dataMarca.moveToNext()) {
            Marca m = new Marca();
            int idColumnIndex = dataMarca.getColumnIndex("_id");
            m.setId(Integer.parseInt(dataMarca.getString(idColumnIndex)));
            int nameColumnIndex = dataMarca.getColumnIndex("nome");
            m.setNome(dataMarca.getString(nameColumnIndex));
            marcas.add(m);
        }
        dataMarca.close();
        databaseHelper.closeDBConnection();

        MarcaAdapter adapterMarcas = new MarcaAdapter(marcas, getActivity());
        recyclerViewMarcas.setAdapter(adapterMarcas);
        return v;
    }
}