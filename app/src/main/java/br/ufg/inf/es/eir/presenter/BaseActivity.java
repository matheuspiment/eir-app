package br.ufg.inf.es.eir.presenter;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import br.ufg.inf.es.eir.R;

/**
 * Created by marceloquinta on 10/02/17.
 */

public class BaseActivity extends AppCompatActivity {

    MaterialDialog dialog;

    public void showDialogWithMessage(String message){
        dialog = new MaterialDialog.Builder(this)
                .content(message)
                .progress(true,0)
                .show();
    }

    public void dismissDialog(){
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showAlert(String message){
        View target = findViewById(R.id.content);
        Snackbar.make(target,message, Snackbar.LENGTH_LONG).show();
    }

}
