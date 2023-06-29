package br.com.dlweb.maternidade.carro;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import br.com.dlweb.maternidade.modelo.Modelo;
import br.com.dlweb.maternidade.database.DatabaseHelper;

public class EditarFragment extends Fragment {

    EditText etNome;
    EditText etRenavam;
    EditText etPlaca;
    EditText etValor;
    EditText etAno;

    Spinner spModelo;
    ArrayList<Integer> listModeloId;
    ArrayList<String> listModeloName;
    DatabaseHelper databaseHelper;
    Carro c;

    public EditarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.carro_fragment_editar, container, false);
        Bundle bundle = getArguments();
        int id_carro = bundle.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        c = databaseHelper.getByIdCarro(id_carro);

        spModelo = v.findViewById(R.id.spinnerModelo);
        etNome = v.findViewById(R.id.editTextNome);
        etRenavam = v.findViewById(R.id.editTextRenavam);
        etPlaca = v.findViewById(R.id.editTextPlaca);
        etValor = v.findViewById(R.id.editTextValor);
        etAno = v.findViewById(R.id.editTextAno);

        listModeloId = new ArrayList<>();
        listModeloName = new ArrayList<>();
        databaseHelper.getAllNameModelo(listModeloId, listModeloName);

        ArrayAdapter<String> spModeloArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listModeloName);
        spModelo.setAdapter(spModeloArrayAdapter);


        etNome.setText(c.getNome());
        spModelo.setSelection(listModeloId.indexOf(c.getId_modelo()));
        etRenavam.setText(String.valueOf(c.getRenavam()));
        etPlaca.setText(c.getPlaca());
        etValor.setText(String.valueOf(c.getValor()));
        etAno.setText(String.valueOf(c.getAno()));

        Button btnEditar = v.findViewById(R.id.buttonEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_carro);
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
                        excluir(id_carro);
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
        if (spModelo.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione o modelo!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        }else if (etRenavam.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o renavam!", Toast.LENGTH_LONG).show();
        }else if (etPlaca.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a placa!", Toast.LENGTH_LONG).show();
        }else if (etValor.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o valor!", Toast.LENGTH_LONG).show();
        }else if (etAno.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o ano!", Toast.LENGTH_LONG).show();
        } else {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            Carro c = new Carro();
            c.setId(id);
            String nomeModelo = spModelo.getSelectedItem().toString();
            c.setId_modelo(listModeloId.get(listModeloName.indexOf(nomeModelo)));
            c.setNome(etNome.getText().toString());
            c.setRenavam(Long.parseLong(etRenavam.getText().toString()));
            c.setPlaca(etPlaca.getText().toString());
            c.setValor(Float.parseFloat(etValor.getText().toString()));
            c.setAno(Integer.parseInt(etAno.getText().toString()));

            databaseHelper.updateCarro(c);
            Toast.makeText(getActivity(), "Carro editado!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameCarro, new ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        c = new Carro();
        c.setId(id);
        databaseHelper.deleteCarro(c);
        Toast.makeText(getActivity(), "Carro excluído!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameCarro, new ListarFragment()).commit();
    }
}