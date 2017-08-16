package com.codedao.doctorfun;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codedao.doctorfun.adapter.CustomRecyclerPageChat;
import com.codedao.doctorfun.model.MessageModel;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Map;

import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity implements AIListener {
    private EditText mEditTextQuestion;
    private Button mButtonSendQuestion;
    private AIService aiService;
    private AIDataService aiDataService;
    private ArrayList<MessageModel> mMessageModels;
    private RecyclerView mRecyclerView;
    private CustomRecyclerPageChat mCustomRecyclerPageChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        final AIConfiguration config = new AIConfiguration("d44fae1ddff846adbfeb5fb0cf5a305d",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        actionClick();


    }

    public void updateListMeassage(ArrayList<MessageModel> list) {
        mMessageModels.addAll(list);
        mCustomRecyclerPageChat.updateRecyclerView(mMessageModels);
    }

    private void actionClick() {
        mButtonSendQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMessageModels.add(new MessageModel(mEditTextQuestion.getText().toString(), 2));
                mCustomRecyclerPageChat.updateRecyclerView(mMessageModels);
                aiService.startListening();
                final AIRequest aiRequest = new AIRequest();
                aiRequest.setQuery(mEditTextQuestion.getText().toString());
                mEditTextQuestion.setText("");
                new LoadAnswer(MainActivity.this).execute(aiRequest);

            }
        });
    }

    private void init() {
        mMessageModels = new ArrayList<>();
        mEditTextQuestion = (EditText) findViewById(R.id.edQuestion);
        mRecyclerView = (RecyclerView) findViewById(R.id.RcListMessage);
        mButtonSendQuestion = (Button) findViewById(R.id.btnSendQuestion);

        mMessageModels.add(new MessageModel("Xin Chao Bạn! Tôi Có Thể Giúp Gì Cho Bạn!", 1));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mCustomRecyclerPageChat = new CustomRecyclerPageChat(this, mMessageModels);
        mRecyclerView.setAdapter(mCustomRecyclerPageChat);
    }

    @Override
    public void onResult(ai.api.model.AIResponse response) {

    }

    @Override
    public void onError(ai.api.model.AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    public class LoadAnswer extends AsyncTask<AIRequest, Void, AIResponse> {
        private AIDataService aiDataService;

        public LoadAnswer(Context context) {
            final AIConfiguration config1 = new AIConfiguration("f5f01cdb0008460b85a8179105012918",
                    AIConfiguration.SupportedLanguages.English,
                    AIConfiguration.RecognitionEngine.System);
            aiDataService = new AIDataService(context, config1);
        }

        @Override
        protected AIResponse doInBackground(AIRequest... requests) {
            final AIRequest request = requests[0];
            try {
                final AIResponse response = aiDataService.request(request);
                return response;
            } catch (AIServiceException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(AIResponse aiResponse) {
            if (aiResponse != null) {
                Result result = aiResponse.getResult();
                Fulfillment fulfillment = aiResponse.getResult().getFulfillment();
                // Get parameters
                String parameterString = "";
                if (result.getParameters() != null && !result.getParameters().isEmpty()) {
                    for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                        parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
                    }
                }
                ArrayList<MessageModel> messageModels = new ArrayList<>();
                messageModels.add(new MessageModel(fulfillment.getSpeech(), 1));
                Log.e("MainActivity", fulfillment.getSpeech());
                updateListMeassage(messageModels);
            }

        }
    }
}
