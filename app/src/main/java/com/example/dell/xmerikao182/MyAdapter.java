package com.example.dell.xmerikao182;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.xmerikao182.bean.InfoBean;

import java.util.List;

/**
 * Created by DELL on 2017/7/27.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.IViewHolder> {

    private List<InfoBean.ResultBean.DataBean> list;
    private Context context;
    private onItemClickListener listener;

    public MyAdapter(List<InfoBean.ResultBean.DataBean> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IViewHolder iViewHolder = new IViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false));
        return iViewHolder;
    }

    @Override
    public void onBindViewHolder(IViewHolder holder, final int position) {
        //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.textView.getLayoutParams();
        holder.textView.setText(list.get(position).title);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class IViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public IViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.item_title);
        }
    }
    public interface onItemClickListener{
        void onItemClickListener(View view,int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
