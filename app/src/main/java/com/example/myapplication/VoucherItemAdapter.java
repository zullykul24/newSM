package com.example.myapplication;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VoucherItemAdapter  extends BaseAdapter {

    private Context context;
    private int layout;
    private List<VoucherItem> listVoucherItem;
    @Override
    public int getCount() {
        return listVoucherItem.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public VoucherItemAdapter(Context context, int layout, List<VoucherItem> listVoucherItem) {
        this.context = context;
        this.layout = layout;
        this.listVoucherItem = listVoucherItem;
    }

    private  class ViewHolder{
        TextView  discount;
        TextView title;
        TextView  startDate;
        TextView endDate;
        TextView conditions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final   ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.discount = (TextView) convertView.findViewById(R.id.discount_view);
            holder.title = (TextView) convertView.findViewById(R.id.title_view);
            holder.startDate = (TextView) convertView.findViewById(R.id.startDate_view);
            holder.endDate= (TextView) convertView.findViewById(R.id.endDate_view);
            holder.conditions = (TextView) convertView.findViewById(R.id.conditions_view);
            convertView.setTag(holder);
        } else {
            holder = (VoucherItemAdapter.ViewHolder) convertView.getTag();
        }
        final  VoucherItem voucher =  listVoucherItem.get(position);
        holder.discount.setText(voucher.getDiscount()+"%");
        holder.title.setText(voucher.getTitle());
        java.sql.Date startDateTime=new java.sql.Date(voucher.getStartDate());
        java.sql.Date endDateTime=new java.sql.Date(voucher.getEndDate());
        holder.startDate.setText("" + startDateTime);
        holder.endDate.setText( "" + endDateTime);
        ArrayList<String> conds = voucher.getConditions();
        if (conds.size()>0){
            String str = "";
            for(String item : conds){
                str += item;
                str +='\n';
            }
        holder.conditions.setText(str);
        }
        else{
            holder.conditions.setText("Áp dụng cho mọi đối tượng");
        }

        return convertView;
    }

}
