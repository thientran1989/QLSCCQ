package evnspc.sucocapquang.vn.qlsccq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.thtsoftlib.function.Thtcovert;

import java.util.ArrayList;
import java.util.List;

import evnspc.sucocapquang.vn.qlsccq.R;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TRU;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_TUYEN;
import evnspc.sucocapquang.vn.qlsccq.object.Obj_donvi;


public class Adapter_TRU extends
        RecyclerView.Adapter<Adapter_TRU.ContactViewHolder> {
    private List<Obj_donvi> list_tuyen;
    private List<Obj_TRU> contactList;
    private List<Obj_TRU> all_contactList;
    Context mcon;

    public Adapter_TRU(Context mcon, List<Obj_donvi> list_tuyen, List<Obj_TRU> all_contactList) {
        this.mcon=mcon;
        this.list_tuyen = list_tuyen;
        this.all_contactList = all_contactList;
        this.contactList = new ArrayList<Obj_TRU>(this.all_contactList);
    }
    public void get_search(String key){
        contactList.clear();
        if (key.length()>0){
            for (Obj_TRU oMBA : all_contactList){
                if (oMBA.getTRU().contains(key)){
                    contactList.add(oMBA);
                }
            }
        }else{
            contactList = new ArrayList<Obj_TRU>(this.all_contactList);
        }
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        final Obj_TRU ci = contactList.get(i);

        contactViewHolder.tv_tru.setText(ci.getTRU());
        contactViewHolder.tv_tuyen.setText(ci.getDONVI_label(list_tuyen));
        contactViewHolder.tv_mangxong.setText("MĂNGXÔNG : " + ci.getMANGXONG_label());
        contactViewHolder.tv_nhanhre.setText("NHÁNH RẼ : " + ci.getNHANH_RE_label());
        contactViewHolder.tv_cachtram.setText("Cach tram : " + Thtcovert.int_to_String(ci.getCACHTRAM()));
        contactViewHolder.tv_ghichu.setText("Ghi chú : " +ci.getGHI_CHU());


        contactViewHolder.setClickListener(new ContactViewHolder.ClickListener() {
            public void onClick(View v, int pos, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(mcon,"longclick "+ci.getTRU(),Toast.LENGTH_LONG).show();
                } else {
                    String TT = "thong tin ";
                    TT=TT+"Tại trụ :"+ci.getTRU()+"\n";
//                    TT=TT+"MSTS :"+ci.getMSTS()+"\n";
//                    TT=TT+"Thuộc kho :"+ci.getKho()+"\n";
//                    TT=TT+"Nhà sx :"+ci.getNha_sanxuat()+"\n";
//                    TT=TT+"Công suất :"+ci.getCong_suat()+"\n";
//                    TT=TT+"Nấc vận hành :"+ci.getNac_vanhanh()+"\n";
                    showBasicLongContent(TT);
                }
            }
        });
    }
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.cardview_row_tru, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public TextView tv_tru;
        public TextView tv_tuyen;
        public TextView tv_mangxong;
        public TextView tv_nhanhre;
        public TextView tv_cachtram;
        public TextView tv_ghichu;

        private ClickListener clickListener;

        public ContactViewHolder(View v) {
            super(v);

           tv_tru = (TextView) v
                    .findViewById(R.id.tv_tru_cardview_tru);
           tv_tuyen = (TextView) v
                    .findViewById(R.id.tv_tuyen_cardview_tru);
            tv_mangxong = (TextView) v
                    .findViewById(R.id.tv_mangxong_cardview_tru);
           tv_nhanhre = (TextView) v
                    .findViewById(R.id.tv_nhanhre_cardview_tru);
            tv_cachtram= (TextView) v
                    .findViewById(R.id.tv_cachtram_cardview_tru);
            tv_ghichu = (TextView) v
                    .findViewById(R.id.tv_ghichu_cardview_tru);


            v.setOnClickListener(this);
        }
        /* Interface for handling clicks - both normal and long ones. */
        public interface ClickListener {
            public void onClick(View v, int position, boolean isLongClick);

        }

        /* Setter for listener. */
        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            // If not long clicked, pass last variable as false.
            clickListener.onClick(v, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View v) {

            // If long clicked, passed last variable as true.
            clickListener.onClick(v, getPosition(), true);
            return true;
        }


    }
    private void showBasicLongContent(String thongtin) {
        new MaterialDialog.Builder(mcon)
                .title(R.string.thong_tin_may)
                .content(thongtin)
                    .positiveText(R.string.dong)
                .negativeText(R.string.sua)
                .show();
    }
}
