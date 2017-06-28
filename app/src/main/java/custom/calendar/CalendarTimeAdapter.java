package custom.calendar;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcp.customviewtest.R;

import java.util.List;

class CalendarTimeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        public CalendarTimeAdapter(Context context, @Nullable List<String> data) {
            super(R.layout.item_time, data);

        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.text, item);
        }
    }