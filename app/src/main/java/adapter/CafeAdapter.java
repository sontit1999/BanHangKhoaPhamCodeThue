package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doantrasua.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.Sanpham;

public class CafeAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraycafe;

    public CafeAdapter(Context context, ArrayList<Sanpham> arraycafe) {
        this.context = context;
        this.arraycafe = arraycafe;
    }

    @Override
    public int getCount() {
        return arraycafe.size();
    }

    @Override
    public Object getItem(int position) {
        return arraycafe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txttencafe, txtgiacafe, txtmotacafe;
        public ImageView imgcafe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_cafe, null);

            viewHolder.txttencafe = convertView.findViewById(R.id.textviewtencafe);
            viewHolder.txtgiacafe = convertView.findViewById(R.id.textviewgiacafe);
            viewHolder.txtmotacafe = convertView.findViewById(R.id.textviewmotacafe);
            viewHolder.imgcafe = convertView.findViewById(R.id.imageviewcafe);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttencafe.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiacafe.setText("Giá: " + decimalFormat.format(sanpham.getGiasanpham()) + " vnd");

//        viewHolder.txtmotatrasua.setMaxLines(2);
//        viewHolder.txtmotatrasua.setEllipsize(TextUtils.TruncateAt.END); //mo ta qua 2 dong thi co dau 3 cham
        viewHolder.txtmotacafe.setText(sanpham.getMotasanpham());

        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.cancel)
                .into(viewHolder.imgcafe);
        return convertView;
    }
}