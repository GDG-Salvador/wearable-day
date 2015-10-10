package br.com.wearable.ssa.gdg.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class DetalheActivity extends ActionBarActivity {

    public static final String EXTRA_TEXTO = "texto";
    public static final String EXTRA_RESPOSTA_VOZ  = "resposta_voz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        // Obtém a string informada por outra activity
        String string = getIntent().getStringExtra(EXTRA_TEXTO);

        String respostaVoz = obterTextoFalado(getIntent());
        if(respostaVoz != null){
            string += ":" + respostaVoz;
        }

        // Obtém a referência do elemento no html
        TextView txt = (TextView)findViewById(R.id.txtDetalhe);
        // Seta o texto informado como parâmetro
        txt.setText(string);
    }

    /**
     * Obtém o texto falado contido no RemoteInput
     * @param intent
     * @return
     */
    private String obterTextoFalado(Intent intent){
        // Obtém o texto falado
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        if(remoteInput != null){
            // Retorna o texto falado através da chave EXTRA_RESPOSTA_VOZ
            return remoteInput.getCharSequence(EXTRA_RESPOSTA_VOZ).toString();
        }else{
            return null;
        }
    }

}
