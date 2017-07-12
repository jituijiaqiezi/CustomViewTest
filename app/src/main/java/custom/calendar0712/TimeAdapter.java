package custom.calendar0712;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcp.customviewtest.R;

import java.util.List;

class TimeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        public TimeAdapter(Context context, @Nullable List<String> data) {
            super(R.layout.item_time, data);

        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.text, item);
        }
    }