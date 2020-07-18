package com.example.nitastudcovid19trackerindia;


public class AdopterItems {
    private String mstate,mColor,mlColor;
    private String mActive,mRecover,mDeath,mTotal,mtChange,mrChange,mdChange,maChange;
    public AdopterItems(String state,String total,String active,String recover,String deth
                        ,String tChange,String aChange,String rChange,String dChange,String colors,String lcolor){
        mstate=state;
        mTotal=total;
        mActive=active;
        mRecover=recover;
        mDeath=deth;
        mtChange=tChange;
        mrChange=rChange;
        mdChange=dChange;
        maChange=aChange;
        mColor=colors;
        mlColor=lcolor;
    }



    public String getState() {
        return mstate;
    }
    public String getTotal() {
        return mTotal;
    }
    public String getActive() {
        return mActive;
    }
    public String getRecover() {
        return mRecover;
    }
    public String getDeath() {
        return mDeath;
    }
    public String gettChange() {
        return mtChange;
    }
    public String getrChange() {
        return mrChange;
    }
    public String getdChange() {
        return mdChange;
    }
    public String getaChange() {
        return maChange;
    }
    public String getaColors() {
        return mColor;
    }
    public String getaLColors() {
        return mlColor;
    }
}
