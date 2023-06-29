package br.com.dlweb.maternidade.marca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import br.com.dlweb.maternidade.R;
import br.com.dlweb.maternidade.database.DatabaseHelper;

public class EditarFragment extends Fragment {

    private DatabaseHelper databaseHelper;
    private Marca m;
    private EditText etNome;

    public EditarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.marca_fragment_editar, container, false);
        Bundle b = getArguments();
        int id_marca = b != null ? b.getInt("id") : null;

        etNome = v.findViewById(R.id.editTextNome);

        databaseHelper = new DatabaseHelper(getActivity());
        m = databaseHelper.getByIdMarca(id_marca);
        etNome.setText(m.getNome());

        Button btnEditar = v.findViewById(R.id.buttonEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_marca);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.dialog_excluir_marca);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir(id_marca);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Não faz nada
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return v;
    }

    private void editar (int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        }else {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            Marca m = new Marca();
            m.setId(id);
            m.setNome(etNome.getText().toString());
            databaseHelper.updateMarca(m);
            Toast.makeText(getActivity(), "Marca atualizada!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMarca, new ListarFragment()).commit();
        }
    }

    private void excluir(int id) {
        boolean PodeExcluir = true;
        Cursor dataModelo = databaseHelper.getAllModelo();
        while (dataModelo.moveToNext()) {
            int idMarcaColumnIndex = dataModelo.getColumnIndex("id_marca");
            if (id == Integer.parseInt(dataModelo.getString(idMarcaColumnIndex))){
                PodeExcluir = false;
                Toast.makeText(getActivity(), "Essa marca é usada em um modelo!", Toast.LENGTH_LONG).show();
            }
        }
        dataModelo.close();
        if (PodeExcluir){
            m = new Marca();
            m.setId(id);
            databaseHelper.deleteMarca(m);
            Toast.makeText(getActivity(), "Marca excluída!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMarca, new ListarFragment()).commit();

        }
    }
}