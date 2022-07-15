package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import modelo.AlumnoDbHelper;
import modelo.AlumnosDb;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fbtnAgregar;
    private Button btnCerrar;
    private Alumno alumno;
    private int posicion = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCerrar = (Button) findViewById(R.id.btnCerrar);
        fbtnAgregar = findViewById(R.id.agregarAlumno);

        Aplicacion app = (Aplicacion) getApplication();
        recyclerView = findViewById(R.id.recId);
        recyclerView.setAdapter(app.getAdaptador());

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        app.getAdaptador().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion = recyclerView.getChildAdapterPosition(v);
                alumno = app.getAlumnos().get(posicion);

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setClass(MainActivity.this, AlumnoAlta.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("alumno", alumno);
                intent.putExtra("posicion", posicion);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });

        fbtnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alumno = null;
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                Bundle bundle = new Bundle();
                intent.setClass(MainActivity.this, AlumnoAlta.class);
                bundle.putSerializable("alumno", alumno);
                bundle.putInt("posicion", posicion);
                intent.putExtras(bundle);

                startActivityForResult(intent, 0);
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirmar = new AlertDialog.Builder(MainActivity.this);
                confirmar.setTitle("¿Cerrar Aplicación?");
                confirmar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                confirmar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                confirmar.show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        recyclerView.getAdapter().notifyDataSetChanged();
        posicion = -1;
    }
}