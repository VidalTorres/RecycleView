package com.example.recycleview;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import modelo.AlumnoDbHelper;
import modelo.AlumnosDb;

public class Aplicacion extends Application {
    private static ArrayList<Alumno> alumnos;
    private MiAdaptador adaptador;

    private AlumnoDbHelper dbHelper = new AlumnoDbHelper(this);
    private AlumnosDb db = new AlumnosDb(this, dbHelper);

    @Override
    public void onCreate(){
        super.onCreate();
        db.openDataBase();
        alumnos = db.allAlumnos();
        adaptador = new MiAdaptador(alumnos, this);
        Log.d("", "onCreate: tamaño array list " + alumnos.size());
        db.closeDataBase();

        /* Llenar con los registros pre hechos
        for(Alumno i: Alumno.llenarAlumnos()){
            db.insertAlumno(i);
        }
         */
    }

    public void addAlumno(Alumno e){
        alumnos.add(e);
        db.openDataBase();
        db.insertAlumno(e);
        db.closeDataBase();
    }

    public static ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public MiAdaptador getAdaptador() {
        return adaptador;
    }

    public void setAdaptador(MiAdaptador adaptador) {
        this.adaptador = adaptador;
    }
}
