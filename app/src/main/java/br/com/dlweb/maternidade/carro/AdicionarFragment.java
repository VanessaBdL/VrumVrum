package br.com.dlweb.maternidade.carro;

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
    EditText etRenavam;
    EditText etPlaca;
    EditText etValor;
    EditText etAno;

    Spinner spModelo;
    ArrayList<Integer> listModeloId;
    ArrayList<String> listModeloName;
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

        View v = inflater.inflate(R.layout.carro_fragment_adicionar, container, false);

        spModelo = v.findViewById(R.id.spinnerModelo);
        etNome    = v.findViewById(R.id.editTextNome);
        etRenavam = v.findViewById(R.id.editTextRenavam);
        etPlaca = v.findViewById(R.id.editTextPlaca);
        etValor = v.findViewById(R.id.editTextValor);
        etAno = v.findViewById(R.id.editTextAno);

        databaseHelper = new DatabaseHelper(getActivity());

        listModeloId = new ArrayList<>();
        listModeloName = new ArrayList<>();
        databaseHelper.getAllNameModelo(listModeloId, listModeloName);

        ArrayAdapter<String> spModeloArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listModeloName);
        spModelo.setAdapter(spModeloArrayAdapter);

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
            Carro c = new Carro();
            String nomeModelo = spModelo.getSelectedItem().toString();
            c.setId_modelo(listModeloId.get(listModeloName.indexOf(nomeModelo)));
            c.setNome(etNome.getText().toString());

            long teste = Long.parseLong(etRenavam.getText().toString());
            c.setRenavam(teste);

            c.setPlaca(etPlaca.getText().toString());

            c.setValor(Float.parseFloat(etValor.getText().toString()));

            c.setAno(Integer.parseInt(etAno.getText().toString()));

            databaseHelper.createCarro(c);
            Toast.makeText(getActivity(), "Carro salvo!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameCarro, new ListarFragment()).commit();
        }
    }
}