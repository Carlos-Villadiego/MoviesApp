package aplications.villadiego.moviesapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MoviesList extends AppCompatActivity {

    ListView listView;
    ArrayList<String> movie;
    ArrayAdapter<String> adapter;
    ArrayList<Movies> movies;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        movie = new ArrayList<>();
        Intent intent = getIntent();
        movies = intent.getParcelableArrayListExtra("Movies");

        for (int i = 1; i<=10; i++) {
            movie.add("Pelicula" + i );

        listView = findViewById(R.id.lstvwmovies);
        ArrayAdapter<Movies> adapter = new ArrayAdapter<Movies>(this, R.layout.activity_listadetalle,
                R.id.txtlist, movies);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        registerForContextMenu(listView);


        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        int id = v.getId();
        MenuInflater mnu = getMenuInflater();
        mnu.inflate(R.menu.menu_contextual, menu);
        switch (id){
            case R.id.txtlist:
                mnu.inflate(R.menu.menu_contextual, menu);
                break;
        }
    }



    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menu = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView texto;
        final ArrayAdapter<Movies> adapter = new ArrayAdapter<Movies>(this, R.layout.activity_listadetalle,
                R.id.txtlist, movies);
            switch (item.getItemId()) {
                case R.id.mnucolor1:
                    texto = findViewById(R.id.txtlist);
                    texto.setTextColor(Color.BLUE);
                    break;
                case R.id.mnucolor2:
                    texto = findViewById(R.id.txtlist);
                    texto.setTextColor(Color.GREEN);
                    break;
                case R.id.mnucolor3:
                    texto = findViewById(R.id.txtlist);
                    texto.setTextColor(Color.RED);
                    break;
                case R.id.mnucolor4:
                    texto = findViewById(R.id.txtlist);
                    texto.setTextColor(Color.YELLOW);
                    break;
            }
            return super.onContextItemSelected(item);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int mnu = item.getItemId();
        final ArrayAdapter<Movies> adapter = new ArrayAdapter<Movies>(this, R.layout.activity_listadetalle,
                R.id.txtlist, movies);

        switch (mnu){
            case R.id.mnueliminar:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MoviesList.this);
                dialog.setMessage("¿Esta seguro de eliminar la pelicula?")
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        movies.remove(0);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = dialog.create();
                alert.show();
                break;
            case R.id.mnuinvertir:
                Collections.reverse(movies);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case R.id.mnurgresar:
                Intent in = new Intent(this, MainActivity.class);
                in.putParcelableArrayListExtra("Movies",movies);
                startActivity(in);
                finish();
                break;
            case R.id.mnuordenar:
                Collections.sort(movies, new Comparator<Movies>() {
                    @Override
                    public int compare(Movies o1, Movies o2) {
                        if (o1.getNombre().compareTo(o2.getNombre()) < 0) {
                            return -1;
                        }
                        if (o1.getNombre().compareTo(o2.getNombre()) > 0) {
                            return 1;
                        }
                        return 0;
                    }
                });
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
