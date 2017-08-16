package com.codedao.doctorfun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codedao.doctorfun.R;
import com.codedao.doctorfun.model.MessageModel;

import java.util.ArrayList;

/**
 * Created by Bruce Wayne on 02/04/2017.
 */

public class CustomRecyclerPageChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<MessageModel> mMessageModels;
    private LayoutInflater mLayoutInflater;

    public CustomRecyclerPageChat(Context mContext, ArrayList<MessageModel> mMessageModels) {
        this.mContext = mContext;
        this.mMessageModels = mMessageModels;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void updateRecyclerView(ArrayList<MessageModel> messageModels) {
        mMessageModels = messageModels;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = mLayoutInflater.inflate(R.layout.itemmessagechatadmin, parent, false);
                return new RecyclerViewHolderAdmin(view, mContext);
            case 2:
                view = mLayoutInflater.inflate(R.layout.itemmessagechatuser, parent, false);
                return new RecyclerViewHolderUser(view, mContext);
            default:
                view = mLayoutInflater.inflate(R.layout.itemmessagechatadmin, parent, false);
                return new RecyclerViewHolderAdmin(view, mContext);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = mMessageModels.get(position);
        if (holder instanceof RecyclerViewHolderAdmin) {
            RecyclerViewHolderAdmin recyclerViewHolderAdmin = (RecyclerViewHolderAdmin) holder;
            recyclerViewHolderAdmin.tvAdmin.setText(messageModel.getmMessage());
        } else if (holder instanceof RecyclerViewHolderUser) {
            RecyclerViewHolderUser recyclerViewHolderUser = (RecyclerViewHolderUser) holder;
            recyclerViewHolderUser.tvUser.setText(messageModel.getmMessage());
        } else {
            RecyclerViewHolderUser recyclerViewHolderUser = (RecyclerViewHolderUser) holder;
            recyclerViewHolderUser.tvUser.setText(messageModel.getmMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mMessageModels.get(position).getmType() == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageModels == null ? 0 : mMessageModels.size();
    }

    public class RecyclerViewHolderUser extends RecyclerView.ViewHolder {
        Context context;
        TextView tvUser;

        public RecyclerViewHolderUser(View itemView, Context context) {
            super(itemView);
            this.context = context;
            tvUser = (TextView) itemView.findViewById(R.id.tvMessageUser);
        }
    }

    public class RecyclerViewHolderAdmin extends RecyclerView.ViewHolder {
        Context context;
        TextView tvAdmin;

        public RecyclerViewHolderAdmin(View itemView, Context context) {
            super(itemView);
            this.context = context;
            tvAdmin = (TextView) itemView.findViewById(R.id.tvMessageAdmin);
        }
    }

}
