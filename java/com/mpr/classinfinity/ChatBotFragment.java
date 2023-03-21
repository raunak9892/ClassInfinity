package com.mpr.classinfinity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.common.collect.Lists;
import com.mpr.classinfinity.Adapter.ChatAdapter;
import com.mpr.classinfinity.Model.Messages;
import com.mpr.classinfinity.databinding.FragmentChatBotBinding;
import com.mpr.classinfinity.interfaces.BotReply;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class ChatBotFragment extends Fragment {

    public ChatBotFragment() {
        // Required empty public constructor
    }

    ChatAdapter chatAdapter;
    List<Messages> messageList = new ArrayList<>();

    //dialogFlow
    FragmentChatBotBinding binding;
    private SessionsClient sessionsClient;
    private SessionName sessionName;
    private String uuid = UUID.randomUUID().toString();
    private String TAG = "ChatBotFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentChatBotBinding.inflate(getLayoutInflater());


        chatAdapter = new ChatAdapter(messageList, getContext());
        binding.chatView.setAdapter(chatAdapter);

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.editMessage.getText().toString();
                if (!message.isEmpty()) {
                    messageList.add(new Messages(message, false));
                    binding.editMessage.setText("");
                    sendMessageToBot(message);
                    Objects.requireNonNull(binding.chatView.getAdapter()).notifyDataSetChanged();
                    Objects.requireNonNull(binding.chatView.getLayoutManager())
                            .scrollToPosition(messageList.size() - 1);
                } else {
                    Toast.makeText(getContext(), "Please enter text!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setUpBot();

        return binding.getRoot();
    }

    private void setUpBot() {
        try {
            InputStream stream = this.getResources().openRawResource(R.raw.key);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            String projectId = ((ServiceAccountCredentials) credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            sessionName = SessionName.of(projectId, uuid);

            Log.d(TAG, "projectId : " + projectId);
        } catch (Exception e) {
            Log.d(TAG, "setUpBot: " + e.getMessage());
        }
    }


    private void sendMessageToBot(String message) {
        QueryInput input = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
        new SendMessageInBg(new BotReply() {
            @Override
            public void callback(DetectIntentResponse returnResponse) {
                if (returnResponse != null) {
                    String botReply = returnResponse.getQueryResult().getFulfillmentText();
                    if (!botReply.isEmpty()) {
                        messageList.add(new Messages(botReply, true));
                        chatAdapter.notifyDataSetChanged();
                        Objects.requireNonNull(binding.chatView.getLayoutManager()).scrollToPosition(messageList.size() - 1);
                    } else {
                        Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "failed to connect!", Toast.LENGTH_SHORT).show();
                }
            }
        }, sessionName, sessionsClient, input).execute();
    }

}
