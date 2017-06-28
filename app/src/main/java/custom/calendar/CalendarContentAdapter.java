package custom.calendar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcp.customviewtest.R;

import java.util.List;

class CalendarContentAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

        public CalendarContentAdapter(List<Integer> data) {
            super(R.layout.item_content, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Integer item) {
        }
    }