package be.kmz.demothreading.threading;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

public class ProgressTask extends AsyncTask<Void, Integer, Void> {

    //object zelf niet gebruiken, maar een referentie ervan gebruiken, zorgt ervoor dat er geen leaks gebeuren.
    private WeakReference<ProgressBar> mProgressBarWeakReference;


    public ProgressTask(ProgressBar pb) {
        mProgressBarWeakReference = new WeakReference<>(pb);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //1. voorbereidend werk
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //2. effectief iets uit storen
        //maakt achterliggend een thread
        for (int i = 1; i <= 100; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);//stuurt data
        }
        //returned naar onPostExecute
        return null;
    }

    @Override
    //3 puntjes betekent dat ge dynamisch parameters kunt geven aan uw functies
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //3. toont vooruitgang
        mProgressBarWeakReference.get().setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //4. klaar met uitvoeren, eventueel resultaat returnen
        mProgressBarWeakReference = null;
    }
}
