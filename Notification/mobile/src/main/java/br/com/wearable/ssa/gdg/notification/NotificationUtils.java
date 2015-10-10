package br.com.wearable.ssa.gdg.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

/**
 * Created by ramon on 23/09/15.
 */
public class NotificationUtils {

    // Ação que será disparada quando a notificação for excluída pelo usuário
    public static final String ACAO_DELETE = "dominando.android.ex25_notification.DELETE_NOTIFICACAO";
    // Ação customizada que será usada na notificação completa
    public static final String ACAO_NOTIFICACAO = "dominando.android.ex25_notification.ACAO_NOTIFICACAO";

    /**
     * Define a chamda de uma nova activity, após o clique em uma determinada notificação.
     * 
     * @param ctx
     * @param texto
     * @param id
     * @return
     */
    public static PendingIntent criarPendingIntent(Context ctx, String texto, int id) {

        // Objeto iniciará a classe DetalheActivity
        Intent resultIntent = new Intent(ctx, DetalheActivity.class);
        resultIntent.putExtra(DetalheActivity.EXTRA_TEXTO, texto);

        // Controle de navegação das telas, impedindo que as mesmas sejão criadas
        // repetidamente ou fora de ordem
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(DetalheActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        // Se houver essa mesma activity com o mesmo ID deve ser atualizada
        return stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Cria uma notificação simples.
     * 
     * @param ctx
     * @param texto
     * @param id
     */
    public static void criarNotificacaoSimples(Context ctx, String texto, int id){
        // Define a intent para abrir a activity de detalhe  
        PendingIntent resultPendingIntent = criarPendingIntent(ctx, texto, id);
        // Usado para criar notificações em todas as versões
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                    .setDefaults(Notification.DEFAULT_ALL)
                    // Define o ícone
                    .setSmallIcon(R.drawable.ic_notificacao)
                    // Define o texto principal da notificação (Obrigatório)
                    .setContentTitle("Simples " + id)
                    // Define o texto que aparecerá abaixo do título (obrigatório)
                    .setContentText(texto)
                    // Define intent para abrir a Detalhe Activity
                    .setContentIntent(resultPendingIntent);

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
        // Enviar a notificação
        nm.notify(id, mBuilder.build());
    }

    /**
     * Cria uma notificação completa.
     * 
     * @param ctx
     * @param texto
     * @param id
     */
    public static void criarNotificacaoCompleta (Context ctx, String texto, int id){
        
        // Define um som customizado para quando a activity for disparada  
        Uri uriSom = Uri.parse("android.resource://" + ctx.getPackageName() + "/raw/som_notificacao");
        
        // Usado para informar quando a ação da  notificação foi clicada\ 
        PendingIntent pitAcao = PendingIntent.getBroadcast(ctx, 0, new Intent(ACAO_NOTIFICACAO), 0);
        // Usado para informar quando a notificação for excluída
        PendingIntent pitDelete = PendingIntent.getBroadcast(ctx, 0, new Intent(ACAO_DELETE), 0);
        // Imagem a ser exibida em destaque do lado esquerdo da notificação
        Bitmap largeIcon = BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher);

        PendingIntent piNotificacao = criarPendingIntent(ctx, texto, id);

        NotificationCompat.Builder  mBuilder =
                 new NotificationCompat.Builder(ctx)
                         // Define o ícone
                        .setSmallIcon(R.drawable.ic_notificacao)
                         // Define o texto principal da notificação (Obrigatório)
                         .setContentTitle("Completa")
                         // Texto que aparecerá abaixo do título (obrigatório)
                        .setContentText(texto)
                         // Texto exibido assim que chegou a notificação
                        .setTicker("Chegou uma notificação")
                         // Hora que será exibida a noitificação
                        .setWhen(System.currentTimeMillis())
                         // Imagem exibida em destaque do lado esquerdo
                        .setLargeIcon(largeIcon)
                         // Define se a notificação será removida  automaticamente ao ser clicada
                        .setAutoCancel(true)
                        // Define intent para abrir a Detalhe Activity
                         .setContentIntent(piNotificacao)
                        // Contém intent a ser disparada quando remover a notifição
                         .setDeleteIntent(pitDelete)
                         // Define a cor  e  o tempo que o led das notificações ficará aceso ou
                         // apagado
                        .setLights(Color.BLUE, 1000, 5000)
                         // Atribui um som personalizado para notificação
                        .setSound(uriSom)
                         // Define a sequência dos milissegundos para iniciar a notificação
                        .setVibrate(new long[]{100, 500, 200, 800})
                         // Permite três butões que ficarão abaixo da notificação
                        .addAction(R.drawable.ic_acao_notificacao, "Ação Customizada", pitAcao)
                         //  Id da notificação
                         .setNumber(id)
                         // Texto opçional para terceira linha da notificação
                        .setSubText("Subtexto");

                        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
                         // Enviar a notificação
                        nm.notify(id, mBuilder.build());

    }

    /**
     * Cria uma notificação maior contendo as outras não visualizadas.
     *
     * @param ctx
     * @param idNotificacao
     */
    public static void criarNotificacaoGrande(Context ctx, int idNotificacao){
               int numero = 5;

        // InboxStyle que permitirá o armazenamento das grandes mensagens
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        // Título
        inboxStyle.setBigContentTitle("Mensagem não lidas:");

        for(int i = 1; i<= numero; i++){
             // Adciciona as mensagens
            inboxStyle.addLine("Mensagem " + i);
        }

        inboxStyle.setSummaryText("Clique para exibir");

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx)
                    // Define o ícone
                    .setSmallIcon(R.drawable.ic_notificacao)
                    // Define o texto principal da notificação (Obrigatório)
                   .setContentTitle("Notificação")
                    // Texto que aparecerá abaixo do título (obrigatório)
                    .setContentText("Vários itens pendentes")
                    // Define intent para abrir a Detalhe Activity
                    .setContentIntent(criarPendingIntent(ctx, "Mensagens não lidas", -1))
                    // Total  de mensagens
                    .setNumber(numero)
                    // Define o estilo da notificação
                      .setStyle(inboxStyle);
        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
        // Enviar a notificação
        nm.notify(idNotificacao, mBuilder.build());


    }

    /**
     * Criar notificação com Resposta por comando de voz (Recurso exclusivo do Android Wear)
     * @param ctx
     * @param idNotificao
     */
    public static void criarNotificacaoComResposta(Context ctx, int idNotificao){

        // Permite adicionar o comando de voz a notificação
        RemoteInput remoteInput = new RemoteInput.Builder(DetalheActivity.EXTRA_RESPOSTA_VOZ)
                .setLabel("Diga a Resposta")
                .build();
        // Define a intent para abrir a activity de detalhe
        PendingIntent pit = criarPendingIntent(ctx, "Notificação com Resposta", idNotificao);
        // Define a ação que enviara a texto para a tela detalhe
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                R.drawable.ic_notificacao, "Responder", pit)
                // Adicionando o remote input a Ação
                .addRemoteInput(remoteInput)
                .build();
        // Cria um objeto wearable extender
        NotificationCompat.WearableExtender werearableExtender =
                new NotificationCompat.WearableExtender();

        Notification notificacao  = new NotificationCompat.Builder(ctx)
                // Define o ícone
                .setSmallIcon(R.drawable.ic_notificacao)
                // Define o texto principal da notificação (Obrigatório)
                .setContentTitle("Com resposta")
                // Texto que aparecerá abaixo do título (obrigatório)
                .setContentText("Passe a página para responder")
                // Define se a notificação será removida  automaticamente ao ser clicada
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                // Adcionando recurso de Wear a noticação através da ação definida com remoteInput
                .extend(werearableExtender.addAction(action))
                .build();

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
        // Enviar a notificação
        nm.notify(idNotificao, notificacao);

    }

}
