package br.com.dlweb.maternidade.modelo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import br.com.dlweb.maternidade.R;
import br.com.dlweb.maternidade.database.DatabaseHelper;

public class AdicionarFragment extends Fragment {

    EditText etNome;
    Spinner spMarca;
    ArrayList<Integer> listMarcaId;
    ArrayList<String> listMarcaName;
    DatabaseHelper databaseHelper;

    public AdicionarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.modelo_fragment_adicionar, container, false);

        spMarca = v.findViewById(R.id.spinnerMarca);
        etNome = v.findViewById(R.id.editTextNome);

        databaseHelper = new DatabaseHelper(getActivity());

        listMarcaId = new ArrayList<>();
        listMarcaName = new ArrayList<>();
        databaseHelper.getAllNameMarca(listMarcaId, listMarcaName);

        ArrayAdapter<String> spMarcaArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listMarcaName);
        spMarca.setAdapter(spMarcaArrayAdapter);

        Button btnSalvar = v.findViewById(R.id.buttonAdicionar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        if (spMarca.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione a marca!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else {
            Modelo m = new Modelo();
            String nomeMarca = spMarca.getSelectedItem().toString();
            m.setId_marca(listMarcaId.get(listMarcaName.indexOf(nomeMarca)));
            m.setNome(etNome.getText().toString());
            databaseHelper.createModelo(m);
            Toast.makeText(getActivity(), "Modelo salvo!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameModelo, new ListarFragment()).commit();
        }
    }
}