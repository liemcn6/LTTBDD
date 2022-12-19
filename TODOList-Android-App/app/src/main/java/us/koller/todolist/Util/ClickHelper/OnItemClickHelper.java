package us.koller.todolist.Util.ClickHelper;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import us.koller.todolist.R;

import us.koller.todolist.Util.Callbacks.OnItemClickInterface;

/**
 * Created by Lukas on 11.03.2016.
 */
public class OnItemClickHelper {

    private RecyclerView mRecyclerView;
    private OnItemClickInterface mOnItemClickInterface;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickInterface != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickInterface.onItemClicked(mRecyclerView, holder.getAdapterPosition(), holder);
            }
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickInterface != null) {
                view.setOnClickListener(mOnClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    public OnItemClickHelper(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        mRecyclerView.setTag(R.id.item_click_helper, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static OnItemClickHelper addTo(RecyclerView mRecyclerView) {
        OnItemClickHelper helper = (OnItemClickHelper) mRecyclerView.getTag(R.id.item_click_helper);
        if (helper == null) {
            helper = new OnItemClickHelper(mRecyclerView);
        }
        return helper;
    }

    public OnItemClickHelper setOnItemClickListener(OnItemClickInterface listener) {
        mOnItemClickInterface = listener;
        return this;
    }
}
