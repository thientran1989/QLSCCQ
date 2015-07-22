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
import evnspc.sucocapquang.vn.qlsccq.object.Obj_SU_CO;


public class Adapter_SUCO extends
        RecyclerView.Adapter<Adapter_SUCO.ContactViewHolder> {
    private List<Obj_SU_CO> contactList;
    private List<Obj_SU_CO> all_contactList;
    Context mcon;

    public Adapter_SUCO(Context mcon, List<Obj_SU_CO> all_contactList) {
        this.mcon=mcon;
        this.all_contactList = all_contactList;
        this.contactList = new ArrayList<Obj_SU_CO>(this.all_contactList);
    }
    public void get_search(String key){
        contactList.clear();
        if (key.length()>0){
            for (Obj_SU_CO oMBA : all_contactList){
                if (oMBA.getTRU().contains(key)){
                    contactList.add(oMBA);
                }
            }
        }else{
            contactList = new ArrayList<Obj_SU_CO>(this.all_contactList);
        }
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        final Obj_SU_CO ci = contactList.get(i);

        contactViewHolder.tv_thoigian.setText(ci.getTIME_PHATHIEN()+" den "+ci.getTIME_XULY());
        contactViewHolder.tv_noidung.setText(ci.getNOI_DUNG());
        contactViewHolder.tv_nguyennhan.setText("Nguyen nhan : " + ci.getNGUYEN_NHAN());
        contactViewHolder.tv_khacphuc.setText("Khac phuc : " + ci.getKHAC_PHUC());
        contactViewHolder.tv_phamvi.setText("Pham vi : " + ci.getPHAM_VI());
        contactViewHolder.tv_donvi.setText("Don vi : " + ci.getDON_VI());
        contactViewHolder.tv_tru.setText("Tai tru : " + ci.getTRU());


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
                inflate(R.layout.cardview_row_suco, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public TextView tv_thoigian;
        public TextView tv_noidung;
        public TextView tv_nguyennhan;
        public TextView tv_khacphuc;
        public TextView tv_phamvi;
        public TextView tv_donvi;
        public TextView tv_tru;

        private ClickListener clickListener;

        public ContactViewHolder(View v) {
            super(v);

           tv_thoigian = (TextView) v
                    .findViewById(R.id.tv_thoigian_cardview_suco);
           tv_noidung = (TextView) v
                    .findViewById(R.id.tv_noidung_cardview_suco);
            tv_nguyennhan = (TextView) v
                    .findViewById(R.id.tv_nguyennhan_cardview_suco);
           tv_khacphuc = (TextView) v
                    .findViewById(R.id.tv_khacphuc_cardview_suco);
            tv_phamvi = (TextView) v
                    .findViewById(R.id.tv_phamvi_cardview_suco);
            tv_donvi = (TextView) v
                    .findViewById(R.id.tv_donvi_cardview_suco);
            tv_tru = (TextView) v
                    .findViewById(R.id.tv_tru_cardview_suco);


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
