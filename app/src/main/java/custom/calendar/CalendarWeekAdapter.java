package custom.calendar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcp.customviewtest.R;

import java.util.List;

class CalendarWeekAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public CalendarWeekAdapter(List<String> data) {
            super(R.layout.item_week, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.text,item);
        }
    }