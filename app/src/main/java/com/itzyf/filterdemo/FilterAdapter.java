package com.itzyf.filterdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 依风听雨
 * @version 创建时间：2017/5/23 15:03
 */

public class FilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnCheckListener listener;
    private Context context;
    private List<LabelBean> data;
    private Map<String, LabelBean> oldCheckBean = new HashMap<>();

    public FilterAdapter(Context context, List<LabelBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (data.get(i).getType() == LabelBean.LABEL)
            return new LabelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_filter, viewGroup, false));
        else {
            return new HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_header, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        LabelBean labelBean = getData(position);
        if (labelBean.getType() == LabelBean.LABEL) {
            CheckedTextView textView = ((CheckedTextView) ((LabelViewHolder) viewHolder).itemView);
            textView.setChecked(labelBean.isChecked()); //重新设置check值，防止因复用布局导致的错乱
            textView.setText(labelBean.getLabel());
            textView.setTag(labelBean);
            textView.setOnClickListener(v -> onCheckClick((CheckedTextView) v));
        } else {
            ((HeaderViewHolder) viewHolder).tvTitle.setText(labelBean.getLabel()); //header
        }
    }

    private void onCheckClick(CheckedTextView view) {
        LabelBean tag = (LabelBean) view.getTag();
        LabelBean oldBean = oldCheckBean.get(tag.getGroupName());
        if (oldBean != null) {
            oldBean.setChecked(false);
            if (oldBean == tag) {
                oldCheckBean.remove(tag.getGroupName());
                notifyDataSetChanged();
                return;
            }
        }
        tag.setChecked(true);
        oldCheckBean.put(tag.getGroupName(), tag);
        if (listener != null)
            listener.onCheck(tag);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public LabelBean getData(int position) {
        return data.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    class LabelViewHolder extends RecyclerView.ViewHolder {

        public LabelViewHolder(View itemView) {
            super(itemView);
        }
    }


    public void setOnCheckListener(OnCheckListener listener) {
        this.listener = listener;
    }

    interface OnCheckListener {
        void onCheck(LabelBean item);
    }
}
