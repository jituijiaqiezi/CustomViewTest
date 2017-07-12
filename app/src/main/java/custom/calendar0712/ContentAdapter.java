package custom.calendar0712;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcp.customviewtest.R;

import java.util.List;

class ContentAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

        public ContentAdapter(List<Integer> data) {
            super(R.layout.item_content, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Integer item) {
            helper.setVisible(R.id.line_bottom,helper.getLayoutPosition()>=23*7);
        }
    }