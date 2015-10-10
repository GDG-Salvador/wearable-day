package br.com.wearable.ssa.gdg.notification;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class HandHeldActivity extends Activity {

    private static final int NOTIFICACAO_SIMPLES = 1;
    private static final int NOTIFICACAO_COMPLETA = 2;
    private static final int NOTIFICACAO_GRANDE = 3;
    private static final int NOTIFICACAO_RESPOSTA = 4;

    EditText  mEdtTexto;
    MeuReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_held);
        mEdtTexto = (EditText) findViewById(R.id.editText);

        // Registrando os receivers para saber quando a notificação for excluida e quando
        // ação completa for invocada
        mReceiver = new MeuReceiver();
        registerReceiver(mReceiver,
                new IntentFilter(NotificationUtils.ACAO_DELETE));
        registerReceiver(mReceiver,
                new IntentFilter(NotificationUtils.ACAO_NOTIFICACAO));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desconecta o  receiver criado
        unregisterReceiver(mReceiver);
    }

    /**
     * Cria Notificação simples
     * @param v
     */
    public void criarNotificacaoSimples(View v) {
        NotificationUtils.criarNotificacaoSimples(
                this,
                mEdtTexto.getText().toString(),
                NOTIFICACAO_SIMPLES);
    }

    /**
     * Cria notificação completa
     * @param v
     */
    public void criarNotificacaoCompleta(View v) {
        NotificationUtils.criarNotificacaoCompleta(
                this,
                mEdtTexto.getText().toString(),
                NOTIFICACAO_COMPLETA);
    }

    /**
     * Cria notificação grande
     * @param v
     */
    public void criarNotificacaoGrande(View v) {
        NotificationUtils.criarNotificacaoGrande(
                this,
                NOTIFICACAO_GRANDE);
    }

    /**
     * Cria notificação com resposta
     * @param v
     */
    public void criarNotificacaoComResposta(View v){
        NotificationUtils.criarNotificacaoComResposta(this, NOTIFICACAO_RESPOSTA);
    }

    /**
     * Classe criada para registrar os eventos de ação, delete e notificação.
     */
    class MeuReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(HandHeldActivity.this, intent.getAction(), Toast.LENGTH_SHORT).show();
        }
    }


}
