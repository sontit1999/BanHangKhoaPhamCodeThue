package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doantrasua.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import activity.MainActivity;
import model.Giohang;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arraygiohang;

    public GioHangAdapter(Context context, ArrayList<Giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txttengiohang, txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus, btnvalue, btnplus;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang, null);

            viewHolder.txttengiohang = convertView.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang = convertView.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang = convertView.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus = convertView.findViewById(R.id.buttonminus);
            viewHolder.btnvalue = convertView.findViewById(R.id.buttonvalue);
            viewHolder.btnplus = convertView.findViewById(R.id.buttonplus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp()) + " vnd");
        Picasso.get().load(giohang.getHinhsp())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.cancel)
                .into(viewHolder.imggiohang);
        viewHolder.btnvalue.setText(giohang.getSoluongsp() + "");
        /*
        int sl = Integer.parseInt(viewHolder.btnvalue.getText().toString());
        if(sl >= 10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if(sl <=1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        } else if(sl >=1){
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }

         */
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.manggiohang.get(position).soluongsp = MainActivity.manggiohang.get(position).soluongsp + 1 ;
                activity.Giohang.Tinhtongtien();
                int slhientai = Integer.parseInt(finalViewHolder.btnvalue.getText().toString());
                finalViewHolder.btnvalue.setText((slhientai + 1)+"");
                /*
                int solmoinhat = Integer.parseInt(finalViewHolder.btnvalue.getText().toString())+1;
                int slht = MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(solmoinhat);
                long giamoinhat = (giaht * solmoinhat)/slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + " vnd");
                activity.Giohang.EventUltil();
                if(solmoinhat > 9){
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(solmoinhat));
                }else {
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(solmoinhat));
                }
                */
            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.manggiohang.get(position).soluongsp = MainActivity.manggiohang.get(position).soluongsp - 1 ;
                int slhientai = Integer.parseInt(finalViewHolder.btnvalue.getText().toString());
                   if(slhientai>1){
                       finalViewHolder.btnvalue.setText((slhientai- 1) + "");
                       activity.Giohang.Tinhtongtien();
                   }else{
                       Toast.makeText(context, "Phải mua ít nhất 1 sản phẩm. Nếu không mua vui lòng giữ vào sản phẩm để xóa ^^", Toast.LENGTH_SHORT).show();
                   }

                 /*
                int solmoinhat = Integer.parseInt(finalViewHolder.btnvalue.getText().toString())-1;
                int slht = MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(solmoinhat);
                long giamoinhat = (giaht * solmoinhat)/slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + " vnd");
                activity.Giohang.EventUltil();
                if(solmoinhat < 2){
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(solmoinhat));
                }else {
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(solmoinhat));
                }
                */
            }
        });
        return convertView;
    }
}
