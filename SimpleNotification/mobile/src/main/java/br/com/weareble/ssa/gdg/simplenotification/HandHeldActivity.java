package br.com.weareble.ssa.gdg.simplenotification;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class HandHeldActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_held);
    }

    /**
     * Cria uma notificação simples quando usuário clica no botão
     * @param v
     */
    public void criarNotificacaoSimples (View v){
        // Instância usada para criar notificação para todas veraões do android
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplication())
                .setDefaults(Notification.DEFAULT_ALL)
                // Icone da notificação
                .setSmallIcon(R.drawable.ic_notification)
                // Titulo da notificação
                .setContentTitle("Simples Notificação")
                // Texto da notificação
                .setContentText("Olá mundo!");

        // Instância usada para disparar a notificação
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());

        // Id da notificação
        int notoficationld = 1;

        // Dispara a notificação
        notificationManager.notify(notoficationld, mBuilder.build());
    }
}
