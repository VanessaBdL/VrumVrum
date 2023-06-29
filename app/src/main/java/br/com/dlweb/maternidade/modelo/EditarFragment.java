package br.com.dlweb.maternidade.modelo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
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
import java.util.List;

import br.com.dlweb.maternidade.R;
import br.com.dlweb.maternidade.carro.Carro;
import br.com.dlweb.maternidade.modelo.Modelo;
import br.com.dlweb.maternidade.database.DatabaseHelper;

public class EditarFragment extends Fragment {

    EditText etNome;
    Spinner spMarca;
    ArrayList<Integer> listMarcaId;
    ArrayList<String> listMarcaName;
    DatabaseHelper databaseHelper;
    Modelo c;

    public EditarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modelo_fragment_editar, container, false);
        Bundle bundle = getArguments();
        int id_modelo = bundle.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        c = databaseHelper.getByIdModelo(id_modelo);

        spMarca = v.findViewById(R.id.spinnerMarca);
        etNome = v.findViewById(R.id.editTextNome);

        listMarcaId = new ArrayList<>();
        listMarcaName = new ArrayList<>();
        databaseHelper.getAllNameMarca(listMarcaId, listMarcaName);

        ArrayAdapter<String> spMarcaArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listMarcaName);
        spMarca.setAdapter(spMarcaArrayAdapter);


        etNome.setText(c.getNome());
        spMarca.setSelection(listMarcaId.indexOf(c.getId_marca()));

        Button btnEditar = v.findViewById(R.id.buttonEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_modelo);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluir);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir_modelo);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_modelo);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Não faz nada
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return v;
    }

    private void editar (int id) {
        if (spMarca.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione a marca!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            Modelo c = new Modelo();
            c.setId(id);
            String nomeMarca = spMarca.getSelectedItem().toString();
            c.setId_marca(listMarcaId.get(listMarcaName.indexOf(nomeMarca)));
            c.setNome(etNome.getText().toString());
            databaseHelper.updateModelo(c);
            Toast.makeText(getActivity(), "Modelo editado!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameModelo, new ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        boolean PodeExcluir = true;
        Cursor dataCarro = databaseHelper.getAllCarro();
        while (dataCarro.moveToNext()) {
            int idModeloColumnIndex = dataCarro.getColumnIndex("id_modelo");
            if (id == Integer.parseInt(dataCarro.getString(idModeloColumnIndex))){
                PodeExcluir = false;
                Toast.makeText(getActivity(), "Esse modelo é usado em um carro!", Toast.LENGTH_LONG).show();
            }
        }
        dataCarro.close();
        if (PodeExcluir){
            c = new Modelo();
            c.setId(id);
            databaseHelper.deleteModelo(c);
            Toast.makeText(getActivity(), "Modelo excluído!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameModelo, new ListarFragment()).commit();

        }
    }
}