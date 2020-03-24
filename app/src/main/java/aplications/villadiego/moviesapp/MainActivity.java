package aplications.villadiego.moviesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner Generos;
    Button guardar, cancelar;
    EditText nombre, director;
    RadioButton español, ingles, portgues;
    ArrayList<Movies> movies = new ArrayList<>();
    String language, genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Generos = findViewById(R.id.spinnerGeneros);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Spinner_Genero, android.R.layout.simple_spinner_item);
        Generos.setAdapter(adapter);

        guardar = findViewById(R.id.btnguardar);
        cancelar = findViewById(R.id.btncancelar);
        nombre = findViewById(R.id.txtnombre);
        director = findViewById(R.id.txtdirector);
        español = findViewById(R.id.rbtnesp);
        portgues = findViewById(R.id.rbtnpt);
        ingles = findViewById(R.id.rbtning);

        español.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (español.isChecked()){
                    language = español.getText().toString();
                }
            }
        });

        ingles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ingles.isChecked()){
                    language = ingles.getText().toString();
                }
            }
        });

        portgues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (portgues.isChecked()){
                    language = portgues.getText().toString();
                }
            }
        });

        guardar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.btncancelar:
                        nombre.setText("");
                        director.setText("");
                        español = null;
                        ingles = null;
                        portgues = null;
                        Generos = null;
                        break;
                }
            }
        });

    }

    private void addMovie(){
        if (TextUtils.isEmpty(nombre.getText()) && TextUtils.isEmpty(director.getText())){
            nombre.setText("");
            director.setText("");
            Toast.makeText(this,"La pelicula no pudo ser agregada, datos incompletos",Toast.LENGTH_LONG).show();
        }else{
            movies.add(new Movies(nombre.getText().toString(), director.getText().toString(),
                    language, Generos.getSelectedItem().toString()));
            nombre.setText("");
            director.setText("");
            Toast.makeText(this,"Pelicula Agregada correctamente",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mnu = item.getItemId();

        switch (mnu){
            case R.id.mnumayuscula:
                nombre.setText(nombre.getText().toString().toUpperCase());
                director.setText(director.getText().toString().toUpperCase());
                break;
            case R.id.mnulistado:
                if (movies.size()>0){
                Intent intent = new Intent(this, MoviesList.class);
                intent.putParcelableArrayListExtra("Movies", movies);
                startActivityForResult(intent, 1);
                }else{
                    Toast.makeText(this, "No hay peliculas para mostrar", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.mnuminuscula:
                nombre.setText(nombre.getText().toString().toLowerCase());
                director.setText(director.getText().toString().toLowerCase());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            movies = data.getParcelableArrayListExtra("Movies");
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnguardar:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Desea agregar esta pelicula?").setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addMovie();
                        nombre.setHint("Nombre de la pelicula");
                        director.setHint("Nombre del director");
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nombre.setText("");
                        director.setText("");
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
                break;
        }
    }
}
